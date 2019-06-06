package com.dream.chat.mapper;

import com.dream.chat.entity.Banner;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
public interface BannerMapper extends SuperMapper<Banner> {

    void delBanner(@Param("id")String id);

}
