package com.example.androidoptimizedemo.memoryOpt;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.androidoptimizedemo.R;

import java.lang.ref.WeakReference;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/4 20:54
 * @类描述 ${TODO}Handler造成的内存泄漏以及解决方法
 */
public class HandlerActivity extends AppCompatActivity {
    private static final String TAG = HandlerActivity.class.getSimpleName();
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nothing);

        //反例
        //创建Handler
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //接收信息
                switch (msg.what) {
                    case 1:
                        Log.e(TAG, "Handler==" + msg.obj);
                        break;
                    default:
                        break;
                }
            }
        };

        //创建线程模拟发送数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                //封装信息数据
                Message message = Message.obtain();
                message.what = 1;
                message.obj = "Handler使用";
                //Handler发送信息
                mHandler.sendMessage(message);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        //正例
        //解决方法1、当外部类结束生命周期时，清空Handler内消息队列；
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    //正例
    //解决方法2、将匿名内部类改为静态内部类，并对上下文或者Activity使用弱引用。
    //静态static可以解决内存泄漏问题，使用弱引用也可以解决内存泄漏，但是需要等到Handler中的任务执行完才释放Activity，没有直接static释放的快。
    private static class MyHandler extends Handler {
        //定义弱引用实例
        WeakReference<Activity> mWeakReference;

        public MyHandler(HandlerActivity activity) {
            //构造方法中将Activity使用弱引用
            mWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mWeakReference.get() != null) {
                //UI更新
            }
        }
    }
}
