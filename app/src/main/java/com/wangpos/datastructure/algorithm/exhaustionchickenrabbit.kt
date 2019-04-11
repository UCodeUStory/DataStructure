package com.wangpos.datastructure.algorithm


fun main(args: Array<String>) {

    chickenAndRabbit()
}

/**
 * 鸡的解空间  0 50
 *
 * 兔的解空间 0 30
 */
fun chickenAndRabbit() {
    for (rabbit in 0 until 31) {
        var max = 50 - rabbit
        for (chicken in 0 until max+1) {
            var count = chicken * 2 + rabbit* 4

            if (count == 120 && (chicken + rabbit)==50) {
                println("鸡 $chicken 兔 $rabbit")
            }
        }
    }
}
