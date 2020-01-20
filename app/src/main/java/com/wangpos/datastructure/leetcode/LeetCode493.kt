package com.wangpos.datastructure.leetcode

import java.util.*

/**
 * 给定一个数组 nums ，如果 i < j 且 nums[i] > 2*nums[j] 我们就将 (i, j) 称作一个重要翻转对。
 *
 * 使用归并排序可以求解
 *
 * 每次二分后 到最后先比对两个集合是否存在翻转对，然后在进行排序，在每个小的集合中逐渐求逆序对，
 *
 * 如果因为两个排好序的数组逆序对比较好求，比如A数组中都是递增的，如果其中第i 大于B中的2被，则第i+1 到后面的都是翻转对，也就是size-i个
 *
 *
 *
 */
fun main() {
    val leetCode = LeetCode493()
    val leftArray = intArrayOf(3, 4, 5)
    val rightArray = intArrayOf(1, 2, 3)
    val counts = IntArray(1)
//    leetCode.countReverse(leftArray,rightArray,counts)

    val nums = intArrayOf(2147483647, 2147483647, 2147483647, 2147483647, 2147483647, 2147483647)

    val numsSize = intArrayOf(2147483647,2147483647,-2147483647,-2147483647,-2147483647,2147483647)
    val sortArray = leetCode.mergeSort(nums, 0, nums.size - 1, counts)
    println(Arrays.toString(sortArray))
    println(counts[0])
}

class LeetCode493 {

    fun mergeSort(nums: IntArray, l: Int, h: Int, counts: IntArray): IntArray {
        if (l == h)
            return intArrayOf(nums[l])

        val mid = l + (h - l) / 2
        val leftArr = mergeSort(nums, l, mid, counts) //左有序数组
        val rightArr = mergeSort(nums, mid + 1, h, counts) //右有序数组
        val newNum = IntArray(leftArr.size + rightArr.size) //新有序数组

        countReverse(leftArr, rightArr, counts)

        var m = 0
        var i = 0
        var j = 0
        /**
         * 两个数组 前一半比后一半小
         */
        while (i < leftArr.size && j < rightArr.size) {
            newNum[m++] = if (leftArr[i] < rightArr[j]) leftArr[i++] else rightArr[j++]
        }
        //左边剩余
        while (i < leftArr.size)
            newNum[m++] = leftArr[i++]
        //右边剩余
        while (j < rightArr.size)
            newNum[m++] = rightArr[j++]


        return newNum
    }

    fun countReverse(leftArr: IntArray, rightArr: IntArray, counts: IntArray) {
        val leftArrSize = leftArr.size
        for (i in rightArr.indices) {
//            println("$i")
            for (j in leftArr.indices) {
//                println("j=$j")
                val rightValue = rightArr[i].toLong()
                val dRightValue = 2 * rightValue
                if (leftArr[j] > dRightValue) {
                    counts[0] += (leftArrSize - j)
//                    println("a=$j ${counts[0]}")
                    break
                }
            }
        }
    }

}
