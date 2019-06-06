package com.dream.chat.service.impl;

import com.dream.chat.entity.Relation;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.RelationMapper;
import com.dream.chat.service.RelationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-07
 */
@Service
public class RelationServiceImpl extends SuperServiceImpl<RelationMapper, Relation> implements RelationService {

    @Override
    public Relation addRelation(String relationName) {
        Relation relation = new Relation();
        relation.setRelationName(relationName);
        this.save(relation);
        return relation;
    }
}
