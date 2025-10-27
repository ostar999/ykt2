package com.xiaomi.push.service;

import com.xiaomi.push.hq;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
final class am implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ List f25581a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ boolean f997a;

    public am(List list, boolean z2) {
        this.f25581a = list;
        this.f997a = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean zB = al.b("www.baidu.com:80");
        Iterator it = this.f25581a.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            zB = zB || al.b((String) it.next());
            if (zB && !this.f997a) {
                break;
            }
        }
        hq.a(zB ? 1 : 2);
    }
}
