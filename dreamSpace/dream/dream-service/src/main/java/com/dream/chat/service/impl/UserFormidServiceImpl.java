package com.dream.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.entity.UserFormid;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.UserFormidMapper;
import com.dream.chat.service.UserFormidService;
import com.dream.common.core.util.TimeTools;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-30
 */
@Service
public class UserFormidServiceImpl extends SuperServiceImpl<UserFormidMapper, UserFormid> implements UserFormidService {

    @Override
    public UserFormid serUserFormId(String userId, String formId) {
        UserFormid userFormid = new UserFormid();
        userFormid.setFormId(formId);
        userFormid.setUserId(userId);
        userFormid.setCreateTime(new Date());
        userFormid.setOverTime(TimeTools.getFetureDate(6));
        this.save(userFormid);

        this.deleteUserFormId(userId);
        return userFormid;
    }

    @Override
    public void deleteUserFormId(String userId) {
        this.remove(new QueryWrapper<UserFormid>().le(UserFormid.OVER_TIME,new Date()));
    }
}
