package com.dream.chat.common.utils;

import com.dream.chat.cache.Wechat.TemplateData;
import com.dream.chat.cache.dto.UserSessionDTO;
import com.dream.chat.entity.User;
import com.dream.chat.vo.req.WxMssVo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2019-02-22
 */
public class AppletsUtil {

    private static final String TEMPLE_ID = "hwaJfRw_RdEzUrtiSUV4rZZvdogmBUuxTHC_tOrguOM";

    public static void appletsTemplate(User user, String userFormid, String body, UserSessionDTO userSessionDTO) {
        //User user = userService.getById(friendId);
        String accessToken = AccessTokenUtil.getToken(); //WeChatLoginUtil.getAccess_Token();

        //userFormidService.deleteFormIds(userSessionDTO.getUserId());
        //List<UserFormid> userFormids = userFormidService.list(new QueryWrapper<UserFormid>().eq(UserFormid.USER_ID,friendId).orderByAsc(UserFormid.INVALID_TIME));
        String url="https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + accessToken;
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setAccess_token(accessToken);
        /*if(userFormids!=null && userFormids.size() > 0){
            wxMssVo.setForm_id(userFormids.get(0).getFormId());
            //templateMsgResVo.setFormId();
            userFormidService.remove(new QueryWrapper<UserFormid>().eq(UserFormid.FORM_ID,userFormids.get(0).getFormId()));
        }else {
            return R.failed("");
        }*/
        wxMssVo.setForm_id(userFormid);
        wxMssVo.setRequest_url(url);
        wxMssVo.setTemplate_id(AppletsUtil.TEMPLE_ID);
        wxMssVo.setTouser(user.getWxOpenid());
        wxMssVo.setPage("/pages/index/index");
        List<TemplateData> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        list.add(new TemplateData("捐助","#ffffff"));
        list.add(new TemplateData(body,"#ffffff"));
        list.add(new TemplateData(userSessionDTO.getUserName(),"#ffffff"));
        list.add(new TemplateData(sdf.format(new Date()),"#ffffff"));
        wxMssVo.setParams(list);
        MessageUtil.sendTemplateMessage(wxMssVo);
       // return R.ok("");
    }
}
