package com.wangpos.datastructure.graph;

/**
 * Created by qiyue on 2018/1/9.
 *
 * 有向图邻接矩阵
 */

public class DirectedGraphMatrix {


    private String[] mVexs;       // 顶点集合
    private int[][] mMatrix;    // 邻接矩阵


    public DirectedGraphMatrix(String []vexs,String [][]edges){


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
             * 有向图有固定方向
             */
            mMatrix[p1][p2] = 1;
//            mMatrix[p2][p1] = 1;
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
}
