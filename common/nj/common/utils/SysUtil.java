package nj.common.utils;

import java.security.MessageDigest;
import java.util.Properties;
import java.util.UUID;

public class SysUtil {
	
	public static final String CODE = "code";//返回码
	
	public static final String USER = "user";
	
	public static final Long GBSIZE = 1024*1024*1024l;
	
	public static final String PERSON_PASSWORD = "123456";//默认初始密码
	
	public static final Long ENTERPRISE_INITIAL_SIZE = 5368709120l;//分配默认企业空间大小
	
	public static final Long PERSON_INITIAL_SIZE = 1073741824l;//分配默认个人空间大小
	
	public static final String CLIENT_TAG = "client_tag";
	
	public static final String TOKEN = "token";
	
	public static final int CLIENT_TAG_IOS = 0;
	public static final int CLIENT_TAG_ANDROID = 1;
	
	public static final int USER_OPERATION_LOGIN = 0;//用户登陆
	public static final int USER_OPERATION_LOGOUT = 1;//用户退出
	
	public static final int GUARANTEE_TIME = 10;//担保时间默认（day）

	/**
     * 地球半径：6378.137KM
     */
    private static double EARTH_RADIUS = 6378.137;
	
	private static Properties cache = new Properties();
	static{
		try {
			cache.load(SysUtil.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key){
		return cache.getProperty(key);
	}
	
	public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

    /**
     * 根据经纬度和距离返回一个矩形范围
     *
     * @param lng
     *            经度
     * @param lat
     *            纬度
     * @param distance
     *            距离(单位为米)
     * @return [lng1,lat1, lng2,lat2] 矩形的左下角(lng1,lat1)和右上角(lng2,lat2)
     */
    public static double[] getRectangle(double lng, double lat, long distance) {
        float delta = 111000;
        if (lng != 0 && lat != 0) {
            double lng1 = lng - distance
                    / Math.abs(Math.cos(Math.toRadians(lat)) * delta);
            double lng2 = lng + distance
                    / Math.abs(Math.cos(Math.toRadians(lat)) * delta);
            double lat1 = lat - (distance / delta);
            double lat2 = lat + (distance / delta);
            return new double[] { lng1, lat1, lng2, lat2 };
        } else {
            // TODO ZHCH 等于0时的计算公式
            double lng1 = lng - distance / delta;
            double lng2 = lng + distance / delta;
            double lat1 = lat - (distance / delta);
            double lat2 = lat + (distance / delta);
            return new double[] { lng1, lat1, lng2, lat2 };
        }
    }
    
    /**
     * 得到两点间的距离 米
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getDistanceOfMeter(double lat1, double lng1,
            double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10;
        return s;
    }
     
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
    
    public static String getVerificationMessage(String code) {
    	//return "【译人提示】您的验证码为"+code+"（验证码将在10分钟后失效）。此验证码只用于注册您的译人，找回密码，验证码提供给他人将导致译人账号被盗，再次提醒，请勿转发。";
    	return "【译人提示】您的验证码为："+code+"（验证码将在10分钟后失效），此验证码只用于注册您的译人、找回密码，验证码提供给他人将导致译人账号被盗。再次提醒，请勿转发。";
    	// "您的验证码为:"+code+"(验证码将在10分钟后失效),此验证码只用于登录您的译人或更换绑定,验证码提供给他人将导致译人被盗,再次提醒,请勿转发.";
    }
    
    public static String getRetrieveMessage(String code) {
    	return "【译人提示】您的验证码为："+code+"（验证码将在10分钟后失效）。此验证码只用于注册您的译人、找回密码，验证码提供给他人将导致译人账号被盗。再次提醒，请勿转发。";
	}
    public static String getpassMessage() {
    	return "【译人提示】您的考核已通过，立刻开始接单赚取外快吧。";
    }
    
    public static String getrefuseMessage() {
    	return "【译人提醒】抱歉，您的考核未能通过，再试一次吧。";
	}
    /** 
     * 初始化HMAC密钥 
     *  
     * @return 
     * @throws Exception 
     */  
    public static String initMacKey() throws Exception {
    	UUID uuid = UUID.randomUUID(); 
    	return  uuid.toString() ;
    } 
}
