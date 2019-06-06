package com.dream.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.entity.UserBank;
import com.dream.chat.entity.UserWallet;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.UserWalletMapper;
import com.dream.chat.service.UserBankService;
import com.dream.chat.service.UserWalletService;
import com.dream.chat.vo.res.UserBankResVo;
import com.dream.chat.vo.res.WalletResVo;
import com.dream.common.core.util.SyncLock;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@Service
public class UserWalletServiceImpl extends SuperServiceImpl<UserWalletMapper, UserWallet> implements UserWalletService {

    @Autowired
    private UserBankService userBankService;

    @Override
    public void addUserWallet(String userId) {
        UserWallet userWallet = new UserWallet();
        userWallet.setUserId(userId);
        this.save(userWallet);
    }

    @Override
    public UserWallet addAmount(String userId,BigDecimal contribution, BigDecimal withdrawal, Integer helpCount) {
        try {
            SyncLock.lock("Wl"+userId);
            UserWallet userWallet = this.getOne(new QueryWrapper<UserWallet>().eq(UserWallet.USER_ID,userId));
            userWallet.setContribution(userWallet.getContribution().add(contribution));
            userWallet.setBalance(userWallet.getBalance().add(withdrawal));
            userWallet.setHelpCount(userWallet.getHelpCount()+helpCount);
            userWallet.setTotal(userWallet.getTotal().add(withdrawal));
            userWallet.setUpdateTime(new Date());
            this.updateById(userWallet);
            return userWallet;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SyncLock.unSyncLock("Wl"+userId);
        }
        return null;
    }

    @Override
    public UserWallet subtractAmount(String userId, BigDecimal contribution, BigDecimal withdrawal) {
        UserWallet userWallet = this.getOne(new QueryWrapper<UserWallet>().eq(UserWallet.USER_ID,userId));
        userWallet.setContribution(userWallet.getContribution().subtract(contribution));
        userWallet.setWithdrawal(userWallet.getWithdrawal().subtract(withdrawal));
        userWallet.setUpdateTime(new Date());
        this.updateById(userWallet);
        return userWallet;
    }

    @Override
    public UserWallet getMyWallet(String userId) {
        return this.getOne(new QueryWrapper<UserWallet>().eq(UserWallet.USER_ID,userId));
    }

    @Override
    public WalletResVo getMyWalletMsg(String userId) {
        UserWallet userWallet = this.getMyWallet(userId);
        List<UserBankResVo> list = userBankService.getMyBankCards(userId);

        WalletResVo walletResVo = new WalletResVo();
        walletResVo.setCardCount(list.size());
        walletResVo.setWithdrawal(userWallet.getWithdrawal());
        walletResVo.setBalance(userWallet.getBalance());
        return walletResVo;
    }
}
