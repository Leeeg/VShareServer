package com.lee.vshare.rest.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.lee.vshare.common.entity.VisitLog;
import com.lee.vshare.common.service.VisitService;
import com.lee.vshare.rest.controller.BlogController;
import com.lee.vshare.rest.utils.BrowserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @title:
 * @gmail jefferyleeeg@gmail.com
 * @author:Lee
 * @date: 2018/12/16
 * @describe: 用于做访问记录的拦截器
 */
public class ForeInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    VisitService visitService;

    private VisitLog visitLog = new VisitLog();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 访问者的IP
        String ip = request.getRemoteAddr();
        logger.info(ip);
        // 访问地址
        String url = request.getRequestURL().toString();
        logger.info(url);
        //得到用户的浏览器名
        String browser = BrowserUtil.getOsAndBrowserInfo(request);
        logger.info(browser);

        visitLog.setIp(StringUtils.isEmpty(ip) ? "0.0.0.0" : ip);
        visitLog.setBrowser(StringUtils.isEmpty(browser) ? "获取浏览器名失败" : browser);
        visitLog.setOperateUrl(StringUtils.isEmpty(url) ? "获取URL失败" : url);

//        // 增加访问量
        visitLog.setIp(StringUtils.isEmpty(ip) ? "0.0.0.0" : ip);
//        visitService.addVisitLog(visitLog);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 保存日志信息
        visitLog.setRemark(method.getName());
//        visitService.addVisitLog(visitLog);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
