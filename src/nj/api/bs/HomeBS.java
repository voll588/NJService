package nj.api.bs;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import nj.api.dao.AdvertisingDAO;
import nj.api.dao.ServiceDAO;
import nj.api.dao.TeacherDAO;

import nj.api.dao.HomeDAO;

import nj.api.dao.UserDAO;
import nj.api.entity.FoodWeekEntity;
import nj.api.entity.ServiceEntity;
import nj.api.entity.UserInfo;

import nj.common.Constants;
import nj.common.utils.APIUtil;
import nj.common.utils.StringUtils;


@Service
public class HomeBS {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private AdvertisingDAO advertisingDAO;
	
	@Autowired
	private TeacherDAO teacherDAO;
	
	@Autowired
	private HomeDAO HomeDAO;
	
	@Autowired
	private ServiceDAO serviceDAO;
	

	
	/**
	 * 幼儿主页
	 * author liangpj
	 * @return
	 */
	public Map<String,Object> homeInfo(String userId,String clienttag,String token)throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();
		
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
		
		
		if(!APIUtil.checkToken(token,olderUser.getuToken())){
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
		
		List<Map<String,Object>> adlist = advertisingDAO.selectAdvertising();
		
		if(adlist.size()>0){
			resMap.put("picUrlList", adlist);
		}
		
		//园区介绍
		Map<String,Object> schoolMap = HomeDAO.selectCommonConfig("school");
		schoolMap.put("schoolUrl", schoolMap.get("value1"));
		schoolMap.put("schoolDesc", schoolMap.get("value2"));
		schoolMap.remove("value1");
		schoolMap.remove("value2");
		resMap.put("school", schoolMap);
				
		//欠费信息
		
		ServiceEntity bse = serviceDAO.selectArrearage(userid, 01);
		
		ServiceEntity cse = serviceDAO.selectArrearage(userid, 02);
		
		if(bse!=null&&cse!=null){
			resMap.put("arrearage", Constants.CLIENT_MSG_ARREARAGE);
		}else if(bse!=null){
			resMap.put("arrearage", Constants.CLIENT_MSG_ARREARAGE_FEE);
		}else if(cse!=null){
			resMap.put("arrearage", Constants.CLIENT_MSG_ARREARAGE_FOOD);
		}
		
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
	}
	
	
	/**
	 * 周餐表
	 * author liangpj
	 * @return
	 */
	public Map<String,Object> getFoodList(String userId,String clienttag,String token)throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();
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
		
		
		Map<String,String> foodMap = HomeDAO.selectFoodList();
		//本周餐表
		resMap.put("foodList", foodMap);
		
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
	}
	
	/**
	 * 老师列表接口
	 * @param userId
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getTeacherList(String userId,String token)throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();
		
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
		
		List<Map<String,Object>> teacherlist = teacherDAO.selectTeacherList();
		
		resMap.put("teacherList", teacherlist);
		
		resMap.put("code", 0);
		return resMap;
		
	}
	
	/**
	 * 周餐表
	 * author liangpj
	 * @return
	 */
	public Map<String,Object> getbgFoodWeek(String foodEntity,String token)throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();		
		if(!StringUtils.isNull(foodEntity)){
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			FoodWeekEntity foodEn = gson.fromJson(foodEntity, FoodWeekEntity.class);
			HomeDAO.updateFoodList(foodEn);
		}
		Map<String,String> foodMap = HomeDAO.selectFoodList();
		//本周餐表
		resMap.put("list", foodMap);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
	}
	
	

