package com.tencent.liteav.videobase.utils;

import java.util.LinkedList;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private final LinkedList<Runnable> f20117a = new LinkedList<>();

    public void a(Runnable runnable) {
        synchronized (this.f20117a) {
            this.f20117a.add(runnable);
        }
    }

    public void a() {
        LinkedList linkedList;
        synchronized (this.f20117a) {
            if (this.f20117a.isEmpty()) {
                linkedList = null;
            } else {
                linkedList = new LinkedList(this.f20117a);
                this.f20117a.clear();
            }
        }
        while (linkedList != null && !linkedList.isEmpty()) {
            ((Runnable) linkedList.removeFirst()).run();
        }
    }
}
