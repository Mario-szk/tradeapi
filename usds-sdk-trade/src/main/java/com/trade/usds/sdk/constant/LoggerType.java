package com.trade.usds.sdk.constant;

/**
 * @Description 
 * 
 * @version 1.0
 */
public enum LoggerType {
	/**下单使用
	 * 
	 */
	ORDER("order"),
	/**
	 * 成交使用
	 */
	DEAL("deal"),
	/**任务使用
	 * 
	 */
	TASK("task"),
	/**错误使用
	 * 
	 */
	ERROR("error"),
	/**错误使用
	 * 
	 */
	API("api"),
	/**业务使用
	 * 
	 */
	BUSINESS("business"),
	/**
	 * 全部文件
	 */
	ALL("all"),

	/**
	 * 单独信息
	 */
	OTHER("other");

	private String value;

	private LoggerType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	/*
	 * order-yyyy-mm-dd.log deal-yyyy-mm-dd.log task-yyyy-mm-dd.log
	 * error-yyyy-mm-dd.log all-yyyy-mm-dd.log
	 */
}
