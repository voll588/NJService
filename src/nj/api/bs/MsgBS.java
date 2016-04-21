package nj.api.bs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import nj.api.dao.PushDAO;

import nj.common.Constants;



@Service
public class MsgBS {
	
	@Autowired
	private PushDAO pushDAO;
	
	/**
	 * 获取消息列表
	 * @param adminId
	 * @param cursor
	 * @param offset
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getMsgList(int cursor,int offset)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();

		Map<String,Object> pageMap = new HashMap<String,Object>();
		pageMap.put("cursor", cursor);
		pageMap.put("offset", offset);
		
		List<Map<String,Object>> msgList = pushDAO.getMsgList(pageMap);
		
		Long count = pushDAO.MsgListCount();
	
		resMap.put("list", msgList);
		resMap.put("count", count);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
	}
	

}
