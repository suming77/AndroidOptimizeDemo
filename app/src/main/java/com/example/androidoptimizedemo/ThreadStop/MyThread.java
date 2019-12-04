package com.example.androidoptimizedemo.ThreadStop;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/25 17:48
 * @类描述 ${TODO}线程停止
 */
public class MyThread implements Runnable {
    private int number;
    private volatile boolean isStop;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " == 进入");
        while (!isStop) {
            synchronized (MyThread.class) {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " == " + number);
                    number++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 设置线程标识
     * @param isStop true：停止 false：不停止
     */
    public void stopThread(boolean isStop) {
        this.isStop = isStop;
    }
}
