package com.journey.dubbo.adaptive;

/**
 * @Author: wuwei
 * @Date: 2019-09-09 22:18
 */
public class Car {

    Wheel wheel;

    public Wheel getWheel() {
        return wheel;
    }

    public void setWheel(Wheel wheel) {
        this.wheel = wheel;
    }

    public Car(Wheel wheel) {
        this.wheel = wheel;
    }
}
