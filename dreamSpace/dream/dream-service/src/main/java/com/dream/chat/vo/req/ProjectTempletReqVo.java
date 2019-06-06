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
@ApiModel(value = "项目模板", description = "项目模板")
public class ProjectTempletReqVo /*extends SupperReqVo*/{

    @ApiModelProperty("目标金额")
    private BigDecimal targetAmount;

    @ApiModelProperty("为谁筹款")
    private Integer relationId;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("用途")
    private Integer categoryId;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("具体事宜")
    private String things;


    @ApiModelProperty("手机")
    private String mobile;

    @ApiModelProperty("微信")
    private String wx_number;

    @ApiModelProperty("学校")
    private String school;

    @ApiModelProperty("公司")
    private String company;

}
