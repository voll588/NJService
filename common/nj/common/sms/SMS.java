package nj.common.sms;

import nj.common.utils.JsonReqClient;

import nj.common.utils.SysUtil;

public class SMS {

	/**
	 * 发送短信模板
	 * 
	 * @param msg
	 *            发送短信内容。填充模板缺失内容
	 * @param templateId
	 *            模板ID
	 * @param to
	 *            接收短信的手机号
	 * @return
	 */
	public String sendMsg(String msg, String templateId, String to) {
		String res = null;
		JsonReqClient jc = new JsonReqClient();
		res = jc.templateSMS(SysUtil.getValue("accountSid"),
				SysUtil.getValue("authToken"), SysUtil.getValue("interappId"),
				templateId, to, msg);
		return res;
	}

	/**
	 * 语音验证码
	 * @return
	 */
	public String sendVoiceMsg(String to,String vCode){
		String res = null;
		JsonReqClient jc = new JsonReqClient();
		res = jc.voiceCode(SysUtil.getValue("accountSid"), SysUtil.getValue("authToken"), 
				SysUtil.getValue("interappId"), to, vCode);
		return res;
	}
	
	public String getIdentifyingcode() {
		// 该变量用来保存系统生成的随机字符串
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			// 取得一个随机字符
			String tmp = getRandomChar();
			sRand += tmp;
		}
		return sRand;
	}

	// 随机生成一个字符
	private String getRandomChar() {
		return randomNumber();
	}

	private String randomNumber() {
		long number = 1;
		do {
			number = Math.round(Math.random() * 9);
		} while (number == 1 || number == 0);
		return String.valueOf(number);
	}
	
	public  static void main(String[] arg){
		SMS s = new SMS();
        s.sendVoiceMsg("15109283246","1111");		
	}
}
