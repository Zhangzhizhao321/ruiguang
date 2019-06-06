package com.dream.chat.entity;

import java.math.BigDecimal;
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
@TableName("t_user_wallet")
public class UserWallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 捐助金额
     */
    @TableField("contribution")
    private BigDecimal contribution;

    /**
     * 体现金额
     */
    @TableField("withdrawal")
    private BigDecimal withdrawal;

    @TableField("balance")
    private BigDecimal balance;

    /**
     * 体现金额
     */
    @TableField("total")
    private BigDecimal total;

    /**
     * 爱心值
     */
    @TableField("love_value")
    private Long loveValue;

    /**
     * 帮助次数
     */
    @TableField("help_count")
    private Long helpCount;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String CONTRIBUTION = "contribution";

    public static final String WITHDRAWAL = "withdrawal";

    public static final String BALANCE = "balance";

    public static final String TOTAL = "total";

    public static final String LOVE_VALUE = "love_value";

    public static final String HELP_COUNT = "help_count";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

}
