package nj.api.bs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import nj.api.dao.UserDAO;
import nj.api.dao.VideoDAO;
import nj.api.entity.UserInfo;
import nj.api.entity.VideoEntity;
import nj.common.Constants;
import nj.common.utils.APIUtil;
import nj.common.utils.StringUtils;

@Service
public class VideoBS {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private VideoDAO videoDAO;
	
	
	public Map<String,Object> getVideoList(String userId,String token)throws Exception{
		
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
		
		List<Map<String,Object>> vlist = videoDAO.getVideoList();
		
		
		resMap.put("list", vlist);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
		
	}
	
	/**
	 * 获取直播列表
	 * @param adminId
	 * @param cursor
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getLiveVideoList(String videoId,int cursor,int offset)throws Exception{
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("cursor", cursor);
		paraMap.put("offset", offset);
		
		List<Map<String,Object>> vlist = new ArrayList<Map<String,Object>>();
		
		if(StringUtils.isNull(videoId)){
			paraMap.put("videoId", null);
			vlist = videoDAO.getLiveVideoList(paraMap);
			Long vCount = videoDAO.getLiveVideoListCount();
			resMap.put("count", vCount);
		}else{
			paraMap.put("videoId", videoId);
			vlist = videoDAO.getLiveVideoList(paraMap);
			resMap.put("count", 1);
		}
		resMap.put("list", vlist);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
		
	}
	
	/**
	 * 直播设备管理
	 * @param adminId
	 * @param videoEntity
	 * @param opType
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> videoMge(String videoEntity,String opType)throws Exception{
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		VideoEntity videoEn = gson.fromJson(videoEntity, VideoEntity.class);
		
		//新增设备
		
		if("add".equals(opType)){
			videoEn.setVideoState(1);

			videoDAO.addLiveVideo(videoEn);
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}

		//修改设备
		
		if("update".equals(opType)){	
			videoDAO.updateLiveVideo(videoEn);	
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}
		
		//删除设备
		
		if("del".equals(opType)){
			
			videoDAO.delLiveVideo(videoEn.getVideoId());
			
			resMap.put("code", Constants.APICODE_OK);
			return resMap;	
		}

		resMap.put("code", Constants.APICODE_ERROR);
		return resMap;
		
	}
	
}
