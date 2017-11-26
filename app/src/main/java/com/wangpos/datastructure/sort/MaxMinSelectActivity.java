package com.wangpos.datastructure.sort;

import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;

import java.util.Arrays;

/**
 * Created by qiyue on 2017/11/26.
 */

public class MaxMinSelectActivity extends BaseActivity {

    int arr[] = {23, 12, 13, 44, 65, 26, 17, 38, 59};

    @Override
    protected void initData() {

        addItem(new CodeBean("同时最大值最小值快速查找法",select_max));

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
        return SelectMax(arr).toString();
    }

    @Override
    protected String getTimeData() {
        return "O(n)";
    }

    @Override
    protected String getSpaceTimeData() {
        return null;
    }

    @Override
    protected String getWendingXingData() {
        return "稳定";
    }

    @Override
    protected String getSummaryData() {
        return "复杂度相同，但是比较次数，缺少了很多，这样如果比较比较耗时，并且数据量很大，就会更加有效率";
    }


    class MaxMin {
        public int max;
        public int min;

        @Override
        public String toString() {
            return "max = "+max +"," +"min ="+min;
        }
    }


    private static final String select_max = "/**\n" +
            "     * 如果是奇数 3(n/2)\n" +
            "     * 如果是偶数 3(n-2)/2\n" +
            "     *\n" +
            "     * 他们时间复杂度同样是 n  n/2,当然按照无穷大，时间复杂度都是n，但比较次数少的更快些\n" +
            "     * @param a\n" +
            "     * @return\n" +
            "     */\n" +
            "    public MaxMin SelectMax(int a[]){\n" +
            "        MaxMin num = new MaxMin();\n" +
            "\n" +
            "        int i = 0;\n" +
            "        int len = a.length;\n" +
            "        if (len%2!=0)\n" +
            "        {\n" +
            "            num.max = num.min = a[0];\n" +
            "            i = 1;\n" +
            "        }\n" +
            "        else\n" +
            "        {\n" +
            "            //如果是偶数，则先比较前两个元素，然后成对处理后面元素。\n" +
            "            if (a[0]>a[1])\n" +
            "            {\n" +
            "                num.max = a[0];\n" +
            "                num.min = a[1];\n" +
            "            }\n" +
            "            else\n" +
            "            {\n" +
            "                num.max = a[1];\n" +
            "                num.min = a[0];\n" +
            "            }\n" +
            "            i = 2;\n" +
            "        }\n" +
            "\n" +
            "        for(;i<len;i+=2){\n" +
            "\n" +
            "            int max_temp;\n" +
            "            int min_temp;\n" +
            "            //首先将两个元素进行比较得出较大较小值\n" +
            "            if (a[i] > a[i + 1])\n" +
            "            {\n" +
            "                max_temp = a[i];\n" +
            "                min_temp = a[i + 1];\n" +
            "            }else{\n" +
            "                max_temp = a[i+1];\n" +
            "                min_temp = a[i];\n" +
            "            }\n" +
            "\n" +
            "            //然后将较大较小值与最大最小值进行比较\n" +
            "            num.max = num.max > max_temp ? num.max : max_temp;\n" +
            "            num.min = num.min < min_temp ? num.min : min_temp;\n" +
            "        }\n" +
            "\n" +
            "        return num;\n" +
            "\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    //对比\n" +
            "    public MaxMin oldSelect(int a[]){\n" +
            "        MaxMin num = new MaxMin();\n" +
            "\n" +
            "        /**\n" +
            "         * 2n次比较\n" +
            "         */\n" +
            "        for(int i = 0;i<a.length;i++){\n" +
            "            if (a[i]>num.max){\n" +
            "                num.max = a[i];\n" +
            "            }\n" +
            "            if (a[i]<num.min){\n" +
            "                num.min = a[i];\n" +
            "            }\n" +
            "        }\n" +
            "        return num;\n" +
            "    }";

    /**
     * 如果是奇数 3(n/2)
     * 如果是偶数 3(n-2)/2
     *
     * 他们时间复杂度同样是 n  n/2,当然按照无穷大，时间复杂度都是n，但比较次数少的更快些
     * @param a
     * @return
     */
    public MaxMin SelectMax(int a[]){
        MaxMin num = new MaxMin();

        int i = 0;
        int len = a.length;
        if (len%2!=0)
        {
            num.max = num.min = a[0];
            i = 1;
        }
        else
        {
            //如果是偶数，则先比较前两个元素，然后成对处理后面元素。
            if (a[0]>a[1])
            {
                num.max = a[0];
                num.min = a[1];
            }
            else
            {
                num.max = a[1];
                num.min = a[0];
            }
            i = 2;
        }

        for(;i<len;i+=2){

            int max_temp;
            int min_temp;
            //首先将两个元素进行比较得出较大较小值
            if (a[i] > a[i + 1])
            {
                max_temp = a[i];
                min_temp = a[i + 1];
            }else{
                max_temp = a[i+1];
                min_temp = a[i];
            }

            //然后将较大较小值与最大最小值进行比较
            num.max = num.max > max_temp ? num.max : max_temp;
            num.min = num.min < min_temp ? num.min : min_temp;
        }

        return num;


    }


    //对比
    public MaxMin oldSelect(int a[]){
        MaxMin num = new MaxMin();

        /**
         * 2n次比较
         */
        for(int i = 0;i<a.length;i++){
            if (a[i]>num.max){
                num.max = a[i];
            }
            if (a[i]<num.min){
                num.min = a[i];
            }
        }
        return num;
    }
}
