package nj.common.sms.model;

import java.util.Date;

public class SmsMessage {
	String content;//短信内容
	String receiver;//接收人号码
	Date timing;//定时发送时间
	String priority;//优先级
	String extend; //扩展码
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	public Date getTiming() {
		return timing;
	}
	public void setTiming(Date timing) {
		this.timing = timing;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	
	
}
