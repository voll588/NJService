package nj.common.sms;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.cookie.DateParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nj.common.sms.enums.ZHSmsError;
import nj.common.sms.except.SmsException;
import nj.common.sms.model.Result;
import nj.common.sms.model.SmsMessage;
import nj.common.sms.model.SmsTask;
import nj.common.sms.utils.PropertyUtils;

public abstract class BaseSms implements SmsComponent {
	Logger logger = LoggerFactory.getLogger(BaseSms.class);
	PropertyUtils prop ;
	public BaseSms() {
		try {
			prop=PropertyUtils.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public synchronized Result sendSms(SmsTask smsTask) throws ClientProtocolException,
			IOException, DateParseException {
		SmsMessage smsMsg = smsTask.getSmsMsg();
		return sendSms(smsMsg.getReceiver(), smsMsg.getContent(), smsMsg.getExtend(),smsMsg.getTiming());
	}

	public synchronized Result sendSms(String mobile, String content, String cell,
			Date sendTime) throws ClientProtocolException, IOException {
		HttpPost httpost = postSendAPI(mobile, content, cell, sendTime);
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();
		logger.debug("send-status: " + response.getStatusLine());
		System.out.println("send-status: " + response.getStatusLine());
		if (response.getStatusLine().getStatusCode() == 200)
			return parseSend(entity);
		else {
			return new Result(ZHSmsError.COMP_HTTP_ERROR.getCode(), response
					.getStatusLine().getReasonPhrase());
		}
	}

	public synchronized void report() throws ClientProtocolException, IOException,
			SmsException {
		HttpPost httpost = postReportAPI();
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();
		logger.debug("report-status: " + response.getStatusLine());
		if (response.getStatusLine().getStatusCode() == 200) {
			parseReport(entity);
		} else {
			throw new SmsException(ZHSmsError.COMP_HTTP_ERROR.getMessage()
					+ ":" + response.getStatusLine().getStatusCode() + " "
					+ response.getStatusLine().getReasonPhrase());
		}
	}

	public synchronized void deliver() throws ClientProtocolException, IOException {
		HttpPost httpost = postDeliverAPI();
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();
		logger.debug("deliver-status: " + response.getStatusLine());
		parseDeliver(entity);
	}

	public Result balance() throws ClientProtocolException, IOException {
		HttpPost httpget = postBalanceAPI();
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		logger.debug("balance-status: " + response.getStatusLine());
		System.out.println("balance-status: " + response.getStatusLine());
		if (response.getStatusLine().getStatusCode() == 200) {
			return parseBalance(entity);
		} else {
			return new Result(ZHSmsError.COMP_HTTP_ERROR.getCode(), response
					.getStatusLine().getReasonPhrase());
		}
	}

	/**
	 * 发送短信的HttpPost
	 * 
	 * @param phones
	 * @param content
	 * @param extend
	 * @param sendTime
	 * @return
	 */
	protected abstract HttpPost postSendAPI(String phones, String content,
			String extend, Date sendTime);

	/**
	 * 获取剩余短信数目的HttpPost
	 * 
	 * @return
	 */
	protected abstract HttpPost postBalanceAPI();

	/**
	 * 获取短信报告的HttpPost
	 * 
	 * @return
	 */
	protected abstract HttpPost postReportAPI();

	/**
	 * 获取上行短信的HttpPost
	 * 
	 * @return
	 */
	protected abstract HttpPost postDeliverAPI();

	/**
	 * 发送短信请求返回-结果分析
	 * 
	 * @param entity
	 * @return
	 */
	protected abstract Result parseSend(HttpEntity entity);

	/**
	 * 查询剩余短信数目请求-结果分析
	 * 
	 * @param entity
	 * @return
	 */
	protected abstract Result parseBalance(HttpEntity entity);

	/**
	 * 获取短信报告请求-结果分析
	 * 
	 * @param entity
	 * @return
	 */
	protected abstract Result parseReport(HttpEntity entity)
			throws SmsException;

	/**
	 * 获取上行短信请求-结果分析
	 * 
	 * @param entity
	 * @return
	 */
	protected abstract Result parseDeliver(HttpEntity entity);

}
