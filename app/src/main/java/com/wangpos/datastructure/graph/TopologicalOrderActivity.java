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

        addItem(new CodeBean("深度优先搜搜拓扑排序",sortByDFSCode));



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
        return "\n" +
                "拓扑排序算法\n" +
                "\n" +
                "1.Kahn 算法 \n" +
                "      每次从该集合中取出(没有特殊的取出规则，随机取出也行，使用队列/栈也行，下同)一个顶点，将该顶点放入保存结果的List中。\n" +
                "紧接着循环遍历由该顶点引出的所有边，从图中移除这条边，同时获取该边的另外一个顶点，如果该顶点的入度在减去本条边之后为0，那么也将这个顶点放到入度为0的集合中。然后继续从集合中取出一个顶点\n" +
                " \n" +
                "注意：当集合为空之后，检查图中是否还存在任何边，如果存在的话，说明图中至少存在一条环路。不存在的话则返回结果List，此List中的顺序就是对图进行拓扑排序的结果。\n" +
                "\n" +
                "2.深度优先搜索算法 \n" +
                "\n" +
                "    DFS的实现更加简单直观，使用递归实现。利用DFS实现拓扑排序，实际上只需要添加一行代码，即上面伪码中的最后一行：add n to L。\n" +
                "需要注意的是，将顶点添加到结果List中的时机是在visit方法即将退出之时。\n" +
                "这个算法的实现非常简单，但是要理解的话就相对复杂一点。\n" +
                "关键在于为什么在visit方法的最后将该顶点添加到一个集合中，就能保证这个集合就是拓扑排序的结果呢？\n" +
                "因为添加顶点到集合中的时机是在dfs方法即将退出之时，而dfs方法本身是个递归方法，只要当前顶点还存在边指向其它任何顶点，它就会递归调用dfs方法，而不会退出。因此，退出dfs方法，意味着当前顶点没有指向其它顶点的边了，即当前顶点是一条路径上的最后一个顶点。\n" +
                " \n" +
                "下面简单证明一下它的正确性：\n" +
                "考虑任意的边v->w，当调用dfs(v)的时候，有如下三种情况：\n" +
                "1. dfs(w)还没有被调用，即w还没有被mark，此时会调用dfs(w)，然后当dfs(w)返回之后，dfs(v)才会返回\n" +
                "2. dfs(w)已经被调用并返回了，即w已经被mark\n" +
                "3. dfs(w)已经被调用但是在此时调用dfs(v)的时候还未返回\n" +
                "需要注意的是，以上第三种情况在拓扑排序的场景下是不可能发生的，因为如果情况3是合法的话，就表示存在一条由w到v的路径。而现在我们的前提条件是由v到w有一条边，这就导致我们的图中存在环路，从而该图就不是一个有向无环图(DAG)，而我们已经知道，非有向无环图是不能被拓扑排序的。\n" +
                " \n" +
                "那么考虑前两种情况，无论是情况1还是情况2，w都会先于v被添加到结果列表中。所以边v->w总是由结果集中后出现的顶点指向先出现的顶点。为了让结果更自然一些，可以使用栈来作为存储最终结果的数据结构，从而能够保证边v->w总是由结果集中先出现的顶点指向后出现的顶点\n";
    }


    public static String sortByKahnCode = "" +
            "public void toplogicSort() {\n" +
            "        int header = 0;\n" +
            "        int result[] = new int[mVexs.length];\n" +
            "        for (int i = 0; i < mVexs.length; i++) {\n" +
            "            result[i] = -1;\n" +
            "        }\n" +
            "\n" +
            "        for (int i = 0; ; i++) {\n" +
            "            if (inDegrees[i] == 0) {\n" +
            "\n" +
            "                boolean isVisit = false;\n" +
            "                for (int m = 0; m < header; m++) {\n" +
            "                    if (result[m] == i) {\n" +
            "                        isVisit = true;\n" +
            "                    }\n" +
            "                }\n" +
            "                if (!isVisit) {\n" +
            "                    result[header] = i;\n" +
            "                    deletePositin(i);\n" +
            "                    header++;\n" +
            "                    if (header == inDegrees.length) {\n" +
            "                        break;\n" +
            "                    }\n" +
            "\n" +
            "                }\n" +
            "                if (i == 5) {\n" +
            "                    i = 0;\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        /***\n" +
            "         * 输出\n" +
            "         */\n" +
            "\n" +
            "        for (int i = 0; i < header; i++) {\n" +
            "            Log.i(\"tu\", mVexs[result[i]]);\n" +
            "        }\n" +
            "\n" +
            "    }";



    public static String sortByDFSCode = "\n" +
            "    public void toplogicSortByDFS(){\n" +
            "\n" +
            "\n" +
            "        boolean[] visited = new boolean[mVexs.length];       // 顶点访问标记\n" +
            "\n" +
            "        // 初始化所有顶点都没有被访问\n" +
            "        for (int i = 0; i < mVexs.length; i++)\n" +
            "            visited[i] = false;\n" +
            "\n" +
            "        Log.i(\"tu\",\"DFS: \");\n" +
            "        for (int i = 0; i < mVexs.length; i++) {\n" +
            "            if (!visited[i]) {\n" +
            "                DFS(i, visited);\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        for (int i=dfsResult.size()-1;i>=0;i--){\n" +
            "            Log.i(\"topo\",mVexs[dfsResult.get(i).intValue()]);\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "\n" +
            "    /*\n" +
            "     * 深度优先搜索遍历图的递归实现\n" +
            "     */\n" +
            "    private void DFS(int i, boolean[] visited) {\n" +
            "\n" +
            "        visited[i] = true;\n" +
            "        Log.i(\"tu\",mVexs[i]);\n" +
            "        // 遍历该顶点的所有邻接顶点。若是没有访问过，那么继续往下走\n" +
            "        for (int w = firstVertex(i); w >= 0; w = nextVertex(i, w)) {\n" +
            "            if (!visited[w]) {\n" +
            "                DFS(w, visited);\n" +
            "\n" +
            "            }\n" +
            "        }\n" +
            "        dfsResult.add(i);\n" +
            "    }";
}
