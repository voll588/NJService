package com.alipay.util;

import java.util.Date;
import java.util.Random;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

/* *
 *类名：UtilDate
 *功能：自定义订单类
 *详细：工具类，可以用作获取系统日期、订单编号等
 *版本：3.3
 *日期：2012-08-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class UtilDate {

	/**
	 * 变量：日期格式化类型 - 格式:yyyy/MM/dd
	 */
	public static final int DEFAULT = 0;
	public static final int YM = 1;

	/**
	 * 变量：日期格式化类型 - 格式:yyyy-MM-dd
	 * 
	 */
	public static final int YMR_SLASH = 11;

	/**
	 * 变量：日期格式化类型 - 格式:yyyyMMdd
	 * 
	 */
	public static final int NO_SLASH = 2;

	/**
	 * 变量：日期格式化类型 - 格式:yyyyMM
	 * 
	 */
	public static final int YM_NO_SLASH = 3;

	/**
	 * 变量：日期格式化类型 - 格式:yyyy/MM/dd HH:mm:ss
	 * 
	 */
	public static final int DATE_TIME = 4;

	/**
	 * 变量：日期格式化类型 - 格式:yyyyMMddHHmmss
	 * 
	 */
	public static final int DATE_TIME_NO_SLASH = 5;

	/**
	 * 变量：日期格式化类型 - 格式:yyyy/MM/dd HH:mm
	 * 
	 */
	public static final int DATE_HM = 6;

	/**
	 * 变量：日期格式化类型 - 格式:HH:mm:ss
	 * 
	 */
	public static final int TIME = 7;

	/**
	 * 变量：日期格式化类型 - 格式:HH:mm
	 * 
	 */
	public static final int HM = 8;

	/**
	 * 变量：日期格式化类型 - 格式:HHmmss
	 * 
	 */
	public static final int LONG_TIME = 9;
	/**
	 * 变量：日期格式化类型 - 格式:HHmm
	 * 
	 */

	public static final int SHORT_TIME = 10;

	/** 年月日时分秒(无下划线) yyyyMMddHHmmss */
	public static final String dtLong = "yyyyMMddHHmmss";

	/** 完整时间 yyyy-MM-dd HH:mm:ss */
	public static final String simple = "yyyy-MM-dd HH:mm:ss";

	/** 年月日(无下划线) yyyyMMdd */
	public static final String dtShort = "yyyyMMdd";

	/**
	 * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
	 * 
	 * @return 以yyyyMMddHHmmss为格式的当前系统时间
	 */
	public static String getOrderNum() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtLong);
		return df.format(date);
	}

	/**
	 * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getDateFormatter() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(simple);
		return df.format(date);
	}

	/**
	 * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
	 * 
	 * @return
	 */
	public static String getDate() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtShort);
		return df.format(date);
	}

	/**
	 * 产生随机的三位数
	 * 
	 * @return
	 */
	public static String getThree() {
		Random rad = new Random();
		return rad.nextInt(1000) + "";
	}

	/**
	 * 比较日期
	 * 
	 * @param date
	 * @param update
	 * @return
	 * @throws ParseException
	 */
	public static boolean checkDate(String date, String update)
			throws ParseException {
		boolean b = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date dateTime = dateFormat.parse(date);
		Date dateTime1 = dateFormat.parse(update);
		b = dateTime.after(dateTime1);
		return b;
	}

	public static final int DATE_TIME_LINE = 12;

	public static String dateToStr(Date date, int type) {
		switch (type) {
		case DEFAULT:
			return dateToStr(date);
		case YM:
			return dateToStr(date, "yyyy/MM");
		case NO_SLASH:
			return dateToStr(date, "yyyyMMdd");
		case YMR_SLASH:
			return dateToStr(date, "yyyy-MM-dd");
		case YM_NO_SLASH:
			return dateToStr(date, "yyyyMM");
		case DATE_TIME:
			return dateToStr(date, "yyyy/MM/dd HH:mm:ss");
		case DATE_TIME_NO_SLASH:
			return dateToStr(date, "yyyyMMddHHmmss");
		case DATE_HM:
			return dateToStr(date, "yyyy/MM/dd HH:mm");
		case TIME:
			return dateToStr(date, "HH:mm:ss");
		case HM:
			return dateToStr(date, "HH:mm");
		case LONG_TIME:
			return dateToStr(date, "HHmmss");
		case SHORT_TIME:
			return dateToStr(date, "HHmm");
		case DATE_TIME_LINE:
			return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
		default:
			throw new IllegalArgumentException("Type undefined : " + type);
		}
	}

	public static String dateToStr(Date date, String pattern) {
		if (date == null || date.equals(""))
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}

	public static String dateToStr(Date date) {
		return dateToStr(date, "yyyy/MM/dd");
	}

}
