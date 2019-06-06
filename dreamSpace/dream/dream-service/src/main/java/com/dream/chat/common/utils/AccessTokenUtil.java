package com.dream.chat.common.utils;

import com.dream.chat.cache.RedisService;
import com.dream.chat.cache.dto.TokenDTO;
import com.dream.chat.cache.dto.TokenH5DTO;
import com.dream.chat.component.SpringContextHolder;
import com.dream.chat.constant.RedisConstant;
import com.dream.common.core.util.WeChatLoginUtil;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2018-11-14
 */
@Component
public class AccessTokenUtil {

    public static String getToken() {
        RedisService redisService = SpringContextHolder.getBean("redisService");

        TokenDTO tokenDTO = redisService.get(RedisConstant.TOKEN+":", TokenDTO.class);
        if (tokenDTO==null){
            tokenDTO=new TokenDTO();
            String access_token = WeChatLoginUtil.getAccess_Token();
            tokenDTO.setAccessToken(access_token);
            redisService.set(new TokenDTO("",access_token),7200, TimeUnit.SECONDS);
        }

        return tokenDTO.getAccessToken();
    }

    public static String getTokenH5() {
        RedisService redisService = SpringContextHolder.getBean("redisService");

        TokenH5DTO tokenDTO = redisService.get(RedisConstant.TOKEN_H5+":", TokenH5DTO.class);
        if (tokenDTO==null){
            tokenDTO=new TokenH5DTO();
            String access_token = WeChatLoginUtil.getAccess_TokenH5();
            tokenDTO.setAccessToken(access_token);
            redisService.set(new TokenH5DTO("",access_token),7200, TimeUnit.SECONDS);
        }

        return tokenDTO.getAccessToken();
    }

}
