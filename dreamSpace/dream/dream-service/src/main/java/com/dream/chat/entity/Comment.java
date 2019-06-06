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
 * @since 2019-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField("user_id")
    private String userId;

    @TableField("project_id")
    private String projectId;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 上一级评论Id
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 第一条评论id
     */
    @TableField("max_parent_id")
    private String maxParentId;

    /**
     * 是否匿名
     */
    @TableField("is_anonymous")
    private Integer isAnonymous;

    /**
     * 捐助金额
     */
    @TableField("amount")
    private BigDecimal amount;

    @TableField("create_time")
    private Date createTime;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String PROJECT_ID = "project_id";

    public static final String CONTENT = "content";

    public static final String PARENT_ID = "parent_id";

    public static final String MAX_PARENT_ID = "max_parent_id";

    public static final String IS_ANONYMOUS = "is_anonymous";

    public static final String AMOUNT = "amount";

    public static final String CREATE_TIME = "create_time";

}
