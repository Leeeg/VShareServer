package com.lee.vshare.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 19-4-17
 * @Time 下午3:31
 */
public class CommonUtils {

    public static boolean checkPhone(String phoneNumber) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phoneNumber.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phoneNumber);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    public static String getToken(String accId, String password) {
        return JWT.create()
                .withClaim("accid", accId)
                .withClaim("generatetime", System.currentTimeMillis())
                .withClaim("exptime", 1 * 24 * 60 * 60 * 1000) //1天失效
                .sign(Algorithm.HMAC256(password));// 以 password 作为 token 的密钥;
    }

}
