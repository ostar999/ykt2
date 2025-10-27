package com.psychiatrygarden.activity.chooseSchool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class AttachmentPreviewAct extends BaseActivity {
    private LinearLayout mLyLoadingView;
    private WebView mWebView;
    private String url;

    /* renamed from: com.psychiatrygarden.activity.chooseSchool.AttachmentPreviewAct$1, reason: invalid class name */
    public class AnonymousClass1 extends WebViewClient {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPageFinished$1() {
            try {
                AttachmentPreviewAct.this.updateUIAfterPageLoad();
            } catch (Exception e2) {
                Log.e(AttachmentPreviewAct.this.TAG, "页面加载完成处理异常", e2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPageStarted$0() {
            AttachmentPreviewAct.this.mLyLoadingView.setVisibility(0);
            AttachmentPreviewAct.this.mWebView.setVisibility(8);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceivedError$2() {
            AttachmentPreviewAct.this.mLyLoadingView.setVisibility(8);
            ToastUtil.shortToast(AttachmentPreviewAct.this, "页面加载失败，请检查网络");
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.g
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11291c.lambda$onPageFinished$1();
                }
            }, 100L);
            super.onPageFinished(view, url);
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            AttachmentPreviewAct.this.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.i
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11310c.lambda$onPageStarted$0();
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            AttachmentPreviewAct.this.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.h
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11300c.lambda$onReceivedError$2();
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private WebViewClient createOptimizedWebViewClient() {
        return new AnonymousClass1();
    }

    private void initWebView(String mUrl) {
        try {
            WebSettings settings = this.mWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);
            settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            settings.setCacheMode(2);
            this.mWebView.setLayerType(2, null);
            settings.setPluginState(WebSettings.PluginState.OFF);
            settings.setGeolocationEnabled(false);
            settings.setNeedInitialFocus(false);
            settings.setSupportZoom(false);
            settings.setBuiltInZoomControls(false);
            settings.setDisplayZoomControls(false);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            settings.setMixedContentMode(0);
            settings.setAllowFileAccess(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
            this.mWebView.removeJavascriptInterface("searchBoxJavaBredge_");
            if (SkinManager.getCurrentSkinType(this) == 1) {
                this.mWebView.setBackgroundColor(Color.parseColor("#121622"));
            } else {
                this.mWebView.setBackgroundColor(-1);
            }
            this.mWebView.setWebViewClient(createOptimizedWebViewClient());
            if (TextUtils.isEmpty(mUrl)) {
                return;
            }
            if (mUrl.endsWith(".pdf")) {
                this.mWebView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + Uri.encode(mUrl));
                return;
            }
            this.mWebView.loadUrl(NetworkRequestsURL.infoUrl + "?url=" + mUrl);
        } catch (Exception e2) {
            Log.e(this.TAG, "WebView初始化异常", e2);
            runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.d
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11235c.lambda$initWebView$2();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0() {
        initWebView(this.url);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1() {
        post(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.f
            @Override // java.lang.Runnable
            public final void run() {
                this.f11253c.lambda$init$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initWebView$2() {
        ToastUtil.shortToast(this, "页面加载异常，请重试");
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateUIAfterPageLoad$3() {
        this.mLyLoadingView.setVisibility(8);
        this.mWebView.setVisibility(0);
    }

    public static void newIntent(Context context, String name, String url) {
        Intent intent = new Intent(context, (Class<?>) AttachmentPreviewAct.class);
        intent.putExtra("name", name);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUIAfterPageLoad() {
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.c
            @Override // java.lang.Runnable
            public final void run() {
                this.f11225c.lambda$updateUIAfterPageLoad$3();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        String stringExtra = getIntent().getStringExtra("name");
        this.url = getIntent().getStringExtra("url");
        this.mLyLoadingView = (LinearLayout) findViewById(R.id.ly_progress);
        this.mWebView = (WebView) findViewById(R.id.webView);
        setTitle(stringExtra);
        new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.e
            @Override // java.lang.Runnable
            public final void run() {
                this.f11244c.lambda$init$1();
            }
        }).start();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_attachment_preview);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
