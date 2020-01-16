package com.wangpos.datastructure.leetcode

import java.util.*

/**
 * 给定一个整数数组  nums，求出数组从索引 i 到 j  (i ≤ j) 范围内元素的总和，包含 i,  j 两点。

update(i, val) 函数可以通过将下标为 i 的数值更新为 val，从而对数列进行修改。


频繁计算总和可以用这总结构，同时这种结构更新效率不再是O(1)
 */
fun main() {

    val result = NumArray(arrayOf(1, 3, 5).toIntArray()).sumRange(0,2)

    println("结果：${result}")
}

class NumArray(nums: IntArray) {

    lateinit var tree: IntArray


    init {
        if (nums.size > 0) {
            val n = nums.size
            //数组元素不一定全部用满
            tree = IntArray(n * 2)
            buildTree(nums)
        }
    }

    /**
     * 先将叶子节点添加到tree 数组中
     * 从2n-1 到 n,因为顺序，所以从n 到2n-1
     *
     * 求所有符节点 = 2*i +2*i+1  i从1开始
     *
     * 最终数组中0号元素没用，1号元素才是树的根
     *
     * 我们是从底部向上逆序，导致多出来一个0号元素，如果是从上到下，就会多出一个2n-1号元素
     *
     * 这个二叉树克制，从底向上，从右向左
     *
     * 注意：：：最终得到的也不是完全二叉树
     *
     * 因为完全二叉树定义：若设二叉树的深度为h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，第 h 层所有的结点都连续集中在最左边，这就是完全二叉树
     *
     *
     *
     *               9   8
     *
     *         1（3）  3（4） 5（5）
     *
     * 从右向左底部线排列好叶子节点，然后计算父节点 2*i +2*i+1，8 = 3 + 5，当叶子节点不够，父节点也会充当叶子节点去，比如9 = 8+1
     *
     * 线段树
     */
    private fun buildTree(nums: IntArray) {
        var i = n //恰好第n个位置为叶子节点，不如3个数据，n=3 表示我们需要3个叶子节点，至少需要2*n的数组来存储，构建一个6个节点的树
        var j = 0
        /**
         * 相当于数组n 到 2n-1,一共n个数据
         */
        while (i < 2 * n) {
            //初始化叶子节点
            println(""+i+"  "+j)

            tree[i] = nums[j]
//            println(nums[j])
            i++
            j++
        }

        println(Arrays.toString(tree))

        i = n-1
        //初始化和
        while (i>0){
            println(i)
            tree[i] = tree[i * 2] + tree[i * 2 + 1]
            i--
        }

        println(Arrays.toString(tree))
    }

    fun update(i: Int, `val`: Int) {
        var pos = i
        pos += n //这个加n才是tree 数组中位置
        //直接更新
        tree[pos] = `val`
        //调整

        /**
         * 如果 pos是偶数 例如修改3  left 是自己，right 是自己+1@author
         *
         * 如果pos是奇数 例如修改1 left 是自己-1，right 是自己
         *
         *            9   8
         *
         *         1    3    5
         */
        while (pos > 0) {
            var left = pos
            var right = pos
            if (pos % 2 == 0) {
                right = pos + 1
            } else {
                left = pos - 1
            }
            // parent is updated after child is updated
            tree[pos / 2] = tree[left] + tree[right]
            pos /= 2
        }

    }

    fun sumRange(i: Int, j: Int): Int {
// get leaf with value 'l'
        var l = i
        var r = j
        l += n//获取tree中坐标
        // get leaf with value 'r'
        r += n
        var sum = 0
        while (l <= r) {


            if ((l % 2) == 1) {
                println(">>>"+tree[l])
                sum += tree[l]
                l++
            }
            if ((r % 2) == 0) {
                println(">>>>>>"+ tree[r])
                sum += tree[r]
                r--
            }
            l /= 2
            r /= 2
        }
        return sum

    }

}


/**
 *      一般方式 sumRang 时间复杂度为O(n)
 *
private int[] nums;
public int sumRange(int i, int j) {
int sum = 0;
for (int l = i; l <= j; l++) {
sum += data[l];
}
return sum;
}

public int update(int i, int val) {
nums[i] = val;
}
 */

/**
sqrt 分解
其思想是将数组分割成块，块的长度为 sqrt n
​
。然后我们计算每个块的和，并将其存储在辅助存储器 b 中。要查询 RSQ(i, j)，我们将添加位于内部的所有块和部分在范围 [i\ldots j][i…j] 重叠的块的总和。


 */