package com.wangpos.datastructure.sort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wangpos.datastructure.R;

import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

public class EasyLinkListReverseActivity extends AppCompatActivity {

    private CodeView codeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_link_list_reverse);

        codeView = (CodeView) findViewById(R.id.codeView);

        codeView.setTheme(CodeViewTheme.DARK);
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

        EasyLinkList<Integer> header = new EasyLinkList<>(1);
        EasyLinkList<Integer> second = new EasyLinkList<>(2);
        EasyLinkList<Integer> three = new EasyLinkList<>(3);

        header.setNext(second);
        second.setNext(three);

        header = reverstList(header);

        while (header != null) {
            Log.i("info", String.valueOf(header.data));
            header = header.getNext();
        }


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
