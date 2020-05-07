package com.wangpos.datastructure.algorithm;

public class NEmpress {

    public static void main(String[] args) {
        // 4 == 2
        // 1 == 1
        // 2 3 == 0

        // 8 ==92
        Queen queen = new Queen(5);
        queen.backtrack(1);
    }

}


 class Queen {
    private int[] column; //同栏是否有皇后，1表示有
    private int[] rup; //右上至左下是否有皇后
    private int[] lup; //左上至右下是否有皇后
    private int[] queen; //解答
    private int num; //解答编号
     private int n = 1;

    public Queen(int N) {
        this.n = N;
        column = new int[n+1];
        rup = new int[(2*n)+1];
        lup = new int[(2*n)+1];
        for (int i = 1; i <= n; i++)
            column[i] = 0;
        for (int i = 1; i <= (2*n); i++)
            rup[i] = lup[i] = 0;  //初始定义全部无皇后
        queen = new int[n+1];
    }

    public void backtrack(int i) {
        if (i > n) {
            showAnswer();
        } else {
            for (int j = 1; j <= n; j++) {
                if ((column[j] == 0) && (rup[i+j] == 0) && (lup[i-j+n] == 0)) {
                    //若无皇后
                    queen[i] = j; //设定为占用
                    column[j] = rup[i+j] = lup[i-j+n] = 1;
                    backtrack(i+1);  //循环调用
                    column[j] = rup[i+j] = lup[i-j+n] = 0;
                }
            }
        }
    }

    protected void showAnswer() {
        num++;
        System.out.println("\n解答" + num);
        for (int y = 1; y <= n; y++) {
            for (int x = 1; x <= n; x++) {
                if(queen[y]==x) {
                    System.out.print("Q");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

}