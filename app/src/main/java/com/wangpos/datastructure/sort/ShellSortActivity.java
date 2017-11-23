package com.wangpos.datastructure.sort;

import android.widget.TextView;

import com.wangpos.datastructure.R;
import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;

import java.util.Arrays;

/**
 * Created by qiyue on 2017/11/23.
 */

public class ShellSortActivity extends BaseActivity {

    int array[] = {82 ,31 ,29 ,71, 72, 42, 64, 5,110};
    @Override
    protected void initData() {
        setTitleText("希尔排序");
        addItem(new CodeBean("希尔排序",shellSortCode));

    }

    @Override
    protected String getTextData() {
        return Arrays.toString(array);
    }

    @Override
    protected int getImageData() {
        return 0;
    }

    @Override
    protected String getResultData() {
        shellsort(array);
        return Arrays.toString(array);
    }

    @Override
    protected String getTimeData() {
        return "O(n^2)";
    }

    @Override
    protected String getSpaceTimeData() {
        return null;
    }

    @Override
    protected String getWendingXingData() {
        return "不稳定";
    }

    @Override
    protected String getSummaryData() {
        return "希尔排序就是对直接插入排序的一个优化。比如有这么一种情况：对一个无序数组进行从小到大的排序，但是数组的最后一个位置的数是最小的，我们要把它挪到第一个位置，其他位置的都要往后移动，要是这个数组非常大，那么直接插入排序的开销就非常大。\n" +
                "\n" +
                "    现在有一个array,希尔排序就是设定一个增量incrementNum(0<incrementNum<array.length)。\n" +
                "\n" +
                "    先从array[0]开始，以incrementNum为增量的进行直接插入排序，直到数组末尾，然后从array[1]开始重复：以incrementNum为增量的进行直接插入排序; 然后从array[1]开始重复......一直到array[n]。\n" +
                "\n" +
                "    然后取一个小于上一步增量的新的增量（比如设置为incrementNum/2）,对前一个步骤的结果array进行遍历，直接插入排序....\n" +
                "\n" +
                "    再取小于上一步增量的新的增量，重复进行：遍历，直接插入排序\n" +
                "\n" +
                "    直到新的增量小于1之后再退出循环\n" +
                "\n" +
                "    步骤1：比如现在有数组{82 ,31 ,29 ,71, 72, 42, 64, 5,110}   第一次取增量设置为array.length/2 = 4    先从82开始以4为增量遍历直到末尾，得到（82,42） 排序得到{42 ,31 ,29 ,71, 72, 82, 64, 5,110}。 然后从第二个数31开始重复上一个步骤，得到（31,64） 排序得到{42 ,31 ,29 ,71, 72, 82, 64, 5,110}.......   以4为增量的遍历完数组之后，得到的结果是{42 ,31,5,71,72,82,64,29,110}\n" +
                "\n" +
                "   然后重新区增量,这儿设定为incrementNum/2 = 2，对{42 ,31,5,71,72,82,64,29,110}重复步骤1。  完事之后，在取新的增量，重复步骤1。 直到取到的增量小于1，退出循环。";
    }



    private static final String shellSortCode = "/**\n" +
            "     *\n" +
            "     * @param arrays 需要排序的序列\n" +
            "     */\n" +
            "    public static void shellsort(int[] arrays) {\n" +
            "        if (arrays == null || arrays.length <= 1) {\n" +
            "            return;\n" +
            "        }\n" +
            "        //增量\n" +
            "        int incrementNum = arrays.length / 2;\n" +
            "        while (incrementNum >= 1) {\n" +
            "            for (int i = 0; i < arrays.length; i++) {\n" +
            "                //进行插入排序\n" +
            "                for (int j = i; j < arrays.length - incrementNum; j = j + incrementNum) {\n" +
            "                    if (arrays[j] > arrays[j + incrementNum]) {\n" +
            "                        int temple = arrays[j];\n" +
            "                        arrays[j] = arrays[j + incrementNum];\n" +
            "                        arrays[j + incrementNum] = temple;\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "            //设置新的增量\n" +
            "            incrementNum = incrementNum / 2;\n" +
            "        }\n" +
            "    }";
    /**
     * 希尔排序
     *
     * @param arrays 需要排序的序列
     */
    public static void shellsort(int[] arrays) {
        if (arrays == null || arrays.length <= 1) {
            return;
        }
        //增量
        int incrementNum = arrays.length / 2;
        while (incrementNum >= 1) {
            for (int i = 0; i < arrays.length; i++) {
                //进行插入排序
                for (int j = i; j < arrays.length - incrementNum; j = j + incrementNum) {
                    if (arrays[j] > arrays[j + incrementNum]) {
                        int temple = arrays[j];
                        arrays[j] = arrays[j + incrementNum];
                        arrays[j + incrementNum] = temple;
                    }
                }
            }
            //设置新的增量
            incrementNum = incrementNum / 2;
        }
    }



}
