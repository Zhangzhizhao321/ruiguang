package com.dream.chat.cache.dto;

import com.dream.chat.constant.RedisConstant;
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
public class BackstageUserSessionDTO extends BaseRedisDTO{

    private final static String namespace = RedisConstant.BACKSTAGEUSER_SESSION;

    //private Integer identity;

    public BackstageUserSessionDTO(String key){
        super(key);
    }

    public BackstageUserSessionDTO(String userId, String phoneNumber){
        super(userId,namespace);
        this.phoneNumber = phoneNumber;
        this.sessionId = userId;
        //this.avatarUrl = avatarUrl;
        //this.clientIP = clientIP;
        this.userId = userId;
        /*this.userName = userName;
        this.barId = barId;
        this.barSeatNumber = barSeatNumber;
        this.position = position;
        this.gender = gender;*/
    }

    public BackstageUserSessionDTO(){}


    @Override
    public String value() {
        return MmlJsonUtil.objToJson(this);
    }

    private String sessionId;
    private String phoneNumber;
    /*private String clientIP;*/
    private String userId;
    /*private String userName;*/
   /* private String barId;
    private String avatarUrl;
    private String barSeatNumber;
    private String position;
    private Integer gender;*/
    /*private String address;
    private int age;*/

}
