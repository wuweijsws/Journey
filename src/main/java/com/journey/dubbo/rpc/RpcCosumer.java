package com.journey.dubbo.rpc;

/**
 * @Author: wuwei
 * @Date: 2019-09-22 23:39
 */
public class RpcCosumer {

    public static void main(String[] args) throws InterruptedException {
        HelloService service = RpcFramework.refer(HelloService.class,
                "127.0.0.1", 8081);
        for (int i = 0; i < 10; i++) {
            String hello = service.hello("world" + i);
            System.out.println(hello);
            Thread.sleep(1000);
        }
    }
}

