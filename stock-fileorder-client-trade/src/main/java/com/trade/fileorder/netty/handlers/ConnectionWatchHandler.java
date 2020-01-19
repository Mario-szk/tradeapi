package com.trade.fileorder.netty.handlers;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.trade.fileorder.netty.client.NettyClient;
import com.trade.fileorder.netty.client.NettyClientCreator;
import com.trade.fileorder.proto.FileOrderMessageProto.Header;
import com.trade.fileorder.proto.FileOrderMessageProto.RespCode;
import com.trade.fileorder.proto.FileOrderMessageProto.WCMessage;
import com.trade.usds.sdk.constant.LoggerType;
import com.trade.usds.sdk.service.ULogger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

/**
 * 
 * @Description 重连检测，当发现当前通道关闭之后，进行重连
 * 
 */
@Sharable
public abstract class ConnectionWatchHandler extends ChannelInboundHandlerAdapter
		implements TimerTask, ChannelHandlerHolder {

	private final Bootstrap bootstrap;
	private final Timer timer;
	private final int port;
	private final String host;
	NettyClient nettyClient;

	// private SubManagement subManagement = SubManagement.getInstance();

	private volatile boolean reconnect = true;
	// 重连间隔时间
	private final int delay = 100;
	// private int attempts;

	Log log = LogFactory.getLog(ConnectionWatchHandler.class);

	public ConnectionWatchHandler(NettyClient nettyClient, boolean reconnect) {
		this.bootstrap = nettyClient.getBootstrap();
		this.timer = nettyClient.getTimer();
		this.port = nettyClient.getAccountInfo().getPort();
		this.host = nettyClient.getAccountInfo().getIp();
		this.reconnect = reconnect;
		this.nettyClient = nettyClient;
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// log.error("通道关闭");
		ULogger.error(LoggerType.ERROR, "与服务器断开连接，开始尝试重连...");
		reconnect();
		ctx.fireChannelInactive();
	}
	
	public void reconnect(){
		if(reconnect){
			ULogger.error(LoggerType.ERROR, "开始重连...");
			timer.newTimeout(this, delay, TimeUnit.MILLISECONDS); // 在delay秒后调用此类的run方法
		}
	}

	/**
	 * 在关闭连接delay秒后，调用此方法
	 */
	@Override
	public void run(Timeout timeout) throws Exception {
		NettyClientCreator.getClient(nettyClient.getAccountInfo());
//		final ChannelFuture future;
//		// bootstrap已经初始化好了，只需要将handler填入就可以了
//		synchronized (bootstrap) {
//			bootstrap.handler(new ChannelInitializer<Channel>() {
//				@Override
//				protected void initChannel(Channel ch) throws Exception {
//					ch.pipeline().addLast(handlers());
//				}
//			});
//			future = bootstrap.connect(host, port);
//
//		}
//		// future对象
//		future.addListener(new ChannelFutureListener() {
//			public void operationComplete(ChannelFuture f) throws Exception {
//				boolean succeed = f.isSuccess();
//				if (!succeed) {
//					ULogger.error(LoggerType.ERROR, "重连失败！！"); 
//					f.channel().pipeline().fireChannelInactive();
//				} else {
//					ULogger.error(LoggerType.ERROR, "重连成功！！"); ;
//				}
//			}
//		});
		// resub
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		WCMessage message = (WCMessage) msg;
		// 1.在登陆失败的情况下，不进行重连
		if (message.getHeader().getType().getNumber() == Header.Type.LOGIN_RESP_VALUE
				&& (message.getLoginRespMessage().getRespCode() == RespCode.ERROR||message.getLoginRespMessage().getRespCode() == RespCode.NO_USER)) {
//			reconnect = false;
			ctx.close();
			log.error("登陆失败...停止重连");
		}
		super.channelRead(ctx, msg);
	}
}
