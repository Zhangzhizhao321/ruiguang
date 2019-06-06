package com.dream.chat.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.text.SimpleDateFormat;
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
@TableName("t_withdrawal")
public class Withdrawal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 流水号
     */
    @TableField("bn")
    private String bn;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("bank_num")
    private String bankNum;

    @TableField("arrival_account")
    private BigDecimal arrivalAccount;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    @TableField("type")
    private Integer type;

    @TableField("remark")
    private String remark;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;


    public static final String ID = "id";

    public static final String BN = "bn";

    public static final String USER_ID = "user_id";

    public static final String AMOUNT = "amount";

    public static final String ARRIVAL_ACCOUNT = "arrivalAccount";

    public static final String STATUS = "status";

    public static final String TYPE = "type";

    public static final String REMARK = "remark";

    public static final String BANK_NUM = "bank_num";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";


    public static String generateBn(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        // System.out.println("SP-" + sdf.format(new Date()));
        return "WD" + sdf.format(new Date());
    }

}
