package nj.api.bs;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nj.api.dao.HomeDAO;
import nj.api.dao.InterestClassDAO;
import nj.api.dao.PayOrderDAO;
import nj.api.dao.ServiceDAO;
import nj.api.dao.StuDAO;
import nj.api.dao.UserDAO;
import nj.api.entity.InterestClassEntity;
import nj.api.entity.OrderEntity;
import nj.api.entity.ServiceEntity;
import nj.api.entity.StuInfo;
import nj.api.entity.UserInfo;
import nj.common.Constants;
import nj.common.utils.APIUtil;
import nj.common.utils.DateUtil;
import nj.common.utils.StringUtils;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.EventData;
import com.pingplusplus.model.Webhooks;



@Service
public class PayBS {

	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PayOrderDAO poDAO;
	
	@Autowired
	private ServiceDAO serviceDAO;
	
	@Autowired
	private InterestClassDAO intClassDAO;
	
	@Autowired
	private StuDAO stuDAO;
	
	@Autowired
	private HomeDAO homeDAO;
	
	/**
	 * 获取支付凭证
	 * @param userId
	 * @param amount
	 * @param channel
	 * @param clientIp
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getPayOrder(String userId,String amount,
			String channel,String clientIp,String token,String buyMonth,
			String terId,String type,String serviceId,String psId)throws Exception{
		Map<String, Object> resMap = new HashMap<String, Object>();
		
//用户验证		
		Long userid = Long.parseLong(StringUtils.base64Decode(userId));
		UserInfo olderUser = userDAO.selectUserById(userid);
		if(olderUser==null){
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
		if(!APIUtil.checkToken(token,olderUser.getuToken())){
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
//用户验证
		
		if(olderUser.getIsAccount()!=0){
			resMap.put("code", Constants.APICODE_ERROR);
			return resMap;
		}
		
		long pServiceId = Long.valueOf(psId);
		StuInfo student = stuDAO.selectStuByUserId(userid);
		Map<String,Object> feeMap = serviceDAO.selectServiceFee(pServiceId);
		Long priceTemp = Long.parseLong(feeMap.get("ps_price").toString());
		Long userAmount = Long.valueOf(amount);	
		//判断是否是业主类型
		if("02".equals(student.getStuType())){
			float dis = Float.valueOf(homeDAO.selectCommonConfig("discount").get("value1").toString());
			priceTemp = new Double( Math.rint(priceTemp*dis)).longValue();
		}
		
		//判断传入的价格是否正确
		if(priceTemp.longValue()>userAmount.longValue() ){
			resMap.put("code", Constants.APICODE_ERROR);
			return resMap;
		}
		
		OrderEntity oe = new OrderEntity();
		
		String orderSub = "test";	
		//保教费订购
		if("1".equals(type)){
			oe.setAmount(userAmount);
			oe.setServiceId(Long.valueOf(serviceId));
			oe.setOrderType(01);
			orderSub=feeMap.get("ps_name").toString();
		}
		//餐费订购
		if("2".equals(type)){
			oe.setAmount(userAmount);
			oe.setServiceId(Long.valueOf(serviceId));
			oe.setOrderType(02);
			orderSub=feeMap.get("ps_name").toString();
		}
		//兴趣班订购，线程锁管理名额数据
		if("3".equals(type)){
	    	InterestClassEntity ice = intClassDAO.getInterestClass(Long.valueOf(terId));  
			if(ice.getTerCount()<=ice.getTerNum()){
				resMap.put("desc", "兴趣班名额已满员！");
				resMap.put("code", Constants.APICODE_CLASS_ERROR);
				return resMap;
			}
			
			//判断订单执行情况
			ServiceEntity sEntity = serviceDAO.getServiceByTer(Long.valueOf(terId), userid);
			if(sEntity != null){
				resMap.put("code", Constants.APICODE_ERROR);
				return resMap;
			}
			
//			ServiceEntity tse = serviceDAO.getServiceByTer(Long.valueOf(terId), userid);
//			if(tse != null){
//				oe.setServiceId(tse.getServiceId());
//			}
			//兴趣班名额
			oe.setAmount(userAmount);
			oe.setTerId(Long.valueOf(terId));
			oe.setOrderType(03);
			orderSub=feeMap.get("ps_name").toString();
		}
		
		oe.setChannel(channel);
		oe.setCreateTime(new Date());
		oe.setState(0);    //表示未交费，只是生成了订单
		oe.setOrderSub(orderSub);
		oe.setUserId(userid);
		oe.setPsId(pServiceId);
		oe.setBuyMonth(Integer.valueOf(buyMonth));
		poDAO.insertPayOrder(oe);
		//生成订单号 ---订单号是８位以上数字
		String orderNo = String.valueOf(oe.getOrderId());
		orderNo = StringUtils.toStringAdd(orderNo,orderNo.length()+8);
		//生成支付凭证
		Charge charge = charge(amount,orderNo,channel,clientIp);
		
		if(charge == null){
			resMap.put("code", Constants.APICODE_ERROR);
			return resMap;
		}
		oe.setChargeId(charge.getId());
		poDAO.updateChargeId(oe);
		resMap.put("charge", charge);
		resMap.put("code", Constants.APICODE_OK);
		
		return resMap;	
	}
	
	/**
	 * 支付回调接口
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> callbackPay(HttpServletRequest request)throws Exception{
		Map<String, Object> resMap = new HashMap<String, Object>();
		
        // 获得 http body 内容
        BufferedReader reader = request.getReader();
        StringBuffer buffer = new StringBuffer();
        String string;
        while ((string = reader.readLine()) != null) {
            buffer.append(string);
        }
        reader.close();
  
        String data = buffer.toString();
        
        // 解析异步通知数据
        Event event = Webhooks.eventParse(data);
        
        EventData ed = event.getData();
        
        Charge charge = (Charge)ed.getObject();
        
		long orderId = Long.parseLong(charge.getOrderNo().substring(8));
		
		OrderEntity order = poDAO.selectPayOrder(orderId);
		
		if(order==null){
			resMap.put("object", charge);
			return resMap;
		}  
		
		long userId = order.getUserId();
		
		String serviceType ="";
		
		if(order.getServiceId()==null){
			serviceType = "03";
		}
             
        if ("charge.succeeded".equals(event.getType())) {
        	
        } else if ("refund.succeeded".equals(event.getType())) {
        	
        } else {
        	//支付失败处理流程
        	payException(userId," ",charge.getOrderNo(),serviceType,"error");
        	return resMap;
        }  
        if(!charge.getPaid()){
        	//支付失败处理流程
        	payException(userId," ",charge.getOrderNo(),serviceType,"error");
        	return resMap;
        }
        System.out.println("AAAAAAAAAAAAAAAAAAA+==========");
        //支付成功流程
        processOrder(order,userId); 

		resMap.put("object", charge);
		
		return resMap;
	}
	
	/**
	 * 支付历史记录查询
	 * @param userId
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> payHistory(String userId,String token)throws Exception{
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		
// 用户鉴权		
		Long userid = 0l;
		try{
			userid = Long.parseLong(StringUtils.base64Decode(userId));
		}catch(Exception e){
			userId="";
		}
		if(StringUtils.isNull(userId)){
			resMap.put("code", Constants.APICODE_ERROR);
			return resMap;
		}
		
		UserInfo user = userDAO.selectUserById(userid);
		
		if(!APIUtil.checkToken(token,user.getuToken())){
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
// 用户鉴权
		
		List<Map<String,Object>> monList = poDAO.selectHisMonth(userid);
		
		if(monList.isEmpty()){
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}
		

		List<Map<String,Object>> mlist = new ArrayList<Map<String,Object>>();
		for(int i=0;i<monList.size();i++){
			Map<String, Object> monthMap = new HashMap<String, Object>();
			Map<String,Object> map = monList.get(i);
			String month = (String)map.get("month");
			Map<String,Object> paraMap =new HashMap<String,Object>();
			paraMap.put("userId", userid);
			paraMap.put("month", month);
			List<Map<String,Object>> hisList = poDAO.payHistory(paraMap);
			monthMap.put("paymentMonth", month);
			monthMap.put("mlist", hisList);
			mlist.add(monthMap);
		}
		
		resMap.put("list", mlist);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
		
	}
	
	/**
	 * 支付订单确认业务
	 * @param userId
	 * @param token
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> payException(Long userId,String token,String orderId,
			String serviceType,String payType)throws Exception{
		Map<String, Object> resMap = new HashMap<String, Object>();
		
//用户验证		
		UserInfo olderUser = userDAO.selectUserById(userId);
		if(olderUser==null){
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
		if(!APIUtil.checkToken(token,olderUser.getuToken())){
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
//用户验证
	
		long orderid = Long.parseLong(orderId.substring(8));
		OrderEntity order = poDAO.selectPayOrder(orderid);
		if(order==null){
			resMap.put("code", Constants.APICODE_ERROR);
			return resMap;
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId", orderid);
		
		if("ok".equals(payType)){
			if(order.getState()==1){
				resMap.put("code", Constants.APICODE_OK);
				return resMap;
			}
			if(checkCharge(order)){
				resMap.put("code", Constants.APICODE_OK);
				return resMap;
			}
			paramMap.put("state", 2);
			poDAO.updatePayOrder(paramMap);
			resMap.put("code", Constants.APICODE_ERROR);
			return resMap;

		}
		if("error".equals(payType)){
			//订单状态改为2，表示订单被取消
			paramMap.put("state", 2);
			poDAO.updatePayOrder(paramMap);		
			//兴趣班订购回退
//			if("03".equals(serviceType)){
//				
//				if(!cancelInterestClass(orderid,order)){
//					resMap.put("code", Constants.APICODE_CLASS_ERROR);
//					return resMap;
//				}
//			}
		}
		if("cancel".equals(payType)){
			paramMap.put("state", 4);
			poDAO.updatePayOrder(paramMap);
		}
	
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
	}
	
	/**
	 * 判断订单缴费信息,成功时执行订单动作
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public boolean checkCharge(OrderEntity order)throws Exception {
		Charge charge = null;
		try{
			//查询交易结果
			charge = Charge.retrieve(order.getChargeId());
		}catch(PingppException e){
		}
		if(charge == null){
			throw new Exception();
		}
		if(charge.getPaid()){
			//处理订单业务
			System.out.println("BBBBBBBBBBBBB+==========");
			processOrder(order,order.getUserId());
			
			return true;
		}else{
			//
			return false;
		}	
	}
	
	/**
	 * 支付成功处理流程
	 * @param order
	 * @param userId
	 * @throws Exception
	 */
	private void processOrder(OrderEntity order,Long userId)throws Exception{
        //操作订单相关信息
		if(order.getState()==2||order.getState()==4||order.getState()==1){
			return ;
		}
		ServiceEntity sEntity = serviceDAO.getServiceByTer(order.getTerId(), userId);
        
        Map<String, Object> serviceMap = new HashMap<String, Object>();
        
		if(order.getOrderType()==03&&sEntity==null){
			InterestClassEntity interClass = intClassDAO.getInterestClass(order.getTerId());
			// 兴趣班
			StuInfo student = stuDAO.selectStuByUserId(userId);
			serviceMap.put("stuId", student.getStuId());
			serviceMap.put("psId", order.getPsId());
			serviceMap.put("userId", order.getUserId());
			serviceMap.put("sTime", new Date());
			serviceMap.put("sType", 03);
			serviceMap.put("state", 1);
			serviceMap.put("endTime", interClass.getTerTime());
			serviceMap.put("terId", order.getTerId());
			try{
				serviceDAO.addNewService(serviceMap);
				updateInterestClass(order.getTerId());
			}catch(Exception e){
				e.printStackTrace();
				return;
			}		
		}else{
			//基础费用
			ServiceEntity se = serviceDAO.getServiceById(order.getServiceId());
			if(se!=null){
				//更新订购服务表
				serviceMap.put("serviceId", se.getServiceId());
				serviceMap.put("endTime", DateUtil.plusMonths(new DateTime(se.getEndTime()),order.getBuyMonth()));
				serviceDAO.updateService(serviceMap);
			}else{
				return;
			}

		}		
		//更新订单表
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("state", 1);
		paramMap.put("orderId", order.getOrderId());
		poDAO.updatePayOrder(paramMap);
	}
	
	
	
