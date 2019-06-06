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
@ApiModel(value = "捐款订单", description = "捐款订单")
public class OrderReqVo extends SupperReqVo{

    @ApiModelProperty("金额")
    private BigDecimal amount;

    @ApiModelProperty("项目id")
    private String projectId;

    @ApiModelProperty("用户项目id")
    private String userProjectId;

    @ApiModelProperty("是否匿名")
    private Integer isAnonymous;

}
