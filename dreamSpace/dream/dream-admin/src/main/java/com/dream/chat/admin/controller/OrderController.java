package com.dream.chat.admin.controller;


import com.dream.chat.service.OrderService;
import com.dream.chat.service.SystemUserService;
import com.dream.chat.vo.req.BsOrderReqVo;
import com.dream.common.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@RestController
@RequestMapping("/bs-order/v1")
@Api(tags = "订单")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/getOrderList")
    @ApiOperation(value = "订单", notes = "订单")
    public R getOrderList(BsOrderReqVo vo){
        return R.ok(orderService.getBsOrderList(vo));
    }

}
