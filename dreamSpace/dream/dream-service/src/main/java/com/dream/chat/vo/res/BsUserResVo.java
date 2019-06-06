package com.dream.chat.vo.res;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangjing
 * @since 2019-04-28
 */
@Data
public class BsUserResVo implements Serializable {

    private String id;


    private String phoneNumber;


    private String headPic;


    private String wxOpenid;

    private String wxH5Openid;

    private String wxUnionid;

    private String userName;

    /**
     * 性别
     */
    private String sex;


    private String idCard;


    //private String inviteId;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

}