//	/**
//	 * 老师列表
//	 * author liangpj
//	 * @return
//	 */
//	public Map serviceList(String userId,Long cityId,int cursor,int offset)throws Exception{
//		Map map = new HashMap();
//		Long userid = 0l;
//		if(!userId.equals("(null)")&&!userId.equals("")){
//			userid = Long.parseLong(StringUtils.base64Decode(userId));
//			UserInfo user = userDAO.selectUserById(userid);
//			if(user==null){
//				map.put("code", 1);
//				return map;
//			}
//		}
//		Map userMap = new HashMap();
//		userMap.put("areaId", cityId);
//		userMap.put("type", 1);
//		userMap.put("cursor", cursor);
//		userMap.put("offset", offset);
//		List list = userDAO.selectUserByCityId(userMap);
//		Long cou = userDAO.selectUserByCityIdCount(userMap);
//		List serviceList = new ArrayList();
//		for(int i=0;i<list.size();i++){
//			Map serviceMap = new HashMap();
//			UserInfo userInfo = (UserInfo)list.get(i);
//			Map commentMap = homeDAO.findServiceCount(userInfo.getUserId());
//			Double score = 5.0;
//			if(!commentMap.get("counts").toString().equals("0")){
//				String sum = commentMap.get("score").toString();
//				String countAll = commentMap.get("counts").toString();
////				score  = Double.parseDouble(sum)/Double.parseDouble(countAll);
//				double Average = Double.parseDouble(sum+500) / Double.parseDouble(countAll+100);
//				DecimalFormat decimalFormat = new DecimalFormat("#.0");
//				decimalFormat.setRoundingMode(RoundingMode.FLOOR);
//				String Averagescore = decimalFormat.format(Average);
//				score = Double.parseDouble(Averagescore);
//				serviceMap.put("commentNum", commentMap.get("counts").toString());
//			}else{
//				serviceMap.put("commentNum", 0);
//			}
//			serviceMap.put("serviceId", userInfo.getUserId());
//			serviceMap.put("serviceName", userInfo.getNickName());
//			Map specialtyMap = homeDAO.findSpecialty(userInfo.getUserId());
//			Long count = homeDAO.findServiceAtt(userInfo.getUserId());
//			if(userid==0){
//				serviceMap.put("attentionType", 0);
//			}else{
//				Map attentionMap = new HashMap();
//				
//				attentionMap.put("userId", userid);
//				attentionMap.put("serviceId", userInfo.getUserId());
//				Attention attention = themeDAO.selectAttention(attentionMap);
//				if(attention==null){
//					serviceMap.put("attentionType", 0);
//				}else{
//					serviceMap.put("attentionType", attention.getType());
//				}
//			}
//			serviceMap.put("attentionNum", count);
//			serviceMap.put("specialty", specialtyMap.get("specialty").toString());
//			serviceMap.put("backGroundPic", specialtyMap.get("backPic").toString());
//			serviceMap.put("servicePhone", userInfo.getUserId());
//			serviceMap.put("headPic", userInfo.getHeadPic());
//			serviceMap.put("headThumbPic", userInfo.getHeadThumbPic());
//			serviceMap.put("sex", userInfo.getSex());
//			serviceMap.put("country", userInfo.getCountry());
//			serviceMap.put("city", userInfo.getCity());
//			serviceMap.put("remark", userInfo.getRemark());
//			
//			serviceMap.put("score", score);
//			serviceList.add(serviceMap);
//		}
//		map.put("count", cou);
//		map.put("list", serviceList);
//		map.put("code", 0);
//		return map;
//	}
//	
//	/**
//	 * 老师详情
//	 * author liangpj
//	 * @return
//	 */
//	public Map serviceInfo(String userId,String serviceId)throws Exception{
//		Map map = new HashMap();
//		Long userid = 0l;
//		Long serviceid = 0l;
//		try{
//			userid = Long.parseLong(userId);
//		}catch(Exception e){
//			try{
//				userid = Long.parseLong(StringUtils.base64Decode(userId));
//			}catch(Exception x){
//				userid = 0l;
//			}
//		}
//		try{
//			serviceid = Long.parseLong(serviceId);
//		}catch(Exception e){
//			serviceid = Long.parseLong(StringUtils.base64Decode(serviceId));
//		}
//		UserInfo user = userDAO.selectUserById(serviceid);
//		Map commentMap = homeDAO.findServiceCount(serviceid);
//		Double score = 5.0;
//		if(!commentMap.get("counts").toString().equals("0")){
//			String sum = commentMap.get("score").toString();
//			String countAll = commentMap.get("counts").toString();
//			double Average = Double.parseDouble(sum+500) / Double.parseDouble(countAll+100);
//			DecimalFormat decimalFormat = new DecimalFormat("#.0");
//			decimalFormat.setRoundingMode(RoundingMode.FLOOR);
//			String Averagescore = decimalFormat.format(Average);
//			score = Double.parseDouble(Averagescore);
//			map.put("commentNum", commentMap.get("counts").toString());
//		}else{
//			map.put("commentNum", 0);
//		}
//		Map specialtyMap = homeDAO.findSpecialty(serviceid);
//		Long count = homeDAO.findServiceAtt(serviceid);
//		map.put("attentionNum", count);
//		if(specialtyMap==null){
//			map.put("specialty", "");
//		}else{
//			map.put("specialty", specialtyMap.get("specialty").toString());
//		}
//		Attention attention = null;
//		if(userid!=0l){
//			Map attentionMap = new HashMap();
//			attentionMap.put("userId", userid);
//			attentionMap.put("serviceId", serviceid);
//			attention = themeDAO.selectAttention(attentionMap);
//		}
//		if(attention==null){
//			map.put("attentionType", 0);
//		}else{
//			map.put("attentionType", attention.getType());
//		}
//		map.put("headPic", user.getHeadPic());
//		map.put("headThumbPic", user.getHeadThumbPic());
//		map.put("authentication", user.getVip());
//		if(user.getRemark()==null||user.getRemark().equals("")){
//			map.put("remark", "这个家伙很懒，什么都没有留下");
//		}else{
//			map.put("remark", user.getRemark());
//		}
//		map.put("nickName", user.getNickName());
//		map.put("score", score);
//		map.put("code", 0);
//		return map;
//	}

	
}
