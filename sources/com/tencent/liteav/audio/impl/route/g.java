package com.tencent.liteav.audio.impl.route;

import android.content.Intent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    private final List<a> f18300a = new ArrayList();

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public long f18301a;

        /* renamed from: b, reason: collision with root package name */
        public f f18302b;
    }

    public synchronized a a(long j2) {
        a next;
        Iterator<a> it = this.f18300a.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (next.f18301a == j2) {
                break;
            }
        }
        return next;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0018, code lost:
    
        r3.f18300a.remove(r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void b(long r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            r0 = 0
        L2:
            java.util.List<com.tencent.liteav.audio.impl.route.g$a> r1 = r3.f18300a     // Catch: java.lang.Throwable -> L23
            int r1 = r1.size()     // Catch: java.lang.Throwable -> L23
            if (r0 >= r1) goto L21
            java.util.List<com.tencent.liteav.audio.impl.route.g$a> r1 = r3.f18300a     // Catch: java.lang.Throwable -> L23
            java.lang.Object r1 = r1.get(r0)     // Catch: java.lang.Throwable -> L23
            com.tencent.liteav.audio.impl.route.g$a r1 = (com.tencent.liteav.audio.impl.route.g.a) r1     // Catch: java.lang.Throwable -> L23
            long r1 = r1.f18301a     // Catch: java.lang.Throwable -> L23
            int r1 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r1 != 0) goto L1e
            java.util.List<com.tencent.liteav.audio.impl.route.g$a> r4 = r3.f18300a     // Catch: java.lang.Throwable -> L23
            r4.remove(r0)     // Catch: java.lang.Throwable -> L23
            goto L21
        L1e:
            int r0 = r0 + 1
            goto L2
        L21:
            monitor-exit(r3)
            return
        L23:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.audio.impl.route.g.b(long):void");
    }

    public synchronized void a(f fVar, long j2) {
        if (a(j2) != null) {
            return;
        }
        a aVar = new a();
        aVar.f18301a = j2;
        aVar.f18302b = fVar;
        this.f18300a.add(aVar);
    }

    public void a(Intent intent) {
        ArrayList arrayList;
        synchronized (this) {
            arrayList = new ArrayList(this.f18300a);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((a) it.next()).f18302b.a(intent);
        }
    }
}
