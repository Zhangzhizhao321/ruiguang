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
@ApiModel(value = "钱包信息", description = "钱包信息")
public class WalletResVo {

    private BigDecimal withdrawal;

    private BigDecimal balance;

    private Integer cardCount;
}
