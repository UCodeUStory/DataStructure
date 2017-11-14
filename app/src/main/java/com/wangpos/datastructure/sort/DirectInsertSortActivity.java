package com.wangpos.datastructure.sort;

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

public class DirectInsertSortActivity extends AppCompatActivity implements View.OnClickListener {

    CodeView codeView;
    private Button btnRun;
    private TextView tvData;
    private TextView tvResult;
    private TextView tvSummary;
    private TextView tvTime;

    int arr[] = {23, 12, 13, 44, 65, 26, 17, 38, 59};
    private TextView tvStorage;

    private DataBean[]dataBeans;

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

        tvResult.setText("");


        tvData.setText(Arrays.toString(arr));
        tvSummary.setText("首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，直接选择排序然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕");
        tvTime.setText("O(N^2)");
        tvStorage.setText("O(1)");
        codeView.showCode(" /**\n" +
                "     * 直接插入排序,从小到大\n" +
                "     *\n" +
                "     * @param arrays 带排序数组\n" +
                "     * @param <Type> 数组类型\n" +
                "     * @return\n" +
                "     */\n" +
                "    public <Type extends Comparable<? super Type>> Type[] directInsertSort(Type[] arrays) {\n" +
                "        for (int i = 0; i < arrays.length; i++) {\n" +
                "            for (int j = i + 1; j<arrays.length; j++) {\n" +
                "                Type temp = arrays[i];\n" +
                "                if (arrays[j].compareTo(arrays[i]) < 0) {//从小到大\n" +
                "                    arrays[i] = arrays[j];\n" +
                "                    arrays[j] = temp;\n" +
                "                }\n" +
                "\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        return arrays;\n" +
                "    }");

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
