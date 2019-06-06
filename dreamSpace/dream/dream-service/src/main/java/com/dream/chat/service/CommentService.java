package com.dream.chat.service;

import com.dream.chat.entity.Comment;
import com.dream.chat.vo.req.CommentReqVo;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-05
 */
public interface CommentService extends SuperService<Comment> {

    Comment addComment(CommentReqVo commentReqVo);

}
