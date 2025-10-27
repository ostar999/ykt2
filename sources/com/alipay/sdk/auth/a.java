package com.alipay.sdk.auth;

import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;

/* loaded from: classes2.dex */
final class a implements DownloadListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ AuthActivity f3152a;

    public a(AuthActivity authActivity) {
        this.f3152a = authActivity;
    }

    @Override // android.webkit.DownloadListener
    public final void onDownloadStart(String str, String str2, String str3, String str4, long j2) {
        try {
            this.f3152a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Throwable unused) {
        }
    }
}
