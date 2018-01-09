package com.wangpos.datastructure.graph;

import android.util.Log;

/**
 * Created by qiyue on 2018/1/8.
 * <p>
 * 无向图的邻接表存储方式
 */

public class UndirectedGraph {

    /**
     * 整个数组表示一个图，也就是多个头结点信息结合构成
     */
    private VNode mVNodeArrays[];

    /**
     * 图的大小
     */
    private int size;

    /**
     * 创建图(用已提供的矩阵)
     * <p>
     * vexs  -- 顶点数组
     * edges -- 边数组
     */
    public UndirectedGraph(String[] vexs, String[][] edges) {
        size = vexs.length;
        mVNodeArrays = new VNode[size];
        //初始化定点信息
        for (int i = 0; i < size; i++) {
            mVNodeArrays[i] = new VNode();
            mVNodeArrays[i].data = vexs[i];
            mVNodeArrays[i].firstEdge = null;
        }
        //将顶点和边链接一起
        for (int j = 0; j < size; j++) {
            String start = edges[j][0];
            String end = edges[j][1];
            int startPosition = getPosition(start);
            int endPosition = getPosition(end);
            ENode eNode = new ENode(endPosition);

            if (mVNodeArrays[startPosition].firstEdge == null) {
                mVNodeArrays[startPosition].firstEdge = eNode;
            } else {
                linkLast(mVNodeArrays[startPosition].firstEdge, eNode);
            }
        }
    }

    private int getPosition(String start) {

        int target = -1;
        for (int i = 0; i < size; i++) {
            String data = mVNodeArrays[i].data;
            if (data.equals(start)) {
                target = i;
                break;
            }

        }
        return target;
    }


    private void linkLast(ENode list, ENode node) {
        ENode p = list;

        while (p.nextEdge != null)
            p = p.nextEdge;
        p.nextEdge = node;
    }

    /**
     * 头结点信息
     */
    private class VNode {
        public String data;//直接用String也可以，就是浪费了一点空间
        public ENode firstEdge;


    }

    /**
     * 边，通过该边对象可以知道该末尾端点和下一条边
     */
    private class ENode {
        public int ivex;  //该边对应结束点的位置
        public ENode nextEdge; // 这里是为了链接下一边

        public ENode(int ivex) {
            this.ivex = ivex;
        }
    }


    public void print() {
        System.out.printf("List Graph:\n");
        for (int i = 0; i < mVNodeArrays.length; i++) {

//            Log.i("tu", "顶点>>"+i + "" + mVNodeArrays[i].data);

            StringBuilder sb = new StringBuilder();
            sb.append("" + i + "" + mVNodeArrays[i].data);
            ENode node = mVNodeArrays[i].firstEdge;
            while (node != null) {
                sb.append(">" + node.ivex + "(" + mVNodeArrays[node.ivex].data + ")");
                node = node.nextEdge;
            }
            Log.i("tu", sb.toString());
//            System.out.printf("\n");
        }
    }


    /*
     * 深度优先搜索遍历图
     */
    public void DFS() {
        boolean[] visited = new boolean[mVNodeArrays.length];       // 顶点访问标记

        // 初始化所有顶点都没有被访问
        for (int i = 0; i < mVNodeArrays.length; i++)
            visited[i] = false;

        Log.i("tu", "DFS:");
        for (int i = 0; i < mVNodeArrays.length; i++) {
            if (!visited[i])
                DFS(i, visited);
        }
        Log.i("tu", "\n");
    }




    private void DFS(int i, boolean[] visited) {
        ENode node;

        visited[i] = true;
//        System.out.printf("%c ", mVNodeArrays[i].data);
        Log.i("tu", mVNodeArrays[i].data);
        node = mVNodeArrays[i].firstEdge;
        while (node != null) {
            if (!visited[node.ivex])
                DFS(node.ivex, visited);
            node = node.nextEdge;
        }
    }

    /**
     * 广度优先搜索
     */
    public void BFS() {

        boolean[] visited = new boolean[mVNodeArrays.length];       // 顶点访问标记

        // 初始化所有顶点都没有被访问
        for (int i = 0; i < mVNodeArrays.length; i++) {
            visited[i] = false;
        }

        Log.i("tu", "BFS");

        int head = 0;
        int rear = 0;
        int[] queue = new int[mVNodeArrays.length];

        for (int i = 0; i < mVNodeArrays.length; i++) {
            if (!visited[i]) {
                visited[i] = true;

                Log.i("tu", "yyy" + mVNodeArrays[i].data);
                //入列
                queue[rear] = i;
                rear++;
            }

            //rear之前的都是被访问的点，通过header 去访问每个点下一层，访问的点通过rear位置加入队列

            while (head != rear) {
                int j = queue[head];
                ENode node = mVNodeArrays[j].firstEdge;
//                Log.i("tu", "node--"+mVNodeArrays[j].data +"node="+node);
                // 开始遍历j的所有边，并且入队列 start
                while (node != null) {
                    int k = node.ivex;
//                    Log.i("tu", "node--"+mVNodeArrays[j].data +"k="+k+"visited[k]= "+visited[k]);
                    if (!visited[k]) {
                        visited[k] = true;
                        Log.i("tu", "" + mVNodeArrays[k].data);
                        queue[rear] = k;
                        rear++;
                    }
                    node = node.nextEdge;
                }
                // 开始遍历j的所有边，end
//                Log.i("tu", "》》》》" );
                //出队列，
                head++;
            }
        }


    }

}
