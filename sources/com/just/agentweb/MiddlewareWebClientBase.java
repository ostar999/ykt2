package com.just.agentweb;

/* loaded from: classes4.dex */
public class MiddlewareWebClientBase extends WebViewClientDelegate {
    private static String TAG = "MiddlewareWebClientBase";
    private MiddlewareWebClientBase mMiddleWrareWebClientBase;

    public MiddlewareWebClientBase() {
        super(null);
    }

    public MiddlewareWebClientBase(android.webkit.WebViewClient webViewClient) {
        super(webViewClient);
    }

    public MiddlewareWebClientBase(MiddlewareWebClientBase middlewareWebClientBase) {
        super(middlewareWebClientBase);
        this.mMiddleWrareWebClientBase = middlewareWebClientBase;
    }

    public final MiddlewareWebClientBase enq(MiddlewareWebClientBase middlewareWebClientBase) {
        setDelegate(middlewareWebClientBase);
        this.mMiddleWrareWebClientBase = middlewareWebClientBase;
        return middlewareWebClientBase;
    }

    public final MiddlewareWebClientBase next() {
        return this.mMiddleWrareWebClientBase;
    }

    @Override // com.just.agentweb.WebViewClientDelegate
    public final void setDelegate(android.webkit.WebViewClient webViewClient) {
        super.setDelegate(webViewClient);
    }
}
