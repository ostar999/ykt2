package com.alipay.sdk.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;

/* loaded from: classes2.dex */
final class m implements DownloadListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f3395a;

    public m(Context context) {
        this.f3395a = context;
    }

    @Override // android.webkit.DownloadListener
    public final void onDownloadStart(String str, String str2, String str3, String str4, long j2) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            intent.setFlags(268435456);
            this.f3395a.startActivity(intent);
        } catch (Throwable unused) {
        }
    }
}
