package com.alipay.sdk.app;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alipay.sdk.util.l;

/* loaded from: classes2.dex */
public class H5PayActivity extends Activity {

    /* renamed from: a, reason: collision with root package name */
    private WebView f3065a;

    /* renamed from: b, reason: collision with root package name */
    private WebViewClient f3066b;

    private void b() {
        try {
            super.requestWindowFeature(1);
        } catch (Throwable unused) {
        }
    }

    public void a() {
        Object obj = PayTask.f3067a;
        synchronized (obj) {
            try {
                obj.notify();
            } catch (Exception unused) {
            }
        }
    }

    @Override // android.app.Activity
    public void finish() {
        a();
        super.finish();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (!this.f3065a.canGoBack()) {
            i.f3096a = i.a();
            finish();
        } else if (((b) this.f3066b).f3082c) {
            j jVarA = j.a(j.NETWORK_ERROR.f3105h);
            i.f3096a = i.a(jVarA.f3105h, jVarA.f3106i, "");
            finish();
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        try {
            super.requestWindowFeature(1);
        } catch (Throwable unused) {
        }
        super.onCreate(bundle);
        try {
            Bundle extras = getIntent().getExtras();
            String string = extras.getString("url");
            if (!l.b(string)) {
                finish();
                return;
            }
            try {
                this.f3065a = l.a(this, string, extras.getString("cookie"));
                b bVar = new b(this);
                this.f3066b = bVar;
                this.f3065a.setWebViewClient(bVar);
            } catch (Throwable th) {
                com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3112b, "GetInstalledAppEx", th);
                finish();
            }
        } catch (Exception unused2) {
            finish();
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        WebView webView = this.f3065a;
        if (webView != null) {
            webView.removeAllViews();
            ((ViewGroup) this.f3065a.getParent()).removeAllViews();
            try {
                this.f3065a.destroy();
            } catch (Throwable unused) {
            }
            this.f3065a = null;
        }
        WebViewClient webViewClient = this.f3066b;
        if (webViewClient != null) {
            b bVar = (b) webViewClient;
            bVar.f3081b = null;
            bVar.f3080a = null;
        }
    }
}
