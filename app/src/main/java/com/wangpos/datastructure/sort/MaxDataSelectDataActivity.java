package com.wangpos.datastructure.sort;

import com.wangpos.datastructure.core.BaseActivity;

/**
 * Created by qiyue on 2017/11/28.
 */

public class MaxDataSelectDataActivity extends BaseActivity {
    @Override
    protected void initData() {

    }

    @Override
    protected String getTextData() {
        return "10亿数据中取最大的100个数据？" +
                "如果采用淘汰法。\n" +
                "具体步骤如下：\n" +
                "选取前100个元素，并排序，记为序列L。\n" +
                "然后一次扫描剩余的元素x，与排好序的100个元素中最小的元素比，如果比这个最小的要大，那么把这个最小的元素删除，并把x利用插入排序的思想，插入到序列L中。依次循环，知道扫描了所有的元素。\n" +
                "\n" +
                "复杂度为O(10亿*100）";
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
        return "(10亿logn)";
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
        return "先取出前100个数，维护一个100个数的最小堆，遍历一遍剩余的元素，在此过程中维护小顶堆就可以了。\n" +
                "具体步骤如下：\n" +
                "取前m个元素（例如m=100），建立一个小顶堆。保持一个小顶堆得性质的步骤，运行时间为O（logm);建立一个小顶堆运行时间为mO（logm）=O(mlogm);\n" +
                "顺序读取后续元素，直到结束。每次读取一个元素，如果该元素比堆顶元素小，直接丢弃；如果大于堆顶元素，则用该元素替换堆顶元素，然后保持最小堆性质。最坏情况是每次都需要替换掉堆顶的最小元素，因此需要维护堆的代价为(N-m)O(lgm); 最后这个堆中的元素就是前最大的100个。时间复杂度为O(N lgm）。\n" +
                "\n" ;
    }
}
