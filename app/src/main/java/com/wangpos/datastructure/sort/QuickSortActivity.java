package com.wangpos.datastructure.sort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wangpos.datastructure.R;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wangpos.datastructure.R;
import com.wangpos.datastructure.core.BaseActivity;

import java.util.Arrays;

import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

public class QuickSortActivity extends BaseActivity {


    int arr[] = {23, 12, 13, 44, 65, 26, 17, 38, 59};


    /**
     * 记录一个23，让出自己位置，将小得放进来
     *
     * 23, 12, 13, 44, 65, 26, 17, 38, 59
     *
     *  17, 12, 13, 44, 65, 26, 17, 38, 59
     *
     *  17, 12, 13, 44, 65, 26, 44, 38, 59
     *
     *  一趟以后得 17, 12, 13, 23, 65, 26, 44, 38, 59
     *
     *                  一趟=[17, 12, 13, 44, 65, 26, 44, 38, 59]
     I/info    (27284): 一趟=[17, 12, 13, 44, 65, 26, 44, 38, 59]
     I/info    (27284): 一趟=[13, 12, 13, 23, 65, 26, 44, 38, 59]
     I/info    (27284): 一趟=[12, 12, 17, 23, 65, 26, 44, 38, 59]
     I/info    (27284): 一趟=[12, 13, 17, 23, 59, 26, 44, 38, 59]
     I/info    (27284): 一趟=[12, 13, 17, 23, 38, 26, 44, 38, 65]
     I/info    (27284): 一趟=[12, 13, 17, 23, 26, 26, 44, 59, 65]

     */
    @Override
    protected void initData() {
        setTitleText("快速排序");

    }

    @Override
    protected String getTextData() {
        return Arrays.toString(arr);
    }

    @Override
    protected int getImageData() {
        return 0;
    }

    @Override
    protected String getResultData() {
        sort(arr,0,arr.length-1);
        return Arrays.toString(arr);
    }

    @Override
    protected String getTimeData() {
        return "O(nlogn)";
    }

    @Override
    protected String getSpaceTimeData() {
        return "";
    }

    @Override
    protected String getWendingXingData() {
        return "不稳定";
    }

    @Override
    protected String getSummaryData() {
        return "快速排序（Quicksort）是对冒泡排序的一种改进。\n" +
                "快速排序由C. A. R. Hoare在1962年提出。它的基本思想是：通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小" +
                "，然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。";
    }



    private static int sortUnit(int[] array, int low, int high)
    {
        int key = array[low];//选取第一个为基准数

        while (low < high)
        {
                /*从后向前搜索比key小的值*/
            while (array[high] >= key && high > low)
                --high;
                /*比key小的放左边*/
            array[low] = array[high];
                /*从前向后搜索比key大的值，比key大的放右边*/
            while (array[low] <= key && high > low)
                ++low;
                /*比key大的放右边*/
            array[high] = array[low];//




            Log.i("info","一趟="+Arrays.toString(array));
//            Log.i("info","一趟="+"low="+low +"high="+high);
        }
            /*左边都比key小，右边都比key大。//将key放在游标当前位置。
            //此时low等于high */
        /**
         * 相遇位置，
         * 一开始meetPosition 等于low
         */
        int meetPosition = low = high;

        array[meetPosition] = key;

        return meetPosition;
    }
    /**快速排序
     *@paramarry
     *@return */
    public static void sort(int[] array, int low, int high)
    {
        if (low >= high)
            return;
            /*完成一次单元排序*/
        int index = sortUnit(array, low, high);
            /*对左边单元进行排序*/
//        sort(array, low, index - 1);
//            /*对右边单元进行排序*/
//        sort(array, index + 1, high);
    }


}
