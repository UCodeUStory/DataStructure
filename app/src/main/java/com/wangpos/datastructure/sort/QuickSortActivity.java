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
