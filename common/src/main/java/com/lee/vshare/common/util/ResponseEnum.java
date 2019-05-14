package com.lee.vshare.common.util;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 18-12-20
 * @Time 上午11:01
 */
public enum ResponseEnum {

    UNKNOWN_ERROR(-1,"未知错误"),
    SUCCESS(200,"成功"),
    REQUEST_INVALID(400,"无效请求"),
    INSERT_FAILED(501,"添加失败"),
    PARAMETER_INVALID(502,"无效参数"),
    ACCOUNT_EXIST(503,"帐号已存在"),
    USER_NOT_EXIST(504,"用户不存在"),
    CODE_INVALID(505,"已失效"),
    DATA_IS_NULL(506,"目标不存在"),
    SEND_FAILED(507,"获取失败");

    private Integer code;
    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
