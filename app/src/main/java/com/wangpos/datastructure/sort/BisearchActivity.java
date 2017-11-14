package com.wangpos.datastructure.sort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.wangpos.datastructure.R;

import java.util.Arrays;

import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

public class BisearchActivity extends AppCompatActivity implements View.OnClickListener {

    CodeView codeView;
    private Button btnRun;
    private TextView tvData;
    private TextView tvResult;
    private TextView tvSummary;
    private TextView tvTime;

    int arr[] = {1,2,3,4,5,6,7,8,9};
    private TextView tvStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bisearch);
        codeView = (CodeView) findViewById(R.id.codeView);
        btnRun = (Button) findViewById(R.id.btnRun);
        btnRun.setOnClickListener(this);
        tvData = (TextView) findViewById(R.id.data);
        tvResult = (TextView) findViewById(R.id.result);
        tvSummary = (TextView) findViewById(R.id.summary);
        codeView.setTheme(CodeViewTheme.DARK);
        tvTime = (TextView)findViewById(R.id.time);
        tvStorage = (TextView) findViewById(R.id.tvStorage);

        tvResult.setText("");


        tvData.setText(Arrays.toString(arr));
        tvSummary.setText("当数据量很大适宜采用该方法。采用二分法查找时，数据需是排好序的,算法适用于已知有序的集合，并且知道是从大到小还是从小到大的;");
        tvTime.setText("O(log N)");
        tvStorage.setText("S(n)");
        codeView.showCode("  /**\n" +
                "     * 已知数组是从小到大的排序\n" +
                "     *\n" +
                "     * @param arr\n" +
                "     * @param target\n" +
                "     */\n" +
                "    public int findBisearch(int[] arr, int target) {\n" +
                "\n" +
                "        int low = 0;\n" +
                "\n" +
                "        int high = arr.length - 1;\n" +
                "\n" +
                "        while (low <= high) {   //必须有等于，如果没有等号相当于，当被查找的数据恰好是两端时就找不到\n" +
                "            int mid = (low + high) / 2;\n" +
                "            if (arr[mid] < target) {\n" +
                "                low = mid + 1; //这里容易直接等于mid，这样就重复检查了mid这个数据\n" +
                "            } else if (arr[mid] > target) {\n" +
                "                high = mid - 1;\n" +
                "            } else {\n" +
                "                return mid;\n" +
                "            }\n" +
                "\n" +
                "        }\n" +
                "        //未找到\n" +
                "        return -1;\n" +
                "\n" +
                "\n" +
                "    }");

    }


    @Override
    public void onClick(View view) {
        tvResult.setText("位置 "+findBisearch(arr,7));
    }


    /**
     * 已知数组是从小到大的排序
     *
     * @param arr
     * @param target
     */
    public int findBisearch(int[] arr, int target) {

        int low = 0;

        int high = arr.length - 1;

        while (low <= high) {   //必须有等于，如果没有等号相当于，当被查找的数据恰好是两端时就找不到
            int mid = (low + high) / 2;
            if (arr[mid] < target) {
                low = mid + 1; //这里容易直接等于mid，这样就重复检查了mid这个数据
            } else if (arr[mid] > target) {
                high = mid - 1;
            } else {
                return mid;
            }

        }
        //未找到
        return -1;

    }
}
