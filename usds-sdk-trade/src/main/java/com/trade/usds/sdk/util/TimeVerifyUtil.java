package com.trade.usds.sdk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trade.usds.sdk.dto.TServiceSlot;

/**
 * @Description 
 * 
 * @version 1.0
 */
public final class TimeVerifyUtil {

	
	static Logger logger=LoggerFactory.getLogger(TimeVerifyUtil.class);
	
	private static	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式

	private static Date startTime=null;
 	
 	
 	private static Date yesterdayStartTime=null;
 	private static Date yesterdayEndTime=null;
 	private static String yesterDay;
 	
 	
 	private static boolean isopen=false;
 
	/**获取系统时间（如09:00:00）
 	 * @return
 	 */
 	public static String getNowTime(){
 		
 		 return df.format(new Date());
 		 
 	}
	
	/**true 代表在交易时间内，如(早上9:30 - 11:30  下午1:00- 3:00)
 	 * false 代表没有在交易时间内
 	 */
 	public static boolean isDealTime(List<TServiceSlot> list){
 		
 		if(list==null || list.size()==0)return true;
 		
 		String nowTime= getNowTime();
 		
 		for (TServiceSlot slot : list) {
 			
 			if(slot.getStartTime()==null || slot.getEndTime()	==null){
 				return false;
 			}
 			
			StringBuffer sb=new StringBuffer();
			sb.append(slot.getStartTime());
			sb.append("-");
			sb.append(slot.getEndTime());
 			if( isInTime(sb.toString(),nowTime)){
 				return true;
 			}
 			
		}
 		 
 		return false;
 	}
	/**
	 * @param time
	 * @param num
	 */
	public static Date setMinute(Date time, int num) {
 
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int minute = c.get(Calendar.MINUTE);
		c.set(Calendar.MINUTE, minute + num);
		return c.getTime();
	}
	
	/**自检不活跃的时间段
	 */
	public static boolean isNotActiveTime(){
		return isInTime("03:55:00-04:30:00", getNowTime());
	}
	  public static void main(String[] args) {
		System.out.println( isNotActiveTime());
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
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    try {
	        long now = sdf.parse(curTime).getTime();
	        long start = sdf.parse(args[0]).getTime();
	        long end = sdf.parse(args[1]).getTime();
	        if (args[1].equals("00:00:00")) {
	            args[1] = "23:59:59";
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
			logger.error(e.getMessage(),e);
			throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
	    }

	}

	/** 
	 * @return
	 */
	public static Date getStartTime() {
		return startTime;
	}

	/**true代表开盘
	 * @return
	 */
	public static boolean isOpen() {
	 
		return isopen;
	}
	 

	/**昨天开始时间
	 * @return
	 */
	public static Date getYesterdayStartTime() {
		return yesterdayStartTime;
	}

	/**昨天结束时间
	 * @return
	 */
	public static Date getYesterdayEndTime() {
		return yesterdayEndTime;
	}

	/**昨天日期 年月日
	 * @return
	 */
	public static String getYesterDay() {
		return yesterDay;
	}
	
	
}
