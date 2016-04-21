package nj.api.entity;

import java.util.Date;

public class MsgEntity {

	private Long noticeId;
	
	private String noticeName;
	
	private String noticeSub;
	
	private String noticeText;
	
	private Date noticeTime;
	
	private String noticeType;
	
	private Long classId;
	
	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeName() {
		return noticeName;
	}

	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}

	public String getNoticeSub() {
		return noticeSub;
	}

	public void setNoticeSub(String noticeSub) {
		this.noticeSub = noticeSub;
	}

	public String getNoticeText() {
		return noticeText;
	}

	public void setNoticeText(String noticeText) {
		this.noticeText = noticeText;
	}

	public Date getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	
}
