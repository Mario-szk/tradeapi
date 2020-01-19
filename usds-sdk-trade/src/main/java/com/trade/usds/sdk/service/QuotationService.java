package com.trade.usds.sdk.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.trade.usds.sdk.util.TradeTimeUtil;

/**
 * @Description  行情服务
 */
public class QuotationService {

	
 
	
	/**行情时间校验
	 * @param quotationTime
	 */
	public  static void check(Long quotationTime){
		long time=60000;
		if(TradeTimeUtil.inactivityTime()){
			//不活跃时间
			 time=time*60;
		} 
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		Long nowTime=System.currentTimeMillis();
		 if(quotationTime!=null){
			if((quotationTime+time)< nowTime){//一分钟都没有更新行情就会报错
				UErrorService.submitWarn (new Exception("当前时间："+df.format(  nowTime)+",您的行情很久没有更新，上一次行情时间："+df.format(quotationTime)));
			}
		 }
		 else{
				UErrorService.submitWarn(new Exception("当前时间："+df.format(nowTime)+",没有收到您的行情，当前时间："+quotationTime)) ;
		 }
	}
	
}
