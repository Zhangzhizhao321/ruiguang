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



public class Test5_msg {
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
	
	public static void main(String[] args){
	    try {
	    	
	        String acntNo ="6217001020009347321";
	        String mchntCd ="0002900F0345142";
	        String verifyCode ="000000";
	        ArrayList<String> list=new ArrayList<String>();
			list.add(acntNo);
			list.add(mchntCd);
			list.add(verifyCode);
		

			
			
			String s=SignatureUtil.hex(list,"123456");
			System.out.println(s);
	        
	        String signature=s;
	        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><custmrBusi><acntNo>" + acntNo + "</acntNo><mchntCd>" + mchntCd + "</mchntCd><verifyCode>" + verifyCode + "</verifyCode><signature>" + signature + "</signature></custmrBusi>";
	        String loginUrl = "https://fht-test.fuiou.com/fuMer/api_verifyMsg.do";
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
