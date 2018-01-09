package com.wangpos.datastructure.graph;

import android.util.Log;

/**
 * Created by qiyue on 2018/1/9.
 *
 * 无向图的邻接矩阵存储方式
 */

public class UndirectedGraphMatrix {

    private String[] mVexs;       // 顶点集合
    private int[][] mMatrix;    // 邻接矩阵


    public UndirectedGraphMatrix(String []vexs,String [][]edges){


        // 初始化"顶点数"和"边数"
        int vlen = vexs.length;
        int elen = edges.length;

        // 初始化"顶点"
        mVexs = new String[vlen];
        for (int i = 0; i < mVexs.length; i++)
            mVexs[i] = vexs[i];

        // 初始化"边"
        mMatrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            int p1 = getPosition(edges[i][0]);
            int p2 = getPosition(edges[i][1]);

            /**
             * 无向图是这样的
             */
            mMatrix[p1][p2] = 1;
            mMatrix[p2][p1] = 1;
        }
    }

    private int getPosition(String s) {

        for(int i=0; i<mVexs.length; i++) {
            if (mVexs[i].equals(s)) {
                return i;
            }
        }
        return -1;
    }

    /*
    * 返回顶点v的第一个邻接顶点的索引，失败则返回-1
    */
    private int firstVertex(int v) {

        if (v<0 || v>(mVexs.length-1))
            return -1;

        for (int i = 0; i < mVexs.length; i++)
            if (mMatrix[v][i] == 1)
                return i;

        return -1;
    }

    /*
     * 返回顶点v相对于w的下一个邻接顶点的索引，失败则返回-1
     */
    private int nextVertex(int v, int w) {

        if (v<0 || v>(mVexs.length-1) || w<0 || w>(mVexs.length-1))
            return -1;

        for (int i = w + 1; i < mVexs.length; i++)
            if (mMatrix[v][i] == 1)
                return i;

        return -1;
    }

    /*
     * 深度优先搜索遍历图的递归实现
     */
    private void DFS(int i, boolean[] visited) {

        visited[i] = true;
        Log.i("tu",mVexs[i]);
        // 遍历该顶点的所有邻接顶点。若是没有访问过，那么继续往下走
        for (int w = firstVertex(i); w >= 0; w = nextVertex(i, w)) {
            if (!visited[w])
                DFS(w, visited);
        }
    }

    /*
 * 深度优先搜索遍历图
 */
    public void DFS() {
        boolean[] visited = new boolean[mVexs.length];       // 顶点访问标记

        // 初始化所有顶点都没有被访问
        for (int i = 0; i < mVexs.length; i++)
            visited[i] = false;

        Log.i("tu","DFS: ");
        for (int i = 0; i < mVexs.length; i++) {
            if (!visited[i])
                DFS(i, visited);
        }
        Log.i("tu","");
    }

    /*
    * 广度优先搜索（类似于树的层次遍历）
    */
    public void BFS() {
        int head = 0;
        int rear = 0;
        int[] queue = new int[mVexs.length];            // 辅组队列
        boolean[] visited = new boolean[mVexs.length];  // 顶点访问标记

        for (int i = 0; i < mVexs.length; i++)
            visited[i] = false;

        Log.i("tu","BFS: ");
        for (int i = 0; i < mVexs.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                Log.i("tu", mVexs[i]);
                queue[rear++] = i;  // 入队列
            }

            while (head != rear) {
                int j = queue[head++];  // 出队列
                for (int k = firstVertex(j); k >= 0; k = nextVertex(j, k)) { //k是为访问的邻接顶点
                    if (!visited[k]) {
                        visited[k] = true;
                        Log.i("tu", mVexs[k]);
                        queue[rear++] = k;
                    }
                }
            }
        }
        Log.i("tu","");
    }



}
