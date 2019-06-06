package com.dream.chat.vo.req;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(value = "机构", description = "机构")
public class InstitutionReqVo{

    private String id;

    private String headImg;

    private String institutionName;

    private String phoneNumber;

    private String userName;

    /**
     * 银行卡
     */
    private String bankNum;

    /**
     * 银行机构号
     */
    private String bankNo;

    /**
     * 银行机构名
     */
    private String bankName;

    private String profit;

    private String idCard;

}
