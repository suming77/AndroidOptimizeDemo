package com.example.androidoptimizedemo.memoryOpt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.androidoptimizedemo.R;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/4 20:54
 * @类描述 ${TODO}Thread类造成的内存泄漏以及解决方法
 */
public class ThreadActivity extends AppCompatActivity {
    private static final String TAG = ThreadActivity.class.getSimpleName();
    private RightThread mRightThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nothing);

        //反例
        //方式一：新建内部类
        new MyThread().start();

        //方式二：匿名Thread内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //正例
        //解决方法：1、将线程类Thread修改为静态内部类，线程类Thread则不再持有外部类的引用
        mRightThread = new RightThread();
        mRightThread.start();
    }

    //自定义Thread
    private class MyThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //创建静态Thread
    private static class RightThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onDestroy() {
        //解决方法：2、当外部类结束时，强制结束线程
        //Thread.stop方法以及过时而且这样停止线程会产生不可预期的错误，https://blog.csdn.net/anhuidelinger/article/details/11746365
//        mRightThread.stop();
        mRightThread.interrupt();
        super.onDestroy();
    }
}
