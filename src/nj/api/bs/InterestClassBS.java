package nj.api.bs;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import nj.api.dao.InterestClassDAO;
import nj.api.dao.PayOrderDAO;

import nj.api.dao.StuDAO;
import nj.api.dao.UserDAO;

import nj.api.entity.InterestClassEntity;
import nj.api.entity.OrderEntity;
import nj.api.entity.StuInfo;
import nj.api.entity.UserInfo;
import nj.common.Constants;
import nj.common.utils.APIUtil;
import nj.common.utils.DateUtil;
import nj.common.utils.StringUtils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


@Service
public class InterestClassBS {

	
	@Autowired
	private InterestClassDAO icDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private StuDAO stuDAO;
	
	@Autowired
	private PayOrderDAO poDAO;
	
	
	/**
	 * 获取兴趣班列表
	 * @param userId
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getInterestList(String userId,String token)throws Exception{
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
		
		StuInfo student = stuDAO.selectStuByUserId(userid);
		
		//查询已经参加的兴趣班信息
		List<InterestClassEntity> joinList = icDAO.getJoinList(student.getStuId());
		//查询兴趣班信息
		List<InterestClassEntity> icList = icDAO.getInterestList();
		List<InterestClassEntity> tempList = new ArrayList<InterestClassEntity>();
		
		if(joinList.isEmpty()){
			resMap.put("interestClassList", icList);	
		}else{
			for(InterestClassEntity ice:icList){
				boolean flag = true;
				for(InterestClassEntity jice:joinList){
					if(ice.getTerId()==jice.getTerId()){
						flag = false;
					}
				}
				if(flag)tempList.add(ice);
			}
			resMap.put("joinClassList", joinList);
			resMap.put("interestClassList", tempList);
		}
		
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
		
	}
	
	public Map<String,Object> getInterestDetail(String userId,String terId,String token)throws Exception {
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
		StuInfo student = stuDAO.selectStuByUserId(userid);
		
		InterestClassEntity interClass = icDAO.getInterestClass(Long.parseLong(terId));
				
		resMap.put("terId", interClass.getTerId());
		resMap.put("terFee", interClass.getTerFee());
		resMap.put("introduction", interClass.getTerDesc());
		resMap.put("serviceId", null);
		resMap.put("psId", interClass.getPsId());
		resMap.put("terNum", interClass.getTerNum());
		resMap.put("terCount", interClass.getTerCount());
		resMap.put("discount","1");
		
		//状态检查
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", userid);
		paraMap.put("terId", interClass.getTerId());
		paraMap.put("stuId", student.getStuId());
		
		Map<String,Object> icMap = icDAO.getInterestState(paraMap);
		
		if(icMap != null){
			//已订购
			resMap.put("interState", 2);
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}
		//判断是否已经支付
		OrderEntity oe = poDAO.selectPayingOrder(paraMap);		
		if(oe==null){
			//可订购
			resMap.put("interState", 1);
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}else{
			resMap.put("code", Constants.APICODE_ERROR);
			return resMap;
		}
	}
	
	/**
	 * 后台兴趣班查询
	 * @param adminId
	 * @param interestName
	 * @param cursor
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getBgInterestList(String interestName,int cursor,int offset)throws Exception{
		Map<String, Object> resMap = new HashMap<String, Object>();
			
		Map<String,Object> pageMap = new HashMap<String,Object>();
		pageMap.put("cursor", cursor);
		pageMap.put("offset", offset);
		
		List<InterestClassEntity> intClassList =  new ArrayList<InterestClassEntity>();
		
		if(StringUtils.isNull(interestName)){
			pageMap.put("interestName", null);
			intClassList = icDAO.getInterestListByName(pageMap);
			Long count = icDAO.getInterestCount();
			resMap.put("count", count);
		}else{
			pageMap.put("interestName", interestName);
			intClassList = icDAO.getInterestListByName(pageMap);
			resMap.put("count", 1);
		}

		resMap.put("list", intClassList);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
	}
	
	
	
	public Map<String,Object> interestMge(String interestEntity,String opType)throws Exception{
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Map<String,Object> intClassMap = gson.fromJson(interestEntity, new TypeToken<Map<String, Object>>() {}.getType());	
		InterestClassEntity intClassEn = new InterestClassEntity();
		intClassEn.setTeacherId(Float.valueOf(intClassMap.get("teacherId").toString()).longValue());
		intClassEn.setPsId(Float.valueOf(intClassMap.get("psId").toString()).longValue());
		intClassEn.setTerName(intClassMap.get("interestName").toString());
		intClassEn.setTerDesc(intClassMap.get("interestDesc").toString());
		intClassEn.setTerCount(Float.valueOf(intClassMap.get("interestCount").toString()).intValue());
		
			
		if("add".equals(opType)){
			intClassEn.setTerThumb(intClassMap.get("interestPic").toString());
			intClassEn.setTerTime(DateUtil.strToDate(intClassMap.get("interestTime").toString()));
			intClassEn.setTerState(1);
			//intClassEn.setTerTime(DateUtil.strToDate(intClassEn.getTerTime()));
			
			icDAO.addInterestClass(intClassEn);
			
			
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}
		
		if("del".equals(opType)){
			intClassEn.setTerId(Float.valueOf(intClassMap.get("interestId").toString()).longValue());

			icDAO.delInterestClass(intClassEn.getTerId());
			
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}
		
		if("update".equals(opType)){
			intClassEn.setTerId(Float.valueOf(intClassMap.get("interestId").toString()).longValue());
			intClassEn.setTerThumb(intClassMap.get("interestPic").toString());
			icDAO.updateInterClassInfo(intClassEn);
			
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}
		
	
		resMap.put("code", Constants.APICODE_ERROR);
		return resMap;
	}
	
	
	
	
}
