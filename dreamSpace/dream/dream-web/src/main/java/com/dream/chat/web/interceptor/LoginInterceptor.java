package com.dream.chat.web.interceptor;

import com.dream.chat.cache.RedisService;
import com.dream.chat.cache.dto.UserSessionDTO;
import com.google.gson.JsonObject;
import com.dream.chat.constant.ErrorContanst;
import com.dream.chat.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>
 *
 * </p>
 *
 * @author lw
 * @since 2018-10-30
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!isLogin(request, response)) {
            //responseJsonMessage(response, ErrorContanst.USER_UNLOGIN_JSON_CODE, "用户未登录");
            return false;
        }
        return true;
    }

    /**
     * 是否已登录
     *
     * @param request
     * @return
     */
    private boolean isLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionId = request.getHeader("sessionId");
        if(sessionId == null){
            responseJsonMessage(response, ErrorContanst.USER_UNLOGIN_JSON_CODE, "用户未登录");
            return false;
        }
        /*String obj =  redisService.get((RedisConstant.USER_SESSION+":"+sessionId)).toString();
        JSONObject json = JSON.parseObject(obj);
        UserDTO userDTO = JSON.toJavaObject(json,UserDTO.class);*/
        UserSessionDTO userDTO = redisService.get(RedisConstant.USER_SESSION+":"+sessionId,UserSessionDTO.class);
        /*if (!MathUtil.getIpAddr(request).equals(userDTO.getClientIP())) {
            return false;
        }*/
        if (userDTO != null) {
            request.setAttribute("UserSession", userDTO);
            redisService.setExpireSecond(RedisConstant.USER_SESSION+":"+sessionId,86400);
            //redisService.setExpireSecond(userDTO.getBarId()+":"+sessionId,18000);
            return true;
        } else {
            String requestType = request.getHeader("X-Requested-With");
            if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
                response.addHeader("loginStatus", "accessDenied");
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            } else {
                if (request.getMethod().equalsIgnoreCase("GET")) {
                    responseJsonMessage(response, ErrorContanst.URL_METHOD_NOPOST_JSON_CODE, "");
                    return false;
                } else {
                    responseJsonMessage(response, ErrorContanst.USER_UNLOGIN_JSON_CODE, "用户未登录");
                    return false;
                }
            }
        }

    }

    private void responseJsonMessage(HttpServletResponse response, String code, String msg) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        JsonObject obj = new JsonObject();
        obj.addProperty("code", code);
        obj.addProperty("msg", msg);
        PrintWriter writer;
        try {
            writer = response.getWriter();

            writer.write(obj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
