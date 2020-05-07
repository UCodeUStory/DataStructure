package com.wangpos.datastructure.algorithm;

public class DynamicCirsscrossString {
    public static final int ROW = 3;
    public static final int COLUMN = 3;

    public static void main(String[] args) {


        int map[][] = new int[][]{{-2, -3, 3}, {-5, -10, 1}, {0, 30, -5}};
        int result = getMinBlood(map);
        System.out.println("最少血量" + result);
    }


    public static int getMinBlood(int[][] map) {

        int dp[][] = new int[ROW][COLUMN];

        //初始化第一行
        dp[0][0] = 1 + getCurrentPositionNeedBlood(map[0][0]);
        for (int i = 1; i < ROW; i++) {
            dp[0][i]  = dp[0][i-1] + getCurrentPositionNeedBlood(map[0][i]);
        }
        for(int j =1;j<COLUMN;j++){
            dp[j][0]=dp[j-1][0] + getCurrentPositionNeedBlood(map[j][0]);
        }
        for(int i = 1;i<ROW;i++){
            for(int j = 1;j<COLUMN;j++){
                dp[i][j]= Math.min(dp[i-1][j],dp[i][j-1])+getCurrentPositionNeedBlood(map[i][j]);
            }
        }
        printArray(map,3,3);
        return dp[ROW - 1][COLUMN - 1];

    }

    private static int getCurrentPositionNeedBlood(int i) {
        if (i < 0) {
            return -i;
        }
        return 0;
    }

    private static void printArray(int[][] m, int l1, int l2) {
        for (int i = 0; i < l1; i++) {
            for (int j = 0; j < l2; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }
}
