package com.dream.chat.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *  基础请求vo
 * </p>
 *
 * @author lw
 * @since 2018-10-31
 */
@Data
public class PageReqVo implements Serializable {

   private Long current;

   private Long size;

}
