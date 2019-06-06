package com.dream.chat.service;

import com.dream.chat.entity.Banner;
import com.dream.chat.vo.req.BsbannerReqVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
public interface BannerService extends SuperService<Banner> {

    Banner addBanner(BsbannerReqVo vo, HttpServletRequest request);

    List<Banner> selectBanner();

    Banner updateBanner(String id,Integer status,Integer sort);

    void delBanner(String id);

}
