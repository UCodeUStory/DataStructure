package com.wangpos.datastructure.sort;

import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by qiyue on 2017/11/26.
 */

public class SelectIndexDataActivity extends BaseActivity {
    int b[]={9,6,7,3,8,2,4,5};
    @Override
    protected void initData() {

        addItem(new CodeBean("随机选择算法(快速选择法)",randomizedSelectCode));
    }

    @Override
    protected String getTextData() {
        return Arrays.toString(b);
    }

    @Override
    protected int getImageData() {
        return 0;
    }

    @Override
    protected String getResultData() {
        return "result = "+randomizedSelect(b,0,b.length-1,3);
    }

    @Override
    protected String getTimeData() {
        return "O(n)";
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
        return " 从一个序列里面选择第k大的数在没有学习算法导论之前我想最通用的想法是给这个数组排序，" +
                "然后按照排序结果返回第k大的数值。如果使用排序方法来做的话时间复杂度肯定至少为O（nlgn）。\n" +
                "\n" +
                "问题是从序列中选择第k大的数完全没有必要来排序，可以采用分治法的思想解决这个问题。Randomize select 算法的期望时间复杂度可以达到O（n），" +
                "这正是这个算法的迷人之处" +
                "" +
                "\n" +
                "核心方法partition 查找中间点，判断k是否等于这个中间点，不等于就判断在哪个区间，然后继续在这个区间找" +
                "";
    }


