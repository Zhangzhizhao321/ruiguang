package com.dream.chat.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(value = "后台查询项目条件", description = "后台查询项目条件")
public class BsProjectReqVo extends PageReqVo{

    private String startTime;

    private String endTime;

    private Integer overFlag;

    private Integer flag;

    private String id;


}
