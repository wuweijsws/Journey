package com.journey.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Java架构设计
 * @date: 2019-09-01 14:31
 */
public class ThreadTest {

    public static void main(String[] args) {
        long now = System.currentTimeMillis();

        ThreadTest threadTest = new ThreadTest();
        //定义一个线程池，此处为演示，实际业务中，建议手动创建线程池
        ExecutorService exec = Executors.newCachedThreadPool();

        exec.execute(() -> {
            try {
                threadTest.method1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        exec.execute(() -> {
            try {
                threadTest.method2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        exec.execute(() -> {
            try {
                threadTest.method3();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        System.out.println("多线程执行总耗时:"
                + (System.currentTimeMillis() - now) + "ms");
    }

    public void method1() throws InterruptedException {
        System.out.println("执行方法1");
        //处理业务逻辑1
        Thread.sleep(60);
    }

    public void method2() throws InterruptedException {
        System.out.println("执行方法2");
        //处理业务逻辑2
        Thread.sleep(50);
    }

    public void method3() throws InterruptedException {
        System.out.println("执行方法3");
        //处理业务逻辑3
        Thread.sleep(40);
    }

}


