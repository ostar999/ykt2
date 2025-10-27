package com.google.android.gms.common.api.internal;

/* loaded from: classes3.dex */
final class zat implements Runnable {
    private final /* synthetic */ zaq zaet;

    public zat(zaq zaqVar) {
        this.zaet = zaqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zaet.zaer.lock();
        try {
            this.zaet.zav();
        } finally {
            this.zaet.zaer.unlock();
        }
    }
}
