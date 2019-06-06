package com.dream.chat.service;

import com.dream.chat.entity.MoneyInout;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
public interface MoneyInoutService extends SuperService<MoneyInout> {

    MoneyInout addMoneyInount(String userId, String orderId, BigDecimal amount,Integer inout,String note);

}
