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
import nj.api.bs.ClassBS;
import nj.api.bs.StudentBS;
import nj.common.Constants;
import nj.common.utils.SysUtil;

@Controller
@RequestMapping("/api")
public class ClassAC {

	final Log logger = LogFactory.getLog(ClassAC.class);
	
	@Autowired
	private ClassBS classBS;
	
	@Autowired
	private BgAdminBS adminBS;
	
	
    /**
     * 查询班级列表
     * @param request
     * @param adminId
     * @param className
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/admin/classList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getClassList(HttpServletRequest request,
			@RequestParam String adminId,
			@RequestParam(required = false, defaultValue = "") String className,
			@RequestParam(required = false, defaultValue = "0") int cursor,
			@RequestParam(required = false, defaultValue = "0") int offset)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return classBS.getClassList(className,cursor,offset);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	}
	
	
	
	@RequestMapping(value = "/admin/classMge", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> classMge(HttpServletRequest request,
			@RequestParam String adminId,@RequestParam String classEntity,
			@RequestParam String opType)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return classBS.classMge(classEntity,opType);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
		
	}	
	
}
