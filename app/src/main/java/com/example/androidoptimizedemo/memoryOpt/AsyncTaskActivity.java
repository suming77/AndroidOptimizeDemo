package com.example.androidoptimizedemo.memoryOpt;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.androidoptimizedemo.R;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/4 20:54
 * @类描述 ${TODO}AsyncTask造成的内存泄漏以及解决方法
 */
public class AsyncTaskActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private AsyncTask<Void, Void, Void> mAsyncTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nothing);

        //AsyncTask执行任务
        mAsyncTask = new AsyncTask<Void, Void, Void>() {
            //开始之前的准备工作
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //相当于子线程，耗时操作
            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }

            //主线程显示进度
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            //相当于主线程，获取数据更新UI
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        };
        //在AsyncTask里面处理耗时操作时，AsyncTask还没操作完就将MainActivity退出，
        // 但是此时AsyncTask已然持有MainActivity的引用，导致MainActivity无法被回收处理。


        //执行异步线程任务
        mAsyncTask.execute();
    }

    @Override
    protected void onDestroy() {
        //解决办法：在使用AsyncTask的时候，在MainActivity销毁时也取消AsyncTask相关的任务AsyncTask.cancel()
        mAsyncTask.cancel(true);
        super.onDestroy();
    }
}
