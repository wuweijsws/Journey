package com.journey.dubbo.rpc;

import java.io.IOException;

/**
 * RPC接口类
 * @Author: java架构设计
 * @Date: 2019-10-21 23:02
 */
public interface Rpc {

    /**
     * 暴露服务
     * @param service 服务
     * @param port 端口
     */
    void export(Object service, int port) throws IOException;

    /**
     * 引用服务
     * @param interfaceClass 接口类
     * @param host 地址
     * @param port 端口
     * @param <T> 接口泛型
     * @return 远程服务
     */
    <T> T refer(Class<T> interfaceClass, String host, int port);
}
