package com.beizi.fusion.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.beizi.fusion.b.b;
import com.beizi.fusion.b.c;
import com.beizi.fusion.g.ar;
import com.beizi.fusion.g.h;
import com.beizi.fusion.g.z;
import com.beizi.fusion.model.TaskBean;
import java.util.List;

/* loaded from: classes2.dex */
public class LandingView extends WebView {

    /* renamed from: a, reason: collision with root package name */
    private static String f5384a = "LandingView";

    /* renamed from: b, reason: collision with root package name */
    private TaskBean.BackTaskArrayBean f5385b;

    /* renamed from: c, reason: collision with root package name */
    private int f5386c;

    /* renamed from: d, reason: collision with root package name */
    private Context f5387d;

    /* renamed from: e, reason: collision with root package name */
    @SuppressLint({"HandlerLeak"})
    private Handler f5388e;

    public LandingView(Context context) {
        this(context, null);
    }

    public void init() {
        WebSettings settings = getSettings();
        settings.setSavePassword(false);
        WebView.setWebContentsDebuggingEnabled(false);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setAllowFileAccess(false);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        setWebChromeClient(new WebChromeClient() { // from class: com.beizi.fusion.widget.LandingView.2
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView, int i2) {
            }
        });
        setWebViewClient(new WebViewClient() { // from class: com.beizi.fusion.widget.LandingView.3
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                h.b().e().execute(new Runnable() { // from class: com.beizi.fusion.widget.LandingView.3.1
                    @Override // java.lang.Runnable
                    public void run() throws InterruptedException {
                        List<String> report;
                        if (LandingView.this.f5385b == null || (report = LandingView.this.f5385b.getReport()) == null || report.size() <= 0) {
                            return;
                        }
                        for (int i2 = 0; i2 < report.size(); i2++) {
                            if (!TextUtils.isEmpty(report.get(i2))) {
                                if (z.a(ar.a(LandingView.this.f5387d, report.get(i2), null), LandingView.this.f5385b.getUserAgent()) != null) {
                                    c.a(LandingView.this.f5387d).b(new b(com.beizi.fusion.d.b.f4908b, "", "520.200", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
                                } else {
                                    c.a(LandingView.this.f5387d).b(new b(com.beizi.fusion.d.b.f4908b, "", "520.500", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
                                }
                                try {
                                    Thread.sleep(LandingView.this.f5385b.getSleepTime());
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                    }
                });
                LandingView.this.f5388e.sendEmptyMessageDelayed(1, LandingView.this.f5385b.getShowTime());
            }

            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                LandingView.this.a();
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
                super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
                LandingView.this.a();
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
                LandingView.this.a();
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return true;
            }
        });
    }

    public void load() {
        TaskBean.BackTaskArrayBean backTaskArrayBean = this.f5385b;
        if (backTaskArrayBean == null || TextUtils.isEmpty(backTaskArrayBean.getContentUrl())) {
            return;
        }
        loadUrl(this.f5385b.getContentUrl());
        this.f5386c--;
    }

    public LandingView(Context context, TaskBean.BackTaskArrayBean backTaskArrayBean) {
        this(context, null, 0);
        this.f5387d = context;
        this.f5385b = backTaskArrayBean;
        this.f5386c = backTaskArrayBean.getRepeatCount();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        c.a(this.f5387d).a(new b(com.beizi.fusion.d.b.f4908b, "", "510.500", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        try {
            c.a(this.f5387d).a(new b(com.beizi.fusion.d.b.f4908b, "", "510.200", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
            clearCache(true);
            clearHistory();
            clearFormData();
            destroy();
            Handler handler = this.f5388e;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public LandingView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f5386c = 1;
        this.f5388e = new Handler() { // from class: com.beizi.fusion.widget.LandingView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what != 1) {
                    return;
                }
                if (LandingView.this.f5386c > 0) {
                    LandingView.this.load();
                } else {
                    LandingView.this.b();
                }
            }
        };
        try {
            init();
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
