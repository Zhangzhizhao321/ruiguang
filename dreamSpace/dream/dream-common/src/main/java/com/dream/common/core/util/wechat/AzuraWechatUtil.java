package com.dream.common.core.util.wechat;

import cn.hutool.setting.dialect.Props;
import com.alibaba.fastjson.JSONObject;
import com.dream.common.core.exception.SDKRuntimeException;
import com.dream.common.core.util.MD5SignUtil;
import com.dream.common.core.util.AzuraX509TrustManager;
import com.dream.common.core.util.CommonUtil;
import com.dream.common.core.util.MomolepcPropUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.weixin4j.Configuration;
import org.weixin4j.pay.SignUtil;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.URL;
import java.security.KeyStore;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 公众平台通用接口工具类
 * 
 */
public class AzuraWechatUtil {
	private static Logger log = LoggerFactory.getLogger(AzuraWechatUtil.class);

	private static HashMap<String, String> parameters = new HashMap<String, String>();
	public static String BASE_URL = null;
	public static final String WECHAT_SIGN_TYPE = "sha1";

	private static Props props;

	static {
		props = new Props("momole.properties");
	}

	/**
	 * App支付
	 */
	//TODO 小程序id
	public static final String WECHAT_PROGRAM_ID = "";
	public static final String WECHAT_APP_ID = "wxcfb017aebb7443bd";
	public static final String WECHAT_SECRET = "lcXCy0OUvvGcoRN9AgV2Gp8cJWZHpixE";
	public static final String H5_WECHAT_APP_ID = "wx1172b871b5ecde47";
	public static final String H5_WECHAT_SECRET = "TongDiandashan312507x2ydreamlys";
	/*public static final String WECHAT_APP_ID = "wx6282433c5178bd8b";
	public static final String WECHAT_SECRET = "abcdefghijklmnopqrstuvwxyz123456";*/
	//TODO 商户号
	public static final String PartnerKey = "1501505691";
	public static final String H5_PartnerKey = "1524550921";
	public static final String gen_prepay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static final String refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	public static final String orderquery_url = "https://api.mch.weixin.qq.com/pay/orderquery";
	public static final String transfers_url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

	/**
	 * 原生公众号支付
	 */
	public static final String NATIVE_PARTNER_KEY = "1247904001";//
	public static final String NATIVE_APP_ID = "wxce89c9f7e34219b3";// 公众好APPID
	public static final String NATIVE_SECRET = "075d0b7556b29f1dafa8ac350acfc919";

	public static final String WECHAT_APP_PAY_SIGN_KEY = "ak132132ak132132ak132132Ak132132";
	public static final String GENPREPAYID_RECHARGE = "/api/purse/recharge/";
	public static final String GENPREPAYID_PAY = "/api/buy/recharge/";

	public static String SSLPATH = "";


	/**
	 * H5商城充值预付单生成
	 *
	 * @param orderId
	 * @param amount
	 * @param ip
	 * @param notifyUrl
	 * @param body
	 * @return
	 * @throws SDKRuntimeException
	 */
	public static String programUnifiedorder(String orderId, BigDecimal amount, String ip, String notifyUrl, String body,String openId,String clientType)
			throws SDKRuntimeException {

		parameters.clear();
		parameters.put("appid", H5_WECHAT_APP_ID);
		parameters.put("body", body);
		parameters.put("mch_id", H5_PartnerKey);
		parameters.put("nonce_str", CommonUtil.CreateNoncestr());
		//parameters.put("notify_url", genUrlPrefix() + notifyUrl);
			parameters.put("notify_url", props.getStr("paimaiurl")+notifyUrl);
		parameters.put("out_trade_no", orderId);
		parameters.put("spbill_create_ip", ip);
		parameters.put("total_fee", amount.multiply(new BigDecimal(100)).intValue() + "");
		parameters.put("trade_type", clientType.trim());
		if(org.apache.commons.lang3.StringUtils.isNotBlank(openId)){
			parameters.put("openid", openId);
		}
		parameters.put("sign", sign1(parameters));
		System.out.println(parameters.toString());
		//log.info("微信支付预付单参数：" + parameters.toString(), LevelType.WARN);

		String xml = CommonUtil.ArrayToXml(parameters);
		return prepayId(gen_prepay_url, "POST", xml).toString();
	}

