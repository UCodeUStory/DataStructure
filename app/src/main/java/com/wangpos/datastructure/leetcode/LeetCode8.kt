package com.wangpos.datastructure.leetcode

fun main() {

    val data = "9223372036854775808"

    val intNum = stringConvertInt(data)

    println("结果；${intNum}")
}

fun stringConvertInt(str: String): Int {
    val str = str.trim()
    if (str.length == 0) {
        return 0
    }
    val isSmallZero = str[0] == '-'
    var num: Long = 0
    for (k in 0 until str.length) {
        val it = str[k]

        if (k == 0 && it !in '0'..'9') {
            if(it !='-' &&it !='+') {
                break
            }
        }
        if(str.length>1) {
            if ((str[0] == '-'||str[0] == '+') && str[1] !in '0'..'9') {
                break
            }

            if (it == '0' && str[1] !in '0'..'9') {
                break
            }

        }
        if (it in '0'..'9') {
            if(num>Integer.MAX_VALUE){
                break
            }
            if (num == 0L) {
                num = charToInt(it).toLong()
            } else {
                num = num * 10 + charToInt(it)
            }
        }else{
            if(k==0 && it!='-'&&it!='+'){
                break
            }
            if(k!=0){
                break
            }
        }
    }
    if (num > Integer.MAX_VALUE) {
        if(isSmallZero){
            num = Integer.MAX_VALUE.toLong()+1L
        }else{
            num = Integer.MAX_VALUE.toLong()
        }

    }
    var result = 0
    if (isSmallZero) {
        result = -num.toInt()
    }else{
        result = num.toInt()
    }
    return result
}

private fun charToInt(it: Char): Int {
    val result = it.toString().toInt()
    println(result)
    return result
}