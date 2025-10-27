package com.easefun.polyv.livecommon.ui.widget.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;
import com.plv.thirdpart.blankj.utilcode.util.LogUtils;

/* loaded from: classes3.dex */
public class PLVWebViewHelper {

    public static class SafeWebChromeClient extends WebChromeClient {
        @Override // android.webkit.WebChromeClient
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }

        @Override // android.webkit.WebChromeClient
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override // android.webkit.WebChromeClient
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        @Override // android.webkit.WebChromeClient
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override // android.webkit.WebChromeClient
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override // android.webkit.WebChromeClient
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }

    public static class SafeWebViewClient extends WebViewClient {
        private boolean isUseActionView;

        @JavascriptInterface
        public void addImageClickListner(WebView webView) {
            webView.loadUrl("javascript:(function(){var objs = document.getElementsByTagName(\"img\"); for(var i=0;i<objs.length;i++)  {    objs[i].onclick=function()      {          window.imagelistner.openImage(this.src);      }  }})()");
        }

        @Override // android.webkit.WebViewClient
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            addImageClickListner(view);
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }

        @Override // android.webkit.WebViewClient
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
        }

        public SafeWebViewClient setIsUseActionView(boolean isUseActionView) {
            this.isUseActionView = isUseActionView;
            return this;
        }

        @Override // android.webkit.WebViewClient
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!this.isUseActionView) {
                try {
                    if (url.startsWith("weixin://")) {
                        ActivityUtils.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                        return true;
                    }
                    view.loadUrl(url);
                } catch (Exception unused) {
                    return true;
                }
            } else if (url.startsWith("yy://")) {
                view.loadUrl(url);
            } else {
                try {
                    ActivityUtils.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } catch (Exception e2) {
                    LogUtils.e(e2);
                }
            }
            return true;
        }
    }

    private static boolean hasKitkat() {
        return true;
    }

    private static void initListener(WebView webView, boolean isUseActionView) {
        webView.setWebViewClient(new SafeWebViewClient().setIsUseActionView(isUseActionView));
        webView.setWebChromeClient(new SafeWebChromeClient());
    }

    private static void initWebSettings(Context context, WebView webView) {
        WebSettings settings = webView.getSettings();
        if (settings == null) {
            return;
        }
        settings.setTextZoom(100);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setLoadsImagesAutomatically(hasKitkat());
        settings.setCacheMode(-1);
        if (!hasKitkat()) {
            settings.setDatabasePath(context.getDatabasePath("html").getPath());
        }
        settings.setSavePassword(false);
        settings.setSupportZoom(true);
        settings.setUserAgentString("");
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
    }

    public static void initWebView(Context context, WebView webView) {
        initWebView(context, webView, true);
    }

    public static void initWebView(Context context, WebView webView, boolean isUseActionView) {
        initWebSettings(context, webView);
        initListener(webView, isUseActionView);
    }
}
