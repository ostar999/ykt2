package com.google.android.gms.common.util.concurrent;

import android.os.Process;

/* loaded from: classes3.dex */
final class zza implements Runnable {
    private final int priority;
    private final Runnable zzhu;

    public zza(Runnable runnable, int i2) {
        this.zzhu = runnable;
        this.priority = i2;
    }

    @Override // java.lang.Runnable
    public final void run() throws SecurityException, IllegalArgumentException {
        Process.setThreadPriority(this.priority);
        this.zzhu.run();
    }
}
