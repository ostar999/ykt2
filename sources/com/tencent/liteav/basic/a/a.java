package com.tencent.liteav.basic.a;

import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private final LinkedList<Runnable> f18327a = new LinkedList<>();

    public void a(Runnable runnable) {
        synchronized (this.f18327a) {
            this.f18327a.add(runnable);
        }
    }

    public void a() {
        LinkedList linkedList;
        synchronized (this.f18327a) {
            if (this.f18327a.isEmpty()) {
                linkedList = null;
            } else {
                linkedList = new LinkedList(this.f18327a);
                this.f18327a.clear();
            }
        }
        if (linkedList != null) {
            Iterator it = linkedList.iterator();
            while (it.hasNext()) {
                ((Runnable) it.next()).run();
            }
        }
    }
}
