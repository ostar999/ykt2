package com.vivo.push;

import android.text.TextUtils;
import com.vivo.push.util.z;

/* loaded from: classes6.dex */
final class f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f24375a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ e f24376b;

    public f(e eVar, String str) {
        this.f24376b = eVar;
        this.f24375a = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.f24376b.f24355h == null || TextUtils.isEmpty(this.f24375a) || !z.b(this.f24376b.f24355h, this.f24376b.f24355h.getPackageName(), this.f24375a)) {
            return;
        }
        this.f24376b.i();
    }
}
