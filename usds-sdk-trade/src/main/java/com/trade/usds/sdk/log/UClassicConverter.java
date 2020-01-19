package com.trade.usds.sdk.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;

 
 
/**
 * @Description 获取类名称
 */
public class UClassicConverter  extends ClassicConverter {
	   /**获取触发的类信息  
     */
    private StackTraceElement getTriggerObject(ILoggingEvent le) {

        StackTraceElement[]  cda = le.getCallerData();
 
    
        if (cda != null && cda.length >2) {
            StackTraceElement  cda_temp = le.getCallerData()[2];
            return cda_temp;
        }
        else  if (cda != null && cda.length >0) {
            StackTraceElement  cda_temp = le.getCallerData()[0];
            return cda_temp;
        }
        else {
            return null;
        }
    }
   
    /**获取类名
     */
    private String getFullyQualifiedName( StackTraceElement ste) {
    
        if (ste != null  ) {
            	   String fileName = ste.getClassName()  ;

            return fileName;
        }
        
        else {
            return CallerData.NA;
        }
    }

     

    @Override
    public String convert(ILoggingEvent le) {
    	StackTraceElement ste= getTriggerObject(  le);
        //log所在的类的类名
        String fileName = getFullyQualifiedName(ste);
     
 
        return fileName  ;
    }
}
