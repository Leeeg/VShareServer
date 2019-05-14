package com.lee.vshare.rest.controller.presenter;

import com.lee.vshare.common.dto.Response;

import javax.servlet.http.HttpServletRequest;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 19-4-15
 * @Time 下午5:07
 */
public interface AccountPresenter {

    Response register(HttpServletRequest request, String userName, String password, String phone, String code);

    Response login(String userName, String password);

    Response getCode(HttpServletRequest request, String phoneNumber);

}
