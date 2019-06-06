package com.dream.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.chat.constant.ProblemTypeEnum;
import com.dream.chat.constant.ProjectCategoryEnum;
import com.dream.chat.constant.RelationEnum;
import com.dream.chat.entity.ProjectTemplet;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.ProjectTempletMapper;
import com.dream.chat.service.ProjectTempletService;
import com.dream.chat.vo.req.BsTempletReqVo;
import com.dream.chat.vo.req.ProjectTempletReqVo;
import com.dream.chat.vo.res.BsTempletResVo;
import com.dream.chat.vo.res.ProjectTempletResVo;
import com.dream.common.core.api.R;
import com.dream.common.core.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@Service
public class ProjectTempletServiceImpl extends SuperServiceImpl<ProjectTempletMapper, ProjectTemplet> implements ProjectTempletService {

    @Override
    public Map<String, Object> useProjectTemplet(ProjectTempletReqVo projectTempletReqVo) {
        //0代表名称 1代表年龄 2代表地址 3代表具体事宜
        QueryWrapper queryWrapper = new QueryWrapper<ProjectTemplet>();
        queryWrapper.eq(ProjectTemplet.CATEGORY_ID, projectTempletReqVo.getCategoryId());
        if (projectTempletReqVo.getRelationId() == RelationEnum.ONESELF.code) {
            queryWrapper.eq(ProjectTemplet.RELASHION_ID, RelationEnum.ONESELF.code);
        } else {
            queryWrapper.notIn(ProjectTemplet.RELASHION_ID, RelationEnum.ONESELF.code);
        }
        List<ProjectTemplet> projectTemplets = this.list(queryWrapper);
        //List<ProjectTemplet> projectTemplets = this.list(null);
        List<String> titles = new ArrayList<>();
        List<String> contents = new ArrayList<>();
        if (projectTemplets != null && projectTemplets.size() > 0) {
            for (ProjectTemplet projectTemplet : projectTemplets) {
                if (StringUtils.isNotBlank(projectTemplet.getTitle())) {
                    titles.add(projectTemplet.getTitle());
                }
                if (projectTemplet.getRelashionId() != projectTempletReqVo.getRelationId()) {
                    continue;
                }
                if (StringUtils.isNotBlank(projectTemplet.getContent())) {
                    String content = projectTemplet.getContent();
                    if(StringUtils.isNotBlank(projectTempletReqVo.getUserName())){
                        content = content.replace("{0}", projectTempletReqVo.getUserName());
                    }
                    if(projectTempletReqVo.getAge() != null && projectTempletReqVo.getAge() > 0){
                        content = content.replace("{1}", projectTempletReqVo.getAge().toString());
                    }
                    if(StringUtils.isNotBlank(projectTempletReqVo.getAddress())){
                        content = content.replace("{2}", projectTempletReqVo.getAddress());
                    }
                    if(StringUtils.isNotBlank(projectTempletReqVo.getThings())){
                        content = content.replace("{3}", projectTempletReqVo.getThings());
                    }
                    if(projectTempletReqVo.getRelationId() != null){
                        content = content.replace("{4}", RelationEnum.of(projectTempletReqVo.getRelationId()).alias);
                    }

                    StringBuffer strContent = new StringBuffer();
                    strContent.append(content);

                    if (StringUtils.isNotBlank(projectTempletReqVo.getMobile())) {
                        strContent.append("\n电话:" + projectTempletReqVo.getMobile());
                    }
                    if (StringUtils.isNotBlank(projectTempletReqVo.getWx_number())) {
                        strContent.append("\n微信号:" + projectTempletReqVo.getWx_number());
                    }
                    if (StringUtils.isNotBlank(projectTempletReqVo.getSchool())) {
                        strContent.append("\n学校:" + projectTempletReqVo.getSchool());
                    }
                    if (StringUtils.isNotBlank(projectTempletReqVo.getCompany())) {
                        strContent.append("\n公司:" + projectTempletReqVo.getCompany());
                    }
                    //projectTemplet.setContent(strContent.toString());

                    //projectTempletResVos.add(projectTempletResVo);
                    contents.add(strContent.toString());
                }
            }
            ProjectTempletResVo projectTempletResVo = new ProjectTempletResVo();
            BeanUtil.copyProperties(projectTempletReqVo, projectTempletResVo);
            projectTempletResVo.setAmount(projectTempletReqVo.getTargetAmount());
            projectTempletResVo.setAge(projectTempletReqVo.getAge());
            projectTempletResVo.setUserName(projectTempletReqVo.getUserName());
            projectTempletResVo.setAmount(projectTempletReqVo.getTargetAmount());
            projectTempletResVo.setRelashionId(projectTempletReqVo.getRelationId());

            Map<String, Object> result = new HashMap<>();
            result.put("projectTempletResVo", projectTempletResVo);
            result.put("titles", titles);
            result.put("contents", contents);
            return result;
        }
        return null;
    }

