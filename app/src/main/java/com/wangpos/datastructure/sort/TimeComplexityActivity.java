package com.wangpos.datastructure.sort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.wangpos.datastructure.R;

public class TimeComplexityActivity extends AppCompatActivity {

    private TextView tvIntroduce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_complexity);

        tvIntroduce = (TextView)findViewById(R.id.introduce);
        TextView tvTwo = (TextView)findViewById(R.id.tvTwo);

        tvIntroduce.setText("计算机科学中，算法的时间复杂度是一个函数，它定性描述了该算法的运行时间。" +
                "这是一个关于代表算法输入值的字符串的长度的函数。时间复杂度常用大O符号表述，不包括这个函数的低阶项和首项系数。" +
                "使用这种方式时，时间复杂度可被称为是渐近的，它考察当输入值大小趋近无穷时的情况。\n" +
                "1.一般情况下，算法中基本操作重复执行的次数是问题规模n的某个函数，用T(n)表示，若有某个辅助函数f(n),使得当n趋近于无穷大时，T(n)/f(n)的极限值为不等于零的常数，则称f(n)是T(n)的同数量级函数。记作T(n)=O(f(n)),称O(f(n)) 为算法的渐进时间复杂度，简称时间复杂度。\n" +
                "分析：随着模块n的增大，算法执行的时间的增长率和 f(n) 的增长率成正比，所以 f(n) 越小，算法的时间复杂度越低，算法的效率越高。\n" +
                "2. 在计算时间复杂度的时候，先找出算法的基本操作，然后根据相应的各语句确定它的执行次数，再找出 T(n) 的同数量级（它的同数量级有以下：1，log2n，n，n log2n ，n的平方，n的三次方，2的n次方，n!），找出后，f(n) = 该数量级，若 T(n)/f(n) 求极限可得到一常数c，则时间复杂度T(n) = O(f(n))" +
                "\n" +
                "\n" +
                "for(i=1; i<=n; ++i) c1  执行 n次\n" +
                "{\n" +
                "    for(j=1; j<=n; ++j) c2 执行 n平方次\n" +
                "    {\n" +
                "        c[i][j] = 0; c3  //该步骤属于基本操作执行次数：n的平方次\n" +
                "        for(k=1; k<=n; ++k) c4 执行 n立方次\n" +
                "            c[i][j] += a[i][k] * b[k][j]; c5 //该步骤属于基本操作执行次数：n的三次方次\n" +
                "    }\n" +
                "}\n" +
                "则有T(n)=n^3+n^2 ，根据上面括号里的同数量级，我们可以确定 n的三次方 为T（n）的同数量级\n" +
                "则有f(n)=n^3  ，然后根据 T(n)/f(n) 求极限可得到常数c\n" +
                "则该算法的时间复杂度：T(n) = O(n^3) 注：n^3即是n的3次方。" +
                "" +
                "" +
                "上面求解一个算法的时间复杂度过程，假设算法的每一步执行时间c1，c2,c3,执行的次可以通过计算的n, n,n^2,n^2,n^3 等" +
                "\n" +
                "T(n) = c1*n + c2*n^2 + c3*n2 + c4*n^3 + c5*n^3\n" +
                "找到最高级数 ，去除常数项，就是n^3\n");

        tvTwo.setText("" +
                "通常说的时间复杂度就是，找到这个算法的函数，找到对应的同级数，比如 a*n^2 + b*n +c  ，同级数就是n^2，时间复杂度就是n^2\n" +
                "最好的情况，就是分析最少次数，比如排序时可以分析已经有序的 然后重新得出一个方程寻找方程\n" +
                "最坏的情\n" +
                "平均的情况就是一半一半\n");
    }
}
