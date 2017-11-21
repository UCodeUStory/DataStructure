package com.wangpos.datastructure.core;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiyue on 2017/11/21.
 */

public class ViewHolder {

    private SparseArray<View> viewSparseArray = new SparseArray<>();

    /**
     * 类似一个通用的懒加载
     * @param view
     * @param id
     * @param <T>
     * @return
     */
    public static  <T extends View> T getView(View view ,@IdRes int id){

        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
