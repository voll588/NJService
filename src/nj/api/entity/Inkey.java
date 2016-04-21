package nj.api.entity;

import java.util.Date;

public class Inkey {

	private Long keyId;
	
	private Long stuId;
	
	private String secret;
	
	private Integer state;
	
	private Date keyTime;
	
	public Long getKeyId() {
		return keyId;
	}

	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}

	public Long getStuId() {
		return stuId;
	}

	public void setStuId(Long stuId) {
		this.stuId = stuId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getKeyTime() {
		return keyTime;
	}

	public void setKeyTime(Date keyTime) {
		this.keyTime = keyTime;
	}
	
	
	
}
