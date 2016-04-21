package nj.api.ac;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nj.api.bs.BgAdminBS;
import nj.api.bs.InterestClassBS;
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
@RequestMapping("api")
public class InterestClassAC {

	
	final Log logger = LogFactory.getLog(InterestClassAC.class);
	@Autowired
	private InterestClassBS icBS;
	
	@Autowired
	private BgAdminBS adminBS;
	
	/**
	 * 兴趣班列表查询
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/user/getEnjoyClassInfo", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getInterestList(HttpServletRequest request,
			@RequestParam String userId)throws Exception {
		//客户端传递标识0 android，1 ios
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		String token = (String) request.getHeader(SysUtil.TOKEN);

		return icBS.getInterestList(userId, token);
	}	
	
	/**
	 * 兴趣班详情
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/user/getInterestDetail", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getInterestDetail(HttpServletRequest request,
			@RequestParam String userId,@RequestParam String terId)throws Exception {
		//客户端传递标识0 android，1 ios
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		String token = (String) request.getHeader(SysUtil.TOKEN);

		return icBS.getInterestDetail(userId,terId,token);
	}
	
	/**
	 * 兴趣班列表
	 * @param request
	 * @param adminId
	 * @param interestName
	 * @param cursor
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/interestList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> interestList(HttpServletRequest request,
			@RequestParam String adminId,
			@RequestParam(required = false, defaultValue = "") String interestName,
			@RequestParam(required = false, defaultValue = "0") int cursor,
			@RequestParam(required = false, defaultValue = "0") int offset)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return icBS.getBgInterestList(interestName,cursor,offset);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	}
	
	/**
	 * 兴趣班管理
	 * @param request
	 * @param adminId
	 * @param interestEntity
	 * @param opType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/interestMge", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> interestMge(HttpServletRequest request,
			@RequestParam String adminId,@RequestParam String interestEntity,
			@RequestParam String opType)throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return icBS.interestMge(interestEntity, opType);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	}
	
}
