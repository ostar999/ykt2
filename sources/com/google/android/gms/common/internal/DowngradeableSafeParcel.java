package com.google.android.gms.common.internal;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

@KeepForSdk
/* loaded from: classes3.dex */
public abstract class DowngradeableSafeParcel extends AbstractSafeParcelable implements ReflectedParcelable {
    private static final Object zzdc = new Object();
    private static ClassLoader zzdd;
    private static Integer zzde;
    private boolean zzdf = false;

    @KeepForSdk
    public static boolean canUnparcelSafely(String str) {
        zzp();
        return true;
    }

    @KeepForSdk
    public static Integer getUnparcelClientVersion() {
        synchronized (zzdc) {
        }
        return null;
    }

    private static ClassLoader zzp() {
        synchronized (zzdc) {
        }
        return null;
    }

    @KeepForSdk
    public abstract boolean prepareForClientVersion(int i2);

    @KeepForSdk
    public void setShouldDowngrade(boolean z2) {
        this.zzdf = z2;
    }

    @KeepForSdk
    public boolean shouldDowngrade() {
        return this.zzdf;
    }
}
