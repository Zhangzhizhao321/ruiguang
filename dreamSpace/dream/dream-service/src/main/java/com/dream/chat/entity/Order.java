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
 * @since 2019-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField("bn")
    private String bn;

    @TableField("user_id")
    private String userId;

    @TableField("project_id")
    private String projectId;

    @TableField("user_project_id")
    private String userProjectId;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("status")
    private Integer status;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    /**
     * 是否匿名
     */
    @TableField("is_anonymous")
    private Integer isAnonymous;


    public static final String ID = "id";

    public static final String BN = "bn";

    public static final String USER_ID = "user_id";

    public static final String PROJECT_ID = "project_id";

    public static final String USER_PROJECT_ID = "user_project_id";

    public static final String AMOUNT = "amount";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String IS_ANONYMOUS = "is_anonymous";

    public static String generateBn(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        // System.out.println("SP-" + sdf.format(new Date()));
        return "BN" + sdf.format(new Date());
    }

}
