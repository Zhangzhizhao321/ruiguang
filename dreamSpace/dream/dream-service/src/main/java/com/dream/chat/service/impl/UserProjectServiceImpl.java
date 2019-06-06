package com.dream.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.entity.UserProject;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.UserProjectMapper;
import com.dream.chat.service.UserProjectService;
import com.dream.chat.vo.req.ProjectReqVo;
import com.dream.chat.vo.res.UserProjectListResVo;
import com.dream.common.core.util.TimeTools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@Service
public class UserProjectServiceImpl extends SuperServiceImpl<UserProjectMapper, UserProject> implements UserProjectService {

    @Resource
    private UserProjectMapper userProjectMapper;

    @Override
    public UserProject addUserProject(ProjectReqVo projectReqVo) {
        UserProject userProject = new UserProject();
        /*userProject.setUserName(projectReqVo.getUserName());
        userProject.setUserId(projectReqVo.getUserId());
        userProject.setUserAge(projectReqVo.getUserAge());
        userProject.setTargetAmount(projectReqVo.getTargetAmount());*/
        BeanUtil.copyProperties(projectReqVo,userProject);
        userProject.setProjectId(projectReqVo.getProjectId());
        userProject.setCreateTime(new Date());
        userProject.setHelpCount(0L);
        userProject.setNowAmount(BigDecimal.ZERO);
        userProject.setUpdateTime(new Date());
        this.save(userProject);
        return userProject;
    }

    @Override
    public List<UserProjectListResVo> getMyProjectList(String userId,Long current,Long size) {
        List<UserProjectListResVo> myProject = userProjectMapper.getMyProject(userId, size * (current - 1), size);
        for(UserProjectListResVo vo : myProject){
            Integer days = TimeTools.differentDays(new Date(),vo.getOverTime());
            vo.setOverDays(days < 0 ? 0 : days);
        }
        return myProject;
    }

    @Override
    public Integer getMyProCount(String userId) {
        List<UserProject> userProjects = this.list(new QueryWrapper<UserProject>().eq(UserProject.USER_ID,userId));
       return userProjects.size();
    }
}
