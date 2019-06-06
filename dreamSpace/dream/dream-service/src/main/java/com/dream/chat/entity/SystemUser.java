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
 * @since 2019-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_system_user")
public class SystemUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 电话号码 用于登录
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField("mobile")
    private String mobile;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    @TableField("create_time")
    private Date createTime;

    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;


    public static final String ID = "id";

    public static final String MOBILE = "mobile";

    public static final String PASSWORD = "password";

    public static final String CREATE_TIME = "create_time";

    public static final String LAST_LOGIN_TIME = "last_login_time";

}
