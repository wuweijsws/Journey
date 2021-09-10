package com.journey.day.test;

/**
 * @Author: wuwei
 * @Date: 2019-10-17 14:18
 */
@SuppressWarnings("all")
public class TestTryCatch {

    public static void main(String[] args) {
        System.out.println(checkReturn(0));
    }

    public static int checkReturn(int x) {
        try {
            return ++x;
        } catch (Exception e) {
            return 0;
        } finally {
            return ++x;
        }
    }
}
