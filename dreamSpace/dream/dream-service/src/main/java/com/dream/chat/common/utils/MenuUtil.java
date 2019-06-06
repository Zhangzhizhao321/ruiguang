package com.dream.chat.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.dream.chat.cache.Wechat.Menu;
import com.dream.chat.cache.Wechat.ToXcx;
import com.dream.common.core.util.UrlUtil;

import java.awt.*;

/**
 * 自定义菜单工具类
 * @author why
 *
 */
public class MenuUtil {
    //private static Logger logger = Logger.getLogger(MenuUtil.class);// 日志
    //创建菜单接口地址
    //String access_token = AccessTokenUtil.getTokenH5();
    //public final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
    /**
     *创建菜单
     * @param menu 菜单实例
     * @param accessToken 凭证
     * @return true 成功  false 失败
     */
    public static boolean createMenu(Menu menu, String accessToken){
        String access_token = AccessTokenUtil.getTokenH5();
        String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
        boolean result = false;
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        //将菜单对象转换成JSON字符串
        String jsonMenu = JSONObject.toJSONString(menu);
        System.out.println(jsonMenu);
        //发起post请求创建菜单
        String str = UrlUtil.postURL(url,jsonMenu);
        if(null != str){
            JSONObject jsonObject = JSONObject.parseObject(str);
            int errorCode = jsonObject.getInteger("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            System.out.println("====================="+errorCode+"     "+errorMsg);
            if(0 == errorCode){
                result = true;
            }else{
                result = false;
                //logger.error("创建菜单失败errorCode:{"+errorCode+"} errorMsg:{"+errorMsg+"}");
                System.out.println(errorCode+"     "+errorMsg);
            }
        }
        return result;
    }



}

