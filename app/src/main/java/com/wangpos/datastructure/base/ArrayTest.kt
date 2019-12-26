package com.wangpos.datastructure.base

import com.wangpos.datastructure.algorithm.swap
import java.util.*


/**
 * 数组和字符串
 *
 * 数组的优缺点
 * 能够在O(1)的情况查询一个元素
 * 构建必须是连续的空间
 * 删除和添加某一个元素必须遍历整个表O(n)
 */
fun main() {
    println("hello World")

    val data = "helloWorld"

    val result = reverseString(data)
    val result2  = reverseCharArray(data)
    println(result)
    println(result2)
}

/**
 * 反转一个字符串
 */
fun reverseString(data: String): String {
    var headerIndex = 0
    var footerIndex = data.length - 1
    val dataArray = data.toCharArray()

    while (headerIndex != footerIndex && headerIndex < footerIndex) {
        swap(headerIndex, footerIndex, dataArray)
        headerIndex++
        footerIndex--
    }
    val a = dataArray.toString()
    var result = ""
    for(i in 0 until dataArray.size){
        result += dataArray[i]
    }
    return result
}

fun swap(headerIndex: Int, footerIndex: Int, dataArray: CharArray) {
    val tempData = dataArray[footerIndex]
    dataArray[footerIndex] = dataArray[headerIndex]
    dataArray[headerIndex] = tempData
}


fun reverseCharArray(data: String):String {
    val array = data.toCharArray()
    var reverse = ""
    for (i in array.size - 1 downTo 0) {
        reverse +=  array[i]
    }

    return reverse
}


