package com.xiaomi.push;

import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.Iterator;

/* loaded from: classes6.dex */
class bw extends Handler {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ bv f24653a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bw(bv bvVar, Looper looper) {
        super(looper);
        this.f24653a = bvVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        int i2 = message.what;
        if (i2 == 200) {
            synchronized (this.f24653a.f234a) {
                Iterator it = this.f24653a.f234a.iterator();
                while (it.hasNext()) {
                    ((bt) it.next()).a((NetworkInfo) message.obj);
                }
            }
            return;
        }
        if (i2 != 201) {
            return;
        }
        synchronized (this.f24653a.f234a) {
            Iterator it2 = this.f24653a.f234a.iterator();
            while (it2.hasNext()) {
                ((bt) it2.next()).b((NetworkInfo) message.obj);
            }
        }
    }
}
