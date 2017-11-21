package com.wangpos.datastructure.core;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wangpos.datastructure.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import thereisnospon.codeview.CodeView;

/**
 * Created by qiyue on 2017/11/21.
 */

public class ListScrollAdapter extends BaseAdapter {

    List<CodeBean> codes = new ArrayList<>();

    public List<CodeBean> getCodes() {
        return codes;
    }

    public void setCodes(List<CodeBean> codes) {
        this.codes = codes;
    }

    public ListScrollAdapter(List<CodeBean>datas){
        codes = datas;

    }
    @Override
    public int getCount() {
        return codes.size();
    }

    @Override
    public Object getItem(int i) {
        return codes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View itemView = View.inflate(parent.getContext(), R.layout.code_item,null);

        TextView tvDescription = itemView.findViewById(R.id.tv_descript);
        CodeView codeView = itemView.findViewById(R.id.codeView);

        tvDescription.setText(codes.get(position).description);
        codeView.showCode(codes.get(position).codeStr);


        return itemView;
    }







}
