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
public class TokenH5DTO extends BaseRedisDTO {
    private final static String namespace = RedisConstant.TOKEN_H5;
    private String accessToken;


    public TokenH5DTO(){}

    public TokenH5DTO(String key){super(key);}
    public TokenH5DTO(String key, String accessToken){
        super(key, RedisConstant.TOKEN_H5);
        this.accessToken = accessToken;

    }

    @Override
    public String value() {
        return MmlJsonUtil.objToJson(this);
    }
}
