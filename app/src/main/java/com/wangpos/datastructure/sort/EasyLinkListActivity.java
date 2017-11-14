package com.wangpos.datastructure.sort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wangpos.datastructure.R;

import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

public class EasyLinkListActivity extends AppCompatActivity {

    private CodeView codeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_link_list);

        codeView = (CodeView) findViewById(R.id.codeView);

        codeView.setTheme(CodeViewTheme.DARK);
        codeView.showCode("EasyLinkList<Integer> header = new EasyLinkList<>(1);\n" +
                "        EasyLinkList<Integer> second = new EasyLinkList<>(2);\n" +
                "        EasyLinkList<Integer> three = new EasyLinkList<>(3);\n" +
                "\n" +
                "        header.setNext(second);\n" +
                "        second.setNext(three);\n" +
                "\n" +
                "        while (header != null) {\n" +
                "            Log.i(\"info\", String.valueOf(header.data));\n" +
                "            header = header.getNext();\n" +
                "        }" +
                "\n" +
                " class EasyLinkList<Type> {\n" +
                "\n" +
                "        private Type data;\n" +
                "        private EasyLinkList<Type> next;\n" +
                "\n" +
                "        public EasyLinkList(Type dataParam) {\n" +
                "            this.data = dataParam;\n" +
                "        }\n" +
                "\n" +
                "        public void setNext(EasyLinkList<Type> easyLinkList) {\n" +
                "            this.next = easyLinkList;\n" +
                "        }\n" +
                "\n" +
                "        public EasyLinkList<Type> getNext() {\n" +
                "            return this.next;\n" +
                "        }\n" +
                "\n" +
                "    }");

        EasyLinkList<Integer> header = new EasyLinkList<>(1);
        EasyLinkList<Integer> second = new EasyLinkList<>(2);
        EasyLinkList<Integer> three = new EasyLinkList<>(3);

        header.setNext(second);
        second.setNext(three);

        while (header != null) {
            Log.i("info", String.valueOf(header.data));
            header = header.getNext();
        }

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
