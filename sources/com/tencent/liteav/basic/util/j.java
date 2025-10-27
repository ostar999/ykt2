package com.tencent.liteav.basic.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* loaded from: classes6.dex */
public class j extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private int f18752a;

    /* renamed from: b, reason: collision with root package name */
    private boolean f18753b;

    /* renamed from: c, reason: collision with root package name */
    private a f18754c;

    public interface a {
        void onTimeout();
    }

    public j(Looper looper, a aVar) {
        super(looper);
        this.f18753b = false;
        this.f18754c = aVar;
    }

    public void a(int i2, int i3) {
        a();
        this.f18752a = i3;
        this.f18753b = true;
        sendEmptyMessageDelayed(0, i2);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        a aVar = this.f18754c;
        if (aVar != null) {
            aVar.onTimeout();
        }
        if (this.f18753b) {
            sendEmptyMessageDelayed(0, this.f18752a);
        }
    }

    public void a() {
        while (hasMessages(0)) {
            removeMessages(0);
        }
        this.f18753b = false;
    }
}
