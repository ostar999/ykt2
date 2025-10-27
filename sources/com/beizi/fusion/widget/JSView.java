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
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.RequiresApi;
import com.beizi.fusion.b.b;
import com.beizi.fusion.b.c;
import com.beizi.fusion.g.ar;
import com.beizi.fusion.g.h;
import com.beizi.fusion.g.z;
import com.beizi.fusion.model.TaskBean;
import com.github.lzyzsd.jsbridge.BridgeUtil;
import java.util.List;

/* loaded from: classes2.dex */
public class JSView extends WebView {

    /* renamed from: a, reason: collision with root package name */
    private static String f5374a = "JSView";

    /* renamed from: b, reason: collision with root package name */
    private TaskBean.BackTaskArrayBean f5375b;

    /* renamed from: c, reason: collision with root package name */
    private int f5376c;

    /* renamed from: d, reason: collision with root package name */
    private Context f5377d;

    /* renamed from: e, reason: collision with root package name */
    @SuppressLint({"HandlerLeak"})
    private Handler f5378e;

    public JSView(Context context) {
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
        setWebChromeClient(new WebChromeClient() { // from class: com.beizi.fusion.widget.JSView.2
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView, int i2) {
            }
        });
        setWebViewClient(new WebViewClient() { // from class: com.beizi.fusion.widget.JSView.3
            @Override // android.webkit.WebViewClient
            @RequiresApi(api = 19)
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                List<String> action = JSView.this.f5375b.getAction();
                if (action != null && action.size() > 0) {
                    for (int i2 = 0; i2 < action.size(); i2++) {
                        if (!TextUtils.isEmpty(action.get(i2))) {
                            JSView.this.evaluateJavascript(BridgeUtil.JAVASCRIPT_STR + action.get(i2) + "()", new ValueCallback<String>() { // from class: com.beizi.fusion.widget.JSView.3.1
                                @Override // android.webkit.ValueCallback
                                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                                public void onReceiveValue(String str2) {
                                }
                            });
                        }
                    }
                }
                h.b().e().execute(new Runnable() { // from class: com.beizi.fusion.widget.JSView.3.2
                    @Override // java.lang.Runnable
                    public void run() throws InterruptedException {
                        List<String> report;
                        if (JSView.this.f5375b == null || (report = JSView.this.f5375b.getReport()) == null || report.size() <= 0) {
                            return;
                        }
                        for (int i3 = 0; i3 < report.size(); i3++) {
                            if (!TextUtils.isEmpty(report.get(i3))) {
                                if (z.a(ar.a(JSView.this.f5377d, report.get(i3), null), JSView.this.f5375b.getUserAgent()) != null) {
                                    c.a(JSView.this.f5377d).b(new b(com.beizi.fusion.d.b.f4908b, "", "520.200", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
                                } else {
                                    c.a(JSView.this.f5377d).b(new b(com.beizi.fusion.d.b.f4908b, "", "520.500", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
                                }
                                try {
                                    Thread.sleep(JSView.this.f5375b.getSleepTime());
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                    }
                });
                JSView.this.f5378e.sendEmptyMessageDelayed(1, JSView.this.f5375b.getShowTime());
            }

            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                JSView.this.a();
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
                super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
                JSView.this.a();
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
                JSView.this.a();
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return true;
            }
        });
    }

    public void load() {
        TaskBean.BackTaskArrayBean backTaskArrayBean = this.f5375b;
        if (backTaskArrayBean == null || TextUtils.isEmpty(backTaskArrayBean.getContentUrl())) {
            return;
        }
        loadUrl(this.f5375b.getContentUrl());
        this.f5376c--;
    }

    public JSView(Context context, TaskBean.BackTaskArrayBean backTaskArrayBean) {
        this(context, null, 0);
        this.f5377d = context;
        this.f5375b = backTaskArrayBean;
        this.f5376c = backTaskArrayBean.getRepeatCount();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        c.a(this.f5377d).a(new b(com.beizi.fusion.d.b.f4908b, "", "510.500", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        try {
            c.a(this.f5377d).a(new b(com.beizi.fusion.d.b.f4908b, "", "510.200", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
            clearCache(true);
            clearHistory();
            clearFormData();
            destroy();
            Handler handler = this.f5378e;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public JSView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f5376c = 1;
        this.f5378e = new Handler() { // from class: com.beizi.fusion.widget.JSView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what != 1) {
                    return;
                }
                if (JSView.this.f5376c > 0) {
                    JSView.this.load();
                } else {
                    JSView.this.b();
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
