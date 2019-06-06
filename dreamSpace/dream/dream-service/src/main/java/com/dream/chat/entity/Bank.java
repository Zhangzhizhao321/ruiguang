package com.dream.chat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("t_bank")
public class Bank implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机构号
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 银行名称
     */
    @TableField("bank_name")
    private String bankName;

    @TableField("img")
    private String img;

    @TableField("create_time")
    private Date createTime;


    public static final String ID = "id";

    public static final String BANK_NAME = "bank_name";

    public static final String IMG = "img";

    public static final String CREATE_TIME = "create_time";

}
