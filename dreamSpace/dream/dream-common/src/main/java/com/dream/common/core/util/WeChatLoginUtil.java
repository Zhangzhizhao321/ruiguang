package com.dream.common.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

public class WeChatLoginUtil {

    //TODO wxappid
    private static final String APP_ID = "wx20a0b0527db5aa3c"; //"wxcfb017aebb7443bd";
    private static final String APP_SECRET = "400b9993716c7cb99191abf35a2db0f1";//"1a004c607e00c6cae96d42c23ef1596f";

    private static final String APP_ID_H5 = "wx312fae5d7cdb1bb9";
    private static final String APP_SECRET_H5 = "edb3db2718d91b4a4cc45a16efc34d2d";

    private static final String APP_ID_APP = "wxcfb017aebb7443bd";
    private static final String APP_SECRET_APP = "1a004c607e00c6cae96d42c23ef1596f";

    public static final String TEMPLATE_ID = "iNzdMn-1eB-_xX8apWQcHvra-hXbHAdNcbz7N0Q4pv0";//"6rhoD-Z1Fs3Dst7rZ_fZ-qpyir_9c5LnRoshaYw1HYw";

	/*public static final String APP_ID = "wx6282433c5178bd8b";
	public static final String APP_SECRET = "abcdefghijklmnopqrstuvwxyz123456";*/

    /**
     * 获取access_token
     */
    public static String getAccess_Token() {
        String post = prepayId("https://api.weixin.qq.com/cgi-bin/token?appid=" + APP_ID + "&secret=" + APP_SECRET + "&grant_type=client_credential", "POST", "");
        JSONObject jsonObject = (JSONObject) JSON.parse(post);
        String access_token = (String) jsonObject.get("access_token");

        return access_token;
    }

    public static String getAccess_TokenH5() {
        String post = prepayId("https://api.weixin.qq.com/cgi-bin/token?appid=" + APP_ID_H5 + "&secret=" + APP_SECRET_H5 + "&grant_type=client_credential", "POST", "");
        JSONObject jsonObject = (JSONObject) JSON.parse(post);
        String access_token = (String) jsonObject.get("access_token");

        return access_token;
    }

    /**
     * 微信获取token
     */
    public static JSONObject GetToken(String code) {

		/*String urlStr = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APP_ID + "&secret=" + APP_SECRET
				+ "&code=" + code + "&grant_type=authorization_code";*/

        //https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        String urlStr = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APP_ID + "&secret=" + APP_SECRET
                + "&js_code=" + code + "&grant_type=authorization_code";
        String str = prepayId(urlStr, "GET", null);
        System.out.println(str);
        return JSONObject.parseObject(str);
    }

    public static JSONObject GetTokenForApp(String code) {

		/*String urlStr = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APP_ID + "&secret=" + APP_SECRET
				+ "&code=" + code + "&grant_type=authorization_code";*/

        //https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        String urlStr = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APP_ID_APP + "&secret=" + APP_SECRET_APP
                + "&js_code=" + code + "&grant_type=authorization_code";
        String str = prepayId(urlStr, "GET", null);
        System.out.println(str);
        return JSONObject.parseObject(str);
    }

    /**
     * 微信获取token
     */
    public static JSONObject GetTokenByH5(String code) throws Exception {
        String urlStr = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + APP_ID_H5 + "&secret=" + APP_SECRET_H5
                + "&code=" + code + "&grant_type=authorization_code";
        String str = HttpRequestUtils.doGet(urlStr, "utf-8");
        System.out.println(str);
        return JSONObject.parseObject(str);
    }


    /**
     * 获取微信个人信息
     */
    public static JSONObject GetUserInfo(String openId, String access_token) {
        String urlStr = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openId;
        String str = prepayId(urlStr, "GET", null);
        System.out.println(str);
        return JSONObject.parseObject(str);
    }

    /**
     * 获取微信个人信息
     */
    public static JSONObject GetUserInfoPro(String encryptedData, String session_key, String iv) {
        JSONObject jsonObject = new JSONObject();
        try {
            Thread.sleep(500);
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                jsonObject.put("status", 1);
                jsonObject.put("msg", "解密成功");

                JSONObject userInfoJSON = JSONObject.parseObject(result);
                jsonObject.put("nickName", userInfoJSON.get("nickName"));
                jsonObject.put("gender", userInfoJSON.get("gender"));
                jsonObject.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                jsonObject.put("unionid", userInfoJSON.get("unionId"));
                System.out.println(jsonObject);
                return jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonObject.put("status", 0);
        jsonObject.put("msg", "解密失败");
        return jsonObject;
    }

    /**
     * 验证access_token是否有效
     */
    public static String CheckAccessToken(String openId, String access_token) {
        String urlStr = "https://api.weixin.qq.com/sns/auth?access_token=" + access_token + "&openid=" + openId;
        String str = prepayId(urlStr, "GET", null);
        System.out.println(str);
        return str;
    }

    /**
     * 刷新或续期access_token使用
     */
    public static String RefreshToken(String refresh_token) {
        String urlStr = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + APP_ID
                + "&grant_type=refresh_token&refresh_token=" + refresh_token;
        String str = prepayId(urlStr, "GET", null);
        System.out.println(str);
        return str;
    }

    public static JSONObject getSubUserInfo(String access_token,String openId){
        String urlStr = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openId+"&lang=zh_CN ";
        String str = prepayId(urlStr, "GET", null);
        System.out.println(str);
        return JSONObject.parseObject(str);
    }

    /**
     * 图片下载到本地服务器
     */
    public static String getImageByUrl(String imageurl, String fileName) {
        try {
            // 构造URL
            URL url = new URL(imageurl);
            // 打开连接
            URLConnection con = url.openConnection();
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            OutputStream os = new FileOutputStream(fileName);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * 提交https请求
     *
     * @param requestUrl
     * @param requestMethod
     * @param outputStr
     * @return
     */
    private static String prepayId(String requestUrl, String requestMethod, String outputStr) {
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
            // log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            // log.error("https request error:{}", e);
        }
        return buffer.toString();
    }

}
