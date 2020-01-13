package com.wangpos.datastructure.leetcode;

import java.util.LinkedList;
import java.util.Stack;

public class LeetCode210 {

    public int[] findOrder(int numCourses, int[][] prerequisites) {

        Stack<Integer> stack = new Stack();
        int[] array = new int[0];
        if (canFinish2(numCourses, prerequisites, stack)) {
            array = new int[numCourses];
        } else {
            return array;
        }

        int index = 0;
        while (!stack.isEmpty()) {
            array[index] = stack.pop();
            index++;
        }
        return array;
    }

    /**
     * 时间复杂度: O(N)，其中NN为课程数。我们需要要对森林中的所有结点执行完全的深度优先搜索。之所以是森林而不是图，是因为并非所有结点都连接在一起。也可能存在不连通的部分。
     * 空间复杂度: O(N), 递归栈占用的空间(不是用于存储拓扑排序的栈)。
     * 利用深度优先搜索
     */

    public boolean canFinish2(int numCourses, int[][] prerequisites, Stack<Integer> stack) {
        int[][] adjacency = new int[numCourses][numCourses];
        //定义节点标识
        int[] flags = new int[numCourses];
        //将数组依赖关系变成矩阵
        for (int[] cp : prerequisites)
            adjacency[cp[1]][cp[0]] = 1;

        for (int i = 0; i < numCourses; i++) {
            if (!dfs(numCourses, adjacency, flags, i, stack)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 这里使用了一个小技巧，就是通过标识 0未被访问，1 标识正在被这条分支访问中，-1标识这个分支访问结束
     * 所以可以很容易判断 如果被访问状态是1状态就说明存在了环路
     *
     * @param adjacency
     * @param flags
     * @param i
     * @return
     */
    private boolean dfs(int numCourses, int[][] adjacency, int[] flags, int i, Stack<Integer> stack) {
        if (flags[i] == 1) return false;//表示本次访问的节点还没访问结束又被子节点访问了，所以就存在了环路
        if (flags[i] == -1) {
            return true;//表示此点已被访问
        }
        if (flags[i] == 0) flags[i] = 1; //当前已被访问，继续这个点的子节点
        for (int j = 0; j < adjacency.length; j++) {
            //adjacency[i][j] == 1 判断有边，如果result false证明有环就return
            if (adjacency[i][j] == 1 && !dfs(numCourses, adjacency, flags, j, stack)) return false;

            //等价代码
//            if (adjacency[i][j] == 1) {
//                boolean result = dfs(numCourses, adjacency, flags, j, stack);
//                if (!result) {
//                    //有环路
//                    return false;
//                }
//            }

        }
        ///当前已被访问完毕，子节点也访问完毕

        flags[i] = -1;
        //保存遍历
        stack.push(i);
        return true;
    }


    /**
     *   方法二: 利用结点的入度
     */


}
