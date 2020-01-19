package com.trade.fileorder.netty.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trade.fileorder.netty.exception.FileOrderException;
import com.trade.fileorder.netty.listener.FileOrderListener;
import com.trade.fileorder.netty.push.dto.AccountInfo;
import com.trade.fileorder.netty.push.service.impl.FileOrderServiceImpl;
import com.trade.fileorder.netty.util.SyncKeyUtil;
import com.trade.fileorder.proto.FileOrderMessageProto.DealPackageResp;
import com.trade.fileorder.proto.FileOrderMessageProto.DealResp;
import com.trade.fileorder.proto.FileOrderMessageProto.FundInfoResp;
import com.trade.fileorder.proto.FileOrderMessageProto.OrderResp;
import com.trade.fileorder.proto.FileOrderMessageProto.PositionResp;
import com.trade.usds.sdk.constant.LoggerType;
import com.trade.usds.sdk.service.ULogger;

public class NettyClientCreator{
	
	static Map<String, NettyClient> nettyClientMap = new HashMap<String, NettyClient>();
	

	public static FileOrderListener fileOrderListener;
	
	public static NettyClient getClient(AccountInfo accountInfo) throws FileOrderException{
		String uniqueKey = SyncKeyUtil.getSyncKey(accountInfo);
		synchronized (uniqueKey) {
			NettyClient nettyClient = nettyClientMap.get(uniqueKey);
			if(nettyClient == null){
				nettyClient = new NettyClient(accountInfo);
				nettyClient.setPushService(new FileOrderServiceImpl(nettyClient));
				nettyClient.setCusListener(fileOrderListener);
				nettyClientMap.put(uniqueKey, nettyClient);
				nettyClient.SyncConnect();
			}else{
				AccountInfo currentAccountInfo = nettyClient.getAccountInfo();
				if(!currentAccountInfo.getIp().equals(accountInfo.getIp())||currentAccountInfo.getPort()!=accountInfo.getPort()) {
					nettyClient = new NettyClient(accountInfo);
					nettyClient.setPushService(new FileOrderServiceImpl(nettyClient));
					nettyClient.setCusListener(fileOrderListener);
					nettyClientMap.put(uniqueKey, nettyClient);
					nettyClient.SyncConnect();
				}else {
					if(!nettyClient.isActive()){
						nettyClient.SyncConnect();
					}
				}
				
			}
			if(!nettyClient.isActive()){
				throw new FileOrderException("连接失败  "+accountInfo);
			}
			return nettyClient;
		}
	}
	
	public static void close(AccountInfo accountInfo) {
		String uniqueKey = SyncKeyUtil.getSyncKey(accountInfo);
		synchronized (uniqueKey) {
			NettyClient nettyClient = nettyClientMap.get(uniqueKey);
			if(nettyClient!=null) {
				nettyClient.close();
				
			}
		}
	}
	
	public static FileOrderListener getFileOrderListener() {
		return fileOrderListener;
	}

	public static void setFileOrderListener(FileOrderListener fileOrderListener) {
		NettyClientCreator.fileOrderListener = fileOrderListener;
	}

	public static Map<String, NettyClient> getNettyClientMap() {
		return nettyClientMap;
	}
	
	public static void connect(){
		List<String> keys = new ArrayList<String>(nettyClientMap.keySet());
		for(String key:keys){
			String uniqueKey = SyncKeyUtil.getSyncKey(key);
			synchronized (uniqueKey) {
				NettyClient nettyClient = nettyClientMap.get(uniqueKey);
				if(!nettyClient.isActive()){
					ULogger.error(LoggerType.ERROR, "正在重连 "+uniqueKey);
					nettyClient.SyncConnect();
				}
			}
		}
	}

	public static void main(String[] args) {
		AccountInfo accountInfo = new AccountInfo();
//		accountInfo.setAccount("123");
//		accountInfo.setPassword("456");
//		accountInfo.setSecId("20");
//		accountInfo.setIp("171.221.254.18");
//		accountInfo.setIp("192.168.0.45");
		
		accountInfo.setAccount("123");
		accountInfo.setPassword("456");
		accountInfo.setSecId("20");
		accountInfo.setIp("127.0.0.1");

		accountInfo.setPort(8802);
		FileOrderListener listener= new FileOrderListener() {
			
			@Override
			public void onRtnDealPackage(AccountInfo accountInfo, DealPackageResp dealPackageResp) {
//				System.out.println("打包 "+dealPackageResp);
			}
			
			@Override
			public void onQryPosition(AccountInfo accountInfo, List<PositionResp> positionList) {
				System.out.println("持仓"+positionList);
			}
			
			@Override
			public void onQryOrder(AccountInfo accountInfo, List<OrderResp> orderList) {
				System.out.println("订单"+orderList);
			}
			
			@Override
			public void onQryFundInfo(AccountInfo accountInfo, FundInfoResp fundInfoResp) {
				System.out.println("资金"+fundInfoResp);
			}
			
			@Override
			public void onQryDeal(AccountInfo accountInfo, List<DealResp> dealList) {
				System.out.println("成交"+dealList);
			}
		};
		NettyClientCreator.setFileOrderListener(listener);
		
		NettyClient nettyClient = null;
		try {
			nettyClient = NettyClientCreator.getClient(accountInfo);
//			nettyClient.getPushService().order("0", "SZ", "131810", "0", "2", "14.0", "100000", "1015");
//			nettyClient.getPushService().cancelOrder("0", "SZ", "131810", "0", "2", "14.0", "100", "1011", "163140");
//			nettyClient.getPushService().cancelOrder("0", "SZ", "000001", "0", "1", "15", "1000", "1009", "SAJ162231702208");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

}
