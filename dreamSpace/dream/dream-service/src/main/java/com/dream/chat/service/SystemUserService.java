package com.dream.chat.service;

import com.dream.chat.cache.dto.BackstageUserSessionDTO;
import com.dream.chat.entity.SystemUser;
import com.dream.common.core.api.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-17
 */
public interface SystemUserService extends SuperService<SystemUser> {

    SystemUser getUserByMobile(String mobile);

    R register(String mobile, String password);

    R login(String mobile, String password);

}
