package com.dream.chat.service;

import com.dream.chat.entity.Project;
import com.dream.chat.entity.ProjectCategory;
import com.dream.chat.vo.req.BsProCategoryReqVo;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
public interface ProjectCategoryService extends SuperService<ProjectCategory> {

    ProjectCategory addProCategory(BsProCategoryReqVo vo);

}
