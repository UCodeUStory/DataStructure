package com.wangpos.datastructure.algorithm;

public class DynamicMoneyWays {

    public static void main(String[] args) {

        int arr[] = new int[]{5, 1};

        int aim = 15;
        /***
         * 5
         * 55
         * 555
         * 11111111
         *
         * 10 5
         * 10 1 1 1 1 1
         *
         */
//        int aim = 1000;

//        int result = coin(arr, 0, aim);
        int result = coin2(arr, aim);
        System.out.println(result);
    }

    private static int coin(int[] arr, int index, int aim) {
        if (index == arr.length) {
            return 0;
        }
        int count = 0;
        int maxUse = 0;
        //找到小于当前面值的元素
        for (int i = index; i < arr.length; i++) {
            maxUse = aim / arr[i];
            if (maxUse != 0) {
                index = i;
                break;
            }
        }

        if (maxUse != 0) {//表示没有越界
            for (int j = 0; j <= maxUse; j++) {
                int rest = aim - j * arr[index];
                if (rest == 0) {//有解
                    count += 1;
                } else {//无解，遍历下一个
                    int next = coin(arr, index + 1, rest);
                    if (next != 0) {//等于0无解
                        count += next;
                    }
                }
            }
        }
        return count;
    }

    /**
     * 动态规划方法
     * @param arr
     * @param aim
     * @return
     */
    private static int coin2(int[] arr, int aim) {
        int dp[][] = new int[arr.length + 1][aim + 1];
        for (int i = 0; i <= arr.length; i++) {
            dp[i][0] = 1;
        }

        int N = arr.length - 1;
        for (int i = N; i >= 0; i--) {
            for (int j = 1; j <= aim; j++) {
                int maxValue = j / arr[i];
                if (maxValue == 0) {
                    continue;//无效
                }
                int count = 0;
                for (int k = 0; k <= maxValue; k++) {
                    int before = dp[i + 1][j - k * arr[i]];
                    count += before;
                }
                dp[i][j] = count;

            }
        }

        printArray(dp);

        return dp[0][aim];
    }


    private static void printArray(int[][] m) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }
}
