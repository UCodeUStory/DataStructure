package com.wangpos.datastructure.leetcode2;

import java.util.TreeMap;

public class LeetCode731 {

    TreeMap<Integer, Integer> singleMap = new TreeMap<>();
    TreeMap<Integer, Integer> doubleMap = new TreeMap<>();

    public LeetCode731() {

    }

    public boolean book(int start, int end) {
        //返回小于key最大的Key
        Integer j = doubleMap.lowerKey(start);//出现两次区间中，起点小于start的
        //大于等于Key的最小Key
        Integer k = doubleMap.ceilingKey(start);//出现两次区间中，起点大于等于start的
        if (j != null && doubleMap.get(j) > start) {
            return false;
        }
        if (k != null && k < end) {
            return false;
        }
        Integer a = singleMap.lowerKey(start);

        Integer b = singleMap.ceilingKey(start);

        //完全没有冲突
        //判断开始元素比他小的不存在
        /**
         *
         * left  = a
         *
         * right = singleMap.get(a)
         *
         * 新插入的start end 如果不能存在重复
         *
         * 则应该瞒住，start 不在这个集合中存在，或者如果存在，start应该大于这个区间的右区间也就是right
         *
         * 并且，end 不存在，或者小于这个区间left区间
         *
         */
        if ((a == null || singleMap.get(a) < start)
                && (b == null || b > end)) {
            singleMap.put(start, end);
            return true;
        }

        if (a == null || singleMap.get(a) < start) {
        } else {
            //与前一个区间有重叠部分，将重叠部分塞入doubleMap，同时在singleMap中合并当前区间和前一个区间
            Integer i = singleMap.get(a);
            //区间合并
            singleMap.put(a, Math.max(end, i));
            //存储重复区间
            addDouble(start, Math.min(end, i));
            start = a;
            end = Math.max(end, i);
        }

        //如果和后一个区间有冲突，冲突区域塞入doubleMap，同时在singleMap中合。
        //需要考虑当前准备插入的区间，同时包含了多个已有区间的情况，所以继续向后找
        //这里应该有优化点，直接查找后一个是比调用higherKey方法来得快的，没仔细想了
        while (b != null && b <= end) {
            Integer i = singleMap.get(b);
            addDouble(b, Math.min(end, i));
            singleMap.remove(b);
            //这里start 如果前一区间重就需要重新合并
            singleMap.put(start, Math.max(end, i));
            end = Math.max(end, i);
            //返回大于start最小值
            b = singleMap.higherKey(start);
        }

        return true;
    }

    public void addDouble(int start, int end) {
        if (start >= end) {
            return;
        }
        doubleMap.put(start, end);
    }

}