    /**
     * 寻找中间点
     * @param a
     * @param p 开始位置（因为同一个数组所以要表明每段开始位置）
     * @param r 长度
     * @return 中间点
     */
    public static int partition(int[] a, int p, int r) {
        /**以最后一个元素作为基准点 来与其他比较**/
        int x = a[r];
        /**
         * i记录比x小的位置，默认是前一个，如果找到一个加1，并将找到这个当前i位置互换
         * 如果每次都找到比最后一个小，那相当于每次自己和自己交换
         * 如果找了多次才找到与i位置交换也是正确的，因为交换位置一定是通过判断过不符合条件，应该放在后面
         */
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (a[j] <= x) {
                i = i + 1;   // 0 和 0 交换
                swap(a, i, j);
                System.out.println("交换=" + "i=" + i + Arrays.toString(a));
            }else{
                System.out.println("未交换=" + "i=" + i + Arrays.toString(a));
            }

        }
        swap(a, i + 1, r);
        System.out.println("结果=" + "i=" + i + Arrays.toString(a));
        return i + 1;
    }

    /**
     * 随机分割
     * @param a 数组
     * @param p 分割的开始位置（我们每次只对p后面数据进行分割 ）
     * @param r 分割的结束位置（我们每次只对 p到 r进行分割，找出中间点）
     * @return
     */
    private static int randomizedPartition(int[] a, int p, int r) {
        java.util.Random random = new java.util.Random();
        //因为r是数组最大访问游标，比length少1，所以要加一
        //产生指定范围内的随机数作为基准位
        int i = Math.abs(random.nextInt() % (r - p + 1) + p);
        //这个基准数移动和交换，目的是更好的进行分割排序，其他方法当然也可以，就是比较次数多
        swap(a, i, r);
        return partition(a, p, r);
    }


    /**
     * @param a 数组
     * @param p 数组（起始分割点）
     * @param r 数组 (结尾分割点）
     * @param i 需要求第几小的元素
     * @return
     */
    public static int randomizedSelect(int[] a, int p, int r, int i) {
        if (p == r) {
            return a[p];//这种情况就是数组内只有一个元素
        }
        // 查找p r中间点 q
        int q = randomizedPartition(a, p, r);
        // 这里都是基于游标的，因为k是第几个，所以要加 1
        int k = q - p + 1;//拿到上一句中作为枢纽的数是第几小的数
        if (i == k) {
            //找到
            return a[q];
        } else if (i < k) {
            // 在左边区域找
            return randomizedSelect(a, p, q - 1, i);
        } else {
            // 在右边区域找
            return randomizedSelect(a, q + 1, r, i - k);
        }

    }

    /**
     * 交换
     *
     * @param a
     * @param i
     * @param j
     */
    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }



    private static final String randomizedSelectCode = "\n" +
            "    /**\n" +
            "     * 寻找中间点\n" +
            "     * @param a\n" +
            "     * @param p 开始位置（因为同一个数组所以要表明每段开始位置）\n" +
            "     * @param r 长度\n" +
            "     * @return 中间点\n" +
            "     */\n" +
            "    public static int partition(int[] a, int p, int r) {\n" +
            "        /**以最后一个元素作为基准点 来与其他比较**/\n" +
            "        int x = a[r];\n" +
            "        /**\n" +
            "         * i记录比x小的位置，默认是前一个，如果找到一个加1，并将找到这个当前i位置互换\n" +
            "         * 如果每次都找到比最后一个小，那相当于每次自己和自己交换\n" +
            "         * 如果找了多次才找到与i位置交换也是正确的，因为交换位置一定是通过判断过不符合条件，应该放在后面\n" +
            "         */\n" +
            "        int i = p - 1;\n" +
            "        for (int j = p; j < r; j++) {\n" +
            "            if (a[j] <= x) {\n" +
            "                i = i + 1;   // 0 和 0 交换\n" +
            "                swap(a, i, j);\n" +
            "                System.out.println(\"交换=\" + \"i=\" + i + Arrays.toString(a));\n" +
            "            }else{\n" +
            "                System.out.println(\"未交换=\" + \"i=\" + i + Arrays.toString(a));\n" +
            "            }\n" +
            "\n" +
            "        }\n" +
            "        swap(a, i + 1, r);\n" +
            "        System.out.println(\"结果=\" + \"i=\" + i + Arrays.toString(a));\n" +
            "        return i + 1;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 随机分割\n" +
            "     * @param a 数组\n" +
            "     * @param p 分割的开始位置（我们每次只对p后面数据进行分割 ）\n" +
            "     * @param r 分割的结束位置（我们每次只对 p到 r进行分割，找出中间点）\n" +
            "     * @return\n" +
            "     */\n" +
            "    private static int randomizedPartition(int[] a, int p, int r) {\n" +
            "        java.util.Random random = new java.util.Random();\n" +
            "        //因为r是数组最大访问游标，比length少1，所以要加一\n" +
            "        //产生指定范围内的随机数作为基准位\n" +
            "        int i = Math.abs(random.nextInt() % (r - p + 1) + p);\n" +
            "        //这个基准数移动和交换，目的是更好的进行分割排序，其他方法当然也可以，就是比较次数多\n" +
            "        swap(a, i, r);\n" +
            "        return partition(a, p, r);\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    /**\n" +
            "     * @param a 数组\n" +
            "     * @param p 数组（起始分割点）\n" +
            "     * @param r 数组 (结尾分割点）\n" +
            "     * @param i 需要求第几小的元素\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static int randomizedSelect(int[] a, int p, int r, int i) {\n" +
            "        if (p == r) {\n" +
            "            return a[p];//这种情况就是数组内只有一个元素\n" +
            "        }\n" +
            "        // 查找p r中间点 q\n" +
            "        int q = randomizedPartition(a, p, r);\n" +
            "        // 这里都是基于游标的，因为k是第几个，所以要加 1\n" +
            "        int k = q - p + 1;//拿到上一句中作为枢纽的数是第几小的数\n" +
            "        if (i == k) {\n" +
            "            //找到\n" +
            "            return a[q];\n" +
            "        } else if (i < k) {\n" +
            "            // 在左边区域找\n" +
            "            return randomizedSelect(a, p, q - 1, i);\n" +
            "        } else {\n" +
            "            // 在右边区域找\n" +
            "            return randomizedSelect(a, q + 1, r, i - k);\n" +
            "        }\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 交换\n" +
            "     *\n" +
            "     * @param a\n" +
            "     * @param i\n" +
            "     * @param j\n" +
            "     */\n" +
            "    private static void swap(int[] a, int i, int j) {\n" +
            "        int temp = a[i];\n" +
            "        a[i] = a[j];\n" +
            "        a[j] = temp;\n" +
            "    }";

}
