package com.tencent.smtt.sdk;

import android.content.Context;

/* loaded from: classes6.dex */
public class WebViewDatabase {

    /* renamed from: a, reason: collision with root package name */
    private static WebViewDatabase f21101a;

    /* renamed from: b, reason: collision with root package name */
    private Context f21102b;

    public WebViewDatabase(Context context) {
        this.f21102b = context;
    }

    private static synchronized WebViewDatabase a(Context context) {
        if (f21101a == null) {
            f21101a = new WebViewDatabase(context);
        }
        return f21101a;
    }

    public static WebViewDatabase getInstance(Context context) {
        return a(context);
    }

    public void clearFormData() {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            android.webkit.WebViewDatabase.getInstance(this.f21102b).clearFormData();
        } else {
            wVarA.c().g(this.f21102b);
        }
    }

    public void clearHttpAuthUsernamePassword() {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            android.webkit.WebViewDatabase.getInstance(this.f21102b).clearHttpAuthUsernamePassword();
        } else {
            wVarA.c().e(this.f21102b);
        }
    }

    @Deprecated
    public void clearUsernamePassword() {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            android.webkit.WebViewDatabase.getInstance(this.f21102b).clearUsernamePassword();
        } else {
            wVarA.c().c(this.f21102b);
        }
    }

    public boolean hasFormData() {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.WebViewDatabase.getInstance(this.f21102b).hasFormData() : wVarA.c().f(this.f21102b);
    }

    public boolean hasHttpAuthUsernamePassword() {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.WebViewDatabase.getInstance(this.f21102b).hasHttpAuthUsernamePassword() : wVarA.c().d(this.f21102b);
    }

    @Deprecated
    public boolean hasUsernamePassword() {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.WebViewDatabase.getInstance(this.f21102b).hasUsernamePassword() : wVarA.c().b(this.f21102b);
    }
}
