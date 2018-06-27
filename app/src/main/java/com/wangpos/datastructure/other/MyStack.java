package com.wangpos.datastructure.other;

import java.util.Stack;

/**
 * Created by qiyue on 2018/6/27.
 */

public class MyStack {

    private Stack<Integer> stackData;

    private Stack<Integer> stackMin;

    public MyStack() {
        this.stackData = new Stack<Integer>();
        this.stackMin = new Stack<Integer>();
    }

    public void push(int num) {
        stackData.push(num);
        if (!stackMin.isEmpty()) {
            stackMin.push(num);
        }
        if (num < stackMin.pop()) {
            stackMin.push(num);
        }
        stackData.push(num);
    }

    public int pop() {

        int data = 0;
        if(stackData.isEmpty()){
           throw new RuntimeException("集合为null");
        }

        data = stackData.pop();

        return data;

    }

    public int getMin() {
        if(stackMin.isEmpty()){
            throw new RuntimeException("数据为null");
        }
        return stackMin.peek();

    }
}
