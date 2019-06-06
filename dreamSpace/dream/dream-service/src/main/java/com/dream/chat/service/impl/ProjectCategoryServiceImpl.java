package com.dream.chat.service.impl;

import com.dream.chat.entity.Project;
import com.dream.chat.entity.ProjectCategory;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.ProjectCategoryMapper;
import com.dream.chat.service.ProjectCategoryService;
import com.dream.chat.vo.req.BsProCategoryReqVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@Service
public class ProjectCategoryServiceImpl extends SuperServiceImpl<ProjectCategoryMapper, ProjectCategory> implements ProjectCategoryService {

    @Override
    public ProjectCategory addProCategory(BsProCategoryReqVo vo) {
        ProjectCategory projectCategory = new ProjectCategory();
        BeanUtils.copyProperties(vo,projectCategory);
        this.save(projectCategory);
        return projectCategory;
    }
}
