package com.journey.dubbo.adaptive;

import com.alibaba.dubbo.common.URL;

/**
 * @Author: wuwei
 * @Date: 2019-09-09 22:18
 */
public interface CarMaker {

    Car makerCar(URL url);
}
