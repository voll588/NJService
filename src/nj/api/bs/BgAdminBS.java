package nj.api.bs;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nj.api.dao.BgAdminDAO;
import nj.api.entity.AdminEntity;
import nj.common.Constants;
import nj.common.utils.APIUtil;
import nj.common.utils.StringUtils;
import nj.common.utils.SysUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class BgAdminBS {
	
	@Autowired
	private BgAdminDAO adminDAO;

	
	/**
	 * 管理员登录
	 * author liangpj
	 * name 名称
	 * password 密码
	 * @return
	 */
	public Map<String,Object> login(String name,String password)throws Exception{
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		Map<String,Object> paramap = new HashMap<String,Object>();
		
		paramap.put("name", name);
		paramap.put("password", password);
		
		AdminEntity admin = adminDAO.selectAdmin(paramap);
		
		if(admin==null){
			resMap.put("code", Constants.APICODE_LOGIN_ERROR);
			return resMap;
		}
		Long roleId = admin.getAdminRoleId();
		List<Map<String,Object>> rolelist = adminDAO.selectAdminRole(roleId);
		
		resMap.put("adminId", StringUtils.base64Encode("" + admin.getAdminId()));
		resMap.put("nickName", admin.getAdminName());
		resMap.put("roleId", admin.getAdminRoleId());
		
		resMap.put("roleList", rolelist);
		
		//生成token
		resMap.put("token", SysUtil.initMacKey());
		adminDAO.insertUserToekn(admin.getAdminId(), String.valueOf(resMap.get("token")));
		resMap.put("code", 0);
		return resMap;
	}
	
	/**
	 * 首页信息
	 * author liangpj
	 * name 名称
	 * password 密码
	 * @return
	 */
	public Map<String,Object> getHomeInfo(String adminId,String address,
			String token)throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();		
		
		Long stuCount = adminDAO.selectStuCount();
		
		Long userCount = adminDAO.selectUserCount();
		
		Long classCount = adminDAO.selectClassCount();
		
		Long deviceCount = adminDAO.selectDeviceCount();

		String curTime = StringUtils.dateToString(new Date());
		resMap.put("stuCount", stuCount);
		resMap.put("parentCount", userCount);
		resMap.put("classCount", classCount);
		resMap.put("deviceCount", deviceCount);
		resMap.put("sysVersio", "1.0.0");
		resMap.put("sysName", "windows");
		resMap.put("webAddress", address);
		resMap.put("dbVersion", "mySQL5.6.24");
		resMap.put("serverTime", curTime);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
	}
	
	/**
	 * 管理员信息查询
	 * author liangpj
	 * name 名称
	 * password 密码
	 * @return
	 */
	public Map<String,Object> getAdminList(String adminId,int cursor,int offset)throws Exception{
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		Long adid = Long.parseLong(StringUtils.base64Decode(adminId));
		
		//判断是否是超级管理员
		if(checkAdminRole(adid,1)){
			resMap.put("code", Constants.APICODE_ADMIN_ROLE_ERROR);
			return resMap;
		}
		
		Map<String,Object> pageMap = new HashMap<String,Object>();
		pageMap.put("cursor", cursor);
		pageMap.put("offset", offset);
		
		
		List<Map<String,Object>> adminList = adminDAO.selectAdminList(pageMap);
		
		Long count = adminDAO.getAdminCount();
		
		resMap.put("adminList", adminList);
		resMap.put("count", count);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
	}
	
	/**
	 * editor管理员
	 * author liangpj
	 * name 名称
	 * password 密码
	 * @return
	 */
	public Map<String,Object> editorAdmin(String adminId,String adminEntity,
			String opType)throws Exception{
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		Long adid = Long.parseLong(StringUtils.base64Decode(adminId));
		
		if(checkAdminRole(adid,1)){
			resMap.put("code", Constants.APICODE_ADMIN_ROLE_ERROR);
			return resMap;
		}
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		AdminEntity ae = gson.fromJson(adminEntity, AdminEntity.class);

		
		//添加管理员
		if("add".equals(opType)){
			
			ae.setAdminTime(new Date());
			adminDAO.addAdmin(ae);
			
			resMap.put("code", 0);
			return resMap;
		}
		
		//修改管理员
		if("update".equals(opType)){
			
			adminDAO.updateAdmin(ae);
			

			resMap.put("code", 0);
			return resMap;
		}
		
		//删除管理员
		if("del".equals(opType)){
			
			adminDAO.delAdmin(ae.getAdminId());
			

			resMap.put("code", 0);
			return resMap;
		}
		
		
		resMap.put("code", Constants.APICODE_ERROR);
		return resMap;
	}
	
	/**
	 * 管理员退出
	 * author liangpj
	 * @return
	 */
	public Map<String,Object> loginout(String adminId,String token)throws Exception{
		Map<String,Object> paraMap = new HashMap<String,Object>();

		
		Long aId = Long.parseLong(StringUtils.base64Decode(adminId));
		AdminEntity admin = adminDAO.selectAdminById(aId);
		if(admin==null){
			paraMap.put("code", Constants.APICODE_ERROR);
			return paraMap;
		}

		adminDAO.insertUserToekn(admin.getAdminId(),"");//删除token
		paraMap.put("code", 0);
		return paraMap;
	}	
	
	
	
	
	
	/**
	 * 判断管理员角色是否正确
	 * @return
	 */
	public boolean checkAdminRole(Long adminId,Integer roleId)throws Exception{
		
		AdminEntity ae = adminDAO.checkAdmin(adminId,roleId);
		
		if(ae == null){
			return false;
		}
		return true;
	}
	
	/**
	 * 管理员校验
	 * @param adminId
	 * @param token
	 * @return true:有效 false:无效
	 */
	public boolean checkAdmin(String adminId,String token)throws Exception{
		Long adminid = 0l;
		try{
			adminid = Long.parseLong(StringUtils.base64Decode(adminId));
		}catch(Exception e){
			adminId="";
		}
		if(StringUtils.isNull(adminId)){
			return false;
		}
		AdminEntity ae = adminDAO.selectAdminById(adminid);
		if(ae==null){
			return false;
		}
		if(!APIUtil.checkToken(token,ae.getAdminToken())){
			return false;
		}
		return true;
	}
	
}
