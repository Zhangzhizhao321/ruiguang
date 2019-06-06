package com.dream.chat.web.controller;


import com.dream.chat.cache.dto.UserSessionDTO;
import com.dream.chat.common.utils.FuiouUtil;
import com.dream.chat.entity.Withdrawal;
import com.dream.chat.service.UserWalletService;
import com.dream.chat.service.WithdrawalService;
import com.dream.chat.vo.req.WithdrawlReqVo;
import com.dream.common.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@RestController
@RequestMapping("/user-wallet/v1")
@Api(tags = "用户钱包")
public class UserWalletController {

    @Autowired
    private UserWalletService userWalletService;

    @Autowired
    private WithdrawalService withdrawalService;

    @PostMapping("/withdrawal")
    @ApiOperation(value = "提现", notes = "用户提现")
    public R withdrawal(HttpServletRequest request, WithdrawlReqVo vo) {
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        vo.setUserId(userSessionDTO.getUserId());
        return R.ok(withdrawalService.withdrawal(vo));
    }

    @PostMapping("/myWallet")
    @ApiOperation(value = "我的钱包", notes = "我的钱包")
    public R myWallet(HttpServletRequest request) {
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        return R.ok(userWalletService.getMyWalletMsg(userSessionDTO.getUserId()));
    }

    @PostMapping("/withdrawalInout")
    @ApiOperation(value = "提现明细", notes = "提现明细")
    public R withdrawal(HttpServletRequest request,Long current,Long size) {
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        return R.ok(withdrawalService.getWithdrawInount(userSessionDTO.getUserId(),current,size));
    }


}
