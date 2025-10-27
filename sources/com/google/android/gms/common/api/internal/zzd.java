package com.google.android.gms.common.api.internal;

/* loaded from: classes3.dex */
final class zzd implements Runnable {
    private final /* synthetic */ LifecycleCallback zzbi;
    private final /* synthetic */ String zzbj;
    private final /* synthetic */ zzc zzbl;

    public zzd(zzc zzcVar, LifecycleCallback lifecycleCallback, String str) {
        this.zzbl = zzcVar;
        this.zzbi = lifecycleCallback;
        this.zzbj = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzbl.zzbg > 0) {
            this.zzbi.onCreate(this.zzbl.zzbh != null ? this.zzbl.zzbh.getBundle(this.zzbj) : null);
        }
        if (this.zzbl.zzbg >= 2) {
            this.zzbi.onStart();
        }
        if (this.zzbl.zzbg >= 3) {
            this.zzbi.onResume();
        }
        if (this.zzbl.zzbg >= 4) {
            this.zzbi.onStop();
        }
        if (this.zzbl.zzbg >= 5) {
            this.zzbi.onDestroy();
        }
    }
}
