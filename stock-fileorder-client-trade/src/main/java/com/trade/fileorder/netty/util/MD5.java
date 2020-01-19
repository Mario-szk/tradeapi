package com.trade.fileorder.netty.util;
import java.security.MessageDigest;

/**
 * 数据MD5加密工具类
 * @since 1.0
 */
public class MD5 {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 字符加密
	 * @param origin 需加密的字符串
	 * @return 已加密字符串
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;

		try {
			resultString = new String(origin.getBytes("utf-8"));
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes("utf-8")));
		} catch (Exception ex) {

		}
		return resultString;
	}
	
	
}