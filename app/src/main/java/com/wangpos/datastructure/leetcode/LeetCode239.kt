package com.wangpos.datastructure.leetcode

import com.wangpos.datastructure.algorithm.search
import java.util.*
import kotlin.collections.ArrayList


/**
 *
 *
 * LeetCode 第 239 题：给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口 k 内的数字，滑动窗口每次只向右移动一位。返回滑动窗口最大值。

注意：你可以假设 k 总是有效的，1 ≤ k ≤ 输入数组的大小，且输入数组不为空。

示例：给定一个数组以及一个窗口的长度 k，现在移动这个窗口，要求打印出一个数组，数组里的每个元素是当前窗口当中最大的那个数。
输入：nums = [1, 3, -1, -3, 5, 3, 6, 7]，k = 3
输出：[3, 3, 5, 5, 6, 7]
 */

fun main() {

    val nums = intArrayOf(1, 3, -1, -3, 5, 3, 6, 7)

    val k = 3//k = 8代表总长度
    val result = search(nums, k)
    println(result.toString())

}

/**
 * 排序算法通常容易犯错 // val temp = nums[i] 一次访问赋值，记住这里不是指针，Java中没有指针，想要动态获取值，只能通过游标
 */
fun search(nums: IntArray, k: Int): ArrayList<Int> {
    val dQueue = LinkedList<Int>()
    val resultList = arrayListOf<Int>()
    //先将k排序,大到小

    for (i in 0..(k - 2)) { //kotlin 遍历包含右边值，所以要减掉1，因为直接插入排序，少比较一个所以再减1
        // val temp = nums[i]//一次访问赋值，记住这里不是指针，Java中没有指针，想要动态获取值，只能通过游标
        for (j in (i + 1)..(k - 1)) {
            if (nums[j] > nums[i]) {
                val temp = nums[i]
                nums[i] = nums[j]
                nums[j] = temp
            }
        }
    }

    // 将k个放入到队列滑块中,这里可以保证第一个是最大的

    for (i in 0..(k - 1)) {
        dQueue.add(nums[i])
    }
    resultList.add(dQueue.first)

    for (j in k..(nums.size - 1)) {
        var data = nums[j]
        //是否加入队列
        ifAddQueue(dQueue, data)
        //记录最大值，取队头就可以
        resultList.add(dQueue.first)
    }
    return resultList

}

private fun ifAddQueue(dQueue: LinkedList<Int>, data: Int) {
    while (!dQueue.isEmpty()) {
        val minData = dQueue.peekLast()
        if (data > minData) {
            dQueue.removeLast()
        } else {
            // 非空 且小于3 入队列
            if (dQueue.size < 3) {
                dQueue.addLast(data)
            }
            //记住退出，队列没有比他再小的了退出
            break
        }
    }
    //都被移除了，ok 你最大进来吧
    if (dQueue.isEmpty()) {
        dQueue.add(data)
    }
}