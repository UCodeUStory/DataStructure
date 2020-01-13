package com.wangpos.datastructure.leetcode

import java.util.*

/**
 * 现在你总共有 n 门课需要选，记为 0 到 n-1。

在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们: [0,1]

给定课程总量以及它们的先决条件，返回你为了学完所有课程所安排的学习顺序。

可能会有多个正确的顺序，你只要返回一种就可以了。如果不可能完成所有课程，返回一个空数组
 */

fun main() {

    val course = arrayOf(
        arrayOf(1, 0).toIntArray(),
        arrayOf(2, 0).toIntArray(),
        arrayOf(3, 1).toIntArray(),
        arrayOf(3, 2).toIntArray()
    )

    val num = course.size

    //深度优先搜索，判断无环就可以完全修，并且保存所有路径

    //将课程数组转化成邻接矩阵

    //

   val result =  LeetCode210().findOrder(4,course)

    println("result=${Arrays.toString(result)}")


}