package com.dream.chat.mapper;

import com.dream.chat.entity.UserBank;
import com.dream.chat.vo.res.UserBankResVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangjing
 * @since 2019-05-10
 */
public interface UserBankMapper extends SuperMapper<UserBank> {

    List<UserBankResVo> getMyCardList(@Param("userId")String userId);

}
