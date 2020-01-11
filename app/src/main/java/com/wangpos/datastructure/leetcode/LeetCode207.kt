package com.wangpos.datastructure.leetcode

/**
 * 207. 课程表
 *
 * 现在你总共有 n 门课需要选，记为 0 到 n-1。

在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们: [0,1]

给定课程总量以及它们的先决条件，判断是否可能完成所有课程的学习？

 */
fun main() {

    val data = arrayOf(arrayOf(1,0).toIntArray(),arrayOf(0,1).toIntArray())

    //统计课程安排图中每个节点的入度，生成 入度表 indegrees

    val result = LeetCode207().canFinish(2,data)

    println("结果：${result}")
}