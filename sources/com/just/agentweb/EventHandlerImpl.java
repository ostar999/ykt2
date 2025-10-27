package com.just.agentweb;

import android.view.KeyEvent;
import android.webkit.WebView;

/* loaded from: classes4.dex */
public class EventHandlerImpl implements IEventHandler {
    private EventInterceptor mEventInterceptor;
    private WebView mWebView;

    public EventHandlerImpl(WebView webView, EventInterceptor eventInterceptor) {
        this.mWebView = webView;
        this.mEventInterceptor = eventInterceptor;
    }

    public static final EventHandlerImpl getInstantce(WebView webView, EventInterceptor eventInterceptor) {
        return new EventHandlerImpl(webView, eventInterceptor);
    }

    @Override // com.just.agentweb.IEventHandler
    public boolean back() {
        EventInterceptor eventInterceptor = this.mEventInterceptor;
        if (eventInterceptor != null && eventInterceptor.event()) {
            return true;
        }
        WebView webView = this.mWebView;
        if (webView == null || !webView.canGoBack()) {
            return false;
        }
        this.mWebView.goBack();
        return true;
    }

    @Override // com.just.agentweb.IEventHandler
    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 == 4) {
            return back();
        }
        return false;
    }
}
