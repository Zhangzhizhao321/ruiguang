package com.dream.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.common.utils.CommonUtil;
import com.dream.chat.constant.ImgUrlContanst;
import com.dream.chat.entity.Banner;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.BannerMapper;
import com.dream.chat.service.BannerService;
import com.dream.chat.vo.req.BsbannerReqVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@Service
public class BannerServiceImpl extends SuperServiceImpl<BannerMapper, Banner> implements BannerService {

    @Resource
    private BannerMapper bannerMapper;

    @Override
    @Transactional
    public Banner addBanner(BsbannerReqVo vo, HttpServletRequest request) {
        String url = CommonUtil.upload(ImgUrlContanst.PROJECT_IMG, vo.getFile(),request);
        Banner banner = new Banner();
        banner.setBaseUrl(ImgUrlContanst.BASE_URL);
        banner.setCreateTime(new Date());
        banner.setImgUrl(url);
        banner.setSort(vo.getSort());
        this.save(banner);
        return banner;
    }

    @Override
    public List<Banner> selectBanner() {
        List<Banner> bannerList = this.list(new QueryWrapper<Banner>().orderByAsc(Banner.SORT));
        return bannerList;
    }

    @Override
    public Banner updateBanner(String id, Integer status, Integer sort) {
        Banner banner = new Banner();
        banner.setId(id);
        banner.setSort(sort);
        banner.setStatus(status);
        this.updateById(banner);
        return banner;
    }

    @Override
    public void delBanner(String id) {
        String[] ids = id.split(",");
        for(String i : ids){
            this.removeById(i);
        }
    }
}
