package com.journey.dubbo.rpc;

/**
 * @Author: wuwei
 * @Date: 2019-09-22 23:38
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String hello) {
        return "Hello:" + hello;
    }
}

