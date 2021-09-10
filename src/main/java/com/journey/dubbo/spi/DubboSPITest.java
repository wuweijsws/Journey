package com.journey.dubbo.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;

/**
 * @Author: wuwei
 * @Date: 2019-09-09 15:53
 */
public class DubboSPITest {

    public static void main(String[] args) {
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        Robot first = extensionLoader.getExtension("first");
        first.sayHello();
        Robot second = extensionLoader.getExtension("second");
        second.sayHello();
    }
}
