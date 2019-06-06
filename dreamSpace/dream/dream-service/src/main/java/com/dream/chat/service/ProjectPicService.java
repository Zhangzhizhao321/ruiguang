package com.dream.chat.service;

import com.dream.chat.entity.ProjectPic;
import com.dream.chat.vo.req.ProjectReqVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
public interface ProjectPicService extends SuperService<ProjectPic> {

    ProjectPic addProjectPic(String img,String projectId,Integer categoryId);

}
