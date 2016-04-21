package nj.api.bs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nj.api.dao.PushDAO;
import nj.api.dao.StuDAO;
import nj.api.dao.UserDAO;

import nj.api.entity.MsgEntity;
import nj.api.entity.StuInfo;
import nj.api.entity.UserInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.push.UserANDROIDPush;
import com.push.UserIOSPush;

import nj.common.Constants;
import nj.common.utils.APIUtil;
import nj.common.utils.StringUtils;
import nj.common.utils.SysUtil;

@Service
public class PushBS {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PushDAO pushDAO;
	
	@Autowired
	private StuDAO stuDAO;

	
	/**
	 * 绑定cid
	 * @param uid
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> bindingCid(String userId, String cid,String token)throws Exception {
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
		
		try {
			Map<String,Object> cidMap = new HashMap<String,Object>();
			cidMap.put("userId", olderUser.getUserId());
			cidMap.put("cid", cid);
			pushDAO.updateCid(cidMap);
			resMap.put(SysUtil.CODE, Constants.APICODE_OK);
		} catch (Exception e) {
			resMap.put(SysUtil.CODE, Constants.APICODE_ERROR);
			e.printStackTrace();
		}
		return resMap;
	}
	
	/**
	 * 消息列表
	 * */
	public Map<String,Object> pushList(String userId,String msgType,int cursor,int offset,String token)throws Exception {
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
		
		Map<String,Object> listMap = new HashMap<String,Object>();
		listMap.put("userId", userid);
		listMap.put("msgType", msgType);
		listMap.put("cursor", cursor);
		listMap.put("offset", offset);
		List<Map<String,Object>> list = pushDAO.pushList(listMap);
		Long count = pushDAO.pushListCount(listMap);
		resMap.put("list", list);
		resMap.put("count", count);
		resMap.put("code", 0);
		return resMap;
	}
	
	/**
	 * 已读消息
	 * @param userId
	 * @param msgId
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> readMsg(String userId,String msgId,String token)throws Exception {
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
		
		pushDAO.updateReadMsg(Long.parseLong(msgId));
		
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
		
	}
	
	/**
	 * 消息推送
	 * @param adminId
	 * @param noticeEntity
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> sendMsg(String noticeEntity)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();		
				
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		MsgEntity msgEntity = gson.fromJson(noticeEntity, MsgEntity.class);
		
		if(msgEntity == null){
			resMap.put("code", Constants.APICODE_ERROR);
			return resMap;
		}
		
		List<UserInfo> userlist = new ArrayList<UserInfo>();
		
		if("1".equals(msgEntity.getNoticeType())){
		//校园通知	
			userlist = userDAO.selectAllUser();
			
			
		}else if("2".equals(msgEntity.getNoticeType())){
		//班级通知
			
			List<StuInfo> stulist = stuDAO.selectStuByClass(msgEntity.getClassId());
			
			for(int i=0;i<stulist.size();i++){
				Long stuId = stulist.get(i).getStuId();
				
				userlist.addAll(userDAO.selectUserByStu(stuId));
			}
		}else{
			resMap.put("code", Constants.APICODE_ERROR);
			return resMap;
		}
		
		Long pushId = commonMsg(msgEntity);
		
		for(int i=0;i<userlist.size();i++){
			UserInfo olderUser = (UserInfo)userlist.get(i);
			insertMsgLog(olderUser.getUserId(),pushId);
			if(StringUtils.isNull(StringUtils.nullToBlank(olderUser.getCid()))){
				continue;
			}
			
			if(olderUser.getClientTag() == 0) {//安卓终端
				new UserANDROIDPush().pushtoSingle(olderUser.getCid(),String.valueOf(msgEntity.getNoticeSub()));
			} else if(olderUser.getClientTag() == 1) { // 1 是ios终端
				new UserIOSPush().pushtoSingle(olderUser.getCid(),msgEntity.getNoticeSub(),"notice");
			}else{
				throw new Exception();
			}
		}	
		resMap.put("code", 0);
		return resMap;
	}
	
	/**
	 * 保存消息内容
	 * @param msgEntity
	 * @return
	 * @throws Exception
	 */
	private Long commonMsg(MsgEntity msgEntity)throws Exception{
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("pushId", "");
		paraMap.put("msgName", msgEntity.getNoticeName());
		paraMap.put("msgSub", msgEntity.getNoticeSub());
		paraMap.put("msgContent", msgEntity.getNoticeText());
		paraMap.put("msgType", msgEntity.getNoticeType());
		paraMap.put("msgTime", new Date());
		paraMap.put("classId", msgEntity.getClassId());
		int i = pushDAO.insertMsg(paraMap);
		return Long.parseLong(paraMap.get("pushId").toString());
		
	}
	
	/**
	 * 插入用户消息记录
	 * @param userId
	 * @param pushId
	 * @throws Exception
	 */
	private void insertMsgLog(Long userId,Long pushId)throws Exception{
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("userId", userId);
		paraMap.put("pushId", pushId);
		paraMap.put("mRead", 0);
		paraMap.put("mTime", new Date());
		pushDAO.insertMsgLog(paraMap);
		
	}
	
}
