package com.dream.chat.web.controller;


import com.dream.chat.cache.dto.UserSessionDTO;
import com.dream.chat.entity.Project;
import com.dream.chat.service.ProjectService;
import com.dream.chat.vo.req.ProjectReqVo;
import com.dream.common.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@RestController
@RequestMapping("/project/v1")
@Api(tags = "项目")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/getProjectDetail")
    @ApiOperation(value = "项目详情", notes = "项目详情")
    public R getProjectDetail(@ApiParam("项目id") String projectId,@ApiParam("页")Long current,@ApiParam("行")Long size) {
        return R.ok(projectService.getProjectDetail(projectId,current,size));
    }

    @PostMapping("/createProject")
    @ApiOperation(value = "发起项目", notes = "发起项目")
    public R createProject(ProjectReqVo projectReqVo, HttpServletRequest request) {
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        projectReqVo.setUserId(userSessionDTO.getUserId());
        return projectService.createProject(projectReqVo,request);
    }

    @PostMapping("/overProject")
    @ApiOperation(value = "结束项目", notes = "结束项目")
    public R overProject(String projectId, HttpServletRequest request) {
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        Project project = projectService.getById(projectId);
        return projectService.overProject(userSessionDTO.getUserId(),project);
    }

}
