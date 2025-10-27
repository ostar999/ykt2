package com.google.android.gms.common.internal;

import androidx.annotation.NonNull;

/* loaded from: classes3.dex */
public final class zzh {

    @NonNull
    private final String mPackageName;
    private final int zzdt = 129;

    @NonNull
    private final String zzej;
    private final boolean zzek;

    public zzh(@NonNull String str, @NonNull String str2, boolean z2, int i2) {
        this.mPackageName = str;
        this.zzej = str2;
        this.zzek = z2;
    }

    @NonNull
    public final String getPackageName() {
        return this.mPackageName;
    }

    public final int zzq() {
        return this.zzdt;
    }

    @NonNull
    public final String zzt() {
        return this.zzej;
    }
}
