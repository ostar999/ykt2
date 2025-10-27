package com.google.android.gms.common.api.internal;

/* loaded from: classes3.dex */
final class zaaj implements Runnable {
    private final /* synthetic */ zaak zafz;

    public zaaj(zaak zaakVar) {
        this.zafz = zaakVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zafz.zaey.cancelAvailabilityErrorNotifications(this.zafz.mContext);
    }
}
