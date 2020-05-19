package com.wangpos.datastructure.algorithm;

import java.util.Stack;

public class StringBracketStringInValid {

    public static void main(String[] args) {

        String str = "()()()";
        String str2 = "((()()(()(";
        String str3 = "121()()()";
        System.out.println("result=" + inValidBracketString(str));
        System.out.println("result=" + inValidBracketString(str3));
    }

    public static boolean inValidBracketString(String origin) {
        char[] arrays = origin.toCharArray();
        Stack<Character> stack = new Stack<Character>();

        for (char data : arrays) {
            if (data != '(' && data != ')') {
                return false;
            }
            if (stack.isEmpty()) {
                stack.push(data);
            } else {
                char top = stack.peek();
                if (top == '(' && data == ')') {
                    stack.pop();
                } else {
                    stack.push(data);
                }
            }
        }

        if (stack.isEmpty()) {
            return true;
        }
        return false;
    }


    public static int maxLengthBrcketString(String origin) {
        char[] arrays = origin.toCharArray();
        int length = arrays.length;
        int dp[] = new int[length];

        dp[0] = 0;
        for (int i = 1; i < arrays.length; i++) {
            if (arrays[i] == ')' && arrays[i - 1] == '(') {
                if (dp[i - 2] == dp[i - 1]) {

                }
            }
        }


    }

}
