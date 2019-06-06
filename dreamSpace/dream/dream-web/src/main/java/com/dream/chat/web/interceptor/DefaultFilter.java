package com.dream.chat.web.interceptor;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author lw
 * @since 2018-10-30
 */
@Slf4j
public class DefaultFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String pathInfo= request.getServletPath();
        String logSeq=genLogSeq(request);
        request.setAttribute("logSeq",logSeq);
        log.info("logSeq:{},path:{}",logSeq,pathInfo);
        chain.doFilter(request,servletResponse);
    }

    /**
     * 生成日志流水号
     * @param request
     * @return
     */
    private String genLogSeq( HttpServletRequest request ){
        return request.getSession().getId();
    }

    @Override
    public void destroy() {

    }

}
