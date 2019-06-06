package com.dream.chat.common.utils;

import cn.hutool.setting.dialect.Props;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2019-02-26
 */
public class ProgramQrUtil {

    private static Props props;

    static {
        props = new Props("momole.properties");
    }

    public static String getminiqrQr( String accessToken,String id) {
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String urlName = "";
        try {
            String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token="+accessToken;
            Map<String,Object> param = new HashMap<>();
            param.put("path", "pages/home/home?id="+id);
            /*param.put("width", 430);
            param.put("auto_color", false);*/
            /*Map<String,Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color", line_color);*/
            //LOG.info("调用生成微信URL接口传参:" + param);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            //LOG.info("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
            byte[] result = entity.getBody();
            //LOG.info(Base64.encodeBase64String(result));
            inputStream = new ByteArrayInputStream(result);
            urlName =id+".jpg";
            File file = new File(props.getStr("upload_url") +"/"+/* ImageConstant.QR_IMG+*/"/"+ urlName);
            if (!file.exists()){
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            //LOG.error("调用小程序生成微信永久小程序码URL接口异常",e);
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return props.getStr("watch_url") + "/"/*+ImageConstant.QR_IMG*/+"/"+ urlName;
    }
}
