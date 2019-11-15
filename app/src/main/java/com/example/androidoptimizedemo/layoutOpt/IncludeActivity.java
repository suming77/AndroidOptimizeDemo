package com.example.androidoptimizedemo.layoutOpt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewStub;

import com.example.androidoptimizedemo.R;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/6 19:40
 * @类描述 ${TODO}布局优化-<include>标签 <merge>标签 <Space>标签 <ViewStub>标签
 */
public class IncludeActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_include);

        final ViewStub viewStub = findViewById(R.id.view_stub);
        //延迟2秒后显示viewStub布局
        viewStub.postDelayed(new Runnable() {
            @Override
            public void run() {
                //加载显示
                viewStub.inflate();
//                viewStub.setVisibility(View.GONE);
            }
        }, 2000);
    }
}
