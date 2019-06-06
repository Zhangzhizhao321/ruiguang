package com.dream.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 短信通知
 * </p>
 *
 * @author lw
 * @since 2018-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sms")
public class Sms extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 手机号码
     */
    @TableField("phone_number")
    private String phoneNumber;

    /**
     * 短信内容
     */
    @TableField("sms_content")
    private String smsContent;

    /**
     * 发送时间
     */
    @TableField("send_time")
    private Date sendTime;

    /**
     * 发送状态（0：未发送，1：已发送，2：发送失败）
     */
    @TableField("send_state")
    private Integer sendState;

    /**
     * 根据业务定义（VCODE：验证码）
     */
    @TableField("template_type")
    private String templateType;

    @TableField("request_id")
    private String requestId;

    @TableField("code")
    private String code;

    /**
     * 说明
     */
    @TableField("message")
    private String message;

    @TableField("biz_id")
    private String bizId;

	@TableField("out_id")
    private String outId;


    public static final String ID = "id";

    public static final String PHONE_NUMBER = "phone_number";

    public static final String SMS_CONTEXT = "sms_context";

    public static final String SEND_TIME = "send_time";

    public static final String SEND_STATE = "send_state";

    public static final String TEMPLATE_TYPE = "template_type";

    public static final String REQUEST_ID = "request_id";

    public static final String CODE = "code";

    public static final String MESSAGE = "message";

    public static final String BIZ_ID = "biz_id";

    public static final String OUT_ID = "out_id";

}
