package com.trade.fileorder.netty.handlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.trade.fileorder.netty.client.NettyClient;
import com.trade.fileorder.netty.listener.FileOrderListener;
import com.trade.fileorder.proto.FileOrderMessageProto.Header;
import com.trade.fileorder.proto.FileOrderMessageProto.Header.CallBackType;
import com.trade.fileorder.proto.FileOrderMessageProto.ReqMsgType;
import com.trade.fileorder.proto.FileOrderMessageProto.RespMessage;
import com.trade.fileorder.proto.FileOrderMessageProto.WCMessage;
import com.trade.usds.sdk.constant.LoggerType;
import com.trade.usds.sdk.service.ULogger;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 
 * @version 1.0
 */
@Sharable
public class ClientBussinessHandler extends ChannelInboundHandlerAdapter {

	// 回调
	private FileOrderListener cusListener;
	NettyClient nettyClient;
	
	public ClientBussinessHandler(NettyClient nettyClient, FileOrderListener cusListener) {
		super();
		this.cusListener = cusListener;
		this.nettyClient = nettyClient;
	}

	Log log = LogFactory.getLog(ClientBussinessHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		WCMessage message = (WCMessage) msg;
		if (message.getHeader().getType().getNumber() == Header.Type.SERVICE_RESP_VALUE) {
			
			if(message.getHeader().getCallbackType() == CallBackType.SYNC){
				SyncCallbackCommon.set(message.getHeader().getUuid(), message);
			}
			// 如果返回的信息为业务消息
			if (message.getRespMessage() != null ) {
				RespMessage respMessage = message.getRespMessage();
				//成交
				// 此时已经建立观察者 此处实现自动重连订阅
				// (回调接口的登陆成功)
				if(message.getRespMessage().getType()== ReqMsgType.Deal){
					if(cusListener!=null){
						cusListener.onRtnDealPackage(nettyClient.getAccountInfo(),respMessage.getDealPackageResp());
					}
				}else if(message.getRespMessage().getType()== ReqMsgType.QueryFundinfo){
					if(cusListener!=null){
						cusListener.onQryFundInfo(nettyClient.getAccountInfo(),respMessage.getFundInfoResp());
					}
				}
				else if(message.getRespMessage().getType()== ReqMsgType.QueryPosition){
					if(cusListener!=null){
						cusListener.onQryPosition(nettyClient.getAccountInfo(),respMessage.getPositionRespList());
					}
				}
				else if(message.getRespMessage().getType()== ReqMsgType.QueryOrder){
					if(cusListener!=null){
						cusListener.onQryOrder(nettyClient.getAccountInfo(),respMessage.getOrderRespList());
					}
				}
				else if(message.getRespMessage().getType()== ReqMsgType.QueryDeal){
					if(cusListener!=null){
						cusListener.onQryDeal(nettyClient.getAccountInfo(),respMessage.getDealRespList());
					}
				}
			} 
		}
		
	}


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ctx.close();
		super.channelInactive(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleState state = ((IdleStateEvent) evt).state();
			if (state == IdleState.READER_IDLE) {
				ULogger.error(LoggerType.ERROR, "消息读取超时，断开连接...");
				ctx.close();
			}
		}
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

//	/**
//	 * 行情回调
//	 * 
//	 * @param pDepthMarketData
//	 */
//	private void onRtnSignalData(PushSignalMessageToClient signalMessage) {
//		cusListener.OnRtnSignalData(SignalDataConvertor.toModel(signalMessage));
//	}


}
