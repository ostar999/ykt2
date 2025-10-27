package com.xiaomi.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Handler;

/* loaded from: classes6.dex */
class bx extends BroadcastReceiver {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ bv f24654a;

    private bx(bv bvVar) {
        this.f24654a = bvVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action;
        NetworkInfo networkInfo;
        Handler handler;
        int i2;
        if (intent == null || (action = intent.getAction()) == null || !action.equals("android.net.conn.CONNECTIVITY_CHANGE") || (networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo")) == null) {
            return;
        }
        if (networkInfo.isConnected()) {
            handler = this.f24654a.f233a;
            i2 = 200;
        } else {
            if (networkInfo.getState() != NetworkInfo.State.DISCONNECTED) {
                return;
            }
            handler = this.f24654a.f233a;
            i2 = 201;
        }
        handler.obtainMessage(i2, networkInfo).sendToTarget();
    }
}
