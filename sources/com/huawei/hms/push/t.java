package com.huawei.hms.push;

import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class t extends Handler {

    /* renamed from: a, reason: collision with root package name */
    public WeakReference<a> f8039a;

    public interface a {
        void handleMessage(Message message);
    }

    public t(a aVar) {
        this.f8039a = new WeakReference<>(aVar);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        a aVar = this.f8039a.get();
        if (aVar != null) {
            aVar.handleMessage(message);
        }
    }
}
