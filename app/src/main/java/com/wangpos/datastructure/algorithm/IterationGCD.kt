package com.wangpos.datastructure.algorithm


/**
 * 迭代算法又称辗转法，一般求解数学问题，如一元高次方程，线性和非线性方程和曲线拟合等问题，一般用来求出解或者近似解
 *
 * 1. 确定迭代变量
 *
 * 2. 确定迭代递推关系
 *
 * 3. 确定迭代终止条件
 *
 *
 *
 *
 * 欧几里得算法，两个整数的最大公约数等于其中较小的那个数和两数相除余数的最大公约数
 *
 * 所以拿到较小的数和余数再次进行这条规则，直到余数为0，返回除数
 *
 * Greatest Common Divisor  最大公约数
 */


fun main(args: Array<String>) {

    var a = 26
    var b = 8

    println("求解 $a 和 $b 的最大公约数")

    println("迭代算法的实现；" + getGCD(a, b))

    println("递归算法的实现；" + getGCDByRecursion(a, b))

}

/**
 * 欧几里得 辗转相除法
 */
fun getGCD(first: Int, second: Int): Int? {

    var a = first
    var b = second

    if (a < b) {
        var temp = a
        a = b
        b = a
    }

    while (b > 0) {//终止条件除数为0
        var c = a % b
        a = b
        b = c
    }

    return a
}

fun getGCDByRecursion(first: Int, second: Int): Int {

    var a = first
    var b = second
    if (a < b) {
        var temp = a
        a = b
        b = a
    }
    var c = a % b

    if (c == 0) {
        return b
    } else {
        return getGCDByRecursion(b, c)
    }
}
