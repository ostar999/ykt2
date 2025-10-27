package com.meizu.cloud.pushsdk.c.e.a;

import com.meizu.cloud.pushsdk.c.b.a.b;
import com.meizu.cloud.pushsdk.c.e.c;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public class a extends c {

    /* renamed from: n, reason: collision with root package name */
    private static final String f9389n = "a";

    /* renamed from: o, reason: collision with root package name */
    private static ScheduledExecutorService f9390o;

    public a(c.a aVar) {
        super(aVar);
        b.a(this.f9412k);
        c();
    }

    @Override // com.meizu.cloud.pushsdk.c.e.c
    public void a(final com.meizu.cloud.pushsdk.c.c.b bVar, final boolean z2) {
        b.a(new Runnable() { // from class: com.meizu.cloud.pushsdk.c.e.a.a.2
            @Override // java.lang.Runnable
            public void run() {
                a.super.a(bVar, z2);
            }
        });
    }

    public void c() {
        if (f9390o == null && this.f9410i) {
            com.meizu.cloud.pushsdk.c.f.c.b(f9389n, "Session checking has been resumed.", new Object[0]);
            final com.meizu.cloud.pushsdk.c.e.a aVar = this.f9405d;
            ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
            f9390o = scheduledExecutorServiceNewSingleThreadScheduledExecutor;
            Runnable runnable = new Runnable() { // from class: com.meizu.cloud.pushsdk.c.e.a.a.1
                @Override // java.lang.Runnable
                public void run() {
                    aVar.b();
                }
            };
            long j2 = this.f9411j;
            scheduledExecutorServiceNewSingleThreadScheduledExecutor.scheduleAtFixedRate(runnable, j2, j2, this.f9413l);
        }
    }
}
