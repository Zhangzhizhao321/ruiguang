package com.dream.chat.service;

import com.dream.chat.entity.UserBank;
import com.dream.chat.vo.req.UserBankReqVo;
import com.dream.chat.vo.res.UserBankResVo;
import com.dream.common.core.api.R;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-10
 */
public interface UserBankService extends SuperService<UserBank> {

    R verifyBankCard(UserBankReqVo userBankReqVo);

    R bindBankCard(UserBankReqVo userBankReqVo);

    UserBank addUserBank(UserBankReqVo userBankReqVo);

    List<UserBankResVo> getMyBankCards(String userId);

    R bindInstitutionBankCard(UserBankReqVo userBankReqVo);
}
