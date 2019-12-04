package com.example.androidoptimizedemo.ThreadStop;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/25 17:48
 * @类描述 ${TODO}线程停止
 */
public class ThreadStopActivity /*extends AppCompatActivity */ {
    private final String TAG = this.getClass().getSimpleName();
    private Thread mThread;
/*
    private int number;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyThread runnable = new MyThread();
        mThread = new Thread(runnable);
        mThread.start();

        for (int i = 0; i < 4; i++) {
            new Thread(runnable).start();
        }
        mThread.stop();
//        runnable.stopThread();
        mThread.isInterrupted();
        Thread.interrupted();
    }*/
/*
    public static void main(String[] args) {
        //自定义线程
        MyThread runnable = new MyThread();
        Thread thread = new Thread(runnable, "子线程");
        thread.start();
        thread.interrupt();
        try {
            //睡5秒
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //停止线程
        runnable.stopThread(true);
    }*/

    public static void main(String[] args) {
  /*      try {
            //自定义线程
            MyThread3 thread = new MyThread3();
            thread.start();
            //睡0.01秒
            //中断线程(不是真正停止线程，而是改变线程中断状态)
//            Thread.sleep(200);
            thread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("main 异常信息 == "+e.getMessage());
        }*/

        MyThread runnable = new MyThread();
        Thread mThread = new Thread(runnable,"111111");
        mThread.start();

        for (int i = 0; i < 4; i++) {
            new Thread(runnable).start();
        }
//        mThread.stop();

    }
/*
    private volatile boolean isStop;

    private class MyThread implements Runnable {
        @Override
        public void run() {
            Log.e(TAG, Thread.currentThread().getName() + " == 进入");
            while (!isStop) {
                synchronized ("") {

                    number++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    number--;
                    Log.e(TAG, Thread.currentThread().getName() + " == " + number);
                }
            }
        }

        public void stopThread() {
            isStop = true;
        }
    }
*/
}
