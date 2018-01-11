package com.wangpos.datastructure.graph;

import com.wangpos.datastructure.R;
import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;

import java.util.List;

/**
 * Created by qiyue on 2018/1/9.
 */

public class TopologicalOrderActivity extends BaseActivity {

    @Override
    protected void initData() {

        addImage("发现V6和v1是没有前驱的，所以我们就随机选去一个输出，我们先输出V6，删除和V6有关的边，得到如下图", R.drawable.t2);
        addImage("然后，我们继续寻找没有前驱的顶点，发现V1没有前驱，所以输出V1，删除和V1有关的边，得到下图的结果", R.drawable.t3);
        addImage("然后，我们又发现V4和V3都是没有前驱的，那么我们就随机选取一个顶点输出（具体看你实现的算法和图存储结构），我们输出V4，得到如下图结果： ", R.drawable.t4);
        addImage("然后，我们输出没有前驱的顶点V3，得到如下结果：", R.drawable.t5);

        addItem(new CodeBean("拓扑排序结果","v6–>v1—->v4—>v3—>v5—>v2"));



        String[] vexs = {"V1", "V2", "V3", "V4", "V5", "V6"};
        String[][] edges = new String[][]{
                {"V1", "V2"},
                {"V1", "V4"},
                {"V1", "V3"},
                {"V3", "V2"},
                {"V3", "V5"},
                {"V4", "V5"},
                {"V6", "V4"},
                {"V6", "V5"}
        };


        //创建有向图
        DirectedGraphMatrix graphMatrix = new DirectedGraphMatrix(vexs,edges);

//        graphMatrix.printPointDegree();

//        graphMatrix.toplogicSort();

        graphMatrix.toplogicSortByDFS();

//        List<Integer> result = graphMatrix.getDfsResult();



    }

    @Override
    protected String getTextData() {
        return null;
    }

    @Override
    protected int getImageData() {
        return R.drawable.t1;
    }

    @Override
    protected String getResultData() {
        return  "  1.拓扑排序介绍，对一个有向无环图(Directed Acyclic Graph简称DAG)G进行拓扑排序，是将G中所有顶点排成一个线性序列，使得图中任意一对顶点u和v，若边(u,v)∈E(G)，则u在线性序列中出现在v之前。 \n" +
                "\n" +
                "  2.举例子，当我们想选修无人驾驶研发课程的时候，我们需要学习机器学习课程，同时学习这个课程我们还要学习算法，并且需要学习一门语言，这种决定了那些课程必须先执行，如果课程很多的话，我们可以来做一个拓扑排序，来决定要先学那些课程\n" +
                "\n" +
                "  3.拓扑排序前提，是一个有向无环图。想想如果我们的选课的时候出现了环路，相互依赖了，那就没办法进行了\n" +
                "\n" +
                "  4.拓扑排序步骤：\t\n" +
                "  A.\t在有向图中选一个没有前驱的顶点并且输出\n" +
                "  B.  从图中删除该顶点和所有以它为尾的弧（白话就是：删除所有和它有关的边)\n" +
                "  C.   重复上述两步，直至所有顶点输出，或者当前图中不存在无前驱的顶点为止，后者代表我们的有向图是有环的，因此，也可以通过拓扑排序来判断一个图是否有环。" +
                "" +
                "5.拓扑排序的结果不一定是唯一的，有可能存在多个入度为0的点";
    }

    @Override
    protected String getTimeData() {
        return null;
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


}
