package com.trade.usds.sdk.constant;

import com.trade.usds.sdk.util.ULogRes;

/**
 * @Description 
 * 
 * @version 1.0
 */
public final class CommonConstant {

	/**
	 * 提交异常连接
	 */
	public static final   String SUBMIT_ERROR="api/error/submitException.m";
	
	/**
	 * 初始化服务
	 */
	public static final   String INIT_SERVICE="api/service/initService.m";
	
	/**
	 * 初始化服务
	 */
	public static final   String INIT_HEART="api/service/heart.m";
	
	/**
	 * 初始化服务
	 */
	public static final   String CHECK_COLLECT="api/beforeCheck/collect.m";
    private static String baseUrl;
    private static String appKey;
	static{
		  baseUrl = ULogRes.getProperties("host");
	      appKey = ULogRes.getProperties("appkey");
	}
	/**获取基本连接
	 */
	public static String getBaseUrl() {
		return baseUrl;
	}
	/**APP_KEY
	 */
	public static String getAppKey() {
		return appKey;
	}

	

	
}
