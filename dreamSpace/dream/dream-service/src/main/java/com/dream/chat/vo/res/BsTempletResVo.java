package com.dream.chat.vo.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

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
@ApiModel(value = "后台模板", description = "后台模板")
public class BsTempletResVo {

    private Integer id;

    private String category;

    private String relation;

    private String title;

    private String content;


}
