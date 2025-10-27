package com.tencent.smtt.sdk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import com.tencent.smtt.export.external.interfaces.ClientCertRequest;
import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.IX5WebViewClient;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.export.external.proxy.X5ProxyWebViewClient;
import com.tencent.smtt.utils.TbsLog;

/* loaded from: classes6.dex */
class j extends X5ProxyWebViewClient {

    /* renamed from: c, reason: collision with root package name */
    private static String f21204c;

    /* renamed from: a, reason: collision with root package name */
    private WebViewClient f21205a;

    /* renamed from: b, reason: collision with root package name */
    private WebView f21206b;

    public j(IX5WebViewClient iX5WebViewClient, WebView webView, WebViewClient webViewClient) {
        super(iX5WebViewClient);
        this.f21206b = webView;
        this.f21205a = webViewClient;
        webViewClient.mX5Client = this;
    }

    public void a(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(this.f21206b.b(), 0, 0, str, bitmap);
    }

    public void a(String str) {
        Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(str));
        intent.addFlags(268435456);
        try {
            if (this.f21206b.getContext() != null) {
                this.f21206b.getContext().startActivity(intent);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient
    public void countPVContentCacheCallBack(String str) {
        this.f21206b.f21067a++;
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void doUpdateVisitedHistory(IX5WebViewBase iX5WebViewBase, String str, boolean z2) {
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.doUpdateVisitedHistory(this.f21206b, str, z2);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onDetectedBlankScreen(IX5WebViewBase iX5WebViewBase, String str, int i2) {
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.onDetectedBlankScreen(str, i2);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onFormResubmission(IX5WebViewBase iX5WebViewBase, Message message, Message message2) {
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.onFormResubmission(this.f21206b, message, message2);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onLoadResource(IX5WebViewBase iX5WebViewBase, String str) {
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.onLoadResource(this.f21206b, str);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onPageCommitVisible(IX5WebViewBase iX5WebViewBase, String str) {
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.onPageCommitVisible(this.f21206b, str);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onPageFinished(IX5WebViewBase iX5WebViewBase, int i2, int i3, String str) throws Throwable {
        com.tencent.smtt.utils.p pVarA;
        TbsPrivacyAccess.rmPrivacyItemIfNeeded(iX5WebViewBase.getView().getContext().getApplicationContext());
        if (f21204c == null && (pVarA = com.tencent.smtt.utils.p.a()) != null) {
            pVarA.a(false);
            f21204c = Boolean.toString(false);
        }
        this.f21206b.a(iX5WebViewBase);
        this.f21206b.f21067a++;
        this.f21205a.onPageFinished(this.f21206b, str);
        if ("com.qzone".equals(iX5WebViewBase.getView().getContext().getApplicationInfo().packageName)) {
            this.f21206b.a(iX5WebViewBase.getView().getContext());
        }
        TbsLog.app_extra("SmttWebViewClient", iX5WebViewBase.getView().getContext());
        try {
            super.onPageFinished(iX5WebViewBase, i2, i3, str);
        } catch (Exception unused) {
        }
        WebView.c();
        if (!TbsShareManager.mHasQueryed && this.f21206b.getContext() != null && TbsShareManager.isThirdPartyApp(this.f21206b.getContext())) {
            TbsShareManager.mHasQueryed = true;
            new Thread(new Runnable() { // from class: com.tencent.smtt.sdk.j.1
                @Override // java.lang.Runnable
                public void run() {
                    TbsDownloader.needDownload(j.this.f21206b.getContext(), false);
                }
            }).start();
        }
        if (this.f21206b.getContext() == null || TbsLogReport.getInstance(this.f21206b.getContext()).getShouldUploadEventReport()) {
            return;
        }
        TbsLogReport.getInstance(this.f21206b.getContext()).setShouldUploadEventReport(true);
        TbsLogReport.getInstance(this.f21206b.getContext()).dailyReport();
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onPageFinished(IX5WebViewBase iX5WebViewBase, String str) throws Throwable {
        onPageFinished(iX5WebViewBase, 0, 0, str);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onPageStarted(IX5WebViewBase iX5WebViewBase, int i2, int i3, String str, Bitmap bitmap) {
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.onPageStarted(this.f21206b, str, bitmap);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onPageStarted(IX5WebViewBase iX5WebViewBase, String str, Bitmap bitmap) {
        onPageStarted(iX5WebViewBase, 0, 0, str, bitmap);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onReceivedClientCertRequest(IX5WebViewBase iX5WebViewBase, ClientCertRequest clientCertRequest) {
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.onReceivedClientCertRequest(this.f21206b, clientCertRequest);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onReceivedError(IX5WebViewBase iX5WebViewBase, int i2, String str, String str2) {
        if (i2 < -15) {
            if (i2 != -17) {
                return;
            } else {
                i2 = -1;
            }
        }
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.onReceivedError(this.f21206b, i2, str, str2);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onReceivedError(IX5WebViewBase iX5WebViewBase, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.onReceivedError(this.f21206b, webResourceRequest, webResourceError);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onReceivedHttpAuthRequest(IX5WebViewBase iX5WebViewBase, HttpAuthHandler httpAuthHandler, String str, String str2) {
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.onReceivedHttpAuthRequest(this.f21206b, httpAuthHandler, str, str2);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onReceivedHttpError(IX5WebViewBase iX5WebViewBase, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.onReceivedHttpError(this.f21206b, webResourceRequest, webResourceResponse);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onReceivedLoginRequest(IX5WebViewBase iX5WebViewBase, String str, String str2, String str3) {
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.onReceivedLoginRequest(this.f21206b, str, str2, str3);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onReceivedSslError(IX5WebViewBase iX5WebViewBase, SslErrorHandler sslErrorHandler, SslError sslError) {
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.onReceivedSslError(this.f21206b, sslErrorHandler, sslError);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onScaleChanged(IX5WebViewBase iX5WebViewBase, float f2, float f3) {
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.onScaleChanged(this.f21206b, f2, f3);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onTooManyRedirects(IX5WebViewBase iX5WebViewBase, Message message, Message message2) {
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.onTooManyRedirects(this.f21206b, message, message2);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public void onUnhandledKeyEvent(IX5WebViewBase iX5WebViewBase, KeyEvent keyEvent) {
        this.f21206b.a(iX5WebViewBase);
        this.f21205a.onUnhandledKeyEvent(this.f21206b, keyEvent);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public WebResourceResponse shouldInterceptRequest(IX5WebViewBase iX5WebViewBase, WebResourceRequest webResourceRequest) {
        this.f21206b.a(iX5WebViewBase);
        return this.f21205a.shouldInterceptRequest(this.f21206b, webResourceRequest);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public WebResourceResponse shouldInterceptRequest(IX5WebViewBase iX5WebViewBase, WebResourceRequest webResourceRequest, Bundle bundle) {
        this.f21206b.a(iX5WebViewBase);
        return this.f21205a.shouldInterceptRequest(this.f21206b, webResourceRequest, bundle);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public WebResourceResponse shouldInterceptRequest(IX5WebViewBase iX5WebViewBase, String str) {
        this.f21206b.a(iX5WebViewBase);
        return this.f21205a.shouldInterceptRequest(this.f21206b, str);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public boolean shouldOverrideKeyEvent(IX5WebViewBase iX5WebViewBase, KeyEvent keyEvent) {
        this.f21206b.a(iX5WebViewBase);
        return this.f21205a.shouldOverrideKeyEvent(this.f21206b, keyEvent);
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public boolean shouldOverrideUrlLoading(IX5WebViewBase iX5WebViewBase, WebResourceRequest webResourceRequest) {
        String string = (webResourceRequest == null || webResourceRequest.getUrl() == null) ? null : webResourceRequest.getUrl().toString();
        if (string == null || this.f21206b.showDebugView(string)) {
            return true;
        }
        this.f21206b.a(iX5WebViewBase);
        boolean zShouldOverrideUrlLoading = this.f21205a.shouldOverrideUrlLoading(this.f21206b, webResourceRequest);
        if (!zShouldOverrideUrlLoading) {
            if (string.startsWith("wtai://wp/mc;")) {
                this.f21206b.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(WebView.SCHEME_TEL + string.substring(13))));
                return true;
            }
            if (string.startsWith(WebView.SCHEME_TEL)) {
                a(string);
                return true;
            }
        }
        return zShouldOverrideUrlLoading;
    }

    @Override // com.tencent.smtt.export.external.proxy.ProxyWebViewClient, com.tencent.smtt.export.external.interfaces.IX5WebViewClient
    public boolean shouldOverrideUrlLoading(IX5WebViewBase iX5WebViewBase, String str) {
        if (str == null || this.f21206b.showDebugView(str)) {
            return true;
        }
        this.f21206b.a(iX5WebViewBase);
        boolean zShouldOverrideUrlLoading = this.f21205a.shouldOverrideUrlLoading(this.f21206b, str);
        if (!zShouldOverrideUrlLoading) {
            if (str.startsWith("wtai://wp/mc;")) {
                this.f21206b.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(WebView.SCHEME_TEL + str.substring(13))));
                return true;
            }
            if (str.startsWith(WebView.SCHEME_TEL)) {
                a(str);
                return true;
            }
        }
        return zShouldOverrideUrlLoading;
    }
}
