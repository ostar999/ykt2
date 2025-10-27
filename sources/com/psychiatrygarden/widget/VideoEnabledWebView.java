package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.psychiatrygarden.interfaceclass.VideoEnabledWebChromeClient;
import java.util.Map;

/* loaded from: classes6.dex */
public class VideoEnabledWebView extends WebView {
    private boolean addedJavascriptInterface;
    private VideoEnabledWebChromeClient videoEnabledWebChromeClient;

    public interface ToggledFullscreenCallback {
        void toggledFullscreen(boolean fullscreen);
    }

    public VideoEnabledWebView(Context context) {
        super(context);
        this.addedJavascriptInterface = false;
    }

    private void addJavascriptInterface() {
        System.out.println(this.addedJavascriptInterface);
        if (this.addedJavascriptInterface) {
            return;
        }
        addJavascriptInterface(new Object() { // from class: com.psychiatrygarden.widget.VideoEnabledWebView.1
        }, "_VideoEnabledWebView");
        this.addedJavascriptInterface = true;
    }

    @Override // android.webkit.WebView
    public void loadData(String data, String mimeType, String encoding) {
        addJavascriptInterface();
        super.loadData(data, mimeType, encoding);
    }

    @Override // android.webkit.WebView
    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        addJavascriptInterface();
        super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    @Override // android.webkit.WebView
    public void loadUrl(String url) {
        addJavascriptInterface();
        super.loadUrl(url);
    }

    @Override // android.webkit.WebView
    @SuppressLint({"SetJavaScriptEnabled"})
    public void setWebChromeClient(WebChromeClient client) {
        getSettings().setJavaScriptEnabled(true);
        if (client instanceof VideoEnabledWebChromeClient) {
            this.videoEnabledWebChromeClient = (VideoEnabledWebChromeClient) client;
        }
        super.setWebChromeClient(client);
    }

    public VideoEnabledWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.addedJavascriptInterface = false;
    }

    @Override // android.webkit.WebView
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        addJavascriptInterface();
        super.loadUrl(url, additionalHttpHeaders);
    }

    public VideoEnabledWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.addedJavascriptInterface = false;
    }
}
