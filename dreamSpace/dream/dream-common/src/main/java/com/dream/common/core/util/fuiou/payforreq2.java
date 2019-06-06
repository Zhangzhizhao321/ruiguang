package com.dream.common.core.util.fuiou;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;



public class payforreq2 {
	private static final String ENCODEING="UTF-8";
	public static void requestPost(String url,List<NameValuePair> params) throws ClientProtocolException, IOException {
	    CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	         
	    HttpPost httppost = new HttpPost(url);
	        httppost.setEntity(new UrlEncodedFormEntity(params,ENCODEING));
	         
	        CloseableHttpResponse response = httpclient.execute(httppost);
	        System.out.println(response.toString());
	         
	        HttpEntity entity = response.getEntity();
	        String jsonStr = EntityUtils.toString(entity, "utf-8");
	        System.out.println(jsonStr);
	         
	        httppost.releaseConnection();
	}
	
	public static String getDate(){
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		Date date=new Date();
		String date1=sf.format(date);
		return date1;		
	}
	
	public static void main(String[] args){
	    try {
	    	String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"+
	    			"<payforreq>"+
	    			"<ver>2.00</ver>"+
	    			"<merdt>"+payforreq2.getDate()+"</merdt>"+
	    			"<orderno>"+System.currentTimeMillis()+"</orderno>"+
	    			"<bankno>0102</bankno>"+
	    			"<cityno>2900</cityno>"+
	    			//"<branchnm>中国银行股份有限公司北京西单支行</branchnm>"+
	    			"<accntno>6212261904006115311</accntno>"+
	    			"<accntnm>似曾相识</accntnm>"+
	    			"<amt>10000</amt>"+//金额 以分为单位  不能有小数点，1块钱 100   1分钱 1
	    			//"<txncd>05</txncd>"+
	    			//"<projectid>0002900F0345178_20160121_0222</projectid>"+
	    			//"<txncd></txncd>"+
	    			//"<projectid></projectid>"+
	    			"</payforreq>";
	    	String macSource = "0002900F0345142|123456|"+"payforreq"+"|"+xml;
	    	System.out.println(macSource);
	    	String mac = MD5Util.encode(macSource, "UTF-8").toUpperCase();
	    	System.out.println(mac);
	        String loginUrl = "https://fht-test.fuiou.com/fuMer/req.do";
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("merid", "0002900F0345142"));
	        params.add(new BasicNameValuePair("reqtype", "payforreq"));
	        params.add(new BasicNameValuePair("xml", xml));
	        params.add(new BasicNameValuePair("mac", mac));
	             
	        requestPost(loginUrl,params);
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
}
