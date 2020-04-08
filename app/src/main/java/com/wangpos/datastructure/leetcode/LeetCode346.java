package com.wangpos.datastructure.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/**
 * 346. 数据流中的移动平均值
 *
 * 给定一个整数数据流和一个窗口大小，根据该滑动窗口的大小，计算其所有整数的移动平均值。
 *
 * 示例:
 *
 * MovingAverage m = new MovingAverage(3);
 * m.next(1) = 1
 * m.next(10) = (1 + 10) / 2
 * m.next(3) = (1 + 10 + 3) / 3
 * m.next(5) = (10 + 3 + 5) / 3
 *
 */
public class LeetCode346 {

    public static void main(String[] args) {
        System.out.println("----start----");

        LeetCode346 leetCode346 = new LeetCode346(3);
        System.out.println(leetCode346.next(1));
        System.out.println(leetCode346.next(10));
        System.out.println(leetCode346.next(3));
        System.out.println(leetCode346.next(5));
    }

    Deque<Integer> dqueues = new ArrayDeque<Integer>();

    int maxSize = 0;

    int queueSum = 0;

    /**
     * Initialize your data structure here.
     */
    public LeetCode346(int size) {
        this.maxSize = size;
    }


    public double next(int val) {

        //如果size小于maxSize 直接添加

        //返回队列和除以真实size

        int removeValue = 0;
        if (dqueues.size() >= maxSize) {
            removeValue = dqueues.removeFirst();
        }
        dqueues.add(val);

        if (queueSum == 0) {
            for (Integer dqueue : dqueues) {
                queueSum += dqueue;
            }
        } else {
            //减去移除的，添加新的
            queueSum = queueSum - removeValue + val;
        }

        return queueSum*1.0 / dqueues.size();
    }

}



