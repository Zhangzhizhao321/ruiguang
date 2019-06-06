package com.dream.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.cache.RedisService;
import com.dream.chat.cache.dto.ProjectDTO;
import com.dream.chat.common.utils.CommonUtil;
import com.dream.chat.common.utils.WechatProOverUtil;
import com.dream.chat.constant.*;
import com.dream.chat.entity.*;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.*;
import com.dream.chat.service.*;
import com.dream.chat.vo.req.BsProjectReqVo;
import com.dream.chat.vo.req.ProjectReqVo;
import com.dream.chat.vo.res.CommentResVo;
import com.dream.chat.vo.res.InstitutionProjectResVo;
import com.dream.chat.vo.res.ProjectDetailResVo;
import com.dream.chat.vo.res.ProjectResVo;
import com.dream.common.core.api.R;
import com.dream.common.core.util.PageUtil;
import com.dream.common.core.util.StringUtil;
import com.dream.common.core.util.SyncLock;
import com.dream.common.core.util.TimeTools;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.math.BigDecimal.ROUND_DOWN;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@Service
public class ProjectServiceImpl extends SuperServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Resource
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectPicService projectPicService;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserProjectService userProjectService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserWalletService userWalletService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private MoneyInoutService moneyInoutService;

    @Resource
    private UserMapper userMapper;


    @Override
    public List<ProjectResVo> getProjectList(Integer flag, Long current, Long size) {
        //flag 0:热门 1：最新
        if (current == null) {
            current = 1L;
        }
        if (size == null) {
            size = 5L;
        }
        List<ProjectResVo> projectList = projectMapper.getProjectList(flag, size * (current - 1), size);
        for (ProjectResVo vo : projectList) {
            Integer days = TimeTools.differentDays(new Date(), TimeTools.forma3(vo.getOverTime()));
            vo.setOverDays(days < 0 ? 0 : days);
            BigDecimal percent = vo.getNowAmount().multiply(new BigDecimal(100)).divide(vo.getTargetAmount(), 2, ROUND_DOWN);
            vo.setPercent(percent);
        }
        return projectList;
    }

    @Override
    public Map getProjectListForBs(BsProjectReqVo rvo) {
        List<ProjectResVo> projectListForBs = projectMapper.getProjectListForBs(rvo.getStartTime(), rvo.getEndTime(), rvo.getOverFlag(), rvo.getFlag(), rvo.getId());
        for (ProjectResVo vo : projectListForBs) {
            vo.setRelation(RelationEnum.of(vo.getRelationId()).alias);
        }
        //System.out.println(JSON.toJSONString(projectListForBs));
        Map<String, Object> map = PageUtil.ListByPage(rvo.getCurrent().intValue(), rvo.getSize().intValue(), projectListForBs);
        return map;
    }

    @Override
    public ProjectDetailResVo getProjectDetail(String id, Long current, Long size) {
        ProjectDetailResVo projectDetailResVo = projectMapper.getProjectDetail(id);
        //projectMapper.updateProjectLookCount(id);
        List<ProjectPic> projectPics = projectPicService.list(new QueryWrapper<ProjectPic>().eq(ProjectPic.PROJECT_ID, id).orderByAsc(ProjectPic.CREATE_TIME));
        if (projectPics != null && projectPics.size() > 0) {
            List<String> imgs = new ArrayList<>();
            for (ProjectPic pic : projectPics) {
                String img = pic.getBaseUrl() + pic.getImgUrl();
                imgs.add(img);
            }
            projectDetailResVo.setImgs(imgs);
        }
        if (current == null) {
            current = 1L;
        }
        if (size == null) {
            size = 5L;
        }
        List<CommentResVo> commentResVos = commentMapper.getCommentList(id, size * (current - 1), size);
        projectDetailResVo.setComments(commentResVos);

        List<Problem> problem = problemService.getProblemByType(ProblemTypeEnum.SHARE.code);
        projectDetailResVo.setShareNote(problem.get(0).getNote());
        return projectDetailResVo;
    }

    @Override
    public Project updateProjectOver(String id) {
        Project project = new Project();
        project.setId(id);
        project.setIsOver(1);
        project.setOverTime(new Date());
        project.setUpdateTime(new Date());
        this.updateById(project);
        return project;
    }

    @Override
    @Transactional
    public R createProject(ProjectReqVo projectReqVo, HttpServletRequest request) {
        Project project = new Project();
        project.setUserId(projectReqVo.getUserId());
        project.setTitle(projectReqVo.getTitle());
        project.setTargetAmount(projectReqVo.getTargetAmount());
        project.setSort(500);
        project.setRelationId(projectReqVo.getRelationId() == null ? 1 : projectReqVo.getRelationId());
        project.setPublishFlag(projectReqVo.getPublishFlag());
        project.setIsPass(1);
        project.setCreateTime(new Date());
        project.setCategoryId(projectReqVo.getCategoryId());
        project.setCategoryName(projectReqVo.getCategoryName());
        project.setAddressId(0);
        project.setArea(projectReqVo.getArea());
        project.setIsOver(0);
        project.setOverTime(TimeTools.getFetureDate(30));
        project.setUpdateTime(new Date());
        project.setContent(projectReqVo.getContent());
        if (projectReqVo.getImgs() != null && projectReqVo.getImgs().size() > 0) {
            project.setIndexPic(ImgUrlContanst.BASE_URL + projectReqVo.getImgs().get(0));
        }

        this.save(project);

        //添加素材图片
        for (String str : projectReqVo.getImgs()) {
            projectPicService.addProjectPic(str, project.getId(), 0);
        }

        //添加用户和项目表
        projectReqVo.setProjectId(project.getId());
        userProjectService.addUserProject(projectReqVo);

        ProjectDTO projectDTO = new ProjectDTO(project.getId(), project.getUserId(), project.getTitle());
        redisService.set(projectDTO, 30, TimeUnit.DAYS);

        return R.ok(project.getId());
    }

    @Override
    public R createInstitutionProject(ProjectReqVo projectReqVo, HttpServletRequest request) {
        Project project = new Project();
        project.setUserId(projectReqVo.getUserId());
        project.setTitle(projectReqVo.getTitle());
        project.setTargetAmount(projectReqVo.getTargetAmount());
        project.setSort(500);
        project.setRelationId(projectReqVo.getRelationId() == null ? 1 : projectReqVo.getRelationId());
        project.setPublishFlag(projectReqVo.getPublishFlag());
        project.setIsPass(1);
        project.setCreateTime(new Date());
        project.setCategoryId(projectReqVo.getCategoryId());
        project.setCategoryName(projectReqVo.getCategoryName());
        project.setAddressId(0);
        project.setArea(projectReqVo.getArea());
        project.setIsOver(0);
        project.setOverTime(projectReqVo.getOverTime());
        project.setUpdateTime(new Date());
        project.setContent(projectReqVo.getContent());
        if (projectReqVo.getImgs() != null && projectReqVo.getImgs().size() > 0) {
            project.setIndexPic(ImgUrlContanst.BASE_URL + projectReqVo.getImgs().get(0));
        }

        this.save(project);

        //添加素材图片
        for (String str : projectReqVo.getImgs()) {
            projectPicService.addProjectPic(str, project.getId(), 0);
        }

        if (StringUtils.isNotBlank(projectReqVo.getDetailImg()) && StringUtils.isNotBlank(projectReqVo.getKnowImg()) && StringUtils.isNotBlank(projectReqVo.getInstitutionImg())) {
            //String detailUrl = CommonUtil.upload(ImgUrlContanst.PROJECT_IMG,projectReqVo.getDetailImg(),request);
            projectPicService.addProjectPic(projectReqVo.getDetailImg(), project.getId(), ImgCategoryEnum.DETAIL.code);
            //String knowUrl = CommonUtil.upload(ImgUrlContanst.PROJECT_IMG,projectReqVo.getKnowImg(),request);
            projectPicService.addProjectPic(projectReqVo.getKnowImg(), project.getId(), ImgCategoryEnum.KNOW.code);
            //String institutionUrl = CommonUtil.upload(ImgUrlContanst.PROJECT_IMG,projectReqVo.getInstitutionImg(),request);
            projectPicService.addProjectPic(projectReqVo.getInstitutionImg(), project.getId(), ImgCategoryEnum.INSTITUTION.code);
        }

        //添加用户和项目表
        projectReqVo.setProjectId(project.getId());
        userProjectService.addUserProject(projectReqVo);

        ProjectDTO projectDTO = new ProjectDTO(project.getId(), project.getUserId(), project.getTitle());
        redisService.set(projectDTO, 30, TimeUnit.DAYS);

        return R.ok(project.getId());
    }

    /**
     * 提前结束项目
     *
     * @param userId
     * @param
     * @return
     */
    @Override
    @Transactional
    public R overProject(String userId, Project project) {
        //个人项目有提前结束 机构项目报名了就没有提前结束
        String projectId = project.getId();
        try {
            SyncLock.lock("Ov" + projectId);
            //Project project = this.getById(projectId);
            ProjectDTO projectDTO = redisService.get(RedisConstant.PROJECT + ":" + projectId, ProjectDTO.class);
            if (projectDTO == null) {
                return R.ok("未能找到该项目");
            }
            if (project.getRelationId() == RelationEnum.INSTITUTION.code) {
                return R.failed("");
            }
            if (project == null) {
                return R.ok("未能找到该项目");
            }
            if (!userId.equals(project.getUserId())) {
                return R.ok("未能找到该您的项目信息");
            }
            if (project.getIsOver() == 1) {
                return R.ok("该项目已结束");
            }
            this.updateProjectOver(projectId);

            //修改账户可提现金额
            UserProject userProject = userProjectService.getOne(new QueryWrapper<UserProject>().eq(UserProject.PROJECT_ID, projectId).eq(UserProject.USER_ID, userId));

            UserWallet userWallet = userWalletService.getMyWallet(userId);
            userWallet.setWithdrawal(userWallet.getWithdrawal().add(userProject.getNowAmount()));
            userWallet.setBalance(userWallet.getBalance().subtract(userProject.getNowAmount()));
            userWallet.setUpdateTime(new Date());
            userWalletService.updateById(userWallet);

            redisService.delObj(RedisConstant.PROJECT + ":" + projectId);
            return R.ok("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SyncLock.unSyncLock("Ov" + projectId);
        }
        return null;
    }

    @Override
    public Project hotProject(String projectId) {
        Project project = this.getById(projectId);
        if (project.getSort() == 1) {
            project.setSort(500);
        } else {
            project.setSort(1);
        }
        project.setUpdateTime(new Date());
        this.updateById(project);
        return project;
    }

    @Override
    public Integer newProjectCount() {
        String day = TimeTools.format1(new Date());
        List<Project> projectList = this.list(new QueryWrapper<Project>().like(Project.CREATE_TIME, day));
        return projectList.size();
    }

    @Override
    public Integer totalProjectCount() {
        List<Project> projectList = this.list(null);
        return projectList.size();
    }

    @Override
    public List<InstitutionProjectResVo> getInstitutionProList(Long current, Long size) {
        List<InstitutionProjectResVo> recommendProjectList = projectMapper.getRecommendProjectList(current, size);
        for (InstitutionProjectResVo pro : recommendProjectList) {
            Integer days = TimeTools.differentDays(pro.getCreateTime(), pro.getOverTime());
            Integer percent = days / 100;
            pro.setPercent(BigDecimal.valueOf(percent));
            pro.setOverDays(days);
        }
        return recommendProjectList;
    }
}
