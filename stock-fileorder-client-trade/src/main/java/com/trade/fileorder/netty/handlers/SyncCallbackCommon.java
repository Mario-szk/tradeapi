package com.trade.fileorder.netty.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.trade.fileorder.netty.push.dto.AccountInfo;
import com.trade.fileorder.netty.util.SyncKeyUtil;
import com.trade.fileorder.proto.FileOrderMessageProto.WCMessage;


/**
 * @Description 下单回调处理类
 * 
 */
public class SyncCallbackCommon {
	
	
	
	/**
	 * 存放接口的地方
	 */
	public static  Map<String,WCMessage>  map=new  HashMap<String,WCMessage> ();
	
	private static Map<String,String> setData=new HashMap<String,String> ();
	
	public final static Long timeOut=10000L;//10秒超时
	
	
	
	/**存放数据
	 * @param key -客户端生成的key
	 * @param obj
	 */
	public static void set(String key,WCMessage obj) {
		
 
			 map.put(key, obj);
 
		
	} 
	/**有返回true
	 * @param key
	 * @return
	 */
	public static boolean isHave(String key ) {
		
		  return map.containsKey(key);
	} 
	
 
	/**存放数据
	 * @param key -客户端生成的key
	 * @return
	 * @throws TimeoutException
	 * @throws JTraderException 
	 */
	public    static WCMessage syncGet(AccountInfo accountInfo,String key ) throws TimeoutException {
		//五秒超时
		
		synchronized ( SyncKeyUtil.getSyncCallBackKey(accountInfo)) {
			
		
		Long start=System.currentTimeMillis();
 		while(true){
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			WCMessage res=map.get(key);
		
			if(res!=null){
				map.remove(key);
//				if(StringUtils.isNotEmpty(res.getErrorMessage())){
//					if(res.getErrorCode()!=null && res.getErrorCode()>0){
//						
//						throw new JTraderException(res.getErrorCode(),res.getErrorTips());
//					}
//					 
//					throw new JTraderException(-500,res.getErrorMessage());
//				}
				
				return  res;
			}
			 
			 if((System.currentTimeMillis()-start)>timeOut){
				throw new TimeoutException("请求超时"+timeOut);
			} 
		}
		}
	} 
//	/**获取同步对象
//	 * @param tempSessionId
//	 * @return
//	 */
//	public  synchronized static String getSyncSessionId(String tempSessionId) {
//		 String sessionId=setData.get(tempSessionId);
//		 if(StringUtils.isEmpty(sessionId)){
//			 sessionId=tempSessionId;
//			 setData.put(tempSessionId, tempSessionId);
//			 
//		 }
//		
//		return sessionId;
//	}
	/**
	 * 清除数据
	 * @param sessionId 
	 */
	public static void clearData(String sessionId){ 
	 setData.remove(sessionId);
	}
	/**
	 * 清除数据
	 * @param sessionId 
	 */
	public static void clearData( ){ 
	 setData.clear();
	}
}
