package com.trade.fileorder.netty.push.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;

import com.trade.fileorder.netty.client.NettyClient;
import com.trade.fileorder.netty.handlers.SyncCallbackCommon;
import com.trade.fileorder.netty.push.service.FileOrderService;
import com.trade.fileorder.proto.FileOrderMessageProto.CancelOrderReq;
import com.trade.fileorder.proto.FileOrderMessageProto.DealResp;
import com.trade.fileorder.proto.FileOrderMessageProto.FundInfoResp;
import com.trade.fileorder.proto.FileOrderMessageProto.Header;
import com.trade.fileorder.proto.FileOrderMessageProto.Header.CallBackType;
import com.trade.fileorder.proto.FileOrderMessageProto.Header.Type;
import com.trade.fileorder.proto.FileOrderMessageProto.OrderReq;
import com.trade.fileorder.proto.FileOrderMessageProto.OrderResp;
import com.trade.fileorder.proto.FileOrderMessageProto.PositionResp;
import com.trade.fileorder.proto.FileOrderMessageProto.ReqMessage;
import com.trade.fileorder.proto.FileOrderMessageProto.ReqMsgType;
import com.trade.fileorder.proto.FileOrderMessageProto.WCMessage;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author pdy
 */
public class FileOrderServiceImpl implements FileOrderService {

	private ChannelHandlerContext ctx;
	
	NettyClient nettyClient;
	

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	
	public NettyClient getNettyClient() {
		return nettyClient;
	}

	public FileOrderServiceImpl(NettyClient nettyClient) {
		this.nettyClient = nettyClient;
	}

	@Override
	public void order(String stockType, String exchange, String code, String type, String priceType, String price,
			String entrustNumber, String orderRef) {
		ReqMsgType reqMsgType = ReqMsgType.Order;
		OrderReq.Builder orderReq = OrderReq.newBuilder();
		orderReq.setStockType("0");
		orderReq.setExchangeCode(exchange);
		orderReq.setStockCode(code);
		orderReq.setType(type);
		orderReq.setPriceType("2");
		orderReq.setPrice(price);
		orderReq.setEntrustNumber(entrustNumber);
		orderReq.setOrderRef(orderRef);
		
		ReqMessage.Builder reqMessage = ReqMessage.newBuilder();
		reqMessage.setOrderReq(orderReq);
		reqMessage.setType(reqMsgType);
		WCMessage.Builder message = build(reqMessage, false);
		ctx.writeAndFlush(message.build());
	}

	@Override
	public void cancelOrder(String stockType, String exchange, String code, String type, String priceType, String price,
			String entrustNumber, String orderRef,String contractNo) {
		ReqMsgType reqMsgType = ReqMsgType.CancelOrder;
		CancelOrderReq.Builder orderReq = CancelOrderReq.newBuilder();
		orderReq.setStockType("0");
		orderReq.setExchangeCode(exchange);
		orderReq.setStockCode(code);
		orderReq.setType(type);
		orderReq.setPriceType("2");
		orderReq.setPrice(price);
		orderReq.setEntrustNumber(entrustNumber);
		orderReq.setOrderRef(orderRef);
		orderReq.setContractNo(StringUtils.isEmpty(contractNo)?"":contractNo);
		
		ReqMessage.Builder reqMessage = ReqMessage.newBuilder();
		reqMessage.setCancelOrderReq(orderReq);
		reqMessage.setType(reqMsgType);
		WCMessage.Builder message = build(reqMessage, false);
		ctx.writeAndFlush(message.build());
	}

	@Override
	public void getFundInfo() {
		ReqMessage.Builder reqMessage = ReqMessage.newBuilder();
		reqMessage.setType(ReqMsgType.QueryFundinfo);
		WCMessage.Builder message = build(reqMessage, false);
		ctx.writeAndFlush(message.build());
	}

	@Override
	public void getPositionList() {
		ReqMessage.Builder reqMessage = ReqMessage.newBuilder();
		reqMessage.setType(ReqMsgType.QueryPosition);
		WCMessage.Builder message = build(reqMessage, false);
		ctx.writeAndFlush(message.build());
	}

	@Override
	public void getDealList() {
		ReqMessage.Builder reqMessage = ReqMessage.newBuilder();
		reqMessage.setType(ReqMsgType.QueryDeal);
		WCMessage.Builder message = build(reqMessage, false);
		ctx.writeAndFlush(message.build());
	}

	@Override
	public void getOrderList() {
		ReqMessage.Builder reqMessage = ReqMessage.newBuilder();
		reqMessage.setType(ReqMsgType.QueryOrder);
		WCMessage.Builder message = build(reqMessage, false);
		ctx.writeAndFlush(message.build());
	}

	@Override
	public FundInfoResp syncGetFundInfo() throws TimeoutException {
		ReqMessage.Builder reqMessage = ReqMessage.newBuilder();
		reqMessage.setType(ReqMsgType.QueryFundinfo);
		WCMessage.Builder message = build(reqMessage, true);
		ctx.writeAndFlush(message.build());
		String uuid = message.getHeader().getUuid();
		WCMessage resp = SyncCallbackCommon.syncGet(nettyClient.getAccountInfo(), uuid);
		return resp.getRespMessage().getFundInfoResp();
	}

	@Override
	public List<PositionResp> syncGetPositionList() throws TimeoutException {
		ReqMessage.Builder reqMessage = ReqMessage.newBuilder();
		reqMessage.setType(ReqMsgType.QueryPosition);
		WCMessage.Builder message = build(reqMessage, true);
		ctx.writeAndFlush(message.build());
		String uuid = message.getHeader().getUuid();
		WCMessage resp = SyncCallbackCommon.syncGet(nettyClient.getAccountInfo(), uuid);
		return resp.getRespMessage().getPositionRespList();
	}

	@Override
	public List<DealResp> syncGetDealList() throws TimeoutException {
		ReqMessage.Builder reqMessage = ReqMessage.newBuilder();
		reqMessage.setType(ReqMsgType.QueryDeal);
		WCMessage.Builder message = build(reqMessage, true);
		ctx.writeAndFlush(message.build());
		String uuid = message.getHeader().getUuid();
		WCMessage resp = SyncCallbackCommon.syncGet(nettyClient.getAccountInfo(), uuid);
		return resp.getRespMessage().getDealRespList();
	}

	@Override
	public List<OrderResp> syncGetOrderList() throws TimeoutException {
		ReqMessage.Builder reqMessage = ReqMessage.newBuilder();
		reqMessage.setType(ReqMsgType.QueryOrder);
		WCMessage.Builder message = build(reqMessage, true);
		ctx.writeAndFlush(message.build());
		String uuid = message.getHeader().getUuid();
		WCMessage resp = SyncCallbackCommon.syncGet(nettyClient.getAccountInfo(), uuid);
		return resp.getRespMessage().getOrderRespList();
	}

	WCMessage.Builder build(ReqMessage.Builder reqMessage,boolean sync){
		WCMessage.Builder wcMessage = WCMessage.newBuilder();
		Header.Builder header = Header.newBuilder();
		header.setCallbackType(sync?CallBackType.SYNC:CallBackType.ASYNC);
		header.setType(Type.SERVICE_REQ);
		header.setUuid(UUID.randomUUID().toString());
		wcMessage.setHeader(header);
		wcMessage.setReqMessage(reqMessage);
		return wcMessage;
	}
	
}
