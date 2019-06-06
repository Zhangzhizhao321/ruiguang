package com.dream.chat.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserBankReqVo {

    @ApiModelProperty("银行卡号")
    private String bankNum;

    @ApiModelProperty("银行名称")
    private String bankName;

    @ApiModelProperty("机构号")
    private String bankNo;

    @ApiModelProperty("真实名称")
    private String userName;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("电话号码")
    private String mobile;

    @ApiModelProperty("流水号")
    private String requestNo;

    //后面填上
    private String userId;

    @ApiModelProperty("验证码")
    private String verifyCode;

}
