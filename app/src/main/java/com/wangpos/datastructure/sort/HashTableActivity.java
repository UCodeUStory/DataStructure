package com.wangpos.datastructure.sort;

import com.wangpos.datastructure.core.BaseActivity;

import static com.wangpos.datastructure.sort.HashTableDataProvider.text;

/**
 * Created by qiyue on 2017/11/29.
 */

public class HashTableActivity extends BaseActivity {
    @Override
    protected void initData() {
        tvData.setTextColor(0xff333333);
    }

    @Override
    protected String getTextData() {
        return text;
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
