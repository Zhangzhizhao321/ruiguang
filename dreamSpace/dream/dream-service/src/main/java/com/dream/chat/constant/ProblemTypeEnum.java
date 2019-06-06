package com.dream.chat.constant;

import lombok.Getter;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2018-11-06
 */
@Getter
public enum ProblemTypeEnum {

    SECRETBOOK(0,"秘籍"),PROBLEM(1,"问题"),COMMENT(2,"留言"),SHARE(3,"分享")
    ,CAROUSEL(4,"轮播公告"),HOTNOTE(5,"热门显示文案"),NEWNOTE(6,"最新显示文案"),
    SERVICE_PHONE(7,"客服电话");

    public Integer code;
    public String alias;

    ProblemTypeEnum(Integer code, String alias){
        this.code=code;
        this.alias=alias;
    }
}
