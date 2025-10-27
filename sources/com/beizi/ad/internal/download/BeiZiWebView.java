package com.beizi.ad.internal.download;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

/* loaded from: classes2.dex */
public class BeiZiWebView extends WebView {
    public BeiZiWebView(Context context) {
        super(context);
        a();
    }

    private void a() {
        try {
            b();
        } catch (Throwable unused) {
        }
    }

    private void b() {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setBuiltInZoomControls(false);
        settings.setEnableSmoothTransition(true);
        settings.setLightTouchEnabled(false);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setLoadsImagesAutomatically(true);
        settings.setSavePassword(false);
        settings.setSupportZoom(false);
        settings.setUseWideViewPort(false);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setMixedContentMode(0);
        WebView.setWebContentsDebuggingEnabled(false);
        settings.setAppCachePath(getContext().getCacheDir().getAbsolutePath());
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(-1);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        CookieManager cookieManager = CookieManager.getInstance();
        if (cookieManager != null) {
            cookieManager.setAcceptThirdPartyCookies(this, true);
        }
        setHorizontalScrollbarOverlay(false);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollbarOverlay(false);
        setVerticalScrollBarEnabled(false);
        setScrollBarStyle(0);
    }

    public BeiZiWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public BeiZiWebView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a();
    }
}
