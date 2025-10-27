package com.huawei.secure.android.common.webview;

import android.util.Log;
import android.webkit.WebView;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes4.dex */
public class SafeGetUrl {

    /* renamed from: c, reason: collision with root package name */
    private static final String f8472c = "SafeGetUrl";

    /* renamed from: d, reason: collision with root package name */
    private static final long f8473d = 200;

    /* renamed from: a, reason: collision with root package name */
    private String f8474a;

    /* renamed from: b, reason: collision with root package name */
    private WebView f8475b;

    public class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ CountDownLatch f8476a;

        public a(CountDownLatch countDownLatch) {
            this.f8476a = countDownLatch;
        }

        @Override // java.lang.Runnable
        public void run() {
            SafeGetUrl safeGetUrl = SafeGetUrl.this;
            safeGetUrl.setUrl(safeGetUrl.f8475b.getUrl());
            this.f8476a.countDown();
        }
    }

    public SafeGetUrl() {
    }

    public String getUrlMethod() throws InterruptedException {
        if (this.f8475b == null) {
            return "";
        }
        if (com.huawei.secure.android.common.util.b.a()) {
            return this.f8475b.getUrl();
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        com.huawei.secure.android.common.util.c.a(new a(countDownLatch));
        try {
            countDownLatch.await();
        } catch (InterruptedException e2) {
            Log.e(f8472c, "getUrlMethod: InterruptedException " + e2.getMessage(), e2);
        }
        return this.f8474a;
    }

    public WebView getWebView() {
        return this.f8475b;
    }

    public void setUrl(String str) {
        this.f8474a = str;
    }

    public void setWebView(WebView webView) {
        this.f8475b = webView;
    }

    public SafeGetUrl(WebView webView) {
        this.f8475b = webView;
    }
}
