package com.dream.common.core.util.wechat;

import com.alibaba.fastjson.JSONObject;
import org.weixin4j.pay.UnifiedOrder;

import java.util.HashMap;
import java.util.Map;

public class WxUnifiedOrder extends UnifiedOrder {
	  private String appid;
	  private String mch_id;
	  private String device_info;
	  private String nonce_str;
	  private String sign;
	  private String body;
	  private String detail;
	  private JSONObject attach;
	  private String out_trade_no;
	  private String fee_type;
	  private String total_fee;
	  private String spbill_create_ip;
	  private String time_start;
	  private String time_expire;
	  private String goods_tag;
	  private String notify_url;
	  private String trade_type;
	  private String product_id;
	  private String openid;
	  private String sub_mch_id;
	  
	  @Override
	public Map<String, String> toMap() {
		  Map<String, String> map = new HashMap();
		    map.put("appid", this.appid);
		    if(attach!=null){
		    	map.put("attach", JSONObject.toJSONString(attach));
		    }
		    map.put("body", this.body);
		    map.put("mch_id", this.mch_id);
		    if(sub_mch_id!=null)
		    map.put("sub_mch_id", this.sub_mch_id);
		    map.put("nonce_str", this.nonce_str);
		    map.put("notify_url", this.notify_url);
		    map.put("openid", this.openid);
		    map.put("out_trade_no", this.out_trade_no);
		    map.put("spbill_create_ip", this.spbill_create_ip);
		    map.put("total_fee", this.total_fee);
		    map.put("trade_type", this.trade_type);
		    return map;
	}
	
	@Override
	public String toXML() {
		   StringBuilder sb = new StringBuilder();
		    sb.append("<xml>");
		    sb.append("<appid><![CDATA[").append(this.appid).append("]]></appid>");
		    sb.append("<body><![CDATA[").append(this.body).append("]]></body>");
		    sb.append("<mch_id><![CDATA[").append(this.mch_id).append("]]></mch_id>");
		    if(sub_mch_id!=null){
		      sb.append("<sub_mch_id><![CDATA[").append(this.sub_mch_id).append("]]></sub_mch_id>");
		    }
		    sb.append("<nonce_str><![CDATA[").append(this.nonce_str).append("]]></nonce_str>");
		    sb.append("<notify_url><![CDATA[").append(this.notify_url).append("]]></notify_url>");
		    sb.append("<openid><![CDATA[").append(this.openid).append("]]></openid>");
		    sb.append("<out_trade_no><![CDATA[").append(this.out_trade_no).append("]]></out_trade_no>");
		    sb.append("<spbill_create_ip><![CDATA[").append(this.spbill_create_ip).append("]]></spbill_create_ip>");
		    sb.append("<total_fee><![CDATA[").append(this.total_fee).append("]]></total_fee>");
		    sb.append("<trade_type><![CDATA[").append(this.trade_type).append("]]></trade_type>");
		    if(this.attach!=null){
			    sb.append("<attach><![CDATA[").append(this.attach.toJSONString()).append("]]></attach>");
		    }
		    sb.append("<sign><![CDATA[").append(this.sign).append("]]></sign>");
		    sb.append("</xml>");
		    return sb.toString();
	}


	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

/*	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}*/
	
	

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public JSONObject getAttach() {
		return attach;
	}

	public void setAttach(JSONObject attach) {
		this.attach = attach;
	}
	
	public void setAttach(String flied,Object value) {
		if(this.attach==null){
			this.attach=new JSONObject();
		}
		this.attach.put(flied, value);
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
    
	
}
