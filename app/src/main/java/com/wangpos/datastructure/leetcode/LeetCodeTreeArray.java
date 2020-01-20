package com.wangpos.datastructure.leetcode;


/**
 * 树状数组
 * 注意：和“堆”一样，“树状数组”的 00 号索引不放置元素，从 11 号索引开始使用。从上图可以观察到，
 * 与数组 C 的某个结点有关的数组 A 的某些结点，它们的索引值之间有如下关系。
 *
 * 初始化策略，通过update 方式，update 更新的是差值
 *
 * parentIndex =   i + lowbit(i);
 *

 */
public class LeetCodeTreeArray {

    int len = 0;
    int[] tree;

    /**
     * 初始化策略，通过update 方式，update 更新的是差值
     * @param nums
     */
    LeetCodeTreeArray(int nums[]) {
        this.len = nums.length + 1;
        tree = new int[this.len + 1];
        for (int i = 1; i <= len; i++) {
            update(i, nums[i-1]);
        }
    }

    public static void main(String[] args) {
        System.out.println("Test");

        int nums[]= {1,2,3,4,5};
        new LeetCodeTreeArray(nums);
    }

    /**
     * @param x 返回 2^k  每个tree的下标元素个数
     * @return
     */
    public static int lowbit(int x) {
        return x & (-x);
    }

    /**
     * 单点更新
     *
     * @param i     原始数组索引 i
     * @param delta 变化值 = 更新以后的值 - 原始值
     *
     *   parent(i)=i+lowbit(i)
     *
     *  先看 C[3] ，lowbit(3) = 1， 3 + lowbit(3) = 4 就是 C[3] 的父亲结点 C[4] 的索引值。
     *
     * 再看 C[4] ，lowbit(4) = 4， 4 + lowbit(4) = 8 就是 C[4] 的父亲结点 C[8] 的索引值。

     */
    public void update(int i, int delta) {
        // 从下到上更新，注意，预处理数组，比原始数组的 len 大 1，故 预处理索引的最大值为 len
        //i = 0 位置更新后面的都需要更新 C0 = 原始数据 +变化值
        while (i <= len) {
            tree[i] += delta;//当前元素增加delta, tree[i]不管是一系列元素，还是一个元素都是增加一样的
            //获取parent 将parent 同样加上偏移量
            i = getParentIndex(i);
        }
    }

    private int getParentIndex(int i) {
        i += lowbit(i);
        return i;
    }

    /**
     * 查询前缀和
     *
     * @param i 前缀的最大索引，即查询区间 [0, i] 的所有元素之和
     */
    public int query(int i) {
        // 从右到左查询
        int sum = 0;
        while (i > 0) {
            sum += tree[i];
            i -= lowbit(i);
        }
        return sum;
    }

}


/**
 *
 *
 * 使用 lowbit 实现“前缀和查询”
 * lowbit 可以帮助我们计算前缀和由预处理数组的哪些元素表示。
 *
 * 例：计算前 66 个元素的“前缀和”。
 *
 * 由“图 3”可以看出前 66 个元素的“前缀和” = C[6] + C[4]。
 *
 * 你可以验证一下：
 *
 * C[6] = C[5] + A[6]、C[5] = A[5]，而 C[4] = A[1] + A[2] + A[3] + A[4] 。
 *
 * 先看 C[6] ，lowbit(6) = 2， 6 - lowbit(6) = 4 正好是 C[6] 的上一个非叶子结点 C[4] 的索引值。
 *
 * 可以这样理解：
 *
 * 数组 C 的结点的下标表示了数组 C 的元素来自多少个数组 A 的元素，也可以理解成高度，那么上一个非叶子结点，其实就是从右边向左边画一条水平线，遇到的墙的索引值。
 *
 * 下面我们使用“前缀和(i)”表示前 i 个元素的“和”。
 *
 * 例：计算前 55 个元素的“前缀和”。
 *
 * 再看 C[5]，lowbit(5) = 1， 5 - lowbit(6) = 4 正好是 C[5] 的上一个非叶子结点 C[4] 的索引值，故“前缀和(5)” = C[5] + C[4]。
 *
 * 例：计算前 77 个元素的“前缀和”。
 *
 * 再看 C[7]，lowbit(7) = 1， 7 - lowbit(7) = 6 正好是 C[7] 的上一个非叶子结点 C[6] 的索引值，“前缀和(7)” = C[7] + C[6] + C[4]。
 *
 * 例：计算前 88 个元素的“前缀和”。
 *
 * 再看 C[8]，lowbit(8) = 8， 8 - lowbit(8) = 0， 0 表示没有，从“图 3”也可以看出从右边向左边画一条水平线，不会遇到的墙，故“前缀和(8)” = C[8]。

 */