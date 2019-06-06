package com.dream.chat.service;

import com.dream.chat.entity.UserFormid;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-30
 */
public interface UserFormidService extends SuperService<UserFormid> {

    UserFormid serUserFormId(String userId,String formId);

    void deleteUserFormId(String userId);

}
