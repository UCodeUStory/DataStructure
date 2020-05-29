package com.wangpos.datastructure.algorithm;

import java.util.ArrayList;
import java.util.List;

public class StringToDiKaEr {


    public static void main(String[] args) {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> listSub1 = new ArrayList<String>();
        List<String> listSub2 = new ArrayList<String>();
        List<String> listSub3 = new ArrayList<String>();
        List<String> listSub4 = new ArrayList<String>();
        List<String> listSub5 = new ArrayList<String>();
        List<String> listSub6 = new ArrayList<String>();
        List<String> listSub7 = new ArrayList<String>();
        List<String> listSub8 = new ArrayList<String>();
        listSub1.add("A");
        listSub1.add("B");
        listSub1.add("C");

        listSub2.add("D");
        listSub2.add("E");
        listSub2.add("F");

        listSub3.add("G");
        listSub3.add("H");
        listSub3.add("I");

        listSub4.add("J");
        listSub4.add("K");
        listSub4.add("L");

        listSub5.add("M");
        listSub5.add("N");
        listSub5.add("O");

        listSub6.add("P");
        listSub6.add("Q");
        listSub6.add("R");

        listSub7.add("S");
        listSub7.add("T");
        listSub7.add("U");

        listSub8.add("V");
        listSub8.add("W");
        listSub8.add("X");


        list.add(listSub1);
        list.add(listSub2);
        list.add(listSub3);
        list.add(listSub4);
        list.add(listSub5);
        list.add(listSub6);
        list.add(listSub7);
        list.add(listSub8);

        System.out.println("start:"+System.currentTimeMillis());
        List<List<String>> result = new ArrayList<List<String>>();
        descartes(list, result, 0, new ArrayList<String>());
         System.out.println("end:"+System.currentTimeMillis());
    }
    /**
     * Created on 2014年4月27日
     * <p>
     * Discription:笛卡尔乘积算法
     * 把一个List{[1,2],[3,4],[a,b]}转化成List{[1,3,a],[1,3,b],[1,4
     * ,a],[1,4,b],[2,3,a],[2,3,b],[2,4,a],[2,4,b]}数组输出
     * </p>
     *
     * @param layer
     *            中间参数
     * @param curList
     *            中间参数
     */
    private static void descartes(List<List<String>> dimvalue,
                                  List<List<String>> result, int layer, List<String> curList) {
        if (layer < dimvalue.size() - 1) {
            if (dimvalue.get(layer).size() == 0) {
                descartes(dimvalue, result, layer + 1, curList);
            } else {
                for (int i = 0; i < dimvalue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<String>(curList);
                    list.add(dimvalue.get(layer).get(i));
                    descartes(dimvalue, result, layer + 1, list);
                }
            }
        } else if (layer == dimvalue.size() - 1) {
            if (dimvalue.get(layer).size() == 0) {
                result.add(curList);
            } else {
                for (int i = 0; i < dimvalue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<String>(curList);
                    list.add(dimvalue.get(layer).get(i));
                    result.add(list);
                }
            }
        }
    }
}
