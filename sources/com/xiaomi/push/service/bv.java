package com.xiaomi.push.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/* loaded from: classes6.dex */
class bv extends Handler {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f25668a;

    public bv(XMPushService xMPushService) {
        this.f25668a = xMPushService;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (message != null) {
            try {
                int i2 = message.what;
                if (i2 == 17) {
                    Object obj = message.obj;
                    if (obj != null) {
                        this.f25668a.onStart((Intent) obj, XMPushService.f25542a);
                    }
                } else if (i2 == 18) {
                    Message messageObtain = Message.obtain((Handler) null, 0);
                    messageObtain.what = 18;
                    Bundle bundle = new Bundle();
                    bundle.putString("xmsf_region", this.f25668a.f963a);
                    messageObtain.setData(bundle);
                    message.replyTo.send(messageObtain);
                }
            } catch (Throwable unused) {
            }
        }
    }
}
