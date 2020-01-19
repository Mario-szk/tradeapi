package com.trade.fileorder.netty.handlers;

import com.trade.fileorder.netty.client.NettyClient;
import com.trade.fileorder.netty.push.service.impl.FileOrderServiceImpl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientPushHandler extends ChannelInboundHandlerAdapter{
	
	private FileOrderServiceImpl pushService;
	
	public ClientPushHandler(NettyClient nettyClient,FileOrderServiceImpl pushService) {
		super();
		this.pushService = pushService;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if(pushService != null){
			pushService.setCtx(ctx);
		}
		super.channelActive(ctx);
	}
}
