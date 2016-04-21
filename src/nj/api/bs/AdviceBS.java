package nj.api.bs;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nj.api.dao.AdviceDAO;
import nj.api.dao.BgAdminDAO;
import nj.api.dao.UserDAO;
import nj.api.entity.AdminEntity;
import nj.api.entity.UserInfo;

import nj.common.Constants;
import nj.common.utils.APIUtil;
import nj.common.utils.StringUtils;

@Service
public class AdviceBS {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private AdviceDAO adviceDAO;
	
	/**
	 * 提交建议信息
	 * @param content
	 * @param userId
	 * @param clienttag
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> advice(String content,String userId,String clienttag,String token)throws Exception{
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
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userid);
		map.put("phone", olderUser.getPhone());
		map.put("time", new Date());
		map.put("clientTag", clienttag);
		map.put("content", content);
		adviceDAO.insertAdvice(map);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
	}
	
	/**
	 * 查询建议列表
	 * @param adminId
	 * @param cursor
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> adviceList(int cursor,int offset)throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("cursor", cursor);
		paraMap.put("offset", offset);
		List<Map<String,Object>> adviceList = adviceDAO.getAdviceList(paraMap);
		
		Long adviceCount = adviceDAO.getAdviceCount();
		
		resMap.put("list", adviceList);
		resMap.put("count", adviceCount);
		
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
	}
	
}
