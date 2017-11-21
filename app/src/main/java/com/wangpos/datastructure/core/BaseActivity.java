package com.wangpos.datastructure.core;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangpos.datastructure.R;
import com.wangpos.datastructure.sort.ListViewForScrollView;

import java.util.ArrayList;

import thereisnospon.codeview.CodeView;

public abstract class BaseActivity extends AppCompatActivity {

    protected ListViewForScrollView mListView;

    ListScrollAdapter adapter;

    protected TextView tvDataMenu;
    protected TextView tvData;
    protected ImageView ivData;
    protected TextView tvResultMenu;
    protected TextView tvResult;
    protected Button runBtn;
    protected TextView tvSummaryMenu;
    protected TextView tvSummary;

    protected TextView tvTimeMenu;
    protected TextView tvTime;

    protected TextView tvSpaceMenu;
    protected TextView tvSpace;

    protected TextView tvWendingXingMenu;
    protected TextView tvWendingXing;


    protected LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

//        mListView = $(R.id.listView);
//        adapter = new ListScrollAdapter(new ArrayList<CodeBean>());

        linearLayout = $(R.id.lly_codeView);
        initData();
//        mListView.setAdapter(adapter);

        tvDataMenu = $(R.id.tvDataMenu);
        tvData = $(R.id.tvData);
        ivData = $(R.id.ivData);

        tvResultMenu = $(R.id.tvResultMenu);
        tvResult = $(R.id.tvResult);

        runBtn = $(R.id.btnRun);
        tvSummaryMenu = $(R.id.tvSummaryMenu);
        tvSummary = $(R.id.tvSummary);

        tvTimeMenu = $(R.id.tvTimeMenu);
        tvTime = $(R.id.tvTime);

        tvSpaceMenu = $(R.id.tvSpaceMenu);
        tvSpace = $(R.id.tvSpace);

        tvWendingXingMenu = $(R.id.tvWendingxingMenu);
        tvWendingXing = $(R.id.tvWendingXing);



        tvData.setText(getTVData());
        ivData.setImageResource(getIVData());
        tvResult.setText(getResult());
        tvSummary.setText(getSummary());
        tvTime.setText(getTime());
        tvSpace.setText(getSpaceTime());

        tvWendingXing.setText(getWendingXing());

        


    }

    protected abstract void initData();


    protected <V extends View> V $(int id) {
        return (V) this.findViewById(id);
    }


    protected <V extends View> V $(View root,int id) {
        return (V) root.findViewById(id);
    }


    protected void addItem(CodeBean codeBean){

        View itemView = null;
        itemView = LayoutInflater.from(this).inflate(R.layout.code_item,null);

        TextView tvDescription = itemView.findViewById(R.id.tv_descript);
        CodeView codeView = itemView.findViewById(R.id.codeView);

        tvDescription.setText(codeBean.description);
        codeView.showCode(codeBean.codeStr);

        linearLayout.addView(itemView,linearLayout.getChildCount());
//        adapter.notifyDataSetChanged();
    }


    public String getTVData() {
        String text = getTextData();
        if (TextUtils.isEmpty(text)){
            tvData.setVisibility(View.GONE);
            text = "";
        }else{
            tvData.setVisibility(View.VISIBLE);
        }
        return text;
    }

    public int getIVData(){
        int id = getImageData();

        if (id==0){
            ivData.setVisibility(View.GONE);
        }else{
            ivData.setVisibility(View.VISIBLE);
        }

        return id;
    }

    public String getResult() {
        String result = getResultData();
        result = isEmptyToGone(result,tvResult,tvResultMenu);
        return result;
    }


    public String getSummary() {
        String summary = getSummaryData();
        summary = isEmptyToGone(summary,tvSummary,tvSummaryMenu);
        return summary;
    }


    public String getTime() {
        String time = getTimeData();
        time =  isEmptyToGone(time,tvTime,tvTimeMenu);
        return time;
    }
    
    public String getSpaceTime() {
        String spaceTime = getSpaceTimeData();
        spaceTime = isEmptyToGone(spaceTime,tvSpace,tvSpaceMenu);
        return spaceTime;
    }

    public String getWendingXing() {
        String wendingXing = getWendingXingData();
        wendingXing =  isEmptyToGone(wendingXing,tvWendingXing,tvWendingXingMenu);
        return wendingXing;
    }


    protected abstract String getTextData();

    protected abstract int getImageData();

    protected abstract String getResultData();

    protected abstract String getTimeData();

    protected abstract String getSpaceTimeData();

    protected abstract String getWendingXingData();

    protected abstract String getSummaryData();

    public String isEmptyToGone(String text,View ...views ){
        if (TextUtils.isEmpty(text)){
            for (View view : views) {
                view.setVisibility(View.GONE);
            }
            text = "";
        }

        return text;
    }
}
