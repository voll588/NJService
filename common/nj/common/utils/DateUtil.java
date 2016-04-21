package nj.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

public class DateUtil {
	
	/**
	 * 获取今天的日期
	 * @return
	 */
	public static String getday() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new Date());
	}
	
	/**
	 * 从当前月的最后一天向后加N个月
	 * @return
	 */
	public static String plusMonths(DateTime datetime,int num) {
		DateTime mDate = datetime.dayOfMonth().withMaximumValue();
		DateTime endDate =mDate.plusMonths(num);
		return endDate.toString("yyyy-MM-dd");
	}
		
	/**
	 * 把字符型的时间转化为date对象
	 * @return
	 * @throws ParseException 
	 */
	public static Date strToDate(String strdate) throws ParseException {
		
		DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = fmt.parse(strdate);
		return date;
	}
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String args[]) throws ParseException{
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		DateTime datetime =  new DateTime(DateUtil.getday());
//		DateTime datetime1 =  new DateTime(formatter.parse("2016-03-10"));
//
//		System.out.println(datetime.isAfter(datetime1));
//		System.out.println(datetime.isBefore(datetime1));
//		System.out.println(datetime.equals(datetime1));
		
//		String str = StringUtils.toStringAdd("56","56".length()+8);
		System.out.println(new DateTime().getYear());
		
	}
	
}

