package com.dream.common.core.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpRequestUtils {

	public static String GetHtttpLink(String path) throws Exception {

		HttpClient client = new HttpClient();// 打开新窗口

		GetMethod get = new GetMethod(path);// path是请求地址，可以带参数

		int st1 = client.executeMethod(get);

		System.out.println("执行状态：" + st1);

		String result = get.getResponseBodyAsString();// 服务端返回的Response

		get.releaseConnection();// 释放链接

		return result;

	}

	public static String PostHtttpLink(String path) throws Exception {
		HttpClient client = new HttpClient();// 打开新窗口

		PostMethod po = new PostMethod(path);

		int st1 = client.executeMethod(po);

		System.out.println("执行状态：" + st1);

		String result = po.getResponseBodyAsString();// 服务端返回的Response

		po.releaseConnection();

		return result;

	}


	public static String doGet(String url, String charset) throws Exception {

		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);

		if (null == url || !url.startsWith("http")) {
			throw new Exception("请求地址格式不对");
		}
		// 设置请求的编码方式
		if (null != charset) {
			method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + charset);
		} else {
			method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + "utf-8");
		}
		int statusCode = client.executeMethod(method);

		if (statusCode != HttpStatus.SC_OK) {// 打印服务器返回的状态
			System.out.println("Method failed: " + method.getStatusLine());
		}
		// 返回响应消息
		byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
		// 在返回响应消息使用编码(utf-8或gb2312)
		String response = new String(responseBody, "utf-8");
		System.out.println("------------------response:" + response);
		// 释放连接
		method.releaseConnection();
		return response;
	}

	public static JSONObject doPost(String url, Map<String, String> map) {
		DefaultHttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new DefaultHttpClient();
			httpPost = new HttpPost(url);
			httpPost.setHeader("content-type", "application/x-www-form-urlencoded");
			// 设置参数
			if (map != null) {
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				Iterator iterator = map.entrySet().iterator();
				while (iterator.hasNext()) {
					@SuppressWarnings("unchecked")
					Entry<String, String> elem = (Entry<String, String>) iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
				}
				if (list.size() > 0) {
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
					httpPost.setEntity(entity);
				}
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, "utf-8");
					//System.out.println(result);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return JSONObject.parseObject(result);
	}

	public static Object doPostToJD(String url, Map<String, String> map) {
		DefaultHttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new DefaultHttpClient();
			httpPost = new HttpPost(url);
			httpPost.setHeader("content-type", "application/x-www-form-urlencoded");
			// 设置参数
			if (map != null) {
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				Iterator iterator = map.entrySet().iterator();
				while (iterator.hasNext()) {
					@SuppressWarnings("unchecked")
					Entry<String, String> elem = (Entry<String, String>) iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
				}
				if (list.size() > 0) {
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
					httpPost.setEntity(entity);
				}
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, "utf-8");
					System.out.println(result);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static JSONObject doPost1(String url, Map<String, Object> map) {
		DefaultHttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new DefaultHttpClient();
			httpPost = new HttpPost(url);
			httpPost.setHeader("content-type", "application/x-www-form-urlencoded");
			// 设置参数
			if (map != null) {
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				Iterator iterator = map.entrySet().iterator();
				while (iterator.hasNext()) {
					@SuppressWarnings("unchecked")
					Entry<String, String> elem = (Entry<String, String>) iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
				}
				if (list.size() > 0) {
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
					httpPost.setEntity(entity);
				}
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, "utf-8");
					System.out.println(result);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return JSONObject.parseObject(result);
	}

}
