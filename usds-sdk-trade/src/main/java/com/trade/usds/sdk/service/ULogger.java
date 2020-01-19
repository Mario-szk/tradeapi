package com.trade.usds.sdk.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trade.usds.sdk.constant.LoggerType;

/**
 * @Description 
 * 
 * @version 1.0
 */
public  final class ULogger    {

	static Map<String,Logger> loggers=new  HashMap<String,Logger> ();
	
	/**获取logger
	 * @param logName
	 */
	private static  Logger getLogger(LoggerType logName){
		Logger l=loggers.get(logName.getValue());
		if(l==null){
			synchronized (ULogger.class) {
				if(l==null){
					l=LoggerFactory.getLogger(logName.getValue());
					loggers.put(logName.getValue(), l);
					
				} 
				  l=loggers.get(logName.getValue());
				 
			}
		}
		return l;
	}
	
	
	/**记录日志 <font style="color:red;font-size:14px;">error级别</font>
	 * @param logName
	 * @param message
	 */
	private static  void errorOriginal(LoggerType logName,String message,Throwable e){
		
		 Logger logger= getLogger(  logName);
//		 if(logName != LoggerType.DEAL){
//			 return;
//		 }
		if(e==null){
			logger.error(message );
		}
		else{
			logger.error(message, e);
		}
	}
	
	/**记录日志 <font style="color:red;font-size:14px;">error级别</font>
	 * @param logName
	 * @param message
	 * @param e
	 */
	public static  void error (LoggerType logName,String message,Throwable e){
		
		 errorOriginal(logName,message,e );
	}
	/**记录日志 <font style="color:red;font-size:14px;">error级别</font>
	 * @param logName
	 * @param message
	 */
	public static  void error(LoggerType logName,String message ){
		
		 	 errorOriginal(logName,message,null );
	 
	}
 

	/**记录日志 <font style="color:red;font-size:14px;">error级别</font>
	 * @param logName
	 * @param message
	 * @param e
	 */
	public static  void errorReport(LoggerType logName,String message,Throwable e){
		
	 
	 
		if(e!=null){
			errorOriginal(logName,message,e );
		}
		
		 
		UErrorService.submitError(e, message);
	}
	
	/**记录日志 <font style="color:red;font-size:14px;">error级别</font>
	 * @param logName
	 * @param message
	 */
	public static  void errorReport(LoggerType logName,String message ){
		errorOriginal(logName,message,null );
		try {
			errorReport(logName,message,null );
		} catch (Exception e) {
			e.printStackTrace();
		}
	 
	}
	
	/**记录日志<font style="color:red;font-size:14px;">info级别</font>
	 * @param logName
	 * @param message
	 * @param e
	 */
	private static  void infoOriginal(LoggerType logName,String message,Throwable e){
		
		 Logger logger= getLogger(  logName);
		if(e==null){
			
			logger.info(message );
			
		}
		else{
		 
			logger.info(message, e);
		}
	}
	/**记录日志<font style="color:red;font-size:14px;">info级别</font>
	 * @param logName
	 * @param message
	 * @param e
	 */
	public static  void info (LoggerType logName,String message,Throwable e){
		
		infoOriginal(  logName,  message,  e);
	}
	/**记录日志 <font style="color:red;font-size:14px;">info级别</font>
	 * @param logName
	 * @param message
	 */
	public static  void info(LoggerType logName,String message ){
		
		infoOriginal(  logName,  message,  null);
	 
	}
	
	/**记录日志<font style="color:red;font-size:14px;">warn级别</font>
	 * @param logName
	 * @param message
	 * @param e
	 */
	private static  void warnOriginal(LoggerType logName,String message,Throwable e){
		
		 Logger logger= getLogger(  logName);
		if(e==null){
			logger.warn(message );
		}
		else{
			logger.warn(message, e);
		 
		}
	}
	/**记录日志<font style="color:red;font-size:14px;">warn级别</font>
	 * @param logName
	 * @param message
	 * @param e
	 */
	public static  void warn(LoggerType logName,String message,Throwable e){
		
		 warnOriginal(logName,message,e );
	}
 
	/**记录日志 <font style="color:red;font-size:14px;">warn级别</font>
	 * @param logName
	 * @param message
	 */
	public static  void warn(LoggerType logName,String message ){
		
		 warnOriginal(logName,message,null );
	 
	}
	 
	/**记录日志<font style="color:red;font-size:14px;">warn级别</font>
	 * @param logName
	 * @param message
	 * @param e
	 */
	public static  void warnReport(LoggerType logName,String message,Throwable e){
		
		if(e!=null){
			warnOriginal(logName, message, e);
		}
		  
		UErrorService.submitWarn(e, message);
	}
	/**记录日志 <font style="color:red;font-size:14px;">warn级别</font>
	 * @param logName
	 * @param message
	 */
	public static  void warnReport(LoggerType logName,String message ){
		warnOriginal(logName, message, null);
		try {
			warnReport(logName,message,null );
		} catch (Exception e) {
			e.printStackTrace();
		}
	 
	}
}
