package com.dream.chat.service;

import com.dream.chat.entity.Institution;
import com.dream.chat.entity.Project;
import com.dream.chat.vo.req.BsProjectReqVo;
import com.dream.chat.vo.req.ProjectReqVo;
import com.dream.chat.vo.res.InstitutionProjectResVo;
import com.dream.chat.vo.res.ProjectDetailResVo;
import com.dream.chat.vo.res.ProjectResVo;
import com.dream.common.core.api.R;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
public interface ProjectService extends SuperService<Project> {

    List<ProjectResVo> getProjectList(Integer flag, Long current, Long size);

    Map getProjectListForBs(BsProjectReqVo vo);

    ProjectDetailResVo getProjectDetail(String id,Long current,Long size);

    Project updateProjectOver(String id);

    R createProject(ProjectReqVo projectReqVo, HttpServletRequest request);

    R createInstitutionProject(ProjectReqVo projectReqVo, HttpServletRequest request);

    R overProject(String userId,Project project);

    Project hotProject(String projectId);

    Integer newProjectCount();

    Integer totalProjectCount();

    List<InstitutionProjectResVo> getInstitutionProList(Long current,Long size);

}
