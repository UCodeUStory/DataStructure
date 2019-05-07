package com.wangpos.datastructure.algorithm

import kotlin.reflect.KFunction3

/**
 *
 * 穷举加分治法，子问题就是求两个数的四则运算
 */
fun main(args: Array<String>) {

    var arrays = arrayOf(::add, ::sub, ::multiply,::divide)
    var nums = arrayListOf<Number>(
        Number(3.toDouble(), "3"),
        Number(2.toDouble(), "2"),
        Number(6.toDouble(), "6"),
        Number(7.toDouble(), "7")
    )
    Calc24(nums, arrays)

}

class Number(var num: Double, var num_str: String)

fun add(number1: Number, number2: Number, next: (Number) -> Unit): Boolean {
    var result = number1.num + number2.num
    var newNumber = Number(result, "("+number1.num_str + "+" + number2.num_str+")")
    next(newNumber)
    return true
}

fun sub(number1: Number, number2: Number, next: (Number) -> Unit): Boolean {
    var result = number1.num - number2.num
    var newNumber = Number(result, "("+number1.num_str + "-" + number2.num_str+")")
    next(newNumber)
    return true
}

fun multiply(number1: Number, number2: Number, next: (Number) -> Unit): Boolean {
    var result = number1.num * number2.num
    var newNumber = Number(result, "("+number1.num_str + "*" + number2.num_str+")")
    next(newNumber)
    return true

}

fun divide(number1: Number, number2: Number, next: (Number) -> Unit): Boolean {
    if (number2.num == 0.toDouble()) return false
    var result = number1.num / number2.num
//    println("////$result")
    var newNumber = Number(result, "("+number1.num_str + "/" + number2.num_str+")")
    next(newNumber)
    return true
}

fun Calc24(
    nums: ArrayList<Number>,
    operations: Array<KFunction3<@ParameterName(name = "number1") Number, @ParameterName(
        name = "number2"
    ) Number, @ParameterName(name = "next") (Number) -> Unit, Boolean>>
) {
    if (nums.size == 1) {
        if (nums[0].num.toInt() == 24) {
            println(nums[0].num_str + "=" + nums[0].num)
        }
        return
    }
//    println("---------")
//    nums.forEach {
//        println(it.num_str + "=" + it.num)
//    }
//    println("---------")

    for (i in nums.indices) {
        for (j in nums.indices) {
            if (i == j) continue

            operations.forEach {
                var newNumber: Number? = null
                if (it(nums[i], nums[j]) {
                        newNumber = it
                    }) {
                    var list = arrayListOf<Number>()

                    // 新数据添加集合
                    newNumber?.let { it1 -> list.add(it1) }

                    for (k in nums.indices) {
                        if (k == i || k == j) continue
                        // 将剩余数添加到集合
                        list.add(nums[k])
                    }

                    Calc24(list, operations)
                }
            }
        }
    }
}

