package com.cmic.sso.sdk.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/* loaded from: classes3.dex */
public class h extends Dialog {

    /* renamed from: a, reason: collision with root package name */
    private WebView f6520a;

    /* renamed from: b, reason: collision with root package name */
    private String f6521b;

    /* renamed from: c, reason: collision with root package name */
    private String f6522c;

    /* renamed from: d, reason: collision with root package name */
    private LinearLayout f6523d;

    public h(Context context, int i2, String str, String str2) {
        super(context, i2);
        try {
            this.f6522c = str;
            this.f6521b = str2;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private ViewGroup c() {
        View viewFindViewById;
        try {
            LinearLayout linearLayout = new LinearLayout(getContext());
            this.f6523d = linearLayout;
            linearLayout.setOrientation(1);
            this.f6523d.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            a aVarA = com.mobile.auth.e.a.a(getContext()).a();
            int iE = aVarA.e();
            String str = TextUtils.isEmpty(this.f6522c) ? com.cmic.sso.sdk.c.f6389d[aVarA.ap()] : this.f6522c;
            if (iE != -1) {
                RelativeLayout relativeLayoutA = i.a(getContext(), getLayoutInflater().inflate(iE, (ViewGroup) this.f6523d, false), 1118481, 0, str, (View.OnClickListener) null);
                String strF = aVarA.f();
                if (!TextUtils.isEmpty(strF) && (viewFindViewById = relativeLayoutA.findViewById(g.a(getContext(), strF))) != null) {
                    viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.cmic.sso.sdk.view.h.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            h.this.f6520a.stopLoading();
                            h.this.b();
                        }
                    });
                }
                this.f6523d.addView(relativeLayoutA);
            } else {
                this.f6523d.addView(i.a(getContext(), (View) null, 1118481, 2236962, str, new View.OnClickListener() { // from class: com.cmic.sso.sdk.view.h.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        h.this.f6520a.stopLoading();
                        h.this.b();
                    }
                }));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return this.f6523d;
    }

    private void d() {
        WebView webView = new WebView(getContext());
        this.f6520a = webView;
        WebSettings settings = webView.getSettings();
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setSavePassword(false);
        settings.setJavaScriptEnabled(true);
        this.f6523d.addView(this.f6520a, new LinearLayout.LayoutParams(-1, -1));
        this.f6520a.setWebViewClient(new WebViewClient() { // from class: com.cmic.sso.sdk.view.h.3
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView2, String str) {
                h.this.f6520a.loadUrl(str);
                return true;
            }
        });
        this.f6520a.loadUrl(this.f6521b);
    }

    public void a() {
        View decorView;
        requestWindowFeature(1);
        int i2 = 0;
        getWindow().setFeatureDrawableAlpha(0, 0);
        a aVarA = com.mobile.auth.e.a.a(getContext()).a();
        if (aVarA.a() != 0) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().clearFlags(67108864);
            getWindow().setStatusBarColor(aVarA.a());
            getWindow().setNavigationBarColor(aVarA.a());
        }
        if (aVarA.b()) {
            decorView = getWindow().getDecorView();
            i2 = 8192;
        } else {
            decorView = getWindow().getDecorView();
        }
        decorView.setSystemUiVisibility(i2);
        setContentView(c());
    }

    public void b() {
        if (this.f6520a.canGoBack()) {
            this.f6520a.goBack();
        } else {
            dismiss();
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        WebView webView = this.f6520a;
        if (webView != null) {
            webView.stopLoading();
        }
    }

    @Override // android.app.Dialog
    public void show() {
        if (this.f6523d == null) {
            a();
        }
        if (this.f6520a == null) {
            d();
        }
        super.show();
    }
}
