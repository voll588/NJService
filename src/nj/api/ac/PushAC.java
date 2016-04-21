package nj.api.ac;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nj.api.ac.PushAC;
import nj.api.bs.BgAdminBS;
import nj.api.bs.MsgBS;
import nj.api.bs.PushBS;

import nj.common.utils.SysUtil;
import nj.common.Constants;
import nj.common.utils.StringUtils;

@Controller
@RequestMapping("/api")
public class PushAC {
	final Log logger = LogFactory.getLog(PushAC.class);
	
	@Autowired
	private PushBS pushBS;
	
	@Autowired
	private MsgBS msgBS;
	
	@Autowired
	private BgAdminBS adminBS;
	
	/**
	 * 绑定用户推送id
	 * 
	 * @param request
	 * @param uid
	 * @param cid
	 * @return
	 */
	@RequestMapping(value = "/user/push", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> send(HttpServletRequest request,
			@RequestParam String userId, @RequestParam String cid)throws Exception {
		String token = (String) request.getHeader(SysUtil.TOKEN);
		return pushBS.bindingCid(userId, cid,token);
	}
	
	/**
	 * 用户消息列表
	 */
	@RequestMapping(value = "/user/noticeMsg", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> pushList(HttpServletRequest request,
			@RequestParam String userId,
			@RequestParam String msgType,
			@RequestParam(required = false, defaultValue = "0") int cursor,
			@RequestParam(required = false, defaultValue = "0") int offset)throws Exception {
		String token = (String) request.getHeader(SysUtil.TOKEN);
		return pushBS.pushList(userId,msgType,cursor,offset,token);
	}
	
	/**
	 * 已读消息状态修改
	 */
	@RequestMapping(value = "/user/readMsg", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> readMsg(HttpServletRequest request,
			@RequestParam String userId,
			@RequestParam String msgId)throws Exception {
		String token = (String) request.getHeader(SysUtil.TOKEN);
		return pushBS.readMsg(userId,msgId,token);
	}
	
	
	/**
	 * 管理消息列表
	 */
	@RequestMapping(value = "/admin/noticeList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> noticeList(HttpServletRequest request,
			@RequestParam String adminId,
			@RequestParam(required = false, defaultValue = "0") int cursor,
			@RequestParam(required = false, defaultValue = "0") int offset)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return msgBS.getMsgList(cursor,offset);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
		
	}
	
	
	
	/**
	 * 发送消息
	 */
	@RequestMapping(value = "/admin/sendMsg", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> sendMsg(HttpServletRequest request,
			@RequestParam String adminId,@RequestParam String noticeEntity)throws Exception {	
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return pushBS.sendMsg(noticeEntity);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}	
	}
}
