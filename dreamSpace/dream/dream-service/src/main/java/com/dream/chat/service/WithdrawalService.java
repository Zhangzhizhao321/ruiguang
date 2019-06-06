package com.dream.chat.service;

import com.dream.chat.entity.Withdrawal;
import com.dream.chat.vo.req.BsOrderReqVo;
import com.dream.chat.vo.req.WithdrawlReqVo;
import com.dream.chat.vo.res.BsWithdrawResVo;
import com.dream.chat.vo.res.WalletResVo;
import com.dream.chat.vo.res.WithdrawResVo;
import com.dream.common.core.api.R;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-10
 */
public interface WithdrawalService extends SuperService<Withdrawal> {

    Withdrawal createWithdrawalOrder(WithdrawlReqVo withdrawlReqVo);

    boolean withdrawal(WithdrawlReqVo vo);

    Map getBsWithdrawList(BsOrderReqVo vo);

    List<WithdrawResVo> getWithdrawInount(String userId, Long current, Long size);

    Withdrawal updateWithdrawStatus(String id,Integer status,String remark);

}
