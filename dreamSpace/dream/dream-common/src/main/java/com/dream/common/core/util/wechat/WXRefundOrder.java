package com.dream.common.core.util.wechat;

import lombok.Data;
import org.weixin4j.Configuration;
import org.weixin4j.pay.SignUtil;

import java.util.HashMap;

/**
 * 微信退款订单
 * 
 * @author Administrator
 *
 */
@Data
public class WXRefundOrder {
	private String appId;
	private String mchId;
	private String nonceStr;
	private String refundNo;
	private String tradeNo;
	private int refundFee;
	private int totalFee;
	private String sign;
	
	@Deprecated
	public WXRefundOrder(String refundNo, String tradeNo, int refundFee) {
		super();
		this.appId= Configuration.getOAuthAppId();
		this.mchId= Configuration.getProperty("weixin4j.pay.partner.id");
		this.nonceStr=WeixinPayUtil.CreateNoncestr();
		this.refundNo = refundNo;
		this.tradeNo = tradeNo;
		this.refundFee = refundFee; 
		this.totalFee=refundFee;    //退款金额和订金一样
		this.sign= SignUtil.getSign(this.toMap(), Configuration.getProperty("weixin4j.pay.partner.key"));
	}
	public WXRefundOrder(String refundNo, String tradeNo, int refundFee,int totalFee) {
		this.appId= Configuration.getOAuthAppId();
		this.mchId= Configuration.getProperty("weixin4j.pay.partner.id");
		this.nonceStr=WeixinPayUtil.CreateNoncestr();
		this.refundNo = refundNo;
		this.tradeNo = tradeNo;
		this.refundFee = refundFee; 
		this.totalFee=totalFee;    //退款金额和订金一样
		this.sign=SignUtil.getSign(this.toMap(), Configuration.getProperty("weixin4j.pay.partner.key"));
	}
	
	public WXRefundOrder(String appId, String mchId, String nonceStr, String refundNo, String tradeNo, int refundFee,
			int totalFee, String sign) {
		super();
		this.appId = appId;
		this.mchId = mchId;
		this.nonceStr = nonceStr;
		this.refundNo = refundNo;
		this.tradeNo = tradeNo;
		this.refundFee = refundFee;
		this.totalFee = totalFee;
		this.sign = sign;
	}
    public HashMap<String, String> toMap(){
    	HashMap<String, String> map = new HashMap();
    	map.put("appid", this.appId);
    	map.put("mch_id", this.mchId);
    	map.put("nonce_str", this.nonceStr);
    	map.put("out_refund_no", this.refundNo);
    	map.put("out_trade_no", this.tradeNo);
    	map.put("refund_fee", this.refundFee+"");
    	map.put("total_fee", this.totalFee+"");
    	return map;
    }
	public String toXml() {
		/**
		 * <xml> <appid>wx2421b1c4370ec43b</appid> <mch_id>10000100</mch_id>
		 * <nonce_str>6cefdb308e1e2e8aabd48cf79e546a02</nonce_str>
		 * <out_refund_no>1415701182</out_refund_no>
		 * <out_trade_no>1415757673</out_trade_no> <refund_fee>1</refund_fee>
		 * <total_fee>1</total_fee> <transaction_id></transaction_id>
		 * <sign>FE56DD4AA85C0EECA82C35595A69E153</sign> </xml>
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		sb.append("<appid><![CDATA[").append(this.appId).append("]]></appid>");
		sb.append("<mch_id><![CDATA[").append(this.mchId).append("]]></mch_id>");
		sb.append("<nonce_str><![CDATA[").append(this.nonceStr).append("]]></nonce_str>");
		sb.append("<out_refund_no><![CDATA[").append(this.refundNo).append("]]></out_refund_no>");
		sb.append("<out_trade_no><![CDATA[").append(this.tradeNo).append("]]></out_trade_no>");
		sb.append("<refund_fee><![CDATA[").append(this.refundFee).append("]]></refund_fee>");
		sb.append("<total_fee><![CDATA[").append(this.totalFee).append("]]></total_fee>");
		sb.append("<sign><![CDATA[").append(this.sign).append("]]></sign>");
		sb.append("</xml>");
        return sb.toString();
	}

	





	
}
