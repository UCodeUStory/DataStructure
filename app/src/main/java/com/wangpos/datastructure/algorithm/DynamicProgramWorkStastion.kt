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



/**
用动态规划法设计算法的关键是找出子问题和子问题状态的定义。
子问题的状态就是子问题的最优解，当子问题的规模是最终的问题的时候，
那么其对应的状态就是问题的最优解。基于这一思想，通常选择把问题的规模作为状态变量的方式定义子问题。
以取硬币问题为例，其问题是取 N 元硬币需要的最小硬币数量。
于是我们就选择“取 i（0 ≤ i ≤ N）元硬币需要的最小硬币数量”作为子问题，
并定义状态 d[i] 为取 i 元硬币需要的最小硬币数量。
 */