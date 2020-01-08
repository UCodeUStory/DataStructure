package com.wangpos.datastructure.leetcode


/**
 * 7. 整数反转
 */
fun main() {

    val number = -123

    val result = reverse(number)

    println("结果：${result}")
}

//fun reverse(x: Int): Int {
//
//    var s = ""
//    var target = x.toString()
//    if (x < 0) {
//        target = target.removePrefix("-")
//        target = target.trim()
//    }
//    for (i in 0 until target.length) {
//        s = target[i] + s
//    }
//
//
//    if (s.length == 0) {
//        s = "0"
//    }
//    if (s.toLong() > Integer.MAX_VALUE) {
//        return 0
//    }
//
//    if (x < 0) {
//        s = "-" + s
//    }
//    return s.toInt()
//
//}

fun reverse(x: Int): Int {
    var x = x
    var ans = 0
    while (x != 0) {
        if (ans * 10 / 10 != ans) {
            ans = 0
            break
        }
        ans = ans * 10 + x % 10
        x = x / 10
    }
    return ans
}