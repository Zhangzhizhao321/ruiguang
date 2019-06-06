package com.dream.chat.cache.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 *
 * </p>
 *
 * @author lw
 * @since 2018-10-29
 */
public abstract class BaseRedisDTO {

//    private static final String baseNamespace="chat:";

    private String key;
    private String namespace="";

    BaseRedisDTO(){}

     BaseRedisDTO(String key){
        this.key=key;
    }

    BaseRedisDTO(String key, String namespace){
        this.key=key;
        if(StringUtils.isNotBlank(namespace)){
            this.namespace=namespace+":";
        }
    }

    public String key(){
        return namespace+key;
    }

    public abstract String value();

}
