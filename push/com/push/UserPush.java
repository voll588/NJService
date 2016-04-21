package com.push;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.NotyPopLoadTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import nj.common.utils.SysUtil;

public abstract class UserPush {

	static String appId = "oHfK9yS1rV9SpZoU0KJZ86";
	static String appkey = "Cl2x5oDe8C700aEYSuP8R9";
	static String master = "XpTwgZ290y8LSDuWATNGK";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";

	static long OfflineExpireTime = 0;
    
	static IGtPush push;

	static {
		appId = SysUtil.getValue("appId_u");
		appkey = SysUtil.getValue("appkey_u");
		master = SysUtil.getValue("master_u");
		host = SysUtil.getValue("host");
		OfflineExpireTime = Long.parseLong(SysUtil.getValue("OfflineExpireTime"));
		push = new IGtPush(host, appkey, master);
		try {
			push.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 对单个列表推送
	 * @param list
	 * @return
	 */
	public abstract Map pushtoList(List list);
	
	/**
	 * 对列表推送
	 * @return
	 */
	public abstract Map pushtoSingle(String CID);
	
	/**
	 * 点击通知打开应用模板
	 * @return
	 */
	public NotificationTemplate NotificationTemplate(String text) {
	    NotificationTemplate template = new NotificationTemplate();
	    // 设置APPID与APPKEY
	    template.setAppId(appId);
	    template.setAppkey(appkey);
	    // 设置通知栏标题与内容
	    template.setTitle("牛津幼儿园通知");
	    template.setText(text);
	    // 配置通知栏图标
	    template.setLogo("push_icon_45.png");
	    // 配置通知栏网络图标
	    template.setLogoUrl("");
	    // 设置通知是否响铃，震动，或者可清除
	    template.setIsRing(true);
	    template.setIsVibrate(true);
	    template.setIsClearable(true);
	    // 透传消息设置
	    template.setTransmissionType(1);
	    template.setTransmissionContent("请输入您要透传的内容");
	    return template;
	}
	
	/**
	 * 点击通知打开网页模板
	 * @return
	 */
	public LinkTemplate linkTemplate() {
	    LinkTemplate template = new LinkTemplate();
	    // 设置APPID与APPKEY
	    template.setAppId(appId);
	    template.setAppkey(appkey);
	    // 设置通知栏标题与内容
	    template.setTitle("请输入通知栏标题");
	    template.setText("请输入通知栏内容");
	    // 配置通知栏图标
	    template.setLogo("icon.png");
	    // 配置通知栏网络图标
	    template.setLogoUrl("");
	    // 设置通知是否响铃，震动，或者可清除
	    template.setIsRing(true);
	    template.setIsVibrate(true);
	    template.setIsClearable(true);
	    // 设置打开的网址地址
	    template.setUrl("http://www.baidu.com");
	    return template;
	}
	
	/**
	 * 点击通知栏弹框下载模板
	 * @return
	 */
	public NotyPopLoadTemplate NotyPopLoadTemplate() {
	    NotyPopLoadTemplate template = new NotyPopLoadTemplate();
	    // 设置APPID与APPKEY
	    template.setAppId(appId);
	    template.setAppkey(appkey);
	    // 设置通知栏标题与内容
	    template.setNotyTitle("请输入通知栏标题");
	    template.setNotyContent("请输入通知栏内容");
	    // 配置通知栏图标
	    template.setNotyIcon("icon.png");
	    // 设置通知是否响铃，震动，或者可清除
	    template.setBelled(true);
	    template.setVibrationed(true);
	    template.setCleared(true);
	  
	    // 设置弹框标题与内容
	    template.setPopTitle("弹框标题");
	    template.setPopContent("弹框内容");
	    // 设置弹框显示的图片
	    template.setPopImage("http://www-igexin.qiniudn.com/wp-content/uploads/2013/08/logo_getui1.png");
	    template.setPopButton1("下载");
	    template.setPopButton2("取消");
	  
	    // 设置下载标题
	    template.setLoadTitle("下载标题");
	    template.setLoadIcon("file://icon.png");
	    //设置下载地址       
	    template.setLoadUrl("http://wap.igexin.com/android_download/Gexin_android_2.0.apk");
	    return template;
	}
	
	/**
	 * 透传消息模板
	 * @return
	 */
	public TransmissionTemplate TransmissionTemplate(String content) {
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(appId);
	    template.setAppkey(appkey);
	    template.setTransmissionType(2);
	    template.setTransmissionContent(content);
	    return template;
	}
	
	/**
	 * iOS模板
	 * @return
	 * @throws Exception 
	 */
	public TransmissionTemplate iosTransmissionTemplate() throws Exception {
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(appId);
	    template.setAppkey(appkey);
	    template.setTransmissionType(1);
	    template.setTransmissionContent("请输入需要透传的内容");
	 
	    /*iOS 推送需要对该字段进行设置具体参数详见iOS模板说明*/
	    template.setPushInfo("actionLocKey", 4, "message", "sound",
	    "payload", "locKey", "locArgs", "launchImage");
	    return template;
	}
	
	public abstract Map pushtoSingle(String token,String title,String cmd);
	
	public abstract Map pushtoSingle(String token,String cmd);
}
