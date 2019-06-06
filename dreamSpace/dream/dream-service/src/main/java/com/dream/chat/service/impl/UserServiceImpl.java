package com.dream.chat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.cache.RedisService;
import com.dream.chat.cache.dto.UserSessionDTO;
import com.dream.chat.constant.GenderEnum;
import com.dream.chat.constant.LoginEnum;
import com.dream.chat.constant.RedisConstant;
import com.dream.chat.entity.User;
import com.dream.chat.entity.UserFormid;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.UserMapper;
import com.dream.chat.service.UserService;
import com.dream.chat.service.UserWalletService;
import com.dream.chat.vo.req.LoginReqVo;
import com.dream.chat.vo.res.UserPhoneResVo;
import com.dream.common.core.api.R;
import com.dream.common.core.util.AesCbcUtil;
import com.dream.common.core.util.MathUtil;
import com.dream.common.core.util.TimeTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private UserWalletService userWalletService;

    @Autowired
    private RedisService redisService;

    @Override
    public R userLogin(LoginReqVo loginReqVo) {
        //if (StringUtils.isNotBlank(loginReqVo.getOpenId())) {
            User user = null;
            if (StringUtils.isNotBlank(loginReqVo.getUnionId())) {
                user = this.getOne(new QueryWrapper<User>().eq(User.WX_UNIONID, loginReqVo.getUnionId()));
                if(user != null){
                    this.updateLoginUser(user, loginReqVo);
                    UserSessionDTO userSessionDTO = new UserSessionDTO(user.getWxOpenid(), user.getId(), user.getUserName(), user.getHeadPic(), user.getSex(), user.getPhoneNumber());
                    redisService.set(userSessionDTO, 24, TimeUnit.HOURS);
                    return R.ok(userSessionDTO);
                }
            }

            if (user == null) {
                if (StringUtils.isNotBlank(loginReqVo.getMobile())) {
                    user = this.getOne(new QueryWrapper<User>().eq(User.PHONE_NUMBER, loginReqVo.getMobile()));
                    //if (user == null) {
                    //user = this.getOne(new QueryWrapper<User>().eq(User.WX_OPENID, loginReqVo.getOpenId()));
                    if (user == null) {
                        //新用户
                        user = this.addUser(loginReqVo);
                        //添加用户钱包
                        userWalletService.addUserWallet(user.getId());

                    } else {
                        this.updateLoginUser(user, loginReqVo);
                    }
                    //}
                } else {
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    if (loginReqVo.getFlag() == LoginEnum.PROGRAM.code) {
                        queryWrapper.eq(User.WX_OPENID, loginReqVo.getOpenId());
                    }
                    if (loginReqVo.getFlag() == LoginEnum.H5.code) {
                        queryWrapper.eq(User.WX_H5_OPENID, loginReqVo.getH5OpenId());
                    }
                    if (loginReqVo.getFlag() == LoginEnum.APP.code) {
                        queryWrapper.eq(User.WX_H5_OPENID, loginReqVo.getAppOpenId());
                    }
                    user = this.getOne(queryWrapper);
                    if (user == null) {
                        //新用户
                        user = this.addUser(loginReqVo);
                        //添加用户钱包
                        userWalletService.addUserWallet(user.getId());
                    }
                }
            //}
            UserSessionDTO userSessionDTO = new UserSessionDTO(user.getWxOpenid(), user.getId(), user.getUserName(), user.getHeadPic(), user.getSex(), user.getPhoneNumber());
            redisService.set(userSessionDTO, 24, TimeUnit.HOURS);
            return R.ok(userSessionDTO);
        }
        return R.failed("没有获取到该用户信息");
    }

    @Override
    public User addUser(LoginReqVo loginReqVo) {
        User user = new User();
        user.setPhoneNumber(loginReqVo.getMobile());
        user.setWxOpenid(loginReqVo.getOpenId());
        user.setUserName(loginReqVo.getNickName());
        user.setHeadPic(loginReqVo.getAvatarUrl());
        user.setSex(loginReqVo.getGender());
        user.setCreateTime(new Date());
        user.setWxUnionid(loginReqVo.getUnionId());
        user.setUpdateTime(new Date());
        user.setInviteId(loginReqVo.getInviteId());
        user.setWxH5Openid(loginReqVo.getH5OpenId());
        user.setWxAppOpenid(loginReqVo.getAppOpenId());
        this.save(user);
        return user;
    }

    @Override
    public User updateLoginUser(User user, LoginReqVo loginReqVo) {
        user.setPhoneNumber(loginReqVo.getMobile());
        user.setWxOpenid(loginReqVo.getOpenId());
        user.setUserName(loginReqVo.getNickName());
        user.setHeadPic(loginReqVo.getAvatarUrl());
        user.setSex(loginReqVo.getGender());
        user.setCreateTime(new Date());
        user.setWxUnionid(loginReqVo.getUnionId());
        user.setUpdateTime(new Date());
        user.setInviteId(loginReqVo.getInviteId());
        user.setWxH5Openid(loginReqVo.getH5OpenId());
        this.updateById(user);
        return user;
    }

    @Override
    public UserSessionDTO getUserSessionById(String openId) {
        UserSessionDTO userSessionDTO = redisService.get(RedisConstant.USER_SESSION + ":" + openId, UserSessionDTO.class);
        return userSessionDTO;
    }

    @Override
    public User getUserByOpenid(String openId) {
        User user = this.getOne(new QueryWrapper<User>().eq(User.WX_OPENID, openId));
        return user;
    }

    @Override
    public R loginByMobile(String mobile) {
        List<User> users = this.list(new QueryWrapper<User>().eq(User.PHONE_NUMBER, mobile).orderByDesc(User.UPDATE_TIME));
        User user = null;
        if (users != null && users.size() > 0) {
            user = users.get(0);
        } else {
            LoginReqVo loginReqVo = new LoginReqVo();
            loginReqVo.setMobile(mobile);
            loginReqVo.setNickName("用户" + MathUtil.getSixNumber());
            loginReqVo.setGender(GenderEnum.MAN.code);
            user = this.addUser(loginReqVo);
            //添加用户钱包
            userWalletService.addUserWallet(user.getId());
        }
        UserSessionDTO userSessionDTO = new UserSessionDTO(mobile, user.getId(), user.getUserName(), user.getHeadPic(), user.getSex(), user.getPhoneNumber());
        redisService.set(userSessionDTO, 24, TimeUnit.HOURS);
        return R.ok(userSessionDTO);
    }

    @Override
    public UserPhoneResVo getUserPhone(String userId, String sessionid, String data, String iv) {
        try {
            String decrypt = AesCbcUtil.decrypt(data, sessionid, iv, "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(decrypt);
            if (jsonObject != null) {
                UserPhoneResVo userPhoneResVo = new UserPhoneResVo();
                userPhoneResVo.setPhoneNumber(jsonObject.getString("phoneNumber"));
                userPhoneResVo.setPurePhoneNumber(jsonObject.getString("purePhoneNumber"));
                userPhoneResVo.setCountryCode(jsonObject.getString("countryCode"));
                User user = this.getById(userId);
                user.setPhoneNumber(jsonObject.getString("phoneNumber"));
                this.updateById(user);
                return userPhoneResVo;
            }
            //System.out.println(decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Integer newNumber() {
        String day = TimeTools.format1(new Date());
        List<User> userList = this.list(new QueryWrapper<User>().like(User.CREATE_TIME, day));
        return userList.size();
    }

    @Override
    public Integer totalNumber() {
        List<User> userList = this.list(null);
        return userList.size();
    }

    @Override
    public User bindUserIdCard(String userId,String idCard) {
        User user = new User();
        user.setId(userId);
        user.setIdCard(idCard);
        this.updateById(user);
        return user;
    }


}
