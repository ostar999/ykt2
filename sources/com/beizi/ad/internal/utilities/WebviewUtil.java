package com.beizi.ad.internal.utilities;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.WebView;
import com.beizi.ad.internal.g;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class WebviewUtil {
    private static final String VERSION_ZERO_HEADER = "Set-cookie";

    public static void cookieSync(Map<String, List<String>> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        CookieManager cookieManager = CookieManager.getInstance();
        if (cookieManager == null) {
            HaoboLog.i(HaoboLog.httpRespLogTag, "Unable to find a CookieManager");
            return;
        }
        try {
            String existingSDKUID = getExistingSDKUID();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key != null && key.equalsIgnoreCase(VERSION_ZERO_HEADER)) {
                    for (String str : entry.getValue()) {
                        if (!TextUtils.isEmpty(str) && str.contains("sdkuid") && (existingSDKUID == null || !str.contains(existingSDKUID))) {
                            cookieManager.setCookie(g.a().g(), str);
                            cookieManager.flush();
                        }
                    }
                }
            }
        } catch (IllegalStateException | Exception unused) {
        }
    }

    public static String getCookie() {
        return null;
    }

    private static String getExistingSDKUID() {
        CookieManager cookieManager = CookieManager.getInstance();
        if (cookieManager == null) {
            return null;
        }
        String cookie = cookieManager.getCookie(g.a().g());
        if (TextUtils.isEmpty(cookie)) {
            return null;
        }
        for (String str : cookie.split("; ")) {
            if (str != null && str.contains("sdkuid")) {
                return str;
            }
        }
        return null;
    }

    public static void onPause(WebView webView) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (webView == null) {
            return;
        }
        try {
            WebView.class.getDeclaredMethod("onPause", new Class[0]).invoke(webView, new Object[0]);
        } catch (Exception unused) {
        }
    }

    public static void onResume(WebView webView) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (webView == null) {
            return;
        }
        try {
            WebView.class.getDeclaredMethod("onResume", new Class[0]).invoke(webView, new Object[0]);
        } catch (Exception unused) {
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public static void setWebViewSettings(WebView webView) {
        if (webView == null) {
            return;
        }
        try {
            webView.getSettings().setBuiltInZoomControls(false);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setMixedContentMode(0);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
            webView.getSettings().setAllowFileAccess(false);
            webView.getSettings().setAllowContentAccess(false);
            webView.getSettings().setSavePassword(false);
            webView.getSettings().setAllowFileAccessFromFileURLs(false);
            webView.getSettings().setAllowUniversalAccessFromFileURLs(false);
            CookieManager cookieManager = CookieManager.getInstance();
            if (cookieManager != null) {
                cookieManager.setAcceptThirdPartyCookies(webView, true);
            } else {
                HaoboLog.d(HaoboLog.baseLogTag, "Failed to set Webview to accept 3rd party cookie");
            }
            webView.getSettings().setAppCachePath(webView.getContext().getCacheDir().getAbsolutePath());
            webView.getSettings().setAppCacheEnabled(true);
            webView.getSettings().setCacheMode(-1);
            WebView.setWebContentsDebuggingEnabled(false);
        } catch (Exception e2) {
            HaoboLog.e(HaoboLog.httpRespLogTag, "Unable update webview settings - Exception: " + e2.getMessage());
        }
    }
}
