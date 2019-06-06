package com.dream.chat.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.cache.dto.UserSessionDTO;
import com.dream.chat.common.utils.FuiouUtil;
import com.dream.chat.entity.User;
import com.dream.chat.entity.UserBank;
import com.dream.chat.service.BankService;
import com.dream.chat.service.UserBankService;
import com.dream.chat.vo.req.UserBankReqVo;
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
@RequestMapping("/bank/v1")
@Api(tags = "银行卡")
public class BankController {

    @Autowired
    private UserBankService userBankService;

    @Autowired
    private BankService bankService;

    @PostMapping("/verifyBankCard")
    @ApiOperation(value = "验证银行卡", notes = "验证银行卡")
    public R verifyBankCard(HttpServletRequest request, UserBankReqVo userBankReqVo) {
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        userBankReqVo.setUserId(userSessionDTO.getUserId());
        return R.ok(userBankService.verifyBankCard(userBankReqVo));
    }

    @PostMapping("/bindBankCard")
    @ApiOperation(value = "用户绑定银行卡", notes = "用户绑定银行卡")
    public R bindBankCard(HttpServletRequest request, UserBankReqVo userBankReqVo) {
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        userBankReqVo.setUserId(userSessionDTO.getUserId());
        return R.ok(userBankService.bindBankCard(userBankReqVo));
    }

    @PostMapping("/getMyCardList")
    @ApiOperation(value = "我的银行卡列表", notes = "我的银行卡列表")
    public R getMyCardList(HttpServletRequest request) {
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        return R.ok(userBankService.getMyBankCards(userSessionDTO.getUserId()));
    }

    @PostMapping("/getBankList")
    @ApiOperation(value = "支持的银行", notes = "支持的银行")
    public R getBankList() {
       return R.ok(bankService.list(null));
    }

}
