package com.wangpos.datastructure.leetcode

import android.R.string


fun main() {

    val testCase = "LEETCODEISHIRING"


    val result = convert(testCase, 3)

//    convert(testCase, 10)
//    convert(testCase, 11)
//    convert(testCase, 12)
//    convert(testCase, 13)
//    convert(testCase, 14)
//    convert(testCase, 15)
//    convert(testCase, 16)
    println("结果：${result}")
}


fun convert(s: String, numRows: Int): String {

    val size = s.length

    if (numRows == 1 || s.length <2) {
        return s
    }

    //有几个循环单位
    var d = (size - numRows) / (2 * numRows - 2)

    val yushu = (size - numRows) % (2 * numRows - 2)


    /**
     * 每一列
     *
     */

    var result = ""

//    println(d)
    var index = 0
    /**
     * 判断余数是否满足顶层
     */
    if (yushu >= (2 * numRows - 2) / 2) {
        d++
    }
    // 层数
    var b = 0
    while (b < numRows) {
        var index = 0
        while (index <= d) {
            if (b == 0) {
                result += s[(numRows - 1) * 2 * index]
            } else {
                if (index == 0) {
                    println("2${index} ${d} ${b}")
                    if(((numRows - 1) * 2 * index + b)<s.length) {
                        result += s[(numRows - 1) * 2 * index + b]
                    }

                } else {
                    println("3${index} ${d} ${b}")
                    if (b != numRows - 1) {
                        result += s[(numRows - 1) * 2 * index - b]
                    }
                    if (((numRows - 1) * 2 * index + b) < s.length) {
                        result += s[(numRows - 1) * 2 * index + b]
                    }
                }

                if (index == d && b != numRows - 1) {
//                        println("b${b} index${index} s-length=${(s.length - 1)}")
                    val moreLeft = ((numRows - 1) * 2 * (index + 1) - b)
                    val moreDown = ((numRows - 1) * 2 * (index + 1) + b)
                    println(moreLeft)
                    println(moreDown)
                    if (moreLeft < (s.length)) {
                        result += s[moreLeft]
                    }
                    if (moreDown < (s.length)) {
                        result += s[moreDown]
                    }
                }
            }
            index++
        }
        b++
    }
//    println("d=${d},total=${total}")
    return result

}