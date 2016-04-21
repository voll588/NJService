package com.push;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class UserANDROIDPush extends UserPush{

	@Override
	public Map pushtoList(List list) {
		
		return null;
	}

	@Override
	public Map pushtoSingle(String CID) {

		return null;
	}

	/**
	 * 穿透推送
	 * @param CID
	 * @return
	 */
	public Map pushTransmission(String CID) {
		TransmissionTemplate template = TransmissionTemplate("cancel");
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		//离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(OfflineExpireTime);
		message.setData(template); 
		Target target1 = new Target(); 
		target1.setAppId(appId);
		target1.setClientId(CID); 
		IPushResult ret = push.pushMessageToSingle(message, target1);
		System.out.println(ret.getResponse().toString());
		return null;
	}
	/**
	 * 穿透推送
	 * @param CID
	 * @return
	 */
	public Map pushPhoto(String CID,String sub) {
//		NotificationTemplate template = NotificationTemplate();
//		SingleMessage message = new SingleMessage();
//		message.setOffline(true);
//		//离线有效时间，单位为毫秒，可选
//		message.setOfflineExpireTime(OfflineExpireTime);
//		message.setData(template); 
//		Target target1 = new Target(); 
//		target1.setAppId(appId);
//		target1.setClientId(CID); 
//		IPushResult ret = push.pushMessageToSingle(message, target1);
//		System.out.println(ret.getResponse().toString());
		return null;
	}
	@Override
	public Map pushtoSingle(String token, String title, String cmd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map pushtoSingle(String CID, String noticeSub) {
		NotificationTemplate template = NotificationTemplate(noticeSub);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		//离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(OfflineExpireTime);
		message.setData(template);
		Target target1 = new Target();

		target1.setAppId(appId);
		target1.setClientId(CID);

		IPushResult ret = push.pushMessageToSingle(message, target1);
		System.out.println(ret.getResponse().toString());
		return null;
	}
}
