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
 * @since 2019-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_banner")
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField("base_url")
    private String baseUrl;

    @TableField("img_url")
    private String imgUrl;

    @TableField("sort")
    private Integer sort;

    @TableField("status")
    private Integer status;

    @TableField("create_time")
    private Date createTime;


    public static final String ID = "id";

    public static final String BASE_URL = "base_url";

    public static final String IMG_URL = "img_url";

    public static final String SORT = "sort";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

}
