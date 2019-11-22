package com.example.referencedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/21 11:17
 * @类描述 ${TODO}四种引用的使用：强引用、软引用、弱引用、虚引用
 * 我的博客：https://blog.csdn.net/m0_37796683
 */
public class MainActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        phantomReference();
        weakReference(this);
    }


    /**
     * 强引用:当我们使用一个new关键字去创建对象的时候，这个对象的引用就是强引用。
     * 特点：不会被回收，宁愿抛出OOM也不会回收强引用指向的对象
     */
    public void strongReference() {
        //str表示强引用，指向new String()这个对象
        String str = new String();
    }

    /**
     * 软引用：软引用是除了强引用外，最强的引用类型。如果系统内存足够是不会回收软引用指向的对象的，如果系统内存不足那么就会回收。
     * 特点：当内存不足时会回收
     */
    public void softReference() {
        ImageView imageView = findViewById(R.id.iv_soft_reference);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        SoftReference<Bitmap> softReference = new SoftReference<>(bitmap);
        if (true){
            //将软引用中的对象设置为null，否则既持有强引用也持有软引用是无法回收的
            bitmap = null;
            System.gc();
        }
        if (softReference.get() != null) {
            imageView.setImageBitmap(softReference.get());
        } else {

        }
    }

    /**
     * 弱引用:弱引用是弱于软引用的引用类型，与软引用类似，不同的是弱引用不能阻止垃圾回收。
     * 特点：在垃圾回收机制运行时，如果一个对象的引用是弱引用的话，不管内存空间是否足够，对象都会被回收。
     */
    public void weakReference(Context context) {
        String str = new String("value");
        WeakReference<String> weakReference = new WeakReference<>(str);
        str = null;
        Log.e(TAG, "弱引用：WeakReference == GC回收前：" + weakReference.get());

        //系统GC垃圾回收
        System.gc();
        Log.e(TAG, "弱引用：WeakReference == GC回收后：" + weakReference.get());

        //这里打印的值都是value,因为Java和Android中的垃圾收集器是不一样的，Android不是用标准的JVM,而是使用Dalvik VM。
//        InstanceClass instanceClass = InstanceClass.getInstance(this);
    }

    /**
     * 虚引用:虚引用是最弱的引用，一个持有虚引用的对象和没有引用几乎是一样的，随时都可能被垃圾回收器回收。
     * 通过虚引用的get()方法获取到的引用都会失败(为null)，虚引用必须和引用队列ReferenceQueue一起使用。
     * <p>
     * ReferenceQueue引用队列作用在于跟踪垃圾回收过程。当回收对象时，如果发现还有虚引用，就会在回收后销毁这个对象，
     * 并且将虚引用指向的对象加入到引用队列。只能通过虚引用是否被加入到ReferenceQueue来判断虚引用是否为GC回收。
     */
    public void phantomReference() {
        //引用队列
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        //虚引用
        PhantomReference<Object> phantomReference = new PhantomReference<Object>(new Object(), queue);

        Log.e(TAG, "虚引用：PhantomReference == " + phantomReference.get());

        //系统垃圾回收
        System.gc();
        System.runFinalization();

        Log.e(TAG, "虚引用：PhantomReference == 是否被回收：" + (queue.poll() == phantomReference) + " | 队列中的PhantomReference == " + queue.poll());
    }

}
