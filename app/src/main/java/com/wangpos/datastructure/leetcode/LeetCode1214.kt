package com.wangpos.datastructure.leetcode

import java.util.*

fun main() {

    val root1Array = arrayOf(0, -10, 10)

    val root2Array = arrayOf(5, 1, 7, 0, 2)

    val target = 18

    var root1: TreeNode? = null
    var root2: TreeNode? = null
    root1Array.forEach {
        if (it != null) {
            if (root1 == null) {
                root1 = TreeNode(it)
            } else {
                createBinearySearchTree(root1!!, it!!)
            }
        }
    }

    root2Array.forEach {
        if (it != null) {
            if (root2 == null) {
                root2 = TreeNode(it)
            } else {
                createBinearySearchTree(root2!!, it!!)
            }
        }
    }

//    root1?.let { printNode(it) }
//    println()
//    root2?.let { printNode(it) }

//    val result = twoSumBSTs(root1, root2, target)

//    val result = twoSumBSTs2(root1, root2, target)
    val result = LeetCode1214().twoSumBSTs(root1,root2,target);
    println("结果：${result}")
}

/**
 * 这种双层嵌套效率比较低，中序遍历时间复杂度是O(n) 后面的查找属于也是中序遍历所以O(n*n)
 *
 */
fun twoSumBSTs(root1: TreeNode?, root2: TreeNode?, target: Int): Boolean {
    searchRoot1(root1, root2, target)
    return searchResult
}

fun searchRoot1(root1: TreeNode?, root2: TreeNode?, target: Int) {

    if (root1 == null || searchResult) {
        return
    }
    searchRoot1(root1?.left, root2, target)
    if (root1.`val` > target) {
        return
    }
    searchRoot2(root2, target - root1.`val`)
    searchRoot1(root1?.right, root2, target)
}

var searchResult = false
fun searchRoot2(root2: TreeNode?, i: Int) {
    if (root2 == null || searchResult) {
        return
    }
    searchRoot2(root2.left, i)
    if (root2.`val` == i) {
        searchResult = true
        return
    }
    if (root2.`val` > i) {
        return
    }
    searchRoot2(root2.right, i)
}

/**
 * 将两个树进行中序遍历，和逆向中序，时间复杂度 O(n)*2
 * 然后放入两个栈中，得到两个一个从小到大，一个从大到小的
 *
 * 然后遍历两个栈，如果相加之和小于t,就从最小的栈去继续找，反之从大的栈中找
 *
 *
 */
fun twoSumBSTs2(root1: TreeNode?, root2: TreeNode?, target: Int): Boolean {

    val stack1 = Stack<Int>()//从小到大 top最大
    val stack2 = Stack<Int>()//从大到小
    searchRoot11(root1, stack1)
    searchRoot22(root2, stack2)
    var t = 0
    var small = stack1.pop()
    var large = stack2.pop()
    while (stack1.isNotEmpty() || stack2.isNotEmpty()) {
        t = small + large
        println(small)
        println(large)
        if (t == target) {
            return true
        } else if (t > target) {
            // 取较小的
            if(stack1.isNotEmpty()) {
                small = stack1.pop()
            }
        } else {
            // 取较大的
            if(stack1.isNotEmpty()) {
                large = stack2.pop()
            }
        }
    }
    return false
}

fun searchRoot11(root1: TreeNode?, stack: Stack<Int>) {
    if (root1 == null) {
        return
    }
    searchRoot11(root1.left, stack)
    stack.add(root1.`val`)
    searchRoot11(root1.right, stack)
}

fun searchRoot22(root2: TreeNode?, stack: Stack<Int>) {
    if (root2 == null) {
        return
    }
    searchRoot22(root2.right, stack)
    stack.add(root2.`val`)
    searchRoot22(root2.left, stack)
}