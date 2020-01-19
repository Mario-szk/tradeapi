package com.trade.usds.sdk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.trade.usds.sdk.constant.LoggerType;
import com.trade.usds.sdk.service.ULogger;

/**
 * @Description MAC地址获取工具类
 * 
 */
public class MACUtil {
	  
    /**获取操作系统的名字
     */
    public static String getOsName() {
        String os = "";
        os = System.getProperty("os.name");
        return os;
    }

    
    /**获取电脑MAC地址
     */
    private static String getMACAddress() {
        String address = "";
        String os = getOsName();
        if (os.startsWith("Windows")) {
            try {
                String command = "cmd.exe /c ipconfig /all";
                Process p = Runtime.getRuntime().exec(command);
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String  line = br.readLine();
             
                    if (line.indexOf("Physical Address") > 0) {
                        int index = line.indexOf(":");
                        index += 2;
                        address = line.substring(index);
                      
                    }
              
                br.close();
                 
                address=address.trim();
                 
                
                return address.trim();
            } catch (IOException e) {
            }
        } else if (os.startsWith("Linux")) {
            String command = "/bin/sh -c ifconfig -a";
            Process p;
            try {
                p = Runtime.getRuntime().exec(command);
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                //System.out.println(br);
                while ((line = br.readLine()) != null) {
                  
                    if (line.indexOf("HWaddr") > 0) {
                        int index = line.indexOf("HWaddr") + "HWaddr".length();
                        address = line.substring(index);
                        break;
                    }
                }
                br.close();
            } catch (IOException e) {
            }
        }
        address = address.trim();
        return address;
    }

	/**获取本地IP
	 */
	private static InetAddress getHostIp(){
		try{
			 Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()){
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()){
					InetAddress ip = (InetAddress) addresses.nextElement();
					if (ip != null 
							&& ip instanceof Inet4Address
                    		&& !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                    		&& ip.getHostAddress().indexOf(":")==-1){
				 
						return ip ;
					} 
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
	
    /**获取mac地址
     */
    public  static String getMAC () {
         
       String mac = getMACAddress();
       if(mac==null||"".equals(mac)){
        
   		      
            //貌似此方法需要JDK1.6。      
            byte[] byte_mac;
   		try {
   
   			byte_mac = NetworkInterface.getByInetAddress( getHostIp()).getHardwareAddress();
   		} catch (SocketException e) {
   		 
   			e.printStackTrace();
   			return mac;
   		}      
   		if(byte_mac==null){
   			ULogger.error(LoggerType.ERROR, "没有获取到您的mac地址");
   			return "";
   		}
            //下面代码是把mac地址拼装成String      
            StringBuilder sb = new StringBuilder();      
            for (int i = 0; i < byte_mac.length; i++) {      
                if (i != 0) {      
                    sb.append("-");      
                }      
                //mac[i] & 0xFF 是为了把byte转化为正整数      
                String s = Integer.toHexString(byte_mac[i] & 0xFF);      
                sb.append(s.length() == 1 ? 0 + s : s);      
            }      
            //把字符串所有小写字母改为大写成为正规的mac地址并返回      
             mac = sb.toString().trim().toUpperCase();      
           
       }
        return mac;
    }
    /**
     * Main Class.
     * 
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Operation System=" + getOsName());
        System.out.println("Mac Address=" + getMAC ());
       }

}
