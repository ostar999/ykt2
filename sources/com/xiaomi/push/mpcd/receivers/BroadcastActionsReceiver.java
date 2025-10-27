package com.xiaomi.push.mpcd.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.push.dz;

/* loaded from: classes6.dex */
public class BroadcastActionsReceiver extends BroadcastReceiver {

    /* renamed from: a, reason: collision with root package name */
    private dz f25524a;

    public BroadcastActionsReceiver(dz dzVar) {
        this.f25524a = dzVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        dz dzVar = this.f25524a;
        if (dzVar != null) {
            dzVar.a(context, intent);
        }
    }
}
