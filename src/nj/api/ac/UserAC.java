package nj.api.ac;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nj.api.bs.UserBS;
import nj.common.utils.SysUtil;


@Controller
@RequestMapping("api")
public class UserAC {
	
	
	final Log logger = LogFactory.getLog(UserAC.class);
	@Autowired
	private UserBS userBS;
	
	/**
	 * 用户注册
	 * author liangpj
	 * phone 注册电话
	 * password 密码
	 * verCode 手机验证码
	 * secret 邀请码
	 * @return
	 */
	@RequestMapping(value = "/user/register", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> register(HttpServletRequest request,
			@RequestParam String phone,@RequestParam String password,
			@RequestParam String verCode,@RequestParam String secret)throws Exception {
		//客户端传递标识0 android，1 ios
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		return userBS.register(phone,password,verCode,secret,clienttag);
	}
	
	/**
	 * 用户登录
	 * author liangpj
	 * phone 电话
	 * password 密码
	 * @return
	 */
	@RequestMapping(value = "/user/login", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> login(HttpServletRequest request,
			@RequestParam String phone,@RequestParam String password)throws Exception {
		//客户端传递标识0 android，1 ios
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		return userBS.login(phone,password,clienttag);
	}
	
	/**
	 * 用户退出
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/user/logout", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> loginout(HttpServletRequest request,
			@RequestParam String userId)throws Exception {
		//客户端传递标识0 android，1 ios
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		String token = (String) request.getHeader(SysUtil.TOKEN);
		return userBS.loginout(userId,token,clienttag);
	}
	
	/**
	 * 用户获取验证码
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/user/getCode", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getCode(HttpServletRequest request,
			@RequestParam String phone,
			@RequestParam String type)throws Exception {
		//客户端传递标识0 android，1 ios
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		return userBS.getCode(phone,type);
	}
	
	/**
	 * 重置密码
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/user/forgetPwd", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> newPwd(HttpServletRequest request,
			@RequestParam String phone,
			@RequestParam String verCode,
			@RequestParam String newPwd)throws Exception {
		//客户端传递标识0 android，1 ios
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		return userBS.newPwd(phone,verCode,newPwd);
	}
	
	/**
	 * 用户修改密码
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/user/updatePwd", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map updatePwd(HttpServletRequest request,
			@RequestParam String userId,
			@RequestParam String pwd,
			@RequestParam String newPwd)throws Exception {
		//客户端传递标识0 android，1 ios
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		String token = (String) request.getHeader(SysUtil.TOKEN);
		return userBS.updatePwd(userId,pwd,newPwd,token);
	}
	
	public boolean checkToken(String token,String usedToken)throws Exception{
		boolean flag = true;
		if(usedToken==null||"".equals(usedToken)){
			flag = false;
		}
		if(!usedToken.equals(token)){
			flag = false;
		}
		return flag;
		
	}
}
