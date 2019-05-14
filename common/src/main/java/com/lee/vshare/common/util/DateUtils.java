package com.lee.vshare.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 19-4-18
 * @Time 下午2:19
 */
public class DateUtils {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyy:MM:dd HH:mm:ss:S E");

    public static String dateToStr(Date date){
        return DATE_FORMAT.format(date);
    }

    public static String stampToStr(Long time){
        return DATE_FORMAT.format(new Date(time));
    }

}
