package com.wangpos.datastructure.graph;

import android.app.IntentService;

import com.wangpos.datastructure.R;
import com.wangpos.datastructure.core.BaseActivity;

/**
 * Created by qiyue on 2018/1/16.
 *
 */

public class MatrixUDGActivity extends BaseActivity {

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
        return null;
    }
}
