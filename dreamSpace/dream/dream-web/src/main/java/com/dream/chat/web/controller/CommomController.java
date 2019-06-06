package com.dream.chat.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.cache.RedisService;
import com.dream.chat.cache.Wechat.Menu;
import com.dream.chat.cache.Wechat.ToXcx;
import com.dream.chat.cache.dto.ProjectDTO;
import com.dream.chat.cache.dto.SmsDTO;
import com.dream.chat.cache.dto.UserSessionDTO;
import com.dream.chat.common.utils.CommonUtil;
import com.dream.chat.common.utils.MenuUtil;
import com.dream.chat.constant.ImgUrlContanst;
import com.dream.chat.constant.RedisConstant;
import com.dream.chat.entity.Project;
import com.dream.chat.mapper.ProjectMapper;
import com.dream.common.core.api.R;
import com.dream.common.core.util.SecurityHandler;
import com.dream.common.core.util.TimeTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.weixin4j.menu.SingleButton;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2019-01-10
 */
@RestController
@RequestMapping("/commom/v1")
@Api(tags = "通用")
public class CommomController {

    @Autowired
    private RedisService redisService;

    @Resource
    private ProjectMapper projectMapper;


    @PostMapping("/verifyCode")
    @ApiOperation(value = "验证手机号", notes = "验证手机号验证码")
    public R verifyCode(String mobile, String code) {
        SmsDTO smsDTO = redisService.get(RedisConstant.SMS + ":" + mobile, SmsDTO.class);
        if (StringUtils.isNotBlank(code) && code.equals(smsDTO.getCode())) {
            redisService.delObj(RedisConstant.SMS + ":" + mobile);
            return R.ok("成功");
        }
        return R.failed("验证失败");
    }

    @PostMapping("/upload")
    @ApiOperation(value = "上传图片", notes = "上传图片")
    public String upload(MultipartFile file, HttpServletRequest request) {
        String url = CommonUtil.upload(ImgUrlContanst.PROJECT_IMG, file, request);
        return url;
    }

    @PostMapping("/addRedis")
    @ApiOperation(value = "", notes = "")
    public String upload(String id) {
        Project project = projectMapper.selectById(id);
        ProjectDTO projectDTO = new ProjectDTO(id, project.getUserId(), project.getTitle());
        redisService.set(projectDTO, 24, TimeUnit.DAYS);
        return "";
    }

    @PostMapping("/addNoOverRedis")
    @ApiOperation(value = "", notes = "")
    public String addNoOverRedis() {
        List<Project> projects = projectMapper.selectList(new QueryWrapper<Project>().eq(Project.IS_OVER, 0));
        for (Project project : projects) {
            ProjectDTO projectDTO = new ProjectDTO(project.getId(), project.getUserId(), project.getTitle());
            Long s = project.getOverTime().getTime()-new Date().getTime();
            Long second = s/1000;
            System.out.println(second);
            redisService.set(projectDTO, second, TimeUnit.SECONDS);
        }
        return "";
    }

    @PostMapping("/addMenu")
    @ApiOperation(value = "", notes = "")
    public String addMenu() {
        ToXcx xcxBtn1 = new ToXcx();
        xcxBtn1.setName("首页");
        xcxBtn1.setType("miniprogram");
        xcxBtn1.setUrl("https://www.pmxiang.com");
        xcxBtn1.setAppid("wx20a0b0527db5aa3c");
        xcxBtn1.setPagepath("/pages/index/index?currtaget==1");

        ToXcx xcxBtn2 = new ToXcx();
        xcxBtn2.setName("发起众筹");
        xcxBtn2.setType("miniprogram");
        xcxBtn2.setUrl("https://www.pmxiang.com");
        xcxBtn2.setAppid("wx20a0b0527db5aa3c");
        xcxBtn2.setPagepath("/pages/initiate/index");

        ToXcx xcxBtn3 = new ToXcx();
        xcxBtn3.setName("我的");
        xcxBtn3.setType("miniprogram");
        xcxBtn3.setUrl("https://www.pmxiang.com");
        xcxBtn3.setAppid("wx20a0b0527db5aa3c");
        xcxBtn3.setPagepath("/pages/index/index?currtaget==2");

        Menu menu = new Menu();
        menu.setButton(new ToXcx[]{xcxBtn1, xcxBtn2, xcxBtn3});
        MenuUtil.createMenu(menu, "");
        return "";
    }


   /*  @PostMapping("/getUsersessionById")
    public R getUsersessionById(String sessionId) {
        UserSessionDTO userSessionDTO = redisService.get(RedisConstant.USER_SESSION + ":" + sessionId, UserSessionDTO.class);
        return R.ok(userSessionDTO);
    }

   @PostMapping("/getTemplate")
    public R getTemplate(String friendId, HttpServletRequest request) {
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        User user = userService.getById(friendId);
        String accessToken = AccessTokenUtil.getToken(); //WeChatLoginUtil.getAccess_Token();

        userFormidService.deleteFormIds(userSessionDTO.getUserId());
        List<UserFormid> userFormids = userFormidService.list(new QueryWrapper<UserFormid>().eq(UserFormid.USER_ID, friendId).orderByAsc(UserFormid.INVALID_TIME));
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + accessToken;
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setAccess_token(accessToken);
        if (userFormids != null && userFormids.size() > 0) {
            wxMssVo.setForm_id(userFormids.get(0).getFormId());
            //templateMsgResVo.setFormId();
            userFormidService.remove(new QueryWrapper<UserFormid>().eq(UserFormid.FORM_ID, userFormids.get(0).getFormId()));
        } else {
            return R.failed("");
        }
        wxMssVo.setRequest_url(url);
        wxMssVo.setTemplate_id(WeChatLoginUtil.TEMPLATE_ID);
        wxMssVo.setTouser(user.getWxOpenid());
        wxMssVo.setPage("/pages/home/home");
        List<TemplateData> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // list.add(new TemplateData(sdf.format(new Date()),"#ffffff"));
        list.add(new TemplateData(userSessionDTO.getUserName(), "#ffffff"));
        list.add(new TemplateData("TA通过搭讪平台送来“玫瑰”请点击登入领取，进入“玫瑰商城”查看礼品兑换！", "#ffffff"));
        wxMssVo.setParams(list);
        MessageUtil.sendTemplateMessage(wxMssVo);
        return R.ok("");
    }

    @PostMapping("/sendTemple")
    public R sendTemple() {
        User user = new User();
        user.setWxOpenid("o90dy1qVe5Az8Xa8KAZzpkvcS-Z0");

        WechatUtil.registTemplate(user, "nini", "酒水", BigDecimal.ONE);
        return R.ok("");
    }*/

    @GetMapping("/getId")
    public Object getId() {
        return projectMapper.getProjectList(1, 1L, 20L);
    }

}
