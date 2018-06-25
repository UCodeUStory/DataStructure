package com.wangpos.datastructure.core;

import com.wangpos.datastructure.BuildConfig;

import java.util.ArrayList;

/**
 * Created by qiyue on 2018/6/21.
 */

public class USList<E> extends ArrayList<E> {


    public boolean add(E e){
//        if(BuildConfig.DEBUG_MODEL) {
//            super.add(0, e);
//        }else{
            super.add(e);
//        }
         return true;
    }
}
