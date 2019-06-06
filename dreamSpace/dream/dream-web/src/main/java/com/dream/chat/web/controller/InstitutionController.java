package com.dream.chat.web.controller;


import com.dream.chat.cache.dto.UserSessionDTO;
import com.dream.chat.entity.UserBank;
import com.dream.chat.service.UserBankService;
import com.dream.chat.vo.req.UserBankReqVo;
import com.dream.common.core.api.R;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/institution/v1")
public class InstitutionController {

    @Autowired
    private UserBankService userBankService;

    @PostMapping("/verifyBankCard")
    @ApiOperation(value = "验证银行卡", notes = "验证银行卡")
    public R verifyBankCard(HttpServletRequest request, UserBankReqVo userBankReqVo) {
        return R.ok(userBankService.verifyBankCard(userBankReqVo));
    }

    @PostMapping("/bindBankCard")
    @ApiOperation(value = "机构绑定银行卡", notes = "机构绑定银行卡")
    public R bindBankCard(HttpServletRequest request, UserBankReqVo userBankReqVo) {
        return R.ok(userBankService.bindInstitutionBankCard(userBankReqVo));
    }

}
