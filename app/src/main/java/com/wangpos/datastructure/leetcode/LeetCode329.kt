package com.wangpos.datastructure.leetcode

/**
 * 矩阵中最长增长路径
 *
 * 记忆化: 对于大量重复调用的问题，缓存其结果。
   动态规划要求按照拓扑顺序解决子问题。对于很多问题，拓扑顺序与自然秩序一致。而对于那些并非如此的问题，需要首先执行拓扑排序。因此,对于复杂拓扑问题（如本题），使用记忆化搜索通常是更容易更好的选择。

   想要让动态规划有效，如果问题 B 依赖于问题 A 的结果，就必须确保问题 A 比问题 B先计算。这样的依赖顺序对许多问题十分简单自然。如著名的斐波那契数列
 */
fun main() {

    /**
     * 通过二维数组定义方向
     *
     * 比如当前的二维数组中元素为0,0；垂直整下的元素就需要第二位加一，也就是列加一，所以是0，1
     * 同理向上0，-1, 向左 -1，0,向右 1.0
     *
     *
     */

    val matrix = arrayOf<Array<Int>>(arrayOf(9, 9, 4), arrayOf(6, 6, 8), arrayOf(2, 2, 1))
    val result = longestIncreasingPath(matrix)

    println("结果：${result}")
}

/**
 * 采用深度优先搜索解决此题，
 *
 * 因为最坏时间是从第一列第一个个元素到第一列最后元素再到最后一行最后一个元素
 *
 * 所以相当于第一次搜索 1分为二，第二次二分为4 所以时间复杂度2^n,注意这只是一个点的深度优先搜索，mn 个所以复杂度很高，具体为什么是2^(m+n)本人也没太仔细算
 *
 * 时间复杂度是 O(2^(m+n))
 *
 * 空间复杂度O(mn)
 */
fun longestIncreasingPath(matrix: Array<Array<Int>>): Int {

    if (matrix.size == 0) return 0
    //几列
    m = matrix.size
    //几行
    n = matrix[0].size
    //最长路径
    var ans = 0
    for (i in 0 until m) {
        for (j in 0 until n) {
            //每个顶点做四个方向的深度优先搜索
            ans = Math.max(ans, dfs(matrix, i, j));
        }
    }

    return ans
}

/**
 * 深度优先搜索
 */
fun dfs(matrix: Array<Array<Int>>, i: Int, j: Int): Int {
    //定义本次记录的路径长度
    var ans = 0
    //四个方向相当于图的四个分支，每个方向做深度优先搜索，
    for (d in dirs) {
        val x = i + d[0]//下一个元素行坐标
        val y = j + d[1]//下一个元素列坐标
        /**
         * 校验下标合法范围
         */
        if (0 <= x && x < m && 0 <= y && y < n ){
            //表示递增的
            if( matrix[x][y] > matrix[i][j]){
                //每次查找如果比当前的大就替换
                ans = Math.max(ans, dfs(matrix, x, y))
            }
        }
    }
    //这里+1表示到达当前一步，如果四个方向都没有找到递增的就返回1
    // ，回溯一次多了1，回溯一次多了1，所以每次判断哪个路径回溯的最多，
    //最终回溯到终点就是最长路径 ans
    return ++ans
}


var m = 0
var n = 0
val dirs = arrayOf<Array<Int>>(arrayOf(0, 1), arrayOf(1, 0), arrayOf(0, -1), arrayOf(-1, 0))


/**
    记忆化深度优先搜索

    将递归的结果存储下来，这样每个子问题只需要计算一次。
    从上面的分析中，我们知道在淳朴的深度优先搜索方法中有许多重复的计算。
    一个优化途径是我们可以用一个集合来避免一次深度优先搜索中的重复访问。
    该优化可以将一次深度优先搜索的时间复杂度优化到 O(mn)O(mn)，总时间复杂度 O(m^2n^2)。

 **/

fun longestIncreasingPath2(matrix: Array<Array<Int>>): Int {

    if (matrix.size == 0) return 0
    //几列
    m = matrix.size
    //几行
    n = matrix[0].size
    //最长路径
    var ans = 0

    val cache = Array(m) { IntArray(n) }
    for (i in 0 until m) {
        for (j in 0 until n) {
            //每个顶点做四个方向的深度优先搜索
            ans = Math.max(ans, dfs2(matrix, i, j,cache));
        }
    }

    return ans
}

/**
 * 记忆 深度优先搜索
 */
fun dfs2(
    matrix: Array<Array<Int>>,
    i: Int,
    j: Int,
    cache: Array<IntArray>
): Int {
    //从缓存获取结果
    if (cache[i][j] != 0) return cache[i][j]

    //定义本次记录的路径长度
    var ans = 0
    //四个方向相当于图的四个分支，每个方向做深度优先搜索，
    for (d in dirs) {
        val x = i + d[0]//下一个元素行坐标
        val y = j + d[1]//下一个元素列坐标
        /**
         * 校验下标合法范围
         */
        if (0 <= x && x < m && 0 <= y && y < n ){
            //表示递增的
            if( matrix[x][y] > matrix[i][j]){
                //每次查找如果比当前的大就替换
                //缓存每个节点的最长路径
                cache[i][j]  = Math.max(ans, dfs2(matrix, x, y,cache))
            }
        }
    }
    //这里+1表示到达当前一步
    return ++cache[i][j]
}
