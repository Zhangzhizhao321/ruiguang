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
public enum PayFlagEnum {

    PENDING_PAY(0,"未支付"),PAYED(1,"已支付");

    public Integer code;
    public String alias;

    PayFlagEnum(Integer code, String alias){
        this.code=code;
        this.alias=alias;
    }
}
