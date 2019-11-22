package com.example.androidoptimizedemo.memoryOpt;

import android.content.Context;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/4 20:54
 * @类描述 ${TODO}static修饰的成员变量造成内存泄漏问题以及解决方法
 * 经典例子：单例模式持有context
 */
public class SingleInstanceClass {

    private Context mContext;
    private static SingleInstanceClass sInstanceClass;

    //反例
    //构造方法传入Activity的引用
    public SingleInstanceClass(Context context) {
        mContext = context;
    }

    //正例
    //解决办法：1、保证Context的生命周期与应用的生命周期一致
//    public SingleInstanceClass(Context context) {
//        mContext = context.getApplicationContext();
//    }

    //解决办法：2、使用弱引用代替强引用持有实例
//    public SingleInstanceClass(Context context) {
//        //弱引用
//        WeakReference<Context> weakReference = new WeakReference<>(context);
//        mContext = weakReference.get();
//    }

    //单例方式获取实例
    public static SingleInstanceClass getInstance(Context context) {
        if (sInstanceClass == null) {
            sInstanceClass = new SingleInstanceClass(context);
        }
        return sInstanceClass;
    }


    /**
     * 静态方法的使用
     * 创建的对象建议不要全局化，全局话变量必须加上static,全局化的变量或者对象会造成内存泄漏
     *
     * @param str
     * @return
     */
    public static String getStaticMember(String str) {
        return str;
    }
}
