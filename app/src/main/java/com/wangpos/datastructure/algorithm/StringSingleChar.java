package com.wangpos.datastructure.algorithm;

public class StringSingleChar {

    public static void main(String[] args) {
        String testStr1 = "aaabbb";
        String testStr2 = "abcd";

        System.out.println(isSingleCharString(testStr1));
        System.out.println(isSingleCharString(testStr2));

        System.out.println(isUnique2(testStr1.toCharArray()));
        System.out.println(isUnique2(testStr2.toCharArray()));
    }

    // O (n)
    public static boolean isSingleCharString(String str) {
        char[] chars = str.toCharArray();
        int[] counts = new int[256];
        for (char aChar : chars) {
            if (counts[aChar] > 0) {
                return false;
            }
            counts[aChar]++;
        }
        return true;
    }

    // 要求空间复杂度O 1
    /**
     * 所有O(n)时间复杂度的排序算法（桶排序，基数排序，计数排序）都需要额外空间排序，所以空间复杂度不是O(1)
     * 那么看O(nlogn)时间复杂度算法 （归并排序，快速排序，希尔排序，堆排序）
     * 归并排序中有连个数组合并成一个数组过程，这个过程需要辅助数组来完成
     * 快速排序额外空间复杂度最低 logN
     * 希尔排序同样也排除，因为他的时间复杂度不固定，最低N*N,取决于步长
     * 堆排序 可以时间复杂度能稳定O(NlogN) 并且空间复杂度是O(1),但是要使用非递归实现
     * 否则会浪费函数栈空间
     *
     * @param chas
     * @return
     */
    //堆排序 O(nlogn)  空间复杂度O(1)
    public static boolean isUnique2(char[] chas) {

        if (chas == null) {
            return true;
        }
        heapSort(chas);
        for (int i = 1; i < chas.length; i++) {
            if (chas[i] == chas[i - 1]) {
                return false;
            }
        }
        return true;
    }

    public static void heapSort(char[] chas) {
        for (int i = 0; i < chas.length; i++) {
            heapInsert(chas, i);
        }
        for (int i = chas.length - 1; i > 0; i--) {
            swap(chas, 0, i);
            heapify(chas, 0, i);
        }
    }

    public static void heapify(char[] chas, int i, int size) {
        int left = i * 2 + 1;
        int right = i * 2 + 2;
        int largest = i;
        while ((left < size)) {
            if (chas[left] > chas[i]) {
                largest = left;
            }
            if (right < size && chas[right] > chas[largest]) {
                largest = right;
            }
            if (largest != i) {
                swap(chas, largest, i);
            } else {
                break;
            }
            i = largest;
            left = i * 2 + 1;
            right = i * 2 + 2;
        }
    }

    public static void heapInsert(char[] chas, int i) {
        int parent = 0;
        while (i != 0) {

            parent = (i - 1) / 2;
            if (chas[parent] < chas[i]) {
                swap(chas, parent, i);
                i = parent;
            } else {
                break;
            }
        }
    }

    public static void swap(char[] chas, int index1, int index2) {
        char tmp = chas[index1];
        chas[index1] = chas[index2];
        chas[index2] = tmp;
    }
}
