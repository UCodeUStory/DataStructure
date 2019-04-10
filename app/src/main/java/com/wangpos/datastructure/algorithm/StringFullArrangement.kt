package com.wangpos.datastructure.algorithm

import java.util.Collections.swap

/**
 * 分治法，顾名思义分而治之，将无法着手的大问题分解成一系列相同的小问题，然后逐一解决
 *
 * 分治算法基本上可以归纳为三个步骤
 *
 * 1. 分解：将问题分解为若干规模小的相同结构问题
 *
 * 2. 解决：如果上一步问题可以解决就解决，否则，对每个子问题使用和上一步相同的方法再次分解，然后求解分解后的子问题，这个过程可能是一个递归的过程
 *
 * 3. 合并：将上一步解决的各个子问题的解通过某种规则合并起来，得到原问题的解
 *
 * 分治法难点，如果将子问题分解，并将子问题的解合并，针对不同的问题，有不同的分解与合并的方式
 *
 * 递归作为一种算法的实现方式，与分治是一✅天然的好朋友。当然分治也可以用非递归方式实现出来，就是比较难
 *
 *
 */
fun main(args: Array<String>) {


    var target = "abc"
    var targetArray = target.toCharArray()

    var resultList = arrayListOf<String>()

    stringFullArrangement(resultList, targetArray, 0, target.length)
    println("输出字符串：$target 的所有排列")
    println("全部排列方式：$resultList")

}

/**
 *  以 a b 为例 ，第一次固定 a 不用交换（集合中还是 a，b）子集合还剩b, b start等于end 添加 ab,
 *
 * 第二次循环 ab 交换 （ba） 子集合还剩 a  start等于end 添加 ba
 *
 * ba 交换回来 ab
 */
fun stringFullArrangement(resultList: ArrayList<String>, target: CharArray, start: Int, end: Int) {

    if (start == end) {//相等表示就一个字母不需要全排列了
        println("add"+target.toList().toString()+"start$start")
        resultList.add(target.toList().toString())
    }
    for (i in start until end) {
        swap(target, i, start)//start表示固定的位置,每个元素都换到start位置，其他在去全排列，
        stringFullArrangement(resultList,target,start+1,end)
        swap(target, i, start)//进行下一个字符固定时要将上一个固定的位置还原到原来位置
    }

}

fun swap(target: CharArray, first: Int, start: Int) {
    if (target[first] != target[start]) {
        var temp = target[first]
        target[first] = target[start]
        target[start] = temp
    }
}

