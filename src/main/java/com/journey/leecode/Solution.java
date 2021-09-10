package com.journey.leecode;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: wuwei
 * @Date: 2019-11-06 23:20
 */
public class Solution {

    private static int[] numberOfLines(int[] widths, String str) {
        //获取字符串S的长度
        int sLen = str.length();
        int line = 1;
        int remain = 0;
        for(int i=0; i < sLen; i++) {
            char c = str.charAt(i);
            int widthsIndex = c-97;
            int chatLen = widths[widthsIndex];
            remain = remain + chatLen;
            //另起一行
            if (remain > 100) {
                line++;
                remain = chatLen;
            }
        }
        return new int[]{line, remain};
    }

    /**
     * 开关切换
     * @param n n个灯泡n轮
     * @return 还有多少个亮着
     */
    private static int bulbSwitch(int n) {
        int result = 0;
        Boolean[] bulbList = new Boolean[n];
        for (int i = 0; i < n; i++) {
            bulbList[i] = false;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (j % i == 0) {

                    bulbList[j-1] = !bulbList[j-1];

                    if (bulbList[j - 1]) {
                        result++;
                    } else {
                        result--;
                    }
                }
            }
        }

        return result;
    }

    /**
     * 输出n个斐波那契数列
     * @param n 指定数量
     */
    private static void fibonacci(int n) {
        if(n <= 0) {
            System.out.println("输入的n值错误！");
            return;
        }
        //定义一个数组
        int[] array = new int[n];
        System.out.print("斐波那契数列为：");
        for(int i = 1 ; i <= n ; i++) {
            if(i < 3) {
                array[i-1] = 1;
            } else {
                array[i-1] = array[i-2] + array[i-3];
            }

        }
        System.out.print(Arrays.toString(array));
    }

    public static void main(String[] args) {
        fibonacci(10);
//        int[] widths = {10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10};
//        String str = "abcdefghijklmnopqrstuvwxyz";
//        System.out.println(Arrays.toString(numberOfLines(widths, str)));

//        System.out.println(bulbSwitch(3));
    }


}
