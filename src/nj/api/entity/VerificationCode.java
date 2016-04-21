package nj.api.entity;

import java.util.Date;

public class VerificationCode {
	
	//手机号码
	private String phone;
	
	//手机验证码
	private String code;
	
	//验证码时间
	private Date time;
	
	//验证码类型
	private String type;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}