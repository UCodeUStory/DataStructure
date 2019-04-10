package com.wangpos.datastructure.algorithm

/**
 * 分治法，顾名思义分而治之，将无法着手的大问题分解成一系列相同的小问题，然后逐一解决
 *
 * 分治算法基本上可以归纳为三个步骤
 *
 * 1. 分解：将问题分解为若干规模小的相同结构问题
 *
 * 2. 解决：如果上一步问题可以解决就解决，否则，对每个子问题使用和上一步相同的方法再次分解，然后求解分解后的子问题，这个过程可能是一个递归的过程
 *
 * 3. 合并：将上一步解决的各个子问题的解通过某种规则合并起来，得到原问题的解
 *
 * 分治法难点，如果将子问题分解，并将子问题的解合并，针对不同的问题，有不同的分解与合并的方式
 *
 * 递归作为一种算法的实现方式，与分治是一✅天然的好朋友。当然分治也可以用非递归方式实现出来，就是比较难
 *
 *
 */

fun main(args: Array<String>) {

    var arrays = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    var target = 7

    var index = binearySearch(arrays, 0, arrays.size, target)

    println("被查询的对象：$target")
    println("查询到的位置：$index")
    println("查询到的数据：${arrays[index]}")
}


/**
 * 二分法查找
 */
fun binearySearch(arrays: Array<Int>, start: Int, end: Int, target: Int): Int {

    var middleIndex = (start + end) / 2
    var middleValue = arrays[middleIndex]

    if (target == middleValue) {
        return middleIndex
    } else if (target < middleValue) {
        return binearySearch(arrays, start, middleIndex - 1, target)
    } else if (target > middleValue) {
        return binearySearch(arrays, middleIndex + 1, end, target)
    }
    return -1
}

