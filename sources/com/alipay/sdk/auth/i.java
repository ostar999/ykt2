package com.alipay.sdk.auth;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import java.util.List;

/* loaded from: classes2.dex */
final class i implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Activity f3165a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ StringBuilder f3166b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ APAuthInfo f3167c;

    public i(Activity activity, StringBuilder sb, APAuthInfo aPAuthInfo) {
        this.f3165a = activity;
        this.f3166b = sb;
        this.f3167c = aPAuthInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        com.alipay.sdk.packet.b bVarA;
        try {
            try {
                bVarA = new com.alipay.sdk.packet.impl.a().a(this.f3165a, this.f3166b.toString());
            } catch (Throwable unused) {
                bVarA = null;
            }
            if (h.f3163c != null) {
                h.f3163c.b();
                h.b();
            }
        } catch (Exception unused2) {
            if (h.f3163c == null) {
                return;
            }
        } catch (Throwable th) {
            if (h.f3163c != null) {
                h.f3163c.b();
            }
            throw th;
        }
        if (bVarA == null) {
            String unused3 = h.f3164d = this.f3167c.getRedirectUri() + "?resultCode=202";
            h.a(this.f3165a, h.f3164d);
            if (h.f3163c != null) {
                h.f3163c.b();
                return;
            }
            return;
        }
        List<com.alipay.sdk.protocol.b> listA = com.alipay.sdk.protocol.b.a(bVarA.a().optJSONObject(com.alipay.sdk.cons.c.f3228c).optJSONObject(com.alipay.sdk.cons.c.f3229d));
        int i2 = 0;
        while (true) {
            if (i2 >= listA.size()) {
                break;
            }
            if (listA.get(i2).f3313a == com.alipay.sdk.protocol.a.WapPay) {
                String unused4 = h.f3164d = listA.get(i2).f3314b[0];
                break;
            }
            i2++;
        }
        if (!TextUtils.isEmpty(h.f3164d)) {
            Intent intent = new Intent(this.f3165a, (Class<?>) AuthActivity.class);
            intent.putExtra("params", h.f3164d);
            intent.putExtra("redirectUri", this.f3167c.getRedirectUri());
            this.f3165a.startActivity(intent);
            if (h.f3163c == null) {
                return;
            }
            h.f3163c.b();
            return;
        }
        String unused5 = h.f3164d = this.f3167c.getRedirectUri() + "?resultCode=202";
        h.a(this.f3165a, h.f3164d);
        if (h.f3163c != null) {
            h.f3163c.b();
        }
    }
}
