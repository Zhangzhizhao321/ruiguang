package com.dream.chat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.math.BigDecimal;
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
@TableName("t_project_category")
public class ProjectCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

    @TableField("category_name")
    private String categoryName;

    @TableField("min_amount")
    private BigDecimal minAmount;

    @TableField("max_amount")
    private BigDecimal maxAmount;

    @TableField("category_note")
    private String categoryNote;

    @TableField("create_time")
    private Date createTime;


    public static final String ID = "id";

    public static final String CATEGORY_NAME = "category_name";

    public static final String MIN_AMOUNT = "min_amount";

    public static final String MAX_AMOUNT = "max_amount";

    public static final String CATEGORY_NOTE = "category_note";

    public static final String CREATE_TIME = "create_time";

}
