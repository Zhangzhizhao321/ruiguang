package com.dream.chat.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.constant.GenderEnum;
import com.dream.chat.entity.Project;
import com.dream.chat.entity.User;
import com.dream.chat.service.OrderService;
import com.dream.chat.service.ProjectService;
import com.dream.chat.service.UserService;
import com.dream.chat.vo.res.BsUserResVo;
import com.dream.common.core.api.R;
import com.dream.common.core.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/bs-user/v1")
@Api(tags = "用户")
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping("/userList")
    @ApiOperation(value = "用户列表", notes = "用户列表")
    public R userList(Integer current,Integer size,String startTime,String endTime,String phoneNumber,String idCard){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc(User.CREATE_TIME);
        if(StringUtils.isNotBlank(startTime)){
            queryWrapper.ge(User.CREATE_TIME,startTime);
        }
        if(StringUtils.isNotBlank(endTime)){
            queryWrapper.le(User.CREATE_TIME,endTime);
        }
        if(StringUtils.isNotBlank(phoneNumber)){
            queryWrapper.eq(User.PHONE_NUMBER,phoneNumber);
        }
        if(StringUtils.isNotBlank(idCard)){
            queryWrapper.eq(User.ID_CARD,idCard);
        }
        List<User> users = userService.list(queryWrapper);
        List<BsUserResVo> userResVos = new ArrayList<>();
        for(User user : users){
            BsUserResVo bsUserResVo = new BsUserResVo();
            BeanUtils.copyProperties(user,bsUserResVo);
            bsUserResVo.setSex(GenderEnum.of(user.getSex() != null ? user.getSex() : 1).alias);
            userResVos.add(bsUserResVo);
        }
        return R.ok(PageUtil.ListByPage(current,size,userResVos));
    }

}
