package com.dream.chat.service.impl;

import com.dream.chat.entity.MoneyInout;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.MoneyInoutMapper;
import com.dream.chat.service.MoneyInoutService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@Service
public class MoneyInoutServiceImpl extends SuperServiceImpl<MoneyInoutMapper, MoneyInout> implements MoneyInoutService {

    @Override
    public MoneyInout addMoneyInount(String userId, String orderId, BigDecimal amount, Integer inout, String note) {
        MoneyInout moneyInout = new MoneyInout();
        moneyInout.setAmount(amount);
        moneyInout.setCreateTime(new Date());
        moneyInout.setInount(inout);
        moneyInout.setNote(note);
        moneyInout.setOrderId(orderId);
        moneyInout.setUserId(userId);
        this.save(moneyInout);
        return moneyInout;
    }
}
