package com.wangpos.datastructure.algorithm;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 支持整数加减乘除公式
 */
public class StringFormulaCalculate {

    public static void main(String[] args) {
        /**
         * 加减法计算
         */
        String formula = "1+2-1+5-6";
        /**
         * 加减乘
         */
        String formula1 = "1+2*3"; //7
        String formula2 = "1+2*3+4"; //11
//        String formula3 = "1*2+3+4"; //9
        String formula4 = "1-2+3*4+6/2";//14

        String formula5 = "2*(4-2)+5";

        String formula6 = "2*(4-2)";

        String formula7 = "(4-2)*2";
        String formula8 = "10*((5-2)-2)";
        String formula9 = "48*((70-65)-43)+8*1";
//        String formula = "48*((70-65)-43)+8*1";
//        String formula = "48*((70-65)-43)+8*1";

//        calculate(formula1, 0);
//        calculate(formula2, 0);
//        calculate(formula3, 0);
//        calculate(formula4,0);
        calculate(formula9, 0);
        Deque<String> queues2 = queues;
        System.out.println("结果" + queues);
    }

    /**
     * 存储 数字和运算符
     */
    private static Deque<String> queues = new LinkedList<>();

    private static void addLastNum(Integer cur) {
        String curNum = cur.toString();
        if (queues.size() > 1) {
            String sign = queues.removeLast();
            String prev = queues.removeLast();
            int result = calculateExpress(prev, curNum, sign);
            queues.add(String.valueOf(result));
        } else {
            queues.add(curNum);
        }
        if (queues.size() > 1) {
            String last = queues.removeLast();
            addLastNum(Integer.valueOf(last));
        }
    }

    /**
     * 新加元素进行判断，如果前面是* / 直接运算，然后存入
     * 如果+ - 暂时不运算
     */
    private static void addNum(Integer cur) {
        String curNum = cur.toString();
        String sign = queues.peekLast();
        if ("*".equals(sign) || "/".equals(sign)) {
            queues.removeLast();
            String lastNum = queues.pollLast();
            int result = calculateExpress(lastNum, curNum, sign);
            queues.add(String.valueOf(result));
        } else if ("+".equals(sign) || "-".equals(sign)) {
            queues.add(curNum);
        } else {
            queues.add(curNum);
        }
    }

    /**
     * 如果+ - ,前两个有值运算前两个
     * 否者直接加入
     *
     * @param sign
     */
    private static Integer addSign(char sign) {
        if (sign == '+' || sign == '-' || sign == '(') {
            String last = queues.peekLast();
            if ("(".equals(last) || queues.size() < 2) {
            } else if (queues.size() > 2) {
                String prev1 = queues.removeLast();
                String s = queues.removeLast();
                String prev3 = queues.removeLast();
                if ("(".equals(prev1)||"(".equals(s)||"(".equals(prev3)){
                    queues.add(prev3);
                    queues.add(s);
                    queues.add(prev1);
                }else {
                    int result = calculateExpress(prev3, prev1, s);
                    queues.add(String.valueOf(result));
                }
            }
            queues.add(String.valueOf(sign));
        } else if (sign == '*' || sign == '/') {
            queues.add(String.valueOf(sign));
        }

        if (sign == ')') {
            String prev1 = queues.removeLast();
            String s = queues.removeLast();
            String prev3 = queues.removeLast();
            int result = calculateExpress(prev3, prev1, s);
            queues.removeLast();//移除左括号
            queues.add(String.valueOf(result));
            return result;
        }
        return 0;
    }

    /**
     * 结果表示 遍历到哪一位，和计算结果
     *
     * @param formula
     * @param
     * @return
     */
    private static int[] calculate(String formula, int index) {

        int s1 = 0;

        char[] f = formula.toCharArray();
        for (int i = index; i < formula.length(); i++) {
            if (isNumber(f[i])) {
                //转数字
                s1 = s1 * 10 + f[i] - '0';

            } else if (isSign(f[i])) {
                // 运算符前一定有数字
                if(s1>0) {
                    addNum(s1);
                    s1 = 0;
                }
                addSign(f[i]);
            } else if (f[i] == '(') {
                addSign(f[i]);
                int result[] = calculate(formula, i + 1);
                i = result[1];
                //记录回溯的位置，因为后面还可能有元素要处理
                //比如  1 + 2  *（ 3 +  4 ）+ 5
            } else if (f[i] == ')') {
                addNum(s1);
                int result = addSign(f[i]);
                s1 = 0;
                return new int[]{result, i};
            }

        }
        /**
         * 综合计算
         */
        if (s1 != 0) {
            addLastNum(s1);
            s1 = 0;
        }else{
            if(queues.size()>1){
                String last = queues.removeLast();
                addLastNum(Integer.valueOf(last));
            }
        }

        return new int[]{0, formula.length()};
    }

    private static boolean isSign(char c) {
        if (c == '*' || c == '/' || c == '-' || c == '+') {
            return true;
        }
        return false;
    }

    private static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    private static int calculateExpress(String s1, String s2, String sign) {
        int num1 = Integer.parseInt(s1);
        int num2 = Integer.parseInt(s2);
        int result = 0;
        switch (sign) {
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
        }
        return result;
    }


}
