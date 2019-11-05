package com.example.androidoptimizedemo.memoryOpt;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.androidoptimizedemo.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/4 20:54
 * @类描述 ${TODO}相关资源关闭/注销避免内存泄漏
 */
public class ResActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ResActivity.class.getSimpleName();
    private TextView mTextView;
    private ObjectAnimator mAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res);

        mTextView = findViewById(R.id.tv_animation);
        mTextView.setOnClickListener(this);

        //2.IO流使用完毕后及时关闭
        InputStream stream = null;
        try {
            stream = new FileInputStream("name");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //3.cursor 对于数据库游标Cursor，使用完毕后关闭。
//        Cursor cursor = db.query(SQLiteHelper.TABLE_NAME, COLUMNS, key + " = ?", new String[]{values}, null, null, null);
//        cursor.close();

        //4.Bitmap 使用完毕后回收
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        bitmap.recycle();
        bitmap = null;
        //对于图片资源Bitmap，Andorid分配给图片的资源只有8M，如果1个Bitmap对象占用资源较多时，
        // 当不再使用时应该recycle()回收对象像素所占用的内存，最后赋值为Null。

        //5.集合类
        ArrayList<String> arrayList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            String s = new String();
            arrayList.add(s);
            s = null;
        }

        //虽然释放了对象本身：s=null；但是集合list仍然引用这该对象，导致GC无法回收该对象
        arrayList.clear();
        arrayList = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_animation:
                startAnimation();
                break;
            default:
                break;
        }
    }

    /**
     * 1.动画
     * 动画中有无限循环的动画，动画播放时，Activity会被View所持有，从而导致Activity无法被释放，需要cancel()退出动画
     */
    private void startAnimation() {
        mAnimator = ObjectAnimator.ofFloat(mTextView, "rotationY", 0, 360);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                stopAnimator();
                mTextView.clearAnimation();
                mAnimator.cancel();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 退出动画
     */
    private void stopAnimator() {
        if (mTextView != null) {
            mTextView.clearAnimation();
        }

        if (mAnimator != null) {
            mAnimator.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        mTextView.clearAnimation();
        mAnimator.cancel();
        stopAnimator();
        super.onDestroy();
    }
}
