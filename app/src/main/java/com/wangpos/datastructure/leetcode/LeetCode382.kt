package com.wangpos.datastructure.leetcode

import java.util.*

/**
 * 给定一个单链表，随机选择链表的一个节点，并返回相应的节点值。保证每个节点被选的概率一样。

进阶:
如果链表十分大且长度未知，如何解决这个问题？你能否使用常数级空间复杂度实现？

 */
fun main() {
    val head =  ListNode(1)
    head.next =  ListNode(2)
    head.next!!.next =  ListNode(3)

    val solution = Solution382(head)

    println("结果:${solution.random}")
}


internal class Solution382(private val head: ListNode) {

    val random: Int
        get() {
            var res = head.`val`
            var no = head.next
            var i = 2
            val random = Random()
            while (no != null) {
                if (random.nextInt(i) === 0) {
                    res = no.`val`
                }
                i++
                no = no.next
            }
            return res

        }
}