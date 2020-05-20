package com.wangpos.datastructure.algorithm;

import java.util.Arrays;
import java.util.Stack;

public class StringBracketStringInValid {

    public static void main(String[] args) {

        String str = "()()()";
        String str2 = "((()()(()(";
        String str3 = "121()()()";
        System.out.println("result=" + inValidBracketString(str));
        System.out.println("result=" + inValidBracketString(str3));

        String str4 = "())";

        String str5 = "()(()()(";

        System.out.println(maxLengthBrcketString(str4));

        System.out.println(maxLengthBrcketString(str5));
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
         //dp的值代表以当前结尾最长有效子串长度，什么意思？ 比如（)( dp[0]=0 dp[1]=2 dp[2] = 0
        //（这里我会有疑问，我们以为dp[2]= 2 其实不是
        // (() dp[2] = 2 如果结尾是） 从后向前找有效数量
        // ()(() dp[4] = 2 从后向前找到
        //()(()() dp[6] = 4 pre = 5 pre-1 = 4 dp[pre-1] = 2
        //()()) dp[4] = 0
        dp[0] = 0;
        int pre = 0;
        int res = 0;
        for (int i = 1; i < arrays.length; i++) {
            if (arrays[i] == ')') {
                pre = i - dp[i - 1] - 1;
                if (pre >= 0 && arrays[pre] == '(') {
                    //()(()())
                    dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
                }
            }
            res = Math.max(res, dp[i]);
        }
        System.out.println(Arrays.toString(dp));
        return res;
    }

}
