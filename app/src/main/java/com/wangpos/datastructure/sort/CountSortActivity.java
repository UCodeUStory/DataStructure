package com.wangpos.datastructure.sort;

import android.util.Log;

import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;

import java.util.Arrays;

/**
 * Created by qiyue on 2017/11/24.
 */

public class CountSortActivity extends BaseActivity {

    int a[] = {100, 93, 97, 92, 96, 99, 92, 89, 93, 97, 90, 94, 92, 95};

    @Override
    protected void initData() {
        setTitleText("计数排序");
        addItem(new CodeBean("计数排序",countSortStr));
    }

    @Override
    protected String getTextData() {
        return Arrays.toString(a);
    }

    @Override
    protected int getImageData() {
        return 0;
    }

    @Override
    protected String getResultData() {
        return Arrays.toString(countSort(a));
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
        return "计数排序是一个非基于比较的排序算法，该算法于1954年由 Harold H. Seward 提出。" +
                "它的优势在于在对一定范围内的整数排序时，它的复杂度为Ο(n+k)（其中k是整数的范围），快于任何比较排序算法。" +
                " 当然这是一种牺牲空间换取时间的做法，而且当O(k)>O(n*log(n))的时候其效率反而不如基于比较的排序" +
                "（基于比较的排序的时间复杂度在理论上的下限是O(n*log(n)), 如归并排序，堆排序）" +
                "" +
                "" +
                "k 是集合中最大数与最小数的差值" +
                "" +
                "排序步骤\n" +
                "1 需要定义一个输出b和a一样空间大小的辅助数组\n" +
                "2 定义一个c大小是k这样的一个数组，c[0]表示最小元素位置，c[k-1]表示元素最大位置，" +
                "里面的值对应的是他们出现的次数\n" +
                "3 确定自己位置，通过确认c中每一个前面出现次数和自身只和，现在每个位置存储的是自身位置的最大值，如果，这个位置相同数很多，" +
                "从这个最大值 向前排列" +
                "\n"
                +"";
    }



    public int[] countSort(int[] a) {
        int b[] = new int[a.length];
        int max = a[0], min = a[0];
        for (int i : a) {
            if (i > max) {
                max = i;
            }
            if (i < min) {
                min = i;
            }
        }//这里k的大小是要排序的数组中，元素大小的极值差+1
        int k = max - min + 1;


        int c[] = new int[k];  // 100 - 89 = 11 + 1=12，
        for (int i = 0; i < a.length; ++i) {
            /**
             * 每一个数减最小值得是 0 到 K范围
             * c数组默认初始都为0，这里计算
             */
            c[a[i] - min] += 1;//优化过的地方，减小了数组c的大小
        }
        print(c);//[1, 1, 0, 3, 2, 1, 1, 1, 2, 0, 1, 1]  c 里面第一位对应89 ,最后一位对应100，里面的值对应的是个数
        for (int i = 1; i < c.length; ++i) {
            //确定每个位置比他下的数量，也就是一次加上前一个，
            // 所以现在c存储的值,是他的位置，应该说是他的最大位置（ 比如前面又9 个元素，这个值是15，说明， 15 14 13 12 11 10 都是我的位置）
            c[i] = c[i] + c[i - 1];
        }
        print(c);
        for (int i = a.length - 1; i >= 0; --i) {
            int dm = a[i] - min;
            /**
             * 相同数向前排列
             */
            c[dm] = c[dm] - 1 ;
            b[c[dm]] = a[i];//按存取的方式取出c的元素
        }
        return b;
    }

    private static final String countSortStr = "public int[] countSort(int[] a) {\n" +
            "        int b[] = new int[a.length];\n" +
            "        int max = a[0], min = a[0];\n" +
            "        for (int i : a) {\n" +
            "            if (i > max) {\n" +
            "                max = i;\n" +
            "            }\n" +
            "            if (i < min) {\n" +
            "                min = i;\n" +
            "            }\n" +
            "        }//这里k的大小是要排序的数组中，元素大小的极值差+1\n" +
            "        int k = max - min + 1;\n" +
            "\n" +
            "\n" +
            "        int c[] = new int[k];  // 100 - 89 = 11 + 1=12，\n" +
            "        for (int i = 0; i < a.length; ++i) {\n" +
            "            /**\n" +
            "             * 每一个数减最小值得是 0 到 K范围\n" +
            "             * c数组默认初始都为0，这里计算\n" +
            "             */\n" +
            "            c[a[i] - min] += 1;//优化过的地方，减小了数组c的大小\n" +
            "        }\n" +
            "        print(c);//[1, 1, 0, 3, 2, 1, 1, 1, 2, 0, 1, 1]  c 里面第一位对应89 ,最后一位对应100，里面的值对应的是个数\n" +
            "        for (int i = 1; i < c.length; ++i) {\n" +
            "            //确定每个位置比他下的数量，也就是一次加上前一个，\n" +
            "            // 所以现在c存储的值,是他的位置，应该说是他的最大位置（ 比如前面又9 个元素，这个值是15，说明， 15 14 13 12 11 10 都是我的位置）\n" +
            "            c[i] = c[i] + c[i - 1];\n" +
            "        }\n" +
            "        print(c);\n" +
            "        for (int i = a.length - 1; i >= 0; --i) {\n" +
            "            int dm = a[i] - min;\n" +
            "            c[dm] = c[dm] - 1 ;\n" +
            "\n" +
            "            b[c[dm]] = a[i];//按存取的方式取出c的元素\n" +
            "        }\n" +
            "        return b;\n" +
            "    }";
}
