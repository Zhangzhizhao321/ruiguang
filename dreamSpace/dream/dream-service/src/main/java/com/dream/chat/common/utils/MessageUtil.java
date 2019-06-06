package com.dream.chat.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.dream.chat.vo.req.WxMssVo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2018-11-14
 */
public class MessageUtil {


    //发送模板消息
    public static String sendTemplateMessage(WxMssVo wxMssVo) {
        String info = "";
        try {
            //创建连接
            URL url = new URL(wxMssVo.getRequest_url());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Type", "utf-8");
            connection.connect();
            //POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            JSONObject obj = new JSONObject();

            obj.put("access_token", wxMssVo.getAccess_token());
            obj.put("touser", wxMssVo.getTouser());
            obj.put("template_id", wxMssVo.getTemplate_id());
            obj.put("form_id", wxMssVo.getForm_id());
            obj.put("page", wxMssVo.getPage());

            JSONObject jsonObject = new JSONObject();

            for (int i = 0; i < wxMssVo.getParams().size(); i++) {
                JSONObject dataInfo = new JSONObject();
                dataInfo.put("value", wxMssVo.getParams().get(i).getValue());
                dataInfo.put("color", wxMssVo.getParams().get(i).getColor());
                jsonObject.put("keyword" + (i + 1), dataInfo);
            }

            obj.put("data", jsonObject);
            out.write(obj.toString().getBytes());
            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            info = sb.toString();
            System.out.println(sb);
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    }
