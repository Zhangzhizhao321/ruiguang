package com.dream.chat.service;

import com.dream.chat.entity.Institution;
import com.dream.chat.vo.req.InstitutionReqVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
public interface InstitutionService extends SuperService<Institution> {

    Institution addOrUpdateInstitution(InstitutionReqVo vo);

    Institution unbindInstitutionCard(String id);

}
