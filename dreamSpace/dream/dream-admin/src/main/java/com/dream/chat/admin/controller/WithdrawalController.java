package com.dream.chat.admin.controller;


import com.dream.chat.entity.Withdrawal;
import com.dream.chat.service.OrderService;
import com.dream.chat.service.WithdrawalService;
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
@RequestMapping("/bs-withdraw/v1")
@Api(tags = "提现订单")
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @PostMapping("/getWithdrawList")
    @ApiOperation(value = "提现订单", notes = "提现订单")
    public R getWithdraList(BsOrderReqVo vo){
        return R.ok(withdrawalService.getBsWithdrawList(vo));
    }

    @PostMapping("/updateWithdrawStatus")
    @ApiOperation(value = "修改订单状态", notes = "修改订单状态")
    public R updateWithdrawStatus(String id,Integer status,String remark){
        return R.ok(withdrawalService.updateWithdrawStatus(id,status,remark));
    }

}
