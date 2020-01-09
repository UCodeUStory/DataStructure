package com.wangpos.datastructure.leetcode

import java.util.*


fun main() {
    val obj = LeetCode478(10.0,0.0,0.0)

    println("结果：${Arrays.toString(obj.randPoint())}")
}
internal class LeetCode478(var rad: Double, var xc: Double, var yc: Double) {

    fun randPoint(): DoubleArray {
        val x0 = xc - rad
        val y0 = yc - rad

        while (true) {
            val xg = x0 + Math.random() * rad * 2.0
            val yg = y0 + Math.random() * rad * 2.0
            if (Math.sqrt(Math.pow(xg - xc, 2.0) + Math.pow(yg - yc, 2.0)) <= rad)
                return doubleArrayOf(xg, yg)
        }
    }
}
