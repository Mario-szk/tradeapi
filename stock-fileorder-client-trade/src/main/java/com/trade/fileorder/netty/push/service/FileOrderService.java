package com.trade.fileorder.netty.push.service;

import java.util.List;
import java.util.concurrent.TimeoutException;

import com.trade.fileorder.proto.FileOrderMessageProto.DealResp;
import com.trade.fileorder.proto.FileOrderMessageProto.FundInfoResp;
import com.trade.fileorder.proto.FileOrderMessageProto.OrderResp;
import com.trade.fileorder.proto.FileOrderMessageProto.PositionResp;

/**
 * 
 * @Description 向行情分发推送 分时 和 变化行情
 * 
 */
public interface FileOrderService {
	
	
	/**
	 * 	下单接口
	 * @param stockType	 类型 传 0
	 * @param exchange	 交易所    上交所 SH，深交所 SZ
	 * @param code		股票代码
	 * @param type		买卖类型    0 买入  ，1 卖出
	 * @param priceType	价格类型    传 2
	 * @param price		 价格
	 * @param entrustNumber		委托数目
	 * @param orderRef			委托标识  自增的数字  ，每天不能重复	
	 */
	void order(String stockType,String exchange,String code,String type,String priceType,String price,String entrustNumber,String orderRef);
	
	/**
	 * 撤单接口
	 * @param stockType			类型 传 0
	 * @param exchange			 交易所    上交所 SH，深交所 SZ
	 * @param code				股票代码
	 * @param type				买卖类型    0 买入  ，1 卖出
	 * @param priceType			价格类型    传 2
	 * @param price				价格
	 * @param entrustNumber		委托数目
	 * @param orderRef			委托单的下单标识
	 * @param contractNo		委托编号
	 */
	void cancelOrder(String stockType,String exchange,String code,String type,String priceType,String price,String entrustNumber,String orderRef,String contractNo);

	/**
	 * 获取账户信息
	 * @return
	 */
	void getFundInfo();
	
	/**
	 *	 查询持仓
	 */
	void getPositionList();
	
	/**
	 * 	查询成交
	 */
	void getDealList();
	
	/**
	 * 	查询委托
	 */
	void getOrderList();
	
	/**
	 * 	同步查询账户信息
	 * @return
	 * @throws TimeoutException
	 */
	FundInfoResp syncGetFundInfo() throws TimeoutException;
	
	/**
	 * 	同步查询持仓列表
	 * @return
	 * @throws TimeoutException
	 */
	List<PositionResp> syncGetPositionList() throws TimeoutException ;
	
	/**
	 * 	同步查询成交
	 * @return
	 * @throws TimeoutException
	 */
	List<DealResp> syncGetDealList() throws TimeoutException;
	
	/**
	 * 	同步查询委托
	 * @return
	 * @throws TimeoutException
	 */
	List<OrderResp> syncGetOrderList() throws TimeoutException;

}
