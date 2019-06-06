package com.dream.chat.service;

import com.dream.chat.entity.UserWallet;
import com.dream.chat.vo.res.WalletResVo;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
public interface UserWalletService extends SuperService<UserWallet> {

    void addUserWallet(String userId);

    UserWallet addAmount(String userId,BigDecimal contribution,BigDecimal withdrawal,Integer helpCount);

    UserWallet subtractAmount(String userId,BigDecimal contribution,BigDecimal withdrawal);

    UserWallet getMyWallet(String userId);

    WalletResVo getMyWalletMsg(String userId);


}
