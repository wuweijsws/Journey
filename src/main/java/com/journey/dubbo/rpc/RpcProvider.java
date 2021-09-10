package com.journey.dubbo.rpc;

import java.io.IOException;

/**
 * @Author: wuwei
 * @Date: 2019-09-22 23:38
 */
public class RpcProvider {

    public static void main(String[] args) throws IOException {
        HelloService helloService = new HelloServiceImpl();
        RpcFramework.export(helloService, 8081);
    }
}

