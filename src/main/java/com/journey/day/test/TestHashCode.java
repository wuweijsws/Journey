package com.journey.day.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wuwei
 * @Date: 2019-10-15 12:01
 */
@SuppressWarnings("all")
public class TestHashCode {

    static class TestObj {
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public TestObj(Integer id) {
            this.id = id;
        }
    }

    public static void main(String[] args) {
        TestObj test1 = new TestObj(1);
        TestObj test2 = new TestObj(1);

        Map<TestObj, String> map = new HashMap<>(2);
        map.put(test1, "id is 1");
        map.put(test2, "id is 2");
        System.out.println(map.get(test2));
    }
}
