package nj.api.entity;

import java.util.Date;

public class ClassEntity {

	//班级id
	private Long classId;
	
	//班级名称
	private String className;
	
	//老师id
	private Long teacherId;
	
	//学生人数
	private Integer classNum;
	
	//创建时间
	private Date classTime;
	
	//班级
	private Integer classState;
	
	//班级描述
	private String classDesc;
	

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getClassNum() {
		return classNum;
	}

	public void setClassNum(Integer classNum) {
		this.classNum = classNum;
	}

	public Date getClassTime() {
		return classTime;
	}

	public void setClassTime(Date classTime) {
		this.classTime = classTime;
	}

	public Integer getClassState() {
		return classState;
	}

	public void setClassState(Integer classState) {
		this.classState = classState;
	}

	public String getClassDesc() {
		return classDesc;
	}

	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}
	
	
}
