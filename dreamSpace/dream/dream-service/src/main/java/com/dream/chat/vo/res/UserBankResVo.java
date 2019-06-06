package com.dream.chat.vo.res;

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
@ApiModel(value = "用户银行", description = "用户银行")
public class UserBankResVo {

    private String id;

    private String img;

    private String bankName;

    private String userName;

    private String bankNum;

}
