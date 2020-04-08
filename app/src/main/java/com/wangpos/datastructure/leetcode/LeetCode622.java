package com.wangpos.datastructure.leetcode;


class LeetCode622 {

    private int size;
    private int front;
    private int rear;
    private int [] data;

    public LeetCode622(int k) {
        data = new int[k+1];
        front = 0;
        rear = 0;
        size = 0;
    }

    public boolean enQueue(int value) {
        if(size == data.length - 1)return false;
        data[rear] = value;
        size++;
        rear = (rear+1)%data.length;
        return true;
    }

    public boolean deQueue() {
        if(isEmpty())return false;
        size--;
        front = (front + 1)%data.length;
        return true;
    }

    public int Front() {
        if(isEmpty())return -1;
        return data[front];
    }

    public int Rear() {
        if(isEmpty())return -1;
        return data[(rear - 1 + data.length)%data.length];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == data.length - 1;
    }

}