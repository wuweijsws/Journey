package com.journey.dubbo.spi;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * @Author: wuwei
 * @Date: 2019-09-09 15:37
 */
@SPI
public interface Robot {

    void sayHello();
}
