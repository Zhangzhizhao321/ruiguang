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



public class Test5 {
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
	    	String isCallback ="0";
	        String busiCd ="AC01" ;
	        String credtTp = "0";
	        String acntNo ="6217001020009347321";
	        String bankCd ="0105";
	        String userNm = "刘小丽";
	        String credtNo ="232303198312176221";
	        String srcChnl ="APP";
	        String acntTp = "01";
	        String mobileNo ="15765804993";
	        String mchntCd ="0002900F0345142";
	        String reserved1 ="备注";
	        ArrayList<String> list=new ArrayList<String>();
			list.add(isCallback);
			list.add(busiCd);
			list.add(credtTp);
			list.add(acntNo);
			list.add(bankCd);
			list.add(userNm);
			list.add(credtNo);
			list.add(srcChnl);
			list.add(acntTp);
			list.add(mobileNo);
			list.add(mchntCd);
			list.add(reserved1);
		

			
			
			String s=SignatureUtil.hex(list,"123456");
			System.out.println(s);
	        
	        String signature=s;
	        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><custmrBusi><isCallback>" + isCallback + "</isCallback><busiCd>" + busiCd + "</busiCd><credtTp>" + credtTp + "</credtTp><acntNo>" + acntNo + "</acntNo><bankCd>" + bankCd + "</bankCd><userNm>" + userNm + "</userNm><credtNo>" + credtNo + "</credtNo><srcChnl>" + srcChnl + "</srcChnl><acntTp>" + acntTp + "</acntTp><mobileNo>" + mobileNo + "</mobileNo><mchntCd>" + mchntCd + "</mchntCd><reserved1>" + reserved1 + "</reserved1><signature>" + signature + "</signature></custmrBusi>";
	        System.out.println(xml);
	        String loginUrl = "https://fht-test.fuiou.com/fuMer/api_contract5.do";
	        System.out.println(loginUrl);
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
