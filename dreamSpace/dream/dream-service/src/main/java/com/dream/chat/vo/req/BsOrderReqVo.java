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
@ApiModel(value = "后台捐款订单", description = "后台捐款订单")
public class BsOrderReqVo extends PageReqVo{

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("项目id")
    private String projectId;

}
