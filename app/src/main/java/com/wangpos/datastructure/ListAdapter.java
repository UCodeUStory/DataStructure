package com.wangpos.datastructure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wangpos.datastructure.core.DataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiyue on 2017/11/13.
 */

public class ListAdapter extends BaseAdapter {

    public List<DataBean> getDataBeanList() {
        return dataBeanList;
    }

    public void setDataBeanList(List<DataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
    }

    private List<DataBean> dataBeanList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context ;

    public ListAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView)view.findViewById(R.id.name);
            viewHolder.tvCount = (TextView)view.findViewById(R.id.index);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }


        viewHolder.tvName.setText(dataBeanList.get(i).name);
        viewHolder.tvCount.setText(String.valueOf(i+1));

        return view;
    }

    class ViewHolder {

        public TextView tvName;

        public TextView tvCount;


    }
}
