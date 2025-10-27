package com.ta.a.d;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes6.dex */
public class h implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    private static volatile boolean f17216b = false;
    private Context mContext;

    public h(Context context) {
        this.mContext = context;
    }

    private void a() {
        com.ta.a.e.h.f();
        if (com.ta.a.e.f.e(this.mContext) && !f17216b) {
            f17216b = true;
            try {
                b();
            } catch (Throwable unused) {
            }
            f17216b = false;
        }
    }

    private void b() {
        com.ta.a.e.h.f();
        String strI = i();
        if (TextUtils.isEmpty(strI)) {
            com.ta.a.e.h.m109a("postData is empty", new Object[0]);
        } else if (a(strI)) {
            com.ta.a.e.h.m109a("", "upload success");
        } else {
            com.ta.a.e.h.m109a("", "upload fail");
        }
    }

    private String i() {
        String strR = com.ta.utdid2.device.a.a().r();
        if (TextUtils.isEmpty(strR)) {
            return null;
        }
        String strA = com.ta.a.c.a.a(strR);
        if (com.ta.a.e.h.b()) {
            com.ta.a.e.h.b("", strA);
        }
        return com.ta.a.c.b.b(strA);
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            a();
        } catch (Throwable th) {
            com.ta.a.e.h.a("", th, new Object[0]);
        }
    }

    private boolean a(String str) throws Throwable {
        a aVarA = b.a("https://mpush-api.aliyun.com/v2.0/a/audid/req/", str, true);
        if (aVarA == null) {
            return false;
        }
        return com.ta.utdid2.device.f.a(aVarA);
    }
}
