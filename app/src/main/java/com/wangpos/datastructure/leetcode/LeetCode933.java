package com.wangpos.datastructure.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 933. 最近的请求次数
 */
public class LeetCode933 {

    Queue<Integer> q;
    public LeetCode933() {
        q = new LinkedList();
    }

    public int ping(int t) {
        q.add(t);
        while (q.peek() < t - 3000)
            q.poll();
        return q.size();
    }
}
