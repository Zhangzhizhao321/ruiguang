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
 */
public class ProgramPayWechatUtil {
    private static Logger log = LoggerFactory.getLogger(ProgramPayWechatUtil.class);

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
    public static final String WECHAT_APP_ID = "wx1172b871b5ecde47";
    public static final String WECHAT_SECRET = "396df52682d12f6db308f2ee391453f1";
    public static final String H5_WECHAT_APP_ID = "wx5bb0cb889bcfebf5";
    public static final String H5_WECHAT_SECRET = "abcdefghijklmnopqrstuvwxyz123456";
    /*public static final String WECHAT_APP_ID = "wx6282433c5178bd8b";
    public static final String WECHAT_SECRET = "abcdefghijklmnopqrstuvwxyz123456";*/
    //TODO 商户号
    public static final String PartnerKey = "1524550921";
    public static final String H5_PartnerKey = "1487461042";
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

    public static String programUnifiedorder(String orderId, BigDecimal amount, String ip, String notifyUrl, String body, String openId, String clientType,String affairId,Integer flag) throws SDKRuntimeException {
        HashMap<String, String> packageParams = new HashMap<>();
        packageParams.put("appid", WECHAT_APP_ID);//微信小程序ID
        packageParams.put("mch_id", PartnerKey);//商户ID
        packageParams.put("nonce_str", CommonUtil.CreateNoncestr());//随机字符串（32位以内） 这里使用时间戳
        packageParams.put("body", body);//支付主体名称 自定义
        packageParams.put("out_trade_no", orderId);//编号 自定义以时间戳+随机数+商品ID
        packageParams.put("total_fee", amount.multiply(new BigDecimal(100)).intValue() + "");//价格 自定义
        packageParams.put("spbill_create_ip", ip);
        packageParams.put("notify_url", props.getStr("chat_url") + notifyUrl);//支付返回地址要外网访问的到， localhost不行，调用下面buy方法。（订单存入数据库）
        packageParams.put("trade_type", clientType);//这个api有，固定的
        packageParams.put("openid", openId);//用户的openid 可以要 可以不要
        /*Map<String,String> map = new HashMap<>();
        map.put("openId",openId);
        map.put("flag",flag.toString());*/
        /*JSONObject jsonObject = new JSONObject();
        jsonObject.put("openId",openId);
        jsonObject.put("flag",flag.toString());
        String json = JSONObject.toJSONString(jsonObject);
        packageParams.put("attach",json);*/
        /*packageParams.put("affairId",affairId);
        packageParams.put("flag",flag.toString());*/
        packageParams.put("sign", sign(packageParams));
        System.out.println(packageParams.toString());
        //log.info("微信支付预付单参数：" + parameters.toString(), LevelType.WARN);

        String xml = CommonUtil.ArrayToXml(packageParams);
        return prepayId(gen_prepay_url, "POST", xml).toString();
    }

    public static HashMap<String,String> programPrepay(String prepayId) throws SDKRuntimeException {
        HashMap<String, String> packageP = new HashMap<>();
        packageP.put("appId", WECHAT_APP_ID);//！！！注意，这里是appId,上面是appid
        packageP.put("nonceStr", CommonUtil.CreateNoncestr());//时间戳
        packageP.put("package", "prepay_id=" + prepayId);//必须把package写成 "prepay_id="+prepay_id这种形式
        packageP.put("signType", "MD5");//paySign加密
        packageP.put("timeStamp", (System.currentTimeMillis() / 1000) + "");
        //得到paySign
        //String paySign = PayCommonUtil.createSign("UTF-8", packageP, "32位秘钥");
        packageP.put("paySign", sign(packageP));
        return packageP;
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
        if ("APP".equals(map.get("trade_type"))) {
            currentSign = ProgramPayWechatUtil.sign(signMap);
        } else {
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
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static String prepayId(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new AzuraX509TrustManager()};
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
     * @param path   请求路径
     * @param method 请求方法
     * @param body   请求数据体
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
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
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

}
