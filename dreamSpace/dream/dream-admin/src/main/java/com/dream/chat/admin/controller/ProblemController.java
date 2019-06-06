package com.dream.chat.admin.controller;

import com.dream.chat.entity.Banner;
import com.dream.chat.entity.Problem;
import com.dream.chat.service.BannerService;
import com.dream.chat.service.ProblemService;
import com.dream.chat.vo.req.BsbannerReqVo;
import com.dream.common.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/bs-problem/v1")
@Api(tags = "文案")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @PostMapping("/getProblemsById")
    @ApiOperation(value = "通过id获取文案", notes = "通过id获取文案")
    public R getProblemsById(Integer id){
        return R.ok(problemService.getById(id));
    }

    @PostMapping("/getProblems")
    @ApiOperation(value = "获取文案", notes = "获取文案")
    public R getProblems(Integer id){
        return R.ok(problemService.getProblemByType(id));
    }

    @PostMapping("/getProblemsType")
    @ApiOperation(value = "获取文案类型", notes = "获取文案类型")
    public R getProblemsType(){
        return R.ok(problemService.getProblemsType());
    }

    @PostMapping("/deleteProblem")
    @ApiOperation(value = "删除文案", notes = "删除文案")
    public R deleteProblem(Integer id){
        return R.ok(problemService.removeById(id));
    }

    @PostMapping("/updateProblem")
    @ApiOperation(value = "编辑文案", notes = "编辑文案")
    public R updateProblem(Integer id,String note,String answer){
        return R.ok(problemService.updateProblem(id, note, answer));
    }

    @PostMapping("/addProblem")
    @ApiOperation(value = "添加文案", notes = "添加文案")
    public R addProblem(String note,String answer,Integer type){
        return R.ok(problemService.addProblem(note,answer,type));
    }
}
