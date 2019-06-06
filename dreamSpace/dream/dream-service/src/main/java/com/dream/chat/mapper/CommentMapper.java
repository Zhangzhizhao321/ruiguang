package com.dream.chat.mapper;

import com.dream.chat.entity.Comment;
import com.dream.chat.vo.res.CommentResVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangjing
 * @since 2019-05-05
 */
public interface CommentMapper extends SuperMapper<Comment> {

    List<CommentResVo> getCommentList(@Param("projectId")String projectId,@Param("current")Long current,@Param("size")Long size);
}
