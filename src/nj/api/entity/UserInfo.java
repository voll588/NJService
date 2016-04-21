package nj.api.entity;

import java.util.Date;

public class UserInfo {
	
	//账号id
	private Long userId;
	
	//学生id
	private Long stuId;
   
	//注册账号（手机）
	private String phone;
	
	//注册密码
	private String password;
	
	//学生名称
	private String nickName;
	
	//注册时间
	private Date regTime;
	
	//是否是主账号0不是，1是
	private Integer isAccount;
	
	//用户token
	private String uToken;
	
	//用户状态
	private Integer uState;
	
	//邀请码
	private Long inKey;
	
	//客户端标识
	private Integer clientTag;
	
	//备注说明信息
	private String remark;
	
	//推送cid
	private String cid;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Long getStuId() {
		return stuId;
	}

	public void setStuId(Long stuId) {
		this.stuId = stuId;
	}

	public Long getInKey() {
		return inKey;
	}

	public void setInKey(Long inKey) {
		this.inKey = inKey;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public Integer getIsAccount() {
		return isAccount;
	}

	public void setIsAccount(Integer isAccount) {
		this.isAccount = isAccount;
	}

	public Integer getClientTag() {
		return clientTag;
	}

	public void setClientTag(Integer clientTag) {
		this.clientTag = clientTag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getuToken() {
		return uToken;
	}

	public void setuToken(String uToken) {
		this.uToken = uToken;
	}

	public Integer getuState() {
		return uState;
	}

	public void setuState(Integer uState) {
		this.uState = uState;
	}

	

	
}