package nj.api.entity;

import java.util.Date;

public class StuInfo {

	//幼儿id
	private Long stuId;
	
	//家长id
	private Long userId;
   
	//幼儿名称
	private String stuName;
	
	//注册密码
	private Long classId;
	
	//入学时间
	private Date cTime;
	
	//结束时间
	private Date endTime;
	
	//学生名称
	private String phone;
	
	//学生状态
	private int stuState;
	
	//学生类型
	private String stuType;
	

	public Long getStuId() {
		return stuId;
	}

	public void setStuId(Long stuId) {
		this.stuId = stuId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStuState() {
		return stuState;
	}

	public void setStuState(int stuState) {
		this.stuState = stuState;
	}

	public String getStuType() {
		return stuType;
	}

	public void setStuType(String stuType) {
		this.stuType = stuType;
	}
	
	
}
