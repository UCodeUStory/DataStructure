package com.wangpos.datastructure.graph;

import com.wangpos.datastructure.R;
import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;

/**
 * Created by qiyue on 2018/1/2.
 */

public class GraphActivity extends BaseActivity {

    @Override
    protected void initData() {

        String[] vexs = {"A", "B", "C", "D", "E", "F", "G"};
        String[][] edges = new String[][]{
                {"A", "C"},
                {"A", "D"},
                {"A", "F"},
                {"C", "B"},
                {"C", "D"},
                {"F", "G"},
                {"G", "E"},
                };
//        UndirectedGraph undirectedGraph = new UndirectedGraph(vexs,edges);
//        undirectedGraph.print();
//        undirectedGraph.DFS();
//
//        undirectedGraph.BFS();

        UndirectedGraphMatrix undirectedGraphMatrix = new UndirectedGraphMatrix(vexs,edges);
        undirectedGraphMatrix.DFS();
        undirectedGraphMatrix.BFS();


        addImage("深度优先搜索图解",R.drawable.dfs);
        addItem(new CodeBean("邻接表存储-深度优先搜索代码",adjacencylist_dfs));
        addImage("广度优先搜索图解",R.drawable.bfs);
        addItem(new CodeBean("邻接表存储-广度优先搜索代码",bfs_code));

        addItem(new CodeBean("邻接矩阵存储-深度优先搜索代码",matrix_dfs_code));
        addItem(new CodeBean("邻接矩阵存储-广度优先搜索代码",matrix_bfs_code));

    }

    @Override
    protected String getTextData() {
        return null;
    }

    @Override
    protected int getImageData() {
        return R.drawable.tu;
    }

    @Override
    protected String getResultData() {
        return "\n" +
                "1.邻接表存储方式：就是每个顶点对应一个链表，链表内容 就是 所有可以到达的边，（边信息包含这条边尾部点的数组位置信息，和下一条边）\n" +
                "\n" +
                "2.深度优先搜索：从一个点出发寻找第一条边，再从这个点出发寻找一条边，这样递归的找下去\n" +
                "\n" +
                "3.广度优先搜索：相当于从一个点出发寻找他的所有直达的边，然后在遍历每个边对应点的所有到达的边（实现方式可以使用队列的方式，将被访问的点加入队列，访问后出队列，将其他直接到达的点加入队列，一次访问每一个点，访问后将改点出队列，通过时遍历他的直接到达点入队列）\n";
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



    public static String adjacencylist_dfs = "  " +
            "        public void DFS() {\n" +
            "        boolean[] visited = new boolean[mVNodeArrays.length];       // 顶点访问标记\n" +
            "\n" +
            "        // 初始化所有顶点都没有被访问\n" +
            "        for (int i = 0; i < mVNodeArrays.length; i++)\n" +
            "            visited[i] = false;\n" +
            "\n" +
            "        Log.i(\"tu\",\"DFS:\");\n" +
            "        for (int i = 0; i < mVNodeArrays.length; i++) {\n" +
            "            if (!visited[i])\n" +
            "                DFS(i, visited);\n" +
            "        }\n" +
            "        Log.i(\"tu\",\"\\n\");\n" +
            "    }" +
            "" +
            "\n" +
            "" +
            "    private void DFS(int i, boolean[] visited) {\n" +
            "        ENode node;\n" +
            "\n" +
            "        visited[i] = true;\n" +
            "//        System.out.printf(\"%c \", mVNodeArrays[i].data);\n" +
            "        Log.i(\"tu\",mVNodeArrays[i].data);\n" +
            "        node = mVNodeArrays[i].firstEdge;\n" +
            "        while (node != null) {\n" +
            "            if (!visited[node.ivex])\n" +
            "                DFS(node.ivex, visited);\n" +
            "            node = node.nextEdge;\n" +
            "        }\n" +
            "    }";


    String bfs_code = "  /**\n" +
            "     * 广度优先搜索 A C D F B G E\n" +
            "     */\n" +
            "    public void BFS() {\n" +
            "\n" +
            "        boolean[] visited = new boolean[mVNodeArrays.length];       // 顶点访问标记\n" +
            "\n" +
            "        // 初始化所有顶点都没有被访问\n" +
            "        for (int i = 0; i < mVNodeArrays.length; i++) {\n" +
            "            visited[i] = false;\n" +
            "        }\n" +
            "\n" +
            "        Log.i(\"tu\", \"BFS\");\n" +
            "\n" +
            "        int head = 0;\n" +
            "        int rear = 0;\n" +
            "        int[] queue = new int[mVNodeArrays.length];\n" +
            "\n" +
            "        for (int i = 0; i < mVNodeArrays.length; i++) {\n" +
            "            if (!visited[i]) {\n" +
            "                visited[i] = true;\n" +
            "\n" +
            "                Log.i(\"tu\", \"y\" + mVNodeArrays[i].data);\n" +
            "                //入列\n" +
            "                queue[rear] = i;\n" +
            "                rear++;\n" +
            "            }\n" +
            "\n" +
            "            //rear之前的都是被访问的点，通过header 去访问每个点下一层，访问的点通过rear位置加入队列\n" +
            "\n" +
            "            while (head != rear) {\n" +
            "                int j = queue[head];\n" +
            "                ENode node = mVNodeArrays[j].firstEdge;\n" +
            "//                Log.i(\"tu\", \"node--\"+mVNodeArrays[j].data +\"node=\"+node);\n" +
            "                // 开始遍历j的所有边，并且入队列 start\n" +
            "                while (node != null) {\n" +
            "                    int k = node.ivex;\n" +
            "//                    Log.i(\"tu\", \"node--\"+mVNodeArrays[j].data +\"k=\"+k+\"visited[k]= \"+visited[k]);\n" +
            "                    if (!visited[k]) {\n" +
            "                        visited[k] = true;\n" +
            "                        Log.i(\"tu\", \"\" + mVNodeArrays[k].data);\n" +
            "                        queue[rear] = k;\n" +
            "                        rear++;\n" +
            "                    }\n" +
            "                    node = node.nextEdge;\n" +
            "                }\n" +
            "                // 开始遍历j的所有边，end\n" +
            "//                Log.i(\"tu\", \"》》》》\" );\n" +
            "                //出队列，\n" +
            "                head++;\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "    }";

