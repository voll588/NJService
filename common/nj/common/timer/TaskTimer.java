package nj.common.timer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nj.api.bs.ServiceBS;
import nj.api.dao.UserDAO;


@Component
public class TaskTimer {

	private final int overtime = 3;//超时时间，单位（分钟）
	
	private final int overtime1 = 10;//超时时间，单位（分钟） 用于短信失效时间
	
	@Autowired
	private ServiceBS serviceBS;

	/**
	 * 扫描过期的任务
	 */
	public void taskHandle() {
		System.out.println("*************************************************定时任务开始****************************************");
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		String date = df.format(new Date());
//		Map dayMap = new HashMap();
//		dayMap.put("endTime", date+" 23:59:59");
//		dayMap.put("startTime", date+" 00:00:00");
//		dayMap.put("registDate", date);
//		Long count=0l;
		arrearageTask();
		System.out.println("*************************************************定时任务结束****************************************");
	}

	/**
	 * 欠费通知发送
	 * 
	 */
	public void arrearageTask(){
		
		//保教费检测\餐费检测\兴趣班费检测
		
		try {
			serviceBS.checkArrearageTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	public String timeStamp() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -this.overtime);
		Date d1=new Date(cal.getTimeInMillis());
		return df.format(d1);
	}
	
	public String timeStamp1() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -this.overtime1);
		Date d1=new Date(cal.getTimeInMillis());
		return df.format(d1);
	}
}
