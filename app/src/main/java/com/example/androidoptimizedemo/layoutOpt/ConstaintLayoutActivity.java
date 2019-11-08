package com.example.androidoptimizedemo.layoutOpt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.androidoptimizedemo.R;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/6 19:40
 * @类描述 ${TODO}布局优化-<ConstraintLayout>的使用
 */
public class ConstaintLayoutActivity extends AppCompatActivity {
    private static final String TAG = ConstaintLayoutActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constaint);

        //将背景主题设置为null或者设置全透明
//        getWindow().setBackgroundDrawable(null);
//        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
