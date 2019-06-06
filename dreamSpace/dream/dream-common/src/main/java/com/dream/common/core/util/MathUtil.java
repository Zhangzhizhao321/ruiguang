package com.dream.common.core.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.math.BigDecimal.ROUND_DOWN;

public class MathUtil {
	/**
	 * get the random number 1 to 9
	 * 
	 * @return
	 */
	public static int getNumberOneToNine() {
		return (int) (Math.random() * 9) + 1;
	}

	/**
	 * get the random number 0 to 9
	 * 
	 * @return
	 */
	public static int getNumberZreoToNine() {
		return (int) (Math.random() * 10);
	}

	/**
	 * get the length equals six String
	 * 
	 * @return
	 */
	public static String getSixNumber() {
		return "" + getNumberOneToNine() + getNumberZreoToNine() + getNumberZreoToNine() + getNumberZreoToNine()
				+ getNumberZreoToNine() + getNumberZreoToNine();
	}

	public static String getOnlyNumber(String str) {
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		str = m.replaceAll("").trim();
		return str;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getUUID(){
		String uuId = UUID.randomUUID().toString().replace("-","");
		return uuId;
	}

	public static Integer get50To100(){
		Random rand = new Random();
		int num=(int)(Math.random()*50+50);
		System.out.println(num);
		return num;
	}

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		Integer d = 0;
		for (Integer a : list){
			if(a == 2){
				list.remove(a);
				d = a;
				break;
			}
		}
		list.add(d);

		System.out.println(list);
	}
}
