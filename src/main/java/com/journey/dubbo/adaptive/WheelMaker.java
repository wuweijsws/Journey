package com.journey.dubbo.adaptive;


import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.SPI;

/**
 * @Author: wuwei
 * @Date: 2019-09-09 18:49
 */
@SPI
public interface WheelMaker {

    /**
     * 从url制造轮子
     * @param url 链接
     * @return Wheel
     */
    Wheel makeWheel(URL url);
}
