package com.wangpos.datastructure.leetcode

import java.util.*
import kotlin.collections.ArrayList

/**
 * LeetCode 第 739 题：根据每日气温列表，请重新生成一个列表，
 * 对应位置的输入是你需要再等待多久温度才会升高超过该日的天数。如果之后都不会升高，请在该位置用 0 来代替。
 *
 * 示例：给定一个数组 T 代表了未来几天里每天的温度值，要求返回一个新的数组 D，D 中的每个元素表示需要经过多少天才能等来温度的升高。
给定 T：[23, 25, 21, 19, 22, 26, 23]
返回 D:  [  1,   4,   2,   1,   1,   0,   0]

第一个温度值是 23 摄氏度，它要经过 1 天才能等到温度的升高，也就是在第二天的时候，温度升高到 25 摄氏度，所以对应的结果是 1
。接下来，从 25 度到下一次温度的升高需要等待 4 天的时间，那时温度会变为 26 度。

 *
 *
 * 利用堆栈，还可以解决如下常见问题：
求解算术表达式的结果（LeetCode 224、227、772、770)
求解直方图里最大的矩形区域（LeetCode 84）

 */
fun main() {

    //最直观的做法就是针对每个温度值向后进行依次搜索，找到比当前温度更高的值，这样的计算复杂度就是 O(n2)。

    //但是中间有很多重复计算，所以可以使用栈来实现，该方法只需要对数组进行一次遍历，每个元素最多被压入和弹出堆栈一次，算法复杂度是 O(n)


    val temperature = intArrayOf(23, 25, 21, 19, 22, 26, 23)

    val D = getUpList(temperature)

    println(Arrays.toString(D))

}

fun getUpList(temperature: IntArray): IntArray {

    //存储温度
    val stack = Stack<Int>()

    val D = IntArray(temperature.size)

    for (i in temperature.indices) {
        val it = temperature[i]
        while (!stack.isEmpty()) {
            if (temperature[stack.peek()] < it) {
                D[stack.peek()] =  i - stack.peek()
                stack.pop()
            }else{
                break
            }
        }
//        stack.push(it)
        //栈里面存的是数组下标，如果存值，再计算的时候就不知道是第几个了
        stack.push(i)

    }

    return D
}
