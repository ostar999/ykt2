package com.huawei.hmf.tasks.a;

import com.huawei.hmf.tasks.CancellationToken;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class c extends CancellationToken {

    /* renamed from: a, reason: collision with root package name */
    public final List<Runnable> f7360a = new ArrayList();

    /* renamed from: b, reason: collision with root package name */
    public final Object f7361b = new Object();

    /* renamed from: c, reason: collision with root package name */
    public boolean f7362c = false;

    @Override // com.huawei.hmf.tasks.CancellationToken
    public final boolean isCancellationRequested() {
        return this.f7362c;
    }

    @Override // com.huawei.hmf.tasks.CancellationToken
    public final CancellationToken register(Runnable runnable) {
        synchronized (this.f7361b) {
            if (this.f7362c) {
                runnable.run();
            } else {
                this.f7360a.add(runnable);
            }
        }
        return this;
    }
}
