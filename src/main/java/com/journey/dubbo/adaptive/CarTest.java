package com.journey.dubbo.adaptive;

import com.alibaba.dubbo.common.URL;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wuwei
 * @Date: 2019-09-09 23:19
 */
public class CarTest {

    public static void main(String[] args) {
        //String protocol, String host, int port, Map<String, String> parameters
        //"dubbo://192.168.0.101:20880/XxxService?wheel.maker=MichelinWheelMaker"
        Map<String, String> params = new HashMap<>(1);
        params.put("Wheel.maker", "MichelinWheelMaker");
        URL url = new URL("dubbo", "192.168.0.101", 20880, "XxxService", params);
        RaceCarMaker raceCarMaker = new RaceCarMaker();
        AdaptiveWheelMaker adaptiveWheelMaker = new AdaptiveWheelMaker();
        raceCarMaker.wheelMaker = adaptiveWheelMaker;
        raceCarMaker.makerCar(url);
    }
}
