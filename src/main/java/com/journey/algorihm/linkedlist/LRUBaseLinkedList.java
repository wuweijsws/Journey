package com.journey.algorihm.linkedlist;

import java.util.Scanner;

/**
 * 基于链表的LRU（Least Recently Used）算法实现
 * 策略：
 * 1、当有一个新的数据被访问时，开始遍历链表：
 * 2、如果链表中已经存在该数据，则将该数据从原来的节点移动到头节点
 * 3、如果链表中不存在该数据，则将该数据设置到头节点
 * 4、此时判断链表长度是否已经超过设置的最大长度，如果超过了，删除尾节点
 *
 * @author wuwei
 * @date 2021/12/3
 **/
public class LRUBaseLinkedList<T> {

    /**
     * 链表默认容量
     */
    private static final int DEFAULT_CAPACITY = 8;

    /**
     * 头节点
     */
    private Node<T> headNode;

    /**
     * 链表容量
     */
    private int capacity;

    /**
     * 链表当前长度
     */
    private int length;

    public LRUBaseLinkedList() {
        this.capacity = DEFAULT_CAPACITY;
        this.headNode = new Node<>();
        this.length = 0;
    }

    public LRUBaseLinkedList(int capacity) {
        this.capacity = capacity;
        this.headNode = new Node<>();
        this.length = 0;
    }

    public void add(T data) {
        //首先要找到插入点的前一个节点
        Node<T> prevNode = findPrevNode(data);
        if (prevNode != null) {
            //找到了，删除当前节点，并将当前节点插入到头部
            deleteNodeByPrevNode(prevNode);
            insertElementBegin(data);
        } else {
            //没找到，当前节点插入到头部，长度是否超过容量？超过了就删除尾部节点
            if (length >= capacity) {
                //删除尾节点
                deleteEndNode();
            }
            insertElementBegin(data);
        }

    }

    private void deleteEndNode() {
        //空链表直接返回
        if (headNode.next == null) {
            return;
        }
        //找到倒数第二个节点
        Node<T> next = headNode;
        while (next.next.next != null) {
            next = next.next;
        }
        //next为倒数第二个节点，next.next则是要删除的尾部节点
        Node<T> tmp = next.next;
        next.next = null;
        tmp = null;
        length --;

    }

    private void deleteNodeByPrevNode(Node<T> prevNode) {
        Node<T> deleteNode = prevNode.next;
        prevNode.next = deleteNode.next;
        deleteNode = null;
        length --;
    }

    private void insertElementBegin(T data) {
        Node<T> next = headNode.next;
        Node<T> node = new Node<>();
        node.setElement(data);
        node.setNext(next);
        headNode.next = node;
        length ++;
    }

    /**
     * 查找当前节点的前一个节点
     * @param data 要查找的节点
     * @return Node<T>
     */
    private Node<T> findPrevNode(T data) {
        Node<T> prevNode = headNode;
        while (prevNode.next != null) {
            if (data.equals(prevNode.next.element)) {
                return prevNode;
            }
            prevNode = prevNode.next;
        }
        return null;
    }

    private void printAll() {
        Node<T> node = headNode.next;
        while (node != null) {
            System.out.print(node.getElement() + "-->");
            node = node.next;
        }
        System.out.println("nil");
    }

    public static void main(String[] args) {
        LRUBaseLinkedList<Integer> linkedList = new LRUBaseLinkedList<>(10);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            linkedList.add(scanner.nextInt());
            linkedList.printAll();
        }
    }

    public class Node<T> {
        /**
         * 节点当前值
         */
        private T element;

        /**
         * 下一个节点
         */
        private Node<T> next;

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }
}
