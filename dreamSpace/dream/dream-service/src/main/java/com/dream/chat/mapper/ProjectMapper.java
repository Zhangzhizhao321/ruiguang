package com.dream.chat.mapper;

import com.dream.chat.entity.Institution;
import com.dream.chat.entity.Project;
import com.dream.chat.vo.res.InstitutionProjectResVo;
import com.dream.chat.vo.res.ProjectDetailResVo;
import com.dream.chat.vo.res.ProjectResVo;
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
public interface ProjectMapper extends SuperMapper<Project> {

    List<ProjectResVo> getProjectList(@Param("flag")Integer flag, @Param("current")Long current, @Param("size") Long size);

    List<InstitutionProjectResVo> getRecommendProjectList(@Param("current")Long current, @Param("size") Long size);

    List<ProjectResVo> getProjectListForBs(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("overFlag")Integer overFlag,@Param("flag")Integer flag,@Param("id")String id);

    ProjectDetailResVo getProjectDetail(@Param("id")String id);

    void updateProjectLookCount(@Param("id")String id);
}