    @Override
    public ProjectTemplet addTemplet(BsTempletReqVo vo) {
        ProjectTemplet projectTemplet = new ProjectTemplet();
        projectTemplet.setContent(vo.getContent());
        projectTemplet.setTitle(vo.getTitle());
        projectTemplet.setRelashionId(vo.getRelationId());
        projectTemplet.setCategoryId(vo.getCategoryId());
        projectTemplet.setCreateTime(new Date());
        this.save(projectTemplet);
        return projectTemplet;
    }

    @Override
    public R removeTemplet(BsTempletReqVo vo) {
        String[] ids = vo.getIds().split(",");
        List<ProjectTemplet> projectTemplets = this.list(new QueryWrapper<ProjectTemplet>().in(ProjectTemplet.ID, ids));
        if (projectTemplets != null && projectTemplets.size() > 0) {
            for(ProjectTemplet projectTemplet : projectTemplets){
                if (vo.getFlag() == 1) {
                    //标题
                    if (StringUtils.isNotBlank(projectTemplet.getContent())) {
                        projectTemplet.setTitle("");
                        this.updateById(projectTemplet);
                    } else {
                        this.removeById(projectTemplet);
                    }
                }

                if (vo.getFlag() == 2) {
                    if (StringUtils.isNotBlank(projectTemplet.getTitle())) {
                        projectTemplet.setContent("");
                        this.updateById(projectTemplet);
                    } else {
                        this.removeById(projectTemplet);
                    }
                }
            }
        }
        return R.ok("");
    }

    @Override
    public R updateTemplet(BsTempletReqVo vo) {
        ProjectTemplet projectTemplet = this.getById(vo.getId());
        if (vo.getFlag() == 1) {
            //标题
            projectTemplet.setTitle(vo.getTitle());
            this.updateById(projectTemplet);
        }

        if (vo.getFlag() == 2) {
            projectTemplet.setContent(vo.getContent());
            this.updateById(projectTemplet);
        }

        return R.ok("");
    }

    @Override
    public R templetList(BsTempletReqVo vo) {
        QueryWrapper<ProjectTemplet> queryWrapper = new QueryWrapper<>();
        if (vo.getFlag() == 1) {
            queryWrapper.isNotNull(ProjectTemplet.TITLE).notIn(ProjectTemplet.TITLE, "");
        }
        if (vo.getFlag() == 2) {
            queryWrapper.isNotNull(ProjectTemplet.CONTENT).notIn(ProjectTemplet.CONTENT, "");
        }
        if (vo.getCategoryId() != null && vo.getCategoryId() > 0) {
            queryWrapper.eq(ProjectTemplet.CATEGORY_ID, vo.getCategoryId());
        }
        if (vo.getRelationId() != null && vo.getRelationId() > 0) {
            queryWrapper.eq(ProjectTemplet.RELASHION_ID, vo.getRelationId());
        }
        IPage<ProjectTemplet> iPage = new Page<>();
        iPage.setCurrent(vo.getCurrent());
        iPage.setSize(vo.getSize());
        IPage<ProjectTemplet> ipage = this.page(iPage, queryWrapper);
        List<ProjectTemplet> pageList = ipage.getRecords();
        if (pageList != null && pageList.size() > 0) {
            List<BsTempletResVo> titles = new ArrayList<>();
            List<BsTempletResVo> contents = new ArrayList<>();
            for (ProjectTemplet t : pageList) {
                BsTempletResVo bsTempletResVo = new BsTempletResVo();
                BeanUtils.copyProperties(t, bsTempletResVo);
                bsTempletResVo.setCategory(ProjectCategoryEnum.of(t.getCategoryId()).alias);
                bsTempletResVo.setRelation(RelationEnum.of(t.getRelashionId()).alias);
                if (StringUtils.isNotBlank(t.getTitle())) {
                    titles.add(bsTempletResVo);
                }
                if (StringUtils.isNotBlank(t.getContent())) {
                    contents.add(bsTempletResVo);
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("titles", titles);
            map.put("totalCount", ipage.getTotal());
            map.put("contents", contents);
            return R.ok(map);
        }
        return R.failed("");
    }
}
