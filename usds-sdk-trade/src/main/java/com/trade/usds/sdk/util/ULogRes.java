package com.trade.usds.sdk.util;

 
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

 

 
/**
 * 
 * 资源加载  对项目下面这个文件进行加载（config.properties）
 * 
 */
public class ULogRes {
	private static Properties properties;
	static InputStream ips ;
	static BufferedReader ipss ;
	static Logger logger=Logger.getLogger("ULogRes");
	static {

	 
		properties = new Properties();
		try {
			  ips = ULogRes.class.getResourceAsStream("/ULogConfig.properties");
			  ipss = new BufferedReader(new InputStreamReader(ips));
			  properties.load(  ipss );
				close( );
		} catch (IOException e) {
			logger.log(Level.ALL, e.getMessage()+"ULogConfig.properties加载取资源文件出错！！！");
		}
	}
	/**根据名字得到文件的值
	 * @param name
	 * @return
	 * @throws CoreForpyException
	 */
	public static void close( ) throws Error {
		if(ipss!=null){
			try {
				ipss.close();
			} catch (IOException e) {
			 
				e.printStackTrace();
			}
		}
		
		if(ips!=null ){
			try {
				ips.close();
			} catch (IOException e) {
			 
				e.printStackTrace();
			}
		}
		 
	}
	/**根据名字得到文件的值
	 * @param name
	 * @return
	 * @throws CoreForpyException
	 */
	public static String getProperties(String name) throws Error {
		
		if (properties.getProperty(name) == null) {
			Throwable throwable = new Throwable("没有你找的资源！！！！");
			throw new Error("资源名字为：" + name + "  的资源为空", throwable);
		}
		return properties.getProperty(name);
	}
	/**根据名字得到文件的值
	 * @param name
	 * @return
	 * @throws CoreForpyException
	 */
	public static String getProperties(String name,String val )   {
		
		 
		return properties.getProperty(name,val );
	}
	 
	
	/**
	 * @return
	 */
	public static String getParentPath(){
        File directory = new File( ULogRes.class.getProtectionDomain().getCodeSource().getLocation().getPath());// 参数为空
        return directory.getParent();
	}
	
	public static String getabsPath(){
		String path = ULogRes.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return path;
	}
}
