package com.dream.chat.vo.res;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@Data
public class ProjectTempletResVo{

    private String id;

    private Integer categoryId;

    private Integer relashionId;

    //private String title;

    private String content;

    private Date createTime;

    private Date updateTime;

    private BigDecimal amount;

    private Integer age;

    private String userName;

}
