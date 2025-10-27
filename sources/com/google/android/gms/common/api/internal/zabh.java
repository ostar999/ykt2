package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.BackgroundDetector;

/* loaded from: classes3.dex */
final class zabh implements BackgroundDetector.BackgroundStateChangeListener {
    private final /* synthetic */ GoogleApiManager zaia;

    public zabh(GoogleApiManager googleApiManager) {
        this.zaia = googleApiManager;
    }

    @Override // com.google.android.gms.common.api.internal.BackgroundDetector.BackgroundStateChangeListener
    public final void onBackgroundStateChanged(boolean z2) {
        this.zaia.handler.sendMessage(this.zaia.handler.obtainMessage(1, Boolean.valueOf(z2)));
    }
}
