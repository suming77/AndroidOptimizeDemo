package com.example.androidoptimizedemo.memoryOpt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/4 20:54
 * @类描述 ${TODO}AsyncTask造成的内存泄漏以及解决方法
 */
public class NotStaticActivity extends AppCompatActivity {
    private static final String TAG = NotStaticActivity.class.getSimpleName();
    //非静态内部类实例引用，设置为静态
    private static InnerClass sInnerClass;
    //静态内部类实例引用
    private static InnerClass2 sInnerClass2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //保证非静态内部类实例只有一个
        if (sInnerClass == null) {
            sInnerClass = new InnerClass();
        }

        //正确
        if (sInnerClass2 == null) {
            sInnerClass2 = new InnerClass2();
        }
    }

    //反例
    //非静态内部类
    private class InnerClass {
        //……
    }

    //正例
    //静态内部类
    //解决方法：1、将非静态内部类设置为静态内部类（静态内部类默认不持有外部类的引用）
    private static class InnerClass2 {
        //……
    }
    //解决方法： 2、将外部类抽取出来封装成一个单例 如:InnerClass3.class
}
