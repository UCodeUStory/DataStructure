package com.wangpos.datastructure.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 验证原始的序列 org 是否可以从序列集 seqs 中唯一地重建。序列 org 是 1 到 n 整数的排列，
 *
 * 其中 1 ≤ n ≤ 10^4。重建是指在序列集 seqs 中构建最短的公共超序列。（即使得所有  seqs 中的序列都是该最短序列的子序列）。确定是否只可以从 seqs 重建唯一的序列，且该序列就是 org
 */
public class LeetCode444 {

    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        int n = org.length;

        if (n == 0 || seqs.size() == 0) {
            return false;
        }

        // 考虑seqs里头元素为空列表的情况, 已经数字超过n或者<=0的情况
        Set<Integer> numSet = new HashSet<>();
        for (List<Integer> list : seqs) {
            for (Integer num: list) {
                if (num <= 0 || num > n) {
                    return false;
                }
                numSet.add(num);
            }
        }

        if (numSet.size() < n) {
            return false;
        }

        ArrayList<Integer>[] adj = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }

        createTable(seqs, adj);

        // 计算每个节点的入度
        int[] inDegree = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < adj[i].size(); j++) {
                int w = adj[i].get(j);
                inDegree[w]++;
            }
        }

        // 计算入度为0的节点，添加到队列中
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                queue.addLast(i);
            }
        }

        // 入度为0的节点有多个，就会产生多种序列，或者没有入度为0的，不满足题目要求
        if (queue.size() != 1) {
            return false;
        }

        int index = 0;
        while (!queue.isEmpty()) {
            int num = queue.removeFirst();
            if (org[index] != num) {
                return false;
            }
            index++;

            // 删除当前节点后，所有当前节点的下一个节点的入度为0的个数，超过1则说明序列不唯一
            int nextZeroInDegreeCount = 0;
            for (int j = 0; j < adj[num].size(); j++) {
                int w = adj[num].get(j);
                //遍历邻接表中相邻边的入度都减1
                inDegree[w]--;
                if (inDegree[w] == 0) {
                    nextZeroInDegreeCount++;
                    //去掉一个结点后，如果入度为0的节点增加了不止1个，说明有多处一个分支，就直接返回false
                    if (nextZeroInDegreeCount > 1) {
                        return false;
                    }

                    queue.addLast(w);
                }
            }
        }

        return index == n;
    }

    private void createTable(List<List<Integer>> seqs, ArrayList<Integer>[] adj) {
        // 构建邻接表
        for (int i = 0; i < seqs.size(); i++) {
            List<Integer> pair = seqs.get(i);

            for (int j = 0; j < pair.size() - 1; j++) {
                //数组中链表同样初始化
                adj[pair.get(j)].add(pair.get(j+1));
            }
        }
    }

}
