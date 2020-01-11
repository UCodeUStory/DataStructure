package com.wangpos.datastructure.leetcode

/**
 * 292. Nim 游戏
 */
fun main() {

}

fun canWinNim(n: Int): Boolean {
    //取1
    if(n<=3) return true

    if(n%4==0)return false

    return true

}