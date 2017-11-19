package com.wangpos.datastructure.sort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wangpos.datastructure.R;

public class BubbleSortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_sort);
    }


    /**
     *  一次比较两个元素，如果他们的顺序错误就把他们交换过来。走
     *  访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成
     *
     *
     * @param a
     */
    public void sort(int[] a)
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
    }
}
