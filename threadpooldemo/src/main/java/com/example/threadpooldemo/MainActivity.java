package com.example.threadpooldemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * 我的博客：https://blog.csdn.net/m0_37796683
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = this.getClass().getSimpleName();

    private ExecutorService mExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_stop).setOnClickListener(this);
        findViewById(R.id.btn_thread_pool).setOnClickListener(this);
        findViewById(R.id.btn_new_fixed).setOnClickListener(this);
        findViewById(R.id.btn_new_cached).setOnClickListener(this);
        findViewById(R.id.btn_new_single).setOnClickListener(this);
        findViewById(R.id.btn_scheduled).setOnClickListener(this);
        findViewById(R.id.btn_threadpool_manager).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_stop:
                //4.关闭线程池
                mExecutor.shutdown();
                break;
            case R.id.btn_thread_pool://普通线程池创建
                threadPool();
                break;
            case R.id.btn_new_fixed://定长线程池
                newFixedThreadPool();
                break;
            case R.id.btn_new_cached://可缓存线程池
                newCachedThreadPool();
                break;
            case R.id.btn_new_single://单线程化线程池
                newSingleThreadExecutor();
                break;
            case R.id.btn_scheduled://定时线程池
                newScheduledThreadPool();
                break;
            case R.id.btn_threadpool_manager://线程池管理类
                threadPoolManager();
                break;
            default:
                break;
        }
    }

    /**
     * 普通创建
     */
    private void threadPool() {
        //1.创建线程池：核心线程数为3，最大线程数为12，非核心线程保活时间为1秒，任务队列容量为4
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 12,
                1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(4));
        //2.创建任务：手动创建10个任务请求
        for (int i = 0; i < 10; i++) {
            final int index = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.e(TAG, "线程：" + Thread.currentThread().getName() + " 正在执行" + index + "个任务");
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            //3.执行请求：将请求加入到线程池中
            executor.execute(runnable);
//          Future<?> submit = executor.submit(runnable);
        }

        mExecutor = executor;
    }

    /**
     * 定长线程池
     */
    private void newFixedThreadPool() {
        //1.创建定长线程池，核心线程数为3
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
        //2.创建10个任务请求
        for (int i = 0; i < 10; i++) {
            final int index = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.e(TAG, "newFixedThreadPool线程：" + Thread.currentThread().getName() + " 正在执行" + index + "个任务");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            //3.线程池执行任务
            newFixedThreadPool.execute(runnable);
        }

        mExecutor = newFixedThreadPool;
    }

    /**
     * 可缓存线程池
     */
    private void newCachedThreadPool() {
        //1.创建可缓存线程池
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        //2.创建10个任务请求
        for (int i = 0; i < 10; i++) {
            try {
                //睡1秒，即每隔一秒执行一次
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final int index = i;
            //3.线程池执行任务
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.e(TAG, "newCachedThreadPool：" + Thread.currentThread().getName() + " 正在执行第" + index + "个任务");
                        //执行任务时睡 index * 500毫秒
                        Thread.sleep(index * 500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        mExecutor = newCachedThreadPool;
    }

    /**
     * 单线程化线程池
     */
    private void newSingleThreadExecutor() {
        //1.创建单线程化线程池
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        //2.创建10个任务请求
        for (int i = 0; i < 10; i++) {
            final int index = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.e(TAG, "newSingleThreadExecutor线程：" + Thread.currentThread().getName() + " 正在执行" + index + "个任务");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            //3.线程池执行任务
            newSingleThreadExecutor.execute(runnable);
        }

        mExecutor = newSingleThreadExecutor;
    }

    /**
     * 定时线程池
     */
    private void newScheduledThreadPool() {
        //1.创建定时线程池，核心线程数为3，非核心线程数无限制
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        //2.创建任务
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "newScheduledThreadPool线程：" + Thread.currentThread().getName() + " 正在执行任务");
            }
        };
        //3.定时执行任务:延迟2秒执行，每秒执行一次任务
        scheduledExecutorService.scheduleAtFixedRate(runnable, 2, 1, TimeUnit.SECONDS);
        //scheduledExecutorService.schedule(runnable, 2, TimeUnit.SECONDS);延迟2秒执行

        mExecutor = scheduledExecutorService;
    }


    /**
     * 线程池管理类使用
     */
    private void threadPoolManager() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(2000);
                    Log.e(TAG, "线程池管理类：" + Thread.currentThread().getName() + " 正在执行任务");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ThreadPoolManager.getInstance().execute(runnable);//执行请求
        ThreadPoolManager.getInstance().remove(runnable);//移除请求
    }

}
