package com.trade.fileorder.netty.util;

import org.apache.commons.codec.digest.DigestUtils;

public class LoginValidateUtil {

	/**
	 * 生成数字签名
	 * 
	 * @param signKey
	 * @date 2018年9月17日
	 */
	public static String buildSign(String secret, long timeStamp) {

		return DigestUtils.md5Hex(secret + timeStamp);

	}

//	/**
//	 * 校验
//	 * 
//	 * @param signKey
//	 * @return
//	 * @date 2018年9月17日
//	 * @throws java.lang.Exception
//	 */
//	public static boolean verify(String secret, String signKey, long timeStamp) throws java.lang.Exception {
//
//		long nowTimeStamp = System.currentTimeMillis();
//
//		if (nowTimeStamp > (timeStamp + validTime)) {
//			// 时间过期
//			throw new java.lang.Exception("秘钥时间过期，不能够进行使用。");
//		} else if ((nowTimeStamp) < (timeStamp - validTime)) {
//
//			// 时间提前
//			throw new java.lang.Exception("秘钥时间提前，不能够进行使用。");
//
//		}
//
//		return buildSign(secret , timeStamp).equals(signKey);
//
//	}
}
