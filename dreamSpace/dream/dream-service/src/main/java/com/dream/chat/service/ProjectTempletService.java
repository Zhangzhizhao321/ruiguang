package com.dream.chat.service;

import com.dream.chat.entity.ProjectTemplet;
import com.dream.chat.vo.req.BsTempletReqVo;
import com.dream.chat.vo.req.ProjectTempletReqVo;
import com.dream.chat.vo.res.ProjectTempletResVo;
import com.dream.common.core.api.R;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
public interface ProjectTempletService extends SuperService<ProjectTemplet> {

    Map<String,Object> useProjectTemplet(ProjectTempletReqVo projectTempletReqVo);

    ProjectTemplet addTemplet(BsTempletReqVo vo);

    R removeTemplet(BsTempletReqVo vo);

    R updateTemplet(BsTempletReqVo vo);

    R templetList(BsTempletReqVo vo);

}
