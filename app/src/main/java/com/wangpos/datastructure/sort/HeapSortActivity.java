package com.wangpos.datastructure.sort;

import android.app.ActionBar;
import android.util.Log;

import com.wangpos.datastructure.R;
import com.wangpos.datastructure.core.BaseActivity;

import java.util.Arrays;

/**
 * Created by qiyue on 2017/11/22.
 */

public class HeapSortActivity extends BaseActivity {
     int arr[] = { 1, 3, 4, 5, 2, 6, 9, 7, 8, 0 };
    @Override
    protected void initData() {
        addImage("第一次比较，找到最后一个节点的父节,根结点编号为0开始，第i个元素，通过公式(i-1)/2得到他的父亲节点", R.drawable.dui1);
        addImage("第二次比较", R.drawable.dui2);
        addImage("第三次比较", R.drawable.dui3);
        addImage("第四次比较", R.drawable.dui4);
        addImage("第五次比较", R.drawable.dui5);
        addImage("一个堆结构数据", R.drawable.dui6);



//        Log.i("info","排序后="+Arrays.toString(arr));


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
        return Arrays.toString(heapSort(arr));
    }

    @Override
    protected String getTimeData() {
        return "O(nlogn)";
    }

    @Override
    protected String getSpaceTimeData() {
        return "O(1)";
    }

    @Override
    protected String getWendingXingData() {
        return "不稳定";
    }

    @Override
    protected String getSummaryData() {
        return " 堆排序，这里的堆不是java中的堆，是一种数据结构，简单的理解就是一个完全二叉树\n" +
                " 堆的定义\n" +
                "n个元素的序列{k1，k2，…,kn}当且仅当满足下列关系之一时，称之为堆。\n" +
                "\n" +
                "　　情形1：ki <= k2i 且ki <= k2i+1 （最小化堆或小顶堆）\n" +
                "\n" +
                "　　情形2：ki >= k2i 且ki >= k2i+1 （最大化堆或大顶堆）\n" +
                "\n" +
                "若将和此序列对应的一维数组（即以一维数组作此序列的存储结构）看成是一个完全二叉树，" +
                "则堆的含义表明，完全二叉树中所有非终端结点的值均不大于（或不小于）其左、右孩子结点的值。" +
                "\n" +
                "\n" +
                "" +
                "堆的存储\n" +
                "" +
                "<<一般用数组来表示堆，若根结点存在序号0处， i结点的父结点下标就为(i-1)/2。i结点的左右子结点下标分别为2*i+1和2*i+2。>>\n" +
                "" +
                "";


    }


    /**
     *
     * 一般用数组来表示堆，若根结点存在序号0处， i结点的父结点下标就为(i-1)/2。i结点的左右子结点下标分别为2*i+1和2*i+2。
     *
     *
     * 调整堆，也可以叫构建堆，准确的说是构建
     * @param array 数组 1, 3, 4, 5, 2, 6, 9, 7, 8, 0
     * @param parent
     * @param length
     */
    public void HeapAdjust(int[] array, int parent, int length) {
        int temp = array[parent]; // temp保存当前父节点
        int child = 2 * parent + 1; // 先获得左孩子
        while (child < length) {
            /**
             * 两个孩子比较大小
             */
            // 如果有右孩子结点，并且右孩子结点的值大于左孩子结点，则选取右孩子结点
            if (child + 1 < length && array[child] < array[child + 1]) {
                child++;
            }
            /**
             * 孩子中最大的和父亲比较大小，如果父亲大，跳出循环，否则把最大值和父亲交换
             */
            // 如果父结点的值已经大于孩子结点的值，则直接结束
            if (temp >= array[child]) {
                break;
            }
            // 把孩子结点的值赋给父结点
            array[parent] = array[child];
            // 选取孩子结点的左孩子结点,继续向下筛选

            /**
             * 再采用从后构建堆的时候，这里就会没用
             */
            parent = child;
            child = 2 * child + 1;
            Log.i("info","temp="+temp +"HeapAdjust="+Arrays.toString(array));
        }

        array[parent] = temp;

    }


    public int[] heapSort(int[] list) {
        /**
         * 从第一个叶子节点的父节点开始构建减少很多比较
         */
        // 循环建立初始堆
        for (int i = (list.length-1) / 2; i >= 0; i--) {
            HeapAdjust(list, i, list.length);
        }
        //[9, 8, 6, 7, 2, 1, 4, 3, 5, 0]
        //[0, 8, 6, 7, 2, 1, 4, 3, 5, 9]
        Log.i("info", "111111111"+Arrays.toString(list));
        // 进行n-1次循环，完成排序
        for (int i = list.length - 1; i > list.length - 2; i--) {
            // 最后一个元素和第一元素进行交换
            int temp = list[i];
            list[i] = list[0];
            list[0] = temp;
            // 筛选 R[0] 结点，得到i-1个结点的堆
            HeapAdjust(list, 0, i);


            System.out.format("第 %d 趟: \t", list.length - i );

            Log.i("info","第"+(list.length - i)+"趟list="+Arrays.toString(list));
//            printPart(list, 0, list.length - 1);

        }

        return list;



    }



}
