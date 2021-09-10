package com.journey.dubbo.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * RPC Framework
 * @Author: wuwei
 * @Date: 2019-09-22 23:05
 */
public class RpcFramework {

    /**
     * 暴露服务
     * @param service 服务
     * @param port 端口
     */
    public static void export(final Object service, int port) throws IOException {

        //如果服务是空，直接抛出异常
        if (service == null) {
            throw new IllegalArgumentException("service is null");
        }

        //端口号必须在0~65535范围内
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("port is invalid, port=" + port);
        }

        System.out.println("Export service" + service.getClass().getName() + " on port:" + port);
        ServerSocket serverSocket = new ServerSocket(port);

        for (;;) {
            try {

                final Socket socket = serverSocket.accept();
                new Thread(
                        () -> {
                            try {
                                try {
                                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                                    try {

                                        String methodName = inputStream.readUTF();
                                        Class<?>[] parameterTypes = (Class<?>[]) inputStream.readObject();
                                        Object[] arguments = (Object[]) inputStream.readObject();

                                        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                                        try {
                                            Method method = service.getClass().getMethod(methodName, parameterTypes);
                                            Object result = method.invoke(service, arguments);
                                            outputStream.writeObject(result);

                                        } catch (Throwable t) {
                                            outputStream.writeObject(t);
                                        } finally {
                                            outputStream.close();
                                        }

                                    } finally {
                                        inputStream.close();
                                    }
                                } finally {
                                    socket.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                ).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 引用服务
     * @param interfaceClass 接口类
     * @param host 地址
     * @param port 端口
     * @param <T> 接口泛型
     * @return 远程服务
     */
    public static <T> T refer(final Class<T> interfaceClass, final String host, final int port) {
        if (interfaceClass == null) {
            throw new IllegalArgumentException("Interface class == null");
        }

        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException("the " + interfaceClass.getName() + "must be interface class");
        }

        if (host == null || host.length() == 0) {
            throw new IllegalArgumentException("Host == null");
        }

        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port:" +port);
        }

        System.out.println("GET remote service" + interfaceClass.getName() + "from server " + host + ":" + port);

        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                (proxy, method, args) -> {
                    Socket socket = new Socket(host, port);
                    try {
                        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                        try {

                            outputStream.writeUTF(method.getName());
                            outputStream.writeObject(method.getParameterTypes());
                            outputStream.writeObject(args);

                            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                            try {
                                Object result = inputStream.readObject();
                                if (result instanceof Throwable) {
                                    throw  (Throwable) result;
                                }
                                return result;
                            }finally {
                                inputStream.close();
                            }

                        } finally {
                            outputStream.close();
                        }
                    } finally {
                        socket.close();
                    }
                }
        );

    }
}
