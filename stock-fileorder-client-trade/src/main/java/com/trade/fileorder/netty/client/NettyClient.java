package com.trade.fileorder.netty.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.trade.fileorder.netty.handlers.ClientBussinessHandler;
import com.trade.fileorder.netty.handlers.ClientHeartbeatHandler;
import com.trade.fileorder.netty.handlers.ClientPushHandler;
import com.trade.fileorder.netty.handlers.ConnectionWatchHandler;
import com.trade.fileorder.netty.handlers.LoginAuthHandler;
import com.trade.fileorder.netty.listener.FileOrderListener;
import com.trade.fileorder.netty.push.dto.AccountInfo;
import com.trade.fileorder.netty.push.service.impl.FileOrderServiceImpl;
import com.trade.fileorder.proto.FileOrderMessageProto.WCMessage;
import com.trade.usds.sdk.constant.LoggerType;
import com.trade.usds.sdk.service.ULogger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;

public class NettyClient {
	// 处理请求和处理服务端响应的线程组
	private EventLoopGroup group = null;
	// 客户端启动相关配置信息
	private Bootstrap bootstrap = null;
	// 重连定时
	private HashedWheelTimer timer = new HashedWheelTimer();
	// 回调
	// private MdApiListener listener;
	private FileOrderListener cusListener;
	//
	private FileOrderServiceImpl pushService;

	AccountInfo accountInfo;

	private CountDownLatch lathc;

	ConnectionWatchHandler watchHandler;

	private Channel channel;

	public NettyClient(AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
		init();
	}

	// /**
	// * 设置回调接口
	// */
	// public void setListener(MdApiListener listener) {
	// this.listener = listener;
	// }
	//
	public void setCusListener(FileOrderListener cusListener) {
		this.cusListener = cusListener;
	}

	public void setPushService(FileOrderServiceImpl pushService) {
		this.pushService = pushService;
	}

	private void init() {
		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		// 绑定线程组
		bootstrap.group(group);
		// 设定通讯模式为NIO
		bootstrap.channel(NioSocketChannel.class);

	}

	/**
	 * 
	 * @param port
	 * @param host
	 * @param secretKey
	 * @param role
	 *            Sender(0), Receiver(1), Both(2)
	 * @throws Exception
	 */
	public synchronized void  SyncConnect() {
		if(isActive()){
			return;
		}
		if (watchHandler == null) {
			watchHandler = new ConnectionWatchHandler(this, true) {

				public ChannelHandler[] handlers() {
					return new ChannelHandler[] { new ProtobufVarint32FrameDecoder(),
							new ProtobufDecoder(WCMessage.getDefaultInstance()),
							new ProtobufVarint32LengthFieldPrepender(), new ProtobufEncoder(), this,
							new IdleStateHandler(10, 8, 0, TimeUnit.SECONDS), new LoginAuthHandler(NettyClient.this),
							new ClientHeartbeatHandler(), new ClientBussinessHandler(NettyClient.this, cusListener),
							new ClientPushHandler(NettyClient.this, pushService) };
				}
			};
		}

		lathc = new CountDownLatch(1);

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					ChannelFuture future = null;
					bootstrap.handler(new ChannelInitializer<Channel>() {
						// 初始化channel
						@Override
						protected void initChannel(Channel ch) throws Exception {
							ch.pipeline().addLast(watchHandler.handlers());
						}
					});
					bootstrap.remoteAddress(accountInfo.getIp(), accountInfo.getPort());
					future = bootstrap.connect();
					channel = future.channel();
					future.channel().closeFuture().sync();
//					group.shutdownGracefully();
				} catch (Exception e) {
					lathc.countDown();
				} finally {
//					if (group != null) {
//						group.shutdownGracefully();
//					}
//					watchHandler.reconnect();
				}
			}
		}).start();
		if (lathc != null) {
			try {
				lathc.await(3000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				ULogger.error(LoggerType.ERROR, "等待超时", e);
			}
		}
	}
	
	public void close() {
		if(channel!=null) {
			channel.close();
		}
	}

	public FileOrderListener getCusListener() {
		return cusListener;
	}

	public FileOrderServiceImpl getPushService() {
		return pushService;
	}

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}

	public CountDownLatch getLathc() {
		return lathc;
	}

	public EventLoopGroup getGroup() {
		return group;
	}

	public void setGroup(EventLoopGroup group) {
		this.group = group;
	}

	public Bootstrap getBootstrap() {
		return bootstrap;
	}

	public void setBootstrap(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	public HashedWheelTimer getTimer() {
		return timer;
	}

	public void setTimer(HashedWheelTimer timer) {
		this.timer = timer;
	}

	public void setAccountInfo(AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}

	public void setLathc(CountDownLatch lathc) {
		this.lathc = lathc;
	}

	public synchronized boolean isActive() {
		if (channel == null) {
			return false;
		}
		if (!channel.isActive()) {
			return false;
		}
		if (!channel.isOpen()) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws Exception {
		// NettyClient client = new NettyClient();
		// client.setCusListener(new CustomSignalListener() {
		//
		// @Override
		// public void OnRtnSignalData(StrategySignalDataDto signal) {
		// System.out.println(JSON.toJSONString(signal));
		// }
		// });
		// client.SyncConnect(9002, "192.168.0.39",
		// "0c099b32-f74b-4dda-9588-903ad270f68a",3);

	}
}
