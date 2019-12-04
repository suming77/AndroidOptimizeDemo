package com.example.androidoptimizedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.androidoptimizedemo.ImageOpt.GlidePicassoActivity;
import com.example.androidoptimizedemo.ImageOpt.ImageOptActivity;
import com.example.androidoptimizedemo.ImageOpt.ThreadPoolActivity;
import com.example.androidoptimizedemo.ThreadStop.ThreadStopActivity;
import com.example.androidoptimizedemo.layoutOpt.ConstraintLayoutActivity;
import com.example.androidoptimizedemo.layoutOpt.IncludeActivity;
import com.example.androidoptimizedemo.memoryOpt.AsyncTaskActivity;
import com.example.androidoptimizedemo.memoryOpt.HandlerActivity;
import com.example.androidoptimizedemo.memoryOpt.ListenerActivity;
import com.example.androidoptimizedemo.memoryOpt.NotStaticActivity;
import com.example.androidoptimizedemo.memoryOpt.ResActivity;
import com.example.androidoptimizedemo.memoryOpt.SingleInstanceClass;
import com.example.androidoptimizedemo.memoryOpt.ThreadActivity;

/**
 * Android性能优化实例
 * 我的博客：https://blog.csdn.net/m0_37796683
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_not_static).setOnClickListener(this);
        findViewById(R.id.btn_async_task).setOnClickListener(this);
        findViewById(R.id.btn_thread).setOnClickListener(this);
        findViewById(R.id.btn_handler).setOnClickListener(this);
        findViewById(R.id.btn_single_instance).setOnClickListener(this);
        findViewById(R.id.btn_listener).setOnClickListener(this);
        findViewById(R.id.btn_res).setOnClickListener(this);
        findViewById(R.id.btn_include).setOnClickListener(this);
        findViewById(R.id.btn_constraint_layout).setOnClickListener(this);
        findViewById(R.id.btn_image_opt).setOnClickListener(this);

        findViewById(R.id.btn_glide_picasso).setOnClickListener(this);
        findViewById(R.id.btn_thread_pool).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Class<?> cls = null;
        switch (v.getId()) {
            case R.id.btn_not_static://非静态
                cls = NotStaticActivity.class;
                break;
            case R.id.btn_async_task://AsyncTask
                cls = AsyncTaskActivity.class;
                break;
            case R.id.btn_thread://线程类
                cls = ThreadActivity.class;
                break;
            case R.id.btn_handler://Handler
                cls = HandlerActivity.class;
                break;
            case R.id.btn_single_instance://static修饰的成员变量：单例
                SingleInstanceClass.getInstance(this);
                break;
            case R.id.btn_listener://监听
                cls = ListenerActivity.class;
                break;
            case R.id.btn_res://相关资源
                cls = ResActivity.class;
                break;
            case R.id.btn_include://布局优化-<include>标签 <merge>标签 <Space>标签 <ViewStub>标签
                cls = IncludeActivity.class;
                break;
            case R.id.btn_constraint_layout://ConstraintLayout布局
                cls = ConstraintLayoutActivity.class;
                break;
            case R.id.btn_image_opt://图片优化相关
                cls = ImageOptActivity.class;
                break;
            case R.id.btn_glide_picasso://glide和picasso库使用
                cls = GlidePicassoActivity.class;
                break;
            case R.id.btn_thread_pool://线程池
                cls = ThreadPoolActivity.class;
                break;
            case R.id.btn_thread_stop://线程停止的几种方式
                cls = ThreadStopActivity.class;
                break;
            default:
                break;
        }

        if (cls != null) {
            startActivity(new Intent(this, cls));
        } else {
            Toast.makeText(this, "无展示效果，请看源码", Toast.LENGTH_SHORT).show();
        }
    }
}
