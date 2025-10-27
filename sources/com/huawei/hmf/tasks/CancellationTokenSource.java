package com.huawei.hmf.tasks;

import com.huawei.hmf.tasks.a.c;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class CancellationTokenSource {
    private c impl = new c();

    public void cancel() {
        c cVar = this.impl;
        if (cVar.f7362c) {
            return;
        }
        synchronized (cVar.f7361b) {
            cVar.f7362c = true;
            Iterator<Runnable> it = cVar.f7360a.iterator();
            while (it.hasNext()) {
                it.next().run();
            }
        }
    }

    public CancellationToken getToken() {
        return this.impl;
    }
}
