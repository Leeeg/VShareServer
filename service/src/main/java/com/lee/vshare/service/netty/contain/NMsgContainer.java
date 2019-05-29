package com.lee.vshare.service.netty.contain;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 19-5-24
 * @Time 下午4:56
 */
public abstract class NMsgContainer {

    public static final int MSG_USER_UP_LINE = 1001;//有人上线
    public static final int MSG_USER_DOWN_LINE = 1002;//有人下线

    public static final int MSG_USER_TIME_OUT = 1003;//本机心跳超时
    public static final int MSG_USER_HEART_BEAT = 1004;//本机心跳回复


    public static final int MSG_USER_BUSINESS = 2001;//业务消息
    public static final int MSG_USER_MESSAGE_SUCCESS = 2002;//消息发送成功反馈

    public static final int MSG_TALK_APPLY = 3001;//申请话权
    public static final int MSG_TALK_APPLY_SUCCESS = 3002;//申请话权成功
    public static final int MSG_TALK_APPLY_FAILED = 3003;//申请话权失败
    public static final int MSG_TALK_RELEASE = 3004;//释放话权
}
