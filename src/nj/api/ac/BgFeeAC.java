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
import nj.api.bs.BgFeeBS;
import nj.common.Constants;
import nj.common.utils.SysUtil;

@Controller
@RequestMapping("api")
public class BgFeeAC {

	final Log logger = LogFactory.getLog(BgFeeAC.class);
	
	@Autowired
	private BgFeeBS feeBS;
	
	@Autowired
	private BgAdminBS adminBS;
	
	@RequestMapping(value = "/admin/feeList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getFeeList(HttpServletRequest request,
			@RequestParam String adminId,
			@RequestParam(required = false, defaultValue = "") String psId,
			@RequestParam(required = false, defaultValue = "0") int cursor,
			@RequestParam(required = false, defaultValue = "0") int offset)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return feeBS.getFeeList(psId,cursor,offset);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	}
	
	@RequestMapping(value = "/admin/feeMge", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> feeMge(HttpServletRequest request,@RequestParam String adminId,
			@RequestParam String psEntity,@RequestParam String opType)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return feeBS.FeeMge(psEntity, opType);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	}
	
	
}
