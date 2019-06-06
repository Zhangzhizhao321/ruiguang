package com.dream.chat.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.constant.ProblemTypeEnum;
import com.dream.chat.constant.ProjectCategoryEnum;
import com.dream.chat.constant.RelationEnum;
import com.dream.chat.entity.ProjectCategory;
import com.dream.chat.entity.Relation;
import com.dream.chat.service.ProjectCategoryService;
import com.dream.chat.service.RelationService;
import com.dream.common.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@RestController
@RequestMapping("/project-category/v1")
@Api(tags = "项目类型")
public class ProjectCategoryController {

    @Autowired
    private ProjectCategoryService projectCategoryService;

    @Autowired
    private RelationService relationService;


    @PostMapping("/getProCategory")
    @ApiOperation(value = "获取项目类型", notes = "获取项目类型")
    public R getProCategory(){
        List<ProjectCategory> categories = projectCategoryService.list(null);
        ProjectCategory projectCategory = null;
        for(ProjectCategory category : categories){
            if(category.getId() == ProjectCategoryEnum.MEDICAL.code){
                categories.remove(category);
                projectCategory = category;
                break;
            }
        }
        if(projectCategory != null){
            categories.add(projectCategory);
        }
        List<Relation> relations = relationService.list(new QueryWrapper<Relation>().notIn(Relation.ID,0, RelationEnum.OTHER.code));

        Map<String,Object> map = new HashMap<>();
        map.put("categories",categories);
        map.put("relations",relations);

        return R.ok(map);
    }

}
