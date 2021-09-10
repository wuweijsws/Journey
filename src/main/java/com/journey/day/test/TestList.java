package com.journey.day.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wuwei
 * @Date: 2019-10-15 12:13
 */
public class TestList {

    @SuppressWarnings("all")

    public static void main(String[] args) {

        List<TestObj> list = new ArrayList<>(10);
        //循环内创建对象
        for (int i = 0; i < 10; i++) {
            TestObj testObj = new TestObj("key" + i, "value" + i);
            list.add(testObj);
        }
        //复用对象引用
        TestObj testObj;
        for (int i = 0; i < 10; i++) {
            testObj = new TestObj("key" + i, "value" + i);
            list.add(testObj);
        }

        //提前算好集合的长度，不要在for里面每次都算一遍集合的长度
        int size = list.size();
        for (int i = 0; i < size; i++) {
            testObj = list.get(i);
            System.out.println(testObj.getKey() + "=" + testObj.getValue());
        }
    }

}

@SuppressWarnings("all")

class TestObj {
    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TestObj(String key, String value) {
        this.key = key;
        this.value = value;
    }
}

