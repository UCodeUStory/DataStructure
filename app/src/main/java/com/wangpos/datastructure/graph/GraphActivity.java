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
        UndirectedGraph undirectedGraph = new UndirectedGraph(vexs,edges);
//        undirectedGraph.print();
//        undirectedGraph.DFS();

        undirectedGraph.BFS();
        addImage("深度优先搜索图解",R.drawable.dfs);
        addItem(new CodeBean("邻接表存储-深度优先搜索",adjacencylist_dfs));
        addImage("广度优先搜索图解",R.drawable.bfs);
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
        return null;
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
}
