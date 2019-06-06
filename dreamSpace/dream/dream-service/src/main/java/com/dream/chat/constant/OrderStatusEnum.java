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
public enum OrderStatusEnum {
    PENDING_PAY(0,"未支付"),PAYED(1,"已支付"),PAYED_FAIL(2,"支付失败"),REFUND(3,"退款"),REFUND_FAIL(4,"退款失败"),
    PENDING_SEND(0,"待发货"),SENDED(1,"已发货未收货"),RECEIVED(2,"已收货未评价"),COMMENTED(3,"已评价"),EXCHANGE(4,"退换货"),INVALID(5,"失效订单");

    public Integer code;
    public String alias;

    OrderStatusEnum(Integer code, String alias){
        this.code=code;
        this.alias=alias;
    }

    public static OrderStatusEnum of(int code) {
        for (OrderStatusEnum type : values()) {
            if (code == type.code) {
                return type;
            }
        }
        return PAYED_FAIL;
    }
}
