package com.wangpos.datastructure.leetcode2;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LeetCode846 {

    class Solution {
        public boolean isNStraightHand(int[] hand, int W) {

            //排序
            Arrays.sort(hand);
            int len = hand.length;
            if (len % W != 0) {
                return false;
            }

            //将数组添加到列表中，方便移除
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                list.add(hand[i]);
            }

            while (list.size() > 0) {

                Integer curVal = list.get(0);
                for (int i = 0; i < W; i++) {
                    if (list.size() == 0)
                        return false;
                    if (!list.remove(curVal))
                        return false;
                    curVal++;
                }
            }

            return true;

        }

        @TargetApi(Build.VERSION_CODES.N)
        public boolean isNStraightHand2(int[] hand, int W){
            //通过TreeMap实现数组的排序，并记录元素出现的次数
            TreeMap<Integer, Integer> count = new TreeMap();
            for (int card: hand) {
                if (!count.containsKey(card))
                    count.put(card, 1);
                else
                    count.replace(card, count.get(card) + 1);
            }

            while (count.size() > 0) {
                //读取第一个数组
                int first = count.firstKey();
                for (int card = first; card < first + W; ++card) {
                    //不够直接返回fasle
                    if (!count.containsKey(card)) return false;
                    int c = count.get(card);
                    if (c == 1) count.remove(card);
                    else count.replace(card, c - 1);
                }
            }

            return true;
        }
    }
}
