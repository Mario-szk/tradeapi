package com.trade.usds.sdk.constant;
/**
 * @Description 
 * 
 * @version 1.0
 */
public enum ErrorType {
error(1),warn(2)
	;
	private Integer value;

	private ErrorType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	 
	
}
