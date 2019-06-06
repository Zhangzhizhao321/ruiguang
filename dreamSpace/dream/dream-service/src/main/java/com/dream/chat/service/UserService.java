package com.dream.chat.service;

import com.dream.chat.cache.dto.UserSessionDTO;
import com.dream.chat.entity.User;
import com.dream.chat.entity.UserFormid;
import com.dream.chat.vo.req.LoginReqVo;
import com.dream.chat.vo.res.UserPhoneResVo;
import com.dream.common.core.api.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
public interface UserService extends SuperService<User> {

    R userLogin(LoginReqVo loginReqVo);

    User addUser(LoginReqVo loginReqVo);

    User updateLoginUser(User user,LoginReqVo loginReqVo);

    UserSessionDTO getUserSessionById(String openId);

    User getUserByOpenid(String openId);

    R loginByMobile(String mobile);

    UserPhoneResVo getUserPhone(String userId,String sessionid,String data,String iv);

    Integer newNumber();

    Integer totalNumber();

    User bindUserIdCard(String userId,String idCard);

}
