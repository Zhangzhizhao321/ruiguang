package com.dream.chat.service.impl;

import com.dream.chat.entity.Institution;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.InstitutionMapper;
import com.dream.chat.service.InstitutionService;
import com.dream.chat.vo.req.InstitutionReqVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@Service
public class InstitutionServiceImpl extends SuperServiceImpl<InstitutionMapper, Institution> implements InstitutionService {

    @Override
    public Institution addOrUpdateInstitution(InstitutionReqVo vo) {
        Institution institution = new Institution();
        BeanUtils.copyProperties(vo,institution);
        institution.setUpdateTime(new Date());
        if(StringUtils.isBlank(vo.getId())){
            institution.setCreateTime(new Date());
            this.save(institution);
        }else{
            this.updateById(institution);
        }
        return institution;
    }

    @Override
    public Institution unbindInstitutionCard(String id) {
        Institution institution = new Institution();
        institution.setIdCard("");
        institution.setBankNum("");
        institution.setBankNo("");
        institution.setBankName("");
        institution.setId(id);
        this.updateById(institution);
        return institution;
    }
}
