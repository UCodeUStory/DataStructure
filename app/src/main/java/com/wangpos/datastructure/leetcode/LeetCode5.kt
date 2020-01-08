package com.wangpos.datastructure.leetcode

import java.util.*

fun main() {

    val s = "abcdssbasdsdf"

    val resultString = findLongest(s)

    println("最大回文子串 ${resultString}")
}

/**
 *
1 0 0 0 0 0 0 1 0 0 0 0 0
0 1 0 0 0 0 1 0 0 0 0 0 0
0 0 1 0 0 0 0 0 0 0 0 0 0
0 0 0 1 0 0 0 0 0 1 0 1 0
0 0 0 0 1 1 0 0 1 0 1 0 0
0 0 0 0 1 1 0 0 1 0 1 0 0
0 1 0 0 0 0 1 0 0 0 0 0 0
1 0 0 0 0 0 0 1 0 0 0 0 0
0 0 0 0 1 1 0 0 1 0 1 0 0
0 0 0 1 0 0 0 0 0 1 0 1 0
0 0 0 0 1 1 0 0 1 0 1 0 0
0 0 0 1 0 0 0 0 0 1 0 1 0
0 0 0 0 0 0 0 0 0 0 0 0 1
3 11
最大回文子串 dssbasdsd
 */
fun findLongest(s: String): String {

    val size = s.length

    val array = Array(size) { IntArray(size) }

    var maxLength = 0
    var maxStart = 0
    var maxEnd = 0
    for (i in 0 until s.length) {
        for (j in 0 until s.length) {
            if (s[i] == s[j]) {
                array[i][j] = 1
                if (i != j) {
                    if ((j - i) > maxLength) {
                        maxLength = (j - i)
                        maxStart = i
                        maxEnd = j
                    }
                }
            }

        }
    }

    for (i in 0 until s.length) {
        for (j in 0 until s.length) {
            print(array[i][j])
            print(" ")
        }
        println()
    }

    println("${maxStart} ${maxEnd}")
    var resultString = ""
    for (i in maxStart..maxEnd) {
        resultString += s[i]
    }
    return resultString
}
