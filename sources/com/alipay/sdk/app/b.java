package com.alipay.sdk.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alipay.sdk.util.l;

/* loaded from: classes2.dex */
public final class b extends WebViewClient {

    /* renamed from: a, reason: collision with root package name */
    Activity f3080a;

    /* renamed from: b, reason: collision with root package name */
    Handler f3081b;

    /* renamed from: c, reason: collision with root package name */
    boolean f3082c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f3083d;

    /* renamed from: e, reason: collision with root package name */
    private com.alipay.sdk.widget.a f3084e;

    /* renamed from: f, reason: collision with root package name */
    private Runnable f3085f = new f(this);

    public b(Activity activity) {
        this.f3080a = activity;
        this.f3081b = new Handler(this.f3080a.getMainLooper());
    }

    private void c() {
        this.f3081b = null;
        this.f3080a = null;
    }

    private boolean d() {
        return this.f3082c;
    }

    @Override // android.webkit.WebViewClient
    public final void onPageFinished(WebView webView, String str) {
        if (this.f3081b != null) {
            b();
            this.f3081b.removeCallbacks(this.f3085f);
        }
    }

    @Override // android.webkit.WebViewClient
    public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        if (this.f3081b != null) {
            if (this.f3084e == null) {
                com.alipay.sdk.widget.a aVar = new com.alipay.sdk.widget.a(this.f3080a, com.alipay.sdk.widget.a.f3397a);
                this.f3084e = aVar;
                aVar.f3401e = true;
            }
            this.f3084e.a();
            this.f3081b.postDelayed(this.f3085f, 30000L);
        }
        super.onPageStarted(webView, str, bitmap);
    }

    @Override // android.webkit.WebViewClient
    public final void onReceivedError(WebView webView, int i2, String str, String str2) {
        this.f3082c = true;
        super.onReceivedError(webView, i2, str, str2);
    }

    @Override // android.webkit.WebViewClient
    public final void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3111a, com.alipay.sdk.app.statistic.c.f3127q, "证书错误");
        if (!this.f3083d) {
            this.f3080a.runOnUiThread(new c(this, sslErrorHandler));
        } else {
            sslErrorHandler.proceed();
            this.f3083d = false;
        }
    }

    @Override // android.webkit.WebViewClient
    public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
        return l.a(webView, str, this.f3080a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        com.alipay.sdk.widget.a aVar = this.f3084e;
        if (aVar != null) {
            aVar.b();
        }
        this.f3084e = null;
    }

    private void a() {
        if (this.f3084e == null) {
            com.alipay.sdk.widget.a aVar = new com.alipay.sdk.widget.a(this.f3080a, com.alipay.sdk.widget.a.f3397a);
            this.f3084e = aVar;
            aVar.f3401e = true;
        }
        this.f3084e.a();
    }
}
