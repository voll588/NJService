package nj.api.ac;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nj.api.bs.BgAdminBS;
import nj.api.bs.UserBS;
import nj.common.Constants;
import nj.common.utils.SysUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@RequestMapping("api")
public class BgAdminAC {
	
	final Log logger = LogFactory.getLog(BgAdminAC.class);
	
	@Autowired
	private BgAdminBS adminBS;
	
	/**
	 * 管理员登录
	 * author liangpj
	 * name 注册电话
	 * password 密码
	 * @return
	 */
	@RequestMapping(value = "/admin/login", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> adminlogin(HttpServletRequest request,
			@RequestParam String userName,@RequestParam String password)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap = adminBS.login(userName,password);
		return resMap;
	}
	
	/**
	 * 首页信息查看
	 * author liangpj
	 * name 注册电话
	 * password 密码
	 * @return
	 */
	@RequestMapping(value = "/admin/landingInfo", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getHomeInfo(HttpServletRequest request,
			@RequestParam String adminId)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		String address = request.getLocalAddr();
		if(adminBS.checkAdmin(adminId, token)){
			return adminBS.getHomeInfo(adminId,address,token);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	}
	
	/**
	 * 管理员信息列表
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/admin/adminList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getAdminList(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int cursor,
			@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = "") String adminId)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return adminBS.getAdminList(adminId,cursor,offset);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	}
	
	/**
	 * 管理员管理
	 * author liangpj
	 * name 注册电话
	 * password 密码
	 * @return
	 */
	@RequestMapping(value = "/admin/adminMge", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> editorAdmin(HttpServletRequest request,
			@RequestParam String adminId,@RequestParam String adminEntity,
			@RequestParam String opType)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return adminBS.editorAdmin(adminId,adminEntity,opType);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	}
	
	/**
	 * 管理员退出
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/admin/logout", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> loginout(HttpServletRequest request,
			@RequestParam String adminId)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return adminBS.loginout(adminId,token);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	}

}
