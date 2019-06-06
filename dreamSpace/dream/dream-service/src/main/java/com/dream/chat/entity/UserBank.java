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
 * @since 2019-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_bank")
public class UserBank implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField("user_id")
    private String userId;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 银行预留手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 身份证号
     */
    @TableField("id_card")
    private String idCard;

    /**
     * 银行编号
     */
    @TableField("bank_no")
    private String bankNo;

    @TableField("bank_name")
    private String bankName;

    /**
     * 银行卡号
     */
    @TableField("bank_num")
    private String bankNum;

    @TableField("create_time")
    private Date createTime;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String USER_NAME = "user_name";

    public static final String MOBILE = "mobile";

    public static final String ID_CARD = "id_card";

    public static final String BANK_NO = "bank_no";

    public static final String BANK_NAME = "bank_name";

    public static final String BANK_NUM = "bank_num";

    public static final String CREATE_TIME = "create_time";

}
