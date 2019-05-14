package com.lee.vshare.common.util.response;

import com.lee.vshare.common.dto.Response;
import com.lee.vshare.common.util.ResponseEnum;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 18-12-20
 * @Time 上午11:00
 */
@SuppressWarnings("unchecked")
public class ResponseUtil {

    public static Response success(Object object) {
        Response response = new Response();
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMsg(ResponseEnum.SUCCESS.getMsg());
        response.setData(object);
        return response;
    }

    public static Response error(ResponseEnum anEnum) {
        Response response = new Response();
        response.setCode(anEnum.getCode());
        response.setMsg(anEnum.getMsg());
        return response;
    }

}
