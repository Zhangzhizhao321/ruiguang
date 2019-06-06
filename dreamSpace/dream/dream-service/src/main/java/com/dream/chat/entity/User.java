package com.dream.chat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangjing
 * @since 2019-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField("phone_number")
    private String phoneNumber;

    @TableField("head_pic")
    private String headPic;

    @TableField("wx_openid")
    private String wxOpenid;

    @TableField("wx_h5_openid")
    private String wxH5Openid;

    @TableField("wx_app_openid")
    private String wxAppOpenid;

    @TableField("wx_unionId")
    private String wxUnionid;

    @TableField("user_name")
    private String userName;

    /**
     * 性别
     */
    @TableField("sex")
    private Integer sex;

    @TableField("id_card")
    private String idCard;

    @TableField("invite_id")
    private String inviteId;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;


    public static final String ID = "id";

    public static final String PHONE_NUMBER = "phone_number";

    public static final String HEAD_PIC = "head_pic";

    public static final String WX_OPENID = "wx_openid";

    public static final String WX_H5_OPENID = "wx_h5_openid";

    public static final String WX_APP_OPENID = "wx_app_openid";

    public static final String WX_UNIONID = "wx_unionId";

    public static final String USER_NAME = "user_name";

    public static final String SEX = "sex";

    public static final String ID_CARD = "id_card";

    public static final String INVITE_ID = "invite_id";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

}
