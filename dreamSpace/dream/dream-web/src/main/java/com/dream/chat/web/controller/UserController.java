package com.dream.chat.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.cache.RedisService;
import com.dream.chat.cache.dto.UserSessionDTO;
import com.dream.chat.common.utils.AppletsUtil;
import com.dream.chat.common.utils.WechatProOverUtil;
import com.dream.chat.common.utils.WechatUtil;
import com.dream.chat.constant.LoginEnum;
import com.dream.chat.constant.RedisConstant;
import com.dream.chat.entity.*;
import com.dream.chat.service.*;
import com.dream.chat.vo.req.LoginReqVo;
import com.dream.chat.vo.res.UserPhoneResVo;
import com.dream.common.core.api.R;
import com.dream.common.core.util.WeChatLoginUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/user/v1")
@Api(tags = "用户")
public class UserController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFormidService userFormidService;

    @Autowired
    private UserWalletService userWalletService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserProjectService userProjectService;

    @Autowired
    private ProjectService projectService;


    @PostMapping("/setUserSession")
    @ApiOperation(value = "登录", notes = "用openId直接存入redis")
    public R setUserSession(String openId) {
        UserSessionDTO userSessionDTO = userService.getUserSessionById(openId);
        if (userSessionDTO == null) {
            User user = userService.getUserByOpenid(openId);
            if (user == null) {
                return R.failed("没有此用户信息");
            }
            userSessionDTO = new UserSessionDTO(user.getWxOpenid(), user.getId(), user.getUserName(), user.getHeadPic(), user.getSex(),user.getPhoneNumber());
            redisService.set(userSessionDTO, 24, TimeUnit.HOURS);
        }
        return R.ok(userSessionDTO);
    }


    @PostMapping("/loginByWX")
    @ApiOperation(value = "登录", notes = "用户微信登录")
    public R loginByWX(String code, Integer loginFlag,String mobile,String inviteId, String encryptedData, String iv) throws Exception {
        if (StringUtils.isBlank(code)) {
            return R.failed("未获取到用户凭证code");
        }
        JSONObject jsonObject = null;
        LoginReqVo loginReqVo = new LoginReqVo();
        loginReqVo.setMobile(mobile);
        loginReqVo.setInviteId(inviteId);
        loginReqVo.setFlag(loginFlag);
        if (loginFlag == LoginEnum.PROGRAM.code) {
            jsonObject = WeChatLoginUtil.GetToken(code);
            String access_token = jsonObject.getString("access_token");
            // String refresh_token = tokenJson.getString("refresh_token");
            String sessionKey = jsonObject.getString("session_key");
            String openid = jsonObject.getString("openid");
            // 获取个人信息
            System.out.println(encryptedData);
            JSONObject infoJson = WeChatLoginUtil.GetUserInfoPro(encryptedData, sessionKey, iv);

            String unionid = infoJson.getString("unionid");
            String userName = infoJson.getString("nickName");
            String pic = infoJson.getString("avatarUrl");
            String gender = infoJson.getString("gender");
            System.out.println(userName);

            jsonObject.put("openid", openid);
            //jsonObject.put("session_key", UUID.randomUUID().toString().replace("-", ""));
            jsonObject.put("unionId", unionid);

            loginReqVo.setNickName(userName);
            loginReqVo.setAvatarUrl(pic);
            loginReqVo.setGender(gender == null ? 1 : Integer.parseInt(gender));
            loginReqVo.setOpenId(openid);
            loginReqVo.setUnionId(unionid);

        } else if(loginFlag == LoginEnum.H5.code){
            JSONObject tokenJson = null;
            tokenJson = WeChatLoginUtil.GetTokenByH5(code);
            if (StringUtils.isNotBlank(tokenJson.getString("errcode"))) {
                return R.failed("授权失败,errmsg:" + tokenJson.getString("errmsg"));
            }
            String access_token = tokenJson.getString("access_token");
            // String refresh_token = tokenJson.getString("refresh_token");
            String openid = tokenJson.getString("openid");
            // 获取个人信息
            JSONObject infoJson = WeChatLoginUtil.GetUserInfo(openid, access_token);
            if (StringUtils.isNotBlank(infoJson.getString("errcode"))) {
                return R.failed("获取个人信息失败失败,errmsg:" + infoJson.getString("errmsg"));
            }
            String unionid = infoJson.getString("unionid");
            String userName = infoJson.getString("nickname");
            String pic = infoJson.getString("headimgurl");
            String gender = infoJson.getString("gender");
            jsonObject.put("openid", openid);
            //jsonObject.put("session_key", UUID.randomUUID().toString().replace("-", ""));
            jsonObject.put("unionId", unionid);

            loginReqVo.setNickName(userName);
            loginReqVo.setAvatarUrl(pic);
            loginReqVo.setGender(gender == null ? 1 : Integer.parseInt(gender));
            loginReqVo.setH5OpenId(openid);
            loginReqVo.setUnionId(unionid);

        }
        else if(loginFlag == LoginEnum.APP.code){
            JSONObject tokenJson = null;
            tokenJson = WeChatLoginUtil.GetTokenForApp(code);
            if (StringUtils.isNotBlank(tokenJson.getString("errcode"))) {
                return R.failed("授权失败,errmsg:" + tokenJson.getString("errmsg"));
            }
            String access_token = tokenJson.getString("access_token");
            // String refresh_token = tokenJson.getString("refresh_token");
            String openid = tokenJson.getString("openid");
            // 获取个人信息
            JSONObject infoJson = WeChatLoginUtil.GetUserInfo(openid, access_token);
            if (StringUtils.isNotBlank(infoJson.getString("errcode"))) {
                return R.failed("获取个人信息失败失败,errmsg:" + infoJson.getString("errmsg"));
            }
            String unionid = infoJson.getString("unionid");
            String userName = infoJson.getString("nickname");
            String pic = infoJson.getString("headimgurl");
            String gender = infoJson.getString("gender");
            jsonObject.put("openid", openid);
            //jsonObject.put("session_key", UUID.randomUUID().toString().replace("-", ""));
            jsonObject.put("unionId", unionid);

            loginReqVo.setNickName(userName);
            loginReqVo.setAvatarUrl(pic);
            loginReqVo.setGender(gender == null ? 1 : Integer.parseInt(gender));
            loginReqVo.setAppOpenId(openid);
            loginReqVo.setUnionId(unionid);

        }
        if (StringUtils.isNotBlank(jsonObject.getString("errcode"))) {
            return R.failed("授权失败,errmsg:" + jsonObject.getString("errmsg"));
        }
        return userService.userLogin(loginReqVo);
    }

    @PostMapping("/loginByMobile")
    @ApiOperation(value = "登录", notes = "用户手机登录")
    public R loginByMobile(String mobile){
        return userService.loginByMobile(mobile);
    }

    @PostMapping("/getMyHelpedMsg")
    @ApiOperation(value = "获取我捐助信息", notes = "获取我捐助信息")
    public R getMyHelpedMsg(String mobile,HttpServletRequest request){
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        Map<String,Object> map = new HashMap<>();
        UserWallet userWallet = userWalletService.getMyWallet(userSessionDTO.getUserId());
        Integer count = orderService.getMyHelpedProCount(userSessionDTO.getUserId());
        Integer myCount = userProjectService.getMyProCount(userSessionDTO.getUserId());
        map.put("helpAmount",userWallet.getContribution());
        map.put("helpcount",count);
        map.put("totalAmount",userWallet.getTotal());
        map.put("myCount",myCount);
        return R.ok(map);
    }


    @PostMapping("/getPhoneNum")
    @ApiOperation(value = "获取手机号", notes = "获取手机号")
    public R getPhoneNum(HttpServletRequest request, String code, String encryptedData, String iv) {
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        if (StringUtils.isBlank(code)) {
            return R.failed("未获取到用户凭证code");
        }

        JSONObject jsonObject = WeChatLoginUtil.GetToken(code);
        //String access_token = jsonObject.getString("access_token");
        // String refresh_token = tokenJson.getString("refresh_token");
        String sessionKey = jsonObject.getString("session_key");

        // 获取个人信息
        //JSONObject infoJson = WeChatLoginUtil.GetUserInfoPro(encryptedData, sessionKey, iv);
        UserPhoneResVo userPhoneResVo = userService.getUserPhone(userSessionDTO.getUserId(), sessionKey, encryptedData, iv);
        if (userPhoneResVo != null) {
            return R.ok(userPhoneResVo.getPhoneNumber());
        }
        return R.failed("");

    }


    @PostMapping("/setFormId")
    @ApiOperation(value = "存formid", notes = "存formid")
    public R setFormId(HttpServletRequest request, String formId ){
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        return R.ok(userFormidService.serUserFormId(userSessionDTO.getUserId(),formId));
    }

    @PostMapping("/sendTemplet")
    @ApiOperation(value = "发送模板消息", notes = "发送模板消息")
    public R sendTemplet(HttpServletRequest request,String userId,String projectId,BigDecimal amount) {
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getAttribute("UserSession");
        User user = userService.getById(userId);
        Project project = projectService.getById(projectId);
        //List<UserFormid> userFormids = userFormidService.list(new QueryWrapper<UserFormid>().eq(UserFormid.USER_ID,userId).orderByAsc(UserFormid.CREATE_TIME));
        //AppletsUtil.appletsTemplate(user,userFormids.get(0).getFormId(),amount,userSessionDTO);
        WechatUtil.registTemplate(user,userSessionDTO.getUserName(),project.getTitle(),amount);
        WechatProOverUtil.registTemplate(user,project.getTitle(),"20");
        //userFormidService.removeById(userFormids.get(0).getId());
        return R.ok("");
    }

}
