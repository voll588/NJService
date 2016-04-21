package nj.api.ac;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nj.api.bs.BgAdminBS;
import nj.api.bs.PayBS;
import nj.api.bs.ServiceBS;
import nj.common.Constants;
import nj.common.utils.SysUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api")
public class ServiceAC {

	final Log logger = LogFactory.getLog(ServiceAC.class);
	@Autowired
	private ServiceBS serviceBS;
	
	@Autowired
	private BgAdminBS adminBS;
	
	/**
	 * 查询缴费信息
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/user/getFeeInfo", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getBugInfo(HttpServletRequest request,
			@RequestParam String userId,@RequestParam String type)throws Exception {
		//客户端传递标识0 android，1 ios
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		String token = (String) request.getHeader(SysUtil.TOKEN);
		return serviceBS.getBugInfo(userId, type,token);
	}
	
	/**
	 * 查询缴费信息
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/admin/payInfoList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getFeeList(HttpServletRequest request,
			@RequestParam String adminId,
			@RequestParam(required = false, defaultValue = "") String studentId,
			@RequestParam(required = false, defaultValue = "0") int cursor,
			@RequestParam(required = false, defaultValue = "0") int offset)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return serviceBS.getFeeList(studentId,cursor,offset);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	}
	
	
}
