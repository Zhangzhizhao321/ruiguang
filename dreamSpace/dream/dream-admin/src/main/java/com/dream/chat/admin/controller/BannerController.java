package com.dream.chat.admin.controller;

import com.dream.chat.entity.Banner;
import com.dream.chat.service.BannerService;
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
@RequestMapping("/bs-banner/v1")
@Api(tags = "轮播图")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @PostMapping("/bannerQuery")
    @ApiOperation(value = "轮播图", notes = "轮播图")
    public R bannerQuery(){
        List<Banner> bannerList = bannerService.selectBanner();
        return R.ok(bannerList);
    }

    @PostMapping("/delBanner")
    @ApiOperation(value = "删除轮播图", notes = "删除轮播图")
    public R delBanner(String id){
        bannerService.delBanner(id);
        return R.ok("");
    }

    @PostMapping("/updateBanner")
    @ApiOperation(value = "修改轮播图", notes = "修改轮播图")
    public R delBanner(String id,Integer status,Integer sort){
        return R.ok(bannerService.updateBanner(id, status, sort));
    }

    @PostMapping("/addBanner")
    @ApiOperation(value = "轮播图", notes = "轮播图")
    public R addBanner(BsbannerReqVo vo, HttpServletRequest request){
        return R.ok(bannerService.addBanner(vo,request));
    }
}
