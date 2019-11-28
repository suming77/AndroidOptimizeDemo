package com.example.androidoptimizedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/25 17:48
 * @类描述 ${TODO}线程停止
 */
public class ThreadStopActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private Thread mThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mThread = new Thread(new MyThread());
        mThread.start();
        mThread.interrupt();

    }

    private volatile boolean isStop;

    private class MyThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                    Log.e(TAG, "执行数据：" + i + " | 是否中断 == " + mThread.isInterrupted());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (isStop) {

            }
        }
    }
}
