package com.wangpos.datastructure.graph;

/**
 * Created by qiyue on 2018/1/8.
 */

public class UndirectedGraph {

    private class ENode {
        int ivex;
        ENode nextEdge;
    }


    private class VNode {
        char data;
        ENode firstEdge;
    };


}
