package com.wangpos.datastructure.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DynamicRobert {

    private static int N = 7;
    private static int K = 10;//这里步骤要+1
    private static int P = 4;//这里数组-1

    public static void main(String[] args) {

        int dp[][] = new int[K][N];

        int result = way(dp);

        System.out.println("result="+result);

        printArray(dp);
    }

    private static int way(int[][] dp) {
        dp[0][P] = 1;
        for (int i = 1; i < K; i++) {
            dp[i][0] = dp[i][0] + dp[i - 1][1];
            dp[i][N - 1] = dp[i][N - 1] + dp[i - 1][N - 2];
            for (int j = 1; j < N - 1; j++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j + 1];
            }
        }

        return dp[9][3];
    }


    private static void printArray(int[][] m) {
        for (int i = 0; i < K; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }

    }

    public int ways1(int N, int M, int K, int P) {
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }
        return walk(N, M, K, P);
    }


    /**
     * @param N    位置1~N
     * @param cur  当前位置 可变参数
     * @param rest 剩余步骤 可变参数
     * @param P    最终目标位置P
     * @return 表示解法数量
     */
    public int walk(int N, int cur, int rest, int P) {

        if (rest == 0) {
            return cur == P ? 1 : 0;
        }

        if (cur == 1) {
            return walk(N, 2, rest - 1, P);
        }

        if (cur == N) {
            return walk(N, N - 1, rest - 1, P);
        }

        return walk(N, cur + 1, rest - 1, P) + walk(N, cur - 1, rest - 1, P);
    }

}
