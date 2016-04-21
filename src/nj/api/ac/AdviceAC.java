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

import nj.api.ac.AdviceAC;
import nj.api.bs.AdviceBS;
import nj.api.bs.BgAdminBS;
import nj.common.Constants;
import nj.common.utils.SysUtil;


@Controller
@RequestMapping("/api")
public class AdviceAC {

	
	final Log logger = LogFactory.getLog(AdviceAC.class);
	
	@Autowired
	public AdviceBS adviceBS;
	
	@Autowired
	private BgAdminBS adminBS;
	
	/**
	 * 意见反馈
	 * @return
	 */
	@RequestMapping(value = "/user/advice", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> advice(HttpServletRequest request,
			@RequestParam String content,
			@RequestParam String userId) throws Exception{
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		String token = (String) request.getHeader(SysUtil.TOKEN);
		return adviceBS.advice(content,userId,clienttag,token);
	}

	/**
	 * 意见反馈list
	 * @return
	 */
	@RequestMapping(value = "/admin/adviceList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request,@RequestParam String adminId,
			@RequestParam(required = false, defaultValue = "0") int cursor,
			@RequestParam(required = false, defaultValue = "0") int offset) throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return adviceBS.adviceList(cursor,offset);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	}
}
