package com.wangpos.datastructure.sort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wangpos.datastructure.R;
import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;

import java.lang.reflect.Array;
import java.util.Arrays;

public class BubbleSortActivity extends BaseActivity {

    int arr[] = {23, 12, 13, 44, 65, 26, 17, 38, 59};

    @Override
    protected void initData() {
        addItem(new CodeBean("冒泡排序",bubbleSortStr));

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
        return Arrays.toString(bubbleSort(arr));
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
        return "一次比较两个元素，如果他们的顺序错误就把他们交换过来。" +
                "走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成";
    }



    private static final String bubbleSortStr = "public void bubbleSort(int[] a)\n" +
            "    {\n" +
            "        int temp = 0;\n" +
            "        /**这里的i表示比较多少次，第一次要全部进行比较得出最大的一个，第二次应该除去最大的一个，比较剩下的*/\n" +
            "        for (int i = a.length - 1; i > 0; --i)\n" +
            "        {\n" +
            "            for (int j = 0; j < i; ++j)\n" +
            "            {\n" +
            "                if (a[j + 1] < a[j])\n" +
            "                {\n" +
            "                    temp = a[j];\n" +
            "                    a[j] = a[j + 1];\n" +
            "                    a[j + 1] = temp;\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }";

    public int[] bubbleSort(int[] a)
    {
        int temp = 0;
        /**这里的i表示比较多少次，第一次要全部进行比较得出最大的一个，第二次应该除去最大的一个，比较剩下的*/
        for (int i = a.length - 1; i > 0; --i)
        {
            for (int j = 0; j < i; ++j)
            {
                if (a[j + 1] < a[j])
                {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
        return a;
    }
}
