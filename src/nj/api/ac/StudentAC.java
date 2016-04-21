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

import nj.api.bs.BgAdminBS;
import nj.api.bs.StudentBS;
import nj.api.bs.UserBS;
import nj.common.Constants;
import nj.common.utils.SysUtil;

@Controller
@RequestMapping("/api")
public class StudentAC {

	
	final Log logger = LogFactory.getLog(StudentAC.class);
	
	@Autowired
	private StudentBS stuBS;
	
	@Autowired
	private BgAdminBS adminBS;
	

	@RequestMapping(value = "/admin/studentList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getStuList(HttpServletRequest request,
			@RequestParam String adminId,
			@RequestParam(required = false, defaultValue = "0") long stuId,
			@RequestParam(required = false, defaultValue = "0") int cursor,
			@RequestParam(required = false, defaultValue = "0") int offset)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return stuBS.getstuList(stuId,cursor,offset);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	}
	
	
	
	@RequestMapping(value = "/admin/studentMge", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> studentMge(HttpServletRequest request,
			@RequestParam String adminId,@RequestParam String studentEntity,
			@RequestParam String opType)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return stuBS.stuMge(studentEntity,opType);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	}
	
	@RequestMapping(value = "/admin/inkey", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getInKey(HttpServletRequest request,
			@RequestParam String adminId,@RequestParam String studentId)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return stuBS.getInKey(studentId);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	}
	
	@RequestMapping(value = "/admin/userInfoList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getUserList(HttpServletRequest request,
			@RequestParam String adminId,@RequestParam String stuId)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return stuBS.getUserInfo(stuId);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}

	}
	
}
