package nj.common.sms.service;

import java.util.List;

import nj.common.sms.except.SmsException;
import nj.common.sms.model.Deliver;
import nj.common.sms.model.Report;
import nj.common.sms.model.Result;
import nj.common.sms.model.SmsTask;



public interface ISms {
	//提交短信任务
	public Result send(SmsTask smsTask) throws SmsException;
	public Result sned(List<SmsTask> smsTasks)  throws SmsException;
	//获取回复短信
	public List<Deliver> receive() throws SmsException;
	//获取短信回执
	public List<Report> repor(String taskid) throws SmsException;
	//获取账号余额
	public int balance() throws SmsException;
}
