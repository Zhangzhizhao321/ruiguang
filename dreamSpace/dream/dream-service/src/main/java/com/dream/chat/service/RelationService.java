package com.dream.chat.service;

import com.dream.chat.entity.Relation;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-07
 */
public interface RelationService extends SuperService<Relation> {

    Relation addRelation(String relationName);

}
