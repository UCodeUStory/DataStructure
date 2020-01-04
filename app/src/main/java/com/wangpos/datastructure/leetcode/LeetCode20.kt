package com.wangpos.datastructure.leetcode

import java.util.*

/**
 *栈的最大特点就是后进先出（LIFO）。对于栈中的数据来说，所有操作都是在栈的顶部完成的，只可以查看栈顶部的元素，只能够向栈的顶部压⼊数据，也只能从栈的顶部弹出数据。
 *
 * 应用场景：在解决某个问题的时候，只要求关心最近一次的操作，并且在操作完成了之后，需要向前查找到更前一次的操作。
 *
 * 第 20 题：给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 *
 */
fun main() {

    val rightStr = "{[()]}"
    val errorStr = "{}{{))"
    var result = isValid(rightStr)

    println("result = ${result}")
}

fun isValid(data: String): Boolean {

    var charArray = data.toCharArray()
    val stack = Stack<Char>()
    charArray.forEach {
        if (stack.empty()) {
            stack.push(it)
        } else {
            if (checkRight(stack, it)) {//it-1表示是一对的
                stack.pop()
            } else {
                stack.push(it)
            }
        }
    }

    if (stack.isEmpty()) {
        return true
    }
    return false
}

fun checkRight(stack: Stack<Char>, it: Char): Boolean {

    if (stack.peek() == '{' && it == '}') {
        return true
    }
    if (stack.peek() == '[' && it == ']') {
        return true
    }
    if (stack.peek() == '(' && it == ')') {
        return true
    }
    return false

}