	/**
	 * H5商城充值预付单生成
	 *
	 * @param orderId
	 * @param amount
	 * @param ip
	 * @param notifyUrl
	 * @param body
	 * @return
	 * @throws SDKRuntimeException
	 */
	public static String genPrepayRechargeMall(String orderId, BigDecimal amount, String ip, String notifyUrl, String body,String openId,String clientType)
		throws SDKRuntimeException {

		parameters.clear();
		parameters.put("appid", H5_WECHAT_APP_ID);
		parameters.put("body", body);
		parameters.put("mch_id", H5_PartnerKey);
		parameters.put("nonce_str", CommonUtil.CreateNoncestr());
		//parameters.put("notify_url", genUrlPrefix() + notifyUrl);
		parameters.put("notify_url", props.getStr("paimaiurl")+notifyUrl);
		parameters.put("out_trade_no", orderId);
		parameters.put("spbill_create_ip", ip);
		parameters.put("total_fee", amount.multiply(new BigDecimal(100)).intValue() + "");
		parameters.put("trade_type", clientType.trim());
		if(org.apache.commons.lang3.StringUtils.isNotBlank(openId)){
			parameters.put("openid", openId);
		}
		parameters.put("sign", sign1(parameters));
		System.out.println(parameters.toString());
		//log.info("微信支付预付单参数：" + parameters.toString(), LevelType.WARN);

		String xml = CommonUtil.ArrayToXml(parameters);
		return prepayId(gen_prepay_url, "POST", xml).toString();
	}

	/**
	 * 预付单生成
	 * 
	 * @param orderId
	 * @param amount
	 * @param ip
	 * @param notifyUrl
	 * @param body
	 * @return
	 * @throws SDKRuntimeException
	 */
	public static String genPrepayRecharge(String orderId, BigDecimal amount, String ip, String notifyUrl, String body)
			throws SDKRuntimeException {

		parameters.clear();
		parameters.put("appid", WECHAT_APP_ID);
		parameters.put("body", body);
		parameters.put("mch_id", PartnerKey);
		parameters.put("nonce_str", CommonUtil.CreateNoncestr());
		//parameters.put("notify_url", genUrlPrefix() + notifyUrl);
		parameters.put("notify_url",props.getStr("paimaiurl")+notifyUrl);
		parameters.put("out_trade_no", orderId);
		parameters.put("spbill_create_ip", ip);
		parameters.put("total_fee", amount.multiply(new BigDecimal(100)).intValue() + "");
		parameters.put("trade_type", "APP");
		parameters.put("sign", sign(parameters));
		System.out.println(parameters.toString());
		//log.info("微信支付预付单参数："+parameters.toString(), LevelType.WARN);

		String xml = CommonUtil.ArrayToXml(parameters);
		return prepayId(gen_prepay_url, "POST", xml).toString();
	}



	/**
	 * 签名生成
	 * 
	 * @param map
	 * @return
	 * @throws SDKRuntimeException
	 */
	public static String sign(HashMap<String, String> map) throws SDKRuntimeException {
		String unSignParaString = CommonUtil.FormatBizQueryParaMap(map, false);
		return MD5SignUtil.Sign(unSignParaString, WECHAT_SECRET);
	}

	/**
	 * 签名生成
	 *
	 * @param map
	 * @return
	 * @throws SDKRuntimeException
	 */
	public static String sign1(HashMap<String, String> map) throws SDKRuntimeException {
		String unSignParaString = CommonUtil.FormatBizQueryParaMap(map, false);
		System.out.println(unSignParaString);
		return MD5SignUtil.Sign(unSignParaString, H5_WECHAT_SECRET);
	}


	/**
	 * 判断返回数据是否正确
	 * 
	 * @param map
	 * @return
	 * @throws SDKRuntimeException
	 */
	public static String check(Map<String, String> map) throws SDKRuntimeException {

		// 判断返回状态码
		if (!"SUCCESS".equals(map.get("return_code"))) {
			return map.get("return_msg");
		}
		// 判断签名是否正确
		HashMap<String, String> signMap = new HashMap<>(map);
		signMap.remove("sign");
		String currentSign;
		if("APP".equals(map.get("trade_type"))){
			currentSign = AzuraWechatUtil.sign(signMap);
		}else{
			//currentSign = AzuraWechatUtil.sign1(signMap);
			currentSign = SignUtil.getSign(signMap, Configuration.getProperty("weixin4j.pay.partner.key"));
		}
		if (map.get("sign") == null || !map.get("sign").equals(currentSign)) {
			return "签名校验错误";
		}
		// 判断业务结果
		if (!"SUCCESS".equals(map.get("result_code"))) {
			return map.get("err_code") + map.get("err_code_des");
		}
		return "SUCCESS";
	}

