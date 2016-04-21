package nj.common.sms;

import nj.common.sms.enums.SmsPriority;
import nj.common.sms.model.SmsMessage;
import nj.common.sms.model.SmsTask;
import nj.common.utils.SysUtil;

public class ZhSmsComponent {

	/*private String[] locations = {"sms/applicationContext.xml" };
	private ApplicationContext ctx;
	*/
	
	private static ZhSmsComponent zhSmsComp = new ZhSmsComponent();
	
	private ZhSmsComponent(){
		//ctx = new ClassPathXmlApplicationContext(locations);
	}
	public static ZhSmsComponent getInstance() {
		return zhSmsComp;
	}
	
	public SmsComponent getUnicomSms(){
		return new UnicomSms();
		//return new SHBHSms();
	}
	
	public static void main(String[] args){
		
		SmsComponent zhSmsComp =ZhSmsComponent.getInstance().getUnicomSms();
		SmsTask smsTask = new SmsTask();
		SmsMessage smsMessage = new SmsMessage();
		
		smsMessage.setContent(SysUtil.getVerificationMessage("2698"));
							 //您的验证码为:234123(验证码将在10分钟后失效),此验证码只用于登录您的译人或更换绑定,验证码提供给他人将导致译人被盗,再次提醒,请勿转发.

		smsMessage.setReceiver("15109283246");
		smsMessage.setTiming(null);
		smsMessage.setPriority(SmsPriority.THREE.getValue());
		smsTask.setSmsMsg(smsMessage); 
		try {
			zhSmsComp.balance();
			//zhSmsComp.sendSms("15829778157", "糟糕！", "", null);
			zhSmsComp.sendSms(smsTask);
			//zhSmsComp.report();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
