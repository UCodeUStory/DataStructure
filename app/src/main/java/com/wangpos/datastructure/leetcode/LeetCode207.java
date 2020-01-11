package com.wangpos.datastructure.leetcode;

import java.util.LinkedList;

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
            //
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

    private boolean dfs(int[][] adjacency, int[] flags, int i) {
        if (flags[i] == 1) return false;//表示此点被再次访问
        if (flags[i] == -1) return true;//表示此点已被访问
        //当前点
        flags[i] = 1;
        for (int j = 0; j < adjacency.length; j++) {
            //adjacency[i][j] == 1 表示之间有边
            if (adjacency[i][j] == 1 && !dfs(adjacency, flags, j)) return false;
        }
        flags[i] = -1;
        return true;
    }

}

