package com.wangpos.datastructure.algorithm


/**
 * 动态规划首先确定子问题，也就是决策问题
 */
fun main(arg: Array<String>) {

    // 第一条线装配站
    var station1 = arrayOf(1, 10, 6, 3, 7, 2)
    // 第二条线装配站
    var station2 = arrayOf(4, 6, 2, 2, 5, 1)

    var time = Array(6) { IntArray(2) }

    dynamic(station1, station2, time)

}

fun dynamic(station1: Array<Int>, station2: Array<Int>, time: Array<IntArray>) {
    for (i in 0..5) {
        if (station1[i] < station2[i]) {
            time[i][0] = station1[i]
        } else {
            time[i][1] = station2[i]
        }
    }

    findSmallTimes(time)
}

fun findSmallTimes(time: Array<IntArray>) {
    time.forEach {
        if (it[0] > 0) {
            println("one ${it[0]}")
        } else {
            println("two ${it[1]}")
        }
    }

}


