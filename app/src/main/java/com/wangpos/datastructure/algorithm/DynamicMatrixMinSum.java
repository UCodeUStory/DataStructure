package com.wangpos.datastructure.algorithm;


import java.util.Arrays;

class DynamicMatrixMinSum {
    private static final int M = 4;//矩阵行数x
    private static final int N = 4;//矩阵列数

    public static void main(String args[]) {

        /**
         * 矩阵转换程序二维数组的时候，其中 矩阵行数是二维数组的长度，
         * 如  3 行 4 列矩阵赋值给数组
         *    int m[][] = new int[][]{{1, 3, 5, 9}, {8, 1, 3, 4}, {5, 0, 6, 1}};
         *
         */
        // 3 行 4 列
        int m[][] = new int[][]{{1, 3, 5, 9}, {8, 1, 3, 4}, {5, 0, 6, 1}, {8, 8, 4, 0}};

//        System.out.println(m.length);
//        printArray(m);

       int result =  calculateMinSum(m);

       System.out.println("最小和"+result);

    }

    private static int calculateMinSum(int[][] m) {

        int dp[][] = new int[M][N];
        dp[0][0] = m[0][0];
        for (int i = 1; i < M; i++) {
            dp[i][0] = dp[i - 1][0] + m[i][0];

        }

        for (int j = 1; j < N; j++) {
            dp[0][j] = dp[0][j - 1] + m[0][j];

        }
        for (int i = 1; i < M; i++) {
            for (int j = 1; j < N; j++) {
                dp[i][j] = Math.min(dp[i-1][j],dp[i][j-1]) +m[i][j];
            }
        }
        printArray(dp);

        return dp[M-1][N-1];
    }

    private static void printArray(int[][] m) {

//        /**           矩阵
//         *          1 3 5 9
//         *          8 1 3 4
//         *          5 0 6 1
//         */
//        /**
//         * 遍历顺序3 行 4列
//         * 优先遍历一行，逐行遍历
//         * 最外成行长度
//         * 最内层列列长度
//         *
//         */
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 4; j++) {
//                System.out.print("("+i+","+j+ ") ");
//            }
//            System.out.println();
//        }
//
//        /**
//         (0,0) (0,1) (0,2)
//         (1,0) (1,1) (1,2)
//         (2,0) (2,1) (2,2)
//         (3,0) (3,1) (3,2
//         */
//
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 4; j++) {
//                System.out.print(m[i][j]+" ");
//            }
//            System.out.println();
//        }
//        /**
//         1 3 5 9
//         8 1 3 4
//         5 0 6 1
//         *
//         */


        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }

    }

}