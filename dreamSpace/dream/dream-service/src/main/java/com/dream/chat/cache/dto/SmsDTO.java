package com.dream.chat.cache.dto;

import com.dream.common.core.util.MmlJsonUtil;
import com.dream.chat.constant.RedisConstant;
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
public class SmsDTO extends BaseRedisDTO {

    private String singName;
    private String phone;
    private String code;
    private String templateCode;
    private String templateParam;
    private String outId;

    public SmsDTO(){}

    public SmsDTO(String key){super(key);}
    public SmsDTO(String key,String phone,String code){
        super(key, RedisConstant.SMS);
        this.phone = phone;
        this.code = code;
    }

    @Override
    public String value() {
        return MmlJsonUtil.objToJson(this);
    }
}
