package nj.common;

public class Constants {	

	
	/*返回状态常量*/
	public static String APICODE_OK ="0"; //接口正确
	//系统错误
	public static String APICODE_ERROR ="1";
	//token错误
	public static String APICODE_TOKEN_ERROR ="2";
	//用户已经注册
	public static String APICODE_USER_REG ="3";
	//密码长度不足六位
	public static String APICODE_PWD_LENGTHE ="4";
	//密码错误
	public static String APICODE_PWD_ERROR ="5";
	//邀请码错误
	public static String APICODE_INKEY_NULL ="6";
	//验证码错误
	public static String APICODE_CODE_ERROR ="7";
	//登录错误
	public static String APICODE_LOGIN_ERROR ="8";
	//兴趣班满额
	public static String APICODE_CLASS_ERROR ="9";

	//-----------后台状态码------------------------------------------
	
	//管理员权限错误
	public static String APICODE_ADMIN_ROLE_ERROR ="30";
	
	
	//-----------文本信息------------------------------------------
	
	//前台
	//管理员权限错误
	public static String CLIENT_MSG_ARREARAGE ="您宝贝的保教费和餐费都已经欠费，请及时缴费！";
	
	//管理员权限错误
	public static String CLIENT_MSG_ARREARAGE_FEE ="您宝贝的保教费已经欠费，请及时缴费！";
	
	//管理员权限错误
	public static String CLIENT_MSG_ARREARAGE_FOOD ="您宝贝的餐费已经欠费，请及时缴费！";
	
	
	//后台
	//班级删除错误
	public static String BG_DELCLASS_ERROR ="当前班级还有学生存在，无法直接删除！";
	
	//老师删除错误
	public static String BG_DELTEACHER_ERROR ="删除的老师还是带班老师，无法直接删除！";
	
}
