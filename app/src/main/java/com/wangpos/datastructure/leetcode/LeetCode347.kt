package com.wangpos.datastructure.leetcode


import android.os.Build
import android.support.annotation.RequiresApi
import java.util.*

/**
 *
 * 优先队列
 *
 * 优先队列其实是一个二叉堆的结构，Binary Heap，他利用数组的结构来实现二叉树
 *
 * 换句话说优先队列 本质是一个数组，数组里面每一个元素可能是其他元素父节点，也可能是期他元素子节点，并且每个父节点只能有2个子节点
 *
 * 性质
 * 数组里面array[0] 是优先级最好的
 * 对于array[i]而言
 *
 *  父节点是 (i-1)/2
 *  左孩子 2i + 1
 *  右孩子 2i + 2
 *
 * 数组每个元素的优先级都高于孩子
 *
 *
 * 误区 每往堆里放入数据，都需要进行向上赛选，时间复杂度是nlog(n) 这是错误的，实际上再求极限时，时间复杂度是O(n)
 *
 *
 * LeetCode 347 给一个非空数组，返回一个出现频率k高的元素
 *
 * java的优先队列默认为小顶堆
 */
@RequiresApi(Build.VERSION_CODES.N)
fun main() {

    val nums = arrayOf(1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 6, 6)
    val nums2 = arrayOf(1, 1, 1, 2, 2, 3, 4, 5)

    println("给定数组：${Arrays.toString(nums)}")

    val resultArrays = findPriorityData(nums, 2)

    println("优先级最高的 2 个元素：${Arrays.toString(resultArrays)}")
}


@RequiresApi(Build.VERSION_CODES.N)
fun findPriorityData(nums: Array<Int>, index: Int): IntArray {

    val map = mutableMapOf<Int, Int>()
    countPriority(nums, map)
    val comparator =
        Comparator<Map.Entry<Int, Int>> { entry1: Map.Entry<Int, Int>, entry2: Map.Entry<Int, Int> ->
            //java 优先队列默认是小顶堆，所以这里通过 entry2 - entry1 的优先级得到一个大顶堆
            entry2.value - entry1.value
        }
    val priorityQueue = PriorityQueue<Map.Entry<Int, Int>>(comparator)
    map.forEach {
        priorityQueue.add(it)
    }
    val resultList = mutableListOf<Int>()

    for (k in 0 until index) {
        resultList.add(priorityQueue.poll().key)
    }
    return resultList.toIntArray()
}

private fun countPriority(
    nums: Array<Int>,
    map: MutableMap<Int, Int>
) {
    for (i in nums.indices) {
        if (map.containsKey(nums[i])) {
            val increaseNumber = map[nums[i]]!!.plus(1)
            map[nums[i]] = increaseNumber
        } else {
            map[nums[i]] = 1
        }
    }
}
