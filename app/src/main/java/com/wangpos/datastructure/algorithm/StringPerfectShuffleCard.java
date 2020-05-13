package com.wangpos.datastructure.algorithm;

import java.util.Arrays;

public class StringPerfectShuffleCard {
    public static int N;
    public static char chas[];

    public static void main(String args[]) {
        String str = "12345678";
        chas = str.toCharArray();
        N = str.length() / 2;
        System.out.println(shuffleCard(str));
    }

    public static String shuffleCard(String str) {

        for (int i = 1; i < N; ) {
            int startIndex = i;
            int curIndex = -1;
            char currentValue = ' ';
            while (curIndex != startIndex) {
                if (curIndex == -1) {
                    curIndex = startIndex;
                    int nextIndex = getNextPosition(curIndex);
                    currentValue = chas[nextIndex];
                    chas[nextIndex] = chas[curIndex];
                    curIndex = nextIndex;
                } else {
                    int nextIndex = getNextPosition(curIndex);
                    char nextValue = chas[nextIndex];
                    chas[nextIndex] = currentValue;
                    currentValue = nextValue;
                    curIndex = nextIndex;
                }
            }

            i = i + 2;
        }

        return Arrays.toString(chas);
    }

    private static char getValue(int curIndex) {
        return chas[curIndex];
    }

    public static int getNextPosition(int postion) {
        if (postion < N) {
            return 2 * postion;
        } else {
            return 2 * (postion - N) + 1;
        }
    }
}
