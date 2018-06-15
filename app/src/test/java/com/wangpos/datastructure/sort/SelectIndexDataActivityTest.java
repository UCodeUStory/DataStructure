package com.wangpos.datastructure.sort;

import com.wangpos.datastructure.java.DeadLockDemo;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by qiyue on 2017/11/27.
 */
public class SelectIndexDataActivityTest {

    private SelectIndexDataActivity selectIndexDataActivity;

    @Before
    public void setUp() throws Exception {
        selectIndexDataActivity = new SelectIndexDataActivity();
    }

    /**
     * 未交换=i=-1[9, 6, 7, 3, 8, 2, 4, 5]
     未交换=i=-1[9, 6, 7, 3, 8, 2, 4, 5]
     未交换=i=-1[9, 6, 7, 3, 8, 2, 4, 5]

     交换=i=0[3, 6, 7, 9, 8, 2, 4, 5]
     未交换=i=0[3, 6, 7, 9, 8, 2, 4, 5]
     未交换=i=0[3, 6, 7, 9, 8, 2, 4, 5]
     交换=i=1[3, 2, 7, 9, 8, 6, 4, 5]
     未交换=i=1[3, 2, 7, 9, 8, 6, 4, 5]
     交换=i=2[3, 2, 4, 9, 8, 6, 7, 5]
     未交换=i=2[3, 2, 4, 9, 8, 6, 7, 5]
     结果=i=2[3, 2, 4, 5, 8, 6, 7, 9]
     result =3
     * @throws Exception
     */
    @Test
    public void testPartition() throws Exception{
        int a[]={2,5,3,0,2,3,0,3};
        int b[]={9,6,7,3,8,2,4,5};
        int c[]={1,2,3,4,5,6,7,9};
        int result = selectIndexDataActivity.partition(b,0,b.length-1);

        System.out.println("result =" + result);

        Random random = new Random();

    }

    @Test
    public void testSort() throws Exception {

        int a[]={2,5,3,0,2,3,0,3};

        int result= selectIndexDataActivity.randomizedSelect(a,0,a.length-1,3);//产生第三小的数
        System.out.println("快速选择算法求出的,第"+3+"个最小数是："+result);

        assertEquals(2,result);

    }


    /**
     * 以录音为例，有，IDLE，READY_RECORD, RECORDING,READY_PAUSE，PAUSED，READY_CANCEL，CANCELED,SAVING,SAVED
     *
     *
     * @throws Exception
     */
    @Test
    public void testSate() throws Exception {
         //状态机应用场景


    }



    enum EValue {

        IDLE,READY_RECORD,RECORDING,READY_PAUSE,PAUSED

    }



    @Test
    public void test_dead_lock(){
        DeadLockDemo deadLockDemo = new DeadLockDemo();
        deadLockDemo.startAllThread();
    }








}