package com.wangpos.datastructure.sort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wangpos.datastructure.R;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wangpos.datastructure.R;

import java.util.Arrays;

import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

public class QuickSortActivity extends AppCompatActivity implements View.OnClickListener {

    CodeView codeView;
    private Button btnRun;
    private TextView tvData;
    private TextView tvResult;
    private TextView tvSummary;
    private TextView tvTime;

    int arr[] = {23, 12, 13, 44, 65, 26, 17, 38, 59};
    private TextView tvStorage;

    private DataBean[]dataBeans;
    private TextView tvWeidingXing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_insert_sort);
        codeView = (CodeView) findViewById(R.id.codeView);
        btnRun = (Button) findViewById(R.id.btnRun);
        btnRun.setOnClickListener(this);
        tvData = (TextView) findViewById(R.id.data);
        tvResult = (TextView) findViewById(R.id.result);
        tvSummary = (TextView) findViewById(R.id.summary);
        codeView.setTheme(CodeViewTheme.DARK);
        tvTime = (TextView) findViewById(R.id.time);
        tvStorage = (TextView) findViewById(R.id.tvStorage);
        tvWeidingXing = (TextView) findViewById(R.id.tvWendingXing);

        tvResult.setText("");


        tvData.setText(Arrays.toString(arr));
        tvSummary.setText("");
        tvTime.setText("");
        tvStorage.setText("");
        tvWeidingXing.setText("");
        codeView.showCode("");

        dataBeans = new DataBean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            dataBeans[i] = new DataBean(arr[i]);
        }

    }


    @Override
    public void onClick(View view) {

        DataBean arrays[] = directInsertSort(dataBeans);

        tvResult.setText(Arrays.toString(arrays));
    }


    /**
     * 直接插入排序,从小到大
     *
     * @param arrays 带排序数组
     * @param <Type> 数组类型
     * @return
     */
    public <Type extends Comparable<? super Type>> Type[] directInsertSort(Type[] arrays) {
        for (int i = 0; i < arrays.length; i++) {
            for (int j = i + 1; j<arrays.length; j++) {
                Type temp = arrays[i];
                if (arrays[j].compareTo(arrays[i]) < 0) {//从小到大
                    arrays[i] = arrays[j];
                    arrays[j] = temp;
                }

            }
        }

        return arrays;
    }




    private static int sortUnit(int[] array, int low, int high)
    {
        int key = array[low];//选取第一个为基准数
        while (low < high)
        {
                /*从后向前搜索比key小的值*/
            while (array[high] >= key && high > low)
                --high;
                /*比key小的放左边*/
            array[low] = array[high];
                /*从前向后搜索比key大的值，比key大的放右边*/
            while (array[low] <= key && high > low)
                ++low;
                /*比key大的放右边*/
            array[high] = array[low];
        }
            /*左边都比key小，右边都比key大。//将key放在游标当前位置。//此时low等于high */
        array[low] = key;


        return high;
    }
    /**快速排序
     *@paramarry
     *@return */
    public static void sort(int[] array, int low, int high)
    {
        if (low >= high)
            return;
            /*完成一次单元排序*/
        int index = sortUnit(array, low, high);
            /*对左边单元进行排序*/
        sort(array, low, index - 1);
            /*对右边单元进行排序*/
        sort(array, index + 1, high);
    }


    public class DataBean implements Comparable {

        private int data;

        public DataBean(int datap) {
            this.data = datap;
        }

        @Override
        public int compareTo(@NonNull Object o) {
            DataBean target = (DataBean) o;
            if (data > target.data) {
                return 1;
            } else if (data < target.data) {
                return -1;
            } else {
                return 0;
            }
        }

        @Override
        public String toString() {
            return String.valueOf(data);
        }
    }


}
