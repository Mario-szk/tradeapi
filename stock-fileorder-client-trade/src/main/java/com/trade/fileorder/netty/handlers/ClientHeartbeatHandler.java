package com.trade.fileorder.netty.handlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.trade.fileorder.proto.FileOrderMessageProto.Header;
import com.trade.fileorder.proto.FileOrderMessageProto.WCMessage;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 
 * @Description 心跳处理
 */
@Sharable
public class ClientHeartbeatHandler extends ChannelInboundHandlerAdapter {

	Log log = LogFactory.getLog(ClientHeartbeatHandler.class);

	/**
	 * 触发超时状态
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleState state = ((IdleStateEvent) evt).state();
			/*if (state == IdleState.READER_IDLE) {
				ctx.close();
			} else */if (state == IdleState.WRITER_IDLE) {
//				System.out.println("客户端发送心跳:" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));
				ctx.writeAndFlush(buildHeartbeatMsg());
			}
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	/**
	 * 收到心跳包
	 */
	/*@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage) msg;
		if (message.getHeader().getType().getNumber() == Header.Type.HEARTBEAT_RESP_VALUE) {
			System.out.println("收到心跳响应:" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));
		}
		super.channelRead(ctx, msg);
	}*/

	/**
	 * 构造心跳包
	 * 
	 * @return
	 */
	public WCMessage buildHeartbeatMsg() {
		// 请求消息构造器
		WCMessage.Builder msgBuilder = WCMessage.newBuilder();
		// 请求头
		Header.Builder HeadBuilder = Header.newBuilder();
		HeadBuilder.setType(Header.Type.HEARTBEAT_REQ);
		msgBuilder.setHeader(HeadBuilder);

		return msgBuilder.build();
	}
}
