package com.wangpos.datastructure.algorithm


/**
 *  8 5 3 分 4升水，一共有多少种分发
 *
 *  首先找到分析倒水有个动作，是谁到给了谁
 *
 *  然后我们穷举所有倒水可能，放到一个列表中
 *
 *
 *  初始状态 8 0 0
 *  当最终水的状态为 4 4 0 为止，
 *
 *  数据模型为了方便最查找，每个状态包含一个parent
 *
 *  我们针对已经遍历过的状态进行剪枝操作，否者会死循环
 *
 */
fun main() {

    var actions = arrayOf(
        arrayOf(0, 1), arrayOf(0, 2), arrayOf(1, 0), arrayOf(1, 2), arrayOf(2, 0), arrayOf(2, 1)
    )

    var b8 = Bucket(8, 8)
    var b5 = Bucket(0, 5)
    var b3 = Bucket(0, 3)

    var bucketState = BucketState(listOf(b8, b5, b3), 0, 0, null)

    var stateInfos = mutableListOf<BucketState>()
    stateInfos.add(bucketState)
    search(stateInfos, bucketState, actions)

    PrintResult(stateInfos)
}

/**
 * 桶信息
 */
class Bucket(var water: Int, var capcity: Int)

/**
 * 求解一系列桶状态 序列
 */
class BucketState(
    var buckets: List<Bucket>, var from: Int, var to: Int,
    var parent: BucketState?
) {
    fun takeAction(i: Int, j: Int, next: (BucketState?) -> Unit) {

        //穿件一个改变的实体
        var newBucketState = copy()
        newBucketState.parent = this

        var fromBucket = newBucketState.buckets[i]
        var toBucket = newBucketState.buckets[j]

        if (fromBucket.water == 0) {
            next(null)
            return
        }

        var canAcceptWater = newBucketState.buckets[j].capcity - buckets[j].water

        if (canAcceptWater == 0) {
            next(null)
            return
        }

        if (canAcceptWater <= fromBucket.water) {
            toBucket.water = toBucket.capcity
            fromBucket.water = fromBucket.water - canAcceptWater
        } else {
            toBucket.water = toBucket.water + fromBucket.water
            fromBucket.water = 0
        }

        next(newBucketState)
    }
}

/**
 * 查找所有可能
 */
fun search(
    states: MutableList<BucketState>,
    bucketState: BucketState,
    actions: Array<Array<Int>>
) {
    if (IsFinalState(bucketState)) //判断是否到达[4,4,0]状态
    {
        println("找到了一种结果")
        return
    }
    actions.forEach {
        var next: BucketState? = null
        //每次使用新的实体去查找，然后存储
        bucketState.copy().takeAction(it[0], it[1]) {
            next = it
        }
        next?.let {
            if (!isDuplicate(states, it)) {
                search(states, it, actions)
            }
        }
    }
}

/**
 * 是否之前已经操作出现了的状态避免重复
 */
fun isDuplicate(states: MutableList<BucketState>, next: BucketState): Boolean {

    if (IsFinalState(next)) {
        states.add(next)
        return false
    }
    states.forEach {
        if (it.buckets[0].water == next.buckets[0].water
            && it.buckets[1].water == next.buckets[1].water
            && it.buckets[2].water == next.buckets[2].water
        ) {
            return true
        }
    }
    states.add(next)
    return false
}

/**
 * 打印结果
 */
fun PrintResult(states: List<BucketState>) {
    for (i in states) {
        if (IsFinalState(i)) {
            printThree(i)
            println("${i.from},${i.to} => ${printFormat(i)}")
            println()
        }
    }
}

private fun printFormat(bucketState:BucketState):String {
    return "" + bucketState.buckets[0].water + "-" + bucketState.buckets[1].water + "-" + bucketState.buckets[2].water
}

/**
 * 打印树
 */
fun printThree(i: BucketState) {
    i.parent?.let {
        var result =
            "" + it.buckets[0].water + "-" + it.buckets[1].water + "-" + it.buckets[2].water
        printThree(it)
        println("${it.from},${it.to} => " + result)
    }

}

/**
 * 满足 4 4 0
 */
fun IsFinalState(bucketState: BucketState): Boolean {
    if (bucketState.buckets[0].water == 4 && bucketState.buckets[1].water == 4 && bucketState.buckets[2].water == 0) {
        return true
    }
    return false
}

/**
 * 复制新的实体
 */
fun BucketState.copy(): BucketState {
    var b8 = Bucket(this.buckets[0].water, 8)
    var b5 = Bucket(this.buckets[1].water, 5)
    var b3 = Bucket(this.buckets[2].water, 3)
    return BucketState(listOf(b8, b5, b3), from, to, parent)
}