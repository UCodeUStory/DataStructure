package com.wangpos.datastructure.leetcode;

import java.util.LinkedList;

/**
 *
 * 广度优先搜索 通过保存一个入度表
 *
 * 然后取出入度为0的元素添加到队列
 *
 * 遍历入度为0的队列，如果到下一个有边，将下一个入度--1，当所有下一个边都--1 相当于移除这个点
 *
 * 如果下一个点的入度为0 就添加到遍历的队列
 *
 * 在遍历的时候计数一下看看是否所有点都遍历到可以判断环形
 *
 *
 * 深度优先搜索，
 *
 * 通过flag数组标识 0 未遍历 1遍历中，-1遍历完成
 *
 * 如果遍历下一个点的时候判断1标识存在环
 *
 * 遍历下一个节点，然后再递归调用
 *
 * 邻接矩阵通过 graph[][]==1判断有没有边
 * 通过遍历依赖关系列表 [0]位置出现的次数就是入度，比如[1,3]证明 3到1的边，1的入度为1
 *
 * 邻接表，可以通过数组中集合的个数表示入度
 *
 *
 */
public class LeetCode207 {

    /**
     * 时间复杂度 O(N + M)O(N+M)，遍历一个图需要访问所有节点和所有临边，NN 和 MM 分别为节点数量和临边数量；
     * 空间复杂度 O(N)O(N)，为建立邻接矩阵所需额外空间。
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {

        //统计课程安排图中每个节点的入度，生成 入度表 indegrees
        int[] indegrees = new int[numCourses];

        //我们定义的二维数组中，每个数组的含义是前一个依赖后一个，
        //相当于先学完数组[1]号位元素才能学习数组[0]
        //所以按照有向图的构成是数组[1]指向数组[0]的一条边，所以数组[0]的入度为1(相当于有一个箭头指向）
        //由前面条件可以知道，遍历数组，统计每一个数组[0]号位置的出现次数就是他的入度数
        for (int[] cp : prerequisites) indegrees[cp[0]]++;
        //保存入度为0的元素，也就是拓扑排序出发点
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegrees[i] == 0) queue.addLast(i);
        }
        //从每个出发点出发开始遍历
        while (!queue.isEmpty()) {
            //取出入度为0的元素
            Integer pre = queue.removeFirst();
            //每次遍历，相当于从这个起点出发的所有边都遍历到了，所以相当于这个点遍历完就完成了一个课程，这就是广度优先搜索
            numCourses--;
            //遍历其余边
            for (int[] req : prerequisites) {
                //广度优先搜索就是逐个遍历，任意找到一个含有当前起点的边，如果不包含就跳过
                if (req[1] != pre) continue;
                //找到之后，将这条边去掉，也就是将所指方向的点的入度减一
                //如果其入度也变成0了就加入到起点队列，方便下次遍历后也包含此点
                //如果不存在环则可以按照某个顺序遍历所有点
                if (--indegrees[req[0]] == 0) queue.add(req[0]);
            }
        }
        return numCourses == 0;
    }


    public boolean canFinish2(int numCourses, int[][] prerequisites) {
        int[][] adjacency = new int[numCourses][numCourses];
        //定义节点标识
        int[] flags = new int[numCourses];
        //将数组变成矩阵
        for (int[] cp : prerequisites)
            adjacency[cp[1]][cp[0]] = 1;
        for (int i = 0; i < numCourses; i++) {
            if (!dfs(adjacency, flags, i)) return false;
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
    private boolean dfs(int[][] adjacency, int[] flags, int i) {
        if (flags[i] == 1) return false;//表示本次访问的节点还没访问结束又被子节点访问了，所以就存在了环路
        if (flags[i] == -1) return true;//表示此点已被访问
        if (flags[i] == 0) flags[i] = 1; //当前已被访问，继续这个点的子节点
        for (int j = 0; j < adjacency.length; j++) {
            //adjacency[i][j] == 1 表示之间有边
            if (adjacency[i][j] == 1 && !dfs(adjacency, flags, j)) return false;

        }
        ///当前已被访问完毕，子节点也访问完毕
        flags[i] = -1;
        return true;
    }

}

