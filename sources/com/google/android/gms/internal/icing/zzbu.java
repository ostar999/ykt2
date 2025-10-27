package com.google.android.gms.internal.icing;

import android.content.Context;
import android.net.Uri;
import javax.annotation.Nullable;

/* loaded from: classes3.dex */
public final class zzbu {
    final String zzdk;
    final Uri zzdl;
    final String zzdm;
    final String zzdn;
    final boolean zzdo;
    final boolean zzdp;
    final boolean zzdq;
    final boolean zzdr;

    @Nullable
    final zzby<Context, Boolean> zzds;

    public zzbu(Uri uri) {
        this(null, uri, "", "", false, false, false, false, null);
    }

    public final zzbq<Boolean> zza(String str, boolean z2) {
        return zzbq.zza(this, str, z2);
    }

    private zzbu(String str, Uri uri, String str2, String str3, boolean z2, boolean z3, boolean z4, boolean z5, @Nullable zzby<Context, Boolean> zzbyVar) {
        this.zzdk = null;
        this.zzdl = uri;
        this.zzdm = str2;
        this.zzdn = str3;
        this.zzdo = false;
        this.zzdp = false;
        this.zzdq = false;
        this.zzdr = false;
        this.zzds = null;
    }
}
