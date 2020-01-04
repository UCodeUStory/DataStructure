package com.wangpos.datastructure.leetcode


/**
 *
 * LeetCode 第 25 题：给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 *
 * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 *
 * 说明：
你的算法只能使用常数的额外空间。
你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。

给定这个链表：1-›2-›3-›4-›5
当 k=2 时，应当返回：2-›1-›4-›3-›5
当 k=3 时，应当返回：3-›2-›1-›4-›5


解题思路

这道题考察了两个知识点：
对链表翻转算法是否熟悉
对递归算法的理解是否清晰
 */
fun main() {
    var originNode = Node(1, null)
    var current = originNode
    for (i in 2 until 6) {
        val newNode = Node(i)
        current.next = newNode
        current = newNode
    }
//    printNode(prev)
    val k = 3
    val newNode = reverseGroup(originNode, k)
    newNode?.let { printNode(it) }


}
fun reverseGroup(head: Node?, k: Int): Node? {
    var n = k
    var prev: Node? = null
    var curr: Node? = head

    if (curr != null) {
        //每次反转前检查一下是否满足反转的数量
        var m = n
        var checkCurrent = curr
        while (checkCurrent != null && m-- > 0) {
            checkCurrent = checkCurrent.next
        }
        if (m > -1) {
            return head
        }
    }

    while (curr != null && n-- > 0) {
        val next = curr.next
        curr.next = prev
        prev = curr
        curr = next
    }

    head?.next = reverseGroup(curr, k)
    //每次返回第一次反转后的指针
    return prev

}

fun printNode(prev: Node) {
    println(prev.data)
    if (prev.next != null) {
        printNode(prev.next!!)
    }

}


class Node(var data: Int, var next: Node? = null)

