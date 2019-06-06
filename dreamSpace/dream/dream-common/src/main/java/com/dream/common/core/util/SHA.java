package com.dream.common.core.util;

import org.apache.shiro.codec.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {

	/***
	 * 利用Apache的工具类实现SHA-256加密
	 * 
	 * @param str
	 *            加密后的报文
	 * @return
	 */
	public static String getSHA256Str(String str) {
		MessageDigest messageDigest;
		String encdeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
			encdeStr = Hex.encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encdeStr;
	}

	/***
	 * 利用Apache的工具类实现SHA-1加密
	 * 
	 * @param str
	 *            加密后的报文
	 * @return
	 */
	public static String getSHA1Str(String str) {
		MessageDigest messageDigest;
		String encdeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
			byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
			encdeStr = Hex.encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encdeStr;
	}

	public static void main(String[] args) throws Exception {

		String data = "secretId=%E4%B8%AA%E4%BA%BAAPI%E5%AF%86%E9%92%A5%E4%B8%AD%E7%9A%84Secret+Id&currentTimeStamp=1509758418&expireTime=1509931218&random=1706213212";
		System.out.println("加密前数据: string:" + data);
		System.out.println();
		String s = getSHA1Str(data);
		System.out.println("加密后数据: string:" + s);

	}
}
