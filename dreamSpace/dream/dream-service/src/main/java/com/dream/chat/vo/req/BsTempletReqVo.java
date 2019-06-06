package com.dream.chat.vo.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(value = "后台添加模板", description = "后台添加模板")
public class BsTempletReqVo extends PageReqVo{

    //1标题 2说明
    private Integer flag;

    private String title;

    private String content;

    private Integer relationId;

    private Integer categoryId;

    //删除需要
    private String id;

    private String ids;


}
