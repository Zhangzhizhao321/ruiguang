package com.dream.chat.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * Mybatis Plus 自定义元对象字段填充控制器，实现公共字段自动写入
 * <p>
 *
 * @author ouyangzhiming
 * @since 2018-08-21
 */
@Component
public class DefaultMetaObjectHandler implements MetaObjectHandler {

    /**
     * 新增时填充的字段
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        Date currentTime = new Date();

        Object gmtCreate = this.getFieldValByName("gmtCreate", metaObject);
        if (null == gmtCreate) {
            setFieldValByName("gmtCreate", currentTime, metaObject);
        }

        Object gmtModified = this.getFieldValByName("gmtModified", metaObject);
        if (null == gmtModified) {
            setFieldValByName("gmtModified", currentTime, metaObject);
        }

        Object deleted = this.getFieldValByName("deleted", metaObject);
        if (null == deleted) {
            setFieldValByName("deleted", false, metaObject);
        }

        Object version = this.getFieldValByName("version", metaObject);
        if (null == version) {
            setFieldValByName("version", 0, metaObject);
        }
    }

    /**
     * 更新时需要填充字段
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Object gmtModified = this.getFieldValByName("gmtModified", metaObject);

        if (null == gmtModified) {
            setFieldValByName("gmtModified", new Date(), metaObject);
        }
    }
}
