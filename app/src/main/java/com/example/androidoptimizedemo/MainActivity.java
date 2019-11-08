package com.example.androidoptimizedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.androidoptimizedemo.layoutOpt.ConstaintLayoutActivity;
import com.example.androidoptimizedemo.layoutOpt.ImageOptActivity;
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
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn_get_sync).setOnClickListener(this);
        findViewById(R.id.btn_get_sync).setOnClickListener(this);
        findViewById(R.id.btn_get_async).setOnClickListener(this);
        findViewById(R.id.btn_post_sync_str).setOnClickListener(this);
        findViewById(R.id.btn_post_async_str).setOnClickListener(this);
        findViewById(R.id.btn_post_key_value).setOnClickListener(this);
        findViewById(R.id.btn_post_key_value_more).setOnClickListener(this);
        findViewById(R.id.btn_post_file).setOnClickListener(this);
        findViewById(R.id.btn_post_form).setOnClickListener(this);
        findViewById(R.id.btn_post_streaming).setOnClickListener(this);
        findViewById(R.id.btn_post_multipart).setOnClickListener(this);
        findViewById(R.id.btn_header_set_read).setOnClickListener(this);
        findViewById(R.id.btn_timeout).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Class<?> cls = null;
        switch (v.getId()) {
            case R.id.btn_get_sync:
                cls = NotStaticActivity.class;
                break;
            case R.id.btn_get_async:
                cls = AsyncTaskActivity.class;
                break;
            case R.id.btn_post_sync_str:
                cls = ThreadActivity.class;
                break;
            case R.id.btn_post_async_str:
                cls = HandlerActivity.class;
                break;
            case R.id.btn_post_key_value:
                cls = SingleInstanceClass.class;
                break;
            case R.id.btn_post_key_value_more:
                cls = ListenerActivity.class;
                break;
            case R.id.btn_post_file:
                cls = ResActivity.class;
                break;
            case R.id.btn_post_form:
                cls = IncludeActivity.class;
                break;
            case R.id.btn_post_streaming:
                cls = ConstaintLayoutActivity.class;
                break;
            case R.id.btn_post_multipart:
                cls = ImageOptActivity.class;
                break;
            case R.id.btn_header_set_read:
                break;
            case R.id.btn_timeout:
                break;
            default:
                break;
        }
        startActivity(new Intent(this, cls));
    }
}
