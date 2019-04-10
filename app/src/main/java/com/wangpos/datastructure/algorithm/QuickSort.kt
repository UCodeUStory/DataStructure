package com.wangpos.datastructure.algorithm

import android.util.Log
import java.util.*

fun main(args: Array<String>) {

    var arrays = arrayOf(2, 21, 13, 45, 55, 36, 17, 18, 99, 10)

    println("待排序的队列 ${arrays.toList()}")
    quickSort(arrays, 0, arrays.size-1)
    println("已排序的队列 ${arrays.toList()}")

}

fun quickSort(arrays: Array<Int>, start: Int, end: Int) {
    if (start >= end) {
        return
    }
    var flagIndex = quickUnitSort(arrays, start, end)
    quickSort(arrays, start, flagIndex - 1)
    quickSort(arrays, flagIndex + 1, end)
}

fun quickUnitSort(arrays: Array<Int>, start: Int, end: Int): Int {
    var low = start
    var high = end
    var key = arrays[low]
    while (low < high) {
        while (arrays[high] >= key && low < high) {
            --high
        }
        arrays[low] = arrays[high]
        while (arrays[low] <= key && low < high) {
            ++low
        }
        arrays[high] = arrays[low]
    }
    var meetPosition: Int = low
    arrays[meetPosition] = key
    return meetPosition

}


fun swap(arrays: Array<Int>, i: Int, start: Int) {
    var temp = arrays[start]
    arrays[start] = arrays[i]
    arrays[i] = temp
}

