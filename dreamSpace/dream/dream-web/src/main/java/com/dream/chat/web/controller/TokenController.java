package com.dream.chat.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.dream.chat.common.utils.AccessTokenUtil;
import com.dream.chat.common.utils.CheckUtil;
import com.dream.chat.common.utils.MessgaeUtils;
import com.dream.chat.constant.LoginEnum;
import com.dream.chat.service.UserService;
import com.dream.chat.vo.req.LoginReqVo;
import com.dream.common.core.util.WeChatLoginUtil;
import com.dream.common.core.util.fuiou.MapChangeXmlUtil;
import com.dream.common.core.util.wechat.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static com.dream.chat.common.utils.AccessTokenUtil.getTokenH5;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@RestController
@RequestMapping("/token/v1")
public class TokenController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/token", method = {RequestMethod.GET, RequestMethod.POST})
    public void token(HttpServletResponse response, HttpServletRequest request) throws IOException {

        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");  //微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
        response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        boolean isGet = request.getMethod().toLowerCase().equals("get");

        PrintWriter out = response.getWriter();

    /*    if (result.hasErrors()) {
			customLogger.log("参数有误", LevelType.WECHATLOG);

			return;
		}

		customLogger.log(tokenParam.toString() + " wechatToken" + Configuration.getProperty("weixin4j.token"),
				LevelType.WECHATLOG);
		if (TokenUtil.checkSignature(Configuration.getProperty("weixin4j.token"), tokenParam.getSignature(),
				tokenParam.getTimestamp(), tokenParam.getNonce())) {
			try {
				response.getWriter().write(tokenParam.getEchostr());
				//System.out.println("token验证通过");
				customLogger.log("token验证通过", LevelType.WECHATLOG);
			} catch (IOException e) {
				//System.out.println("出现异常");
				customLogger.log("出现异常" + e.getMessage(), LevelType.WECHATLOG);
				e.printStackTrace();
			}

		} else {
			System.out.println("token验证不通过");
			//customLogger.log("token验证不通过", LevelType.WECHATLOG);
		}*/

        try {
            if (isGet) {
                //System.out.println("isGet");
                String signature = request.getParameter("signature");// 微信加密签名
                String timestamp = request.getParameter("timestamp");// 时间戳
                String nonce = request.getParameter("nonce");// 随机数
                String echostr = request.getParameter("echostr");//随机字符串

                // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
                if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
                    //LOGGER.info("Connect the weixin server is successful.");
                    response.getWriter().write(echostr);
                }
            } else {
                System.out.println("post");
                String respMessage = "异常消息！";

                try {
                    /*respMessage =*/
                    weixinPost(request, response);
                    //out.write(respMessage);
	                    /*LOGGER.info("The request completed successfully");
	                    LOGGER.info("to weixin server "+respMessage);*/
                } catch (Exception e) {
                    /*LOGGER.error("Failed to convert the message from weixin!"); */
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            // LOGGER.error("Connect the weixin server is error.");
            e.printStackTrace();
        } finally {
            out.close();
        }

    }

    public void weixinPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //System.out.println("response:"+JSONObject.toJSONString(response));
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.reset();
        PrintWriter out = response.getWriter();

        String str = "";

//将request请求，传到Message工具类的转换方法中，返回接收到的Map对象
        try {
            Map<String, String> map = MessgaeUtils.xmlToMap(request);

//从集合中，获取XML各个节点的内容

            String ToUserName = map.get("ToUserName");

            String FromUserName = map.get("FromUserName");

            String CreateTime = map.get("CreateTime");

            String MsgType = map.get("MsgType");

            String Content = map.get("Content");

            String MsgId = map.get("MsgId");
            //这里处理文本消息
            if (MsgType.equalsIgnoreCase(MessgaeUtils.MESSAGE_TEXT)) {
                //用户发来1
                /*if ("1".equalsIgnoreCase(Content)){
                    //微信公众号回复
                    str=MessgaeUtils.initText(ToUserName,FromUserName,"哦");
                }else if ("2".equalsIgnoreCase(Content)){
                    str=MessgaeUtils.initText(ToUserName,FromUserName,"哦哦哦");
                }*/

            } else if (MsgType.equalsIgnoreCase(MessgaeUtils.MESSAGE_EVENT)) {
                //这里处理事件如关注、取消关注、点击菜单等多种功能
                /*	System.out.println(map.toString()+"event:"+map.get("Event"));*/
                String eventType = map.get("Event");
                //订阅事件
                if (eventType.equalsIgnoreCase(MessgaeUtils.MESSAGE_SUBSCRIBE)) {
                    //System.out.println("订阅事件");
                    String access_token = AccessTokenUtil.getTokenH5();
                    JSONObject jsonObject = WeChatLoginUtil.getSubUserInfo(access_token,FromUserName);
                    String unionid = jsonObject.getString("unionid");
                    String headImg = jsonObject.getString("headimgurl");
                    String sex = jsonObject.getString("sex");
                    String nickName = jsonObject.getString("nickname");
                    LoginReqVo loginReqVo = new LoginReqVo();
                    loginReqVo.setH5OpenId(FromUserName);
                    loginReqVo.setUnionId(unionid);
                    loginReqVo.setAvatarUrl(headImg);
                    loginReqVo.setGender(sex != null ? Integer.parseInt(sex) : 1);
                    loginReqVo.setNickName(nickName);
                    loginReqVo.setFlag(LoginEnum.H5.code);
                    userService.userLogin(loginReqVo);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        out.write(str);
        out.flush();
        out.close();

    }

}
