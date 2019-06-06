package com.dream.chat.vo.res;

import com.dream.common.core.util.TimeTools;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2018-10-31
 */
@Data
@ApiModel(value = "后台订单", description = "后台订单")
public class BsOrderResVo {

    private String Id;

    private String bn;

    private String status;

    private String userName;

    private String projectId;

    private String projectUserName;

    private BigDecimal amount;

    //private String comment;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String createTime;


}