	/**
	 * 组装预付ID参数
	 * 
	 * @param prepayid
	 * @return
	 * @throws SDKRuntimeException
	 */
	public static HashMap<String, String> prepayidSign(String prepayid) throws SDKRuntimeException {
		parameters.clear();
		parameters.put("appid", WECHAT_APP_ID);
		parameters.put("partnerid", PartnerKey);
		parameters.put("noncestr", CommonUtil.CreateNoncestr());
		parameters.put("prepayid", prepayid);
		parameters.put("package", "Sign=WXPay");
		parameters.put("timestamp", String.valueOf((int) (new Date().getTime() / 1000)));
		parameters.put("sign", sign(parameters));
		System.out.println(parameters.toString());
		return parameters;
	}

	/**
	 * 组装预付ID参数
	 *
	 * @param prepayid
	 * @return
	 * @throws SDKRuntimeException
	 */
	public static HashMap<String, String> prepayidSign1(String prepayid) throws SDKRuntimeException {
		parameters.clear();
		parameters.put("appId", Configuration.getOAuthAppId());
		parameters.put("nonceStr", WeixinPayUtil.CreateNoncestr());
		parameters.put("package", "prepay_id=" + prepayid);
		parameters.put("timeStamp", String.valueOf((int) (new Date().getTime() / 1000)));
		parameters.put("signType", "MD5");
		parameters.put("paySign", SignUtil.getSign(parameters, Configuration.getProperty("weixin4j.pay.partner.key")));
		return parameters;
	}

	public static void main(String[] args) throws SDKRuntimeException {

		parameters.put("appid", H5_WECHAT_APP_ID);
		parameters.put("noncestr", "GwkCvZVpnzUvVjOb");
		parameters.put("package", "prepay_id=wx28175420856889123a80f0fa1674919452");
		parameters.put("signType", "MD5");
		parameters.put("timeStamp","1543398860");
		parameters.put("paySign", AzuraWechatUtil.sign1(parameters));
		System.out.println(parameters.toString());

	}

	/**
	 * 微信订单查询
	 * 
	 * @param bn
	 * @return
	 * @throws SDKRuntimeException
	 */
	public static String orderquery(String bn) throws SDKRuntimeException {

		parameters.clear();
		parameters.put("appid", WECHAT_APP_ID);
		parameters.put("mch_id", PartnerKey);
		parameters.put("nonce_str", CommonUtil.CreateNoncestr());
		parameters.put("out_trade_no", bn);
		parameters.put("sign", sign(parameters));
		System.out.println(parameters.toString());

		String xml = CommonUtil.ArrayToXml(parameters);
		return prepayId(orderquery_url, "POST", xml).toString();
	}

