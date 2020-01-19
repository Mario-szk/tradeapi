package com.trade.usds.sdk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
 
/**
 * @Description 
 * 
 */
public final class TradeTimeUtil {

 
 
 
 
	
	/**主账户操作时间
	 */
	public static boolean inactivityTime(){
		String nowTime = getNowTime();
		return    isInTime("00:00:00-02:31:00", nowTime)
				||isInTime("11:28:00-11:31:00", nowTime)
				||isInTime("14:55:00-15:01:00", nowTime) ;
	}
	
	/**主账户操作时间
	 */
	public static boolean masterConnectTime(){
		String nowTime = getNowTime();
 		
 		if( isInTime("08:50:00-11:30:00",nowTime)
 				||  isInTime("12:50:00-15:15:00",nowTime)
 				||  isInTime("20:50:00-23:59:59",nowTime)
 				||  isInTime("00:00:00-02:30:00",nowTime)){
			return true;
		}
 		return false;
	}
	
	
 	/** 
 	 * 获取获取下一分钟数据
 	 */
 	public static Date nextMinuteTime( Date date){
 
 		 
			return getMinuteTime(   date,1);
		 
 	}
 
 	
 	
  
 	
 	/**获取指定的分钟数 number 
 	 * @param date
 	 * @param number
 	 */
 	public static Date getMinuteTime ( Date date,Integer number){
 
 		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE,  number  );
		date = calendar.getTime();
	 
			return date;
		 
 	}
 
 
  
	
	/**
	 * 判断某一时间是否在一个区间内
	 * 
	 * @param sourceTime
	 *            时间区间,半闭合,如[10:00-20:00)
	 * @param curTime
	 *            需要判断的时间 如10:00
	 * @return 
	 * @throws IllegalArgumentException
	 */
	public static boolean isInTime(String sourceTime, String curTime) {
	    if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
	        throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
	    }
	    if (curTime == null || !curTime.contains(":")) {
	        throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
	    }
	    String[] args = sourceTime.split("-");
	    SimpleDateFormat sdf_hms  = new SimpleDateFormat("HH:mm:ss");
	    try {	 
	        long now = sdf_hms.parse(curTime).getTime();
	        long start = sdf_hms.parse(args[0]).getTime();
	        long end = sdf_hms.parse(args[1]).getTime();
	        if (args[1].equals("00:00:00")) {
	            args[1] = "24:00:00";
	        }
	        if (end < start) {
	            if (now >= end && now < start) {
	                return false;
	            } else {
	                return true;
	            }
	        } 
	        else {
	            if (now >= start && now <= end) {
	                return true;
	            } else {
	                return false;
	            }
	        }
	    } catch (ParseException e) {
	        e.printStackTrace();
	        throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
	    }

	}
	
	
	/**获取系统时间（如09:00:00）
 	 */
 	public static String getNowTime(){
 		 SimpleDateFormat sdf_hms  = new SimpleDateFormat("HH:mm:ss");
 		 return sdf_hms.format(new Date());
 		 
 	}
 	
 
 	
  
 	
 	
  
	
	
 
}
