package com.xiaomi.push;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

/* loaded from: classes6.dex */
public class by {

    /* renamed from: a, reason: collision with root package name */
    private static by f24655a;

    /* renamed from: a, reason: collision with other field name */
    private Context f235a;

    /* renamed from: a, reason: collision with other field name */
    private Handler f236a;

    /* renamed from: a, reason: collision with other field name */
    private HandlerThread f237a;

    /* renamed from: a, reason: collision with other field name */
    private cr f238a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f239a;

    /* renamed from: b, reason: collision with root package name */
    private boolean f24656b;

    public static by a() {
        if (f24655a == null) {
            f24655a = new by();
        }
        return f24655a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        cn.a().a(this.f235a);
        this.f24656b = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        cn.a().m295a();
        this.f24656b = false;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m252a() {
        cn.a().c();
    }

    public void a(Context context) {
        this.f235a = context;
        bl.a(context);
        if (this.f239a) {
            return;
        }
        this.f239a = true;
        HandlerThread handlerThread = new HandlerThread("metoknlp_cl");
        this.f237a = handlerThread;
        handlerThread.start();
        this.f236a = new Handler(this.f237a.getLooper());
        this.f238a = new cq(this, null);
        bl.a().a(this.f238a);
        if (bk.a().m238a()) {
            b();
        }
    }

    public void b() {
        Handler handler = this.f236a;
        if (handler == null) {
            return;
        }
        handler.post(new bz(this));
    }
}
