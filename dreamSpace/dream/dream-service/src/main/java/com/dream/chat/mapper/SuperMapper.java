package com.dream.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 自定义父类 SuperMapper
 * 注意这个类不要让 mp 扫描到！！
 * <p>
 *
 * @author ouyangzhiming
 * @since 2018-08-27
 */
public interface SuperMapper<T> extends BaseMapper<T> {

    // 这里可以写 mapper 层公共方法、 注意！！ 多泛型的时候请将泛型T放在第一位.
}
