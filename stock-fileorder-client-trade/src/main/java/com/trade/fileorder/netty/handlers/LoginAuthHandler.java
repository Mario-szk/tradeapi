package com.trade.fileorder.netty.handlers;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.trade.fileorder.netty.client.NettyClient;
import com.trade.fileorder.netty.push.dto.AccountInfo;
import com.trade.fileorder.proto.FileOrderMessageProto.Header;
import com.trade.fileorder.proto.FileOrderMessageProto.Header.Type;
import com.trade.fileorder.proto.FileOrderMessageProto.LoginReqMessage;
import com.trade.fileorder.proto.FileOrderMessageProto.WCMessage;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
/**
 * 
 * @Description 登陆验证
 * 
 */

@Sharable
public class LoginAuthHandler extends ChannelInboundHandlerAdapter {

	NettyClient nettyClient;

	Log log = LogFactory.getLog(LoginAuthHandler.class);

	public LoginAuthHandler(NettyClient nettyClient) {
		this.nettyClient = nettyClient;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		super.channelRead(ctx, msg);
		WCMessage wcMessage = (WCMessage) msg;
		if(wcMessage.getHeader().getType() == Type.LOGIN_RESP){
			if(nettyClient.getLathc()!=null){
				nettyClient.getLathc().countDown();
			}
			log.error(wcMessage);
		}else{
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		WCMessage LoginMsg = buildLoginMsg();
		ctx.writeAndFlush(LoginMsg);
		ctx.fireChannelActive();
	}

	public WCMessage buildLoginMsg() {
//		long timeStamp = System.currentTimeMillis();
//		String signature = LoginValidateUtil.buildSign(secretKey, timeStamp);
		WCMessage.Builder msgBuilder = WCMessage.newBuilder();
		Header.Builder headBuilder = Header.newBuilder();
		LoginReqMessage.Builder bodyBuilder = LoginReqMessage.newBuilder();
		AccountInfo accountInfo=nettyClient.getAccountInfo();
		headBuilder.setType(Header.Type.LOGIN_REQ);
		bodyBuilder.setUsername(accountInfo.getAccount());
		bodyBuilder.setPassword(accountInfo.getPassword());
		bodyBuilder.setSecId(accountInfo.getSecId());
		msgBuilder.setHeader(headBuilder);
		msgBuilder.setLoginReqMessage(bodyBuilder);
		return msgBuilder.build();
	}
}
