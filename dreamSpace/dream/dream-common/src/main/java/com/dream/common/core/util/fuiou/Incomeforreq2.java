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



public class Incomeforreq2 {
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
	        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
	        		+ "<incomeforreq><ver> 2.00</ver>"
	        		+ "<merdt>"+Incomeforreq2.getDate()+"</merdt>"
	        		+ "<orderno>"+System.currentTimeMillis()+"</orderno>"
	        		+ "<bankno>0305</bankno>"
	        		+ "<accntno>6226220615841342</accntno>"
	        		+ "<accntnm>李尚尚</accntnm>"
	        		+ "<amt>100</amt>"//金额 以分为单位  不能有小数点，1块钱 100   1分钱 1
	        		+ "<entseq>test</entseq>"
	        		+ "<memo>备注</memo>"
	        		+ "<mobile>13871445322</mobile>"
	        		+ "<certtp>0</certtp>"
	        		+ "<certno>320321199008231211</certno>"
	        		+"<projectid>0345142_20170726_603257</projectid>"
	    			+"<txncd>06</txncd>"
	        		+ "</incomeforreq>";

	    	String macSource = "0002900F0345142|123456|"+"sincomeforreq"+"|"+xml;
	    	System.out.println(macSource);
	    	String mac = MD5Util.encode(macSource, "UTF-8").toUpperCase();
	        String loginUrl = "https://fht-test.fuiou.com/fuMer/req.do";
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("merid", "0002900F0345142"));
	        params.add(new BasicNameValuePair("reqtype", "sincomeforreq"));
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
