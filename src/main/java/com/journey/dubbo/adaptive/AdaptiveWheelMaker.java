package com.journey.dubbo.adaptive;


import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

/**
 * @Author: wuwei
 * @Date: 2019-09-09 18:51
 */
public class AdaptiveWheelMaker implements WheelMaker {

    @Override
    public Wheel makeWheel(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }
        String wheelMakerName = url.getParameter("Wheel.maker");
        if (wheelMakerName == null || wheelMakerName.length() == 0) {
            throw new IllegalArgumentException("wheelMakerName == null");
        }

        WheelMaker wheelMaker = ExtensionLoader.getExtensionLoader(WheelMaker.class).getExtension(wheelMakerName);
        return wheelMaker.makeWheel(url);
    }
}
