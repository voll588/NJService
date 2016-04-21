package nj.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class DateMapper extends ObjectMapper {
	private String mask = "MM dd yyyy HH mm ss";

	@PostConstruct
	public void afterPropertiesSet() {
		super.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);
		getSerializationConfig().setDateFormat(new SimpleDateFormat(mask));
	}
	
	/*@PostConstruct
	public void afterPropertiesSet() throws Exception {
		super.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
				false);
		getSerializationConfig().setDateFormat(new SimpleDateFormat(mask));
	}*/

	public void setMask(String mask) {
		this.mask = mask;
	}

	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String current_date = sdf.format(new Date());
		return current_date;
	}

	public static String getDate1() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String current_date = sdf.format(new Date());
		return current_date;
	}

	public static String getDate2() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String current_date = sdf.format(new Date());
		return current_date;
	}
	
	public static boolean dateDifference(String dateStr, long diffSecondsValue)
			throws Exception {
		if(dateStr == null){
			return false;
		}
		Date date = null;
		Date currentDate = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		date = df.parse(dateStr);
		currentDate = df.parse(df.format(new Date()));

		long diff = currentDate.getTime() - date.getTime();
		long secondsNum = diff / 1000;

		if (secondsNum > diffSecondsValue) {// 大于diffSecondsValue
			return true;
		}
		return false;
	}
}
