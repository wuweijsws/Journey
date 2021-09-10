package com.journey.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * @Author: java架构设计
 * @Date: 2019-10-22 19:47
 */
public class BubbleSort {

    public static void sort(int[] array) {

        int length = array.length;
        for (int i = 0; i < length; i++) {
            boolean flag = true;
            for (int j = 0; j < length - i - 1; j++) {
                if (array[j] > array[j+1]) {
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                    flag = false;
                }
            }
            if (flag) {
                break;
            }

            System.out.println("第" + i + "次排序：" + Arrays.toString(array));
        }
    }

    public static void main(String[] args) {
        int[] array = {89,7,18,23,9,15,32,67,21,12};
        BubbleSort.sort(array);
    }
}
