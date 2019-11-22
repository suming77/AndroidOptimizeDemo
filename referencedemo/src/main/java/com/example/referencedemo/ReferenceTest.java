package com.example.referencedemo;

import java.lang.ref.WeakReference;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/22 9:51
 * @类描述 ${TODO}弱引用
 */
public class ReferenceTest {

    public static void main(String[] args) {
        String s = new String("value");
        WeakReference<String> weakReference = new WeakReference<>(s);
        s = null;
        System.out.println("弱引用：WeakReference == GC回收前：" + weakReference.get());

        //系统GC垃圾回收
        System.gc();

        System.out.println("弱引用：WeakReference == GC回收后：" + weakReference.get());
    }
}
