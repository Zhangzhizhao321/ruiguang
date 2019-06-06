package com.dream.chat.mapper;

import com.dream.chat.entity.Withdrawal;
import com.dream.chat.vo.req.BsOrderReqVo;
import com.dream.chat.vo.res.BsWithdrawResVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangjing
 * @since 2019-05-10
 */
public interface WithdrawalMapper extends SuperMapper<Withdrawal> {

    List<BsWithdrawResVo> getBsWithdrawList(@Param("vo")BsOrderReqVo vo);

}
