package com.dream.chat.vo.res;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
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
@ApiModel(value = "项目详情", description = "项目详情")
public class ProjectDetailResVo {

    private String Id;

    private String title;

    private String headPic;

    private String userId;

    private String userProjectId;

    private String userName;

    private BigDecimal nowAmount;

    private BigDecimal targetAmount;

    private BigDecimal helpCount;

    private String content;

    private List<String> imgs;

    private List<CommentResVo> comments;

    private Integer isOver;

    private String shareNote;

}
