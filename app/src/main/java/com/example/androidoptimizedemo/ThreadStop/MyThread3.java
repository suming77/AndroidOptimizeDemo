package com.example.androidoptimizedemo.ThreadStop;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/25 17:48
 * @类描述 ${TODO}线程停止
 */
public class MyThread3 extends Thread {
    @Override
    public void run() {
        super.run();
        try {
            System.out.println("=== 线程开始 ===");

            Thread.sleep(2000);

            System.out.println("=== 线程结束 ===");
        } catch (Exception e) {
            System.out.println("=== Thread 异常信息 == "+e.getMessage()+" === isInterrupted()："+this.isInterrupted());
            e.printStackTrace();
        }
    }
}
