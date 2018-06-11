package com.wangpos.datastructure.java;

import android.util.Log;
import android.widget.BaseAdapter;

import com.wangpos.datastructure.core.BaseActivity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiyue on 2018/6/11.
 */

public class ReferenceQueueActivity extends BaseActivity {
    private static String TAG = ReferenceQueueActivity.class.getSimpleName();
    int _1M = 1024*1024*2;
    @Override
    protected void initData() {
        final ReferenceQueue referenceQueue = new ReferenceQueue();
        Object value = new Object();
        final Map<Object, Object> map = new HashMap<>();
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    int cnt = 0;
                    WeakR k;
                    while((k = (WeakR) referenceQueue.remove()) != null) {
                        Log.i(TAG,(cnt++) + "回收了:" + k);
                        map.remove(k.key);
                        Log.i(TAG,"map.size->" + map.size());
                    }
                } catch(InterruptedException e) {
                    //结束循环
                }
            }
        };
        thread.setDaemon(true);
        thread.start();


        for(int i = 0;i < 10;i++) {
            byte[] bytesKey = new byte[_1M];
            byte[] bytesValue = new byte[_1M];
            map.put(bytesKey, new WeakR(bytesKey, bytesValue, referenceQueue));
        }
        Log.i(TAG,"insert finish" + map.size());

//        Map<Object,Object> map1 = new HashMap();
//        for(int i = 0;i<100;i++){
//            byte[] bytes = new byte[_1M];
////            WeakReference<byte[]> weakReference = new WeakReference<byte[]>(bytes, referenceQueue);
//            map1.put(bytes, value);
//        }
    }

    @Override
    protected String getTextData() {
        return null;
    }

    @Override
    protected int getImageData() {
        return 0;
    }

    @Override
    protected String getResultData() {
        return null;
    }

    @Override
    protected String getTimeData() {
        return null;
    }

    @Override
    protected String getSpaceTimeData() {
        return null;
    }

    @Override
    protected String getWendingXingData() {
        return null;
    }

    @Override
    protected String getSummaryData() {
        return null;
    }


    class WeakR extends WeakReference<byte[]> {
        private Object key;
        WeakR(Object key, byte[] referent, ReferenceQueue<? super byte[]> q) {
            super(referent, q);
            this.key = key;
        }
    }
}
