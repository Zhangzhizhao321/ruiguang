package com.dream.common.core.util.fuiou;

import java.io.IOException;
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



public class project {
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
	static int ss=(int)((Math.random()*9+1)*100000);
	static String ssn=String.valueOf(ss);
	
	public static void main(String[] args){
	    try {
	    	String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
					+ "<project>"
					+ "<ver>2.00</ver>"
					+ "<orderno>"+(new Date()).getTime()+"</orderno>"
					+ "<mchnt_nm>nshd</mchnt_nm>"
					+ "<project_ssn>"+ssn+"</project_ssn>"
					+ "<project_amt>50000</project_amt>"
					+ "<expected_return>3.24</expected_return>"
					+ "<project_fee>3.24</project_fee>"
					+ "<contract_nm>26872576960310567000</contract_nm>"
					+ "<project_deadline>360</project_deadline>"
					+ "<raise_deadline>180</raise_deadline>"
					+ "<max_invest_num>999</max_invest_num>"
					+ "<min_invest_num></min_invest_num>"
					+ "<bor_nm>李尚尚</bor_nm>"
					+ "<id_tp>0</id_tp>"
					+ "<id_no>320321199008231211</id_no>"
					+ "<card_no>6226220615841342</card_no>"
					+ "<mobile_no>13871445322</mobile_no>"
					+ "</project>";
	    	String macSource = "0002900F0345142|123456|"+xml;
	    	String mac = MD5Util.encode(macSource, "UTF-8").toUpperCase();
	        String loginUrl = "https://fht-test.fuiou.com/fuMer/inspro.do";
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("merid", "0002900F0345142"));
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
