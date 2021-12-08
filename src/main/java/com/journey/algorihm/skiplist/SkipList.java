package com.journey.algorihm.skiplist;

/**
 * 跳表实现
 *
 * @author wuwei
 * @date 2021/12/2
 **/
public class SkipList {

    /**
     * 跳表每一层的概率值
     */
    public static final float SKIPLIST_P = 0.5f;

    /**
     * 跳表的索引最大层数限制，包括原始链表那一层
     */
    private static final int MAX_LEVEL = 16;

    /**
     * 当前索引层数
     */
    private int levelCount = 1;

    /**
     * 跳表头节点，链表中的头节点，空节点，本身不存储数据，下一跳指向各层索引的头节点
     */
    private Node head = new Node();

    /**
     * 随机索引层数
     * 随机是一个概率事件，例如第一层0.5的概率，第二层0.25概率，第三层0.125概率？
     * @return 随机一个索引层数
     */
    private int randomLevel() {
        int level = 1;
        while (Math.random() < SKIPLIST_P && level < MAX_LEVEL) {
            level ++;
        }

        return level;
    }

    /**
     * 插入一条数据，数据从小到大排序
     * @param value 插入数据
     */
    public void insert(int value) {
        //当前插入的节点应该构建的索引层数
        int level = randomLevel();
        Node newNode = new Node();
        newNode.value = value;
        newNode.level = level;
        //每一层的前置节点数组,初始化为head节点
        Node[] preNodes = new Node[level];
        for (int i = 0; i < level; i++) {
            preNodes[i] = head;
        }
        //默认每一层的前置节点都是初始的head节点
        Node preNode = head;
        //从上往下，每一层去找当前value应该插入的位置
        for (int i = level - 1; i >= 0; i--) {
            //前置节点有后置节点，并且后置节点的值还小于当前值，那就继续往后找到应该插入位置的前置节点
            while (preNode.forwards[i] != null && preNode.forwards[i].value < value) {
                preNode = preNode.forwards[i];
            }
            //找到前置节点了
            preNodes[i] = preNode;
        }

        //从上往下，将各个索引层的前置节点的后置节点设置为新插入的节点，新插入的节点的后置节点为原来的前置节点的后置节点
        for (int i = level - 1; i >= 0; i--) {
            newNode.forwards[i] = preNodes[i].forwards[i];
            preNodes[i].forwards[i] = newNode;
        }

        //更新当前跳表的索引层数
        if (levelCount < level) {
            levelCount = level;
        }
    }

    /**
     * 删除跳表数据
     * @param value 要删除的数据
     */
    public void delete(int value) {
        //先定义一个前置节点的数组，存储每一层的前置节点
        Node[] preNodes = new Node[levelCount];
        //初始化默认前置节点为头节点
        Node preNode = head;

        //找到每一层的前置节点
        for (int i = levelCount - 1; i >= 0; i--) {
            while (preNode.forwards[i] != null && preNode.forwards[i].value < value) {
                preNode = preNode.forwards[i];
            }
            preNodes[i] = preNode;
        }

        //经过上面的循环处理后，preNode应该是啥？应该是第0层要删除的数据的前置节点，也可以写成 preNodes[0].forwards[0]
        //判断第0层的前置节点的后置节点的value是否等于当前要删除的value，只有相等的时候才能网上查找删除掉每层节点
        if (preNode.forwards[0] != null && preNode.forwards[0].value == value) {
            for (int i = levelCount - 1; i >= 0; i--) {
                //查找每一层是否存在该节点，如果存在，则将前置节点的下一个节点指向存在的该节点的下一个节点
                if (preNodes[i].forwards[i] != null && preNodes[i].forwards[i].value == value) {
                    preNodes[i].forwards[i] = preNodes[i].forwards[i].forwards[i];
                }
            }
        }

        //更新索引层数,如果索引层数 > 1并且头节点的下一个节点指向null，意味着该层没有数据了
        while (levelCount > 1 && head.forwards[levelCount - 1] == null) {
            levelCount --;
        }
    }

    public Node find(int value) {
        Node preNode = head;
        //一定要从上往下去找：levelCount - 1 to 0，这样preNode最后就是第0层（原始链表）的前置节点
        for (int i = levelCount - 1; i >= 0; i--) {
            while (preNode.forwards[i] != null && preNode.forwards[i].value < value) {
                preNode = preNode.forwards[i];
            }
        }
        //如果前置节点的下一个节点的value等于value，那么下一个节点即是要找到的节点
        if (preNode.forwards[0] != null && preNode.forwards[0].value == value) {
            return preNode.forwards[0];
        }
        return null;
    }

    /**
     * 打印出来
     */
    public void print() {
        System.out.println("skipList levelCount:" + levelCount);
        //从上往下打印跳表数据
        for (int i = levelCount - 1; i >= 0; i--) {
            Node preNode = head;
            StringBuilder sb = new StringBuilder("第").append(i).append("层：-1(head) -->");
            while (preNode.forwards[i] != null) {
                sb.append(preNode.forwards[i].value);
                sb.append("-->");
                preNode = preNode.forwards[i];
            }
            sb.append("nil");
            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args) {
        SkipList skipList = new SkipList();
        skipList.insert(13);
        skipList.insert(19);
        skipList.insert(6);
        skipList.insert(15);
        skipList.insert(17);
        skipList.insert(9);
        skipList.insert(7);
        skipList.insert(8);
        skipList.print();
        skipList.delete(17);
        skipList.print();
    }

    /**
     * 跳表节点
     */
    public class Node {

        /**
         * 节点村的数值，默认-1
         */
        private int value = -1;

        /**
         * 当前节点索引层数 [0, MAX_LEVEL-1]
         */
        private int level = 0;

        /**
         * 当前节点每一层索引的下一跳节点
         * 示例：forwards[2] 表示当前节点在第2层（从0层开始计算）的下一跳节点
         */
        private Node[] forwards = new Node[MAX_LEVEL];
    }

}
