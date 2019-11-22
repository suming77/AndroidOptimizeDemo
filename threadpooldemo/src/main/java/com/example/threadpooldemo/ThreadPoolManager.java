package com.example.threadpooldemo;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理工具类
 * 我的博客：https://blog.csdn.net/m0_37796683
 */
public class ThreadPoolManager {
    private int corePoolSize;//核心线程池的数量，同时能够执行的线程数量
    private int maximumPoolSize = 100;//最大线程池数量，表示当缓冲队列满的时候能继续容纳的等待任务的数量
    private long keepAliveTime = 30 * 60;//空闲线程存活时间30分钟
    private TimeUnit unit = TimeUnit.SECONDS;

    private ThreadPoolExecutor executor;//线程池实例
    private static ThreadPoolManager mInstance;//线程池管理类

    public static ThreadPoolManager getInstance() {
        if (mInstance == null) {//单例
            synchronized (ThreadPoolManager.class) {//加锁
                if (mInstance == null) {
                    mInstance = new ThreadPoolManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 私有化构造方法
     */
    private ThreadPoolManager() {
        /**
         * 给corePoolSize赋值：当前设备可用处理器核心数*2 + 1,能够让cpu的效率得到最大程度执行（有研究论证的）
         */
        corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        executor = new ThreadPoolExecutor(
                corePoolSize, //当某个核心任务执行完毕，会依次从缓冲队列中取出等待任务
                maximumPoolSize, //5,先corePoolSize,然后new LinkedBlockingQueue<Runnable>(),然后maximumPoolSize,但是它的数量是包含了corePoolSize的
                keepAliveTime, //表示的是maximumPoolSize当中等待任务的存活时间
                unit,
                new LinkedBlockingQueue<Runnable>(), //缓冲队列，用于存放等待任务，Linked的先进先出
                Executors.defaultThreadFactory(), //创建线程的工厂
                new ThreadPoolExecutor.AbortPolicy() //用来对超出maximumPoolSize的任务的处理策略
        );
    }

    /**
     * 执行任务
     */
    public void execute(Runnable runnable) {
        if (runnable == null) return;
        executor.execute(runnable);
    }

    /**
     * 从线程池中移除任务
     */
    public void remove(Runnable runnable) {
        if (runnable == null) return;
        executor.remove(runnable);
    }
}
