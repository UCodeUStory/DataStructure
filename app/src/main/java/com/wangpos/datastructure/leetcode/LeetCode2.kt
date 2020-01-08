package com.wangpos.datastructure.leetcode

/**
 *
 *
 */
fun main() {

    val l1 = ListNode(2)
    l1?.next = ListNode(4)
    l1?.next?.next = ListNode(3)

    val l2 = ListNode(5)
    l2.next = ListNode(6)
    l2?.next?.next = ListNode(4)

    val result = addTwoNumbers(l1, l2)

    if (result != null) {
        printNode(result)
    }


}

fun printNode(node: ListNode) {
    if (node != null) {
        print(node.`val`)
    }
    node.next?.let { printNode(it) }
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
    var currentL1Node = l1
    var currentL2Node = l2
    var result: ListNode? = null
    var currentResult: ListNode? = result
    //进位存储
    var tempResult = 0
    //终止条件，其中有一个不为null就可以循环
    while (currentL1Node != null || currentL2Node != null) {
        var resultValue = 0
        if (tempResult != 0) {
            resultValue += tempResult
            tempResult = 0
        }
        if (currentL1Node != null) {
            resultValue += currentL1Node.`val`
        }
        if (currentL2Node != null) {
            resultValue += currentL2Node.`val`
        }
        if (resultValue > 9) {
            resultValue -= 10
            tempResult = 1
        }
        val newNode = ListNode(resultValue)
        if (currentResult == null) {
            currentResult = newNode
            result = currentResult
        } else {
            currentResult.next = newNode
        }
        currentResult = newNode
        currentL1Node = currentL1Node?.next
        currentL2Node = currentL2Node?.next
    }
    if (tempResult != 0) {
        currentResult?.next = ListNode(1)
    }
    return result
}