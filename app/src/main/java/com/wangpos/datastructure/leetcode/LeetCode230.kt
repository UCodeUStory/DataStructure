package com.wangpos.datastructure.leetcode

/**
LeetCode 第 230 题

给定一个二叉搜索树，编写一个函数 kthSmallest 来查找其中第 k 个最小的元素。

说明：你可以假设 k 总是有效的，1 ≤ k ≤ 二叉搜索树元素个数。


解题思路

这道题考察了两个知识点：
二叉搜索树的性质
二叉搜索树的遍历

二叉搜索树的性质：对于每个节点来说，该节点的值比左孩子大，比右孩子小，而且一般来说，二叉搜索树里不出现重复的值。

二叉搜索树的中序遍历是高频考察点，节点被遍历到的顺序是按照节点数值大小的顺序排列好的。即，中序遍历当中遇到的元素都是按照从小到大的顺序出现。

因此，我们只需要对这棵树进行中序遍历的操作，当访问到第 k 个元素的时候返回结果就好。
 */
fun main() {

    val array = arrayOf(5, 3, 6, 2, 4, 1)
    val tree = buildTree(array)

    // [1, 2, 3, 4, 5, 6]
    // 3
    println(kthSmallest(tree, 3))

}


fun kthSmallest(tree: Tree, k: Int): Int {
    val resultList = arrayListOf<Int>()
    findTree(tree, k, resultList)

    println(resultList)
    return resultList[k - 1]
}

//利用中序遍历
fun findTree(root: Tree, k: Int, result: ArrayList<Int>) {
//    if (result.size == k) {//找到第k就不要找了
//        return
//    }
    root.left?.let { findTree(it, k, result) }
    result.add(root.data)
    root.right?.let { findTree(it, k, result) }
}


fun buildTree(array: Array<Int>): Tree {
    var root: Tree? = null
    array.forEach {
        if (root == null) {
            root = Tree(data = 5)
        } else {
            addTree(root!!, it)
        }
    }
//
//    root?.let { printTree_ROOT_LEFT_RIGHT(it) }
//    println()
//    root?.let { printTree_LEFT_ROOT_RIGHT(it) }
//    println()
//    root?.let { printTree_LEFT_RIGHT__ROOT(it) }

    return root!!
}

//先序遍历
fun printTree_ROOT_LEFT_RIGHT(root: Tree) {
    print(root.data)
    root.left?.let { printTree_ROOT_LEFT_RIGHT(it) }
    root.right?.let { printTree_ROOT_LEFT_RIGHT(it) }
}

//中序遍历
fun printTree_LEFT_ROOT_RIGHT(root: Tree) {
    root.left?.let { printTree_LEFT_ROOT_RIGHT(it) }
    print(root.data)
    root.right?.let { printTree_LEFT_ROOT_RIGHT(it) }
}

//后序遍历
fun printTree_LEFT_RIGHT__ROOT(root: Tree) {
    root.left?.let { printTree_LEFT_RIGHT__ROOT(it) }
    root.right?.let { printTree_LEFT_RIGHT__ROOT(it) }
    print(root.data)
}

fun addTree(root: Tree, it: Int) {

    if (it > root.data) {
        addRight(root, it)
    } else {
        addLeft(root, it)
    }
}

fun addLeft(root: Tree, it: Int) {
    if (root.left != null) {
        addTree(root.left!!, it)
    } else {
        root.left = Tree(data = it)
    }

}

private fun addRight(root: Tree, it: Int) {
    if (root.right != null) {
        addTree(root.right!!, it)
    } else {
        root.right = Tree(data = it)
    }
}

class Tree(var left: Tree? = null, var data: Int, var right: Tree? = null)