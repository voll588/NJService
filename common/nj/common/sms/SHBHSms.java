package nj.common.sms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import nj.common.sms.enums.ZHSmsError;
import nj.common.sms.except.SmsException;
import nj.common.sms.model.Result;
import nj.common.sms.utils.Constants;

public class SHBHSms extends BaseSms {

	private String send_api ="%s/mt/";
	private String report_api ="%s/st/";
	private String balance_api = "%s/mm/";
	private String delive_api = "%s/rx/";
	
	@Override
	protected HttpPost postSendAPI(String phones, String content,
			String extend, Date sendTime) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		HttpPost httpost = getHttpPostWithAuth(String.format(send_api,prop.getString(Constants.SMS_API_HOST)),nvps);
		//短信内容，最大700字
		nvps.add(new BasicNameValuePair("content", content));
		//手机号码(多个号码用”,”分隔)，最多1000个号码
		nvps.add(new BasicNameValuePair("mobile", phones));
		//接入号扩展号
		nvps.add(new BasicNameValuePair("mid", StringUtils.isEmpty(extend)? "":extend));
		//预约发送时间，格式:yyyyMMddhhmmss,如‘20090901010101’，立即发送请填空
		nvps.add(new BasicNameValuePair("SendTime",sendTime==null? "":DateUtils.formatDate(sendTime,prop.getString(Constants.SMS_TIME_FORMAT))));
		//httpost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName(prop.getString(Constants.SMS_CHARSET))));
		httpost.setEntity(encodeEntity(nvps));
		return httpost;
	}

	@Override
	protected HttpPost postBalanceAPI() {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		HttpPost httpost = getHttpPostWithAuth(String.format(balance_api,prop.getString(Constants.SMS_API_HOST)),nvps);
		//httpost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName(prop.getString(Constants.SMS_CHARSET))));
		httpost.setEntity(encodeEntity(nvps));
		return httpost;
	}

	@Override
	protected HttpPost postReportAPI() {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		HttpPost httpost = getHttpPostWithAuth(String.format(report_api,prop.getString(Constants.SMS_API_HOST)),nvps);
		//httpost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName(Constants.SMS_CHARSET)));
		httpost.setEntity(encodeEntity(nvps));
		return httpost;
	}

	@Override
	protected HttpPost postDeliverAPI() {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		HttpPost httpost = getHttpPostWithAuth(String.format(delive_api,prop.getString(Constants.SMS_API_HOST)),nvps);
		//httpost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName(prop.getString(Constants.SMS_CHARSET))));
		httpost.setEntity(encodeEntity(nvps));
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
			/*15829778157,100,158297781578692105,0
			15829778157,100,158297781571971640,0
			15829778157,100,158297781571643503,0*/
			System.out.println(contents);
			
			if(StringUtils.equals(contents, "107")){
				System.out.println("取短信状态频率过快，请隔三秒后再试！");
				return new Result(ZHSmsError.USER_COMP_OK.getCode(),"取短信状态频率过快，请隔三秒后再试！");
			}
			/*String [] cells = contents.split("&");
			String code =cells[0].split("=")[1];
			if (StringUtils.equals(code,"0")) {
				return new Result(ZHSmsError.USER_COMP_OK.getCode(),cells[1]);
			}
			if(Integer.valueOf(code)>0){
				return new Result(ZHSmsError.USER_COMP_ERROR.getCode(),cells[1].split("=")[1]);
			}*/
			return new Result(ZHSmsError.USER_COMP_OK.getCode(),contents);
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

	//----private
	private HttpPost getHttpPostWithAuth(String api_url, List<NameValuePair> nvps) {
		HttpPost httpost = new HttpPost(api_url);
		//企业编码
		//用户名称
		nvps.add(new BasicNameValuePair("uid", prop.getString(Constants.SMS_ACCOUNT)));
		//登录密码
		nvps.add(new BasicNameValuePair("pwd", prop.getString(Constants.SMS_PASSWORD)));
		return httpost;
	}
	
	private Result parseHttpEntity(HttpEntity entity, int key) {
		String contents="";
		try {
			contents = EntityUtils.toString(entity,prop.getString(Constants.SMS_CHARSET));
			System.out.println(contents);
			String [] cells = contents.split("&");
			String code =cells[1].split("=")[1];
			
			if (StringUtils.equals(code,"100")) {
				return parseResult4OK(cells,key);
			}
			if(Integer.valueOf(code)>100){
				return new Result(ZHSmsError.USER_COMP_ERROR.getCode(),cells[2].split("=")[1]);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Result(ZHSmsError.COMP_PARSE_RESULT_ERROR.getCode(),ZHSmsError.COMP_PARSE_RESULT_ERROR.getMessage());
	
	}
	
	private Result parseResult4OK(String[] cells, int key) {
		switch (key) {
		case 0:
			return new Result(ZHSmsError.USER_COMP_OK.getCode(),cells[2].split("=")[1]);
		case 1:
			return new Result(ZHSmsError.USER_COMP_OK.getCode(),cells[1].split("=")[1]);
		default:
			return new Result(ZHSmsError.USER_COMP_OK.getCode(),cells[1].split("=")[1]);
		}
	}
	
	private HttpEntity encodeEntity(List<NameValuePair> nvps){
		try {
			return new UrlEncodedFormEntity(nvps, prop.getString(Constants.SMS_CHARSET));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
