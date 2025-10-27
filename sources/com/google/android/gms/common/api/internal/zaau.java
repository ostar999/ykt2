package com.google.android.gms.common.api.internal;

import androidx.annotation.WorkerThread;

/* loaded from: classes3.dex */
abstract class zaau implements Runnable {
    private final /* synthetic */ zaak zafz;

    private zaau(zaak zaakVar) {
        this.zafz = zaakVar;
    }

    @Override // java.lang.Runnable
    @WorkerThread
    public void run() {
        this.zafz.zaer.lock();
        try {
            if (Thread.interrupted()) {
                return;
            }
            zaal();
        } catch (RuntimeException e2) {
            this.zafz.zafv.zab(e2);
        } finally {
            this.zafz.zaer.unlock();
        }
    }

    @WorkerThread
    public abstract void zaal();

    public /* synthetic */ zaau(zaak zaakVar, zaaj zaajVar) {
        this(zaakVar);
    }
}
