package com.google.android.gms.common.api.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/* loaded from: classes3.dex */
public final class zabq extends BroadcastReceiver {
    private Context mContext;
    private final zabp zajl;

    public zabq(zabp zabpVar) {
        this.zajl = zabpVar;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Uri data = intent.getData();
        if ("com.google.android.gms".equals(data != null ? data.getSchemeSpecificPart() : null)) {
            this.zajl.zas();
            unregister();
        }
    }

    public final synchronized void unregister() {
        Context context = this.mContext;
        if (context != null) {
            context.unregisterReceiver(this);
        }
        this.mContext = null;
    }

    public final void zac(Context context) {
        this.mContext = context;
    }
}
