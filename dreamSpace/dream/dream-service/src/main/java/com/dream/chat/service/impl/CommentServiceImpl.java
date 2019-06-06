package com.dream.chat.service.impl;

import com.dream.chat.entity.Comment;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.CommentMapper;
import com.dream.chat.service.CommentService;
import com.dream.chat.vo.req.CommentReqVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-05
 */
@Service
public class CommentServiceImpl extends SuperServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public Comment addComment(CommentReqVo commentReqVo) {
        Comment comment = new Comment();
        comment.setAmount(commentReqVo.getAmount());
        comment.setContent(commentReqVo.getContent());
        comment.setCreateTime(new Date());
        comment.setIsAnonymous(commentReqVo.getIsAnonymous());
        comment.setProjectId(commentReqVo.getProjectId());
        comment.setMaxParentId("0");
        comment.setParentId("0");
        comment.setUserId(commentReqVo.getUserId());
        this.save(comment);
        return comment;
    }
}
