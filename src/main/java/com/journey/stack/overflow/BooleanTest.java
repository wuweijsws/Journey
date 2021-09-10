package com.journey.stack.overflow;

import java.util.Random;

/**
 * @Author: wuwei
 * @Date: 2019-11-02 16:27
 */
public class BooleanTest {

    public static void main(String[] args) {
        System.out.println(randomString(-229985452) + " " + randomString(-147909649));
    }

    /**
     * 只要有两个true即可返回true
     */
    boolean atLeastTwo(boolean a, boolean b, boolean c) {
        //常规写法
        return a && b || a && c || b && c;

//        //优雅写法1
//        return a ? (b || c) : (b && c);
//
//        //优雅写法2
//        return (a==b) ? a : c;
//
//        //优雅写法3
//        return a ^ b ? c : a;

    }

    public static String randomString(int i) {
        Random ran = new Random(i);
        StringBuilder sb = new StringBuilder();
        while (true)
        {
            int k = ran.nextInt(27);
            if (k == 0) {
                break;
            }

            sb.append((char)('`' + k));
        }
        return sb.toString();
    }
}
