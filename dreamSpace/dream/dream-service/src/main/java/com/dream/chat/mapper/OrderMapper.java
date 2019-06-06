package com.dream.chat.mapper;

import com.dream.chat.entity.Order;
import com.dream.chat.vo.req.BsOrderReqVo;
import com.dream.chat.vo.res.BsOrderResVo;
import com.dream.chat.vo.res.UserProjectListResVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
public interface OrderMapper extends SuperMapper<Order> {

    List<UserProjectListResVo> getMyHelpedProject(@Param("userId")String userId,@Param("current")Long current,@Param("size")Long size);

    List<BsOrderResVo> getBsOrderList(@Param("vo")BsOrderReqVo vo);

    BigDecimal oneOrder();

    BigDecimal totalOrder();
}
