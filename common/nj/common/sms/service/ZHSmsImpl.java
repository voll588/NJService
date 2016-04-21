package nj.common.sms.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;

import nj.common.sms.SmsComponent;
import nj.common.sms.enums.ZHSmsError;
import nj.common.sms.except.SmsException;
import nj.common.sms.model.Deliver;
import nj.common.sms.model.Report;
import nj.common.sms.model.Result;
import nj.common.sms.model.SmsMessage;
import nj.common.sms.model.SmsTask;
import nj.common.sms.validation.Validation;


public class ZHSmsImpl implements ISms {

	SmsComponent  client ;
	
	public Result send(SmsTask smsTask) throws SmsException {
		if(checkIsexist(smsTask.getTaskId())){
			return new Result(ZHSmsError.USER_TASKID_ERROR.getCode(),ZHSmsError.USER_TASKID_ERROR.getMessage());
		}
		Result balance;
		try {
			balance = client.balance();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return new Result(ZHSmsError.COMP_HTTP_ERROR.getCode(),e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return new Result(ZHSmsError.COMP_HTTP_ERROR.getCode(),e.getMessage());
		} 
		if(!StringUtils.equals("0", balance.getCode())){
			return balance;
		}
		
		SmsMessage smsMsg = smsTask.getSmsMsg();
		Validation.checkSmsMsg(smsMsg);
		
		try {
			return client.sendSms(smsMsg.getReceiver(), smsMsg.getContent(),null,null);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return new Result(ZHSmsError.COMP_HTTP_ERROR.getCode(),e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return new Result(ZHSmsError.COMP_HTTP_ERROR.getCode(),e.getMessage());
		}
		
	}


	public Result sned(List<SmsTask> smsTasks) throws SmsException {
		return null;
	}

	public List<Deliver> receive() throws SmsException {
		return null;
	}

	public List<Report> repor(String taskid) throws SmsException {
		return null;
	}

	public int balance() throws SmsException {
		return 0;
	}
	

	private boolean checkIsexist(String taskId) {
		return false;
	}

}
