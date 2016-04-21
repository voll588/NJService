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
import nj.api.bs.TeacherBS;
import nj.common.Constants;
import nj.common.utils.SysUtil;

@Controller
@RequestMapping("/api")
public class TeacherAC {

	
	final Log logger = LogFactory.getLog(TeacherAC.class);
	
	
	@Autowired
	private TeacherBS teacherBS;
	
	@Autowired
	private BgAdminBS adminBS;
	
	

	@RequestMapping(value = "/admin/teacherList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getStuList(HttpServletRequest request,
			@RequestParam String adminId,
			@RequestParam(required = false, defaultValue = "") String teacherName,
			@RequestParam(required = false, defaultValue = "0") int cursor,
			@RequestParam(required = false, defaultValue = "0") int offset)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return teacherBS.getTeacherList(teacherName,cursor,offset);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	}
	
	
	
	@RequestMapping(value = "/admin/teacherMge", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> studentMge(HttpServletRequest request,
			@RequestParam String adminId,@RequestParam String teacherEntity,
			@RequestParam String opType)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return teacherBS.TeacherMge(teacherEntity,opType);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}	
	}
	
	
}
