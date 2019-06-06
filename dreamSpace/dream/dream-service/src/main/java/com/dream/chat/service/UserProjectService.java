package com.dream.chat.service;

import com.dream.chat.entity.UserProject;
import com.dream.chat.vo.req.ProjectReqVo;
import com.dream.chat.vo.res.UserProjectListResVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
public interface UserProjectService extends SuperService<UserProject> {

    UserProject addUserProject(ProjectReqVo projectReqVo);

    List<UserProjectListResVo> getMyProjectList(String userId,Long current,Long size);

    Integer getMyProCount(String userId);

}
