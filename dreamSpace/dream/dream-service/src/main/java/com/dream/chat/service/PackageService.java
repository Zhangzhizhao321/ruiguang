package com.dream.chat.service;
import com.dream.chat.entity.Package;
import java.math.BigDecimal;
import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-15
 */
public interface PackageService extends SuperService<Package> {

    List<Package> selectPackage();

    Package addPackage(BigDecimal amount);

    Package updatePackage(Integer id,BigDecimal amount);

}
