package com.dream.chat.extension;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * SuperService 实现类（ 泛型：M 是 mapper 对象，T 是实体 ， PK 是主键泛型 ）
 * <p>
 *
 * @author ouyangzhiming
 * @since 2018-08-31
 */
@SuppressWarnings("unchecked")
public class SuperServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

}
