package com.dream.chat.web.controller;


import com.dream.chat.cache.dto.UserSessionDTO;
import com.dream.chat.service.OrderService;
import com.dream.chat.vo.req.OrderReqVo;
import com.dream.common.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@RestController
@RequestMapping("/order/v1")
@Api(tags = "订单")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submitOrder")
    @ApiOperation(value = "个人项目捐款", notes = "个人项目捐款")
    public R submitOrder(OrderReqVo orderReqVo, HttpServletRequest request) {
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        orderReqVo.setUserId(userSessionDTO.getUserId());
        orderReqVo.setSessionId(userSessionDTO.getSessionId());
        return orderService.submitOrder(orderReqVo,request);
    }

    @PostMapping("/getMyHelpedProjects")
    @ApiOperation(value = "我帮助过的", notes = "我帮助过的")
    public R getMyHelpedProjects(HttpServletRequest request,Long current,Long size) {
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        return R.ok(orderService.getMyHelpedProjects(userSessionDTO.getUserId(),current,size));
    }




}
