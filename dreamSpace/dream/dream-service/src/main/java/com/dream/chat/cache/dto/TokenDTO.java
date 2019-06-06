package com.dream.chat.cache.dto;

import com.dream.chat.constant.RedisConstant;
import com.dream.common.core.util.MmlJsonUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 短信传输对象
 * </p>
 *
 * @author lw
 * @since 2018-10-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TokenDTO extends BaseRedisDTO {
    private final static String namespace = RedisConstant.TOKEN;
    private String accessToken;


    public TokenDTO(){}

    public TokenDTO(String key){super(key);}
    public TokenDTO(String key, String accessToken){
        super(key, RedisConstant.TOKEN);
        this.accessToken = accessToken;

    }

    @Override
    public String value() {
        return MmlJsonUtil.objToJson(this);
    }
}
