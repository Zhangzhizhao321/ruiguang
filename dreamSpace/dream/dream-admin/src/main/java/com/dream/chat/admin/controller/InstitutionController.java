package com.dream.chat.admin.controller;


import com.dream.chat.entity.Institution;
import com.dream.chat.service.InstitutionService;
import com.dream.chat.service.OrderService;
import com.dream.chat.service.ProjectService;
import com.dream.chat.service.UserService;
import com.dream.chat.vo.req.InstitutionReqVo;
import com.dream.common.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
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
@RequestMapping("/bs-institution/v1")
@Api(tags = "机构")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @PostMapping("/addInstitution")
    @ApiOperation(value = "新增机构", notes = "新增机构")
    public R addInstitution(InstitutionReqVo vo){
        return R.ok(institutionService.addOrUpdateInstitution(vo));
    }

    @PostMapping("/unbindCard")
    @ApiOperation(value = "", notes = "新增机构")
    public R unbindCard(String id){
        return R.ok(institutionService.unbindInstitutionCard(id));
    }

}
