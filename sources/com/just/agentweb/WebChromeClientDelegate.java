package com.just.agentweb;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import androidx.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class WebChromeClientDelegate extends android.webkit.WebChromeClient {
    private android.webkit.WebChromeClient mDelegate;

    public WebChromeClientDelegate(android.webkit.WebChromeClient webChromeClient) {
        this.mDelegate = webChromeClient;
    }

    private void commonRefect(android.webkit.WebChromeClient webChromeClient, String str, Object[] objArr, Class... clsArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (webChromeClient == null) {
            return;
        }
        try {
            webChromeClient.getClass().getMethod(str, clsArr).invoke(webChromeClient, objArr);
        } catch (Exception e2) {
            if (LogUtils.isDebug()) {
                e2.printStackTrace();
            }
        }
    }

    @Override // android.webkit.WebChromeClient
    public Bitmap getDefaultVideoPoster() {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        return webChromeClient != null ? webChromeClient.getDefaultVideoPoster() : super.getDefaultVideoPoster();
    }

    public android.webkit.WebChromeClient getDelegate() {
        return this.mDelegate;
    }

    @Override // android.webkit.WebChromeClient
    public View getVideoLoadingProgressView() {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        return webChromeClient != null ? webChromeClient.getVideoLoadingProgressView() : super.getVideoLoadingProgressView();
    }

    @Override // android.webkit.WebChromeClient
    public void getVisitedHistory(ValueCallback<String[]> valueCallback) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.getVisitedHistory(valueCallback);
        } else {
            super.getVisitedHistory(valueCallback);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onCloseWindow(WebView webView) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onCloseWindow(webView);
        } else {
            super.onCloseWindow(webView);
        }
    }

    @Override // android.webkit.WebChromeClient
    @Deprecated
    public void onConsoleMessage(String str, int i2, String str2) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onConsoleMessage(str, i2, str2);
        } else {
            super.onConsoleMessage(str, i2, str2);
        }
    }

    @Override // android.webkit.WebChromeClient
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        return webChromeClient != null ? webChromeClient.onConsoleMessage(consoleMessage) : super.onConsoleMessage(consoleMessage);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onCreateWindow(WebView webView, boolean z2, boolean z3, Message message) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        return webChromeClient != null ? webChromeClient.onCreateWindow(webView, z2, z3, message) : super.onCreateWindow(webView, z2, z3, message);
    }

    @Override // android.webkit.WebChromeClient
    @Deprecated
    public void onExceededDatabaseQuota(String str, String str2, long j2, long j3, long j4, WebStorage.QuotaUpdater quotaUpdater) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onExceededDatabaseQuota(str, str2, j2, j3, j4, quotaUpdater);
        } else {
            super.onExceededDatabaseQuota(str, str2, j2, j3, j4, quotaUpdater);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onGeolocationPermissionsHidePrompt() {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onGeolocationPermissionsHidePrompt();
        } else {
            super.onGeolocationPermissionsHidePrompt();
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onGeolocationPermissionsShowPrompt(str, callback);
        } else {
            super.onGeolocationPermissionsShowPrompt(str, callback);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onHideCustomView() {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onHideCustomView();
        } else {
            super.onHideCustomView();
        }
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        return webChromeClient != null ? webChromeClient.onJsAlert(webView, str, str2, jsResult) : super.onJsAlert(webView, str, str2, jsResult);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsBeforeUnload(WebView webView, String str, String str2, JsResult jsResult) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        return webChromeClient != null ? webChromeClient.onJsBeforeUnload(webView, str, str2, jsResult) : super.onJsBeforeUnload(webView, str, str2, jsResult);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        return webChromeClient != null ? webChromeClient.onJsConfirm(webView, str, str2, jsResult) : super.onJsConfirm(webView, str, str2, jsResult);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        return webChromeClient != null ? webChromeClient.onJsPrompt(webView, str, str2, str3, jsPromptResult) : super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsTimeout() {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        return webChromeClient != null ? webChromeClient.onJsTimeout() : super.onJsTimeout();
    }

    @Override // android.webkit.WebChromeClient
    @RequiresApi(api = 21)
    public void onPermissionRequest(PermissionRequest permissionRequest) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onPermissionRequest(permissionRequest);
        } else {
            super.onPermissionRequest(permissionRequest);
        }
    }

    @Override // android.webkit.WebChromeClient
    @RequiresApi(api = 21)
    public void onPermissionRequestCanceled(PermissionRequest permissionRequest) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onPermissionRequestCanceled(permissionRequest);
        } else {
            super.onPermissionRequestCanceled(permissionRequest);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onProgressChanged(WebView webView, int i2) {
        super.onProgressChanged(webView, i2);
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onProgressChanged(webView, i2);
        }
    }

    @Deprecated
    public void onReachedMaxAppCacheSize(long j2, long j3, WebStorage.QuotaUpdater quotaUpdater) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onReachedMaxAppCacheSize(j2, j3, quotaUpdater);
        } else {
            super.onReachedMaxAppCacheSize(j2, j3, quotaUpdater);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedIcon(WebView webView, Bitmap bitmap) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onReceivedIcon(webView, bitmap);
        } else {
            super.onReceivedIcon(webView, bitmap);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedTitle(WebView webView, String str) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onReceivedTitle(webView, str);
        } else {
            super.onReceivedTitle(webView, str);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedTouchIconUrl(WebView webView, String str, boolean z2) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onReceivedTouchIconUrl(webView, str, z2);
        } else {
            super.onReceivedTouchIconUrl(webView, str, z2);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onRequestFocus(WebView webView) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onRequestFocus(webView);
        } else {
            super.onRequestFocus(webView);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onShowCustomView(View view, int i2, WebChromeClient.CustomViewCallback customViewCallback) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onShowCustomView(view, i2, customViewCallback);
        } else {
            super.onShowCustomView(view, i2, customViewCallback);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        if (webChromeClient != null) {
            webChromeClient.onShowCustomView(view, customViewCallback);
        } else {
            super.onShowCustomView(view, customViewCallback);
        }
    }

    @Override // android.webkit.WebChromeClient
    @RequiresApi(api = 21)
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        android.webkit.WebChromeClient webChromeClient = this.mDelegate;
        return webChromeClient != null ? webChromeClient.onShowFileChooser(webView, valueCallback, fileChooserParams) : super.onShowFileChooser(webView, valueCallback, fileChooserParams);
    }

    public void openFileChooser(ValueCallback<Uri> valueCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        commonRefect(this.mDelegate, "openFileChooser", new Object[]{valueCallback}, ValueCallback.class);
    }

    public void openFileChooser(ValueCallback valueCallback, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        commonRefect(this.mDelegate, "openFileChooser", new Object[]{valueCallback, str}, ValueCallback.class, String.class);
    }

    public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        commonRefect(this.mDelegate, "openFileChooser", new Object[]{valueCallback, str, str2}, ValueCallback.class, String.class, String.class);
    }

    public void setDelegate(android.webkit.WebChromeClient webChromeClient) {
        this.mDelegate = webChromeClient;
    }
}
