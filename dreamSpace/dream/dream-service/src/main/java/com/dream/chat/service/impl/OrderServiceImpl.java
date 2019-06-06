package com.dream.chat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.common.utils.PayUtil;
import com.dream.chat.common.utils.WechatProOverUtil;
import com.dream.chat.common.utils.WechatUtil;
import com.dream.chat.constant.NotifyConstant;
import com.dream.chat.constant.OrderStatusEnum;
import com.dream.chat.entity.Order;
import com.dream.chat.entity.Project;
import com.dream.chat.entity.User;
import com.dream.chat.entity.UserProject;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.OrderMapper;
import com.dream.chat.mapper.ProjectMapper;
import com.dream.chat.mapper.UserMapper;
import com.dream.chat.mapper.UserProjectMapper;
import com.dream.chat.service.MoneyInoutService;
import com.dream.chat.service.OrderService;
import com.dream.chat.service.ProjectService;
import com.dream.chat.service.UserWalletService;
import com.dream.chat.vo.req.BsOrderReqVo;
import com.dream.chat.vo.req.OrderReqVo;
import com.dream.chat.vo.res.BsOrderResVo;
import com.dream.chat.vo.res.ProjectDetailResVo;
import com.dream.chat.vo.res.UserProjectListResVo;
import com.dream.common.core.api.R;
import com.dream.common.core.exception.SDKRuntimeException;
import com.dream.common.core.util.PageUtil;
import com.dream.common.core.util.SyncLock;
import com.dream.common.core.util.TimeTools;
import com.sun.org.apache.xpath.internal.operations.Or;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weixin4j.WeixinException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@Service
public class OrderServiceImpl extends SuperServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private UserProjectMapper userProjectMapper;

    @Autowired
    private MoneyInoutService moneyInoutService;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserWalletService userWalletService;

    @Resource
    private OrderMapper orderMapper;

    @Override
    @Transactional
    public Boolean notifyOrder(Map<String, String> resMap) {
        try {
            String bn = resMap.get("out_trade_no");
            String json = resMap.get("attach");
            JSONObject jsonObject = JSONObject.parseObject(json);
            SyncLock.lock("notify" + bn);

            Order order = this.getOne(new QueryWrapper<Order>().eq(Order.BN, bn));
            if (order == null) {
                return false;
            }
            if (order.getStatus() != OrderStatusEnum.PENDING_PAY.code) {
                return false;
            }
            order.setStatus(OrderStatusEnum.PAYED.code);
            order.setUpdateTime(new Date());
            this.updateById(order);

            UserProject userProject = userProjectMapper.selectById(jsonObject.getString("affairId"));
            userProject.setHelpCount(userProject.getHelpCount() + 1);
            userProject.setNowAmount(userProject.getNowAmount().add(order.getAmount()));
            userProject.setUpdateTime(new Date());
            userProjectMapper.updateById(userProject);

            //添加流水
            //添加付款人流水
            userWalletService.addAmount(order.getUserId(), order.getAmount(), BigDecimal.ZERO, 1);
            moneyInoutService.addMoneyInount(order.getUserId(), order.getId(), order.getAmount(), 1, "捐款给" + userProject.getUserName() + order.getAmount() + "元");
            //添加收款人流水
            User user = userMapper.selectById(order.getUserId());
            userWalletService.addAmount(userProject.getUserId(), BigDecimal.ZERO, order.getAmount(), 0);
            moneyInoutService.addMoneyInount(userProject.getUserId(), order.getId(), order.getAmount(), 0, "收到来自" + user.getUserName() + "的捐款" + order.getAmount() + "元");

            User user1 = userMapper.selectById(userProject.getUserId());
            Project project = projectService.getById(userProject.getProjectId());

            if (StringUtils.isNotBlank(user1.getWxH5Openid())) {
                WechatUtil.registTemplate(user1, order.getIsAnonymous() != null && order.getIsAnonymous() == 1 ? "有人" : user.getUserName(), project.getTitle(), order.getAmount());
            }
            if (userProject.getNowAmount().compareTo(userProject.getTargetAmount()) >= 0) {
                projectService.overProject(userProject.getUserId(), project);
                if (StringUtils.isNotBlank(user1.getWxH5Openid())) {
                    WechatProOverUtil.registTemplate(user1, project.getTitle(), String.valueOf(userProject.getHelpCount()));
                }
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SyncLock.unSyncLock("notify" + resMap.get("out_trade_no"));
        }
        return false;
    }

    @Override
    public Order createOrder(OrderReqVo orderReqVo) {
        Order order = new Order();
        order.setBn(Order.generateBn());
        order.setIsAnonymous(orderReqVo.getIsAnonymous());
        order.setAmount(orderReqVo.getAmount());
        order.setCreateTime(new Date());
        order.setProjectId(orderReqVo.getProjectId());
        order.setStatus(OrderStatusEnum.PENDING_PAY.code);
        order.setUserProjectId(orderReqVo.getUserProjectId());
        order.setUpdateTime(new Date());
        order.setUserId(orderReqVo.getUserId());
        this.save(order);
        return order;
    }

    @Override
    public R submitOrder(OrderReqVo orderReqVo, HttpServletRequest request) {
        Order order = this.createOrder(orderReqVo);
        //小程序支付
        Map<String, String> map = null;
        /*map = new HashMap<>();
        map.put("out_trade_no",order.getBn());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("affairId",orderReqVo.getUserProjectId());
        map.put("attach",jsonObject.toString());
        this.notifyOrder(map);*/
        try {
            map = PayUtil.submitOrderH5WX4j(order.getBn(), order.getAmount(), request.getRemoteAddr(), NotifyConstant.WX_PAY_NOTIFY, "拼梦想-捐款", orderReqVo.getSessionId(), "JSAPI", orderReqVo.getUserProjectId());
            if (map == null) {
                order.setStatus(OrderStatusEnum.PAYED_FAIL.code);
                order.setUpdateTime(new Date());
                this.updateById(order);
            }
        } catch (WeixinException e) {
            e.printStackTrace();
        } catch (SDKRuntimeException e) {
            e.printStackTrace();
        }
        return R.ok(map);
    }

    @Override
    public List<UserProjectListResVo> getMyHelpedProjects(String userId, Long current, Long size) {
        List<UserProjectListResVo> myHelpedProject = orderMapper.getMyHelpedProject(userId, size * (current - 1), size);
        Map<String, UserProjectListResVo> map = new HashMap<>();
        for (UserProjectListResVo vo : myHelpedProject) {
            Integer days = TimeTools.differentDays(new Date(), vo.getOverTime());
            vo.setOverDays(days < 0 ? 0 : days);
            UserProjectListResVo voMap = map.get(vo.getId());
            if (voMap == null) {
                map.put(vo.getId(), vo);
            } else {
                voMap.setAmount(voMap.getAmount().add(vo.getAmount()));
                map.put(vo.getId(), voMap);
            }
        }

        List<UserProjectListResVo> result = new ArrayList<>();
        for (String key : map.keySet()) {
            result.add(map.get(key));
        }
        return result;
    }

    @Override
    public Integer getMyHelpedProCount(String userId) {
        List<Order> orders = this.list(new QueryWrapper<Order>().eq(Order.USER_ID, userId).eq(Order.STATUS, 1));
        return orders.size();
    }

    @Override
    public Map getBsOrderList(BsOrderReqVo vo) {
        List<BsOrderResVo> bsOrderList = orderMapper.getBsOrderList(vo);
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (BsOrderResVo bs : bsOrderList) {
            bs.setStatus(OrderStatusEnum.of(Integer.parseInt(bs.getStatus())).alias);
            totalAmount = totalAmount.add(bs.getAmount() == null ? BigDecimal.ZERO : bs.getAmount());
        }
        Map<String, Object> map = PageUtil.ListByPage(vo.getCurrent().intValue(), vo.getSize().intValue(), bsOrderList);
        map.put("totalAmount",totalAmount);
        return map;
    }

    @Override
    public Integer orderNumber() {
        String day = TimeTools.format1(new Date());
        List<Order> orderList = this.list(new QueryWrapper<Order>().like(Order.CREATE_TIME, day).eq(Order.STATUS, OrderStatusEnum.PAYED.code));
        return orderList.size();
    }

    @Override
    public Integer totalOrderNumber() {
        List<Order> orderList = this.list(new QueryWrapper<Order>().eq(Order.STATUS, OrderStatusEnum.PAYED.code));
        return orderList.size();
    }

    @Override
    public BigDecimal oneOrder() {
        BigDecimal oneOrderMoney = orderMapper.oneOrder();
        return oneOrderMoney;
    }

    @Override
    public BigDecimal totalOrder() {
        BigDecimal totalOrderMoney = orderMapper.totalOrder();
        return totalOrderMoney;
    }
}
