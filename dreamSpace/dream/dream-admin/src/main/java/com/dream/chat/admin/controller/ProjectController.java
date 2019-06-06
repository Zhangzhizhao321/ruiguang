package com.dream.chat.admin.controller;


import com.dream.chat.constant.RelationEnum;
import com.dream.chat.entity.Project;
import com.dream.chat.service.ProjectService;
import com.dream.chat.service.SystemUserService;
import com.dream.chat.vo.req.BsProjectReqVo;
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
 *  前端控制器
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@RestController
@RequestMapping("/bs-project/v1")
@Api(tags = "登录注册")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/getProjectList")
    @ApiOperation(value = "获取筹款列表", notes = "获取筹款列表")
    public R getProjectList(BsProjectReqVo vo){
        return R.ok(projectService.getProjectListForBs(vo));
    }

    @PostMapping("/getProjectDetail")
    @ApiOperation(value = "项目详情", notes = "项目详情")
    public R getProjectDetail(@ApiParam("项目id") String projectId, @ApiParam("页")Long current, @ApiParam("行")Long size) {
        return R.ok(projectService.getProjectDetail(projectId,current,size));
    }

    @PostMapping("/overProject")
    @ApiOperation(value = "结束项目", notes = "结束项目")
    public R getProjectList(String projectId,String userId){
        Project project = projectService.getById(projectId);
        return R.ok(projectService.overProject(userId,project));
    }

    @PostMapping("/hotProject")
    @ApiOperation(value = "推上热门", notes = "推上热门")
    public R getProjectList(String projectId){
        return R.ok(projectService.hotProject(projectId));
    }

    @PostMapping("/addInstitutionPro")
    @ApiOperation(value = "帮助机构添加项目", notes = "帮助机构添加项目")
    public R addInstitutionPro(String id,ProjectReqVo vo, HttpServletRequest request){
        vo.setUserId(id);
        vo.setRelationId(RelationEnum.INSTITUTION.code);
        vo.setPublishFlag(1);
        return R.ok(projectService.createProject(vo,request));
    }


}
