package com.lee.vshare.service.test;

import java.util.Arrays;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 19-5-9
 * @Time 下午4:03
 */
public class Test {
    public static void main(String[] args) {
        String str = "[ty*A1000051973DA3*000E*PHB3,,,,,,,,,,][ty*A1000051973DA3*0032*VCONTACT,84e1c91810a843609bcf9d773c0ca548,54E554E5]";
        String name = "";
        String userId = "";
        if (str.contains("VCONTACT")) {
            System.out.println("videoMsg : " + str);
            String msg = str;
            System.out.println("videoMsg : msg : " + msg);
            if (msg.startsWith("[")) {
                msg = msg.substring(1, msg.length() - 1);
                System.out.println("videoMsg : msg : " + msg);
            }
            if (msg.endsWith("]")) {
                msg = msg.substring(msg.length() - 1);
                System.out.println("videoMsg : msg : " + msg);
            }
            String[] msgArr = msg.split("]\\[");
            System.out.println("videoMsg : msgArrLength : " + msgArr.length);
            System.out.println("videoMsg : msgArr : " + Arrays.toString(msgArr));
            for (int i = 0; i < msgArr.length; i++) {
                String videoMsg = msgArr[i];
                if (videoMsg.contains("VCONTACT")) {
                    String[] videoArr = videoMsg.split(",");
                    System.out.println("videoMsg : videoMsgLength : " + msgArr.length);
                    if (videoArr.length == 3) {
                        name = videoArr[2];
                        userId = videoArr[1];
                    }
                }
            }
            System.out.println("videoMsg : name = " + name);
            System.out.println("videoMsg : userId = " + userId);
            System.out.println("videoMsg : name = " + unicode2String(name));
            System.out.println("videoMsg : userId = " + unicode2String(userId));
        }
    }

    public static String unicode2String(String string) {
            int end = 0;
            String noSpace = string.trim();
            int count = noSpace.length() / 4;
            StringBuffer buffer = new StringBuffer();

            for (int j = 0; j < count; j++) {
                end += 4;
                int uCode = Integer.parseInt(noSpace.substring(j * 4, end), 16);
                buffer.append((char) uCode);

            }
            return buffer.toString();
    }
}
