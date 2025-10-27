package com.xiaomi.push;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* loaded from: classes6.dex */
class co extends Handler {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ cg f24690a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public co(cg cgVar, Looper looper) {
        super(looper);
        this.f24690a = cgVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        int i2 = message.what;
        if (i2 == 1) {
            this.f24690a.a(false);
        } else {
            if (i2 != 2) {
                return;
            }
            Object obj = message.obj;
            this.f24690a.b(obj != null ? ((Boolean) obj).booleanValue() : false);
        }
    }
}
