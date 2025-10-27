package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;

/* loaded from: classes3.dex */
final class zaaz extends com.google.android.gms.internal.base.zar {
    private final /* synthetic */ zaaw zagv;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaaz(zaaw zaawVar, Looper looper) {
        super(looper);
        this.zagv = zaawVar;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        int i2 = message.what;
        if (i2 == 1) {
            this.zagv.zaat();
            return;
        }
        if (i2 == 2) {
            this.zagv.resume();
            return;
        }
        StringBuilder sb = new StringBuilder(31);
        sb.append("Unknown message id: ");
        sb.append(i2);
        Log.w("GoogleApiClientImpl", sb.toString());
    }
}
