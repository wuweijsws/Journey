package com.journey.day.test;

/**
 * @Author: wuwei
 * @Date: 2019-10-14 14:10
 */
public class TestOddNum {

    /**
     * 判断是否是奇数
     * @param i 数字
     * @return true/false
     */
    private static boolean isOddNum(int i) {

        //错误做法
//        return i%2 == 1;

        //正确方法1
//        return i%2 != 0;

        //正确方法2，推荐，位操作，性能最优
        return (i&1) == 1;
    }

    public static void main(String[] args) {
        System.out.println(isOddNum(9));
        System.out.println(isOddNum(9));
        System.out.println(isOddNum(-9));
        System.out.println(isOddNum(-10));
    }

    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n + 1;
    }
}
