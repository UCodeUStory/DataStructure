package com.wangpos.datastructure.sort;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wangpos.datastructure.R;

import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

public class EasyLinkListReverseActivity extends AppCompatActivity implements View.OnClickListener{

    private CodeView codeView;
    private Button btnRun;
    private EasyLinkList<Integer> header;
    private TextView tvResult;
    private TextView tvWeidingxing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_link_list_reverse);

        codeView = (CodeView) findViewById(R.id.codeView);

        btnRun = (Button)findViewById(R.id.btnRun);
        btnRun.setOnClickListener(this);
        codeView.setTheme(CodeViewTheme.DARK);
        tvResult = (TextView) findViewById(R.id.result);
//        tvWeidingxing = (TextView) findViewById(R.id.tvWendingXing);
//
//        tvWeidingxing.setText("");

        codeView.showCode(" public EasyLinkList reverstList(EasyLinkList header) {\n" +
                "\n" +
                "        /**\n" +
                "         * 保存前一个\n" +
                "         */\n" +
                "        EasyLinkList pre = null;\n" +
                "        /**\n" +
                "         * 保存当前\n" +
                "         */\n" +
                "        EasyLinkList current = header;\n" +
                "\n" +
                "        while (current != null) {   // header 表示当前\n" +
                "\n" +
                "            EasyLinkList temp = current.getNext();\n" +
                "\n" +
                "            /**\n" +
                "             * 将当前的next 设置成前一个\n" +
                "             */\n" +
                "            current.setNext(pre);\n" +
                "\n" +
                "            /**\n" +
                "             * 然后当前的变成了下次的前一个\n" +
                "             */\n" +
                "            pre = current;\n" +
                "\n" +
                "            /**\n" +
                "             * 下次current 是本次的 temp 也就是下一个\n" +
                "             */\n" +
                "            current = temp;\n" +
                "\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "        /**\n" +
                "         * 这里current 会复制为null，所以返回pre\n" +
                "         */\n" +
                "        return pre;\n" +
                "//        return current;\n" +
                "    }");

        this.header = new EasyLinkList<>(1);
        EasyLinkList<Integer> second = new EasyLinkList<>(2);
        EasyLinkList<Integer> three = new EasyLinkList<>(3);
        EasyLinkList<Integer> four = new EasyLinkList<>(4);
        EasyLinkList<Integer> five = new EasyLinkList<>(5);
        EasyLinkList<Integer> six = new EasyLinkList<>(6);
        EasyLinkList<Integer> seven = new EasyLinkList<>(7);
        EasyLinkList<Integer> eight = new EasyLinkList<>(8);
        EasyLinkList<Integer> nine = new EasyLinkList<>(9);
        EasyLinkList<Integer> ten = new EasyLinkList<>(10);

        header.setNext(second);
        second.setNext(three);
        three.setNext(four);
        four.setNext(five);
        five.setNext(six);
        six.setNext(seven);
        seven.setNext(eight);
        eight.setNext(nine);
        nine.setNext(ten);



    }


    public EasyLinkList reverstList(EasyLinkList header) {

        /**
         * 保存前一个
         */
        EasyLinkList pre = null;
        /**
         * 保存当前
         */
        EasyLinkList current = header;

        while (current != null) {   // header 表示当前

            EasyLinkList temp = current.getNext();

            /**
             * 将当前的next 设置成前一个
             */
            current.setNext(pre);

            /**
             * 然后当前的变成了下次的前一个
             */
            pre = current;

            /**
             * 下次current 是本次的 temp 也就是下一个
             */
            current = temp;


        }

        /**
         * 这里current 会复制为null，所以返回pre
         */
        return pre;
//        return current;
    }

    @Override
    public void onClick(View view) {
        header = reverstList(header);

        StringBuilder sb = printString(header);

        tvResult.setText(sb.toString());


    }

    @NonNull
    private StringBuilder printString(EasyLinkList<Integer> param) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        EasyLinkList<Integer> header = param;

        while (header != null) {
            sb.append(String.valueOf(header.data));
            if (header.getNext()!=null) {
                sb.append(",");
            }
            header = header.getNext();
        }

        sb.append("]");
        return sb;
    }


    class EasyLinkList<Type> {

        private Type data;
        private EasyLinkList<Type> next;

        public EasyLinkList(Type dataParam) {
            this.data = dataParam;
        }

        public void setNext(EasyLinkList<Type> easyLinkList) {
            this.next = easyLinkList;
        }

        public EasyLinkList<Type> getNext() {
            return this.next;
        }

    }
}
