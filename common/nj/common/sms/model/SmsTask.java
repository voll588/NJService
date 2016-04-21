package nj.common.sms.model;

public class SmsTask {
	String taskId;//任务id
	String userid;
	SmsMessage smsMsg;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public SmsMessage getSmsMsg() {
		return smsMsg;
	}
	public void setSmsMsg(SmsMessage smsMsg) {
		this.smsMsg = smsMsg;
	}
	
	
}
