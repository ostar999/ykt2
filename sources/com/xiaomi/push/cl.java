package com.xiaomi.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes6.dex */
class cl extends BroadcastReceiver {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ cg f24686a;

    public cl(cg cgVar) {
        this.f24686a = cgVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            if (this.f24686a.f261a.hasMessages(1)) {
                this.f24686a.f261a.removeMessages(1);
            }
            this.f24686a.f261a.sendMessageDelayed(this.f24686a.f261a.obtainMessage(1), com.heytap.mcssdk.constant.a.f7153q);
        }
    }
}
