package com.example.androidoptimizedemo.ImageOpt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.androidoptimizedemo.R;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/13 16:29
 * @类描述 ${TODO}线程池简单使用
 */
public class ThreadPoolActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nothing);

        //核心线程数为3个，最大线程数为10个，保活时间为2秒,任务队列最大为4个
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10,
                2, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(4));
        //手动创建十个请求，线程池执行
        for (int i = 0; i < 10; i++) {
            final int index = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.e(TAG, "线程：" + Thread.currentThread().getName() + ",正在执行第" + index + "个任务");
                        Thread.currentThread().sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            //分别将请求添加到线程池中
            executor.execute(runnable);
        }
    }
}
