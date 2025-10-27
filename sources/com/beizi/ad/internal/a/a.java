package com.beizi.ad.internal.a;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.net.Uri;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.VideoView;
import com.beizi.ad.AdActivity;
import com.beizi.ad.R;
import com.beizi.ad.a.a.i;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.StringUtil;
import com.beizi.ad.internal.utilities.ViewUtil;
import com.beizi.ad.internal.view.AdViewImpl;
import com.beizi.ad.internal.view.h;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes2.dex */
public class a implements AdActivity.a {

    /* renamed from: a, reason: collision with root package name */
    public static LinkedList<WebView> f3988a = new LinkedList<>();

    /* renamed from: b, reason: collision with root package name */
    private Activity f3989b;

    /* renamed from: c, reason: collision with root package name */
    private WebView f3990c;

    /* renamed from: d, reason: collision with root package name */
    private ImageView f3991d;

    public a(Activity activity) {
        this.f3989b = activity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        Activity activity = this.f3989b;
        if (activity != null) {
            activity.finish();
        }
    }

    @Override // com.beizi.ad.AdActivity.a
    public void c() {
        WebView webView = this.f3990c;
        if (webView == null) {
            return;
        }
        ViewUtil.removeChildFromParent(webView);
        this.f3990c.destroy();
    }

    @Override // com.beizi.ad.AdActivity.a
    public void d() {
    }

    @Override // com.beizi.ad.AdActivity.a
    public void e() {
    }

    @Override // com.beizi.ad.AdActivity.a
    public WebView f() {
        return this.f3990c;
    }

    @Override // com.beizi.ad.AdActivity.a
    public void b() {
        i.a("lance", "...........................backPressed...........................");
        if (!this.f3990c.canGoBack()) {
            g();
        } else {
            this.f3990c.goBack();
            i.a("lance", " mWebView.goBack()");
        }
    }

    @Override // com.beizi.ad.AdActivity.a
    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    @TargetApi(17)
    public void a() {
        this.f3989b.setTheme(R.style.BeiZiTheme);
        this.f3989b.setContentView(R.layout.activity_in_app_browser);
        WebView webViewPoll = f3988a.poll();
        this.f3990c = webViewPoll;
        if (webViewPoll != null && webViewPoll.getSettings() != null) {
            if (this.f3990c.getContext() instanceof MutableContextWrapper) {
                ((MutableContextWrapper) this.f3990c.getContext()).setBaseContext(this.f3989b);
            }
            WebView webView = (WebView) this.f3989b.findViewById(R.id.web_view);
            webView.getSettings().setSavePassword(false);
            ViewGroup.LayoutParams layoutParams = webView.getLayoutParams();
            ViewGroup viewGroup = (ViewGroup) webView.getParent();
            int iIndexOfChild = viewGroup.indexOfChild(webView);
            viewGroup.removeView(webView);
            ViewUtil.removeChildFromParent(this.f3990c);
            this.f3990c.setLayoutParams(layoutParams);
            this.f3990c.getSettings().setUseWideViewPort(true);
            this.f3990c.getSettings().setLoadWithOverviewMode(true);
            WebView.setWebContentsDebuggingEnabled(false);
            viewGroup.addView(this.f3990c, iIndexOfChild);
            final ProgressBar progressBar = (ProgressBar) this.f3989b.findViewById(R.id.progress_bar);
            String stringExtra = this.f3989b.getIntent().getStringExtra("bridgeid");
            if (stringExtra != null) {
                Iterator<Pair<String, AdViewImpl.c>> it = AdViewImpl.c.f4566a.iterator();
                while (it.hasNext()) {
                    Pair<String, AdViewImpl.c> next = it.next();
                    if (((String) next.first).equals(stringExtra)) {
                        AdViewImpl.c.f4566a.remove(next);
                    }
                }
            }
            this.f3990c.setDownloadListener(new DownloadListener() { // from class: com.beizi.ad.internal.a.a.1
                @Override // android.webkit.DownloadListener
                public void onDownloadStart(String str, String str2, String str3, String str4, long j2) {
                    a.this.f3990c.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                    a.this.g();
                }
            });
            this.f3990c.setWebViewClient(new WebViewClient() { // from class: com.beizi.ad.internal.a.a.2
                @Override // android.webkit.WebViewClient
                public void onPageFinished(WebView webView2, String str) {
                    CookieSyncManager cookieSyncManager = CookieSyncManager.getInstance();
                    if (cookieSyncManager != null) {
                        cookieSyncManager.sync();
                    }
                }

                @Override // android.webkit.WebViewClient
                public boolean shouldOverrideUrlLoading(WebView webView2, String str) {
                    HaoboLog.v(HaoboLog.browserLogTag, HaoboLog.getString(R.string.opening_url, str));
                    if (str.startsWith("http")) {
                        return false;
                    }
                    a.this.a(str);
                    return true;
                }
            });
            this.f3990c.setWebChromeClient(new h(this.f3989b) { // from class: com.beizi.ad.internal.a.a.3
                @Override // com.beizi.ad.internal.view.a, android.webkit.WebChromeClient
                public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                    HaoboLog.w(HaoboLog.browserLogTag, HaoboLog.getString(R.string.console_message, consoleMessage.message(), consoleMessage.lineNumber(), consoleMessage.sourceId()));
                    return true;
                }

                @Override // com.beizi.ad.internal.view.a, android.webkit.WebChromeClient
                public boolean onJsAlert(WebView webView2, String str, String str2, JsResult jsResult) {
                    HaoboLog.w(HaoboLog.browserLogTag, HaoboLog.getString(R.string.js_alert, str2, str));
                    jsResult.confirm();
                    return true;
                }

                @Override // android.webkit.WebChromeClient
                public void onProgressChanged(WebView webView2, int i2) {
                    if (i2 < 100 && progressBar.getVisibility() == 8) {
                        progressBar.setVisibility(0);
                    }
                    progressBar.setProgress(i2);
                    if (i2 == 100) {
                        progressBar.setVisibility(8);
                    }
                }

                @Override // com.beizi.ad.internal.view.h, android.webkit.WebChromeClient
                public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
                    super.onShowCustomView(view, customViewCallback);
                    if (view instanceof FrameLayout) {
                        FrameLayout frameLayout = (FrameLayout) view;
                        if (frameLayout.getFocusedChild() instanceof VideoView) {
                            VideoView videoView = (VideoView) frameLayout.getFocusedChild();
                            frameLayout.removeView(videoView);
                            ((Activity) a.this.f3990c.getContext()).setContentView(videoView);
                            videoView.start();
                        }
                    }
                }
            });
            ImageView imageView = (ImageView) this.f3989b.findViewById(R.id.close_iv);
            this.f3991d = imageView;
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.a.a.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    a.this.g();
                }
            });
            return;
        }
        g();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        Uri uri = StringUtil.isEmpty(str) ? null : Uri.parse(str);
        if (uri == null) {
            HaoboLog.w(HaoboLog.browserLogTag, HaoboLog.getString(R.string.opening_url_failed, str));
            return;
        }
        Intent intent = new Intent("android.intent.action.VIEW", uri);
        intent.setFlags(268435456);
        try {
            this.f3989b.startActivity(intent);
            c();
            g();
        } catch (Exception unused) {
            HaoboLog.w(HaoboLog.browserLogTag, HaoboLog.getString(R.string.opening_url_failed, str));
        }
    }
}
