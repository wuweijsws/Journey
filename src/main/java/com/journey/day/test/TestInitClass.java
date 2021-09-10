package com.journey.day.test;

/**
 * @Author: wuwei
 * @Date: 2019-10-20 21:22
 */
public class TestInitClass {

    public static String INIT_CONSTANT = "init";

    public void setInitConstant(String constant) {
        INIT_CONSTANT = constant;
    }

    public static int staticMethod() {
        return 1;
    }

    public static void main(String[] args) {
        //new字节码指令
        TestInitClass testInitClass = new TestInitClass();

        //getstatic字节码指令
        System.out.println(TestInitClass.INIT_CONSTANT);

        //putstatic字节码指令
        testInitClass.setInitConstant("init2");

        //invokestatic字节码指令
        System.out.println(TestInitClass.staticMethod());
    }

}
