package com.journey.day.test;

import java.util.*;

/**
 * 测试Collections.emptyList();
 * @Author: wuwei
 * @Date: 2019-10-13 12:49
 */
public class TestEmptyList {

    public static void main(String[] args) {

        List<TestObj> list1 = new ArrayList<>(5);
        list1.add(new TestObj("A", "abc"));
        list1.add(new TestObj("B", "def"));
        list1.add(new TestObj("C", "ghi"));
        list1.add(new TestObj("D", "jkl"));
        list1.add(new TestObj("E", "mno"));

        Map<String, String> map = new HashMap<>(list1.size());

        list1.forEach(testObj -> map.put(testObj.getKey(), testObj.getValue()));

        System.out.println(map.toString());
    }

    static class TestObj {
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

}
