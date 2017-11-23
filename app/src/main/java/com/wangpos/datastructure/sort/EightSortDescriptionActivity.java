package com.wangpos.datastructure.sort;

import com.wangpos.datastructure.R;
import com.wangpos.datastructure.core.BaseActivity;

/**
 * Created by qiyue on 2017/11/23.
 */

public class EightSortDescriptionActivity extends BaseActivity {
    @Override
    protected void initData() {
        setTitleText("八大排序总结");
        addImage("时间复杂度", R.drawable.eight);
        addImage("类别",R.drawable.eight2);

    }

    @Override
    protected String getTextData() {
        return "关于时间复杂度：\n" +
                "\n" +
                "(1)平方阶(O(n2))排序\n" +
                "各类简单排序:直接插入、直接选择和冒泡排序；\n" +
                "\n" +
                "(2)线性对数阶(O(nlog2n))排序\n" +
                "　　快速排序、堆排序和归并排序；\n" +
                "(3)O(n1+§))排序,§是介于0和1之间的常数。\n" +
                "\n" +
                "       希尔排序\n" +
                "(4)线性阶(O(n))排序\n" +
                "基数排序，此外还有桶、箱排序。\n" +
                "\n" +
                " \n" +
                "\n" +
                "关于稳定性：\n" +
                "\n" +
                "稳定的排序算法：冒泡排序、插入排序、归并排序和基数排序\n" +
                "\n" +
                "不是稳定的排序算法：选择排序、快速排序、希尔排序、堆排序" +
                "分类\n" +
                "1）插入排序（直接插入排序、希尔排序）\n" +
                "2）交换排序（冒泡排序、快速排序）\n" +
                "3）选择排序（直接选择排序、堆排序）\n" +
                "4）归并排序\n" +
                "5）分配排序（基数排序）\n" +
                "所需辅助空间最多：归并排序\n" +
                "所需辅助空间最少：堆排序\n" +
                "平均速度最快：快速排序\n" +
                "不稳定：快速排序，希尔排序，堆排序。\n";
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
