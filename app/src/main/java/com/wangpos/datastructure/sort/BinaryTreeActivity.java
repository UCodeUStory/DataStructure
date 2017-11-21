package com.wangpos.datastructure.sort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wangpos.datastructure.R;
import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;
import com.wangpos.datastructure.core.Node;

public class BinaryTreeActivity extends BaseActivity{


    @Override
    protected void initData() {

        Node root = new Node(10);
        Node b = new Node(8);
        Node c = new Node(12);

        Node d = new Node(7);
        Node e = new Node(9);
        Node f = new Node(11);
        Node g = new Node(13);

        root.setLeftChild(b);
        root.setRightChild(c);

        b.setLeftChild(d);
        b.setRightChild(e);

        c.setLeftChild(f);
        c.setRightChild(g);

        addItem(new CodeBean("先序遍历",preorderstr));
        addItem(new CodeBean("中序遍历",inorderStr));
        addItem(new CodeBean("后序遍历",postOrderStr));

        // 10 8 7 9 12 11 13
//        preorder(root);
        // 7 8 9 10 11 12 13
//        inorder(root);
        // 7 9 8 11 13 12 10
//        postorder(root);
    }

    @Override
    protected String getTextData() {
        return null;
    }


    @Override
    protected int getImageData() {
        return R.drawable.tree;
    }

    @Override
    protected String getResultData() {
        return  "先序遍历：10 8 7 9 12 11 13" +
                "\n" +
                "中序遍历：7 8 9 10 11 12 13" +
                "\n" +
                "后序遍历：7 9 8 11 13 12 10";
    }

    @Override
    protected String getTimeData() {
        return "O(nlogn)";
    }

    @Override
    protected String getSpaceTimeData() {
        return null;
    }

    @Override
    protected String getWendingXingData() {
        return null;
    }

    @Override
    protected String getSummaryData() {
        return null;
    }


    private static final String preorderstr = " protected static void preorder(Node p) {\n"+
            "        if (p != null) {\n"+
            "            Log.i(\"qiyue\",\"data=\"+p.getData());\n"+
            "            preorder(p.getLeftChild());\n"+
            "            preorder(p.getRightChild());\n"+
            "        }\n"+
            "    }";

    /** 递归实现前序遍历
     *
     * 跟左右
     * */
    protected static void preorder(Node p) {
        if (p != null) {
            Log.i("info","data="+p.getData());
            preorder(p.getLeftChild());
            preorder(p.getRightChild());
        }
    }




    private static final String inorderStr = " protected static void inorder(Node p){\n" +
            "        if (p != null) {\n" +
            "            inorder(p.getLeftChild());\n" +
            "            Log.i(\"qiyue\",\"data=\"+p.getData());\n" +
            "            inorder(p.getRightChild());\n" +
            "        }\n" +
            "    }";
    /**
     *  递归实现中序遍历
     *  左右跟
     * @param p
     */
    protected static void inorder(Node p){
        if (p != null) {
            inorder(p.getLeftChild());
            Log.i("info","data="+p.getData());
            inorder(p.getRightChild());
        }
    }

    private static final String postOrderStr = " protected static void postorder(Node p){\n" +
            "        if (p !=null){\n" +
            "            postorder(p.getLeftChild());\n" +
            "            postorder(p.getRightChild());\n" +
            "            Log.i(\"qiyue\",\"data=\"+p.getData());\n" +
            "        }\n" +
            "    }";
    /**
     * 递归实现后续遍历
     * @param p
     */
    protected static void postorder(Node p){
        if (p !=null){
            postorder(p.getLeftChild());
            postorder(p.getRightChild());
            Log.i("info","data="+p.getData());
        }
    }





}
