package com.lee.vshare.common.util;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 18-12-20
 * @Time 下午8:19
 */
public class DataUtil {

    public static boolean isNull(Object... objects) {
        for (Object o : objects) {
            if (null == o){
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(String... strings) {
        for (String s : strings) {
            if (null == s || s.isEmpty()){
                return true;
            }
        }
        return false;
    }

}
