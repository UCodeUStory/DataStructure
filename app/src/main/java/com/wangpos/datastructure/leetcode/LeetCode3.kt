package com.wangpos.datastructure.leetcode

/**
 * 无重复字符的最长子串
 */
fun main() {

    println(lengthOfLongestSubstring("abcabcdbb"))
}

fun lengthOfLongestSubstring(s: String): Int {
    //滑块方式 可以使用hashMap
    val dQueue = mutableMapOf<Char, Int>()
    //初始化，添加到滑块当中，直到有重复数据

    //移动滑块，也就是将其他数据分别和滑块中数据比较，如果不包含就加入

    var resultSize = 0

    val charArray = s.toCharArray()
    var startIndex =0
    for (i in 0 until charArray.size) {
        if (dQueue.contains(charArray[i])) {
            //移除掉自己和之前的元素
            println("remove ${charArray[i]}")
            val position = dQueue[charArray[i]]!!
            var index = position
            while (index >= startIndex) {
                val data = charArray[index]
                //判断此元素是之前添加的元素才移除
                if (dQueue.containsKey(data) && dQueue[data] == index) {
                    println("移除：${data}")
                    dQueue.remove(data)
                }
                index--
            }
            startIndex = position
        }
        println(">>>>>>>>>>>${dQueue.size}")
        dQueue[charArray[i]] = i
        if (dQueue.size > resultSize) {
            resultSize = dQueue.size
        }
        println("add ${resultSize} ${dQueue.size}  ${charArray[i]}")
    }

    return resultSize
    //最后返回size
}
