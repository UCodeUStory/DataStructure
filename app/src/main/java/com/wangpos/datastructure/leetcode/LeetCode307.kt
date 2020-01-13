package com.wangpos.datastructure.leetcode

/**
 * 给定一个整数数组  nums，求出数组从索引 i 到 j  (i ≤ j) 范围内元素的总和，包含 i,  j 两点。

update(i, val) 函数可以通过将下标为 i 的数值更新为 val，从而对数列进行修改。


频繁计算总和可以用这总结构，同时这种结构更新效率不再是O(1)
 */
fun main() {


}

class NumArray(nums: IntArray) {

    lateinit var tree: IntArray

    init {
        if (nums.size > 0) {
            n = nums.size
            tree = IntArray(n * 2)
            buildTree(nums)
        }
    }

    private fun buildTree(nums: IntArray) {
        var i = n;
        var j = 0;
        while (i < 2 * n) {
            //初始化叶子节点
            tree[i] = nums[j]
            i++
            j++
        }

        i = n-1
        //初始化和
        while (i>0){
            tree[i] = tree[i * 2] + tree[i * 2 + 1]
            i--
        }
    }

    fun update(i: Int, `val`: Int) {

    }

    fun sumRange(i: Int, j: Int): Int {

    }

}


/**
 *      一般方式 sumRang 时间复杂度为O(n)
 *
private int[] nums;
public int sumRange(int i, int j) {
int sum = 0;
for (int l = i; l <= j; l++) {
sum += data[l];
}
return sum;
}

public int update(int i, int val) {
nums[i] = val;
}
 */

/**
sqrt 分解
其思想是将数组分割成块，块的长度为 sqrt n
​
。然后我们计算每个块的和，并将其存储在辅助存储器 b 中。要查询 RSQ(i, j)，我们将添加位于内部的所有块和部分在范围 [i\ldots j][i…j] 重叠的块的总和。


 */