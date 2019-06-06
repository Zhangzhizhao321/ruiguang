package com.dream.chat.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.cache.dto.UserSessionDTO;
import com.dream.chat.entity.Banner;
import com.dream.chat.service.CommentService;
import com.dream.chat.vo.req.CommentReqVo;
import com.dream.chat.vo.res.CommentResVo;
import com.dream.chat.vo.res.ProjectResVo;
import com.dream.common.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@RestController
@RequestMapping("/comment/v1")
@Api(tags = "评论")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/addComment")
    @ApiOperation(value = "评论", notes = "评论")
    public R addComment(CommentReqVo commentReqVo, HttpServletRequest request){
        UserSessionDTO userSessionDTO = (UserSessionDTO)request.getAttribute("UserSession");
        commentReqVo.setUserId(userSessionDTO.getUserId());
        return R.ok(commentService.addComment(commentReqVo));
    }

}
