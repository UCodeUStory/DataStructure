package com.wangpos.datastructure.sort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wangpos.datastructure.R;

public class BinaryTreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_tree);


        Node root = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        Node d = new Node(4);
        Node e = new Node(5);
        Node f = new Node(6);
        Node g = new Node(7);


        root.setLeftChild(b);
        root.setRightChild(c);

        b.setLeftChild(d);
        b.setRightChild(e);

        c.setLeftChild(f);
        c.setRightChild(g);
    }


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

    /** 递归实现前序遍历
     *
     * 跟左右
     * */
    protected static void preorder(Node p) {
        if (p != null) {
            Log.i("qiyue","data="+p.getData());
            preorder(p.getLeftChild());
            preorder(p.getRightChild());
        }
    }

    /**
     *  递归实现中序遍历
     *  左右跟
     * @param p
     */
    protected static void inorder(Node p){
        if (p != null) {
            inorder(p.getLeftChild());
            Log.i("qiyue","data="+p.getData());
            inorder(p.getRightChild());
        }
    }

    /**
     * 递归实现后续遍历
     * @param p
     */
    protected static void postorder(Node p){
        if (p !=null){
            postorder(p.getLeftChild());
            postorder(p.getRightChild());
            Log.i("qiyue","data="+p.getData());
        }
    }


}
