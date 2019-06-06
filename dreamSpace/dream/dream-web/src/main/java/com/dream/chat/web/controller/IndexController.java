package com.dream.chat.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.cache.RedisService;
import com.dream.chat.cache.dto.UserSessionDTO;
import com.dream.chat.constant.ProblemTypeEnum;
import com.dream.chat.entity.Banner;
import com.dream.chat.entity.Package;
import com.dream.chat.entity.Problem;
import com.dream.chat.service.BannerService;
import com.dream.chat.service.PackageService;
import com.dream.chat.service.ProblemService;
import com.dream.chat.service.ProjectService;
import com.dream.chat.vo.res.ProjectResVo;
import com.dream.common.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@RestController
@RequestMapping("/index/v1")
@Api(tags = "首页")
public class IndexController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private RedisService redisService;

    @PostMapping("/index")
    @ApiOperation(value = "首页", notes = "首页")
    public R index(@ApiParam("当前页") Long current, @ApiParam("条数") Long size) {
        List<Banner> bannerList = bannerService.list(new QueryWrapper<Banner>().eq(Banner.STATUS, 1).orderByAsc(Banner.SORT));
        List<ProjectResVo> hotProjects = projectService.getProjectList(0, 1L, 5L);
        List<ProjectResVo> newProjects = projectService.getProjectList(1, current, size);

        Map<String, Object> map = new HashMap<>();
        map.put("bannerList", bannerList);
        map.put("hotProjects", hotProjects);
        map.put("newProjects", newProjects);

        //首页渲染
        List<String> carousels = new ArrayList<>();
        String hotNote = "";
        String newNote = "";
        List<Problem> problems = problemService.list(null);
        for (Problem problem : problems) {
            if (problem.getType() == ProblemTypeEnum.CAROUSEL.code) {
                carousels.add(problem.getNote());
            }
            if (problem.getType() == ProblemTypeEnum.HOTNOTE.code) {
                hotNote = problem.getNote();
            }
            if (problem.getType() == ProblemTypeEnum.NEWNOTE.code) {
                newNote = problem.getNote();
            }
        }
        map.put("hotNote", hotNote);
        map.put("carousel", carousels);
        map.put("newNote", newNote);
        return R.ok(map);
    }

    @PostMapping("/problems")
    @ApiOperation(value = "常见问题", notes = "常见问题")
    public R getproblems() {
        List<Problem> problems = problemService.list(new QueryWrapper<Problem>().eq(Problem.TYPE, ProblemTypeEnum.PROBLEM.code));
        return R.ok(problems);
    }

    @PostMapping("/secretBook")
    @ApiOperation(value = "筹款秘籍", notes = "筹款秘籍")
    public R secretBook() {
        List<Problem> problem = problemService.getProblemByType(ProblemTypeEnum.SECRETBOOK.code);
        return R.ok(problem != null && problem.size() > 0 ? problem.get(0) : null);
    }

    @PostMapping("/packageList")
    @ApiOperation(value = "捐助页面 套餐金额 留言", notes = "捐助页面 套餐金额 留言")
    public R packageList() {
        Map<String, Object> map = new HashMap<>();
        List<Package> packages = packageService.list(null);
        List<Problem> problem = problemService.list(new QueryWrapper<Problem>().eq(Problem.TYPE, ProblemTypeEnum.COMMENT.code));
        map.put("packages", packages);
        map.put("problem", problem != null && problem.size() > 0 ? problem.get(0) : null);
        return R.ok(map);
    }

    @PostMapping("/servicePhone")
    @ApiOperation(value = "客服电话", notes = "客服电话")
    public R servicePhone() {
        List<Problem> problem = problemService.getProblemByType(ProblemTypeEnum.SERVICE_PHONE.code);
        return R.ok(problem != null && problem.size() > 0 ? problem.get(0) : null);
    }

}
