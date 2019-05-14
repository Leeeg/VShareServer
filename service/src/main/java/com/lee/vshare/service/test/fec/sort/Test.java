package com.lee.vshare.service.test.fec.sort;

import java.util.*;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 19-3-7
 * @Time 下午2:51
 */
public class Test {

    static List<Integer> list = new ArrayList<>(33);


    public static void main(String[] args) {

        for (int i = 1; i <= 33 ; i++){
            list.add(i);
        }


//        System.out.println(arrangement(6, 33));
//        System.out.println(combination(6, 33));

        //从list中每次取三个元素
//        List<List<String>> result =findsort(list, 6);
//        for (int i = 0; i < result.size(); i++) {
//            for (int j = 0; j < result.get(i).size(); j++) {
//                System.out.print(result.get(i).get(j) + " ");
//            }
//            System.out.println();
//        }

        List<List<Integer>> list1 = getAllCombinerDun(list);
        int sun = 0;
        int count = 0;
        for (int i = 0; i < list1.size(); i++) {
            List<Integer> list2 = list1.get(i);
            for (int j = 0; j < list2.size(); j++) {
                sun += list2.get(j);
                if (sun == 84){
                    System.out.println(list2);
                    System.out.println(count++);
                }
            }
            sun = 0;
        }

    }

    public static long combination(int m, int n) {
        return m <= n ? factorial(n) / (factorial(m) * factorial((n - m))) : 0;
    }

    public static long arrangement(int m, int n) {
        return m <= n ? factorial(n) / factorial(n - m) : 0;
    }

    private static long factorial(int n) {
        long sum = 1;
        while( n > 0 ) {
            sum = sum * n--;
        }
        return sum;
    }
    public static List<List<String>> combiner(List<String> elements, int num,
                                              List<List<String>> result){
        //当num为1时，即返回结果集
        if(num == 1){
            return result;
        }
        //result的长度是变化的，故把原始值赋给变量leng
        int leng = result.size();
        //循环遍历，将 elements每两个元素放到一起，作为result中的一个元素
        for (int i = 0; i < leng; i++) {
            for (int j = 0; j < elements.size(); j++) {
                if(!result.get(i).contains(elements.get(j))){
                    List<String> list1 = new ArrayList<String>();
                    for (int j2 = 0; j2 < result.get(i).size(); j2++) {
                        list1.add(result.get(i).get(j2));
                    }
                    list1.add(elements.get(j));
                    Collections.sort(list1);
                    result.add(list1);
                }
            }
        }
        //将result中的循环遍历前的数据删除
        for (int i = 0; i < leng; i++) {
            result.remove(0);
        }
        //对result进行去重
        Iterator<List<String>> it=result.iterator();
        List<List<String>> listTemp= new ArrayList<List<String>>();
        while(it.hasNext()){
            List<String> a=it.next();
            if (listTemp.contains(a)){
                it.remove();
            }else {
                listTemp.add(a);
            }
        }
        //递归计算，根据num的值来确定递归次数
        combiner(elements, num - 1, result);
        return result;
    }

    //elements为要操作的数据集合，即长度为M的容器，num为每次取的元素个数
    public static List<List<String>> findsort(List<String> elements, int num){
        List<List<String>> result = new ArrayList<List<String>>();
        //将elements中的数据取出来，存到新的list中，为后续计算做准备
        for (int i = 0; i < elements.size(); i++) {
            List<String> list = new ArrayList<String>();
            list.add(elements.get(i));
            result.add(list);
        }
        return combiner(elements, num, result);
    }







    public static  List<List<Integer>> getAllCombinerDun(List<Integer> data){
        List<List<Integer>>  combinerResults = new ArrayList<>();

        combinerSelect(combinerResults, data, new ArrayList<Integer>(), data.size(), 6);

        System.out.println("组合大小：{}" + combinerResults.size());
//        logger.info("组合结果：{}", results.toString());
        return combinerResults;
    }


    /***
     * C(n,k) 从n个中找出k个组合
     * @param data
     * @param workSpace
     * @param n
     * @param k
     * @return
     */
    public static List<List<Integer>>  combinerSelect(List<List<Integer>> combinerResults, List<Integer> data, List<Integer> workSpace, int n, int k) {
        List<Integer> copyData;
        List<Integer> copyWorkSpace;

        if(workSpace.size() == k) {
            List<Integer>  dunTiles = new ArrayList<>();
            for(Integer c : workSpace){
                dunTiles.add(c);
            }

            combinerResults.add(dunTiles);
        }

        for(int i = 0; i < data.size(); i++) {
            copyData = new ArrayList<>(data);
            copyWorkSpace = new ArrayList<>(workSpace);

            copyWorkSpace.add(copyData.get(i));
            for(int j = i; j >=  0; j--)
                copyData.remove(j);
            combinerSelect(combinerResults, copyData, copyWorkSpace, n, k);
        }


        return  combinerResults;
    }

}
