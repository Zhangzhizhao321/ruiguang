package com.dream.chat.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.dream.common.core.exception.SDKRuntimeException;
import com.dream.common.core.util.AlipayConfig;
import com.dream.common.core.util.XMLUtil;
import com.dream.common.core.util.wechat.AzuraWechatUtil;
import com.dream.common.core.util.wechat.WeixinPayUtil;
import org.jdom.JDOMException;
import org.weixin4j.Weixin;
import org.weixin4j.WeixinException;
import org.weixin4j.pay.UnifiedOrder;
import org.weixin4j.pay.UnifiedOrderResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.dream.common.core.util.wechat.AzuraWechatUtil.prepayidSign;
import static com.dream.common.core.util.wechat.AzuraWechatUtil.prepayidSign1;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2018-11-14
 */
public class PayUtil {


    /**
     * @param
     * @param ip
     * @return
     */
    public static HashMap<String, String> submitOrderProgram(String id, BigDecimal amount, String ip, String notify, String subject) {
        try {

            String respose = AzuraWechatUtil.genPrepayRecharge(id, amount, ip,
                    notify, subject);
            Map<String, String> resMap = XMLUtil.doXMLParse(respose);
            System.out.println(resMap);
            // 判断返回数据是否正确
            String checkResult = AzuraWechatUtil.check(resMap);
            if (!"SUCCESS".equals(checkResult)) {
                return null;
            }
            HashMap<String, String> map = prepayidSign(resMap.get("prepay_id"));
            return map;
        } catch (SDKRuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param
     * @param ip
     * @return
     */
    public static HashMap<String, String> submitOrderWX(String id, BigDecimal amount, String ip, String notify, String subject) {
        try {

            String respose = AzuraWechatUtil.genPrepayRecharge(id, amount, ip,
                    notify, subject);
            Map<String, String> resMap = XMLUtil.doXMLParse(respose);
            System.out.println(resMap);
            // 判断返回数据是否正确
            String checkResult = AzuraWechatUtil.check(resMap);
            if (!"SUCCESS".equals(checkResult)) {
                return null;
            }
            HashMap<String, String> map = prepayidSign(resMap.get("prepay_id"));
            return map;
        } catch (SDKRuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param
     * @param ip
     * @return
     */
    public static Map<String, String> submitOrderH5WX(String id, BigDecimal amount, String ip, String notify, String subject, String openId, String clientType) {
        try {

            String respose = AzuraWechatUtil.genPrepayRechargeMall(id, amount, ip, notify, subject, openId, clientType);
            Map<String, String> resMap = XMLUtil.doXMLParse(respose);
            System.out.println(resMap);
            // 判断返回数据是否正确
            String checkResult = AzuraWechatUtil.check(resMap);
            if (!"SUCCESS".equals(checkResult)) {
                return null;
            }
            HashMap<String, String> map = AzuraWechatUtil.prepayidSign1(resMap.get("prepay_id"));
            return map;
        } catch (SDKRuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param
     * @param ip
     * @return
     */
    public static Map<String, String> submitOrderH5WX4j(String id, BigDecimal amount, String ip, String notify, String subject, String openId, String clientType,String affId,String flag) throws WeixinException, SDKRuntimeException {
        UnifiedOrder unifiedOrder = WeixinPayUtil.getUnifiedOrder(openId, id, ip,
                amount, subject,notify,clientType,affId/*,flag*/);
        Weixin weixin = new Weixin(true);
        UnifiedOrderResult uor = weixin.payUnifiedOrder(unifiedOrder);

        Map<String,String> result = new HashMap<>();
        if (uor.isSuccess()) {
            result.putAll(prepayidSign1(uor.getPrepay_id()));
            return result;
        }
        return null;
    }

    /**
     * @param
     * @return
     */
    public static Map<String, Object> submitOrderAli(String id, BigDecimal amount, String subject, String notify) {
        String orderStr = "";
        try {
            Map<String, String> orderMap = new LinkedHashMap<String, String>(); // 订单实体
            Map<String, String> bizModel = new LinkedHashMap<String, String>(); // 公共实体
            /****** 2.商品参数封装开始 *****/ // 手机端用
            // 商户订单号，商户网站订单系统中唯一订单号，必填
            orderMap.put("out_trade_no", id);
            // 订单名称，必填
            orderMap.put("subject", subject);
            // 付款金额，必填
            orderMap.put("total_amount", String.valueOf(amount));
            // 销售产品码 必填
            orderMap.put("product_code", "QUICK_MSECURITY_PAY");
            /****** --------------- 3.公共参数封装 开始 ------------------------ *****/ // 支付宝用
            // 1.商户appid
            bizModel.put("app_id", AlipayConfig.APPID);
            // 2.请求网关地址
            bizModel.put("method", AlipayConfig.URL);
            // 3.请求格式
            bizModel.put("format", AlipayConfig.FORMAT);
            // 4.回调地址
            bizModel.put("return_url", AlipayConfig.notify_url);
            // 5.私钥
            //bizModel.put("private_key", AlipayConfig.private_key);
            // 6.商家id
            //bizModel.put("seller_id", AlipayConfig.partner);
            // 7.加密格式
            bizModel.put("sign_type", AlipayConfig.SIGNTYPE + "");
            /****** --------------- 3.公共参数封装 结束 ------------------------ *****/
            // 实例化客户端
            AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
                    AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                    AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
            // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的mod   el入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            // model.setPassbackParams(URLEncoder.encode((String)orderMap.get("body").toString()));;
            // //描述信息 添加附加数据
            // model.setBody(orderMap.get("body")); //商品信息
            model.setSubject(orderMap.get("subject")); // 商品名称
            model.setOutTradeNo(orderMap.get("out_trade_no")); // 商户订单号(自动生成)
            model.setTotalAmount(orderMap.get("total_amount")); // 支付金额
            model.setProductCode(orderMap.get("product_code")); // 销售产品码
           // model.setSellerId(AlipayConfig.APPID); // 商家id
            ali_request.setBizModel(model);
            ali_request.setNotifyUrl(AlipayConfig.noifyUrl(notify)); // 回调地址
            AlipayTradeAppPayResponse responses = client.sdkExecute(ali_request);
            orderStr = responses.getBody();
            //System.err.println(orderStr); // 就是orderString 可以直接给客户端请求，无需再做处理
            Map<String, Object> result = new HashMap<>();
            result.put("formStr", orderStr);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> submitRechargeAliH5(String id, BigDecimal amount, String subject, String notify) throws AlipayApiException {
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
                AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,
                AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();
        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(id);
        model.setSubject(subject);
        model.setTotalAmount(String.valueOf(amount));
        model.setBody("拍卖商城");
        model.setTimeoutExpress("2m");
        model.setProductCode("QUICK_WAP_WAY");
        alipay_request.setBizModel(model);
        // 设置异步通知地址
        alipay_request.setNotifyUrl(AlipayConfig.noifyUrl(notify));
        // 设置同步地址
        //TODO 支付宝设置回调地址
        String returnUrl = AlipayConfig.return_url;
        alipay_request.setReturnUrl(returnUrl);
        Map<String, Object> result = new HashMap<>();
        result.put("formStr", client.pageExecute(alipay_request).getBody());
        return result;
    }

   /* public static Map<String, String> sendTransfers(Integer flag, Withdraw order, BigDecimal amount, String openId, String weixinNickname)
            throws Exception {

        String note = "么么乐商城--提现";

        String response = null;
        if (flag == 0) {
            response = AzuraWechatUtil.sendTransfersH5(order.getWithdrawId(), openId, amount,
                    InetAddress.getLocalHost().getHostAddress(), note);
        }
        if (flag == 1) {
            response = AzuraWechatUtil.sendTransfersApp(order.getWithdrawId(), openId, amount,
                    InetAddress.getLocalHost().getHostAddress(), note);
        }

        // 解析返回数据
        Map<String, String> returnData = XMLUtil.doXMLParse(response);

        System.out.println("商户平台企业付款返回结果:" + returnData);
        return returnData;
        *//* String returnCode = returnData.get("return_code"); // 返回状态码
        String resultCode = returnData.get("result_code"); // 业务结果
       Map<String, Object> result = new HashMap<>();
        if (!"SUCCESS".equals(returnCode) || !"SUCCESS".equals(resultCode)) {
            order.setStatus(2);
            withdrawOrderDao.update(order);
            result.put("resultCode", 2);
            result.put("msg", "提现失败，请在公众号联系管理员处理");
        } else {
            order.setPayNo(returnData.get("payment_no"));
            order.setPayTime(returnData.get("payment_time"));
            order.setStatus(3);
            withdrawOrderDao.update(order);
            result.put("resultCode", 1);
            if (weixinNickname != null && !weixinNickname.equals("")) {
                result.put("msg", "提现到微信账号" + weixinNickname);
            } else {
                result.put("msg", "提现成功");
            }
        }
        return ApiUtils.success(result);*//*
    }*/

    }
