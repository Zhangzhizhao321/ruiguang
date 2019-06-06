package com.dream.chat.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.constant.ProblemTypeEnum;
import com.dream.chat.entity.Problem;
import com.dream.chat.entity.Project;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.ProblemMapper;
import com.dream.chat.service.ProblemService;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-15
 */
@Service
public class ProblemServiceImpl extends SuperServiceImpl<ProblemMapper, Problem> implements ProblemService {

    @Override
    public List<Problem> getProblemByType(Integer type) {
        return this.list(new QueryWrapper<Problem>().eq(Problem.TYPE, type));
    }

    @Override
    public List getProblemsType() {
        ProblemTypeEnum[] values = ProblemTypeEnum.values();
        List<Map> list = new ArrayList<>();
        for(ProblemTypeEnum v : values){
            Map<String,Object> result = new HashMap<>();
            result.put("id",v.code);
            result.put("name",v.alias);
            list.add(result);
        }
        return list;
    }

    @Override
    public Problem updateProblem(Integer id, String note,String answer) {
        Problem problem = new Problem();
        problem.setId(id);
        problem.setNote(note);
        problem.setAnswer(answer);
        this.updateById(problem);
        return problem;
    }

    @Override
    public Problem addProblem(String note, String answer,Integer type) {
        Problem problem = new Problem();
        problem.setAnswer(answer);
        problem.setNote(note);
        problem.setType(type);
        this.save(problem);
        return problem;
    }
}
