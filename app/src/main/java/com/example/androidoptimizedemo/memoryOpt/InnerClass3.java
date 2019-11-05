package com.example.androidoptimizedemo.memoryOpt;

import android.support.v7.app.AppCompatActivity;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/4 20:54
 * @类描述 ${TODO}解决方法： 2、将外部类抽取出来封装成一个单例。
 */
public class InnerClass3 extends AppCompatActivity {
    private static final String TAG = InnerClass3.class.getSimpleName();
    private static InnerClass3 sInnerClass3;

    //单例
    public InnerClass3 getInstance() {
        if (sInnerClass3 == null) {
            sInnerClass3 = new InnerClass3();
        }
        return sInnerClass3;
    }
}
