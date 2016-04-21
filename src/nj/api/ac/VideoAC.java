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
import nj.api.bs.VideoBS;
import nj.api.dao.BgAdminDAO;
import nj.common.Constants;
import nj.common.utils.SysUtil;

@Controller
@RequestMapping("/api")
public class VideoAC {

	final Log logger = LogFactory.getLog(VideoAC.class);
	
	@Autowired
	public VideoBS videoBS;
	
	@Autowired
	private BgAdminBS adminBS;
	
	
	
	/**
	 * 直播列表
	 * @return
	 */
	@RequestMapping(value = "/user/videoList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getVideoList(HttpServletRequest request,
			@RequestParam String userId) throws Exception{
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		String token = (String) request.getHeader(SysUtil.TOKEN);
		return videoBS.getVideoList(userId,token);
	}
	
	
	/**
	 * 后台直播查询
	 * @return
	 */
	@RequestMapping(value = "/admin/videoList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getLiveVideoList(HttpServletRequest request,
			@RequestParam String adminId,
			@RequestParam(required = false, defaultValue = "") String videoId,
			@RequestParam(required = false, defaultValue = "0") int cursor,
			@RequestParam(required = false, defaultValue = "0") int offset) throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return videoBS.getLiveVideoList(videoId,cursor,offset);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}	
	}
	
	
	@RequestMapping(value = "/admin/videoMge", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> videoMge(HttpServletRequest request,
			@RequestParam String adminId,@RequestParam String videoEntity,
			@RequestParam String opType) throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();
		String token = (String) request.getHeader(SysUtil.TOKEN);
		if(adminBS.checkAdmin(adminId, token)){
			return videoBS.videoMge(videoEntity,opType);
		}else{
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
	
	}
	
	
}
