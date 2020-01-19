package com.trade.usds.sdk.service;

 
 
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
 








import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.trade.usds.sdk.constant.CommonConstant;
import com.trade.usds.sdk.constant.ErrorType;
import com.trade.usds.sdk.util.HttpUtil;

 
 
/**
 */
public final class UErrorService {


	private static Logger logger=LoggerFactory.getLogger( UErrorService.class);

	private UErrorService(){
		
	}
    /**提交错误异常
     */
    public static void info(Throwable e)  {
    	logger.info("记录info日志",e);
    }
    /**不会提交
     */
    public static void submitInfo(Throwable e) {
    	logger.info("记录info日志",e);
    }
    /**提交错误异常
     */
    public static void submitError(Throwable e)  {
    	try {
			if(e!=null)
			submit(  e,ErrorType .error);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    }
    /**提交错误异常
     */
    public static void submitError(Throwable e,String message)  {
    	try {
			if(e!=null)
			submit(  e,ErrorType .error);
			else if(message!=null){
				submit(  new Throwable(message),ErrorType .error,message);
			}
		} catch (Exception e1) {
			 
			e1.printStackTrace();
		}
    }
    /**提交警告异常
     */
    public static void submitWarn(Throwable e)  {
    	try {
			if(e!=null)
			submit(  e,ErrorType .warn);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    }
    /**提交警告异常
     */
    public static void submitWarn(Throwable e,String message)  {
    	try {
			if(e!=null)
			submit(  e,ErrorType .warn,message);
			else if(message!=null){
				submit(  new Throwable(message),ErrorType .warn,message);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    }
    /**提交异常
     */
    public static void submit(Throwable e,ErrorType type)  {
    	try {
			if(e!=null)
			submit(  e,  type,null) ;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    }
    /**提交异常
     */
    public static void submit(Throwable e,ErrorType type,String message)  {
		if(!UsdsRequstService.isTure){
			return ;
		}
        try {
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer, true));
            String stackTrace = writer.toString();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("details", stackTrace);
            jsonObject.put("exceptionName", e.getClass().getName());
            if(message==null || "".equals(message)){
            	 jsonObject.put("message", e.getMessage());
            }
            else{
            	 jsonObject.put("message", message);
            }
           
            if(e.getStackTrace() != null && e.getStackTrace().length > 0) {
                StackTraceElement element = e.getStackTrace()[0];
                jsonObject.put("errorClass", element.getClassName());
                jsonObject.put("fileName", element.getFileName());
                jsonObject.put("methodName", element.getMethodName());
                jsonObject.put("errorLine", element.getLineNumber());
                jsonObject.put("status", type.getValue());//异常
            }
            else{
                jsonObject.put("errorClass", "未知异常");
                jsonObject.put("fileName", "无");
                jsonObject.put("methodName", "无");
                jsonObject.put("errorLine",0);
                jsonObject.put("status", "1");//异常
            }
            if(UsdsRequstService.serviceChildId==null){
            	UsdsRequstService.executeInit();
            }
            String res = HttpUtil.post(CommonConstant.getBaseUrl() + CommonConstant.SUBMIT_ERROR+ "?serviceChildId="+UsdsRequstService.serviceChildId+"&appKey=" + URLEncoder.encode(CommonConstant.getAppKey(), "UTF-8"),
                    jsonObject.toString().getBytes("UTF-8"), "UTF-8", "application/octet-stream");
            JSONObject response = JSONObject.parseObject(res, JSONObject.class);
//            logger.error("异常错误",e);
            if (!response.getBoolean("success")) {
                throw new IOException("提交异常失败: " + res);
            }
        } catch (Exception exception) {
        	logger.error("错误"+exception.getMessage());
        }
    }
    /**提交警告
     * @param details -详细信息
     * @param message-信息
     * @param errorClass-错误class
     * @param fileName-文件名
     * @param methodName-方法名
     * @param errorLine-错误行数
     */
    public static void submitWarn(String details,String message,
    		String errorClass,String fileName,String methodName,int errorLine)  {
        try {
  
            JSONObject jsonObject = new JSONObject();
                jsonObject.put("details", details);
                jsonObject.put("message", message);
                jsonObject.put("errorClass", errorClass);
                jsonObject.put("fileName", fileName);
                jsonObject.put("methodName", methodName);
                jsonObject.put("errorLine", errorLine);
                jsonObject.put("status", 1);//警告
           
                if(UsdsRequstService.serviceChildId==null){
                	UsdsRequstService.executeInit();
                }
                
            String res = HttpUtil.post(CommonConstant.getBaseUrl()  + CommonConstant.SUBMIT_ERROR+ "?serviceChildId="+UsdsRequstService.serviceChildId+"&appKey=" + URLEncoder.encode(CommonConstant.getAppKey(), "UTF-8"),
                    jsonObject.toString().getBytes("UTF-8"), "UTF-8", "application/octet-stream");
            JSONObject response = JSONObject.parseObject(res, JSONObject.class);
          //  logger.warn("警告信息--->"+message);
          
            if (!response.getBoolean("success")) {
                throw new IOException("提交异常失败: " + res);
            }
        } catch (Exception exception) {
            logger.warn ("错误"+exception.getMessage());
        }
    }
}
