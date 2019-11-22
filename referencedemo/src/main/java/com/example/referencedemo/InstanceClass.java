package com.example.referencedemo;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/21 14:33
 * @类描述 ${TODO}单例造成的内存泄漏
 */
public class InstanceClass {
    private String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private static InstanceClass mInstanceClass;
    private final WeakReference<Context> mWeakReference;

    public InstanceClass(Context context) {
        mWeakReference = new WeakReference<>(context);
        this.mContext = mWeakReference.get();
    }

    //传入的Context如果外部类(比如Activity)需要销毁时，InstanceClass仍然持有Context，导致Activity无法销毁回收
    public static InstanceClass getInstance(Context context) {
        if (mInstanceClass == null) {
            mInstanceClass = new InstanceClass(context);
        }
        return mInstanceClass;
    }
}
