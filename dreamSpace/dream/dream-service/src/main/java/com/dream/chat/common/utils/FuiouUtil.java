package com.dream.chat.common.utils;


import com.dream.chat.cache.fuiou.NewProtocolBindXmlBeanReq;
import com.dream.chat.constant.MarketFuiouConstans;
import com.dream.chat.entity.Withdrawal;
import com.dream.chat.vo.req.UserBankReqVo;
import com.dream.chat.vo.req.WithdrawlReqVo;
import com.dream.common.core.util.HttpPoster;
import com.dream.common.core.util.MD5Util;
import com.dream.common.core.util.XMapUtil;
import com.dream.common.core.util.fuiou.MapChangeXmlUtil;
import com.fuiou.mpay.encrypt.DESCoderFUIOU;
import org.apache.commons.httpclient.util.DateUtil;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class FuiouUtil {

    public static Map<String, Object> editContract(UserBankReqVo userBankReqVo) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String cellphone = userBankReqVo.getMobile();
        String bankNum = userBankReqVo.getBankNum();
        String bankname = userBankReqVo.getBankName();
        String realname = userBankReqVo.getUserName();
        String cardid = userBankReqVo.getIdCard();

        try {
            //UserBaseInfo bankcard = userBaseInfoBiz.queryUserInfo(userid);
            NewProtocolBindXmlBeanReq beanReq = new NewProtocolBindXmlBeanReq();
            beanReq.setVersion("1.0");
            beanReq.setTradeDate(DateUtil.formatDate(new Date(), "yyyyMMdd"));
            beanReq.setMchntCd(MarketFuiouConstans.mchntcd);
            beanReq.setUserId(userBankReqVo.getUserId());
            beanReq.setAccount(realname);
            beanReq.setCardNo(bankNum);
            beanReq.setIdType("0");
            beanReq.setIdCard(cardid);
            beanReq.setMobileNo(cellphone);
            String requestNo = "BK"+ Withdrawal.generateBn();
            beanReq.setMchntSsn(requestNo);
            beanReq.setSign(MD5Util.getSign(beanReq.sendMsgSignStr(MarketFuiouConstans.key), "MD5",
                    MarketFuiouConstans.privatekey));

            Map<String, String> map = new HashMap<String, String>();
            String url = MarketFuiouConstans.EDIT_CONTRACT_URL;
            String APIFMS = XMapUtil.toXML(beanReq, MarketFuiouConstans.charset);
            APIFMS = DESCoderFUIOU.desEncrypt(APIFMS, DESCoderFUIOU.getKeyLength8(MarketFuiouConstans.key));
            map.put("MCHNTCD", MarketFuiouConstans.mchntcd);
            map.put("APIFMS", APIFMS);
            String result = new HttpPoster(url).postStr(map);
            result = DESCoderFUIOU.desDecrypt(result, DESCoderFUIOU.getKeyLength8(MarketFuiouConstans.key));

            Map<String, String> resultMap = MapChangeXmlUtil.Dom2Map(result);
            if (resultMap.get("RESPONSECODE").equals("0000")) {
                returnMap.put("requestNo", requestNo);// 校验验证码时候需要
                //System.out.println(requestNo);
                returnMap.put("msg",resultMap.get("RESPONSEMSG"));
                return returnMap;
            }if (resultMap.get("RESPONSECODE").equals("321P")) {
                returnMap.put("requestNo", requestNo);// 校验验证码时候需要
                //System.out.println(requestNo);
                returnMap.put("msg",resultMap.get("RESPONSEMSG"));
                return returnMap;
            }else {
                returnMap.put("requestNo", null);
                returnMap.put("msg",resultMap.get("RESPONSEMSG"));
                return returnMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return returnMap;
        }
    }

    public static void main(String[] args) {
        UserBankReqVo userBankReqVo = new UserBankReqVo();
        userBankReqVo.setBankName("建设银行");
        userBankReqVo.setBankNum("6236682940002032338");
        userBankReqVo.setIdCard("430223200004277229");
        userBankReqVo.setMobile("19802095556");
        userBankReqVo.setUserId("1");
        userBankReqVo.setUserName("杨晶");
        userBankReqVo.setRequestNo("BKWD20190513113900659");
        userBankReqVo.setVerifyCode("590608");

        //FuiouUtil.editContract(userBankReqVo);
        FuiouUtil.verifyMsg(userBankReqVo);
    }


    public static boolean verifyMsg(UserBankReqVo userBankReqVo) {
        String verifyCode = userBankReqVo.getVerifyCode();
        String requestNo = userBankReqVo.getRequestNo();

        try {

            //BankInfo bankcard = userBiz.queryBankByUserId(userid);
            NewProtocolBindXmlBeanReq beanReq = new NewProtocolBindXmlBeanReq();
            beanReq.setVersion("1.0");
            beanReq.setTradeDate(DateUtil.formatDate(new Date(), "yyyyMMdd"));
            beanReq.setMchntCd(MarketFuiouConstans.mchntcd);
            beanReq.setUserId(userBankReqVo.getUserId());
            beanReq.setAccount(userBankReqVo.getUserName());
            beanReq.setCardNo(userBankReqVo.getBankNum());
            beanReq.setIdType("0");
            beanReq.setIdCard(userBankReqVo.getIdCard());
            beanReq.setMobileNo(userBankReqVo.getMobile());
            beanReq.setMchntSsn(requestNo);
            beanReq.setMsgCode(verifyCode);
            beanReq.setSign(MD5Util.getSign(beanReq.proBindSignStr(MarketFuiouConstans.key), "MD5",
                    MarketFuiouConstans.privatekey));

            Map<String, String> map = new HashMap<String, String>();
            String url = MarketFuiouConstans.VERIFY_MSG_URL;
            String APIFMS = XMapUtil.toXML(beanReq, MarketFuiouConstans.charset);
            ;
            APIFMS = DESCoderFUIOU.desEncrypt(APIFMS, DESCoderFUIOU.getKeyLength8(MarketFuiouConstans.key));
            map.put("MCHNTCD", MarketFuiouConstans.mchntcd);
            map.put("APIFMS", APIFMS);
            String result = new HttpPoster(url).postStr(map);
            result = DESCoderFUIOU.desDecrypt(result, DESCoderFUIOU.getKeyLength8(MarketFuiouConstans.key));

            Map<String, String> resultMap = MapChangeXmlUtil.Dom2Map(result);
            if (resultMap.get("RESPONSECODE").equals("0000")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }


    public static Boolean addTk(WithdrawlReqVo vo) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>" +
                "<payforreq>" +
                "<ver>1.00</ver>" +
                "<merdt>" + dateFormat.format(new Date()) + "</merdt>" +
                "<orderno>" + vo.getBn() + "</orderno>" +
                "<bankno>" + vo.getBankNo() + "</bankno>" +
                "<cityno>1000</cityno>" +
                "<accntno>" + vo.getBankNum() + "</accntno>" +
                "<accntnm>" + vo.getUserName() + "</accntnm>" +
                "<amt>" +vo.getAmount().multiply(BigDecimal.valueOf(100)) + "</amt>" +
        "</payforreq>";
        String macSource = MarketFuiouConstans.mchntcd + "|6wuaonl0nae8ne8590f01bug1kwdk4th|" + "payforreq" + "|" + xml;

        String mac = MD5Util.encode(macSource, "UTF-8").toUpperCase();
        String loginUrl = MarketFuiouConstans.DF_URL;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("merid", MarketFuiouConstans.mchntcd));
        params.add(new BasicNameValuePair("reqtype", "payforreq"));
        params.add(new BasicNameValuePair("xml", xml));
        params.add(new BasicNameValuePair("mac", mac));
        /*Map<String, String> params = new HashMap<>();
        params.put("merid", MarketFuiouConstans.mchntcd);
        params.put("reqtype", "payforreq");
        params.put("xml", xml);
        params.put("mac", mac);*/


        try {
            String requestResult = requestPost(loginUrl, params);
            Map<String, String> resultMap = MapChangeXmlUtil.Dom2Map(requestResult);
            /*String result = HttpRequestUtils.doPost(loginUrl, params).toJSONString();
            //result = DESCoderFUIOU.desDecrypt(result, DESCoderFUIOU.getKeyLength8(MarketFuiouConstans.key));

            Map<String, String> resultMap = MapChangeXmlUtil.Dom2Map(result);*/
            //受理成功

            if ("000000".equals(resultMap.get("ret"))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }


    public static String requestPost(String url, List<NameValuePair> params) throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));

        CloseableHttpResponse response = httpclient.execute(httppost);
        System.out.println(response.toString());

        HttpEntity entity = response.getEntity();
        String jsonStr = EntityUtils.toString(entity, "utf-8");
        System.out.println(jsonStr);

        httppost.releaseConnection();
        return jsonStr;
    }

}

