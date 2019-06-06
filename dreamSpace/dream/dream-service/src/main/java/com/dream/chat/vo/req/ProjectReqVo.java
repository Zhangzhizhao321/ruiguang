package com.dream.chat.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
@ApiModel(value = "发起项目", description = "发起项目")
public class ProjectReqVo /*extends SupperReqVo*/{

    @NotNull
    @ApiModelProperty("目标金额")
    private BigDecimal targetAmount;

    @NotNull
    @ApiModelProperty("标题")
    private String title;

    @NotNull
    @ApiModelProperty("说明")
    private String content;

    @ApiModelProperty("图片")
    private List<String> imgs;

    @ApiModelProperty("关系")
    private Integer relationId;

    @ApiModelProperty("是否发布 1：发布 0：草稿")
    private Integer publishFlag;

    @ApiModelProperty("用途")
    private Integer categoryId;

    @ApiModelProperty("用途名称")
    private String categoryName;

    @ApiModelProperty("地址")
    private String area;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("年龄")
    private Integer userAge;


    //后台tianjia
    private String detailImg;

    private String knowImg;

    private String institutionImg;

    private Date overTime;

    //后面添加
    private String userId;

    private String projectId;

}
