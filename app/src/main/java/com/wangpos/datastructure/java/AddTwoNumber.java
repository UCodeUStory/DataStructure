package com.wangpos.datastructure.java;

import com.wangpos.datastructure.R;
import com.wangpos.datastructure.core.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by qiyue on 2017/12/7.
 */

public class AddTwoNumber extends BaseActivity {
    @Override
    protected void initData() {
        setTitleText(getString(R.string.addtwonumber));
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



    public ListNode addTwoNumber(ListNode l1,ListNode l2){

        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
        return null;

    }


    public class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
    }
}
