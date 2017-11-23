package com.wangpos.datastructure.sort;

import android.util.Log;

import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;

import java.util.Arrays;

/**
 * Created by qiyue on 2017/11/23.
 */

public class MergeSortActivity extends BaseActivity {

    int arrays[] = {4,7,5,3,2,8,6,1};
    @Override
    protected void initData() {
        setTitleText("归并排序");
        addItem(new CodeBean("归并排序",mergeCode));

    }

    @Override
    protected String getTextData() {
        return Arrays.toString(arrays);
    }

    @Override
    protected int getImageData() {
        return 0;
    }

    @Override
    protected String getResultData() {
        MergeSort(arrays);
        return Arrays.toString(arrays);
    }

    @Override
    protected String getTimeData() {
        return "对长度为n的文件，需进行 趟二路归并，每趟归并的时间为O(n)，故其时间复杂度无论是在最好情况下还是在最坏情况下均是O(nlgn)。";
    }

    @Override
    protected String getSpaceTimeData() {
        return "需要一个辅助向量来暂存两有序子文件归并的结果，故其辅助空间复杂度为O(n)，显然它不是就地排序。";
    }

    @Override
    protected String getWendingXingData() {
        return "稳定";
    }

    @Override
    protected String getSummaryData() {
        return "归并排序 \n" +
                "　　 归并排序 (merge sort) 是一类与插入排序、交换排序、选择排序不同的另一种排序方法。归并的含义是将两个或两个以上的有序表合并成一个新的有序表。归并排序有多路归并排序、两路归并排序 , 可用于内排序，也可以用于外排序。这里仅对内排序的两路归并方法进行讨论。 \n" +
                "1.两路归并排序算法思路\n" +
                "    ①把 n 个记录看成 n 个长度为1的有序子表；\n" +
                "    ②进行两两归并使记录关键字有序，得到 n/2 个长度为 2 的有序子表； \n" +
                "    ③重复第②步直到所有记录归并成一个长度为 n 的有序表为止。";
    }





    private static void MergeSort(int[] a) {
        Log.i("info","开始排序");
        Sort(a, 0, a.length - 1);
    }

    private static void Sort(int[] a, int left, int right) {
        if(left>=right)
            return;

        int mid = (left + right) / 2;
        //二路归并排序里面有两个Sort，多路归并排序里面写多个Sort就可以了
        Sort(a, left, mid);
        Sort(a, mid + 1, right);
        merge(a, left, mid, right);

    }


    private static void merge(int[] a, int left, int mid, int right) {

        int[] tmp = new int[a.length];
        int rightIndex = mid + 1;
        int tmpIndex = left;
        int cIndex=left;
        /**
         * 左右逐个比较大小
         */
        while(left <=mid && rightIndex <= right) {
            if (a[left] <= a[rightIndex])
                tmp[tmpIndex++] = a[left++];
            else
                tmp[tmpIndex++] = a[rightIndex++];
        }
        /** 比较过程有可能左边剩余，将左边剩余的归并 */
        while (left <=mid) {
            tmp[tmpIndex++] = a[left++];
        }
        /** 比较过程有可能右边剩余，将右边剩余的归并 */
        while ( rightIndex <= right ) {
            tmp[tmpIndex++] = a[rightIndex++];
        }
//        System.out.println("第"+(++number)+"趟排序:\t");
        /** 从临时数组拷贝到原数组 **/
        while(cIndex<=right){
            a[cIndex]=tmp[cIndex];
            //输出中间归并排序结果
            System.out.print(a[cIndex]+"\t");
            cIndex++;
        }
        System.out.println();
    }



    private static final String mergeCode = "        private static void MergeSort(int[] a) {\n" +
            "        Log.i(\"info\",\"开始排序\");\n" +
            "        Sort(a, 0, a.length - 1);\n" +
            "    }\n" +
            "\n" +
            "    private static void Sort(int[] a, int left, int right) {\n" +
            "        if(left>=right)\n" +
            "            return;\n" +
            "\n" +
            "        int mid = (left + right) / 2;\n" +
            "        //二路归并排序里面有两个Sort，多路归并排序里面写多个Sort就可以了\n" +
            "        Sort(a, left, mid);\n" +
            "        Sort(a, mid + 1, right);\n" +
            "        merge(a, left, mid, right);\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    private static void merge(int[] a, int left, int mid, int right) {\n" +
            "\n" +
            "        int[] tmp = new int[a.length];\n" +
            "        int rightIndex = mid + 1;\n" +
            "        int tmpIndex = left;\n" +
            "        int cIndex=left;\n" +
            "        /**\n" +
            "         * 左右逐个比较大小\n" +
            "         */\n" +
            "        while(left <=mid && rightIndex <= right) {\n" +
            "            if (a[left] <= a[rightIndex])\n" +
            "                tmp[tmpIndex++] = a[left++];\n" +
            "            else\n" +
            "                tmp[tmpIndex++] = a[rightIndex++];\n" +
            "        }\n" +
            "        /** 比较过程有可能左边剩余，将左边剩余的归并 */\n" +
            "        while (left <=mid) {\n" +
            "            tmp[tmpIndex++] = a[left++];\n" +
            "        }\n" +
            "        /** 比较过程有可能右边剩余，将右边剩余的归并 */\n" +
            "        while ( rightIndex <= right ) {\n" +
            "            tmp[tmpIndex++] = a[rightIndex++];\n" +
            "        }\n" +
            "//        System.out.println(\"第\"+(++number)+\"趟排序:\\t\");\n" +
            "        /** 从临时数组拷贝到原数组 **/\n" +
            "        while(cIndex<=right){\n" +
            "            a[cIndex]=tmp[cIndex];\n" +
            "            //输出中间归并排序结果\n" +
            "            System.out.print(a[cIndex]+\"\\t\");\n" +
            "            cIndex++;\n" +
            "        }\n" +
            "        System.out.println();\n" +
            "    }";
}
