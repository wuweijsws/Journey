package com.journey.file;

import java.io.File;

/**
 * @Author: wuwei
 * @Date: 2019-09-18 19:02
 */
public class FilePathTest {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));

        System.out.println(FilePathTest.class.getClassLoader().getResource(""));

        System.out.println(ClassLoader.getSystemResource(""));

        System.out.println(FilePathTest.class.getResource(""));
        // Class文件所在路径
        System.out.println(FilePathTest.class.getResource("/"));

        System.out.println(new File("/").getAbsolutePath());

        System.out.println(System.getProperty("user.dir"));
    }
}
