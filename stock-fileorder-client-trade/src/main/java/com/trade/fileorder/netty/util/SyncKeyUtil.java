package com.trade.fileorder.netty.util;

import java.util.HashMap;
import java.util.Map;

import com.trade.fileorder.netty.push.dto.AccountInfo;

public class SyncKeyUtil {

	static Map<String, String> syncKeyMap = new HashMap<String, String>();

	public static String getSyncKey(AccountInfo accountInfo){
		synchronized (syncKeyMap) {
			String uniqueKey = accountInfo.getUniqueKey();
			if(syncKeyMap.containsKey(uniqueKey)){
				return syncKeyMap.get(uniqueKey);
			}else{
				syncKeyMap.put(uniqueKey, new String(uniqueKey));
				return syncKeyMap.get(uniqueKey);
			}
		}
	}
	
	public static String getSyncKey(String key){
		synchronized (syncKeyMap) {
			String uniqueKey = key;
			if(syncKeyMap.containsKey(uniqueKey)){
				return syncKeyMap.get(uniqueKey);
			}else{
				syncKeyMap.put(uniqueKey, new String(uniqueKey));
				return syncKeyMap.get(uniqueKey);
			}
		}
	}
	
	static Map<String, String> syncCallBackKeyMap = new HashMap<String, String>();

	public static String getSyncCallBackKey(AccountInfo accountInfo){
		synchronized (syncCallBackKeyMap) {
			String uniqueKey = accountInfo.getUniqueKey();
			if(syncCallBackKeyMap.containsKey(uniqueKey)){
				return syncCallBackKeyMap.get(uniqueKey);
			}else{
				syncCallBackKeyMap.put(uniqueKey, new String(uniqueKey));
				return syncKeyMap.get(uniqueKey);
			}
		}
	}
	
}
