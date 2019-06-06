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
@TableName("t_project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    @TableField("user_id")
    private String userId;

    /**
     * 目标金额
     */
    @TableField("target_amount")
    private BigDecimal targetAmount;

    /**
     * 说明
     */
    @TableField("content")
    private String content;

    /**
     * 显示图片
     */
    @TableField("index_pic")
    private String indexPic;

    /**
     * 是否通过
     */
    @TableField("is_pass")
    private Integer isPass;

    /**
     * 是否结束
     */
    @TableField("is_over")
    private Integer isOver;

    /**
     * 是否发布
     */
    @TableField("publish_flag")
    private Integer publishFlag;

    /**
     * 关系类型
     */
    @TableField("relation_id")
    private Integer relationId;

    /**
     * 类型id
     */
    @TableField("category_id")
    private Integer categoryId;

    @TableField("page_views")
    private Long pageViews;

    /**
     * 类型名称
     */
    @TableField("category_name")
    private String categoryName;

    @TableField("area")
    private String area;

    /**
     * 地址id
     */
    @TableField("address_id")
    private Integer addressId;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    @TableField("create_time")
    private Date createTime;

    /**
     * 结束时间
     */
    @TableField("over_time")
    private Date overTime;

    @TableField("update_time")
    private Date updateTime;


    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String USER_ID = "user_id";

    public static final String TARGET_AMOUNT = "target_amount";

    public static final String CONTENT = "content";

    public static final String INDEX_PIC = "index_pic";

    public static final String IS_PASS = "is_pass";

    public static final String IS_OVER = "is_over";

    public static final String PUBLISH_FLAG = "publish_flag";

    public static final String RELATION_ID = "relation_id";

    public static final String CATEGORY_ID = "category_id";

    public static final String CATEGORY_NAME = "category_name";

    public static final String ADDRESS_ID = "address_id";

    public static final String PAGE_VIEWS = "page_views";

    public static final String SORT = "sort";

    public static final String CREATE_TIME = "create_time";

    public static final String OVER_TIME = "over_time";

    public static final String UPDATE_TIME = "update_time";

}
