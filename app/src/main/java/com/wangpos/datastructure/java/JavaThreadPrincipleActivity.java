package com.wangpos.datastructure.java;

import android.util.Log;

import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.java.thread.USThreadPool;

import org.jsoup.Connection;

/**
 * Created by qiyue on 2018/6/14.
 */

public class JavaThreadPrincipleActivity extends BaseActivity {
    @Override
    protected void initData() {

        USThreadPool usThreadPool = new USThreadPool(5);

        for(int i=0;i<100;i++){
            final int finalI = i;
            usThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    Log.i("USThreadPool","任务="+ finalI);
                }
            });
        }


    }

    @Override
    protected String getTextData() {
        return null;
    }

    @Override
    protected int getImageData() {
        return 0;
    }

    @Override
    protected String getResultData() {
        return null;
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
