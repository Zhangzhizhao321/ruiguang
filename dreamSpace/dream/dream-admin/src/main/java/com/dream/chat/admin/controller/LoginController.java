package com.dream.chat.admin.controller;


import com.dream.chat.service.SystemUserService;
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
@RequestMapping("/bs-login/v1")
@Api(tags = "登录注册")
public class LoginController {

    @Autowired
    private SystemUserService systemUserService;

    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "注册")
    public R register(String mobile,String passWord){
        return systemUserService.register(mobile,passWord);
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public R login(String mobile,String passWord){
        return systemUserService.login(mobile,passWord);
    }



}
