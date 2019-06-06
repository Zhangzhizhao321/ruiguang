package com.dream.chat.mapper;

import com.dream.chat.entity.UserProject;
import com.dream.chat.vo.res.UserProjectListResVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
public interface UserProjectMapper extends SuperMapper<UserProject> {

    List<UserProjectListResVo> getMyProject(@Param("userId")String userId, @Param("current")Long current, @Param("size")Long size);

}
