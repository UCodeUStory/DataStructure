package com.wangpos.datastructure.leetcode2;

import com.wangpos.datastructure.leetcode.LeetCode464;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

public class LeetCode220 {
    public static void main(String args[]) {
        Solution solution = new Solution();

        long nums[] = new long[]{1, 2, 3, 1};
        long nums1[] = new long[]{1, 0, 1, 1};
        long nums2[] = new long[]{1, 5, 9, 1, 5, 9};
        long nums3[] = new long[]{-1, 2147483647};
        int nums4[] = new int[]{2147483647, -2147483647};
//        System.out.println(solution.containsNearbyAlmostDuplicate(nums,3,0));
//
//        System.out.println(solution.containsNearbyAlmostDuplicate(nums1,1,2));
//        System.out.println(solution.containsNearbyAlmostDuplicate(nums2,2,3));
//        System.out.println(solution.containsNearbyAlmostDuplicate(nums3,1,2147483647));
        System.out.println(solution.containsNearbyAlmostDuplicate(nums4, 1, 2147483647));

//        报错的两种情况，因为虽然Number 是Float 父类，但是也推断不出来 List<Number> list44 是List<Integer>父类
//        List<Float> list4 = new ArrayList<Number>();
//       List<Number> list44 = new ArrayList<Float>();

        //所以，就算容器里装的东西之间有继承关系，但容器之间是没有继承关系的
        //为了让泛型用起来更舒服，Sun的大脑袋们就想出了<? extends T>和<? super T>的办法，来让”水果盘子“和”苹果盘子“之间发生关系。
//
//        List<? extends Number> list11 = new ArrayList<Number>();
//        //Integer是Number的子类
//        List<? extends Number> list22 = new ArrayList<Integer>();
//        //Float也是Number的子类
//        List<? extends Number> list33 = new ArrayList<Float>();
//
//        //上面只能遍历不能添加 获取Number 类型
//        Number a = list11.get(0);  // right
//      //  Integer b = list22.get(0);// Error 只能取出Number 类型
//        // 适合用场景，限制集合的修改操作， 只能获取Number 类型的元素
//
//
//
//        List<? super Float> list = new ArrayList<Float>();
//        //Number是Float的父类
//        List<? super Float> list2 = new ArrayList<Number>();
//        list2.add(0.1f);//可以调价Float类型，相当于Float 可以复制给Number；子类可以复制给父类
//        Object obj =  list2.get(0);
//        //Object是Number的父类
//        List<? super Float> list3 = new ArrayList<Object>();
//
//        //上面只能添加Float类型，但读出不出来具体类型，只能是Object，限制了集合的使用
//
////        PECS（Producer Extends Consumer Super）原则，已经很好理解了：
////
////        频繁往外读取内容的，适合用上界Extends。
////        经常往里插入的，适合用下界Super。
//        Collections.copy(new ArrayList<Float>(),new ArrayList<Float>());
//        Collections.copy(new ArrayList<Number>(),new ArrayList<Float>());
//       // Collections.copy(new ArrayList<Float>(),new ArrayList<Number>());//Error
////        Java中所有类的顶级父类是Object，可以认为Null是所有类的子类。

    }

    static class Solution {
//        public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
//            Set<String> records = new HashSet<>();
//            for (int i = 0; i < nums.length; i++) {
//                int maxLength = i + k;
//                if (maxLength >= nums.length) maxLength = nums.length-1;
//
//
//                for (int j = i+1; j <= maxLength; j++) {
//                    //去除重复计算
//                    if(records.contains(i+"_"+j)) continue;
//                    long a = nums[i];
//                    long b = nums[j];
//                    long result = a - b;
//                    if (Math.abs(result) <= t) {
//                        return true;
//                    }
//                    records.add(i+"_"+j);
//                }
//            }
//
//            return false;
//        }

        /**
         * TreeSet 实现使用TreeMap  ,TreeMap 实现是红黑树，默认自然顺序
         * TreeSet为基本操作（add、remove 和 contains）提供受保证的 log(n) 时间开销。
         * 通过ceiling 和Floor 可以找到最贴近的元素
         *
         * @param nums
         * @param k
         * @param t
         * @return
         */
        public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
            //自然平衡二叉树，来表示滑动窗口，这个窗口最大size 不能大于k
            TreeSet<Integer> set = new TreeSet<>();
            for (int i = 0; i < nums.length; ++i) {
                // Find the successor of current element

                // ceiling(E e) 方法返回在这个集合中大于或者等于给定元素的最小元素，如果不存在这样的元素,返回null.
                // 集合中大于此元素的最小值，最小值
                Integer s = set.ceiling(nums[i]);
                //s - nums[i]<=t 等式变换得到，集合中满足条件的最小值都不可以，那就其余都不行
                if (s != null && s <= nums[i] + t) return true;

                // 集合中小于此元素的最大值
                // Find the predecessor of current element
                Integer g = set.floor(nums[i]);
                if (g != null && nums[i] <= g + t) return true;

                //没找到添加到树中，并且会自然平衡
                set.add(nums[i]);
                if (set.size() > k) {
                    //向前数第k个移除
                    set.remove(nums[i - k]);
                }
            }
            return false;
        }

    }
}
