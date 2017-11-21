package com.wangpos.datastructure.core;

/**
 * Created by qiyue on 2017/11/21.
 */

public class Node{
    private Node leftChild;

    private Node rightChild;

    private int data;

    public Node(int data){
        this.data = data;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

}