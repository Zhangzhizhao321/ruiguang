package com.dream.chat.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.constant.ProjectCategoryEnum;
import com.dream.chat.constant.RelationEnum;
import com.dream.chat.entity.ProjectCategory;
import com.dream.chat.entity.ProjectTemplet;
import com.dream.chat.entity.Relation;
import com.dream.chat.service.OrderService;
import com.dream.chat.service.ProjectCategoryService;
import com.dream.chat.service.ProjectTempletService;
import com.dream.chat.service.RelationService;
import com.dream.chat.vo.req.BsOrderReqVo;
import com.dream.chat.vo.req.BsProCategoryReqVo;
import com.dream.chat.vo.req.BsTempletReqVo;
import com.dream.common.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/bs-templet/v1")
@Api(tags = "模板")
public class ProjectTempletController {

    @Autowired
    private ProjectTempletService projectTempletService;

    @Autowired
    private ProjectCategoryService projectCategoryService;

    @Autowired
    private RelationService relationService;

    @PostMapping("/addTemplet")
    @ApiOperation(value = "添加模板", notes = "添加模板")
    public R addTemplet(BsTempletReqVo vo){
        return R.ok(projectTempletService.addTemplet(vo));
    }

    @PostMapping("/removeTemplet")
    @ApiOperation(value = "删除模板", notes = "删除模板")
    public R removeTemplet(BsTempletReqVo vo){
        return R.ok(projectTempletService.removeTemplet(vo));
    }

    @PostMapping("/updateTemplet")
    @ApiOperation(value = "修改模板", notes = "修改模板")
    public R updateTemplet(BsTempletReqVo vo){
        return R.ok(projectTempletService.updateTemplet(vo));
    }

    @PostMapping("/templetList")
    @ApiOperation(value = "模板列表", notes = "模板列表")
    public R templetList(BsTempletReqVo vo){
        return R.ok(projectTempletService.templetList(vo));
    }

    @PostMapping("/getProCategory")
    @ApiOperation(value = "获取项目类型", notes = "获取项目类型")
    public R getProCategory(){
        List<ProjectCategory> categories = projectCategoryService.list(null);
        List<Relation> relations = relationService.list(new QueryWrapper<Relation>().notIn(Relation.ID,0, RelationEnum.OTHER.code));

        Map<String,Object> map = new HashMap<>();
        map.put("categories",categories);
        map.put("relations",relations);

        return R.ok(map);
    }

    @PostMapping("/addProCategory")
    @ApiOperation(value = "新增项目类型", notes = "新增项目类型")
    public R addProCategory(BsProCategoryReqVo vo){
        return R.ok(projectCategoryService.addProCategory(vo));
    }

    @PostMapping("/addRelation")
    @ApiOperation(value = "新增关系", notes = "新增关系")
    public R addRelation(String name){
        return R.ok(relationService.addRelation(name));
    }

}
