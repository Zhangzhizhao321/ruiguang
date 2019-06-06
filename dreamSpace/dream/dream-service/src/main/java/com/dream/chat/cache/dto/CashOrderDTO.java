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
public class CashOrderDTO/* extends BaseRedisDTO*/{

  /*  private final static String namespace = RedisConstant.CASH_ORDER;

    public CashOrderDTO(String key){
        super(key);
    }

    public CashOrderDTO(CashOrder cashOrder){
        super(cashOrder.getBn(),namespace);
        this.cashOrder = cashOrder;
    }

    public CashOrderDTO(){}


    @Override
    public String value() {
        return MmlJsonUtil.objToJson(this);
    }

    private CashOrder cashOrder;*/

}
