package nj.api.dao;

import java.util.List;
import java.util.Map;

import nj.api.entity.AdminEntity;

public interface BgAdminDAO {

	
	//根据用户名密码查找管理员
    public AdminEntity selectAdmin(Map<String,Object> map)throws Exception;
    
	//根据管理员id查找管理员
    public AdminEntity selectAdminById(Long adminId)throws Exception;
    
	//检查管理员权限
    public AdminEntity checkAdmin(Long adminId,Integer roleId)throws Exception;
	
    
	//根据角色id查找权限
    public List<Map<String,Object>> selectAdminList(Map<String,Object> pageMap)throws Exception;
    
	//管理员总数
    public Long getAdminCount()throws Exception;
    
	//设置token信息
    public void insertUserToekn(Long adminId,String token)throws Exception;
    
	//根据角色id查找权限
    public void addAdmin(AdminEntity Admin)throws Exception;
    
	//根据角色id查找权限
    public void updateAdmin(AdminEntity Admin)throws Exception;
    
	//根据角色id查找权限
    public void delAdmin(Long adminId)throws Exception;
    
    
	//查找学生总数
    public Long selectStuCount()throws Exception;
	
    
	//查找家长总数
    public Long selectUserCount()throws Exception;
	
	//查找班级总数
    public Long selectClassCount()throws Exception;
	
    
	//查找设备总数
    public Long selectDeviceCount()throws Exception;
    
	//根据角色id查找权限
    public List<Map<String,Object>> selectAdminRole(Long roleId)throws Exception;

	
    
	
}
