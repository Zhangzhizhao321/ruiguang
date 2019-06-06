package com.dream.chat.admin.controller;


import com.dream.chat.service.OrderService;
import com.dream.chat.service.ProjectService;
import com.dream.chat.service.UserService;
import com.dream.common.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@RestController
@RequestMapping("/bs-index/v1")
@Api(tags = "首页")
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/index")
    @ApiOperation(value = "首页", notes = "首页")
    public R index(){
        Integer newUser = userService.newNumber();
        Integer totalUser = userService.totalNumber();
        Integer newPro = projectService.newProjectCount();
        Integer totalPro = projectService.totalProjectCount();
        Integer orderNum = orderService.orderNumber();
        Integer totalOrderNum = orderService.totalOrderNumber();
        BigDecimal oneOrder = orderService.oneOrder();
        BigDecimal totalOrder = orderService.totalOrder();
        Map<String,Object> map = new HashMap<>();
        map.put("newUser",newUser == null ? 0 : newUser);
        map.put("totalUser",totalUser == null ? 0 : totalUser);
        map.put("newPro",newPro == null ? 0 : newPro);
        map.put("totalPro",totalPro == null ? 0 : totalPro);
        map.put("orderNum",orderNum == null ? 0 : orderNum);
        map.put("totalOrderNum",totalOrderNum == null ? 0 : totalOrderNum);
        map.put("oneOrder",oneOrder == null ? 0 : oneOrder);
        map.put("totalOrder",totalOrder == null ? 0 : totalOrder);
        return R.ok(map);
    }

}
