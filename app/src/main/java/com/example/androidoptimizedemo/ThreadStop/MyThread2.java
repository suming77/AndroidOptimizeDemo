package com.example.androidoptimizedemo.ThreadStop;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/25 17:48
 * @类描述 ${TODO}线程停止
 */
public class MyThread2 extends Thread {
    @Override
    public void run() {
        super.run();
        try {
            for (int i = 0; i < 5000; i++) {
                if (this.isInterrupted()) {//获取线程的中断状态
                    System.out.println("isInterrupted():" + this.isInterrupted() + " ========线程已结束，退出线程=========");
//                break;
//                return;

//                    throw new InterruptedException("抛出异常，线程停止");
                }
                System.out.println(Thread.currentThread().getName() + "：isInterrupted()：" + this.isInterrupted() + " == " + i);
            }
//            Thread.sleep(200);
            System.out.println("=== for下面的语句，线程并没有结束 ===");

        } catch (Exception e) {
            System.out.println("=== Thread 异常信息 == "+e.getMessage()+" === isInterrupted()："+this.isInterrupted());
            e.printStackTrace();
        }
    }
}
