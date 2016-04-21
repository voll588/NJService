package nj.api.entity;

import java.util.Date;

public class InterestClassEntity {
	
	private Long terId;
	
    private Long teacherId;
    
    private Long serviceId;
    
    private Long psId;
    
    private String terName;
    
    private String terDesc;
    
    private String terThumb;
    
    private Integer terNum;
    
    private Integer terCount;
    
    private Integer terState;
    
    private Date terTime;
    
    private Long terFee;
    
    private String teacherName;
    
    private String teacherThumb;

	public Long getTerId() {
		return terId;
	}

	public void setTerId(Long terId) {
		this.terId = terId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	
	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getPsId() {
		return psId;
	}

	public void setPsId(Long psId) {
		this.psId = psId;
	}

	public String getTerName() {
		return terName;
	}

	public void setTerName(String terName) {
		this.terName = terName;
	}

	public String getTerDesc() {
		return terDesc;
	}

	public void setTerDesc(String terDesc) {
		this.terDesc = terDesc;
	}

	public Integer getTerNum() {
		return terNum;
	}

	public void setTerNum(Integer terNum) {
		this.terNum = terNum;
	}

	public Integer getTerCount() {
		return terCount;
	}

	public void setTerCount(Integer terCount) {
		this.terCount = terCount;
	}

	public Integer getTerState() {
		return terState;
	}

	public void setTerState(Integer terState) {
		this.terState = terState;
	}

	public Date getTerTime() {
		return terTime;
	}

	public void setTerTime(Date terTime) {
		this.terTime = terTime;
	}

	public String getTerThumb() {
		return terThumb;
	}

	public void setTerThumb(String terThumb) {
		this.terThumb = terThumb;
	}

	public Long getTerFee() {
		return terFee;
	}

	public void setTerFee(Long terFee) {
		this.terFee = terFee;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherThumb() {
		return teacherThumb;
	}

	public void setTeacherThumb(String teacherThumb) {
		this.teacherThumb = teacherThumb;
	}
	
	
}
