package com.journey.algorihm.linkedlist;

import java.util.Scanner;

/**
 * LRU基于数组实现方式
 * 1、每次新增一个数据时候，如果数组中不存在该数据，需要数组整体往后移动一位，然后将下标为0的位置插入当前数据
 * 2、如果数组中存在该数据，找到该数据，前面的往后移动一位，腾出下标为0的位置给该节点
 * 3、如果数组长度超过容量，则删除数组中最后一条数据
 *
 * @author wuwei
 * @date 2021/12/3
 **/
public class LRUBaseArray<T> {

    public static final int DEFAULT_CAPACITY = 10;

    private T[] arrays;

    private int capacity;

    private int length;

    public LRUBaseArray() {
        this(DEFAULT_CAPACITY);
    }

    public LRUBaseArray(int capacity) {
        this.capacity = capacity;
        arrays = (T[])new Object[capacity];
        length = 0;
    }

    public void add(T data) {
        if (data == null) {
            throw new NullPointerException("invalid value,not allow null object");
        }
        //查找该数组中有没有该数据
        for (int i = 0; i < length; i++) {
            //数组中有该数据
            if (arrays[i].equals(data)) {
                T temp = arrays[i];
                for (int j = i - 1; j >= 0; j--) {
                    arrays[j+1] = arrays[j];
                }
                arrays[0] = temp;
                return;
            }
        }

        //没找到
        if (length >= capacity) {
            T end = arrays[length - 1];
            for (int i = length - 2; i >= 0; i--) {
                arrays[i + 1] = arrays[i];
            }
            arrays[0] = data;
            end = null;
        } else {
            for (int i = length - 1; i >= 0; i--) {
                arrays[i + 1] = arrays[i];
            }
            arrays[0] = data;
            length ++;
        }
    }

    private void printAll() {
        for (T array : arrays) {
            if (array == null) {
                break;
            }
            System.out.print(array + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LRUBaseArray<Integer> lruBaseArray = new LRUBaseArray<>(10);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            lruBaseArray.add(scanner.nextInt());
            lruBaseArray.printAll();
        }
    }
}
