package com.lee.vshare.service.test;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

public class Test2 {

    public static void main(String[] args) {
//        long startTime = System.currentTimeMillis();
//        String[] strArr = new String[500];
//        for(int i=0;i<500;i++){
//            String result = "This is";
//            strArr[i]=String.valueOf(result.hashCode());
//        }
//        long endTime = System.currentTimeMillis();
//        System.out.println("字符串连接"
//                + " - 使用 + 操作符 : "
//                + (endTime - startTime)+ " ms");
//        System.out.println(strArr[0]+"\n"+strArr[1]+"\n"+strArr[2]);
//
//        long startTime1 = System.currentTimeMillis();
//        for(int i=0;i<500;i++){
//            StringBuffer result = new StringBuffer();
//            result.append("This is");
//            strArr[i]=String.valueOf(result.hashCode());
//        }
//        long endTime1 = System.currentTimeMillis();
//        System.out.println("字符串连接"
//                + " - 使用 StringBuffer : "
//                + (endTime1 - startTime1)+ " ms");
//        System.out.println(strArr[0]+"\n"+strArr[1]+"\n"+strArr[2]);

        int[] arr = {0,1,2,3,4,5,6,7,8};
//        //计算交换次数
//        int n=arr.length/2;
//        //将数组中的两个元素进行交换
//        for (int i = 0; i < n; i++) {
//            int temp=arr[i];
//            arr[i]=arr[arr.length-1-i];
//            arr[arr.length-1-i]=temp;
//        }
//        for (int i : arr) {
//            System.out.println(i);
//        }

//        check(arr, 0);

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("---Test---");
            }
        };
        runnable1.run();

    }

    public static void check(int[] array, int index){
        if (index < array.length){
            System.out.println(array[index]);
            check(array, ++index);
        }
    }

    public <T extends Comparable<T>> void test(T pram1){
        Integer i = 1;
    }

}
