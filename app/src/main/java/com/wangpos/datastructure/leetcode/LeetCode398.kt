package com.wangpos.datastructure.leetcode

import java.util.*


/**
 * 给定一个可能含有重复元素的整数数组，要求随机输出给定的数字的索引。 您可以假设给定的数字一定存在于数组中。
 */
fun main() {

    val array = arrayOf(1,2,3,4,4,4)
    val solution398 = Solution398(array.toIntArray())

    println("结果：${solution398.pick(4)}")
}

internal class Solution398(nums: IntArray) {
    var mp = mutableMapOf<Int, MutableList<Int>>()

    init {
        for (i in nums.indices) {
            if (mp.containsKey(nums[i]) === false) {
                val list = ArrayList<Int>()
                list.add(i)
                mp[nums[i]] = list
            } else {
                mp[nums[i]]?.add(i)
            }
        }

    }

    fun pick(target: Int): Int {
        val r = Random()
        val index = mp[target]!!.size
        val random = r.nextInt(index)
        println(random)
        return mp[target]!!.get(random)
    }
}

