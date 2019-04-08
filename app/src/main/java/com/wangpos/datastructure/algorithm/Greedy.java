package com.wangpos.datastructure.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 找零是一个很常见的问题，平时找钱为了都会考虑尽可能找少的数量的钱给用户，顾客也不希望自己得很多零钱
 * 这是一个典型的使用贪婪算法的题
 * <p>
 * 通过币种的问题我们发现，贪心算法每次都要求选择最优的，所以，最终结果并不一定是最优解，
 * <p>
 * 硬币种类
 * [[25, 20, 10, 5, 1]]
 * 应找给对方的硬币 数量 ：4
 * [[25, 10, 5, 1]]
 * <p>
 * <p>
 * 硬币种类
 * [[25, 20, 5, 1]]
 * 应找给对方的硬币 数量 ：5
 * [[25, 5, 5, 5, 1]]   实际最优 是 20 20 1
 * <p>
 * 实际顾客需要的是20 20 1 还是 25 5 5 5 1呢，答案是第二个，因为他心理更想留大面值币种
 * <p>
 * <p>
 * 实际场景 顾客 希望的是尽量给我整意思就是 最大的面值的钱先给我找，也就是贪心算法
 * <p>
 * 贪心算法效率高，找钱就是需要效率高的场景，但影响最终找的金额数，所以
 * <p>
 * 贪心算法可以作为其他最优解算法中的辅助算法
 */
public class Greedy {
    static List<Integer> coins = new ArrayList<Integer>();

    public static void main(String[] args) {
        // 币种是 25 20 10 5 1
//        coins.add(25);
//        coins.add(20);
//        coins.add(10);
//        coins.add(5);
//        coins.add(1);

        // 币种是 25 20 5 1
        coins.add(25);
        coins.add(20);
        coins.add(5);
        coins.add(1);

        System.out.println("硬币种类");
        System.out.println(Arrays.asList(coins));

        int target = 41;
        Result result = getLeastCoins(target);
        System.out.println("应找给对方的硬币 数量 ：" + result.datas.size());
        System.out.println(Arrays.asList(getLeastCoins(target).datas));

        System.out.println(Arrays.asList(getLeastNumberCoins(target).datas));
    }

    private static Result getLeastCoins(int target) {
        Result result = new Result();
        result.datas = new ArrayList<>();
        //已知coins是有序的
        for (Integer coin : coins) {
            if (target >= coin) {
                // 求个数
                int count = target / coin;
                for (int i = 0; i < count; i++) {
                    result.datas.add(coin);
                }
                // 取余数
                target = target % coin;
            }
        }
        return result;
    }

    /**
     * 可以采用局部最优解法加排序算法，找到最优解
     * @param target
     * @return
     */

    private static Result getLeastNumberCoins(int target) {
        Result minNumberResult = new Result();

        for (int i = 0; i < coins.size(); i++) {
            Result current = getLeastCoinsByList(i, target);
            if (minNumberResult.datas != null) {
                if (minNumberResult.getCount() > current.getCount()) {
                    minNumberResult.datas = current.datas;
                }else{
                    minNumberResult.datas = current.datas;
                }
            }
        }

        return minNumberResult;
    }

    private static Result getLeastCoinsByList(int i, int target) {
        Result result = new Result();
        result.datas = new ArrayList<>();
        //已知coins是有序的
        for (int j = i; j < coins.size(); j++) {
            int coin = coins.get(i);
            if (target >= coin) {
                // 求个数
                int count = target / coin;
                for (int m = 0; m < count; m++) {
                    result.datas.add(coin);
                }
                // 取余数
                target = target % coin;
            }
        }
        return result;
    }

    static class Result {
        public List<Integer> datas = null;

        public int getCount() {
            return datas.size();
        }
    }
}
