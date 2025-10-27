package org.wrtca.api;

import java.util.concurrent.CountDownLatch;

/* loaded from: classes9.dex */
public final /* synthetic */ class m implements Runnable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ CountDownLatch f28121c;

    @Override // java.lang.Runnable
    public final void run() {
        this.f28121c.countDown();
    }
}
