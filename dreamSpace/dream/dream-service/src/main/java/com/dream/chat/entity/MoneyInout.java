package com.dream.chat.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_money_inout")
public class MoneyInout implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField("user_id")
    private String userId;

    @TableField("order_id")
    private String orderId;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("inount")
    private Integer inount;

    @TableField("note")
    private String note;

    @TableField("create_time")
    private Date createTime;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String ORDER_ID = "order_id";

    public static final String AMOUNT = "amount";

    public static final String INOUNT = "inount";

    public static final String NOTE = "note";

    public static final String CREATE_TIME = "create_time";

}
