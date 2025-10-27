package com.google.android.gms.common.util;

import android.os.SystemClock;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
/* loaded from: classes3.dex */
public class DefaultClock implements Clock {
    private static final DefaultClock zzgm = new DefaultClock();

    private DefaultClock() {
    }

    @KeepForSdk
    public static Clock getInstance() {
        return zzgm;
    }

    @Override // com.google.android.gms.common.util.Clock
    public long currentThreadTimeMillis() {
        return SystemClock.currentThreadTimeMillis();
    }

    @Override // com.google.android.gms.common.util.Clock
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override // com.google.android.gms.common.util.Clock
    public long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    @Override // com.google.android.gms.common.util.Clock
    public long nanoTime() {
        return System.nanoTime();
    }
}
