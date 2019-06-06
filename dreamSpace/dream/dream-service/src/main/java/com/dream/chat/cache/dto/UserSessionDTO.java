package com.dream.chat.cache.dto;

import com.dream.chat.constant.RedisConstant;
import com.dream.common.core.util.MathUtil;
import com.dream.common.core.util.MmlJsonUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author lw
 * @since 2018-10-29
 */
@Getter
@Setter
/*@JsonIgnoreProperties(ignoreUnknown = true)*/
public class UserSessionDTO extends BaseRedisDTO{

    private final static String namespace = RedisConstant.USER_SESSION;

    private Integer identity;

    public UserSessionDTO(String key){
        super(key);
    }

    public UserSessionDTO(String sessionId,String userId, String userName, String avatarUrl,Integer gender,String phoneNumber){
        super(sessionId,namespace);
        this.phoneNumber = phoneNumber;
        this.sessionId = sessionId;
        this.avatarUrl = avatarUrl;
        //this.clientIP = clientIP;
        this.userId = userId;
        this.userName = userName;
        this.gender = gender;
    }

    public UserSessionDTO(){}


    @Override
    public String value() {
        return MmlJsonUtil.objToJson(this);
    }

    private String sessionId;
    private String phoneNumber;
    /*private String clientIP;*/
    private String userId;
    private String userName;
    private String avatarUrl;
    private Integer gender;
    /*private String address;
    private int age;*/

}
