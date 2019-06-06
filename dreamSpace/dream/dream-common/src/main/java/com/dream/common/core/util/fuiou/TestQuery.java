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



public class TestQuery {
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
	    	
	    	String ver ="1.00";
	        String mchntCd ="0002900F0345178";
	        String contractNo = "000036227864";
	        String startdt ="20170626";
	        String enddt ="20170627";
	        String mobileNo = "13521233364";
	        String userNm ="张衍";
	        String acntNo ="6227002942040547542";
	        String credtNo = "372929199611103614";
	       
	        ArrayList<String> list=new ArrayList<String>();
			list.add(ver);
			list.add(mchntCd);
			list.add(contractNo);
			list.add(startdt);
			list.add(enddt);
			list.add(mobileNo);
			list.add(userNm);
			list.add(acntNo);
			list.add(credtNo);
			
			
			
			String s=SignatureUtil.hex(list,"123456");
			System.out.println(s);
	        
	        String signature=s;
	        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><custmrBusi><ver>" + ver + "</ver><mchntCd>" + mchntCd + "</mchntCd><contractNo>" + contractNo + "</contractNo><startdt>" + startdt + "</startdt><enddt>" + enddt + "</enddt><mobileNo>" + mobileNo + "</mobileNo><userNm>" + userNm + "</userNm><acntNo>" + acntNo + "</acntNo><credtNo>" + credtNo + "</credtNo><signature>" + signature + "</signature></custmrBusi>";
	        String loginUrl = "https://fht-test.fuiou.com/fuMer/api_queryContracts.do";
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
