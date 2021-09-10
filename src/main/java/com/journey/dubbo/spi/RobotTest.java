package com.journey.dubbo.spi;

import java.util.ServiceLoader;

/**
 * @Author: wuwei
 * @Date: 2019-09-09 15:40
 */
public class RobotTest {

    public static void main(String[] args) {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("JAVA SPI");
        serviceLoader.forEach(Robot::sayHello);
    }
}