	/**
	 * app方退款
	 * @param rechargeId
	 * @param refundId
	 * @param refundFee
	 * @param totalFee
	 * @return
	 * @throws SDKRuntimeException
	 */
	public static String refundOrderApp(String rechargeId,String refundId,BigDecimal refundFee,BigDecimal totalFee) throws SDKRuntimeException {

		parameters.clear();
		parameters.put("appid", WECHAT_APP_ID);
		parameters.put("mch_id", PartnerKey);
		parameters.put("nonce_str", CommonUtil.CreateNoncestr());
		parameters.put("out_trade_no", rechargeId);
		parameters.put("out_refund_no", refundId);
		parameters.put("total_fee", totalFee.multiply(new BigDecimal(100)).intValue() + "");
		parameters.put("refund_fee", refundFee.multiply(new BigDecimal(100)).intValue() + "");
		parameters.put("sign", sign(parameters));
		System.out.println(parameters.toString());

		String xml = CommonUtil.ArrayToXml(parameters);
		// 获取证书路径
		String api_cert = props.getStr("credential_url")+"/apiclient_cert_APP.p12";
		System.out.println("证书路径:" + api_cert);

		try {
			return httpsRequestToString(refund_url, "POST", xml ,api_cert , PartnerKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * H5方退款
	 * @param rechargeId
	 * @param refundId
	 * @param refundFee
	 * @param totalFee
	 * @return
	 * @throws SDKRuntimeException
	 */
	public static String refundOrderH5(String rechargeId,String refundId,BigDecimal refundFee,BigDecimal totalFee) throws Exception {

		parameters.clear();
		parameters.put("appid", H5_WECHAT_APP_ID);
		parameters.put("mch_id", H5_PartnerKey);
		parameters.put("nonce_str", CommonUtil.CreateNoncestr());
		parameters.put("out_trade_no", rechargeId);
		parameters.put("out_refund_no", refundId);
		parameters.put("total_fee", totalFee.multiply(new BigDecimal(100)).intValue() + "");
		parameters.put("refund_fee", refundFee.multiply(new BigDecimal(100)).intValue() + "");
		parameters.put("sign", sign1(parameters));
		System.out.println(parameters.toString());

		String xml = CommonUtil.ArrayToXml(parameters);
		System.out.println(parameters.toString());
		// 获取证书路径
		String url = AzuraWechatUtil.class.getResource("").toString();
		url = url.replace("common","web");
		int i = url.indexOf("com");
		String api_cert ="/www/chat/cert/apiclient_cert.p12";
		System.out.println("证书路径:" + api_cert);

		return httpsRequestToString(refund_url, "POST", xml ,api_cert , H5_PartnerKey);
	}

	/**
	 * 提现 app
	 * @return
	 * @throws SDKRuntimeException
	 */
	public static String sendTransfersApp(String orderBn, String reOpenid, BigDecimal amount, String ip, String desc)
			throws Exception {

		parameters.clear();
		parameters.put("nonce_str", CommonUtil.CreateNoncestr());
		parameters.put("partner_trade_no", orderBn);
		parameters.put("mchid", PartnerKey);
		parameters.put("mch_appid", WECHAT_APP_ID);
		parameters.put("check_name", "NO_CHECK");
		parameters.put("openid", reOpenid);
		parameters.put("amount", amount.multiply(new BigDecimal(100)).intValue() + "");
		parameters.put("desc", desc);
		parameters.put("spbill_create_ip", ip);
		parameters.put("sign", sign(parameters));
		System.out.println(parameters.toString());
		String xml = CommonUtil.ArrayToXml(parameters);
		System.out.println(xml);
		// 获取证书路径
		String api_cert = props.getStr("credential_url")+"/apiclient_cert_APP.p12";
		System.out.println("证书路径:" + api_cert);
		return httpsRequestToString(transfers_url, "POST", xml, api_cert,PartnerKey );
	}

	/**
	 * 提现 h5
	 * @return
	 * @throws SDKRuntimeException
	 */
	public static String sendTransfersH5(String orderBn, String reOpenid, BigDecimal amount, String ip, String desc)
			throws Exception {

		parameters.clear();
		parameters.put("nonce_str", CommonUtil.CreateNoncestr());
		parameters.put("partner_trade_no", orderBn);
		parameters.put("mchid", H5_PartnerKey);
		parameters.put("mch_appid", H5_WECHAT_APP_ID);
		parameters.put("check_name", "NO_CHECK");
		parameters.put("openid", reOpenid);
		parameters.put("amount", amount.multiply(new BigDecimal(100)).intValue() + "");
		parameters.put("desc", desc);
		parameters.put("spbill_create_ip", ip);
		parameters.put("sign", SignUtil.getSign(parameters,H5_WECHAT_SECRET));
		System.out.println(parameters.toString());
		String xml = CommonUtil.ArrayToXml(parameters);
		System.out.println(xml);
		// 获取证书路径
		String api_cert = props.getStr("credential_url")+"/apiclient_cert.p12";
		System.out.println("证书路径:" + api_cert);
		return httpsRequestToString(transfers_url, "POST", xml, api_cert,H5_PartnerKey );
	}

	/**
	 * 发起https请求并获取结果
	 *
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static String prepayId(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new AzuraX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return buffer.toString();
	}

	private static String genUrlPrefix() {
		if (StringUtils.isEmpty(BASE_URL)) {
			BASE_URL = MomolepcPropUtil.getPaimaiurl();
		}
		return BASE_URL;
	}

	/**
	 * 以https方式发送请求并将请求响应内容以String方式返回
	 *
	 * @param path
	 *            请求路径
	 * @param method
	 *            请求方法
	 * @param body
	 *            请求数据体
	 * @return 请求响应内容转换成字符串信息
	 */
	public static String httpsRequestToString(String path, String method, String body, String api_cert, String mchId)
			throws Exception {
		// 返回指定类型的密钥库对象
		KeyStore keyStore = KeyStore.getInstance("PKCS12");

		// 证书路径
		FileInputStream instream = new FileInputStream(new File(api_cert));

		// 从给定的输入流加载这个密钥库(param:输入流,密码)
		keyStore.load(instream, mchId.toCharArray());// 商户号

		instream.close();

		/**
		 * 信任自己的CA和所有的自签名证书
		 * SSLContexts.custom():创建自定义SSL上下文,返回SSLContextBuilder实例，来构建SSLContext
		 * SSLContextBuilder.loadKeyMaterial():加载客户端证书,返回SSLContextBuilder
		 * SSLContextBuilder.build():返回SSLContext
		 */
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();// 商户号

		// 只允许TLSv1协议
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

		// 使用HttpClient创建ColseableHttpClient
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

		// 设置请求路径
		HttpPost httpost = new HttpPost(path);

		httpost.addHeader("Connection", "keep-alive");

		httpost.addHeader("Accept", "*/*");

		httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

		httpost.addHeader("Host", "api.mch.weixin.qq.com");

		httpost.addHeader("X-Requested-With", "XMLHttpRequest");

		httpost.addHeader("Cache-Control", "max-age=0");

		httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");

		// 设置请求参数
		httpost.setEntity(new StringEntity(body, "UTF-8"));

		// 调用HttpClient对象的execute(HttpUriRequest
		// request)发送请求，该方法返回一个HttpResponse。
		CloseableHttpResponse response = httpclient.execute(httpost);

		// 获取服务器响应返回的内容
		HttpEntity entity = response.getEntity();

		String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");

		EntityUtils.consume(entity);

		return jsonStr;
	}

	/*public static void main(String[] args) throws Exception {
		//String a = genUrlPrefix();
		*//*parameters.clear();
		parameters.put("appid", WECHAT_APP_ID);
		parameters.put("body", "测试");
		parameters.put("mch_id", PartnerKey);
		parameters.put("nonce_str", CommonUtil.CreateNoncestr());
		parameters.put("notify_url", "www.baidu.com");
		parameters.put("out_trade_no", "TS100001");
		parameters.put("spbill_create_ip", "127.0.0.1");
		parameters.put("total_fee", "100");
		parameters.put("trade_type", "APP");
		parameters.put("sign", sign(parameters));
		System.out.println(parameters.toString());
		String xml = CommonUtil.ArrayToXml(parameters);
		System.out.println(prepayId(gen_prepay_url, "POST", xml).toString());*//*
		
		// 获取证书路径
		*//*String url = AzuraWechatUtil.class.getResource("").toString();
		int i = url.indexOf("com");
		String api_cert = "/" + url.substring(6, i) + "cert/apiclient_cert.p12";
		System.out.println("证书路径:" + api_cert);
		
		TradeOrder order = new TradeOrder();
		order.setBn("O180731153224593");
		order.setTotalFee(new BigDecimal(0.20));
		Integer totalFee = order.getTotalFee().multiply(new BigDecimal(100)).intValue();
		
		parameters.clear();
		parameters.put("appid", WECHAT_APP_ID);
		parameters.put("mch_id", PartnerKey);
		parameters.put("nonce_str", CommonUtil.CreateNoncestr());
		parameters.put("out_trade_no", order.getBn());
		parameters.put("out_refund_no", CommonUtil.CreateNoncestr());
		parameters.put("total_fee",totalFee.toString()); 
		parameters.put("refund_fee",totalFee.toString());
		parameters.put("refund_desc","按摩椅启动失败");
		//parameters.put("notify_url","http://192.168.1.205/dream/");
		parameters.put("sign", sign(parameters));
		
		System.out.println(parameters.toString());
		String xml = CommonUtil.ArrayToXml(parameters);
		String str=  httpsRequestToString(refund_url, "POST", xml ,api_cert , PartnerKey);
		System.out.println(str);*//*

		String url = AzuraWechatUtil.class.getResource("").toString();
		int i = url.indexOf("com");
		String api_cert = "/" + url.substring(6, i) + "credential/apiclient_cert_H5.p12";
		System.out.println("证书路径:" + api_cert);
	}
*/
}
