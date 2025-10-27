package com.xiaomi.push.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;

/* loaded from: classes6.dex */
class bu implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f25667a;

    public bu(XMPushService xMPushService) {
        this.f25667a = xMPushService;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        com.xiaomi.channel.commonutils.logger.b.b("onServiceConnected " + iBinder);
        Service serviceA = XMJobService.a();
        if (serviceA != null) {
            this.f25667a.startForeground(XMPushService.f25543b, XMPushService.a((Context) this.f25667a));
            serviceA.startForeground(XMPushService.f25543b, XMPushService.a((Context) this.f25667a));
            serviceA.stopForeground(true);
            this.f25667a.unbindService(this);
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m117a("XMService connected but innerService is null " + iBinder);
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
    }
}
