package com.dream.chat.service.impl;

import com.dream.chat.entity.Package;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.PackageMapper;
import com.dream.chat.service.PackageService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-15
 */
@Service
public class PackageServiceImpl extends SuperServiceImpl<PackageMapper, Package> implements PackageService {

    @Override
    public List<Package> selectPackage() {
        List<Package> packageList = this.list(null);
        return packageList;
    }

    @Override
    public Package addPackage(BigDecimal amount) {
        Package pk = new Package();
        pk.setAmount(amount);
        this.save(pk);
        return pk;
    }

    @Override
    public Package updatePackage(Integer id, BigDecimal amount) {
        Package pk = new Package();
        pk.setId(id);
        pk.setAmount(amount);
        this.updateById(pk);
        return pk;
    }
}
