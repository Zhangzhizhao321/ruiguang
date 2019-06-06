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
public enum WithdrawTypeEnum {
    BANK(1,"银行卡");
    public Integer code;
    public String alias;

    WithdrawTypeEnum(Integer code, String alias){
        this.code=code;
        this.alias=alias;
    }

    public static WithdrawTypeEnum of(int code) {
        for (WithdrawTypeEnum type : values()) {
            if (code == type.code) {
                return type;
            }
        }
        return BANK;
    }
}
