package com.wangpos.datastructure.leetcode

import java.util.*


/**
 * 两数之和
 *
 * 暴力方法时间复杂度是O(n2)
 *
 * 也可以通过hash表降低查找时间，使其变成O(n),使用hash表注意给定数组中不能出现重复元素
 */

fun main() {
    val nums = arrayOf(1, 2, 3, 4, 5, 6)
    val target = 9
    val resultArray = twoSum(nums, target)
    println("结果：${Arrays.toString(resultArray)}")
}

/**
 * 这种方法找不到所有的元素，只能找到一个，当存在里面有重复元素或不止一种情况
 */
fun twoSum(nums: Array<Int>, target: Int): IntArray {
    // 一个hashMap存一个value 和position
    val map = mutableMapOf<Int, Int>()
    for (i in 0 until nums.size) {
        val targetData = target - nums[i]
        if (map.containsKey(targetData)) {
            val resultArray = IntArray(2)
            resultArray[0] = map[targetData]!!
            resultArray[1] = i
            return resultArray
        }
        map.put(nums[i], i)
    }
    throw  IllegalArgumentException("No two sum solution")
}

