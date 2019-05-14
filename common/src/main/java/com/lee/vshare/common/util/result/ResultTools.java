package com.lee.vshare.common.util.result;

import java.util.Map;

/**
 * @title:
 * @gmail jefferyleeeg@gmail.com
 * @author:Lee
 * @date: 2018/12/16
 */
public class ResultTools {
    /****
     * 错误码记录：
     * 0--------成功
     * 1001-----请求传参错误
     * 1002-----没有对应内容
     * 1003-----此用户已存在
     * 1004-----上传文件为空
     * 404------异常抛出错误
     *
     */

    /**
     * @param Errcode--返回码
     * @param Errmsg---404服务器内部异常时提示消息(返回码不是404时传空即可)
     * @param map------数据源
     * @return
     */
    public static ResultModel result(int Errcode, String Errmsg, Map<String, Object> map) {
        ResultModel model = new ResultModel();
        model.setErrcode(Errcode);
        switch (Errcode) {
            case 0:
                model.setErrmsg("成功");
                if (map != null) {
                    model.setData(map);
                }
                break;
            case 1001:
                model.setErrmsg("请求传参错误 ");
                break;
            case 1002:
                model.setErrmsg("没有对应内容 ");
                break;
            case 1003:
                model.setErrmsg("此用户已存在");
                break;
            case 1004:
                model.setErrmsg("上传文件为空");
                break;
            case 404:
                model.setErrmsg(Errmsg);
                break;
            default:
                model.setErrmsg("未知错误");
                break;
        }
        return model;
    }
}
