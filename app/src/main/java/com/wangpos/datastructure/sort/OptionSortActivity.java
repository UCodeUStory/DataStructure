package com.wangpos.datastructure.sort;

import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;

import java.util.Arrays;

/**
 * Created by qiyue on 2017/11/21.
 */

public class OptionSortActivity extends BaseActivity {
    int arr[] = {23, 12, 13, 44, 65, 26, 17, 38, 59};
    @Override
    protected void initData() {

        addItem(new CodeBean("选择排序",selectSortStr));
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
        return Arrays.toString(selectSort(arr));
    }

    @Override
    protected String getTimeData() {
        return "O(n^2)";
    }

    @Override
    protected String getSpaceTimeData() {
        return "O(1)";
    }

    @Override
    protected String getWendingXingData() {
        return "稳定";
    }

    @Override
    protected String getSummaryData() {
        return "每一趟从待排序的记录中选出最小的元素，顺序放在已排好序的序列最后，直到全部记录排序完毕,此排序就是对冒泡的优化，不必每次都进行交换";
    }

    private static final String selectSortStr = "public void selectSort(int arr[]){\n" +
            "        for(int i = 0; i < arr.length - 1; i++) {// 做第i趟排序\n" +
            "            int k = i;\n" +
            "            for(int j = k + 1; j < arr.length; j++){// 选最小的记录\n" +
            "                if(arr[j] < arr[k]){\n" +
            "                    k = j; //记下目前找到的最小值所在的位置\n" +
            "                }\n" +
            "            }\n" +
            "            //在内层循环结束，也就是找到本轮循环的最小的数以后，再进行交换\n" +
            "            if(i != k){  //交换a[i]和a[k]\n" +
            "                int temp = arr[i];\n" +
            "                arr[i] = arr[k];\n" +
            "                arr[k] = temp;\n" +
            "            }\n" +
            "        }\n" +
            "    }";

    public int[] selectSort(int arr[]){
        for(int i = 0; i < arr.length - 1; i++) {// 做第i趟排序
            int k = i;
            for(int j = k + 1; j < arr.length; j++){// 选最小的记录
                if(arr[j] < arr[k]){
                    k = j; //记下目前找到的最小值所在的位置
                }
            }
            //在内层循环结束，也就是找到本轮循环的最小的数以后，再进行交换
            if(i != k){  //交换a[i]和a[k]
                int temp = arr[i];
                arr[i] = arr[k];
                arr[k] = temp;
            }
        }
        return arr;
    }
}
