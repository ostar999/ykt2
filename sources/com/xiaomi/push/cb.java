package com.xiaomi.push;

import android.os.Handler;
import android.os.Message;
import java.util.HashMap;

/* loaded from: classes6.dex */
class cb extends Handler {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ cn f24660a;

    public cb(cn cnVar) {
        this.f24660a = cnVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        cn cnVar;
        synchronized (this.f24660a.f275a) {
            int i2 = message.what;
            if (i2 != 0) {
                if (i2 == 1) {
                    this.f24660a.f268a = 0;
                    if (this.f24660a.f274a != null) {
                        this.f24660a.f274a.cancel(true);
                    }
                    cnVar = this.f24660a;
                } else if (i2 == 3) {
                    Object obj = message.obj;
                    if (obj != null) {
                        String str = (String) obj;
                        if (this.f24660a.f271a != null) {
                            this.f24660a.f271a.d(str);
                        }
                    }
                    cnVar = this.f24660a;
                }
                cnVar.b();
            } else {
                this.f24660a.a((HashMap) message.obj);
            }
        }
    }
}
