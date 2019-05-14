package com.lee.vshare.rest.interceptor;

import com.lee.vshare.common.dto.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @title:
 * @gmail jefferyleeeg@gmail.com
 * @author:Lee
 * @date: 2018/12/15
 * @describe: 用于验证admin接口的拦截器
 */
public class BackInterceptor implements HandlerInterceptor {
    private static String username = "admin";
    private static String password = "admin";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            flag = false;
        } else {
            // 对用户账号进行验证,是否正确
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }
}
