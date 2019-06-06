package com.dream.chat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2019-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("id")
    private String id;

    /**
     * userid
     */
    @TableField("uid")
    private String uid;

    /**
     * 接收人
     */
    @TableField("consignee")
    private String consignee;

    /**
     * 电话号码
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 省
     */
    @TableField("province")
    private Integer province;

    /**
     * 市
     */
    @TableField("city")
    private Integer city;

    /**
     * 区
     */
    @TableField("district")
    private Integer district;

    /**
     * 补充地址
     */
    @TableField("replenishAddress")
    private String replenishAddress;

    /**
     * 全地址
     */
    @TableField("fullAddress")
    private String fullAddress;

    /**
     * 是否默认
     */
    @TableField("isDefault")
    private Integer isDefault;

    /**
     * 状态 1 正常 2 已删除
     */
    @TableField("state")
    private Integer state;


    public static final String ID = "id";

    public static final String UID = "uid";

    public static final String CONSIGNEE = "consignee";

    public static final String MOBILE = "mobile";

    public static final String PROVINCE = "province";

    public static final String CITY = "city";

    public static final String DISTRICT = "district";

    public static final String REPLENISHADDRESS = "replenishAddress";

    public static final String FULLADDRESS = "fullAddress";

    public static final String ISDEFAULT = "isDefault";

    public static final String STATE = "state";

}
