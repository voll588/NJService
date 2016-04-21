package nj.api.entity;

import java.util.Date;

public class AdminEntity {

	//管理员id
	private Long adminId;
	
	//管理员登陆名
	private String adminUserName;
	
	//管理员名称
	private String adminName;
   
	//管理员密码
	private String adminPassword;
	
	//角色id
	private Long adminRoleId;
	
	//添加时间
	private Date adminTime;
	
	//token
	private String adminToken;
	
	//token
	private String adminState;

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
	
	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public Long getAdminRoleId() {
		return adminRoleId;
	}

	public void setAdminRoleId(Long adminRoleId) {
		this.adminRoleId = adminRoleId;
	}

	public Date getAdminTime() {
		return adminTime;
	}

	public void setAdminTime(Date adminTime) {
		this.adminTime = adminTime;
	}

	public String getAdminToken() {
		return adminToken;
	}

	public void setAdminToken(String adminToken) {
		this.adminToken = adminToken;
	}

	public String getAdminState() {
		return adminState;
	}

	public void setAdminState(String adminState) {
		this.adminState = adminState;
	}
	
	
	
}
