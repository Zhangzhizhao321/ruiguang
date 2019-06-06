package com.dream.common.core.util.fuiou;

import java.io.IOException;
import java.util.ArrayList;
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




public class QryMoney {
	private static final String ENCODEING="UTF-8";
	public static void requestPost(String url,List<NameValuePair> params) throws ClientProtocolException, IOException {
	    CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	         
	    HttpPost httppost = new HttpPost(url);
	        httppost.setEntity(new UrlEncodedFormEntity(params,ENCODEING));
	         
	        CloseableHttpResponse response = httpclient.execute(httppost);
	        System.out.println(response.toString());
	         
	        HttpEntity entity = response.getEntity();
	        String jsonStr = EntityUtils.toString(entity, "utf-8");
	        String ret=jsonStr.substring(jsonStr.indexOf("<ret>")+5,jsonStr.indexOf("</ret>"));
	        if("000000".equals(ret)){
	        	String str1=jsonStr.substring(jsonStr.indexOf("<ctamt>")+7,jsonStr.indexOf("</ctamt>"));
	        	String str2=jsonStr.substring(jsonStr.indexOf("<caamt>")+7,jsonStr.indexOf("</caamt>"));
	        	String str3=jsonStr.substring(jsonStr.indexOf("<cuamt>")+7,jsonStr.indexOf("</cuamt>"));
	        	String str4=jsonStr.substring(jsonStr.indexOf("<cfamt>")+7,jsonStr.indexOf("</cfamt>"));
	        	System.out.println("账面余额: "+str1);
	        	System.out.println("可用余额: "+str2);
	        	System.out.println("未转结余额: "+str3);
	        	System.out.println("冻结余额: "+str4);
	        }else{
	        	System.out.println("查询失败");
	        }
	        System.out.println(jsonStr);
	         
	        httppost.releaseConnection();
	}
	
	private static String SubString(int i, int indexOf) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args){
	    try {
	    	
	    	String macSource = "1.0|0002900F0345142|123456";
	    	String mac = MD5Util.encode(macSource, "UTF-8").toUpperCase();
	    	String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"+
	    			"<qryacnt>"+
	    			"<ver>1.0</ver>"+
	    			"<merid>0002900F0345142</merid>"+
	    			"<mac>"+mac+"</mac>"+
	    			"</qryacnt>";
	        String loginUrl = "https://fht-test.fuiou.com/fuMer/t0zj_qryAcnt.do";
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("xml", xml));          
	        requestPost(loginUrl,params);
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
}
