package nj.common.sms;

import java.io.IOException;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.DateParseException;

import nj.common.sms.except.SmsException;
import nj.common.sms.model.Result;
import nj.common.sms.model.SmsTask;

public interface SmsComponent {
	
	DefaultHttpClient httpclient = new DefaultHttpClient();
	
	public Result sendSms(SmsTask smsTask) throws ClientProtocolException,DateParseException,IOException ;
	
	/**发送短信
	 * @param mobile
	 * @param content
	 * @param cell
	 * @param sendTime
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Result sendSms(String mobile, String content, String cell,Date sendTime) throws ClientProtocolException, IOException ;
	/**获取短信报告
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws SmsException
	 */
	public void report() throws ClientProtocolException, IOException ,SmsException;
	/**取上行短信
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void deliver() throws ClientProtocolException, IOException ;
	/**查询剩余短信数目
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Result balance() throws ClientProtocolException, IOException;
}
