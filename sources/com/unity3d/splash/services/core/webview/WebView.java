package com.unity3d.splash.services.core.webview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.misc.ViewUtilities;
import com.unity3d.splash.services.core.webview.bridge.WebViewBridgeInterface;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes6.dex */
public class WebView extends android.webkit.WebView {
    private static Method _evaluateJavascript;

    public class JavaScriptInvocation implements Runnable {
        private String _jsString;
        private android.webkit.WebView _webView;

        public JavaScriptInvocation(String str, android.webkit.WebView webView) {
            this._jsString = str;
            this._webView = webView;
        }

        @Override // java.lang.Runnable
        public void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (this._jsString == null) {
                DeviceLog.error("Could not process JavaScript, the string is NULL");
                return;
            }
            try {
                WebView._evaluateJavascript.invoke(this._webView, this._jsString, null);
            } catch (Exception e2) {
                DeviceLog.exception("Error while processing JavaScriptString", e2);
            }
        }
    }

    public WebView(Context context) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super(context);
        WebSettings settings = getSettings();
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        try {
            _evaluateJavascript = android.webkit.WebView.class.getMethod("evaluateJavascript", String.class, ValueCallback.class);
        } catch (NoSuchMethodException e2) {
            DeviceLog.exception("Method evaluateJavascript not found", e2);
            _evaluateJavascript = null;
        }
        settings.setAppCacheEnabled(false);
        settings.setBlockNetworkImage(false);
        settings.setBlockNetworkLoads(false);
        settings.setBuiltInZoomControls(false);
        settings.setCacheMode(2);
        settings.setDatabaseEnabled(false);
        settings.setDisplayZoomControls(false);
        settings.setDomStorageEnabled(false);
        settings.setEnableSmoothTransition(false);
        settings.setGeolocationEnabled(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setJavaScriptEnabled(true);
        settings.setLightTouchEnabled(false);
        settings.setLoadWithOverviewMode(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setMixedContentMode(1);
        settings.setNeedInitialFocus(true);
        settings.setPluginState(WebSettings.PluginState.OFF);
        settings.setRenderPriority(WebSettings.RenderPriority.NORMAL);
        settings.setSaveFormData(false);
        settings.setSavePassword(false);
        settings.setSupportMultipleWindows(false);
        settings.setSupportZoom(false);
        settings.setUseWideViewPort(true);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        setInitialScale(0);
        setBackgroundColor(0);
        ViewUtilities.setBackground(this, new ColorDrawable(0));
        setBackgroundResource(0);
        addJavascriptInterface(new WebViewBridgeInterface(), "webviewbridge");
    }

    public void invokeJavascript(String str) {
        Utilities.runOnUiThread(new JavaScriptInvocation(str, this));
    }

    @Override // android.webkit.WebView
    public void loadUrl(String str) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.debug("Loading url: " + str);
        super.loadUrl(str);
    }
}
