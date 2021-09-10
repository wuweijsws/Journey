package com.journey.dubbo.adaptive;

import com.alibaba.dubbo.common.URL;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wuwei
 * @Date: 2019-09-09 22:19
 */
public class RaceCarMaker implements CarMaker {

    WheelMaker wheelMaker;

    public void setWheelMaker(WheelMaker wheelMaker) {
        this.wheelMaker = wheelMaker;
    }

    @Override
    public Car makerCar(URL url) {
        Wheel wheel = wheelMaker.makeWheel(url);

        return new Car(wheel);
    }
}
