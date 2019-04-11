package com.wangpos.datastructure.algorithm


fun main(args: Array<String>) {

   var result =  twoSepearate(-0.8,8.0)

    println("方程的近似解=$result")
}

/**
 * 二分法的局限性就是不能计算复根和重根，需要借助其手段确定零点所在区间。设方程为 f(x) = 2x^{2} + 3.2x - 1.8f(x)=2x
2
+3.2x−1.8，求根精度是 PRECISION = 0.000000001，在 [-0.8,8.0] 区间上求解 xx = 0.440967364
 */
fun f(x: Double): Double {
    return 2.0 * x * x + 3.2 * x - 1.8
}


fun twoSepearate(aa: Double, bb: Double): Double {
    var PRECISION = 0.00000000001
    var a = aa
    var b = bb
    var mid = (a + b) / 2.0

    while ((b - a) > PRECISION) {
        if (f(a) * f(mid) < 0.0) {
            b = mid
        } else {
            a = mid
        }
        mid = (a + b) / 2.0
    }
    return mid
}