package com.google.android.gms.common.api.internal;

/* loaded from: classes3.dex */
final class zabl implements Runnable {
    private final /* synthetic */ zabm zajd;

    public zabl(zabm zabmVar) {
        this.zajd = zabmVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zajd.zaiq.zais.disconnect();
    }
}
