package com.dream.chat.common.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.log.StaticLog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dream.chat.cache.Wechat.TemplateData;
import com.dream.chat.cache.Wechat.WechatTemplate;
import com.dream.chat.entity.User;
import com.dream.common.core.util.TimeTools;

import java.math.BigDecimal;
import java.util.Date;
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
public class WechatProOverUtil {

    private static final String TEMPLE_ID = "8HdHos_Qd_vuequasNS2D9lWzILU3AC6du-2qvS2BFQ";

    public static void registTemplate(User user,String proName, String number){
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
        first.setValue("您好，您的项目已众筹成功");
        first.setColor("#173177");
        mapdata.put("first", first);

        TemplateData keyword1 = new TemplateData();
        keyword1.setValue(proName);
        keyword1.setColor("#173177");
        mapdata.put("keyword1", keyword1);

        TemplateData keyword2 = new TemplateData();
        keyword2.setValue(TimeTools.format2(new Date()));
        keyword2.setColor("#173177");
        mapdata.put("keyword2", keyword2);

        TemplateData keyword3 = new TemplateData();
        keyword3.setValue(number.toString());
        keyword3.setColor("#173177");
        mapdata.put("keyword3", keyword3);

        TemplateData keyword4 = new TemplateData();
        keyword4.setValue("感谢您的参与，请进入小程序查看详情。");
        keyword4.setColor("#173177");
        mapdata.put("remark", keyword4);


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
