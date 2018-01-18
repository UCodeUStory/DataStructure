package com.wangpos.datastructure.graph;

import android.util.Log;

import com.wangpos.datastructure.core.AlignText;

/**
 * Created by qiyue on 2018/1/16.
 */

public class MatrixUDG {

    public MatrixUDG(int mEdgNum, char[] mVexs, int[][] mMatrix) {
        this.mEdgNum = mEdgNum;
        this.mVexs = mVexs;
        this.mMatrix = mMatrix;
    }

    private int mEdgNum;        // 边的数量
    private char[] mVexs;       // 顶点集合
    private int[][] mMatrix;    // 邻接矩阵

    private static final int INF = Integer.MAX_VALUE;   // 最大值

        /*
     * Dijkstra最短路径。
     * 即，统计图中"顶点vs"到其它各个顶点的最短路径。
     *
     * 参数说明：
     *       vs -- 起始顶点(start vertex)。即计算"顶点vs"到其它顶点的最短路径。
     *     prev -- 前驱顶点数组。即，prev[i]的值是"顶点vs"到"顶点i"的最短路径所经历的全部顶点中，位于"顶点i"之前的那个顶点。
     *     dist -- 长度数组。即，dist[i]是"顶点vs"到"顶点i"的最短路径的长度。
     */
    public void dijkstra(int vs) {
        // flag[i]=true表示"顶点vs"到"顶点i"的最短路径已成功获取
        boolean[] flag = new boolean[mVexs.length];
        int[] prev = new int[mVexs.length];
        int[] dist = new int[mVexs.length];

        String[] path = new String[mVexs.length];
        // 初始化
        for (int i = 0; i < mVexs.length; i++) {
            flag[i] = false;          // 顶点i的最短路径还没获取到。
            prev[i] = 0;              // 顶点i的前驱顶点为0。
            dist[i] = mMatrix[vs][i];  // 顶点i的最短路径为"顶点vs"到"顶点i"的权。
        }

        // 对"顶点vs"自身进行初始化
        flag[vs] = true;
        dist[vs] = 0;

        // 遍历mVexs.length-1次；每次找出一个顶点的最短路径。
        int k=0;
        for (int i = 1; i < mVexs.length; i++) {
            // 寻找当前最小的路径；
            // 即，在未获取最短路径的顶点中，找到离vs最近的顶点(k)。
            int min = INF;
            for (int j = 0; j < mVexs.length; j++) {
                if (flag[j]==false && dist[j]<min) {
                    min = dist[j];
                    k = j;
                }
            }
            // 标记"顶点k"为已经获取到最短路径
            flag[k] = true;

            // 修正当前最短路径和前驱顶点
            // 即，当已经"顶点k的最短路径"之后，更新"未获取最短路径的顶点的最短路径和前驱顶点"。
            for (int j = 0; j < mVexs.length; j++) {
                int tmp = (mMatrix[k][j]==INF ? INF : (min + mMatrix[k][j]));
                if (flag[j] == false && (tmp < dist[j]) ) {//之前可能无穷大的点，现在通过k点可以计算出来，也就是原点通过第二点可以计算出第三点
                    dist[j] = tmp;
                    prev[j] = k;
                }
            }
        }

        // 打印dijkstra最短路径的结果
        Log.i("tu","dijkstra(%c): \n"+ mVexs[vs]);
        for (int i=0; i < mVexs.length; i++) {


//            Log.i("tu", "" + mVexs[vs] + "到" + mVexs[i] + " 最短距离 " + dist[i]);

            int p = i;
            while (prev[p] != 0) {

                if (path[i]!=null) {
                    path[i] = mVexs[prev[p]] + ">" + path[i];
                }else{
                    path[i] = mVexs[prev[p]] + "";
                }

                p = prev[p];
            }


            if (path[i]!=null) {
                path[i] = mVexs[vs] +">" + path[i] + ">" + mVexs[i];
            }else{
                path[i] = mVexs[vs] +">" + mVexs[i];
            }

//            Log.i("tu", "" + mVexs[vs] + "到" + mVexs[i] +" "+alignLeft("路径="+path[i],15) + " 最短距离=" + dist[i]);
//            Log.i("tu", "" + mVexs[vs] + "到" + mVexs[i] +" "+alignLeft("路径="+path[i],15) + " 最短距离=" + dist[i]);

            String text = AlignText.join(" ",mVexs[vs] + "到" + mVexs[i],alignLeft("路径="+path[i],15),"最短距离=" + dist[i]);

            Log.i("tu",text);

//            String lujing = AlignText.as(15).alignLeft("路径="+path[i]);






        }
    }






    /**
     * 相当于左对齐
     * @param text
     * @param maxLength
     * @return
     */
    private String alignLeft(String text ,int maxLength){

        int length = text.length();

        for (int i = 0;i<maxLength-length;i++){
            text += " ";
        }

        return text;

    }
}
