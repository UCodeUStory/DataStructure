package com.wangpos.datastructure.leetcode

/**
 * 给定一个字符串 s 和 t
 *
 * 判断t 是否是s的异位词
 *
 * 假设字母都是小写的
 *
 * 异位词 就是相同的字符数量
 */
fun main() {

    val result = isEctopicWords("ahelloworld", "aworldhello")
    println(result)
}

fun isEctopicWords(t: String, s: String): Boolean {
    var arrays = IntArray(26)
    val tArray = t.toCharArray()
    val sArray = s.toCharArray()
    if (t.length != s.length) {
        return false
    }

    for (i in 0 until t.length) {
        arrays[tArray[i] - 'a'] += 1
        arrays[sArray[i] - 'a'] -= 1
    }

    arrays.forEach {
        if (it != 0) {
            return false
        }
    }
    return true
}