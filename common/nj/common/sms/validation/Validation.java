package nj.common.sms.validation;


import org.apache.commons.lang.StringUtils;
import nj.common.sms.enums.ZHSmsError;
import nj.common.sms.model.Result;
import nj.common.sms.model.SmsMessage;
import nj.common.sms.utils.Constants;
import nj.common.sms.utils.PropertyUtils;

public class Validation {
	public static Result checkSmsMsg(SmsMessage smsMsg){
		PropertyUtils prop = null;
		try {
			prop = PropertyUtils.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.isEmpty(smsMsg.getContent()) || smsMsg.getContent().length()>Integer.valueOf(prop.getString(Constants.SMS_CONTENT_SIZE))){
			return new Result(ZHSmsError.USER_DATA_CONTENT_ERROR.getCode(),ZHSmsError.USER_DATA_CONTENT_ERROR.getMessage());
		}
		if(StringUtils.isEmpty(smsMsg.getReceiver())){
			return new Result(ZHSmsError.USER_DATA_RECEIVE_ERROR.getCode(),ZHSmsError.USER_DATA_RECEIVE_ERROR.getMessage());
		}
		return null;
	}
}