    /**
     * 创建 Charge
     * 
     * 创建 Charge 用户需要组装一个 map 对象作为参数传递给 Charge.create();
     * map 里面参数的具体说明请参考：https://pingxx.com/document/api#api-c-new
     * @return
     */
    private Charge charge(String amount,String orderNo,String channel,String clientIp) {
        Charge charge = null;
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        
        Pingpp.apiKey = "sk_test_5WTSm5SCq1WL5CKGSCSeDyX5";
        
        //订单金额分
        chargeMap.put("amount", amount);
        chargeMap.put("currency", "cny");
        //标题
        chargeMap.put("subject", "meal");
        chargeMap.put("body", "meal");
        //8-20订单号
        chargeMap.put("order_no", orderNo);
        //支付渠道
        chargeMap.put("channel", channel);
        
        chargeMap.put("client_ip", clientIp);
        Map<String, String> app = new HashMap<String, String>();
        app.put("id","app_yXPq944SuTy5Cerb");
        chargeMap.put("app", app);
        try {
            //发起交易请求
            charge = Charge.create(chargeMap);
        } catch (PingppException e) {
            e.printStackTrace();
        }
        return charge;
    }
    
    /**
     * 报名兴趣班
     * @param userId
     * @param terId
     * @return
     * @throws Exception
     */
    private synchronized void updateInterestClass(Long terId)throws Exception{
    	intClassDAO.updateInterestClass(terId);
    }
    
//    /**
//     * 取消报名兴趣班
//     * @param userId
//     * @param terId
//     * @return
//     * @throws Exception
//     */
//    private synchronized boolean cancelInterestClass(Long orderId,OrderEntity order)throws Exception{
//    	   	
//    	InterestClassEntity ice = intClassDAO.getInterestClass(order.getTerId());
//    	
//    	if(ice.getTerCount()<=0){
//    		throw new Exception();
//    	}
//    	
//    	int num = intClassDAO.cancelInterestClass(ice.getTerId());
//    	
//    	if(num>0){
//    		return true;
//    	}
//    	
//    	return false;
//    }
	
	
}
