package com.trade.usds.sdk.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
 

/**
 * @Description 
 * 
 */
public class NetworkUtil {

//	static Logger logger =LoggerFactory.getLogger(NetworkUtil.class);
	
 public static void main(String[] args) {
/*	System.out.println(ping( "192.168.0.41" ) );
	System.out.println(ping( "192.168.0.41" ) );
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	System.out.println(telnet( "www.baidu.com",80 ) );
	
}
    /**
     * 检测ip是否可以访问
     * @param host 要检测的ip
     * @param timeOut 连接超时时间 毫秒 milliseconds
     */
    public static boolean ping(String host ) {
        try {
            return InetAddress.getByName(host).isReachable(1000);
        } catch (UnknownHostException e) {
          // logger.error("没有找到你的网络",e);
        } catch (IOException e) {
        	// logger.error("ping 异常",e);
        }
        return false;
    }
	/**ping IP 和端口
	 * @return
	 */
	public static boolean telnetTest( ){
	 
	 
		return telnet( "www.baidu.com",80 );
	}
	
	/**ping IP 和端口
	 * @param host
	 * @param port
	 * @return
	 */
	public static boolean telnet(String host ,int port){
		//开启socket
			Socket s = new Socket();
					
					try {
						SocketAddress add = new InetSocketAddress(host, port);
						s.connect(add, 7000);// 超时7秒
					} catch ( Exception e) {
						//连接超时需要处理的业务逻辑
						//logger.info("测试连接错误",e);
						return false;
					}finally{
						try {
							s.close();
						} catch (IOException e) {
							//logger.error("关闭错误",e);
						}
					}
	 
		return true;
	}
	
	
}
