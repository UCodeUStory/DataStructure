package com.wangpos.datastructure.leetcode;


import java.util.Arrays;

class LeetCode641 {

    public static void main(String[] args) {
        LeetCode641 circularDeque = new LeetCode641(3);
        System.out.println("结果：" + circularDeque.insertLast(1));            // 返回 true
        System.out.println("结果：" + circularDeque.insertLast(2));
        System.out.println("结果：" + circularDeque.insertLast(4));
//        System.out.println("结果：" + circularDeque.insertLast(3));   // 返回 true
//        circularDeque.insertFront(3);			        // 返回 true
//        circularDeque.insertFront(4);			        // 已经满了，返回 false
        System.out.println("结果：" + circularDeque.getRear());                // 返回 2
        System.out.println("结果：" + circularDeque.isFull());                        // 返回 true
//        System.out.println("结果：" + circularDeque.deleteLast());                    // 返回 true
//        circularDeque.insertFront(4);			        // 返回 true
        System.out.println("结果：front " + circularDeque.getFront());
        System.out.println("结果：" + circularDeque.deleteFront());
        System.out.println("结果：" + circularDeque.deleteLast());

        System.out.println(circularDeque.toString());


    }


    private int size;
    private int front;
    private int rear;
    private int[] data;

    /**
     * Initialize your data structure here. Set the size of the deque to be k.
     */
    public LeetCode641(int k) {
        data = new int[k + 1];
        front = 0;
        rear = 0;
        size = 0;
    }

    /**
     * Adds an item at the front of Deque. Return true if the operation is successful.
     */
    public boolean insertFront(int value) {
        if (size == data.length - 1) return false;
        front = (front - 1 + data.length) % data.length;
        data[front] = value;
        size++;
        return true;
    }

    /**
     * Adds an item at the rear of Deque. Return true if the operation is successful.
     */
    public boolean insertLast(int value) {
        if (size == data.length - 1) return false;
        data[rear] = value;
        size++;
        rear = (rear + 1) % data.length;

        return true;
    }

    //    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    public boolean deleteFront() {
        if (isEmpty()) return false;
        size--;
        front = (front + 1) % data.length;
        return true;
    }

    /**
     * Deletes an item from the rear of Deque. Return true if the operation is successful.
     */
    public boolean deleteLast() {
        if (isEmpty()) return false;
        size--;
        rear = (rear - 1 + data.length) % data.length;
        return true;
    }

    /**
     * Get the front item from the deque.
     */
    public int getFront() {
        if (isEmpty()) return -1;
        return data[front];
    }

    /**
     * Get the last item from the deque.
     */
    public int getRear() {
        if (isEmpty()) return -1;
        return data[(rear - 1 + data.length) % data.length];
    }

    /**
     * Checks whether the circular deque is empty or not.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks whether the circular deque is full or not.
     */
    public boolean isFull() {
        return size == data.length - 1;
    }

    @Override
    public String toString() {
        return " front = " + front + " rear=" + rear + " size=" + size + " " + Arrays.toString(data);

    }
}

/**
 * Your MyCircularDeque object will be instantiated and called as such:
 * MyCircularDeque obj = new MyCircularDeque(k);
 * boolean param_1 = obj.insertFront(value);
 * boolean param_2 = obj.insertLast(value);
 * boolean param_3 = obj.deleteFront();
 * boolean param_4 = obj.deleteLast();
 * int param_5 = obj.getFront();
 * int param_6 = obj.getRear();
 * boolean param_7 = obj.isEmpty();
 * boolean param_8 = obj.isFull();
 */