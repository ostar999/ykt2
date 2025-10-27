package com.alipay.apmobilesecuritysdk.f;

import android.os.Process;

/* loaded from: classes2.dex */
final class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ b f3047a;

    public c(b bVar) {
        this.f3047a = bVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            Process.setThreadPriority(0);
            while (!this.f3047a.f3046c.isEmpty()) {
                Runnable runnable = (Runnable) this.f3047a.f3046c.get(0);
                this.f3047a.f3046c.remove(0);
                if (runnable != null) {
                    runnable.run();
                }
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            b.b(this.f3047a);
            throw th;
        }
        b.b(this.f3047a);
    }
}
