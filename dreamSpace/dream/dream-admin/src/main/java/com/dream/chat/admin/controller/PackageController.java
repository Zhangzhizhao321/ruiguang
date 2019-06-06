package com.dream.chat.admin.controller;

import com.dream.chat.entity.Package;
import com.dream.chat.service.PackageService;
import com.dream.common.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/bs-package/v1")
@Api(tags = "捐款套餐")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @PostMapping("/queryPackage")
    @ApiOperation(value = "捐款套餐", notes = "捐款套餐")
    public R queryPackage(){
        List<Package> packageList = packageService.selectPackage();
        return R.ok(packageList);
    }

    @PostMapping("/delPackage")
    @ApiOperation(value = "删除捐款套餐", notes = "删除捐款套餐")
    public R delPackage(Integer id){
        Boolean result = packageService.removeById(id);
        return R.ok(result);
    }

    @PostMapping("/addPackage")
    @ApiOperation(value = "添加捐款套餐", notes = "添加捐款套餐")
    public R addPackage(BigDecimal amount){
        return R.ok(packageService.addPackage(amount));
    }

    @PostMapping("/updatePackage")
    @ApiOperation(value = "修改捐款套餐", notes = "修改捐款套餐")
    public R updatePackage(Integer id,BigDecimal amount){
        return R.ok(packageService.updatePackage(id, amount));
    }

}
