package com.wangpos.datastructure.other;

import com.wangpos.datastructure.core.BaseActivity;

/**
 * Created by qiyue on 2018/6/27.
 */

public class GetMinActivity extends BaseActivity {
    MyStack myStack = null;
    @Override
    protected void initData() {

        myStack = new MyStack();
        myStack.push(4);
        myStack.push(3);
        myStack.push(1);
        myStack.push(2);


    }

    @Override
    protected String getTextData() {
        return "{4,3,1,2}";
    }

    @Override
    protected int getImageData() {
        return 0;
    }

    @Override
    protected String getResultData() {
        return ""+"min="+myStack.getMin();
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


    private void getMin(){

    }



}
