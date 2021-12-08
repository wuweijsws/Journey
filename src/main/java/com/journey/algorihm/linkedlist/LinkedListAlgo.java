package com.journey.algorihm.linkedlist;

import java.util.Comparator;
import java.util.List;

/**
 * 链表反转
 *
 * @author wuwei
 * @date 2021/12/6
 **/
public class LinkedListAlgo<T> {

    /**
     * 定义一个带头链表
     */
    private Node<T> head = new Node<>();

    /**
     * 链尾插入
     * @param data 数据
     */
    public void add(T data) {
        Node<T> node = new Node<>();
        node.value = data;
        if (head.next == null) {
            head.next = node;
        } else {
            node.next = head.next;
            head.next = node;
        }
    }

    private void printAll() {
        Node<T> node = head.next;
        while (node != null) {
            System.out.print(node.value + "-->");
            node = node.next;
        }
        System.out.println("nil");
    }

    /**
     * 反转链表
     */
    public LinkedListAlgo<T> reverse() {
        //先定一个前置节点，一定要为null
        Node<T> prev = null;
        //带头链表，默认从head.next开始反转
        Node<T> curr = head.next;

        while (curr != null) {
            //先定义一个指针指向curr.next，避免curr.next丢失
            Node<T> next = curr.next;
            //开始反转
            curr.next = prev;
            prev = curr;
            //当前节点指向刚刚暂存的next节点
            curr = next;
        }

        LinkedListAlgo<T> linkedListAlgo = new LinkedListAlgo<>();
        linkedListAlgo.head.next = prev;
        return linkedListAlgo;
    }

    /**
     * 环形检查，定义两个指针，一个快指针，每次往下走2步，一个慢指针，每次往下走1步，
     * 当他俩相遇时候就存在环形链表
     * @return true/false
     */
    public boolean checkCircle() {
        if (head.next == null) {
            return false;
        }
        //慢指针
        Node<T> slow = head;
        //快指针
        Node<T> fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    /**
     * 合并两个有序链表
     * @param node1 链表1
     * @param node2 链表2
     * @return 新链表
     */
    public static Node<Integer> mergeSortedLinkedList(Node<Integer> node1, Node<Integer> node2) {
        //定义一个新节点，用来承载合并后的链表
        Node<Integer> newNode = new Node<>();
        //定义一个哨兵，用来标记当前走到哪个节点了,类似于newNode的tail节点
        Node<Integer> solider = newNode;
        while (node1 != null && node2 != null) {
            //如果node1当前比较的节点值小于node2当前比较的节点，将node1的节点设置为p节点的next节点
            if (node1.value < node2.value) {
                solider.next = node1;
                node1 = node1.next;
            } else {
                //反过来将node2的当前节点设置为p节点的next节点
                solider.next = node2;
                node2 = node2.next;
            }

            solider = solider.next;

        }

        if(node1 == null) {
            solider.next = node2;
        }

        if (node2 == null) {
            solider.next = node1;
        }
        return newNode.next;
    }

    /**
     * 删除倒数第k个节点
     * 快慢指针各一个，快指针先走k个节点，然后慢指针和快指针一起走，
     * 快指针走到尾部，则慢指针所在的节点就是当前节点
     * @param k 倒数第k个
     */
    public Node<T> deleteLastKNode(Node<T> node, int k) {
        int index = 1;
        Node<T> slow = node;
        Node<T> fast = node;
        while (fast != null && index < k) {
            fast = fast.next;
            index ++;
        }
        //什么情况下fast会是null？1、node为null； 2、k超过了node节点数,这是不被允许的
        if (fast == null) {
            return node;
        }
        //关键是找到前置节点
        Node<T> prev = null;
        while (fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next;
        }
        //比如2个节点，删除倒数第二个节点
        if (prev == null) {
            return node.next;
        }
        prev.next = prev.next.next;
        return node;
    }

    /**
     * 求中间节点
     * 一个快指针，一个慢指针，快指针每次走两步，一个慢指针每次走一步，
     * 当快指针走完，慢指针就是中间节点
     * @param node 链表
     * @return Node<T>
     */
    public Node<T> middleNode(Node<T> node) {
        Node<T> fast = node;
        Node<T> slow = node;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public Node<T> swapPairs(Node<T> head) {
        if (head == null || head.next == null) {
            return head;
        }
        //一定要掐住头！！！
        Node prev = new Node();
        prev.next = head;
        Node solider = prev;
        while (solider.next != null && solider.next.next != null) {
            Node node1 = solider.next;
            Node node2 = solider.next.next;
            //node1和node2交换节点
            solider.next = node2;
            node1.next = node2.next;
            node2.next = node1;
            solider = node1;
        }
        return prev.next;
    }

    public static void main(String[] args) {
        LinkedListAlgo<Integer> node = new LinkedListAlgo<>();
        node.add(12);
        node.add(11);
        node.add(10);
        node.add(9);
        node.add(8);
        node.add(7);
        node.add(6);
        node.add(5);
        node.add(4);
        node.add(3);
        node.add(2);
        node.add(1);
        Node<Integer> swapNode = node.swapPairs(node.head.next);

        node.printAll();
        System.out.println("链表反转");
        LinkedListAlgo<Integer> linkedListAlgo = node.reverse();
        linkedListAlgo.printAll();

        System.out.println("链表合并");
        LinkedListAlgo<Integer> node1 = new LinkedListAlgo<>();
        node1.add(8);
        node1.add(5);
        node1.add(2);
        node1.add(1);
        node1.printAll();
        LinkedListAlgo<Integer> node2 = new LinkedListAlgo<>();
        node2.add(7);
        node2.add(6);
        node2.add(4);
        node2.add(3);
        node2.printAll();
        Node<Integer> mergeNode = mergeSortedLinkedList(node1.head.next, node2.head.next);

        LinkedListAlgo<Integer> linkedListAlgo1 = new LinkedListAlgo<>();

        linkedListAlgo1.middleNode(mergeNode);

        mergeNode = linkedListAlgo1.deleteLastKNode(mergeNode, 3);
        while (mergeNode != null) {
            System.out.print(mergeNode.value + "-->");
            mergeNode = mergeNode.next;
        }
    }

    /**
     * 定义一个链表
     * @param <T>
     */
    public static class Node<T> {
        private T value;

        private Node<T> next;
    }
}
