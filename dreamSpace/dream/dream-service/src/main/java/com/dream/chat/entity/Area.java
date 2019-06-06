package com.dream.chat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 区域字典
 * </p>
 *
 * @author yangjing
 * @since 2019-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_area")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 区域主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 区域名称
     */
    @TableField("area_name")
    private String areaName;

    /**
     * 区域代码
     */
    @TableField("area_code")
    private String areaCode;

    /**
     * 区域简称
     */
    @TableField("area_short")
    private String areaShort;

    /**
     * 是否热门(0:否、1:是)
     */
    @TableField("area_is_hot")
    private String areaIsHot;

    /**
     * 区域序列
     */
    @TableField("area_sequence")
    private Integer areaSequence;

    /**
     * 上级主键
     */
    @TableField("area_parent_id")
    private Integer areaParentId;


    public static final String ID = "id";

    public static final String AREA_NAME = "area_name";

    public static final String AREA_CODE = "area_code";

    public static final String AREA_SHORT = "area_short";

    public static final String AREA_IS_HOT = "area_is_hot";

    public static final String AREA_SEQUENCE = "area_sequence";

    public static final String AREA_PARENT_ID = "area_parent_id";

}
