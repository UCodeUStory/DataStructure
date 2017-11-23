package com.wangpos.datastructure.sort;

import android.app.ActionBar;
import android.util.Log;

import com.wangpos.datastructure.R;
import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;

import java.util.Arrays;

/**
 * Created by qiyue on 2017/11/22.
 */

public class HeapSortActivity extends BaseActivity {
     int arr[] = { 1, 3, 4, 5, 2, 6, 9, 7, 8, 0 };
    @Override
    protected void initData() {
        addItem(new CodeBean("堆的构建建和排序",heapSortCode));
        addItem(new CodeBean("堆的递归算法",heapAdjustRecursionCode));
        addItem(new CodeBean("堆的非递归算法",heapAdjustIteration));
        addItem(new CodeBean("堆的非递归算法优化",heapAdjustIterationGood));
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
     *
     * 交换都是最后一步， while循环内只是覆盖，上移，每次找出自节点最大最大值给parent赋值，自身先不变，
     * 下次循环自身变为parent，找出孩子中最大值赋值给自身
     *
     * 循环终止条件 是给定一个parent点,大于其孩子 小于 parent 就会终止，
     *
     * @param array 数组 1, 3, 4, 5, 2, 6, 9, 7, 8, 0
     * @param parent
     * @param length
     */
    public void HeapAdjust(int[] array, int parent, int length) {
        int temp = array[parent]; // temp保存当前父节点   第一次始终是0
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

        /**
         * 将本次父节点赋值给最后一个parent ，parent可能是自己
         */
        array[parent] = temp;

    }


    private static final String heapSortCode = "/**\n" +
            "     * 建立堆的过程必须从第一个叶子节点的父节点开始构建，原理就是，我们必须要建立小的堆，逐渐扩大，\n" +
            "     *\n" +
            "     * 建立堆算法解析\n" +
            "     *\n" +
            "     * 第一趟按要求，从倒数第二层开始，只有两层，从第一个叶子节点的父节点开始，判断是否孩子中最大的，如果不是，则和孩子中最大值交换\n" +
            "     * 第二趟指针前移动，如果还是在倒数第二层和第一趟算法一样\n" +
            "     * 经过几次指针移动后，到了倒数第三层，此时是三层结构，也判断是否孩子中最大的，如果不是，把最大值拿过来，但最小值不要着急给出去，\n" +
            "     * 有可能这个值小的离谱，所以继续顺着这个最大值的分支，找他的孩子比较，如果验证已经大于孩子就停止，否者继续向下层比较\n" +
            "     *\n" +
            "     * 第一趟的算法可以写成这样：\n" +
            "     *\n" +
            "     * int temp = array[parent];\n" +
            "     * int child = 2 * parent + 1;\n" +
            "     * if (child + 1 < length && array[child] < array[child + 1]) {\n" +
            "     child++;\n" +
            "     }\n" +
            "\n" +
            "     if (temp >= array[child]) {\n" +
            "\n" +
            "     }else{\n" +
            "     array[parent] = array[child];\n" +
            "     }\n" +
            "\n" +
            "     再倒数三层以上的算法（ 多出了向下查找判断）：\n" +
            "     int temp = array[parent];\n" +
            "     int child = 2 * parent + 1;\n" +
            "     if (child + 1 < length && array[child] < array[child + 1]) {\n" +
            "     child++;\n" +
            "     }\n" +
            "\n" +
            "     if (temp >= array[child]) {\n" +
            "\n" +
            "     }else{\n" +
            "     array[parent] = array[child];\n" +
            "     }\n" +
            "\n" +
            "     //相当于递归\n" +
            "     parent = child;\n" +
            "     child = 2 * parent + 1;\n" +
            "\n" +
            "     *\n" +
            "     *\n" +
            "     */\n" +
            "    public int[] heapSort(int[] list) {\n" +
            "\n" +
            "        // 循环建立初始堆\n" +
            "        for (int i = (list.length-1) / 2; i >= 0; i--) {\n" +
            "//            HeapAdjust(list, i, list.length);\n" +
            "//            heapAdjustRecursion(list,i,list.length);\n" +
            "//            heapAdjustIteration(list,i,list.length);\n" +
            "            heapAdjustIterationGood(list,i,list.length);\n" +
            "        }\n" +
            "        //初始数据[9, 8, 6, 7, 2, 1, 4, 3, 5, 0]\n" +
            "\n" +
            "        Log.i(\"info\", \"初始后堆\"+Arrays.toString(list));\n" +
            "        /**\n" +
            "         * 当前顶点和最后一个交换后，第一个数据有可能不符合堆结构，所以我们要找到他的位置，\n" +
            "         * 如果第一次比较如比孩子大，那就不需要更换，否者，将孩子中最大的换到自己位置，此时空缺位置检测放入3合不合适，\n" +
            "         * 也就是继续和这个位子的孩子中最大值比较，如果大于，那就是这个位置\n" +
            "         */\n" +
            "        for (int i = list.length - 1; i > 0; i--) {\n" +
            "            // 最后一个元素和第一元素进行交换\n" +
            "            int temp = list[i];\n" +
            "            list[i] = list[0];\n" +
            "            list[0] = temp;\n" +
            "            //第一次交换后的数据[0, 8, 6, 7, 2, 1, 4, 3, 5, 9]\n" +
            "//            heapAdjustRecursion(list,0,i);\n" +
            "//            heapAdjustIteration(list,0,i);\n" +
            "            heapAdjustIterationGood(list,0,i);\n" +
            "            //第一趟结果[8, 7, 6, 5, 2, 1, 4, 3, 0, 9]\n" +
            "\n" +
            "//            Log.i(\"info\",\"第\"+(list.length - i)+\"趟list=\"+Arrays.toString(list));\n" +
            "\n" +
            "        }\n" +
            "        return list;\n" +
            "\n" +
            "    }";

    /**
     * 建立堆的过程必须从第一个叶子节点的父节点开始构建，原理就是，我们必须要建立小的堆，逐渐扩大，
     *
     * 建立堆算法解析
     *
     * 第一趟按要求，从倒数第二层开始，只有两层，从第一个叶子节点的父节点开始，判断是否孩子中最大的，如果不是，则和孩子中最大值交换
     * 第二趟指针前移动，如果还是在倒数第二层和第一趟算法一样
     * 经过几次指针移动后，到了倒数第三层，此时是三层结构，也判断是否孩子中最大的，如果不是，把最大值拿过来，但最小值不要着急给出去，
     * 有可能这个值小的离谱，所以继续顺着这个最大值的分支，找他的孩子比较，如果验证已经大于孩子就停止，否者继续向下层比较
     *
     * 第一趟的算法可以写成这样：
     *
     * int temp = array[parent];
     * int child = 2 * parent + 1;
     * if (child + 1 < length && array[child] < array[child + 1]) {
     child++;
     }

     if (temp >= array[child]) {

     }else{
     array[parent] = array[child];
     }

     再倒数三层以上的算法（ 多出了向下查找判断）：
     int temp = array[parent];
     int child = 2 * parent + 1;
     if (child + 1 < length && array[child] < array[child + 1]) {
     child++;
     }

     if (temp >= array[child]) {

     }else{
     array[parent] = array[child];
     }

     //相当于递归
     parent = child;
     child = 2 * parent + 1;

     *
     *
     */
    public int[] heapSort(int[] list) {

        // 循环建立初始堆
        for (int i = (list.length-1) / 2; i >= 0; i--) {
//            HeapAdjust(list, i, list.length);
//            heapAdjustRecursion(list,i,list.length);
//            heapAdjustIteration(list,i,list.length);
            heapAdjustIterationGood(list,i,list.length);
        }
        //初始数据[9, 8, 6, 7, 2, 1, 4, 3, 5, 0]

        Log.i("info", "初始后堆"+Arrays.toString(list));
        /**
         * 当前顶点和最后一个交换后，第一个数据有可能不符合堆结构，所以我们要找到他的位置，
         * 如果第一次比较如比孩子大，那就不需要更换，否者，将孩子中最大的换到自己位置，此时空缺位置检测放入3合不合适，
         * 也就是继续和这个位子的孩子中最大值比较，如果大于，那就是这个位置
         */
        for (int i = list.length - 1; i > 0; i--) {
            // 最后一个元素和第一元素进行交换
            int temp = list[i];
            list[i] = list[0];
            list[0] = temp;
            //第一次交换后的数据[0, 8, 6, 7, 2, 1, 4, 3, 5, 9]
//            heapAdjustRecursion(list,0,i);
//            heapAdjustIteration(list,0,i);
            heapAdjustIterationGood(list,0,i);
            //第一趟结果[8, 7, 6, 5, 2, 1, 4, 3, 0, 9]

//            Log.i("info","第"+(list.length - i)+"趟list="+Arrays.toString(list));

        }
        return list;

    }



    private static final String heapAdjustRecursionCode = "/**\n" +
            "     * 递归方法\n" +
            "     * 1.需要额外的方法栈空间\n" +
            "     * 2.每次都需要交换\n" +
            "     */\n" +
            "    public void heapAdjustRecursion(int array[],int parent,int length){\n" +
            "\n" +
            "        int temp = array[parent];\n" +
            "        int child = 2 * parent + 1;\n" +
            "\n" +
            "        if (child >=length){\n" +
            "            Log.i(\"info\",\"child=\"+child+\"heapAdjustRecursion 结束\");\n" +
            "            return;\n" +
            "        }\n" +
            "        if (child + 1 < length && array[child] < array[child + 1]) {\n" +
            "            child++;\n" +
            "        }\n" +
            "\n" +
            "        if (temp >= array[child]) {\n" +
            "\n" +
            "        }else{\n" +
            "            array[parent] = array[child];\n" +
            "            array[child] = temp;\n" +
            "        }\n" +
            "        parent = child;\n" +
            "\n" +
            "        heapAdjustRecursion(array,parent,length);\n" +
            "    }";


    /**
     * 递归方法
     * 1.需要额外的方法栈空间
     * 2.每次都需要交换
     */
    public void heapAdjustRecursion(int array[],int parent,int length){

        int temp = array[parent];
        int child = 2 * parent + 1;

        if (child >=length){
            Log.i("info","child="+child+"heapAdjustRecursion 结束");
            return;
        }
        if (child + 1 < length && array[child] < array[child + 1]) {
            child++;
        }

        if (temp >= array[child]) {

        }else{
            array[parent] = array[child];
            array[child] = temp;
        }
        parent = child;

        heapAdjustRecursion(array,parent,length);
    }


    private static final String heapAdjustIteration = "/**\n" +
            "     * 迭代算法（非递归算法）\n" +
            "     * 每次都交换\n" +
            "     */\n" +
            "    public void heapAdjustIteration(int array[],int parent,int length){\n" +
            "        int temp = array[parent];\n" +
            "        int child = 2 *parent + 1;\n" +
            "\n" +
            "        while(child<length){\n" +
            "            /**选出最大child**/\n" +
            "            if (child + 1<length && array[child]<array[child +1]){\n" +
            "                child ++;\n" +
            "            }\n" +
            "\n" +
            "            if(temp>array[child]){\n" +
            "                Log.i(\"info\",\"heapAdjustIteration结束\");\n" +
            "                break;\n" +
            "            }else{\n" +
            "                array[parent] = array[child];\n" +
            "                array[child] = temp;\n" +
            "                parent = child;\n" +
            "                child = 2 *parent +1;\n" +
            "            }\n" +
            "\n" +
            "        }\n" +
            "\n" +
            "    }";

    /**
     * 迭代算法（非递归算法）
     * 每次都交换
     */
    public void heapAdjustIteration(int array[],int parent,int length){
        int temp = array[parent];
        int child = 2 *parent + 1;

        while(child<length){
            /**选出最大child**/
            if (child + 1<length && array[child]<array[child +1]){
                child ++;
            }

            if(temp>array[child]){
                Log.i("info","heapAdjustIteration结束");
                break;
            }else{
                array[parent] = array[child];
                array[child] = temp;
                parent = child;
                child = 2 *parent +1;
            }

        }

    }


    private static final String heapAdjustIterationGood = " /**\n" +
            "     * 迭代算法（优化版）（非递归算法）\n" +
            "     * 赋值不交换，最后一次再交换，减少一般的交换次数，也就是确定最终位置（和选择排序一样）\n" +
            "     */\n" +
            "    public void heapAdjustIterationGood(int array[],int parent,int length){\n" +
            "        int temp = array[parent];\n" +
            "        int child = 2 *parent + 1;\n" +
            "\n" +
            "        while(child<length){\n" +
            "            /**选出最大child**/\n" +
            "            if (child + 1<length && array[child]<array[child +1]){\n" +
            "                child ++;\n" +
            "            }\n" +
            "\n" +
            "            if(temp>array[child]){\n" +
            "                Log.i(\"info\",\"heapAdjustIteration结束\");\n" +
            "                break;\n" +
            "            }else{\n" +
            "                array[parent] = array[child];\n" +
            "\n" +
            "                parent = child;\n" +
            "                child = 2 *parent +1;\n" +
            "            }\n" +
            "\n" +
            "        }\n" +
            "        //指向最后一个，child 可能指向没有\n" +
            "        array[parent] = temp;\n" +
            "//        array[child] = temp;\n" +
            "\n" +
            "    }";

    /**
     * 迭代算法（优化版）（非递归算法）
     * 赋值不交换，最后一次再交换，减少一般的交换次数，也就是确定最终位置（和选择排序一样）
     */
    public void heapAdjustIterationGood(int array[],int parent,int length){
        int temp = array[parent];
        int child = 2 *parent + 1;

        while(child<length){
            /**选出最大child**/
            if (child + 1<length && array[child]<array[child +1]){
                child ++;
            }

            if(temp>array[child]){
                Log.i("info","heapAdjustIteration结束");
                break;
            }else{
                array[parent] = array[child];

                parent = child;
                child = 2 *parent +1;
            }

        }
        //指向最后一个，child 可能指向没有
        array[parent] = temp;
//        array[child] = temp;

    }



}
