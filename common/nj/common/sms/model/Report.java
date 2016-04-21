package nj.common.sms.model;

import java.util.Date;

public class Report {
	Integer id;
	String receice;
	String stat;
	String msgid;
	Date reporttime;
	public String getReceice() {
		return receice;
	}
	public void setReceice(String receice) {
		this.receice = receice;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public Date getReporttime() {
		return reporttime;
	}
	public void setReporttime(Date reporttime) {
		this.reporttime = reporttime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
