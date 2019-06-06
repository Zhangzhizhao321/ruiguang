package com.dream.chat.web.controller;


import com.dream.chat.entity.ProjectTemplet;
import com.dream.chat.service.ProjectTempletService;
import com.dream.chat.vo.req.ProjectTempletReqVo;
import com.dream.common.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@RestController
@RequestMapping("/project-templet/v1")
@Api(tags = "项目模板")
public class ProjectTempletController {

    @Autowired
    private ProjectTempletService projectTempletService;

    @PostMapping("/useProjectTemplet")
    @ApiOperation(value = "使用模板", notes = "使用模板")
    public R useProjectTemplet(ProjectTempletReqVo projectTempletReqVo){
        return R.ok(projectTempletService.useProjectTemplet(projectTempletReqVo));
    }

}
