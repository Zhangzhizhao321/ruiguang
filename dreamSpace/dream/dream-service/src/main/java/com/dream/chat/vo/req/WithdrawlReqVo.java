package com.dream.chat.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
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
@ApiModel(value = "提现", description = "提现")
public class WithdrawlReqVo{

    @ApiModelProperty("金额")
    private BigDecimal amount;

    @ApiModelProperty("提现方式 银行卡：1")
    private Integer type;

    @ApiModelProperty("用户银行Id")
    private String userBankId;

    //后面填上
    private String userId;

    //提现填上
    private String bankNo;

    private String bankNum;

    private String userName;

    private String bn;

}
