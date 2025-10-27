package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.GoogleApiManager;

/* loaded from: classes3.dex */
final class zabk implements Runnable {
    private final /* synthetic */ GoogleApiManager.zaa zaiq;

    public zabk(GoogleApiManager.zaa zaaVar) {
        this.zaiq = zaaVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zaiq.zabf();
    }
}
