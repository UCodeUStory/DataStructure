package com.wangpos.datastructure.java.mylist;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by qiyue on 2018/6/20.
 */

public class TestCJArrayList {


    @Test
    public void testArrayList(){
        int a[] = {1,2};

        int b[] = Arrays.copyOf(a,6);

        System.out.println(Arrays.toString(b));
    }

    @Test
    public void testCJList(){
        CJArrayList<String> cjArrayList = new CJArrayList<String>();
        cjArrayList.add("first");
        cjArrayList.add("second");
        cjArrayList.add("third");

        for (int i = 0; i < cjArrayList.size(); i++) {
            System.out.println("data ="+cjArrayList.get(i));
        }
    }
}
