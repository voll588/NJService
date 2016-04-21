package com.push;

import java.util.List;
import java.util.Map;
import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;
import javapns.data.PayLoadCustomAlert;
import org.apache.log4j.Logger;
import org.json.JSONException;
import nj.common.utils.SysUtil;

public class UserIOSPush extends UserPush {
	private static String p12path = SysUtil.getValue("web_root")+"WEB-INF/classes/push.p12";
	private static String password = "123456";
	private String host= "gateway.push.apple.com";  //测试用的苹果推送服务器
	private int port = 2195;
 
	/************************************************
		 测试推送服务器地址：gateway.sandbox.push.apple.com /2195 
		 产品推送服务器地址：gateway.push.apple.com / 2195 

		需要javaPNS_2.2.jar包

	 ***************************************************/
	/**
	 *这是一个比较简单的推送方法，
	 * apple的推送方法
	 * @param tokens   iphone手机获取的token
	 * @param path 这里是一个.p12格式的文件路径，需要去apple官网申请一个 
	 * @param password  p12的密码 此处注意导出的证书密码不能为空因为空密码会报错
	 * @param message 推送消息的内容
	 * @param count 应用图标上小红圈上的数值
	 * @param sendCount 单发还是群发  true：单发 false：群发

	 */
	public void sendpush(String tokens,String path, String password,PayLoad payLoad,Integer count) {
		try {
			//PropertyConfigurator.configure("config.properties");
			Logger console = Logger.getLogger(UserIOSPush.class);
			//PushNotificationPayload payLoad =  PushNotificationPayload.fromJSON(message);
			PushNotificationManager pushManager = PushNotificationManager.getInstance();
			pushManager.addDevice("iPhone", tokens);
			pushManager.initializeConnection(host, port, p12path,password, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
			Device client = pushManager.getDevice("iPhone");
			pushManager.sendNotification(client, payLoad); 
			pushManager.stopConnection();
			pushManager.removeDevice("iPhone");
			System.out.println("push succeed!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Map pushtoSingle(String token,String title,String cmd) {
		PayLoadCustomAlert alert2 = new PayLoadCustomAlert();
		PayLoad payLoad = new PayLoad();
		try {
			alert2.addLocKey(cmd);
			payLoad.addAlert(title);
			payLoad.addBadge(1);
			payLoad.addSound("default");
			payLoad.addCustomDictionary("key", cmd);
			//payLoad.addCustomAlert(alert2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//System.out.println("JSONObject.fromObject(message).toString() = "+JSONObject.fromObject(message).toString());
		this.sendpush(token, p12path, password, payLoad, 1);
		return null;
	}
	public Map pushtoPhoto(String token,String title,String cmd) {
		PayLoadCustomAlert alert2 = new PayLoadCustomAlert();
		PayLoad payLoad = new PayLoad();
		try {
			
			alert2.addLocKey(cmd);
			payLoad.addAlert(title);
			payLoad.addBadge(1);
			payLoad.addSound("default");
			payLoad.addCustomDictionary("userId", cmd);
			payLoad.addCustomDictionary("content-available", 1);
//			payLoad.addCustomAlert(alert2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
//		System.out.println("JSONObject.fromObject(message).toString() = "+JSONObject.fromObject(message).toString());
		this.sendpush(token, p12path, password, payLoad, 1);
		return null;
	}
	@Override
	public Map pushtoList(List tokens) {
		return null;
	}
	 
	@Override
	public Map pushtoSingle(String CID) {
		return null;
	}

	@Override
	public Map pushtoSingle(String token, String cmd) {
		return null;
	}

	/**
	 * TODO
	 * @param args
	 */
	public static void main(String[] args) {
		UserIOSPush send=new UserIOSPush();
	/*	List<String> tokens=new ArrayList<String>();
		tokens.add("13f01e372964990b52abf56eccd203937045b099d3ae848024d5c34a2f39b791");*/
		//String message="{'aps':{'alert':'iphone推送测试 www.baidu.com'}}"; //{"aps":{"alert":"order"}}
		send.pushtoSingle("098b3aa4b3847dfcc2e40ba0b67ca2f942b74b784ec362944eb2c69fd1348052","你娃被老师打了！快来救你娃吧！","order");

	}
}
