package com.lee.vshare.service.test.fec.sort;

import java.util.Arrays;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 19-3-12
 * @Time 上午10:08
 */
public class MathTest {

    static int count = 0;
    static int countAll = 0;

    public static void main(String[] args) {
        String[] s = new String[33];
        for (int i = 0; i < 33; i++) {
            s[i] = "" + (i + 1);
        }
//        arrangementSelect(s, 6);
        System.out.println(Arrays.toString(s));
        combinationSelect(s, 6);
        System.out.println("count = " + count);
        System.out.println("countAll = " + countAll);
    }

    /**
     * 排列选择（从列表中选择n个排列）
     *
     * @param dataList 待选列表
     * @param n        选择个数
     */
    public static void arrangementSelect(String[] dataList, int n) {
        System.out.println(String.format("A(%d, %d) = %d", dataList.length, n, arrangement(dataList.length, n)));
        arrangementSelect(dataList, new String[n], 0);
    }

    /**
     * 排列选择
     *
     * @param dataList    待选列表
     * @param resultList  前面（resultIndex-1）个的排列结果
     * @param resultIndex 选择索引，从0开始
     */
    private static void arrangementSelect(String[] dataList, String[] resultList, int resultIndex) {
        int resultLen = resultList.length;
        if (resultIndex >= resultLen) { // 全部选择完时，输出排列结果
            System.out.println(Arrays.asList(resultList));
            return;
        }

        // 递归选择下一个
        for (int i = 0; i < dataList.length; i++) {
            // 判断待选项是否存在于排列结果中
            boolean exists = false;
            for (int j = 0; j < resultIndex; j++) {
                if (dataList[i].equals(resultList[j])) {
                    exists = true;
                    break;
                }
            }
            if (!exists) { // 排列结果不存在该项，才可选择
                resultList[resultIndex] = dataList[i];
                arrangementSelect(dataList, resultList, resultIndex + 1);
            }
        }
    }

    /**
     * 组合选择（从列表中选择n个组合）
     *
     * @param dataList 待选列表
     * @param n        选择个数
     */
    public static void combinationSelect(String[] dataList, int n) {
        System.out.println(String.format("C(%d, %d) = %d", dataList.length, n, combination(dataList.length, n)));
        combinationSelect(dataList, 0, new String[n], 0);
    }

    /**
     * 组合选择
     *
     * @param dataList    待选列表
     * @param dataIndex   待选开始索引
     * @param resultList  前面（resultIndex-1）个的组合结果
     * @param resultIndex 选择索引，从0开始
     */
    private static void combinationSelect(String[] dataList, int dataIndex, String[] resultList, int resultIndex) {
        int resultLen = resultList.length;
        int resultCount = resultIndex + 1;
        int sun;
        if (resultCount > resultLen) { // 全部选择完时，输出组合结果
//            System.out.println(Arrays.asList(resultList));
            countAll++;
            sun = 0;
            for (int i = 0; i < resultList.length; i++) {
                sun += Integer.parseInt(resultList[i]);
            }
            if (sun == 86) {
                System.out.println("sun = " + sun);
                System.out.println(Arrays.asList(resultList));
                count++;
            }
            return;
        }

        // 递归选择下一个
        for (int i = dataIndex; i < dataList.length + resultCount - resultLen; i++) {
            resultList[resultIndex] = dataList[i];
            combinationSelect(dataList, i + 1, resultList, resultIndex + 1);
        }
    }

    /**
     * 计算阶乘数，即n! = n * (n-1) * ... * 2 * 1
     *
     * @param n
     * @return
     */
    public static long factorial(int n) {
        return (n > 1) ? n * factorial(n - 1) : 1;
    }

    /**
     * 计算排列数，即A(n, m) = n!/(n-m)!
     *
     * @param n
     * @param m
     * @return
     */
    public static long arrangement(int n, int m) {
        return (n >= m) ? factorial(n) / factorial(n - m) : 0;
    }

    /**
     * 计算组合数，即C(n, m) = n!/((n-m)! * m!)
     *
     * @param n
     * @param m
     * @return
     */
    public static long combination(int n, int m) {
        return (n >= m) ? factorial(n) / factorial(n - m) / factorial(m) : 0;
    }

}