    static String matrix_dfs_code = " /*\n" +
            "    * 返回顶点v的第一个邻接顶点的索引，失败则返回-1\n" +
            "    */\n" +
            "    private int firstVertex(int v) {\n" +
            "\n" +
            "        if (v<0 || v>(mVexs.length-1))\n" +
            "            return -1;\n" +
            "\n" +
            "        for (int i = 0; i < mVexs.length; i++)\n" +
            "            if (mMatrix[v][i] == 1)\n" +
            "                return i;\n" +
            "\n" +
            "        return -1;\n" +
            "    }\n" +
            "\n" +
            "    /*\n" +
            "     * 返回顶点v相对于w的下一个邻接顶点的索引，失败则返回-1\n" +
            "     */\n" +
            "    private int nextVertex(int v, int w) {\n" +
            "\n" +
            "        if (v<0 || v>(mVexs.length-1) || w<0 || w>(mVexs.length-1))\n" +
            "            return -1;\n" +
            "\n" +
            "        for (int i = w + 1; i < mVexs.length; i++)\n" +
            "            if (mMatrix[v][i] == 1)\n" +
            "                return i;\n" +
            "\n" +
            "        return -1;\n" +
            "    }\n" +
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
            "            if (!visited[w])\n" +
            "                DFS(w, visited);\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    /*\n" +
            " * 深度优先搜索遍历图\n" +
            " */\n" +
            "    public void DFS() {\n" +
            "        boolean[] visited = new boolean[mVexs.length];       // 顶点访问标记\n" +
            "\n" +
            "        // 初始化所有顶点都没有被访问\n" +
            "        for (int i = 0; i < mVexs.length; i++)\n" +
            "            visited[i] = false;\n" +
            "\n" +
            "        Log.i(\"tu\",\"DFS: \");\n" +
            "        for (int i = 0; i < mVexs.length; i++) {\n" +
            "            if (!visited[i])\n" +
            "                DFS(i, visited);\n" +
            "        }\n" +
            "        Log.i(\"tu\",\"\");\n" +
            "    }";

    static String matrix_bfs_code = "  /*\n" +
            "    * 广度优先搜索（类似于树的层次遍历）\n" +
            "    */\n" +
            "    public void BFS() {\n" +
            "        int head = 0;\n" +
            "        int rear = 0;\n" +
            "        int[] queue = new int[mVexs.length];            // 辅组队列\n" +
            "        boolean[] visited = new boolean[mVexs.length];  // 顶点访问标记\n" +
            "\n" +
            "        for (int i = 0; i < mVexs.length; i++)\n" +
            "            visited[i] = false;\n" +
            "\n" +
            "        Log.i(\"tu\",\"BFS: \");\n" +
            "        for (int i = 0; i < mVexs.length; i++) {\n" +
            "            if (!visited[i]) {\n" +
            "                visited[i] = true;\n" +
            "                Log.i(\"tu\", mVexs[i]);\n" +
            "                queue[rear++] = i;  // 入队列\n" +
            "            }\n" +
            "\n" +
            "            while (head != rear) {\n" +
            "                int j = queue[head++];  // 出队列\n" +
            "                for (int k = firstVertex(j); k >= 0; k = nextVertex(j, k)) { //k是为访问的邻接顶点\n" +
            "                    if (!visited[k]) {\n" +
            "                        visited[k] = true;\n" +
            "                        Log.i(\"tu\", mVexs[k]);\n" +
            "                        queue[rear++] = k;\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "        Log.i(\"tu\",\"\");\n" +
            "    }";
}
