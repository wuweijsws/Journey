package com.journey.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * new ArrayList()和Collections.emptyList()的区别
 * @Author: java架构设计
 * @Date: 2019-08-22 22:54
 */
public class TestList {

    public static void main(String[] args) {
        TestList testList = new TestList();
        List<String> list = Arrays.asList("abc", "def");
        //调用方法获取的
        List<String> list1 = testList.listByIds(Arrays.asList(1L, 2L, 3L));
        //如果list1是由Collections.emptyList();返回的空集合，则会抛出异常，但是编译期没问题
        list1.addAll(list);
    }

    public List<String> listByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return Collections.emptyList();
        }
        //TODO
        return null;
    }
}
