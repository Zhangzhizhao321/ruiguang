package com.dream.common.core.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Http Post
 * @author Aillans
 *
 */
public class HttpPoster {

	public HttpPoster() {
	}

	private String url;

	private String charset;

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public HttpPoster(String url, String charset) {
		super();
		this.url = url;
		this.charset = charset;
	}

	public HttpPoster(String url) {
		super();
		this.url = url;
		this.charset = "UTF-8";
	}

	/**
	 * 静态post
	 */
	public static int post(final Map<String, String> parameters, String url, String charset) throws Exception {
		HttpPoster post = new HttpPoster(url, charset);
		return post.post(parameters);
	};

	public static String getResponseString(final Map<String, String> parameters, String url, String charset) throws Exception {
		HttpPoster post = new HttpPoster(url, charset);
		return post.postStr(parameters);
	};

	public static int post(final Map<String, String> parameters, String url) throws Exception {
		HttpPoster post = new HttpPoster(url, "UTF-8");
		return post.post(parameters);
	};

	/**
	 * 发送参数包
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public int post(final Map<String, String> parameters) {
		return post(new PostMethodCallback() {

			@Override
			public void doInPostMethod(PostMethod postMethod) {
				System.out.println("发送URL==" + url);
				System.out.println("发送参数==" + parameters);
				NameValuePair[] nameValuePairs = new NameValuePair[parameters.size()];
				Set<Entry<String, String>> set = parameters.entrySet();
				int i = 0;
				//设置查询参数
				for (Entry<String, String> entry : set) {
					NameValuePair pair = new NameValuePair(entry.getKey(), entry.getValue());
					nameValuePairs[i] = pair;
					i++;
				}
				//发送参数包
				postMethod.setRequestBody(nameValuePairs);

			}
		});
	}

	public static String postJson(final Map<String, String> parameters, String url, String charset) {
		HttpPoster post = new HttpPoster(url, charset);
		return post.postStrNew(parameters);
	}
	
	public String postStrNew(final Map<String, String> parameters){
		return postStr(new PostMethodCallback() {

			@Override
			public void doInPostMethod(PostMethod postMethod) {
				
			RequestEntity re = null;
			try {
				re = new StringRequestEntity(JSON.toJSONString(parameters),"application/json","UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
	         postMethod.setRequestEntity(re); 
			}
		});
	}
	
	public String postStr(final Map<String, String> parameters) {
		System.out.println("发送url==" + url);
		return postStr(new PostMethodCallback() {

			@Override
			public void doInPostMethod(PostMethod postMethod) {
				System.out.println("发送参数==" + parameters);
				NameValuePair[] nameValuePairs = new NameValuePair[parameters.size()];
				Set<Entry<String, String>> set = parameters.entrySet();
				int i = 0;
				//设置查询参数
				for (Entry<String, String> entry : set) {
					NameValuePair pair = new NameValuePair(entry.getKey(), entry.getValue());
					nameValuePairs[i] = pair;
					i++;
				}
				//发送参数包
				postMethod.setRequestBody(nameValuePairs);

			}
		});
	}

	/**
	 * 使用http协议发送xmltext到url
	 *
	 */
	public int post(final String body) throws Exception {
		return post(new PostMethodCallback() {

			@Override
			public void doInPostMethod(PostMethod postMethod) {

				try {
					InputStream instream = new ByteArrayInputStream(body.getBytes(charset));
					postMethod.setRequestEntity(new InputStreamRequestEntity(instream, body.getBytes(charset).length));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 使用http协议发送xmltext到url
	 *
	 */
	private int post(PostMethodCallback callback) {
		HttpClient httpclient = null;
		PostMethod xmlpost = null;
		try {
			//https设置
			if (url.indexOf("https://") != -1) {
				//创建SSL连接
				@SuppressWarnings("deprecation")
				Protocol myhttps = new Protocol("https", new MySSLSocketFactory(), 443);
				Protocol.registerProtocol("https", myhttps);
			}
			httpclient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(1000 * 10);//设连接超时时间
			httpclient.getHttpConnectionManager().getParams().setSoTimeout(1000 * 10);//设读取数据超时时间
			xmlpost = new PostMethod(url);
			httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
			httpclient.getParams().setContentCharset(charset);
			//xmlpost.setRequestHeader("content-type", "text/xml; charset=" + charset);
			//内部回调，发送数据，设置参数用
			callback.doInPostMethod(xmlpost);
			//执行请求
			int responseStatCode = httpclient.executeMethod(xmlpost);
			//获取返回信息
			InputStream ips = xmlpost.getResponseBodyAsStream();
			List<Byte> byteList = new ArrayList<Byte>();
			int is = 0;
			while ((is = ips.read()) != -1)
				byteList.add((byte) is);
			byte[] allb = new byte[byteList.size()];
			for (int j = 0; j < byteList.size(); j++)
				allb[j] = byteList.get(j);
			String responseString = new String(allb, charset);
			System.out.println("HTTP返回码=" + responseStatCode);
			System.out.println("应答数据=" + responseString);
			if (url.indexOf("https://") != -1)
				Protocol.unregisterProtocol("https");
			return responseStatCode;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("报文发送到[" + url + "]失败:" + e.getMessage());
			throw new IllegalArgumentException("通信异常");
		} finally {
			if (xmlpost != null){
				xmlpost.releaseConnection();
			}
			if (httpclient != null){
				httpclient.getHttpConnectionManager().closeIdleConnections(0);
			}
		}
	}

	private String postStr(PostMethodCallback callback) {
		HttpClient httpclient = null;
		PostMethod xmlpost = null;
		try {
			//https设置
			if (url.indexOf("https://") != -1) {
				//创建SSL连接
				@SuppressWarnings("deprecation")
				Protocol myhttps = new Protocol("https", new MySSLSocketFactory(), 443);
				Protocol.registerProtocol("https", myhttps);
			}
			httpclient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(1000 * 10);
			httpclient.getHttpConnectionManager().getParams().setSoTimeout(1000 * 10);//设读取数据超时时间
			xmlpost = new PostMethod(url);
			httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
			httpclient.getParams().setContentCharset(charset);
			//xmlpost.setRequestHeader("content-type", "text/xml; charset=" + charset);
			//内部回调，发送数据，设置参数用
			callback.doInPostMethod(xmlpost);
			//执行请求
			int responseStatCode = httpclient.executeMethod(xmlpost);
			//获取返回信息
			InputStream ips = xmlpost.getResponseBodyAsStream();
			List<Byte> byteList = new ArrayList<Byte>();
			int is = 0;
			while ((is = ips.read()) != -1)
				byteList.add((byte) is);
			byte[] allb = new byte[byteList.size()];
			for (int j = 0; j < byteList.size(); j++)
				allb[j] = byteList.get(j);
			String responseString = new String(allb, charset);
			System.out.println("HTTP返回码=" + responseStatCode);
			System.out.println("应答数据=" + responseString);
			if (url.indexOf("https://") != -1)
				Protocol.unregisterProtocol("https");
			return responseString;
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("通信异常");
		} finally {
			if (xmlpost != null){
				xmlpost.releaseConnection();
			}
			if (httpclient != null){
				httpclient.getHttpConnectionManager().closeIdleConnections(0);
			}
		}
	}

	/**
	 * PostMethod回调处理
	 *
	 */
	public interface PostMethodCallback {
		public void doInPostMethod(PostMethod postMethod);
	}

}

class MySSLSocketFactory implements ProtocolSocketFactory
{
	private SSLContext	sslcontext	= null;

	private SSLContext createSSLContext()
	{
		SSLContext sslcontext = null;
		try
		{
			sslcontext = SSLContext.getInstance("SSL");
			sslcontext.init(null, new TrustManager [ ] {
					new TrustAnyTrustManager() }, new java.security.SecureRandom());
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (KeyManagementException e)
		{
			e.printStackTrace();
		}
		return sslcontext;
	}

	private SSLContext getSSLContext()
	{
		if (this.sslcontext == null)
		{
			this.sslcontext = createSSLContext();
		}
		return this.sslcontext;
	}

	public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
			UnknownHostException
	{
		return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
	}

	public Socket createSocket(String host, int port) throws IOException, UnknownHostException
	{
		return getSSLContext().getSocketFactory().createSocket(host, port);
	}

	public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException,
			UnknownHostException
	{
		return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
	}

	public Socket createSocket(String host, int port, InetAddress localAddress, int localPort,
							   HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException
	{
		if (params == null)
		{
			throw new IllegalArgumentException("Parameters may not be null");
		}
		int timeout = params.getConnectionTimeout();
		SocketFactory socketfactory = getSSLContext().getSocketFactory();
		if (timeout == 0)
		{
			return socketfactory.createSocket(host, port, localAddress, localPort);
		}
		else
		{
			Socket socket = socketfactory.createSocket();
			SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);
			SocketAddress remoteaddr = new InetSocketAddress(host, port);
			socket.bind(localaddr);
			socket.connect(remoteaddr, timeout);
			return socket;
		}
	}

	private static class TrustAnyTrustManager implements X509TrustManager
	{

		public void checkClientTrusted(X509Certificate [ ] chain, String authType) throws CertificateException
		{
		}

		public void checkServerTrusted(X509Certificate [ ] chain, String authType) throws CertificateException
		{
		}

		public X509Certificate [ ] getAcceptedIssuers()
		{
			return new X509Certificate [ ] { };
		}
	}
}
