package com.dream.chat.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2018-10-31
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "评论", description = "评论")
public class CommentReqVo extends SupperReqVo{

    @ApiModelProperty("金额")
    private BigDecimal amount;

    @ApiModelProperty("项目id")
    private String projectId;

    @ApiModelProperty("是否匿名 1：匿名 默认0")
    private Integer isAnonymous;

    @ApiModelProperty("内容")
    private String content;

}
