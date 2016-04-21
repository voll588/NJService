package nj.common.utils;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class StringUtils {

	public static final String CHARSET_NAME_UTF8 = "UTF-8";

	public static final char[] digital = "0123456789ABCDEF".toCharArray();

	public static String pwdDecode(String pwd){
		pwd = EscapeUtil.escape(pwd);
		pwd = base64Decode(pwd);
		return pwd;
	}

	public static String base64Encode(String str){
		String s = "";
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		s = encoder.encode(str.getBytes());
		return s;
	}

	/**
	 * base64解码
	 * @param str
	 * @return
	 */
	public static String base64Decode(String str){

		String s ="";
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();

		try {
			s = new String(decoder.decodeBuffer(str));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	public static String getSize(long size){
		String s="";
		double b = Double.parseDouble(String.valueOf(size))/1024.00;
		if(b<1024){
			if(b<1){
				String a = b+"0";
				s="0"+a.substring(0, a.indexOf(".")+3)+"KB";   
				return s;
			}else{
				String a = b+"0";
				s=a.substring(0, a.indexOf(".")+3)+"KB";   
				return s;
			}
		}
		double kb = b/1024.00;
		if(kb<1024){
			if(kb<1){
				String a = kb+"0";
				s="0"+a.substring(0, a.indexOf(".")+3)+"MB";   
				return s;
			}else{
				String a = kb+"0";
				s=a.substring(0, a.indexOf(".")+3)+"MB";   
				return s;
			}
		}
		double mb = kb/1024.00;
		if(mb<1024){
			if(mb<1){
				String a = mb+"0";
				s="0"+a.substring(0, a.indexOf(".")+3)+"GB";   
				return s;
			}else{
				String a = mb+"0";
				s=a.substring(0, a.indexOf(".")+3)+"GB";   
				return s;
			}
		}
		double gb = mb/1024.00;
		if(gb<1024){
			if(gb<1){
				String a = gb+"0";
				s="0"+a.substring(0, a.indexOf(".")+3)+"TB";   
				return s;
			}else{
				String a = gb+"0";
				s=a.substring(0, a.indexOf(".")+3)+"TB";
				return s;
			}
		}
		double tb = gb/1024.00;
		if(tb<1){
			String a = tb+"0";
			s="0"+a.substring(0, a.indexOf(".")+3)+"PB";   
			return s;
		}else{
			String a = tb+"0";
			s=a.substring(0, a.indexOf(".")+3)+"PB";   
			return s;
		}
	}

	public static boolean isMailAddr(String email) {
		boolean ret = true;
		Pattern pattern = Pattern
				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		if(!matcher.matches())
			ret = false;
		if(email.trim().length() > 32)
			ret = false;
		return ret;
	}

	public static int getRandomNum(int max)
	{
		Random random = new Random();
		while(true){
			int randomNum = random.nextInt(max);
			if(randomNum > max/10){
				return randomNum;
			}
			else continue;
		}
	}

	public static final Long DEFAULT_MAX_SPACE = 10737418240l;// 用户注册默认最大空间

	public static String strReplace(String str){
		str.replace("(", "<");
		str.replace(")", ">");
		return str;
	}

	/**
	 * 
	 * isNull:判断字符串是否为null或空字符串
	 *
	 * @param  @param value
	 * @param  @return    设定文件
	 * @return boolean    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public static boolean isNull(String value){
		if(value==null){
			return true;
		} else {
			return value.length() == 0;
		}
	}

	public static String getCookieId(String str){
		String val = "";
		if(isNull(str)){
			return "";
		}else{
			String[] as = str.split(";");
			for(int i=0;i<as.length;i++){
				if("JSESSIONID".equals(as[i].substring(0,as[i].lastIndexOf("=")).trim())){
					val=as[i].substring(as[i].indexOf("=")+1);
					break;
				}
			}
		}

		return val;
	}

	/**
	 * 字符串替换
	 * 
	 * @param srcString
	 *            要求替换的字符串
	 * @param oldString
	 *            需要替换的子字符串
	 * @param newString
	 *            匹配后替换的子字符串
	 * @return srcString 替换后的字符串
	 */
	public static String replaceString(String srcString, String oldString,
			String newString) {
		try {
			if (srcString == null) {
				return "";
			} else if (oldString == null || newString == null) {
				return srcString;
			}
			String retString = new String();
			int currPos = srcString.indexOf(oldString);
			int lastPos = 0;
			while (currPos >= 0) {
				retString += srcString.substring(lastPos, currPos);
				retString += newString;
				lastPos = currPos + oldString.length();
				currPos = srcString.indexOf(oldString, lastPos);
			}
			retString += srcString.substring(lastPos, srcString.length());
			srcString = retString;
			return srcString;
		} catch (IndexOutOfBoundsException e) {
			return srcString;
		}
	}

	/**
	 * double型数字转换
	 * 
	 * @param strNumber
	 *            要求转换的数字
	 * @return 转换为double型的数字
	 */
	public static double toDouble(String strNumber) throws ParseException {
		if (strNumber == null || strNumber.equals("")) {
			return 0.0;
		}
		double d = NumberFormat.getInstance().parse(strNumber).doubleValue();
		return d;
	}

	/**
	 * int型数字转换
	 * 
	 * @param strNumber
	 *            要求转换的数字
	 * @return 转换为int型的数字
	 */
	public static int toInteger(String strNumber) {
		int retInt = 0;
		try {
			if (strNumber == null || strNumber.equals("")) {
				return 0;
			}
			retInt = Integer.parseInt(strNumber);
		} catch (Exception e) {
			return 0;
		}
		return retInt;
	}

	/**
	 * 数字9,999格式化转换
	 * 
	 * @param strNumber
	 *            要求转换的数字
	 * @return String the 转换后的数字
	 */
	public static String formatNumberToInt(String strNumber) {
		if (strNumber == null || strNumber.equals("")) {
			return "";
		}

		try {
			double d = Double.parseDouble(strNumber);
			if (d > 0) {
				d = d + 0.5;
			} else {
				d = d - 0.5;
			}
			long i = (long) d;
			d = (double) i;
			DecimalFormat df = new DecimalFormat("#,##0");
			strNumber = df.format(d);
			return strNumber;
		} catch (Exception e) {
			return strNumber;
		}
	}

	/**
	 * 数字9,999.9格式化转换
	 * 
	 * @param strNumber
	 *            要求转换的数字
	 * @return String the 转换后的数字
	 */
	public static String formatNumberToDou(String strNumber) {
		if (strNumber == null || strNumber.equals("")) {
			return "";
		}
		try {
			double d = Double.parseDouble(strNumber);
			DecimalFormat df = new DecimalFormat("#,##0.0");
			strNumber = df.format(d);
			return strNumber;
		} catch (Exception e) {
			return strNumber;
		}
	}

	/**
	 * 3位处理数字9,999格式化转换
	 * 
	 * @param strNumber
	 *            要求转换的字符串
	 * @return String the 转换后的字符串
	 */
	public static String formatNumberToComma(String strNumber) {
		if (strNumber == null || strNumber.equals("")) {
			return "";
		}
		try {
			double d = Double.parseDouble(strNumber);
			DecimalFormat df = new DecimalFormat("#,##0");
			strNumber = df.format(d);
			return strNumber;
		} catch (Exception e) {
			return strNumber;
		}
	}

	/**
	 * 字符串指定位数变换，前边补0
	 * 
	 * @param str
	 *            要求转换的字符串
	 * @param len
	 *            指定的字符串位数
	 * @return 转换后的字符串
	 */
	public static String formatStringByZero(String str, int len) {
		String tempString = "";
		if (str == null || str.equals("")) {
			for (int i = 0; i < len; i++) {
				tempString += "0";
			}
			return tempString;
		}
		if (str.length() >= len) {
			return str;
		}
		for (int i = 0; i < len - str.length(); i++) {
			tempString = "0" + tempString;
		}
		return tempString + str;
	}

	/**
	 * SQL文中逃逸字符的处理
	 * 
	 * @param strSql
	 *            SQL�?
	 * @return strSql 处理后的SQL�?
	 */
	public static String escapeLikeSql(String strSql) {
		if (strSql == null || strSql.equals("")) {
			return "";
		}
		//strSql = replaceString(strSql, "$", "$$");
		strSql = replaceString(strSql, "'", "''");
		//strSql = replaceString(strSql, "_", "$_");
		//strSql = replaceString(strSql, "%", "$%");
		return strSql;
	}

	/**
	 * 长字符串的省略表示用。
	 * 
	 * @param oldString
	 *            要求处理的字符串
	 * @param len
	 *            指定的长度
	 * @return 处理后的字符串
	 */
	public static String subStringApostrophe(String oldString, int len) {
		if (oldString == null || oldString.equals("")) {
			return "";
		}
		if (oldString.length() <= len) {
			return oldString;
		} else {
			return oldString.substring(0, len) + "...";
		}
	}

	/**
	 * 截取自字符串
	 * 
	 * @param oldString
	 *            要求截取的字符串
	 * @param len
	 *            指定字符串长度
	 * @return 处理后的字符串
	 */
	public static String subString(String oldString, int len) {
		if (oldString == null || oldString.equals("")) {
			return "";
		}
		if (oldString.length() <= len) {
			return oldString;
		} else {
			return oldString.substring(0, len);
		}
	}

	/**
	 * 字符串前后空格的删除
	 * 
	 * @param oldString
	 *            要求处理的字符串
	 * @return 处理后的字符串
	 */
	public static String trim(String oldString) {
		if (oldString == null) {
			return "";
		}
		StringBuffer sbStr = new StringBuffer();
		boolean bEnd = false;
		for (int i = 0; i < oldString.length(); i++) {
			if ((" ".equals(String.valueOf(oldString.charAt(i))) || "  "
					.equals(String.valueOf(oldString.charAt(i))))
					&& !bEnd) {
			} else {
				bEnd = true;
				sbStr.append(oldString.charAt(i));
			}
		}
		oldString = sbStr.toString();
		sbStr = new StringBuffer();
		bEnd = false;
		for (int i = oldString.length(); i > 0; i--) {
			if ((" ".equals(String.valueOf(oldString.charAt(i - 1))) || "  "
					.equals(String.valueOf(oldString.charAt(i - 1))))
					&& !bEnd) {
			} else {
				bEnd = true;
				sbStr.insert(0, oldString.charAt(i - 1));
			}
		}
		return sbStr.toString().trim();
	}

	/**
	 * 字符串split处理
	 * 
	 * @return fieldArr
	 */
	public static String[] split(String splitString, String splitFlag) {
		String[] fieldArr;
		try {
			if (splitString == null) {
				return null;
			} else if (splitFlag == null) {
				fieldArr = new String[1];
				fieldArr[0] = splitString;
				return fieldArr;
			}
			Vector tempVector = new Vector();
			String tempString;
			int currPos = splitString.indexOf(splitFlag);
			int lastPos = 0;
			while (currPos >= 0) {
				tempString = splitString.substring(lastPos, currPos);
				tempVector.addElement(tempString);
				lastPos = currPos + splitFlag.length();
				currPos = splitString.indexOf(splitFlag, lastPos);
			}
			tempString = splitString.substring(lastPos, splitString.length());
			tempVector.addElement(tempString);

			fieldArr = new String[tempVector.size()];
			for (int i = 0; i < tempVector.size(); i++) {
				fieldArr[i] = (String) tempVector.elementAt(i);
			}
			return fieldArr;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * 数字字符串指定长度，左侧补0
	 * 
	 * @param text
	 *            处理的数字字符串
	 * @param length
	 *            指定长度
	 * @return 处理后的数字字符串
	 * 
	 */
	public static String toStrAddZero(String text, int length) {
		String OutString = "";
		try {
			if (text != null) {
				if (text.length() >= length) {
					OutString = text;
				} else {
					while (length > text.length()) {
						OutString = OutString + "0";
						length--;
					}
					OutString = OutString + text;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			return "";
		}
		return OutString;
	}

	/**
	 * null 空字符串的替换处理<BR>
	 * 
	 * @param s
	 *            处理的字符串
	 * @return 处理后的字符串
	 */
	public static String nullToBlank(String s) {
		if (null == s || "null".equals(s) ||"NULL".equals(s) || "(null)".equals(s) || "(NULL)".equals(s) ) {
			return "";
		} else {
			return trim(s);
		}
	}

	/**
	 * null 空字符串的替换处理<BR>
	 * 
	 * @param s
	 *            处理的字符串
	 * @return 处理后的字符串
	 */
	public static String nullToZero(String s) {
		if (null == s  ||"".equals(s) || "null".equals(s) || "NULL".equals(s)) {
			return "0";
		} else {
			return trim(s);
		}
	}

	/**
	 * 数字字符串指定长度，左侧补0
	 * 
	 * @param text
	 *            处理的数字字符串
	 * @param length
	 *            指定长度
	 * @return 处理后的数字字符串
	 */
	public static java.lang.String toStringAdd(java.lang.String text, int length) {
		String OutString = "";
		try {
			if (text.length() == length) {
				OutString = text;
			} else {
				OutString = "";
				while (length > text.length()) {
					OutString = OutString + "0";
					length--;
				}
				OutString = OutString + text;
			}
		} catch (Exception ex) {
			return "";
		}
		return OutString;
	}

	/**
	 * 取得系统日期
	 * 
	 * @return String 返回系统日期(YYYYMMDD)
	 */
	public static String getSysDate() {
		String strDate = new String();
		try {
			GregorianCalendar sysDate = new GregorianCalendar();
			strDate = String.valueOf(sysDate.get(Calendar.YEAR));
			strDate += toStringAdd(String
					.valueOf(sysDate.get(Calendar.MONTH) + 1), 2);
			strDate += toStringAdd(String.valueOf(sysDate.get(Calendar.DATE)),
					2);
		} catch (Exception ex) {
			return "";
		}
		return strDate;
	}

	// /**
	// * 日期计算
	// *
	// * @param curTime
	// * @param days
	// * @return
	// */
	// public static String calDate(String curTime,int days){
	// String strDate = new String();
	// try {
	// Calendar cal = Calendar.getInstance();
	// cal.set(Calendar.YEAR, toInteger(curTime.substring(0, 2)));
	// cal.set(Calendar.MONTH, toInteger(curTime.substring(2, 4)));
	// cal.set(Calendar.DATE, toInteger(curTime.substring(4, 8)));
	// cal.add(Calendar.DATE, days);
	// strDate = String.valueOf(cal.get(Calendar.YEAR));
	// strDate += toStringAdd(String
	// .valueOf(cal.get(Calendar.MONTH) + 1), 2);
	// strDate += toStringAdd(String.valueOf(cal.get(Calendar.DATE)),
	// 2);
	// } catch (Exception ex) {
	// return "";
	// }
	// return strDate;
	// }
	/**
	 * 取得系统时间
	 * 
	 * @return String 返回系统时间(YYYY-MM-DD HH:MM:SS)
	 */
	public static String getSystemTime() {
		String strDate = new String();
		try {
			GregorianCalendar sysDate = new GregorianCalendar();
			strDate = String.valueOf(sysDate.get(Calendar.YEAR)) + "-";
			strDate += toStringAdd(String
					.valueOf(sysDate.get(Calendar.MONTH) + 1), 2)
					+ "-";
			strDate += toStringAdd(String.valueOf(sysDate.get(Calendar.DATE)),
					2)
					+ " ";
			strDate += toStringAdd(String.valueOf(sysDate
					.get(Calendar.HOUR_OF_DAY)), 2)
					+ ":";
			strDate += toStringAdd(
					String.valueOf(sysDate.get(Calendar.MINUTE)), 2)
					+ ":";
			strDate += toStringAdd(
					String.valueOf(sysDate.get(Calendar.SECOND)), 2);
		} catch (Exception ex) {
			return "";
		}
		return strDate;
	}

	/**
	 * 取得系统时间
	 * 
	 * @return String 返回系统时间(YYYYMMDDHHMMSS)
	 */
	public static String getSysDateTime() {
		String strDate = new String();
		try {
			GregorianCalendar sysDate = new GregorianCalendar();
			strDate = String.valueOf(sysDate.get(Calendar.YEAR));
			strDate += toStringAdd(String
					.valueOf(sysDate.get(Calendar.MONTH) + 1), 2);
			strDate += toStringAdd(String.valueOf(sysDate.get(Calendar.DATE)),
					2);
			strDate += toStringAdd(String.valueOf(sysDate
					.get(Calendar.HOUR_OF_DAY)), 2);
			strDate += toStringAdd(
					String.valueOf(sysDate.get(Calendar.MINUTE)), 2);
			strDate += toStringAdd(
					String.valueOf(sysDate.get(Calendar.SECOND)), 2);
		} catch (Exception ex) {
			return "";
		}
		return strDate;
	}

	/**
	 * 日期计算
	 * 
	 * @param String
	 *            date 日期
	 * @param int
	 *            iDay 日数
	 * @param int
	 *            iType 算法 0:加法 1:减法
	 * @return String strDate 计算后的日期
	 */
	public static String calculateDate(String date, int iDay, int iType)
			throws Exception {
		String strDate = new String();
		try {
			int iYear = 0;
			int iMonth = 0;
			int iDate = 0;
			iYear = Integer.parseInt(date.substring(0, 4));
			iMonth = Integer.parseInt(date.substring(4, 6));
			iMonth -= 1;
			iDate = Integer.parseInt(date.substring(6, 8));
			Calendar calendar = Calendar.getInstance();
			// 设置日期
			calendar.set(iYear, iMonth, iDate);
			if (iType == 1) {
				calendar.add(Calendar.DATE, -iDay);
			} else {
				calendar.add(Calendar.DATE, +iDay);
			}
			strDate = toStrAddZero(Integer.toString(calendar
					.get(Calendar.YEAR)), 4)
					+ toStrAddZero(Integer.toString(calendar
							.get(Calendar.MONTH) + 1), 2)
							+ toStrAddZero(Integer.toString(calendar
									.get(Calendar.DATE)), 2);
			return strDate;
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * 根据"/"分割字符串
	 * 
	 * @param String
	 *            array 分割前String
	 * @return String RankArray 分割后String
	 */
	public static String stringFormat(String string) {

		String stringFormat = "";
		stringFormat = string.substring(2, 4) + "/" + string.substring(4, 6)
				+ "/" + string.substring(6, 8);
		return stringFormat;
	}

	/**
	 * String null to ""
	 * 
	 * @param strTemp
	 * @return String
	 */
	public static String nullToNone(String strTemp) {
		String strTo = "";
		if (strTemp != null) {
			strTo = strTemp.trim();
		}
		return strTo;
	}

	/**
	 * 特殊字符转换
	 * 
	 * @param str
	 *            处理前字符串
	 * @return str 处理后字符串
	 */
	public static String htmlToJspText(String html) {

		String text = html;

		if (text == null) {
			text = "";
		} else {
			text = trim(text);
			text = text.replaceAll("\uFF5E", "\u301C");
			text = text.replaceAll("\uFF0D", "\u2212");
			text = text.replaceAll("\r\n", "<br>");
			text = text.replaceAll("\r", "<br>");
			text = text.replaceAll("&lt;", "<");
			text = text.replaceAll("&gt;", ">");
		}
		return text;
	}

	/**
	 * 特殊字符转换
	 * 
	 * @param str
	 *            处理前字符串
	 * @return str 处理后字符串
	 */
	public static String sqlToText(String html) {

		String text = html;

		if (text == null) {
			text = "";
		} else {
			text = trim(text);
			text = text.replaceAll("\uFF5E", "\u301C");
			text = text.replaceAll("&lt;", "<");
			text = text.replaceAll("&gt;", ">");
		}
		return text;
	}

	/**
	 * 特殊字符转换
	 * 
	 * @param str
	 *            处理前字符串
	 * @return str 处理后字符串
	 */
	public static String htmlToSql(String html) {

		String strSql = html;

		if (strSql == null) {
			strSql = "";
		} else {
			strSql = trim(strSql);
			strSql = strSql.replaceAll("\u301C", "\uFF5E");
			strSql = strSql.replaceAll("\u2212", "\uFF0D");
			strSql = strSql.replaceAll("<", "&lt;");
			strSql = strSql.replaceAll(">", "&gt;");
			String temp = strSql;
			strSql = "";
			for (int i = 0; i < temp.length(); i++) {
				if (temp.substring(i, i + 1).equals("'")) {
					strSql += "\\'";
				} else if (temp.substring(i, i + 1).equals("\"")) {
					strSql += "\\\"";
				} else if (temp.substring(i, i + 1).equals("\\")) {
					strSql += "\\\\";
				} else {
					strSql += temp.substring(i, i + 1);
				}
			}
		}
		return strSql;
	}

	/**
	 * 特殊字符转换
	 * 
	 * @param str
	 *            处理前字符串
	 * @return str 处理后字符串
	 */
	public static String htmlToText(String html) {

		String text = html;

		if (text == null) {
			text = "";
		} else {
			text = text.trim();
			text = text.replaceAll("\uFF5E", "\u301C");
			text = text.replaceAll("\uFF0D", "\u2212");
			text = text.replaceAll("&lt;", "<");
			text = text.replaceAll("&gt;", ">");
		}
		return text;
	}

	/**
	 * 日期计算
	 * 
	 * @param String
	 *            strSysDate, String strQueryDate 开始日,结束日
	 * @return int DateCount 日数
	 */
	public static int getDateSpan(String strSysDate, String strQueryDate) {
		int Year1 = new Integer(strSysDate.substring(0, 4)).intValue();
		int Month1 = new Integer(strSysDate.substring(4, 6)).intValue();
		int Day1 = new Integer(strSysDate.substring(6, 8)).intValue();

		int Year2 = new Integer(strQueryDate.substring(0, 4)).intValue();
		int Month2 = new Integer(strQueryDate.substring(4, 6)).intValue();
		int Day2 = new Integer(strQueryDate.substring(6, 8)).intValue();

		GregorianCalendar g1 = new GregorianCalendar(Year1, Month1 - 1, Day1);
		GregorianCalendar g2 = new GregorianCalendar(Year2, Month2 - 1, Day2);

		int iElapsed = 0;
		GregorianCalendar gc1, gc2;

		if (g2.after(g1)) {
			gc2 = (GregorianCalendar) g2.clone();
			gc1 = (GregorianCalendar) g1.clone();
		} else {
			gc2 = (GregorianCalendar) g1.clone();
			gc1 = (GregorianCalendar) g2.clone();
		}

		gc1.clear(Calendar.MILLISECOND);
		gc1.clear(Calendar.SECOND);
		gc1.clear(Calendar.MINUTE);
		gc1.clear(Calendar.HOUR_OF_DAY);

		gc2.clear(Calendar.MILLISECOND);
		gc2.clear(Calendar.SECOND);
		gc2.clear(Calendar.MINUTE);
		gc2.clear(Calendar.HOUR_OF_DAY);

		while (gc1.before(gc2)) {
			gc1.add(Calendar.DATE, 1);
			iElapsed++;
		}
		return iElapsed;
	}

	/**
	 * URL验证
	 * 
	 * @param strUrl
	 */
	public static boolean checkUrl(String strUrl) {
		boolean state = true;
		try {
			Pattern p = Pattern
					.compile("^((http|https|ftp):(\\/\\/|\\\\)(([a-z0-9-])+[.]){1,}([a-z0-9])((([a-z0-9])+)|[.]([a-z0-9])+)*(((([?]([a-z0-9])+){1}[=]*))*(([a-z0-9])+){1}([\\&]([a-z0-9])+[\\=]([a-z0-9])+)*)*)");
			Matcher m = p.matcher(strUrl);
			if (m.find()) {
				state = true;
			} else {
				state = false;
			}
			return state;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean checkstrUrl(String strUrl) {
		boolean state = true;
		try {
			if (strUrl == null || strUrl.equals("")) {
				return false;
			} else {
				for (int i = 0; i < strUrl.length(); i++) {
					if (strUrl.substring(i, i + 1).getBytes().length > 1) {
						state = false;
						return state;
					}
				}
				if (state) {
					char curl[] = strUrl.toCharArray();
					for (int i = 0; i < curl.length; i++) {
						int charsi = (int) curl[i];
						if (charsi >= 65 && charsi <= 90) {
							state = false;
							return state;
						}
					}

				}
			}
			return state;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 
	 * @param strDate
	 *            (yyyymmdd)
	 * @return String (yy/mm/dd)
	 */
	public static String fomartDate(String strDate) {
		String strTo = "";
		if (strDate != null && !strDate.equals("") && strDate.length() >= 8) {
			strTo = strDate.substring(2, 4) + "/";
			strTo += strDate.substring(4, 6) + "/";
			strTo += strDate.substring(6, 8);
		}
		return strTo;
	}

	/**
	 * 
	 * @param strDate
	 *            (yyyymmdd)
	 * @return String (yyyy/mm/dd)
	 */
	public static String fomartData(String strDate, int flag) {
		String strTo = "";
		String splitSign = "";
		switch (flag) {
		case 1:
			splitSign = "/";
			break;
		case 2:
			splitSign = "-";
			break;
		default:
			splitSign = "/";
			break;
		}
		if (strDate != null && !strDate.equals("") && strDate.length() >= 8) {
			String[] strDateArr = strDate.split(splitSign);
			strTo =  strDateArr[0].trim() + splitSign;
			strTo += strDateArr[1].trim() + splitSign;
			strTo += strDateArr[2].trim().substring(0,2);
		}
		return strTo;
	}

	/**
	 * 
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public static String toNumberString(long number) throws Exception {
		java.lang.String outString = new java.lang.String("");
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();

		try {
			nf.setMaximumFractionDigits(0);
			outString = nf.format(number);
		} catch (Exception e) {
			throw e;
		}
		return outString;
	}

	/**
	 * <code>java.util.Date</code>指定格式变换
	 * 
	 * @param format
	 *            格式
	 * @param currentTime
	 *            要求变换的时间
	 * @return 变换后时间
	 */
	public static String dateToString(String format, java.util.Date currentTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.getCalendar().setLenient(false);
		sdf.setLenient(false);
		return sdf.format(currentTime);
	}

	/**
	 * <code>java.util.Date</code>指定格式变换
	 * 
	 * @param format
	 *            格式
	 * @param currentTime
	 *            要求变换的时间
	 * @return 变换后时间
	 */
	public static String dateToString(String format, String currentTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = (Date)formatter.parse(currentTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateToString(format,date);
	}

	/**
	 * 用 yyyy-MM-dd hh:mm:ss 的格式进行格式化
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	public static String dateToString1(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.format(date);
	}

	public static Date stringToDate(String time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			return formatter.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 字符编码的转换
	 * 
	 * @param str
	 * @return
	 */
	public static String make8859toGB(String str) {
		try {
			String str8859 = new String(str.getBytes("8859_1"), "GB2312");
			return str8859;
		} catch (UnsupportedEncodingException ioe) {
			return str;
		}
	}

	/**
	 * checkAGE
	 * 
	 * @param String
	 *            age
	 * @return String checkedAge
	 */
	public static String getSql(String strSql) {
		if (strSql == null && strSql.equals("")) {
			strSql = "";
		} else {
			strSql.trim();
			strSql = strSql.replace("¥u301c", "¥uFF5E");
		}
		return strSql;
	}

	/**
	 * 字符串是否在数组中存在
	 * 
	 * @param value
	 * @param array
	 * @return
	 */
	public static boolean isExistInArray(String value, String[] array) {
		for (int i = 0; i < array.length; i++) {
			if (value.equals(nullToBlank(array[i]))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 随机生成String
	 * @return String
	 */
	public static String randRomsStrValue(){
		final char[] str = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		StringBuffer rtnStr = new StringBuffer();
		Random rd = new Random();
		int for_i = rd.nextInt(6);
		for(int i=0;i<=for_i;i++){
			if(i%2==0){
				rtnStr.append(str[rd.nextInt(25)]);
			}else{
				rtnStr.append(rd.nextInt(10));
			}
		}
		return rtnStr.toString();
	}

	/**  
	 * 删除input字符串中的html格式  
	 *   
	 * @param input  
	 * @param length  
	 * @return  
	 */  
	public static String splitAndFilterString(String input, int length) {   
		if (input == null || input.trim().equals("")) {   
			return "";   
		}   
		// 去掉所有html元素,   
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(   
				"<[^>]*>", "");   
		str = str.replaceAll("[(/>)<]", "");   
		int len = str.length();   
		if (len <= length) {   
			return str;   
		} else {   
			str = str.substring(0, length);   
			str += "......";   
		}   
		return str;   
	} 

	/**  
	 * 删除input字符串中的html格式  
	 *   
	 * @param input  
	 * @param length  
	 * @return  
	 */  
	public static String splitAndFilterString(String input) {   
		if (input == null || input.trim().equals("")) {   
			return "";   
		}   
		// 去掉所有html元素,   
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(   
				"<[^>]*>", "");   
		str = str.replaceAll("[(/>)<]", "");   
		return str;   
	} 

	/**
	 * 字符串是否在数组中存在

	 * 
	 * @param strVal1
	 * @param strVal2
	 * @return 0-正确 1-漏选 2-错误
	 */
	public static int isLeakChoose(String strVal1, String strVal2,String splitFlag) {
		int flag = 0;
		if(strVal2.length() > strVal1.length()){
			flag = 2;
		}else{
			if("".equals(strVal2.trim())){
				flag = 2;
			}
			if(strVal2.length() == strVal1.length()){
				if(strVal2.equals(strVal1)){
					flag = 0;
				}else{
					String[] str = split(strVal2, splitFlag);
					boolean isRight = true;
					for (int i = 0; i < str.length; i++) {
						if(!isExistInArray(str[i], split(strVal1, splitFlag))){
							isRight = false;
							flag = 2;
							break;
						}
					}
					if(isRight){
						flag = 0;
					}
				}
			}else{
				String[] str = split(strVal2, splitFlag);
				boolean isRight = true;
				for (int i = 0; i < str.length; i++) {
					if(!isExistInArray(str[i], split(strVal1, splitFlag))){
						isRight = false;
						break;
					}
				}
				if(isRight){
					flag = 1;
				}else{
					flag = 2;
				}
			}
		}
		return flag;
	}

	public static String getNewIp(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0
				|| "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0
				|| "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0
				|| "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/*
	 * 替换字符串中所指定的字符  
	 */
	/**
	 * @param from  字符串中所要替换的字符
	 * @param to	替换成的字符
	 * @param source 字符串
	 * @return
	 */
	public static String replace(String from, String to, String source) {   
		if (source == null || from == null || to == null)   
			return null;   
		StringBuffer bf = new StringBuffer("");   
		int index = -1;   
		while ((index = source.indexOf(from)) != -1) {   
			bf.append(source.substring(0, index) + to);   
			source = source.substring(index + from.length());   
			index = source.indexOf(from);   
		}   
		bf.append(source);   
		return bf.toString();   
	}

	public static String toString(Object obj){
		String rtnStr = "";
		if(obj != null){
			if (obj instanceof Integer) {
				int value = ((Integer) obj).intValue();
				rtnStr = String.valueOf(value);
			} else if (obj instanceof String) {
				rtnStr = nullToBlank((String) obj);
			} else if (obj instanceof Double) {
				double d = ((Double) obj).doubleValue();
				rtnStr = String.valueOf(d);
				if(d == ((Double) obj).intValue()){
					rtnStr = String.valueOf(((Double) obj).intValue());
				}
				if("NaN".equals(rtnStr)){
					rtnStr = "";
				}
			} else if (obj instanceof Float) {
				float f = ((Float) obj).floatValue();
				rtnStr = String.valueOf(f);
			} else if (obj instanceof Long) {
				long l = ((Long) obj).longValue();
				rtnStr = String.valueOf(l);
			} else if (obj instanceof Boolean) {
				boolean b = ((Boolean) obj).booleanValue();
				rtnStr = String.valueOf(b);
			} else if (obj instanceof Date) {
				Date d = (Date) obj;
				rtnStr = dateToString("yyyy-MM-dd", d);
			}  
		}
		return rtnStr;
	}


	public static String getValidStr (String tmpStr){
		StringBuffer outStr = new StringBuffer(); 
		for (int i = 0; i < tmpStr.length(); i++) {
			boolean flag = false;
			char a = tmpStr.charAt(i);
			if(isAlpha(a) || isDigit(a)){
				flag = true;
			}
			if(!flag && checkChinse(String.valueOf(a))){
				flag = true;
			}
			if(!flag && (String.valueOf(a).indexOf("/") != -1 || String.valueOf(a).indexOf("\\") != -1
					|| String.valueOf(a).indexOf("-") != -1) || String.valueOf(a).indexOf(":") != -1){
				flag = true;
			}
			if(flag){
				outStr.append(a);
			}
		}
		return outStr.toString();
	}
	/**
	 * 英文字符验证
	 * 
	 * @param c
	 *            字符
	 * @return 是英文字符<code>true</code>
	 */
	private static boolean isAlpha(char c) {
		return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
	}

	/**
	 * 数字验证
	 * 
	 * @param c
	 *            字符
	 * @return 是数字 <code>true</code>
	 */
	private static boolean isDigit(char c) {
		return (c >= '0' && c <= '9');
	}

	/**
	 * 中文验证
	 * 
	 * @param c
	 *            字符
	 * @return 是数字 <code>true</code>
	 */
	public static boolean checkChinse(String s){
		s=new String(s.getBytes());//用GBK编码
		String pattern="[\u4e00-\u9fa5]+";
		Pattern p=Pattern.compile(pattern);
		Matcher result=p.matcher(s);
		return result.find(); //是否含有中文字符
	}

	public static long getMd5Code(){
		Random   random   =   new   Random(); 
		long salt = 100000   +   random.nextInt(899999);
		return salt;
	}

	public static String encrypt(String strText)
	{

		if(strText != null && !strText.trim().isEmpty())
		{
			MessageDigest md;
			try {
				md = MessageDigest.getInstance("md5"); // 同样可以使用SHA1
				md.update(strText.getBytes());
				String pwd = new BigInteger(1, md.digest()).toString(16); //参数也可不只用16可改动,当然结果也不一样了
				return pwd;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return strText;
		}
		return strText;
	}

	public static String getIdentifyingcode() {
		// 该变量用来保存系统生成的随机字符串
		String sRand = "";
		for (int i = 0; i < 5; i++) {
			// 取得一个随机字符
			String tmp = randomNumber();
			sRand += tmp;
		}
		return sRand;
	}

	private static String randomNumber() {
		long number = 1;
		do{
			number = Math.round(Math.random() * 9);
		}while(number==1 || number==0);
		return String.valueOf(number);
	}

	public static Long getTimestamp(Date startTime, Date endTime,   
			String format, String str) {   
		// 按照传入的格式生成一个simpledateformate对象   
		SimpleDateFormat sd = new SimpleDateFormat(format);   
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数   
		long nh = 1000 * 60 * 60;// 一小时的毫秒数   
		long nm = 1000 * 60;// 一分钟的毫秒数   
		long ns = 1000;// 一秒钟的毫秒数   
		long diff;   
		long day = 0;   
		long hour = 0;   
		long min = 0;   
		long sec = 0;   
		// 获得两个时间的毫秒时间差异   
		try {   
			diff = endTime.getTime() - startTime.getTime();   
			day = diff / nd;// 计算差多少天   
			hour = diff % nd / nh + day * 24;// 计算差多少小时   
			min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟   
			sec = diff % nd % nh % nm / ns;// 计算差多少秒   
			// 输出结果   
			System.out.println("时间相差：" + day + "天" + (hour - day * 24) + "小时"  
					+ (min - day * 24 * 60) + "分钟" + sec + "秒。");   
			System.out.println("hour=" + hour + ",min=" + min);   
			if (str.equalsIgnoreCase("h")) {   
				return hour;   
			} else {   
				return min;   
			}   

		} catch (Exception e) {   
			// TODO Auto-generated catch block   
			e.printStackTrace();   
		}   
		if (str.equalsIgnoreCase("h")) {   
			return hour;   
		} else {   
			return min;   
		}   
	}

	public static Long getTimestamp(Date startTime, Date endTime) {   
		// 按照传入的格式生成一个simpledateformate对象   
		long diff; 
		long min = 0;   
		// 获得两个时间的毫秒时间差异   
		try {   
			diff = endTime.getTime() - startTime.getTime();   
			min = diff / 1000 / 60;// 计算差多少分钟   
		} catch (Exception e) {   
			// TODO Auto-generated catch block   
			e.printStackTrace();   
		}
		return min;
	}
	public static String encodeHexStr(final byte[] bytes){
		if(bytes == null){
			return null;
		}
		char[] result = new char[bytes.length * 2];
		for (int i = 0; i < bytes.length; i++) {
			result[i*2] = digital[(bytes[i] & 0xf0) >> 4];
			result[i*2 + 1] = digital[bytes[i] & 0x0f];
		}
		return new String(result);
	}
	public static byte[] toBytes(final String str) {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes(CHARSET_NAME_UTF8);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}

