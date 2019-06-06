package com.dream.common.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.SortedMap;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;


public class ApiUtil {
	public final static String DEFAULT_CHARSET = "utf-8";

	public static String getRemoteAddress(HttpServletRequest request) {
		String remoteIp = StringUtil.nullToString(request.getHeader("x-real-ip")).trim();
		if (remoteIp.length() == 0) {
			remoteIp = StringUtil.nullToString(request.getHeader("x-forwarded-for")).trim();
		}
		if (remoteIp.length() == 0) {
			remoteIp = request.getRemoteAddr();
		}

		return remoteIp;
	}

	public static String genParam(Map<String, ?> map) {
		String param = "";
		for (Map.Entry<String, ?> set : map.entrySet()) {
			String key = set.getKey();
			Object value = set.getValue();
			if (value != null) {
				try {
					param += "&" + key + "=" + URLEncoder.encode(value.toString(), DEFAULT_CHARSET);
				} catch (UnsupportedEncodingException e) {
					param += "&" + key + "=" + value;
				}
			}
		}
		return param.substring(1);
	}

	public static String getGUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}

	/*public static String signParam(SortedMap<String, ?> map,String apiKey) {
		String source = "";
		for (Map.Entry<String, ?> set : map.entrySet()) {
			String key = set.getKey();
			Object value = set.getValue();
			if (value != null) {
				if(value instanceof String[]){
					String[] vals = (String[]) value;
					for (String val : vals) {
						if(val != null) source += key + val;
					}
				}else{
					source += key + value;
				}
			}
		}
		source += apiKey;
		String signCode = Md5Util.encrypt(source, DEFAULT_CHARSET);
		System.out.println("source="+source);
		System.out.println("checkCode="+signCode);
		return signCode;
	}*/

	public static byte[] getByteData(HttpServletRequest request){
		int len = request.getContentLength();
		byte[] buffer = new byte[len];
		ServletInputStream io;
		try {
			io = request.getInputStream();
			io.read(buffer, 0, len);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
}
