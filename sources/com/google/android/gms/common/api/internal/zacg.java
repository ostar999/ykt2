package com.google.android.gms.common.api.internal;

/* loaded from: classes3.dex */
final class zacg implements Runnable {
    private final /* synthetic */ com.google.android.gms.signin.internal.zak zagu;
    private final /* synthetic */ zace zakl;

    public zacg(zace zaceVar, com.google.android.gms.signin.internal.zak zakVar) {
        this.zakl = zaceVar;
        this.zagu = zakVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zakl.zac(this.zagu);
    }
}
