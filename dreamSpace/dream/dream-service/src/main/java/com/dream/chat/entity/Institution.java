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
 * @since 2019-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_institution")
public class Institution implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 头像
     */
    @TableField("head_img")
    private String headImg;

    @TableField("institution_name")
    private String institutionName;

    @TableField("phone_number")
    private String phoneNumber;

    @TableField("user_name")
    private String userName;

    /**
     * 银行卡
     */
    @TableField("bank_num")
    private String bankNum;

    /**
     * 银行机构号
     */
    @TableField("bank_no")
    private String bankNo;

    @TableField("id_card")
    private String idCard;
    /**
     * 银行机构名
     */
    @TableField("bank_name")
    private String bankName;

    @TableField("profit")
    private String profit;


    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;


    public static final String ID = "id";

    public static final String HEAD_IMG = "head_img";

    public static final String INSTITUTION_NAME = "institution_name";

    public static final String PHONE_NUMBER = "phone_number";

    public static final String USER_NAME = "user_name";

    public static final String BANK_NUM = "bank_num";

    public static final String BANK_NO = "bank_no";

    public static final String BANK_NAME = "bank_name";

    public static final String ID_CARD = "id_card";

    public static final String PROFIT = "profit";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

}
