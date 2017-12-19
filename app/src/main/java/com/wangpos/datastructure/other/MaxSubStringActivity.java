package com.wangpos.datastructure.other;

import android.util.Log;
import android.widget.BaseAdapter;

import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiyue on 2017/12/19.
 *
 * 最长的子字符串不重复字符
 */

public class MaxSubStringActivity extends BaseActivity {
    @Override
    protected void initData() {


//        Log.i("sub",""+lengthOfLongestSubstring("abcdefghi"));
//        Log.i("sub",""+lengthOfLongestSubstring("aabccdefghi"));

        Log.i("sub",""+lengthOfLongestSubstring("aabcadefghi"));//实际这个bcacefghi  //[a, c, d, e, f, g, h, i]
        addItem(new CodeBean("最长的子字符串不重复字符",code));

    }

    @Override
    protected String getTextData() {
        return "在计算机中，所有的数据在存储和运算时都要使用二进制数表示（因为计算机用高电平和低电平分别表示1和0）" +
                "，例如，像a、b、c、d这样的52个字母（包括大写）、以及0、1等数字还有一些常用的符号（例如*、#、@等）在计算机中存储时也要使用二进制数来表示，" +
                "而具体用哪些二进制数字表示哪个符号，当然每个人都可以约定自己的一套（这就叫编码），而大家如果要想互相通信而不造成混乱，" +
                "那么大家就必须使用相同的编码规则，" +
                "于是美国有关的标准化组织就出台了ASCII编码，统一规定了上述常用符号用哪些二进制数来表示。 "+

                "\n" +
                "\n" + "" +
                "ASCII 码使用指定的7 位或8 位二进制数组合来表示128 或256 种可能的字符。计算机的标准，一个英文用1个字节表示，一个中文用2个字节表示";
    }

    @Override
    protected int getImageData() {
        return 0;
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

//
//    public int lengthOfLongestSubstring(String s) {
//        // write your code here
//        if(s.length()==0){
//            return 0;
//        }
//        int maxLength=1;
//        List<Character> list=new ArrayList<Character>();
//        for (int i=0;i<s.length();i++) {
//            if (list.contains(s.charAt(i))) {
//                list = list.subList(list.indexOf(s.charAt(i))+1,list.size());
//                list.add(s.charAt(i));
//                maxLength = Math.max(maxLength,list.size());
//            }else{
//                list.add(s.charAt(i));
//                maxLength = Math.max(maxLength,list.size());
//            }
//        }
//
//
//        Log.i("sub","最长子串="+list.toString());
//        return maxLength;
//    }



    private static String code = "       public int lengthOfLongestSubstring(String s) {\n" +
            "        int[] count = new int[256];\n" +
            "\n" +
            "        int maxLen = 0;\n" +
            "\n" +
            "//        count['a'] = 10;\n" +
            "\n" +
            "        for (int k=0;k<count.length;k++){\n" +
            "            Log.i(\"sub\",\"\"+k+\"=\" + count[k]);\n" +
            "        }\n" +
            "        for (int start = 0, j = 0; j < s.length(); ) {\n" +
            "\n" +
            "            Log.i(\"sub\",\"count[s.charAt(j)]=\"+count[s.charAt(j)]);\n" +
            "\n" +
            "            if (count[s.charAt(j)] == 0) {//一次没出现过\n" +
            "                count[s.charAt(j)]++;\n" +
            "                maxLen = Math.max(maxLen, j - start + 1);\n" +
            "                j++;\n" +
            "            } else {\n" +
            "                //否者 ,数组中的这个数字-1，start 后移一位\n" +
            "                --count[s.charAt(start++)];\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        return maxLen;\n" +
            "    }";

    public int lengthOfLongestSubstring(String s) {
        int[] count = new int[256];

        int maxLen = 0;

//        count['a'] = 10;

        for (int start = 0, j = 0; j < s.length(); ) {

            Log.i("sub","count[s.charAt(j)]="+count[s.charAt(j)]);

            if (count[s.charAt(j)] == 0) {//一次没出现过
                count[s.charAt(j)]++;
                maxLen = Math.max(maxLen, j - start + 1);
                j++;
            } else {
                //否者 ,数组中的这个数字-1，start 后移一位
                --count[s.charAt(start++)];
            }
        }

        return maxLen;
    }


    /**
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring2(String s) {
        char[] chars = s.toCharArray();
        if(2 > chars.length){
            return chars.length;
        }
        int max = 0;
        int split_at = 0;
        int cur_len = 1;
        for(int i=1;i<chars.length;i++){
            int j = split_at;
            for(;j<i;j++){
                if(chars[i] == chars[j]){
                    break;
                }
            }
            if(j < i){
                split_at = j+1;
                cur_len = i-j;
            }else{
                cur_len++;
            }
            if(cur_len > max) max = cur_len;
        }
        return max;
    }


}
