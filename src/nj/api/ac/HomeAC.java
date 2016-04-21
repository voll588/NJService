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

import nj.api.ac.HomeAC;
import nj.api.bs.BgAdminBS;
import nj.api.bs.HomeBS;
import nj.common.Constants;
import nj.common.utils.SysUtil;


@Controller
@RequestMapping("/api")
public class HomeAC {
	final Log logger = LogFactory.getLog(HomeAC.class);
	
	@Autowired
	private HomeBS homeBS;

	@Autowired
	private BgAdminBS adminBS;
	
	/**
	 * 首页信息列表
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/user/home", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> homeInfo(HttpServletRequest request,
			@RequestParam String userId) throws Exception{
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		String token = (String) request.getHeader(SysUtil.TOKEN);
		return homeBS.homeInfo(userId,clienttag,token);
	}

	/**
	 * 周套餐列表
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/user/foodList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getFoodList(HttpServletRequest request,
			@RequestParam String userId) throws Exception{
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		String token = (String) request.getHeader(SysUtil.TOKEN);
		return homeBS.getFoodList(userId,clienttag,token);
	}

	
	/**
	 * 老师列表
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/user/teacherList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> cityList(HttpServletRequest request,
			@RequestParam String userId) throws Exception{
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		String token = (String) request.getHeader(SysUtil.TOKEN);
		return homeBS.getTeacherList(userId,token);
	}

	
	
	/**
	 * 后台周套餐列表
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/admin/foodWeek", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getBgFoodList(HttpServletRequest request,
			@RequestParam String adminId,
			@RequestParam(required = false, defaultValue = "") String foodEntity) throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return homeBS.getbgFoodWeek(foodEntity,token);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
		
	}
	
}