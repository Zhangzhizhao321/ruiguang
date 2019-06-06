package com.dream.chat.web.controller;


import com.dream.chat.cache.dto.UserSessionDTO;
import com.dream.chat.entity.UserProject;
import com.dream.chat.service.UserProjectService;
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
@RequestMapping("/user-project/v1")
@Api(tags = "用户项目")
public class UserProjectController {

    @Autowired
    private UserProjectService userProjectService;

    @PostMapping("/getMyProject")
    @ApiOperation(value = "我的筹款", notes = "我的筹款")
    public R getMyProject(HttpServletRequest request, @ApiParam("页")Long current,@ApiParam("条数")Long size){
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        return R.ok(userProjectService.getMyProjectList(userSessionDTO.getUserId(),current,size));
    }



}
