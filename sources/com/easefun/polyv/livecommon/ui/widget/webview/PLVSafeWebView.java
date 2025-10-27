package com.easefun.polyv.livecommon.ui.widget.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes3.dex */
public class PLVSafeWebView extends WebView {
    private static final String TAG = "PLVSafeWebView";

    public PLVSafeWebView(Context context) {
        super(context);
        removeSearchBox();
    }

    public static void disableAccessibility(Context context) {
    }

    private void removeSearchBox() {
        super.removeJavascriptInterface("searchBoxJavaBridge_");
        super.removeJavascriptInterface("accessibility");
        super.removeJavascriptInterface("accessibilityTraversal");
    }

    @Override // android.webkit.WebView
    public void destroy() {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        stopLoading();
        clearMatches();
        clearHistory();
        clearSslPreferences();
        clearCache(true);
        loadUrl("about:blank");
        removeAllViews();
        super.destroy();
    }

    @Override // android.webkit.WebView
    public boolean isPrivateBrowsingEnabled() {
        return super.isPrivateBrowsingEnabled();
    }

    @Override // android.view.View
    public void scrollTo(int x2, int y2) {
        super.scrollTo(0, 0);
    }

    @Override // android.webkit.WebView, android.view.View
    public void setOverScrollMode(int mode) {
        try {
            super.setOverScrollMode(mode);
        } catch (Throwable th) {
            String stackTraceString = Log.getStackTraceString(th);
            if (!stackTraceString.contains("android.content.pm.PackageManager$NameNotFoundException") && !stackTraceString.contains("java.lang.RuntimeException: Cannot load WebView") && !stackTraceString.contains("android.webkit.WebViewFactory$MissingWebViewPackageException: Failed to load WebView provider: No WebView installed")) {
                throw th;
            }
            PLVCommonLog.e(TAG, "setOverScrollMode:" + th.getMessage());
        }
    }

    public PLVSafeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        removeSearchBox();
    }

    public PLVSafeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        removeSearchBox();
    }
}
