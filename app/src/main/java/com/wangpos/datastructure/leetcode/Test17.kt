package com.wangpos.datastructure.leetcode

import java.util.*

fun main() {

    val array = arrayOf(1,2,3,4).toIntArray()
    val count = Test17().decompressRLElist(array)

//    println(Arrays.toString(count))
//
    val arrayMatrix = arrayOf(arrayOf(1,2,3).toIntArray(),
        arrayOf(4,5,6).toIntArray(),arrayOf(7,8,9).toIntArray())

    val resultArray = Test17().matrixBlockSum(arrayMatrix,1)

    resultArray.forEach {
        println(Arrays.toString(it))
    }
}