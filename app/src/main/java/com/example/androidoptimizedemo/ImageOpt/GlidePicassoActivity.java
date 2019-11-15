package com.example.androidoptimizedemo.ImageOpt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidoptimizedemo.R;
import com.squareup.picasso.Picasso;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/12 12:12
 * @类描述 ${TODO}glide和picasso的简单使用
 */
public class GlidePicassoActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private ImageView mIv_glide;
    private ImageView mIv_glide2;
    private ImageView mIv_picasso;
    private ImageView mIv_picasso2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_picasso);

        findViewById(R.id.btn_glide).setOnClickListener(this);
        mIv_glide = findViewById(R.id.iv_glide);
        mIv_glide2 = findViewById(R.id.iv_glide2);
        findViewById(R.id.btn_picasso).setOnClickListener(this);
        mIv_picasso = findViewById(R.id.iv_picasso);
        mIv_picasso2 = findViewById(R.id.iv_picasso2);
        findViewById(R.id.btn_back).setOnClickListener(this);

        //Glide大部分的设置选项都可以通applyDefaultRequestOptions添加RequestOptions到应用程序中
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.ic_launcher)//加载成功前的占位图
                //错误站位图
                .error(R.mipmap.woman)
                //指定图片尺寸
                .override(400, 400)
                //执行缩放类型，fitCenter()表示等比例缩放，宽或者高等于ImageView的宽或者高；
                // centerCrop()：等比例缩放图片，知道宽高大于ImageView宽高，然后截取中间的显示，circleCrop()：缩放类型为圆形
                .fitCenter()
                //缓存策略：DiskCacheStrategy.ALL：缓存所以版本的图片；DiskCacheStrategy.NONE：跳过磁盘缓存；
                // DiskCacheStrategy.DATA：只缓存原来分辨率的图片；DiskCacheStrategy.RESOURCE：只缓存最终的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //跳过内存缓存
                .skipMemoryCache(true);

        Glide.with(GlidePicassoActivity.this)//传入上下文
                .applyDefaultRequestOptions(options)
                //加载图片地址
                .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
                //设置图片的控件
                .into(mIv_glide);
        Glide.with(GlidePicassoActivity.this)
                .load(R.drawable.timg)
                .into(mIv_glide2);

        Picasso.with(GlidePicassoActivity.this)//传入上下文
                //加载图片地址
                .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
                //设置图片的控件
                .into(mIv_picasso);
        Picasso.with(GlidePicassoActivity.this)
                .load(R.drawable.timg)
                .into(mIv_picasso2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back://返回
                finish();
                break;
        }
    }

}
