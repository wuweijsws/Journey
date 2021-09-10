package com.journey.stack.overflow;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

/**
 * @Author: wuwei
 * @Date: 2019-11-02 16:07
 */
public class ArrayMerge {

    public static void main(String[] args) {

        String[] array1 = {"a", "b", "c", "d"};
        String[] array2 = {"e", "f", "g", "h"};

        //commons-lang3-3.8.1.jar
        String[] mergeArray = ArrayUtils.addAll(array1, array2);
        System.out.println(Arrays.toString(mergeArray));

        //不依赖第三方jar包
        String[] newArray = new String[array1.length + array2.length];
        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);
        System.out.println(Arrays.toString(newArray));

    }
}
