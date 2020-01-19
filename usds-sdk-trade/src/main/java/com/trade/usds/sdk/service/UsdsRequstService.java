package com.trade.usds.sdk.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.trade.usds.sdk.constant.CommonConstant;
import com.trade.usds.sdk.constant.LoggerType;
import com.trade.usds.sdk.dto.TServiceUjuit;
import com.trade.usds.sdk.util.HttpUtil;
import com.trade.usds.sdk.util.MACUtil;
import com.trade.usds.sdk.util.TimeVerifyUtil;
import com.trade.usds.sdk.util.ULogRes;

/**
 * @Description 
 * 
 */
public final class UsdsRequstService {

	final static String mac =MACUtil.getMAC();
	final static Logger log=Logger.getLogger (UsdsRequstService.class.getName());
	static TServiceUjuit serv=null;
	public static Integer serviceChildId;
	public static boolean isTure=new Boolean(ULogRes.getProperties("disable"));
	private static Thread mainThread = Thread.currentThread();
	static ScheduledExecutorService service = Executors   .newSingleThreadScheduledExecutor();  
	/**
	 * 初始化数据
	 */
	public static void  init(){
		ULogger.error(LoggerType.ERROR, "开始初始化");
		mainThread=Thread.currentThread();
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				ULogger.errorReport(LoggerType.ERROR,
						"线程名称"+t.getName() 
								+ " 线程ID"+t.getId() ,e);
				
			}
		});
		new Thread(
				new Runnable(){

					@Override
					public void run() {
						ULogger.error(LoggerType.ERROR, "开始初始化---启动线程");
						executeInit();
						ULogger.error(LoggerType.ERROR, "开始初始化---完成");
					}
					
				}
				).start();
		
		
	}
	
	/**执行初始化操作
	 */
	protected static void  executeInit(){
		if(!isTure){
			ULogger.error(LoggerType.ERROR, "不进行初始化---isTure="+isTure);
			return ;
		}
	//	ULogger.error(LoggerType.ERROR, "开始初始化---请求服务器");
		 serv= startService( );
	//		ULogger.error(LoggerType.ERROR, "开始初始化---请求服务器完成");
		 Runnable runnable = new Runnable() {  
	            public void run() {  
	             
	            	//log.log(Level.ALL, ("++++++++++++++++++++++订阅超时"+entry.getKey()+" !  ++++++++++++++++++++++");
	            	 heart( ) ;
	             
	            }  
	        };  
	       
	        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
	     //   service.scheduleAtFixedRate(runnable, serv.getOuttime(),  serv.getOuttime(), TimeUnit.MINUTES); 
	        service.scheduleAtFixedRate(runnable, 0,  2, TimeUnit.MINUTES); 

	        serviceChildId=serv.getServiceChildId();
	        heart( ) ;
		 
	}
	private UsdsRequstService(){
		
	}
   
    /**开始启动 
     */
    public static TServiceUjuit startService( )  {
        try {
           
 
            String res = HttpUtil.post(CommonConstant.getBaseUrl() + CommonConstant.INIT_SERVICE+"?appKey="+CommonConstant.getAppKey() +
            		"&mac="+mac+"&serviceChildId="+serviceChildId+"&path="+ URLEncoder.encode(ULogRes.getabsPath(), "UTF-8")+"&port="+ULogRes.getProperties("port"),
                     "UTF-8", "application/octet-stream");
            JSONObject response = JSONObject.parseObject(res, JSONObject.class);
            if (!response.getBoolean("success")) {
                throw new IOException("提交异常失败: " + res);
            }
            Object result= response.get("data");
           
            
            return JSON.parseObject (result.toString(), TServiceUjuit.class);
	 
        } catch (Exception exception) {
        	// UErrorService.submitError(exception);
        	exception.printStackTrace();
			try {
				log.log(Level.WARNING, CommonConstant.getBaseUrl() + CommonConstant.INIT_SERVICE+"?appKey="+CommonConstant.getAppKey() +
						"&mac="+mac+"&serviceChildId="+serviceChildId+"&path="+  URLEncoder.encode(ULogRes.getabsPath(), "UTF-8")+
						"&port="+ULogRes.getProperties("port")+"心跳失败"+exception.getMessage());
			} catch (Error | UnsupportedEncodingException e) {
			 
				ULogger.error(LoggerType.ERROR, "错误",e);
			}
            throw new RuntimeException("您没注册应用或服务器,没有获取到您的数据"+exception.getMessage());
        }
     
    }
    
    /**心跳
     * @param appKey
     * @date 2018年4月18日
     */
    public static void heart( )  {
        try {
            if( serviceChildId==null){
            	 executeInit();
            }
            if(mainThread!=null && !mainThread.isAlive()
            	   
            		){
            	if(serv.getList()!=null &&serv.getList().size()>0
            			&& !TimeVerifyUtil.isDealTime(serv.getList())
            			){
            		ULogger.error(LoggerType.ERROR, "--------------准备开始结束--------------");
            	service.shutdown();
            	 ULogger.error(LoggerType.ERROR,  "---------------结束---------------");
            	return;
            	}
            
            }
 
            
            String res = HttpUtil.post(CommonConstant.getBaseUrl()  + CommonConstant.INIT_HEART+"?appKey=" + URLEncoder.encode(CommonConstant.getAppKey(), "UTF-8")+"&serviceChildId="+serviceChildId,
                     "UTF-8", "application/octet-stream");
            JSONObject response = JSONObject.parseObject(res, JSONObject.class);
            if (!response.getBoolean("success")) {
                throw new IOException("提交异常失败: " + res);
            }
        } catch (Exception exception) {
        	 
        
            try {
				String res = HttpUtil.post(CommonConstant.getBaseUrl()  + CommonConstant.INIT_HEART+"?appKey=" + URLEncoder.encode(CommonConstant.getAppKey(), "UTF-8")+"&serviceChildId="+serviceChildId,
				        "UTF-8", "application/octet-stream");
         JSONObject response = JSONObject.parseObject(res, JSONObject.class);
         if (!response.getBoolean("success")) {
				   throw new IOException("提交异常失败: " + res);
         }
			} catch ( Exception e) {
				ULogger.error(LoggerType.ERROR,  "请求心跳错误",e);
				  
				 //UErrorService.submitError(exception);
			}
        }
    }
    
    /**任务自检
     * @param isSuccess true代表是成功 false 代表失败
     */
    public static void taskInspect(boolean isSuccess ){
    	taskInspect(  isSuccess,null   );
    }
    
    /**任务自检
     * @param isSuccess true代表是成功 false 代表失败
     * @param notes 一个备注，可以为空
     */
    public static void taskInspect(boolean isSuccess,String notes )  {
        try {
            if( serviceChildId==null){
            	 executeInit();
            }
            
            int k=isSuccess?0:1;
            
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("actionStatus", k);
            jsonObject.put("notes", notes);
            
            String res = HttpUtil.post(CommonConstant.getBaseUrl()  + CommonConstant.CHECK_COLLECT+"?appKey=" + URLEncoder.encode(CommonConstant.getAppKey(), "UTF-8")+"&serviceChildId="+serviceChildId,
            	      jsonObject.toString().getBytes("UTF-8"), "UTF-8", "application/octet-stream");
            JSONObject response = JSONObject.parseObject(res, JSONObject.class);
            if (!response.getBoolean("success")) {
                throw new IOException("提交异常失败: " + res);
            }
        } catch (Exception exception) {
        	exception.printStackTrace();
        	
           
        }
    }
    
    
    
}
