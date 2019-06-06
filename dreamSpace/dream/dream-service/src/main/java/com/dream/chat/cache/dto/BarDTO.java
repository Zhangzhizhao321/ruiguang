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
public class BarDTO extends BaseRedisDTO{

    private final static String namespace = RedisConstant.BAR_SESSION;

    public BarDTO(String key){
        super(key);
    }

    public BarDTO(String userId, String userName, String barId, String barSeatNumber,String avatarUrl,String position,Integer gender){
        super(userId,barId);
        //this.phoneNumber = phoneNumber;
       // this.sessionId = sessionId;
        //this.clientIP = clientIP;
        this.userId = userId;
        this.userName = userName;
        this.barId = barId;
        this.barSeatNumber = barSeatNumber;
        this.avatarUrl=avatarUrl;
        this.position = position;
        this.gender = gender;
    }

    public BarDTO(){}


    @Override
    public String value() {
        return MmlJsonUtil.objToJson(this);
    }

    private String sessionId;
    /*private String phoneNumber;
    private String clientIP;*/
    private String userId;
    private String userName;
    private String barId;
    private String avatarUrl;
    private String barSeatNumber;
    private String position;
    private Integer gender;
    /*private String address;
    private int age;*/

}
