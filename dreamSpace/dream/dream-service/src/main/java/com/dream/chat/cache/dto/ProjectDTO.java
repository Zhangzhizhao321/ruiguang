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
public class ProjectDTO extends BaseRedisDTO{

    private final static String namespace = RedisConstant.PROJECT;

    private Integer identity;

    public ProjectDTO(String key){
        super(key);
    }

    public ProjectDTO(String sessionId, String userId, String projectName){
        super(sessionId,namespace);
        this.sessionId = sessionId;
        this.userId = userId;
        this.projectName = projectName;
    }

    public ProjectDTO(){}


    @Override
    public String value() {
        return MmlJsonUtil.objToJson(this);
    }

    private String sessionId;
    private String userId;
    /*private String clientIP;*/
    private String projectName;
    /*private String address;
    private int age;*/

}
