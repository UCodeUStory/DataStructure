package com.wangpos.datastructure.leetcode;

import java.util.ArrayList;
import java.util.List;

class NumArray4 {

    public static void main(String[] args) {
        int value[] = new int[]{1, 2, 3, 4, 5};
        NumArray4 numArray = new NumArray4(value);
//        System.out.println("sumRange结果=" + numArray.sumRange(0, 1));

        System.out.println("区间数量=" + numArray.countRange(0, 1));

    }

    private int[] b;
    private int len;
    private int[] nums;
    SegementTree<Integer> tree;

    public NumArray4(int[] nums) {

        Merger<Integer> merger = new Merger<Integer>() {
            @Override
            public Integer merge(Integer a, Integer b) {
                return a + b;
            }
        };

        Integer data[] = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            data[i] = nums[i];
        }
        tree = new SegementTree(data, merger);
        tree.printSegementTree();
//        System.out.println("区间数量：" + tree.countRangeSum(1, 3));

    }

    public int sumRange(int i, int j) {
        return tree.query(i, j);
    }

    public void update(int i, int val) {
        tree.set(i, val);
    }

    public int countRange(int i, int j) {
        return tree.countRangeSum(i, j);
    }


    public interface Merger<E> {

        /**
         * 合成方法，a和b代表一个父节点下的两个子节点的值
         *
         * @param a
         * @param b
         * @return 根据a和b，计算出的父节点对应的值
         */
        public E merge(E a, E b);

    }

    public static class SegementTree<E> {

        /**
         * 线段树中传入的值，存储的副本
         */
        public E[] data;

        /**
         * 线段树中的节点，其中父节点的值为它的两个子节点merge后的值
         */
        public E[] tree;

        /**
         * 合成器，构造线段树时候同时传入合成器
         */
        public Merger<E> merger;

        /**
         * 构造线段树
         *
         * @param data   传入的数据
         * @param merger 传入的合成器
         */
        public SegementTree(E[] data, Merger<E> merger) {
            if (data.length == 0) {
                return;
            }
            this.merger = merger;
            int length = data.length;
            this.data = (E[]) new Object[length];
            //复制数据到data中
            for (int i = 0; i < length; i++) {
                this.data[i] = data[i];
            }
            //总共n个叶子节点，n-1个非叶子节点
            tree = (E[]) new Object[length * 4];
            //构造线段树
            buildSegementTree(0, 0, length - 1);
        }

        /**
         * 构造线段树中的tree中的节点
         *
         * @param treeIndex tree中对应节点的index
         * @param left      这个节点对应data中的范围的左边界,root对应0
         * @param right     这个节点对应data中的范围的右边界,root对应length-1
         *                  <p>
         *                  <p>
         *                  <p>
         *                  由根节点出发构建子树，最终通过回溯，构建父节点
         *                  通常比较难理解的就是数组中元素位置和树节点对应关系，
         *                  <p>
         *                  首先从根节点出发(也就是tree 0元素)递归去为每个节点赋值
         *                  递归一次计算左右孩子节点的index,递归一次相当于数组被分成了两份，所以除以二，其中奇数时算在左区间中
         *                  递归终止条件就是被拆分的数组 left == right，说明不可拆分，此时将tree对应index的数组修改成这个不可分的数组值,
         *                  其他节点也是这样不相等就继续分，因为叶子节点只能保存一个值，最终全部分配到叶子节点后向上回溯父节点的值
         *                  <p>
         *                  这里通过一个合成器，来动态定义父节点的赋值策略，可以使两个节点的和，也可以是两个节点中最大值
         *                  <p>
         *                  比如 一个根节点  放3个数组，放不下，继续拆分， 其中有节点 放[2,2]这只有一个元素，直接存储
         *                  左边[0,1]放不下，继续才分，[0,0][1,1] 可以放下了，结束
         */
        public void buildSegementTree(int treeIndex, int left, int right) {
            if (left == right) {
                //如果left==right，证明递归结束，在对应的index设置data里left的值
                System.out.println("size" + tree.length + "treeIndex=" + treeIndex + "left=" + left);
                tree[treeIndex] = data[left];
                return;
            }
            //tree中父节点为treeIndex,的左右孩子的index
            int leftChildIndex = getLeftChild(treeIndex);
            int rightChildIndex = getRightChild(treeIndex);
            int mid = left + (right - left) / 2;//如果偶数左边右边一样多，如果是奇数，算到左边中
            //构造左右孩子节点
            buildSegementTree(leftChildIndex, left, mid);
            buildSegementTree(rightChildIndex, mid + 1, right);
            //根据左右孩子的值，通过合成器，决定父节点的值
            tree[treeIndex] = merger.merge(tree[leftChildIndex], tree[rightChildIndex]);
        }

        /**
         * 返回左孩子在数组中的位置
         *
         * @param index 父节点的index
         * @return 左孩子节点的index
         */
        public int getLeftChild(int index) {
            //可以这样看，root节点，index：0
            //root的左孩子，index：1
            //root的右孩子，index：2
            //root的左孩子的左孩子，index：3
            //root的左孩子的有孩子，index：4
            return 2 * index + 1;
        }

        /**
         * 返回右孩子在数组中的位置
         *
         * @param index 父节点的index
         * @return 右孩子节点的index
         */
        public int getRightChild(int index) {
            return 2 * index + 2;
        }

        /**
         * 打印线段树
         */
        public void printSegementTree() {
            System.out.println("开始打印线段树----------");
            System.out.println("线段树数据的长度为" + data.length);
            for (int i = 0; i < tree.length; i++) {
                System.out.println("位置" + i + ": " + tree[i]);
            }

            System.out.println("打印线段树结束----------");
        }

        /**
         * 返回data中区间left和right间，对应的值
         *
         * @param left
         * @param right
         * @return
         */
        public E query(int left, int right) {
            if (left < 0 || right < 0 || left >= data.length || right >= data.length || left > right) {
                return null;
            }
            return queryRange(0, 0, data.length - 1, left, right);

        }

        /**
         * 统计区间数量，也就是节点数量
         *
         * @param left
         * @param right
         * @return
         */
        public int countRangeSum(int left, int right) {
            int l = left;
            int r = right;
            if (left > right) {
                return 0;
            }
            if (right < 0) {
                return 0;
            }
            if (r >= data.length) {
                r = data.length - 1;
            }
            if (l < 0) {
                l = 0;
            }

            List<Integer> posList = new ArrayList<Integer>();
            queryCount(0, 0, data.length - 1, l, r, posList);
            return posList.size();
        }


        public E queryCount(int treeIndex, int treeLeft, int treeRight, int queryLeft, int queryRight, List<Integer> posList) {


            if (treeLeft == queryLeft && treeRight == queryRight) {
                //如果该节点的范围正好对应查询范围，直接返回
                System.out.println("********1");
                posList.add(treeIndex);
                return tree[treeIndex];
            }
            int leftChildIndex = getLeftChild(treeIndex);
            int rightChildIndex = getRightChild(treeIndex);
            int mid = treeLeft + (treeRight - treeLeft) / 2;
            if (queryLeft >= mid + 1) {
                //如果查询范围仅仅对应左孩子或者右孩子
                return queryCount(rightChildIndex, mid + 1, treeRight, queryLeft, queryRight,posList);
            } else {
                if (queryRight <= mid) {
                    return queryCount(leftChildIndex, treeLeft, mid, queryLeft, queryRight,posList);
                }
            }
            //查询范围，左右孩子都有
            E resultLeft = queryCount(leftChildIndex, treeLeft, mid, queryLeft, mid,posList);
            E resultRight = queryCount(rightChildIndex, mid + 1, treeRight, mid + 1, queryRight,posList);
            //最终结果是左右孩子的合并
            E result = merger.merge(resultLeft, resultRight);
            System.out.println("********2");
            posList.add(treeIndex);
            return result;
        }

        /**
         * 在以tree中位置为treeIndex为根节点，而且该节点对应的data中的范围为[treeLeft,treeRight] <br>
         * 查询范围为[queryLeft,queryRight]对应的值
         *
         * @param treeIndex
         * @param treeLeft
         * @param treeRight
         * @param queryLeft
         * @param queryRight
         * @return
         */
        public E queryRange(int treeIndex, int treeLeft, int treeRight, int queryLeft, int queryRight) {
            if (treeLeft == queryLeft && treeRight == queryRight) {
                //如果该节点的范围正好对应查询范围，直接返回
                System.out.println("********1");
                return tree[treeIndex];
            }
            int leftChildIndex = getLeftChild(treeIndex);
            int rightChildIndex = getRightChild(treeIndex);
            int mid = treeLeft + (treeRight - treeLeft) / 2;
            if (queryLeft >= mid + 1) {
                //如果查询范围仅仅对应左孩子或者右孩子
                return queryRange(rightChildIndex, mid + 1, treeRight, queryLeft, queryRight);
            } else {
                if (queryRight <= mid) {
                    return queryRange(leftChildIndex, treeLeft, mid, queryLeft, queryRight);
                }
            }
            //查询范围，左右孩子都有
            E resultLeft = queryRange(leftChildIndex, treeLeft, mid, queryLeft, mid);
            E resultRight = queryRange(rightChildIndex, mid + 1, treeRight, mid + 1, queryRight);
            //最终结果是左右孩子的合并
            E result = merger.merge(resultLeft, resultRight);
            System.out.println("********2");
            return result;
        }


        /**
         * 在线段树中修改data中index的元素，设置新的值为value
         *
         * @param index
         * @param value
         */
        public void set(int index, E value) {
            if (index < 0 || index >= data.length) {
                return;
            }
            setValue(0, 0, data.length - 1, index, value);
        }

        /**
         * 在以tree中位置为treeIndex为根节点，而且该节点对应的data中的范围为[treeLeft,treeRight] 下，<br>
         * 修改data中index的元素，设置新的值为value
         *
         * @param treeIndex
         * @param treeLeft
         * @param treeRight
         * @param index
         * @param value
         */
        public void setValue(int treeIndex, int treeLeft, int treeRight, int index, E value) {
            if (treeLeft == treeRight) {
                tree[treeIndex] = value;
                return;
            }
            int leftChildIndex = getLeftChild(treeIndex);
            int rightChildIndex = getRightChild(treeIndex);
            int mid = treeLeft + (treeRight - treeLeft) / 2;
            if (index <= mid) {
                setValue(leftChildIndex, treeLeft, mid, index, value);
            } else {
                setValue(rightChildIndex, mid + 1, treeRight, index, value);
            }
            tree[treeIndex] = merger.merge(tree[leftChildIndex], tree[rightChildIndex]);
        }

    }

}
