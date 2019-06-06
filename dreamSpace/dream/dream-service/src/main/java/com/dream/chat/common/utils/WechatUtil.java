package com.dream.chat.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.log.StaticLog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dream.chat.cache.Wechat.TemplateData;
import com.dream.chat.cache.Wechat.WechatTemplate;
import com.dream.chat.entity.User;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2019-02-22
 */
public class WechatUtil {

    private static final String TEMPLE_ID = "lQvE9fEwxT66Rt2MkkRxfn-eIny4yY6OWvmNpwRDuqQ";

    public static void registTemplate(User user, String friendName, String proName, BigDecimal amount){
        // 获取基础支持的access_token
        /*String resultUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appId +"&secret="+ secret;
        String json = HttpUtil.get(resultUrl);
        JSONObject jsonObject = JacksonUtil.toEntity(json, JSONObject.class);
        String access_token = jsonObject.get("access_token").toString();*/
        String access_token = AccessTokenUtil.getTokenH5();
        // 发送模板消息
        String resultUrl2 = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
        // 封装基础数据
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id(TEMPLE_ID);
        wechatTemplate.setTouser(user.getWxH5Openid());
        //wechatTemplate.setUrl("https://api.tongdiandashan.com/chat/html/bar/barOrder.html");
        Map<String, TemplateData> mapdata = new HashMap<>();
        // 封装模板数据
        TemplateData first = new TemplateData();
        first.setValue(friendName+"支持了您的筹款项目");
        first.setColor("#173177");
        mapdata.put("first", first);

        TemplateData keyword1 = new TemplateData();
        keyword1.setValue(proName);
        keyword1.setColor("#173177");
        mapdata.put("keyword1", keyword1);

        BigDecimal amount1 = amount.setScale(2,BigDecimal.ROUND_DOWN);
        TemplateData keyword2 = new TemplateData();
        keyword2.setValue(String.valueOf(amount1)+"元");
        keyword2.setColor("#173177");
        mapdata.put("keyword2", keyword2);

        TemplateData keyword3 = new TemplateData();
        keyword3.setValue("请进入小程序查看详情。");
        keyword3.setColor("#173177");
        mapdata.put("remark", keyword3);


        wechatTemplate.setData(mapdata);
        String toString = JSON.toJSONString(wechatTemplate);
        String json2 = HttpUtil.post(resultUrl2,toString);
        JSONObject jsonObject2 = JSON.parseObject(json2);
        int result = 0;
        if (null != jsonObject2) {
            if (0 != jsonObject2.getInteger("errcode")) {
                result = jsonObject2.getInteger("errcode");
                StaticLog.error("错误 errcode:{} errmsg:{}", jsonObject2.getInteger("errcode"), jsonObject2.get("errmsg").toString());
            }
        }
        StaticLog.info("模板消息发送结果："+result);
    }
}
