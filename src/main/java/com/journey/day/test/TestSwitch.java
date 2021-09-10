package com.journey.day.test;

/**
 * @Author: wuwei
 * @Date: 2019-10-16 14:01
 */
@SuppressWarnings("all")
public class TestSwitch {

    public static void main(String[] args) {
        method("test");
        method("null");
        method(null);
    }

    public static void method(String param) {

        switch (param) {
            case "test":
                System.out.println("it's test");
                break;
            case "null":
                System.out.println("it's null");
                break;
            default:
                System.out.println("default");
        }
    }
}
