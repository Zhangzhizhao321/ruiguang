package com.dream.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dream.chat.cache.RedisService;
import com.dream.chat.cache.dto.BackstageUserSessionDTO;
import com.dream.chat.entity.SystemUser;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.SystemUserMapper;
import com.dream.chat.service.SystemUserService;
import com.dream.common.core.api.R;
import com.dream.common.core.util.SecurityHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-17
 */
@Service
public class SystemUserServiceImpl extends SuperServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

    @Autowired
    private RedisService redisService;

    @Override
    public SystemUser getUserByMobile(String mobile) {
        return this.getOne(new QueryWrapper<SystemUser>().eq(SystemUser.MOBILE,mobile));
    }

    @Override
    public R register(String mobile, String password) {
        SystemUser systemUser = this.getUserByMobile(mobile);
        if(systemUser != null){
            return R.failed("用户已存在");
        }
        systemUser = new SystemUser();
        String pass = SecurityHandler.encodePassword(password,mobile);
        systemUser.setCreateTime(new Date());
        systemUser.setLastLoginTime(new Date());
        systemUser.setMobile(mobile);
        systemUser.setPassword(pass);
        this.save(systemUser);
        return R.ok("注册成功");
    }

    @Override
    public R login(String mobile, String password) {
        SystemUser systemUser = this.getUserByMobile(mobile);
        if(systemUser == null){
            return R.failed("未找到该用户");
        }
        boolean flag = SecurityHandler.verifyPassword(mobile,password,systemUser.getPassword());
        if(flag){
            BackstageUserSessionDTO backstageUserSessionDTO = new BackstageUserSessionDTO(systemUser.getId(),systemUser.getMobile());
            redisService.set(backstageUserSessionDTO,1, TimeUnit.DAYS);
            return R.ok(backstageUserSessionDTO);
        }
        return R.failed("用户名或密码错误");
    }
}
