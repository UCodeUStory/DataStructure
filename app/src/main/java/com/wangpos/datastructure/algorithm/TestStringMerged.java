package com.wangpos.datastructure.algorithm;

import java.util.HashMap;
import java.util.HashSet;

public class TestStringMerged {

    public static void main(String[] args) {

        String str1 = "ABCD";

        String str2 = "EFGH";

        String str3 = "IJK";

        String str4 = "LMN";

        String str5 = "OPQ";

        String str6 = "RST";

        String str7 = "UVW";

        String str8 = "XYZ";

        StringMerged sm = new StringMerged();

        sm.findAllKindSplice(str1);
        sm.findAllKindSplice(str2);
        sm.findAllKindSplice(str3);
        sm.findAllKindSplice(str4);
        sm.findAllKindSplice(str5);
        sm.findAllKindSplice(str6);
        sm.findAllKindSplice(str7);
        System.out.println("startTime " + System.currentTimeMillis());
        HashSet<String> resultSet = sm.findAllKindSplice(str8);
        System.out.println("endTime " + System.currentTimeMillis());


//        for (String s : resultArray) {
//            System.out.print(s + " ");
//        }

        System.out.println();
//        System.out.println("______________________");
////
        System.out.println("种类" + resultSet.size());
//        for (String s : resultSet) {
//            System.out.print(s + " ");
//        }


    }
}
