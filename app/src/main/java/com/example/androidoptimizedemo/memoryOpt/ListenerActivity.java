package com.example.androidoptimizedemo.memoryOpt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.androidoptimizedemo.R;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/4 20:54
 * @类描述 ${TODO}监听类造成的内存泄漏以及解决方法
 */
public class ListenerActivity extends AppCompatActivity implements ViewTreeObserver.OnWindowFocusChangeListener{
    private final String TAG = this.getClass().getSimpleName();
    private MyReceiver mMyReceiver;
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nothing);

        //1.自定义广播
        mMyReceiver = new MyReceiver();
        //创建过滤器，增加手势参数Action
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //动态注册广播
        registerReceiver(mMyReceiver, filter);


        //2.add监听
        mTextView = new TextView(this);
        //监听执行完回收对象，不用考虑内存泄漏
        //textView.setOnClickListener(null);

        //add监听，放到集合里面，需要考虑内存泄漏
        mTextView.getViewTreeObserver().addOnWindowFocusChangeListener(this);
    }

    //自定义广播接收类
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //对接收到的广播进行处理，intent里面包含数据
        }
    }

    @Override
    protected void onDestroy() {
        //正例
        //对于监听类相关资源需要在Activity销毁时进行注销和回收，否则导致资源不回收造成内存泄漏。
        //解除广播
        unregisterReceiver(mMyReceiver);

        //解除控件监听
        mTextView.getViewTreeObserver().removeOnWindowFocusChangeListener(this);
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //监听View的加载，加载处理计算他的宽高
    }
}
