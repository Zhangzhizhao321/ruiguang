package com.dream.chat.vo.res;

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
@ApiModel(value = "后台提现订单", description = "后台提现订单")
public class BsWithdrawResVo {

    private String Id;

    private String bn;

    private String status;

    private String type;

    private String userName;

    private BigDecimal amount;

    private BigDecimal arrivalAccount;

    private String bankName;

    private String bankNum;

    //private String comment;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    private String remark;


}
