package com.meizu.cloud.pushsdk.base;

import android.content.Context;
import android.os.Environment;

/* loaded from: classes4.dex */
public class h extends j<g> implements g {

    /* renamed from: c, reason: collision with root package name */
    private static h f9265c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f9266d;

    public h(g gVar) {
        super(gVar);
        this.f9266d = false;
    }

    public static h b() {
        if (f9265c == null) {
            synchronized (h.class) {
                if (f9265c == null) {
                    f9265c = new h(new b());
                }
            }
        }
        return f9265c;
    }

    public void a(Context context) {
        if (this.f9266d) {
            return;
        }
        this.f9266d = true;
        b((context.getApplicationInfo().flags & 2) != 0);
        a(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/pushSdk/" + context.getPackageName());
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void a(String str) {
        c().a(str);
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void a(String str, String str2) {
        c().a(str, str2);
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void a(String str, String str2, Throwable th) {
        c().a(str, str2, th);
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void a(boolean z2) {
        c().a(z2);
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public boolean a() {
        return c().a();
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void b(String str, String str2) {
        c().b(str, str2);
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void b(boolean z2) {
        c().b(z2);
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void c(String str, String str2) {
        c().c(str, str2);
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void d(String str, String str2) {
        c().d(str, str2);
    }
}
