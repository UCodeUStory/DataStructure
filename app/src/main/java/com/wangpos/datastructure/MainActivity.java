package com.wangpos.datastructure;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mListView = (ListView)findViewById(R.id.listView);
        ListAdapter adapter = new ListAdapter(this);


        List<DataBean>list = new ArrayList<>();
        list.add(new DataBean("二分法排序"));
        list.add(new DataBean("快速排序"));
        adapter.setDataBeanList(list);
        mListView.setAdapter(adapter);


    }
}
