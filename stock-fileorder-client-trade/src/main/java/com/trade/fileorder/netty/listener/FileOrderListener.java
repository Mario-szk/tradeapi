package com.trade.fileorder.netty.listener;

import java.util.List;

import com.trade.fileorder.netty.push.dto.AccountInfo;
import com.trade.fileorder.proto.FileOrderMessageProto.DealPackageResp;
import com.trade.fileorder.proto.FileOrderMessageProto.DealResp;
import com.trade.fileorder.proto.FileOrderMessageProto.FundInfoResp;
import com.trade.fileorder.proto.FileOrderMessageProto.OrderResp;
import com.trade.fileorder.proto.FileOrderMessageProto.PositionResp;

public interface FileOrderListener {
	/**
	 * 成交回报
	 * @param dealPackageResp
	 */
	void onRtnDealPackage(AccountInfo accountInfo,DealPackageResp dealPackageResp);
	
	/**
	 * 查询资金信息
	 * @param fundInfoResp
	 */
	void onQryFundInfo(AccountInfo accountInfo,FundInfoResp fundInfoResp);
	
	/**
	 * 查询持仓
	 * @param positionList
	 */
	void onQryPosition(AccountInfo accountInfo,List<PositionResp> positionList);
	
	/**
	 * 查询委托
	 * @param orderList
	 */
	void onQryOrder(AccountInfo accountInfo,List<OrderResp> orderList);
	
	/**
	 * 查询成交
	 * @param dealList
	 */
	void onQryDeal(AccountInfo accountInfo,List<DealResp> dealList);
	
//	/**
//	 * 断开了
//	 * @param accountInfo
//	 */
//	void onDisConnected(AccountInfo accountInfo);
}
