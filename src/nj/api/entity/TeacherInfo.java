package nj.api.entity;

import java.util.Date;

public class TeacherInfo {

	private Long teacherId;
	
	private String teacherName;
	
	private String teacherInfo;
	
	private String teacherPic;
	
	private String teacherPhone;
	
	private Integer teacherState;
	
	private Date createTime;
	
	private String teacherVideo;

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherInfo() {
		return teacherInfo;
	}

	public void setTeacherInfo(String teacherInfo) {
		this.teacherInfo = teacherInfo;
	}

	public String getTeacherPic() {
		return teacherPic;
	}

	public void setTeacherPic(String teacherPic) {
		this.teacherPic = teacherPic;
	}

	public String getTeacherPhone() {
		return teacherPhone;
	}

	public void setTeacherPhone(String teacherPhone) {
		this.teacherPhone = teacherPhone;
	}

	public Integer getTeacherState() {
		return teacherState;
	}

	public void setTeacherState(Integer teacherState) {
		this.teacherState = teacherState;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTeacherVideo() {
		return teacherVideo;
	}

	public void setTeacherVideo(String teacherVideo) {
		this.teacherVideo = teacherVideo;
	}

	
}
