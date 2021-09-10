package com.journey.serialization;

import com.alibaba.dubbo.common.serialize.fastjson.FastJsonObjectInput;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: wuwei
 * @Date: 2019-09-25 00:01
 */
public class TestHashSet {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Set root = new HashSet();
        Set s1 = root;
        Set s2 = new HashSet();

        for (int i = 0; i < 100; i++) {
            Set t1 = new HashSet();
            Set t2 = new HashSet();
            //使得t2 != t1
            t1.add("foo");
            s1.add(t1);
            s1.add(t2);
            s2.add(t1);
            s2.add(t2);
            s1 = t1;
            s2 = t2;
        }
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("/Users/edz/ss.txt"));
        outputStream.writeObject(s1);
        System.out.println("序列化成功");
        outputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("/Users/edz/ss.txt"));
        Set object = (HashSet) objectInputStream.readObject();
        System.out.println("反序列化成功");
        System.out.println(object.toString());
    }
}
