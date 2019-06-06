package com.dream.chat.service.impl;

import com.dream.chat.constant.ImgUrlContanst;
import com.dream.chat.entity.ProjectPic;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.ProjectPicMapper;
import com.dream.chat.service.ProjectPicService;
import com.dream.chat.utils.CommonUtil;
import com.dream.chat.vo.req.ProjectReqVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@Service
public class ProjectPicServiceImpl extends SuperServiceImpl<ProjectPicMapper, ProjectPic> implements ProjectPicService {

    @Override
    public ProjectPic addProjectPic(String img,String projectId,Integer categoryId) {
        ProjectPic pic = new ProjectPic();
        pic.setBaseUrl(ImgUrlContanst.BASE_URL);
        pic.setCategoryId(categoryId);
        pic.setCreateTime(new Date());
        pic.setImgUrl(img);
        pic.setProjectId(projectId);
        pic.setSort(0);
        this.save(pic);
        return pic;
    }
}
