package com.dream.chat.service;

import com.dream.chat.entity.Problem;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-15
 */
public interface ProblemService extends SuperService<Problem> {

    List<Problem> getProblemByType(Integer type);

    List getProblemsType();

    Problem updateProblem(Integer id,String note,String answer);

    Problem addProblem(String note,String answer,Integer type);

}
