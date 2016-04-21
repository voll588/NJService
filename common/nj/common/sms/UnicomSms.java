package nj.common.sms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nj.common.sms.enums.ZHSmsError;
import nj.common.sms.except.SmsException;
import nj.common.sms.model.Result;
import nj.common.sms.utils.Constants;

class UnicomSms extends BaseSms {
	Logger logger = LoggerFactory.getLogger(UnicomSms.class);
	
	private String send_api = "%s/sms/Api/Send.do";
	private String report_api ="%s/sms/Api/report.do";
	private String balance_api = "%s/sms/Api/SearchNumber.do"; 
	private String delive_api = "%s/sms/Api/reply.do";
	private String delive_confirm_api = "%s/sms/Api/replyConfirm.do";
	
	private int number=0;
	
	@Override
	protected HttpPost postSendAPI(String phones, String content, String extend,Date sendTime) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		HttpPost httpost = getHttpPostWithAuth(String.format(send_api,prop.getString(Constants.SMS_API_HOST)),nvps);
		//短信内容，最大700字
		nvps.add(new BasicNameValuePair("MessageContent", content));
		//手机号码(多个号码用”,”分隔)，最多1000个号码
		nvps.add(new BasicNameValuePair("UserNumber", phones));
		//接入号扩展号
		nvps.add(new BasicNameValuePair("ExtendAccessNum", StringUtils.isEmpty(extend)? "":extend));
		//预约发送时间，格式:yyyyMMddhhmmss,如‘20090901010101’，立即发送请填空
		nvps.add(new BasicNameValuePair("ScheduleTime",sendTime==null? "":DateUtils.formatDate(sendTime,prop.getString(Constants.SMS_TIME_FORMAT))));
		//流水号，20位数字，唯一
		nvps.add(new BasicNameValuePair("SerialNumber", generateSerialNumber()));
		nvps.add(new BasicNameValuePair("f","1"));
		//httpost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName(prop.getString(Constants..SMS_CHARSET)));
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, prop.getString(Constants.SMS_CHARSET)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return httpost;
	}

	@Override
	protected HttpPost postBalanceAPI() {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		HttpPost httpost = getHttpPostWithAuth(String.format(balance_api,prop.getString(Constants.SMS_API_HOST)),nvps);
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, prop.getString(Constants.SMS_CHARSET)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return httpost;
	}

	@Override
	protected HttpPost postReportAPI() {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		HttpPost httpost = getHttpPostWithAuth(String.format(report_api,prop.getString(Constants.SMS_API_HOST)),nvps);
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, prop.getString(Constants.SMS_CHARSET)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return httpost;
	}
	
	@Override
	protected HttpPost postDeliverAPI() {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		HttpPost httpost = getHttpPostWithAuth(String.format(delive_api,prop.getString(Constants.SMS_API_HOST)),nvps);
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, prop.getString(Constants.SMS_CHARSET)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return httpost;
	}

	@Override
	protected Result parseSend(HttpEntity entity) {
		return parseHttpEntity(entity,1);
	}

	@Override
	protected Result parseBalance(HttpEntity entity) {
		return parseHttpEntity(entity,0);
	}

	@Override
	protected Result parseReport(HttpEntity entity) throws SmsException {
		String contents="";
		try {
			contents = EntityUtils.toString(entity,prop.getString(Constants.SMS_CHARSET));
			System.out.println(contents);
			String [] cells = contents.split("&");
			String code =cells[0].split("=")[1];
			if (StringUtils.equals(code,"0")) {
				return new Result(ZHSmsError.USER_COMP_OK.getCode(),cells[1]);
			}
			if(Integer.valueOf(code)>0){
				return new Result(ZHSmsError.USER_COMP_ERROR.getCode(),cells[1].split("=")[1]);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new SmsException(ZHSmsError.COMP_PARSE_RESULT_ERROR.getCode()+":"+ZHSmsError.COMP_PARSE_RESULT_ERROR.getMessage());
	}

	@Override
	protected Result parseDeliver(HttpEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void deliver() throws ClientProtocolException, IOException {
		HttpPost httpost = postDeliverAPI();
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();
		logger.debug("deliver-status: "+ response.getStatusLine());
		parseDeliver(entity);
		
		//confirm
		String id="";
		httpost = postDeliverConfirmAPI(id);
		response = httpclient.execute(httpost);
		entity = response.getEntity();
		logger.debug("deliver-status: "+ response.getStatusLine());
		parseDeliverConfirm(entity);
	}
	
	

	private void parseDeliverConfirm(HttpEntity entity) {
		// TODO Auto-generated method stub
		
	}
	
	/**解析请求结果，key代表请求类型
	 * @param entity
	 * @param key
	 * @return
	 */
	private Result parseHttpEntity(HttpEntity entity,int key) {
		String contents="";
		try {
			contents = EntityUtils.toString(entity,prop.getString(Constants.SMS_CHARSET));
			System.out.println(contents);
			String [] cells = contents.split("&");
			String code =cells[0].split("=")[1];
			
			if (StringUtils.equals(code,"0")) {
				return parseResult4OK(cells,key);
			}
			if(Integer.valueOf(code)>0){
				return new Result(ZHSmsError.USER_COMP_ERROR.getCode(),cells[1].split("=")[1]);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Result(ZHSmsError.COMP_PARSE_RESULT_ERROR.getCode(),ZHSmsError.COMP_PARSE_RESULT_ERROR.getMessage());
	}
	
	/**对操作成功返回结果【key=0剩余短信；=1发送短信】
	 * @param cells
	 * @param key
	 * @return
	 */
	private Result parseResult4OK(String []cells,int key){
		
		switch (key) {
		case 0:
			return new Result(ZHSmsError.USER_COMP_OK.getCode(),cells[2].split("=")[1]);
		case 1:
			return new Result(ZHSmsError.USER_COMP_OK.getCode(),cells[1].split("=")[1]);
		default:
			return new Result(ZHSmsError.USER_COMP_OK.getCode(),cells[1].split("=")[1]);
		}
		
	}
	
	/**自动生成流水号
	 * @return
	 */
	private synchronized String generateSerialNumber(){
		if(++number >=100000){
			number=0;
		}
		String rang =("000000"+number);
		return DateUtils.formatDate(new Date(), prop.getString(Constants.SMS_TIME_FORMAT))+rang.substring(rang.length()-6, rang.length());
	}
	
	/**含认证参数【HttpPost】
	 * @param api_url
	 * @param nvps
	 * @return
	 */
	private HttpPost getHttpPostWithAuth(String api_url,List<NameValuePair> nvps){
		HttpPost httpost = new HttpPost(api_url);
		//企业编码
		nvps.add(new BasicNameValuePair("SpCode", prop.getString(Constants.SMS_SP_CODE)));
		//用户名称
		nvps.add(new BasicNameValuePair("LoginName", prop.getString(Constants.SMS_ACCOUNT)));
		//登录密码
		nvps.add(new BasicNameValuePair("Password", prop.getString(Constants.SMS_PASSWORD)));
		return httpost;
	}
	
	/**回复短信确认【HttpPost】
	 * @param id
	 * @return
	 */
	private HttpPost postDeliverConfirmAPI(String id) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		HttpPost httpost = getHttpPostWithAuth(String.format(delive_confirm_api,prop.getString(Constants.SMS_API_HOST)),nvps);
		nvps.add(new BasicNameValuePair("id",id));
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, prop.getString(Constants.SMS_CHARSET)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return httpost;
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException, SmsException{
		SmsComponent client =new UnicomSms();
		Result r=null;
		//r =client.sendSms("180", "你好试。", "", null);
		r=client.balance();
		client.report();
		System.out.println(r.getMessage());
	}
}
