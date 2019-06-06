package com.dream.common.core.util.wechat;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.weixin4j.Configuration;
import org.weixin4j.pay.SignUtil;
import org.weixin4j.pay.UnifiedOrder;

import java.math.BigDecimal;
import java.util.Random;

public class WeixinPayUtil {
	/**
	 * 生成统一订单
	 * @param ip
	 * @param totalFee
	 * @return
	 */
    public static UnifiedOrder getUnifiedOrder(String openId, String bn, String ip, BigDecimal totalFee, String body, String notify, String clientType, String affrId/*, String flag*/){
    	WxUnifiedOrder unifiedOrder = new WxUnifiedOrder();
		unifiedOrder.setAppid(Configuration.getOAuthAppId());
		unifiedOrder.setBody(body);		
		unifiedOrder.setMch_id(Configuration.getProperty("weixin4j.pay.partner.id"));
		//unifiedOrder.setMch_id("1488379992");
		unifiedOrder.setNonce_str(CreateNoncestr());
		unifiedOrder.setNotify_url(Configuration.getProperty("weixin4j.pay.notify_url")+notify);
		unifiedOrder.setOut_trade_no(bn);
		if(StringUtils.isNotBlank(openId)){
			unifiedOrder.setOpenid(openId);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("affairId",affrId);
		/*jsonObject.put("flag",flag);*/
		unifiedOrder.setAttach(jsonObject);
		unifiedOrder.setSpbill_create_ip(ip);
		unifiedOrder.setTotal_fee(totalFee.multiply(new BigDecimal(100)).intValue()+"");
		unifiedOrder.setTrade_type(clientType);
		unifiedOrder.setSign(SignUtil.getSign(unifiedOrder.toMap(), Configuration.getProperty("weixin4j.pay.partner.key")));
		//unifiedOrder.setSign(SignUtil.getSign(unifiedOrder.toMap(), "abcdefghijklmnopqrstuvwxyz123456"));
		return unifiedOrder;
    }
    public static WxUnifiedOrder getWxUnifiedOrder(String openId, String bn, String ip, BigDecimal totalFee, String body){
    	WxUnifiedOrder unifiedOrder = new WxUnifiedOrder();
		unifiedOrder.setAppid(Configuration.getOAuthAppId());
		unifiedOrder.setBody("按摩椅消费");
		//unifiedOrder.setMch_id(Configuration.getProperty("weixin4j.pay.partner.id"));
		unifiedOrder.setMch_id("1487323982");
		unifiedOrder.setSub_mch_id("1488379992");
		unifiedOrder.setNonce_str(CreateNoncestr());
		unifiedOrder.setNotify_url(Configuration.getProperty("weixin4j.pay.notify_url"));
		unifiedOrder.setOut_trade_no(bn);
		unifiedOrder.setOpenid(openId);
		unifiedOrder.setSpbill_create_ip(ip);
		unifiedOrder.setTotal_fee(totalFee.multiply(new BigDecimal(100)).intValue()+"");
		unifiedOrder.setTrade_type("JSAPI");
		//unifiedOrder.setSign(SignUtil.getSign(unifiedOrder.toMap(), Configuration.getProperty("weixin4j.pay.partner.key")));
		unifiedOrder.setSign(SignUtil.getSign(unifiedOrder.toMap(), "abcdefghijklmnopqrstuvwxyz123456"));
		return unifiedOrder;
    }
    public static WxUnifiedOrder getWxUnifiedOrder(String openId, String bn, String ip, BigDecimal totalFee, String body, JSONObject attach){
    	WxUnifiedOrder unifiedOrder = new WxUnifiedOrder();
		unifiedOrder.setAppid(Configuration.getOAuthAppId());
		unifiedOrder.setBody(body);
		unifiedOrder.setMch_id(Configuration.getProperty("weixin4j.pay.partner.id"));
        unifiedOrder.setAttach(attach);
		unifiedOrder.setNonce_str(CreateNoncestr());
		unifiedOrder.setNotify_url(Configuration.getProperty("weixin4j.pay.notify_url"));
		unifiedOrder.setOut_trade_no(bn);
		unifiedOrder.setOpenid(openId);
		unifiedOrder.setSpbill_create_ip(ip);
		unifiedOrder.setTotal_fee(totalFee.multiply(new BigDecimal(100)).intValue()+"");
		unifiedOrder.setTrade_type("JSAPI");
		unifiedOrder.setSign(SignUtil.getSign(unifiedOrder.toMap(), Configuration.getProperty("weixin4j.pay.partner.key")));
		
		return unifiedOrder;
    }
    /**
     * 订单退款
     * @param wxRefundOrder
     * @return
     */
    public static String refundOrder(WXRefundOrder wxRefundOrder){
    	/*HttpsClient https=new HttpsClient();
    	try {
    		String xml=wxRefundOrder.toXml();
    		//System.out.println("xml:"+xml); //https://api.mch.weixin.qq.com/pay/unifiedorder   https://api.mch.weixin.qq.com/secapi/pay/refund
			Response response=https.postXml("https://api.mch.weixin.qq.com/secapi/pay/refund", xml,true);
			*//**
			 * <xml><return_code><![CDATA[SUCCESS]]></return_code>\n<return_msg><![CDATA[OK]]></return_msg>\n<appid><![CDATA[]]></appid>\n<mch_id><![CDATA[1487461042]]></mch_id>\n<nonce_str><![CDATA[DimOkb7Av5xhfFtz]]></nonce_str>\n<sign><![CDATA[AE3DB1780C8E800E7AF36556D746511A]]></sign>\n<result_code><![CDATA[FAIL]]></result_code>\n<err_code><![CDATA[NOTENOUGH]]></err_code>\n<err_code_des><![CDATA[基本账户余额不足，请充值后重新发起]]></err_code_des>\n</xml>
			 *//*
			return response.asString();
			
		} catch (WeixinException e) {
			e.printStackTrace();
		}*/
		return null;
    	
    }
    /**
	 * 商户生成的随机字符串 字符串类型，32个字节以下
	 * 
	 * @return
	 */
	public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static void main(String[] args) {
		System.out.println(Configuration.getOAuthAppId());
	}

}
