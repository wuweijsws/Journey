package com.journey.dubbo.adaptive;

import com.alibaba.dubbo.common.URL;

/**
 * @Author: wuwei
 * @Date: 2019-09-09 23:08
 */
public class MichelinWheelMaker implements WheelMaker {
    @Override
    public Wheel makeWheel(URL url) {
        System.out.println("url:" + url.toString());
        System.out.println("make Michelin Wheel");
        return new Wheel();
    }
}
