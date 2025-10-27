package com.xiaomi.push;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.text.ParseException;

/* loaded from: classes6.dex */
class cs extends Handler {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ bk f24692a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public cs(bk bkVar, Looper looper) {
        super(looper);
        this.f24692a = bkVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) throws ParseException {
        int i2 = message.what;
        if (i2 == 101) {
            this.f24692a.c();
        } else {
            if (i2 != 102) {
                return;
            }
            bl.a().m244b();
        }
    }
}
