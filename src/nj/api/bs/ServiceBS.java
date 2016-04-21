package nj.api.bs;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import nj.api.dao.HomeDAO;
import nj.api.dao.ServiceDAO;
import nj.api.dao.StuDAO;
import nj.api.dao.UserDAO;


import nj.api.entity.ServiceEntity;
import nj.api.entity.StuInfo;
import nj.api.entity.UserInfo;
import nj.common.Constants;
import nj.common.utils.APIUtil;
import nj.common.utils.DateUtil;
import nj.common.utils.StringUtils;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.push.UserANDROIDPush;
import com.push.UserIOSPush;


@Service
public class ServiceBS {

	
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ServiceDAO serviceDAO;	
	
	@Autowired
	private HomeDAO homeDAO;
	
	@Autowired
	private StuDAO stuDAO;
	

	/**
	 * 获取订购的服务信息
	 * @param userId
	 * @param type
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getBugInfo(String userId,String type,String token)throws Exception{
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
		
		//保教费
		if("01".equals(type)){
			resMap.put("feeSubject", "保教费");
			resMap.putAll(selectInfo(type,userid));
		} 
		//餐费
		if("02".equals(type)){
			resMap.put("feeSubject", "餐费");
			resMap.putAll(selectInfo(type,userid));
		}	
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
	}
	
	/**
	 * 查询费用信息
	 * @param type
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object>  selectInfo(String type,Long userId)throws Exception{
		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> stuMap = new HashMap<String, Object>();
		stuMap.put("userId", userId);
		stuMap.put("type", type);
		List<ServiceEntity> stulist = serviceDAO.getServiceList(stuMap);
		ServiceEntity stuse = null;
		if(stulist.size() > 0){
			stuse = stulist.get(0);
		}else{
			new Exception();
		}
		
		Map<String,Object> feeMap= serviceDAO.selectServiceFee(stuse.getPsId());
		Long price = Long.parseLong(feeMap.get("ps_price").toString());
		resMap.put("serviceId",stuse.getServiceId());
		resMap.put("fee",price);
		resMap.put("psId", stuse.getPsId());
		
		StuInfo student = stuDAO.selectStuByUserId(userId);
		
		//判断是否是业主类型
		if("02".equals(student.getStuType())){
			resMap.put("discount",homeDAO.selectCommonConfig("discount").get("value1"));
		}else{
			resMap.put("discount","1");
		}
		//判断新学生
		if(isNewStudent(stuse)){
			resMap.put("endTime","");
			resMap.put("arrea",0);
			
		}else{	
			resMap.put("endTime",stuse.getEndTime());
			resMap.put("arrea",countArrea(stuse)*price);	
		}	
		return resMap;
		
	}
	
	
	
	/**
	 * 判断是否首次
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private boolean isNewStudent(ServiceEntity se)throws Exception{
		if(se.getEndTime()==null){
			return true;
		}
	
		return false;
	}
	
	/**
	 * 计算欠费金额
	 * @param stuse
	 * @return
	 * @throws Exception 
	 */
	private Integer countArrea(ServiceEntity stuse) throws Exception{	
		
		DateTime datetime =  new DateTime(DateUtil.getday());
		DateTime datetime1 =  new DateTime(stuse.getEndTime());
		
		if(datetime.isBefore(datetime1)||datetime.equals(datetime1)){
			return 0 ;
		}
		int months = Months.monthsBetween(datetime1, datetime).getMonths();
		months=months+1;
		DateTime dd = datetime.dayOfMonth().withMaximumValue();
		
		if(dd.equals(datetime)){
			months=months-1;
		}
		return months;
	}
	
	
	
	
	/**
	 * 后台查询费用单价类表
	 * @param adminId
	 * @param psId
	 * @param cursor
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getFeeList(String stuId,int cursor,int offset)throws Exception{
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> payList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> pageMap = new HashMap<String,Object>();
		pageMap.put("cursor", cursor);
		pageMap.put("offset", offset);
		Long count =  0L;
		//查所有
		if(StringUtils.isNull(stuId)){
			pageMap.put("stuId", null);
			payList = serviceDAO.getPayList(pageMap);
			count = serviceDAO.getServiceCount(pageMap);
		}else{
			//按条件查询
			pageMap.put("stuId", stuId);
			payList = serviceDAO.getPayList(pageMap);
			count = serviceDAO.getServiceCount(pageMap);
		}
		
		resMap.put("list", payList);
		resMap.put("count", count);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;	
	}
	
	
	/**
	 * 后台定时任务,判断欠费学生,并发送通知给用户
	 * @throws Exception
	 */
	public void checkArrearageTask()throws Exception{		
		
		
		List<ServiceEntity> serviceList = serviceDAO.selectArrearageTask();
		if(serviceList.isEmpty()){
			return ;
		}
		
		for(ServiceEntity se:serviceList){
			
			Integer feeType=se.getServiceType();
			UserInfo user = userDAO.selectUserById(se.getUserId());
			
			String errorMsg = "系统通知！";
			if(feeType == 01){
				errorMsg = homeDAO.selectCommonConfig("sysMsg01").get("value1").toString();
				
			}else if(feeType == 02){
				errorMsg = homeDAO.selectCommonConfig("sysMsg02").get("value1").toString();
				
			}else if(feeType == 03){
				errorMsg = homeDAO.selectCommonConfig("sysMsg03").get("value1").toString();
			}else{
				continue;
			}
			if(user.getClientTag() == 0) {//安卓终端
				new UserANDROIDPush().pushtoSingle(user.getCid(),errorMsg);
			} else if(user.getClientTag() == 1) { // 1 是ios终端
				new UserIOSPush().pushtoPhoto(user.getCid(),errorMsg,se.getUserId().toString());
			}else{
				throw new Exception();
			}
			
		}
		
	}
	
}
