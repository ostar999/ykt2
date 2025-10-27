package com.xiaomi.push;

import android.util.Log;
import com.xiaomi.push.al;

/* loaded from: classes6.dex */
class dq extends al.b {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ dp f24744a;

    public dq(dp dpVar) {
        this.f24744a = dpVar;
    }

    @Override // com.xiaomi.push.al.b
    public void b() throws Throwable {
        if (dp.f331a.isEmpty()) {
            return;
        }
        try {
            if (aa.d()) {
                this.f24744a.m329a();
            } else {
                Log.w(this.f24744a.f24742b, "SDCard is unavailable.");
            }
        } catch (Exception e2) {
            Log.e(this.f24744a.f24742b, "", e2);
        }
    }
}
