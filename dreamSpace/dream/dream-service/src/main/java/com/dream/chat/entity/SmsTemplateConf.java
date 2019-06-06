package com.dream.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 短信通知模板
 * </p>
 *
 * @author lw
 * @since 2018-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("nt_sms_template_conf")
public class SmsTemplateConf extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private Long id;

    /**
     * 短信通知模板编号
     */
    @TableField("template_code")
    private String templateCode;

    /**
     * 内容
     */
    @TableField("signName")
    private String signName;

    /**
     * 状态（0：停用，1：启用）
     */
    @TableField("state")
    private Integer state;

    /**
     * 一种模板类型对应一种业务类型
     */
    @TableField("template_type")
    private String templateType;


    public static final String ID = "id";

    public static final String TEMPLATE_CODE = "template_code";

    public static final String SIGNNAME = "signName";

    public static final String STATE = "state";

    public static final String TEMPLATE_TYPE = "template_type";

}
