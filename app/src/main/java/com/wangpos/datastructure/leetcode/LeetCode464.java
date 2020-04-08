package com.wangpos.datastructure.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class LeetCode464 {

    public static void main(String args[]) {
        Solution solution = new Solution();

        System.out.println(solution.canIWin(10, 11));

//        System.out.println(solution.canIWin(10,12));

    }

    static class Solution {

        public Set<Integer> selects = new HashSet();

        /**
         * 1 A  -1 B
         */
        public int currentSelectPerson = 1;

        public int sum = 0;

        public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
            //12
            //  1   9 10
            // 选2

            //Set集合初始化


            //最大和小于目标和返回false
            int sumTemp = (1 + maxChoosableInteger) * maxChoosableInteger / 2;
            if (sumTemp < desiredTotal) return false;


            //有序链表
            LinkedList<Integer> numPools = new LinkedList<>();

//            for (int i = 1; i <= maxChoosableInteger; i++) {
//                numPools.add(i);
//            }


            return canWinCalculate(1, maxChoosableInteger, desiredTotal, new HashMap<Integer, Boolean>());

        }

        /**
         * @param i                     当前选的值
         * @param maxChoosableInteger
         * @param desiredTotal
         * @param integerBooleanHashMap
         * @return
         */
        private boolean canWinCalculate(int i, int maxChoosableInteger, int desiredTotal, HashMap<Integer, Boolean> integerBooleanHashMap) {

            for (i = maxChoosableInteger; i > 0; i--) {
                selects.clear();
                selects.add(i);
                currentSelectPerson = 1;
                sum = i;
                boolean result = canWinUnit(i, maxChoosableInteger, desiredTotal, integerBooleanHashMap);
                if (result) {
//                    integerBooleanHashMap.put(i, result);
                    return result;
                }
            }

            return false;
        }

        private boolean canWinUnit(int i, int maxChoosableInteger, int desiredTotal, HashMap<Integer, Boolean> integerBooleanHashMap) {

//            if (integerBooleanHashMap.containsKey(i)) {
//                return integerBooleanHashMap.get(i);
//            }

            boolean result = false;
            int prevSum = sum;

//            Set<Integer> filters = new HashSet();
            for (i = maxChoosableInteger; i > 0; i--) {
                if (selects.contains(i)) continue;
                selects.add(i);
                sum = sum + i;
                currentSelectPerson = -currentSelectPerson;//取相反数

                if (sum >= desiredTotal) {
                    if (currentSelectPerson == 1) {
                        result = true;
                    }
                } else {
                    result = canWinUnit(i, maxChoosableInteger, desiredTotal, integerBooleanHashMap);
                }
                //底下返回false 继续，返回true 停止
                if (result) {
                    break;
                } else {
                    //恢复到之前状态
                    sum = prevSum;
                    currentSelectPerson = -currentSelectPerson;
                    selects.remove(i);
                }
            }

            return result;

        }
    }
}
