package com.wangpos.datastructure.algorithm


/**
 *穷举法又称穷举搜索法，是一种在问题域的解空间中对所有可能的解穷举搜索，并根据条件选择最优解的方法的总称。
 * 数学上也把穷举法称为枚举法，就是在一个由有限个元素构成的集合中，把所有元素一一枚举研究的方法。
 *
 * 穷举法的敌人就是规模大，解空间大
 *
 *一般来说，只要一个问题有其他更好的方法解决，通常不会选择穷举法，穷举法也常被作为“不是办法的办法”或“最后的办法”来使用，但是绝对不能因为这样而轻视穷举法，穷举法在算法设计模式中占有非常重要的地位，它还是很多问题的唯一解决方法。
 *
 *
 *
 */

fun main(args: Array<String>) {

    var count = buychicken(100)

    println("百钱买鸡共有 $count 种方法")

}


/**
 *
 *  *
 *一百个钱买一百只鸡，是个典型的穷举法应用。问题描述：每只大公鸡值 5 个钱，
 * 每只母鸡值 3 个钱，
 * 每 3 只小鸡值 1 个钱，
 * 现在有 100 个钱，想买 100 只鸡，问如何买？有多少种方法？
 *
 * 公鸡枚举的空间 0 20
 *
 * 母鸡枚举的空间 0 33
 */

fun buychicken(money: Int): Int {

    var maxGJ = 20

    var maxMJ = 33

    var count = 0

    var startTime = System.currentTimeMillis()
    for (i in 0 until 21) {
        // 剪枝操作，提高效率
        var maxCount = (100 - i*5)/3
        for (j in 0 until maxCount) {
            var smallChicken = 100 - i - j
            if (smallChicken % 3 == 0 && (smallChicken / 3 + j * 3 + i * 5) == 100){
                println("公鸡=$i ,母鸡=$j ,小鸡=$smallChicken")
                count++
            }
        }
    }
    println("算法花费时间"+(System.currentTimeMillis()-startTime))


    return count
}