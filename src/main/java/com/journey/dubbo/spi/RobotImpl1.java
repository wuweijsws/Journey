package com.journey.dubbo.spi;

/**
 * @Author: wuwei
 * @Date: 2019-09-09 15:37
 */
public class RobotImpl1 implements Robot {
    @Override
    public void sayHello() {
        System.out.println("hello, I am first one!");
    }
}
