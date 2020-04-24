package com.wangpos.datastructure.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DynamicMoney {

    public static void main(String[] args) {
        Integer m[] = new Integer[]{5, 2, 3};

        int mInt[] = new int[]{5, 2, 3};
        //由大到小排序，效率更高
        Arrays.sort(m, Collections.<Integer>reverseOrder());

//        int curMoney = 20;//4
//        int curMoney = 19; // 5 5 5 2 2
        int curMoney = 18; // 5 5 5 3

//        int count = splitMoney(m, 0, curMoney);
        int count = minConins3(mInt, curMoney);
        System.out.println("count=" + count);
    }

    /**
     * 思路就是遍历逐个遍历，使用 0 张 1 ，2 张直到最大
     *
     * @param m
     * @param index
     * @param curMoney
     * @return
     */
    private static int splitMoney(Integer[] m, int index, int curMoney) {
        //都遍历完了，也得到了最小值
        if (curMoney == 0) {
            return 0;
        }

        /**
         * 回溯当前Size
         */
        int currentSize = -1;
        /**
         * 遍历每一个元素，从不选，到选择最大
         */
        for (int i = index; i < m.length; i++) {
            if (curMoney >= m[i]) {
                int maxSelect = curMoney / m[i];//最大选择
                //从0开始 j 就是选择张数
                for (int j = 0; j <= maxSelect; j++) {
                    int restMoney = curMoney - m[i] * j;
                    int next = splitMoney(m, i + 1, restMoney);

                    if (next != -1) {//有解
                        if (currentSize == -1) {
                            //一次没有被回溯直接赋值,这里是i 加一，不是index +1
                            currentSize = j + next;
                        } else {
                            currentSize = Math.min(currentSize, j + next);
                        }
                    } else {
                        if (restMoney == 0) {
                            currentSize = j;
                        }
                    }
                }
            }
        }
        return currentSize;
    }

    public int minCoins1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return -1;
        }
        return process(arr, 0, aim);
    }

    /**
     * 优化两层循环合并成一层 ，因为循环不是每次都从0开始，后续的遍历不涉及到前面
     *
     * @param arr
     * @param i
     * @param rest
     * @return
     */
    private int process(int[] arr, int i, int rest) {
        if (i == arr.length) {
            return rest == 0 ? 0 : -1;
        }

        int count = -1;

        for (int k = 0; k * arr[i] <= rest; k++) {
            int next = process(arr, i + 1, rest - k * arr[i]);
            if (next != -1) {
                count = count == -1 ? next + k : Math.min(count, next + k);
            }
        }
        return count;
    }


    /**
     * 动态规划最优实现方案
     * @param arr
     * @param aim
     * @return
     */
    public static int minConins2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return -1;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        //设置最后一排的值，除dp[N][0]外，其他都是-1

        for (int col = 1; col <= aim; col++) {
            dp[N][col] = -1;
        }
        for (int i = N - 1; i >= 0; i--) {//从底向上计算每一行
            for (int rest = 0; rest <= aim; rest++) {
                dp[i][rest] = -1;//初始时先设置dp[i][rest]的值无效
                if (dp[i + 1][rest] != -1) {//下面的值如果有效
                    dp[i][rest] = dp[i + 1][rest];//先设置成下面的值
                }
                if (rest - arr[i] >= 0 && dp[i][rest - arr[i]] != -1) {
                    if (dp[i][rest] == -1) {
                        dp[i][rest] = dp[i][rest - arr[i]] + 1;
                    } else {
                        dp[i][rest] = Math.min(dp[i][rest], dp[i][rest - arr[i]] + 1);
                    }
                }
            }

        }
        printArray(dp);
        return dp[0][aim];
    }

    /**
     * 动态规划普通方案
     * @param arr
     * @param aim
     * @return
     */
    public static int minConins3(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return -1;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        //设置最后一排的值，除dp[N][0]外，其他都是-1

        //最后一排为溢出所以都为-1无解
        for (int col = 1; col <= aim; col++) {
            dp[N][col] = -1;
        }
        for (int i = N - 1; i >= 0; i--) {//从底向上计算每一行
            for (int rest = 0; rest <= aim; rest++) {
                singleDataCalculate(arr[i], dp, i, rest);
            }
        }
        printArray(dp);//测试打印
        return dp[0][aim];
    }

    //这个可以优化
    //把所有等式列出来发现    dp[i][rest] = Math.min(dp[i][rest], dp[i][rest - arr[i]] + 1);
    private static void singleDataCalculate(int i, int[][] dp, int i2, int rest) {
        dp[i2][rest] = -1;//初始时先设置dp[i][rest]的值无效
        int k = rest / i2;//可试的最大张数
        if (rest == 0) {//如果0表示不需要找钱 也就需要0张
            dp[i2][rest] = 0;
        } else {
            int minValue = -1;//记录最小值
            for (int j = 0; j <= k; j++) {
                int next = dp[i2 + 1][rest - j * i2];
                if (next != -1) {//-1表示无解不记录统计
                    if (minValue == -1) {//第一次统计，无需比较直接赋值
                        minValue = next + j;
                    } else {
                        minValue = Math.min(minValue, next + j);
                    }
                }
            }
            dp[i2][rest] = minValue;
        }
    }

    private static void printArray(int[][] m) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 19; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }
}
