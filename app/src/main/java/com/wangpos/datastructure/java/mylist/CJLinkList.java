package com.wangpos.datastructure.java.mylist;

import java.util.LinkedList;

/**
 * Created by qiyue on 2018/6/20.
 */

public class CJLinkList<E> {


    transient int size = 0;
    //头指针
    transient Node<E> first;

    //尾指针
    transient Node<E> last;


    public CJLinkList() {
    }

    /**
     * 双链表每个节点包括 前驱、后继、数据
     * 构造函数也直接对3个数据进行赋值
     * @param <E>
     */
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }


    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    /**
     * 链接到尾部
     * @param e
     */
    void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);//封装新的Node
        last = newNode;//改变最后元素指针
        if (l == null)
            first = newNode;
        else
            l.next = newNode;//与前面链子链接
        size++;
    }


    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    Node<E> node(int index) {
        // assert isElementIndex(index);

        /**
         * 判断数据的是在前半部分，还是后半部分,决定查找方法
         */
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    /**
     * 检查index 是否在size范围
     * @param index
     */
    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    /**
     * 判断集合是否包含元素
     * @param o
     * @return
     */
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    /**
     * 查找数据的位置第一次出现的位置，找不到返回-1
     * @param o
     * @return
     */
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {//当数据是null的时候,下面方式查找
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null)
                    return index;
                index++;
            }
        } else {// 当数据不为null时，调用对象本身equals进行比较
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item))
                    return index;
                index++;
            }
        }
        return -1;
    }

    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 断开非空元素
     *
     * |prev|dataX|next|  |prev|dataY|next|  |prev|dataZ|next|
     *
     * 断开 dataY 数据，需要改变 dataX 的后继指向，和dataZ的前驱指向，同时自身的前驱后继要指向null 这才算完全断开
     */
    E unlink(Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        /**
         * 将前驱断开，并连接
         */
        if (prev == null) {//前驱为null，证明当前是first，所以，first指向next即可
            first = next;
        } else {
            prev.next = next;//前驱不为null，前驱的next指向next，此时自己的前驱指向为null
            x.prev = null;
        }
        /**
         * 将后继断开并，配置后继的前驱指向
         */
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        return element;
    }
}
