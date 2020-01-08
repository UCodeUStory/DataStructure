package com.wangpos.datastructure.leetcode

import java.util.*

/**
 * 4. 寻找两个有序数组的中位数
 *
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。

请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。

你可以假设 nums1 和 nums2 不会同时为空。

 */
fun main() {

    val num1 = arrayOf(1, 3, 5, 7, 9, 10).toIntArray()
    val num2 = arrayOf(2, 4, 6, 8).toIntArray()
//    val middleNumber = findMedianSortedArrays(num1, num2)
    val middleNumber2 =  findMedianSortedArrays2(num1,num2)
    println("中位数 ${middleNumber2}")
}

/**
 * 暴力方法 合并再求解 时间复杂度是 O(m+n)
 * 空间复杂度O(m+n)
 */
fun findMedianSortedArrays(nums1: Array<Int>, nums2: Array<Int>): Double {

    val m = nums1.size
    val n = nums2.size
    val nums = IntArray(m + n)

    //合并
    var count = 0
    var i = 0
    var j = 0
    while (count < (m + n)) {
        if (i == m) {
            while (j < n) {
                nums[count++] = nums2[j++]
            }
            break
        }

        if (j == n) {
            while (i < m) {
                nums[count++] = nums1[i++]
            }
            break
        }

        //这里会存在i 或j 有一个先放完的情况，所以前面要做判断
        if (nums1[i] < nums2[j]) {//比较小的放数组前面，放完移动标志位进行下一个
            nums[count++] = nums1[i++]
        } else {
            nums[count++] = nums2[j++]
        }
    }

    println("合并后的数组${Arrays.toString(nums)}")
    //获取中位数
    if (count % 2 == 0) {
        //偶数
        return (nums[count / 2 - 1] + nums[count / 2]) / 2.0
    } else {
        //奇数
        return nums[count / 2].toDouble()
    }
}


fun findMedianSortedArrays2(nums1: IntArray, nums2: IntArray): Double {
    val n = nums1.size
    val m = nums2.size
    val left = (n + m + 1) / 2  //如果是奇数，left位置 恰好是这个中位数
    val right = (n + m + 2) / 2  //偶数，需要获得(n+m)/2   和（n+m)/2两个数的平均值

    //将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k 。
//    return getKth(nums1, 0, n - 1, nums2, 0, m - 1, left).toDouble()
    return (getKth(nums1, 0, n - 1, nums2, 0, m - 1, left) + getKth(
        nums1,
        0,
        n - 1,
        nums2,
        0,
        m - 1,
        right
    )) * 0.5
}

/**
 * 寻找第k个小的
 */
fun getKth(
    nums1: IntArray,
    start1: Int,
    end1: Int,
    nums2: IntArray,
    start2: Int,
    end2: Int,
    k: Int
): Int {
    val len1 = end1 - start1 + 1
    val len2 = end2 - start2 + 1

    // 如果len1大于len2 就对调一下，保证len1小于len2
    if (len1 > len2) return getKth(nums2, start2, end2, nums1, start1, end1, k)

    //如果第一个数组被排除完，那么中位数肯定在未排除的数据中，因为都是有序的，所以直接可以推断出
    if (len1 == 0) return nums2[start2 + k - 1]

    if (k == 1) return Math.min(nums1[start1], nums2[start2])

    //如果k/2小于剩下的长度，就取最小值，否者下面会数组越界，这是一个新的start

    //这里注意为什么是k/2 ,而不是k, 如果是k 相当于我们再比较数组中最大值，然后排除掉，下一次还是比较数组最大值，这样效率很低
    //所以每次排除一半数量k,然后拿k/2和另两个数组长度比，找最小值作为比较的位置，然后舍去之前的位置
    val i = start1 + Math.min(len1, k / 2) - 1

    val j = start2 + Math.min(len2, k / 2) - 1

    if (nums1[i] > nums2[j]) {
        //排除前j个
        return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
    } else {
        //排除前i个
        return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));

    }

}
//
//private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
//    int len1 = end1 - start1 + 1;
//    int len2 = end2 - start2 + 1;
//    //让 len1 的长度小于 len2，这样就能保证如果有数组空了，一定是 len1
//    if (len1 > len2) return getKth(nums2, start2, end2, nums1, start1, end1, k);
//    if (len1 == 0) return nums2[start2 + k - 1];
//
//    if (k == 1) return Math.min(nums1[start1], nums2[start2]);
//
//    int i = start1 + Math.min(len1, k / 2) - 1;
//    int j = start2 + Math.min(len2, k / 2) - 1;
//
//    if (nums1[i] > nums2[j]) {
//        return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
//    }
//    else {
//        return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
//    }
//}
