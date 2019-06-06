package com.dream.chat.service;

import com.dream.chat.entity.Order;
import com.dream.chat.vo.req.BsOrderReqVo;
import com.dream.chat.vo.req.OrderReqVo;
import com.dream.chat.vo.res.BsOrderResVo;
import com.dream.chat.vo.res.UserProjectListResVo;
import com.dream.common.core.api.R;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
public interface OrderService extends SuperService<Order> {

    Boolean notifyOrder(Map<String, String> resMap);

    Order createOrder(OrderReqVo orderReqVo);

    R submitOrder(OrderReqVo orderReqVo, HttpServletRequest request);

    List<UserProjectListResVo> getMyHelpedProjects(String userId,Long current,Long size);

    Integer getMyHelpedProCount(String userId);

    Map getBsOrderList(BsOrderReqVo vo);

    Integer orderNumber();

    Integer totalOrderNumber();

    BigDecimal oneOrder();

    BigDecimal totalOrder();
}
