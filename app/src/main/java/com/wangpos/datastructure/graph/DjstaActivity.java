package com.wangpos.datastructure.graph;

import android.app.IntentService;

import com.wangpos.datastructure.R;
import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;

import thereisnospon.codeview.CodeViewTheme;

/**
 * Created by qiyue on 2018/1/16.
 *
 */

public class DjstaActivity extends BaseActivity {

    int MAX = Integer.MAX_VALUE;
    @Override
    protected void initData() {

        char mVexs[] = {'A','B','C','D','E','F','G'};
        int data[][] = {
                {0,5,MAX,MAX,4,MAX,MAX},
                {MAX,0,10,MAX,MAX,MAX,MAX},
                {MAX,MAX,0,3,MAX,4,MAX},
                {MAX,MAX,MAX,0,MAX,MAX,8},
                {MAX,MAX,7,12,0,MAX,MAX},
                {MAX,MAX,MAX,MAX,MAX,0,6},
                {MAX,MAX,MAX,MAX,MAX,MAX,0},
        };

        MatrixUDG matrixUDG = new MatrixUDG(9,mVexs,data);

        matrixUDG.dijkstra(0);


        addItem(new CodeBean("最短路径迪杰斯特拉算法",djsta), CodeViewTheme.DARK);

    }

    @Override
    protected String getTextData() {
        return null;
    }

    @Override
    protected int getImageData() {
        return R.drawable.short_path;
    }

    @Override
    protected String getResultData() {
        return  " A到A  路径=A>A           最短距离=0 \n" +
                " A到B  路径=A>B           最短距离=5 \n" +
                " A到C  路径=A>E>C         最短距离=11 \n" +
                " A到D  路径=A>E>C>D       最短距离=14 \n" +
                " A到E  路径=A>E           最短距离=4 \n" +
                " A到F  路径=A>E>C>F       最短距离=15 \n" +
                " A到G  路径=A>E>C>F>G     最短距离=21 ";
    }

    @Override
    protected String getTimeData() {
        return null;
    }

    @Override
    protected String getSpaceTimeData() {
        return null;
    }

    @Override
    protected String getWendingXingData() {
        return null;
    }

    @Override
    protected String getSummaryData() {
        return "\n" +
                "0 .给一个原点\n" +
                "\n" +
                "\n" +
                "1.初始化所有点的前驱 0 ，原点到所有点距离，是否被标记\n" +
                "2.从所有未标记过的点中，找到路径（相对于原点）最短的一个点 （这才是每次找出来最短的）\n" +
                "3.以这个点为基础，更新到达其他点的路径长度，（dist一开始除了相邻点有值，其他都是无穷大）写到dist数组中,同时标记前驱\n";
    }

    private static final String djsta = "public void dijkstra(int vs) {\n" +
            "        // flag[i]=true表示\"顶点vs\"到\"顶点i\"的最短路径已成功获取\n" +
            "        boolean[] flag = new boolean[mVexs.length];\n" +
            "        /**\n" +
            "         * 路径保存辅助类\n" +
            "         */\n" +
            "        int[] prev = new int[mVexs.length];\n" +
            "        /**\n" +
            "         * 最短距离\n" +
            "         */\n" +
            "        int[] dist = new int[mVexs.length];\n" +
            "\n" +
            "        /**\n" +
            "         * 路径保存\n" +
            "         */\n" +
            "        String[] path = new String[mVexs.length];\n" +
            "        // 初始化\n" +
            "        for (int i = 0; i < mVexs.length; i++) {\n" +
            "            flag[i] = false;          // 顶点i的最短路径还没获取到。\n" +
            "            prev[i] = 0;              // 顶点i的前驱顶点为0。\n" +
            "            dist[i] = mMatrix[vs][i];  // 顶点i的最短路径为\"顶点vs\"到\"顶点i\"的权。\n" +
            "        }\n" +
            "\n" +
            "        // 对\"顶点vs\"自身进行初始化\n" +
            "        flag[vs] = true;\n" +
            "        dist[vs] = 0;\n" +
            "\n" +
            "        // 遍历mVexs.length-1次；每次找出一个顶点的最短路径。\n" +
            "        int k=0;\n" +
            "        for (int i = 1; i < mVexs.length; i++) {\n" +
            "            // 寻找当前最小的路径；\n" +
            "            // 即，在未获取最短路径的顶点中，找到离vs最近的顶点(k)。\n" +
            "            int min = INF;\n" +
            "            for (int j = 0; j < mVexs.length; j++) {\n" +
            "                if (flag[j]==false && dist[j]<min) {\n" +
            "                    min = dist[j];\n" +
            "                    k = j;\n" +
            "                }\n" +
            "            }\n" +
            "            // 标记\"顶点k\"为已经获取到最短路径\n" +
            "            flag[k] = true;\n" +
            "\n" +
            "            // 修正当前最短路径和前驱顶点\n" +
            "            // 即，当已经\"顶点k的最短路径\"之后，更新\"未获取最短路径的顶点的最短路径和前驱顶点\"。\n" +
            "            for (int j = 0; j < mVexs.length; j++) {\n" +
            "                int tmp = (mMatrix[k][j]==INF ? INF : (min + mMatrix[k][j]));\n" +
            "                if (flag[j] == false && (tmp < dist[j]) ) {//之前可能无穷大的点，现在通过k点可以计算出来，也就是原点通过第二点可以计算出第三点\n" +
            "                    dist[j] = tmp;\n" +
            "                    prev[j] = k;\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        // 打印dijkstra最短路径的结果\n" +
            "        Log.i(\"tu\",\"dijkstra(%c): \\n\"+ mVexs[vs]);\n" +
            "        for (int i=0; i < mVexs.length; i++) {\n" +
            "            int p = i;\n" +
            "            while (prev[p] != 0) {\n" +
            "                if (path[i]!=null) {\n" +
            "                    path[i] = mVexs[prev[p]] + \">\" + path[i];\n" +
            "                }else{\n" +
            "                    path[i] = mVexs[prev[p]] + \"\";\n" +
            "                }\n" +
            "                p = prev[p];\n" +
            "            }\n" +
            "\n" +
            "            if (path[i]!=null) {\n" +
            "                path[i] = mVexs[vs] +\">\" + path[i] + \">\" + mVexs[i];\n" +
            "            }else{\n" +
            "                path[i] = mVexs[vs] +\">\" + mVexs[i];\n" +
            "            }\n" +
            "\n" +
            "//            Log.i(\"tu\", \"\" + mVexs[vs] + \"到\" + mVexs[i] +\" \"+alignLeft(\"路径=\"+path[i],15) + \" 最短距离=\" + dist[i]);\n" +
            "//            Log.i(\"tu\", \"\" + mVexs[vs] + \"到\" + mVexs[i] +\" \"+alignLeft(\"路径=\"+path[i],15) + \" 最短距离=\" + dist[i]);\n" +
            "\n" +
            "            String text = AlignText.join(\" \",mVexs[vs] + \"到\" + mVexs[i],alignLeft(\"路径=\"+path[i],15),\"最短距离=\" + dist[i]);\n" +
            "\n" +
            "            Log.i(\"tu\",text);\n" +
            "\n" +
            "        }\n" +
            "    }\n";
}
