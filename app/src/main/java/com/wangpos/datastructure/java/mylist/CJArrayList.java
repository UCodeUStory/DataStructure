package com.wangpos.datastructure.java.mylist;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by qiyue on 2018/6/19.
 * ArrayList 简易版 来讲述基本原理
 */

public class CJArrayList<E> {

    transient Object[] elementData; // non-private to simplify nested class access

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * 要分配的最大数组大小。
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 默认初始化容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    private int size;

    /**
     * 默认创建一个空的数组
     */
    public CJArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * 向集合添加数据
     */
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;//size++ 表示真实数据的size
        return true;
    }

    /**
     * 最少要达到的容量，否者溢出
     * @param minCapacity
     */
    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
    }

    private void ensureExplicitCapacity(int minCapacity) {
//        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)//minCapacity当前需要的容量是否超过数组最大值
            grow(minCapacity);
    }


    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);//增加1一半
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;//默认一般的扩容还是小于minCapacity 就用minCapacity
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);//超出MA_ARRAY_SIZE 重新计算
        elementData = Arrays.copyOf(elementData, newCapacity);//创建新的数组并将原数据拷贝
    }



    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    public int size() {
        return size;
    }

    public E get(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));

        return (E) elementData[index];
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }
}
