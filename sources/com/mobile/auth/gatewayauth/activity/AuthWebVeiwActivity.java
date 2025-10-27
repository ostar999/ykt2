package com.mobile.auth.gatewayauth.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.d;
import com.mobile.auth.gatewayauth.utils.k;
import com.mobile.auth.gatewayauth.utils.l;
import com.mobile.auth.o.a;
import com.nirvana.tools.core.AppUtils;
import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
public class AuthWebVeiwActivity extends Activity {

    /* renamed from: a, reason: collision with root package name */
    private WebView f9960a;

    /* renamed from: b, reason: collision with root package name */
    private String f9961b;

    /* renamed from: c, reason: collision with root package name */
    private String f9962c;

    /* renamed from: d, reason: collision with root package name */
    private ProgressBar f9963d;

    /* renamed from: e, reason: collision with root package name */
    private TextView f9964e;

    /* renamed from: f, reason: collision with root package name */
    private RelativeLayout f9965f;

    /* renamed from: g, reason: collision with root package name */
    private AuthUIConfig f9966g;

    /* renamed from: h, reason: collision with root package name */
    private ImageButton f9967h;

    private int a(Context context) {
        try {
            return context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("status_bar_height", "dimen", "android"));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public static /* synthetic */ ProgressBar a(AuthWebVeiwActivity authWebVeiwActivity) {
        try {
            return authWebVeiwActivity.f9963d;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static /* synthetic */ TextView b(AuthWebVeiwActivity authWebVeiwActivity) {
        try {
            return authWebVeiwActivity.f9964e;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static /* synthetic */ String c(AuthWebVeiwActivity authWebVeiwActivity) {
        try {
            return authWebVeiwActivity.f9962c;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static /* synthetic */ AuthUIConfig d(AuthWebVeiwActivity authWebVeiwActivity) {
        try {
            return authWebVeiwActivity.f9966g;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        AuthUIConfig authUIConfigR;
        AuthUIConfig authUIConfig;
        TextView textView;
        int navTextSize;
        try {
            this.f9961b = getIntent().getStringExtra("url");
            this.f9962c = getIntent().getStringExtra("name");
            int intExtra = getIntent().getIntExtra(Constant.LOGIN_ACTIVITY_UI_MANAGER_ID, 0);
            setRequestedOrientation(getIntent().getIntExtra("orientation", 1));
            super.onCreate(bundle);
            d dVarA = d.a(intExtra);
            if (dVarA == null) {
                a.a(getApplicationContext()).e("UIManager is null!|ID:", String.valueOf(intExtra));
                authUIConfigR = d.f9978a;
            } else {
                authUIConfigR = dVarA.r();
            }
            this.f9966g = authUIConfigR;
            if (dVarA.f() && Build.VERSION.SDK_INT >= 28) {
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
                attributes.layoutInDisplayCutoutMode = 1;
                getWindow().setAttributes(attributes);
            }
            setContentView(AppUtils.getResID(this, "authsdk_dialog_layout", TtmlNode.TAG_LAYOUT));
            int webViewStatusBarColor = this.f9966g.getWebViewStatusBarColor();
            if (AuthUIConfig.DEFAULT_WEB_STATUS_BAR_COLOR == webViewStatusBarColor) {
                webViewStatusBarColor = this.f9966g.getWebNavColor();
            }
            d.a(this.f9966g, webViewStatusBarColor, this);
            this.f9964e = (TextView) findViewById(AppUtils.getResID(this, "authsdk_title_tv", "id"));
            this.f9965f = (RelativeLayout) findViewById(AppUtils.getResID(this, "authsdk_title_rl", "id"));
            if (l.a(this.f9966g.getBottomNavBarColor())) {
                this.f9965f.setY(a((Context) this));
            }
            this.f9967h = (ImageButton) findViewById(AppUtils.getResID(this, "authsdk_back_btn", "id"));
            this.f9965f.setBackgroundColor(this.f9966g.getWebNavColor());
            this.f9964e.setTextColor(this.f9966g.getWebNavTextColor());
            if (this.f9966g.getWebNavTextSize() != -1) {
                authUIConfig = this.f9966g;
                textView = this.f9964e;
                navTextSize = authUIConfig.getWebNavTextSize();
            } else {
                authUIConfig = this.f9966g;
                textView = this.f9964e;
                navTextSize = authUIConfig.getNavTextSize();
            }
            authUIConfig.setTextSize(textView, navTextSize);
            this.f9967h.setBackgroundColor(0);
            this.f9967h.setScaleType(this.f9966g.getNavReturnScaleType());
            this.f9967h.setPadding(0, 0, 0, 0);
            Drawable webNavReturnImgDrawable = this.f9966g.getWebNavReturnImgDrawable();
            if (webNavReturnImgDrawable == null) {
                webNavReturnImgDrawable = k.c(this, this.f9966g.getWebNavReturnImgPath());
            }
            if (webNavReturnImgDrawable == null) {
                webNavReturnImgDrawable = this.f9966g.getNavReturnImgDrawable();
            }
            if (webNavReturnImgDrawable == null) {
                webNavReturnImgDrawable = k.a(this, this.f9966g.getNavReturnImgPath(), "authsdk_return_bg");
            }
            this.f9967h.setImageDrawable(webNavReturnImgDrawable);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.f9967h.getLayoutParams();
            layoutParams.width = AppUtils.dp2px(this, this.f9966g.getNavReturnImgWidth());
            layoutParams.height = AppUtils.dp2px(this, this.f9966g.getNavReturnImgHeight());
            this.f9967h.setOnClickListener(new View.OnClickListener() { // from class: com.mobile.auth.gatewayauth.activity.AuthWebVeiwActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    try {
                        AuthWebVeiwActivity.this.finish();
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
            if (dVarA.b()) {
                getWindow().getDecorView().setSystemUiVisibility(R2.attr.searchHintIcon);
                if (!this.f9966g.isStatusBarHidden()) {
                    this.f9965f.setY(a((Context) this));
                }
            }
            this.f9963d = (ProgressBar) findViewById(AppUtils.getResID(this, "authsdk_progressBar", "id"));
            if (this.f9966g.isWebHiddeProgress()) {
                this.f9963d.setVisibility(8);
            } else {
                this.f9963d.setVisibility(0);
            }
            this.f9960a = (WebView) findViewById(AppUtils.getResID(this, "authsdk_webview", "id"));
            if (l.a(this.f9966g.getBottomNavBarColor())) {
                this.f9960a.setY(this.f9965f.getHeight() + this.f9965f.getY());
            }
            if (dVarA.b() && !this.f9966g.isStatusBarHidden()) {
                this.f9960a.setY(this.f9965f.getHeight() + a((Context) this));
            }
            this.f9960a.setWebChromeClient(new WebChromeClient() { // from class: com.mobile.auth.gatewayauth.activity.AuthWebVeiwActivity.2
                @Override // android.webkit.WebChromeClient
                public void onProgressChanged(WebView webView, int i2) {
                    TextView textViewB;
                    String strC;
                    try {
                        if (i2 != 100) {
                            if (AuthWebVeiwActivity.d(AuthWebVeiwActivity.this).isWebHiddeProgress()) {
                                AuthWebVeiwActivity.a(AuthWebVeiwActivity.this).setVisibility(8);
                                return;
                            } else {
                                AuthWebVeiwActivity.a(AuthWebVeiwActivity.this).setVisibility(0);
                                AuthWebVeiwActivity.a(AuthWebVeiwActivity.this).setProgress(i2);
                                return;
                            }
                        }
                        AuthWebVeiwActivity.a(AuthWebVeiwActivity.this).setVisibility(8);
                        String title = webView.getTitle();
                        if (!TextUtils.isEmpty(title)) {
                            AuthWebVeiwActivity.b(AuthWebVeiwActivity.this).setText(title);
                            return;
                        }
                        if (TextUtils.isEmpty(AuthWebVeiwActivity.c(AuthWebVeiwActivity.this))) {
                            textViewB = AuthWebVeiwActivity.b(AuthWebVeiwActivity.this);
                            strC = "服务协议";
                        } else {
                            textViewB = AuthWebVeiwActivity.b(AuthWebVeiwActivity.this);
                            strC = AuthWebVeiwActivity.c(AuthWebVeiwActivity.this);
                        }
                        textViewB.setText(strC);
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
            this.f9960a.setWebViewClient(new WebViewClient() { // from class: com.mobile.auth.gatewayauth.activity.AuthWebVeiwActivity.3
                @Override // android.webkit.WebViewClient
                public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                    return false;
                }
            });
            this.f9960a.setVerticalScrollBarEnabled(false);
            this.f9960a.setHorizontalScrollBarEnabled(false);
            WebSettings settings = this.f9960a.getSettings();
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setDomStorageEnabled(true);
            settings.setSavePassword(false);
            settings.setAllowFileAccess(false);
            settings.setJavaScriptEnabled(this.f9966g.isWebSupportedJavascript());
            this.f9960a.setVerticalScrollbarOverlay(false);
            settings.setUseWideViewPort(true);
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(false);
            settings.setSupportZoom(true);
            settings.setCacheMode(this.f9966g.getWebCacheMode());
            this.f9960a.loadUrl(this.f9961b);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        try {
            WebView webView = this.f9960a;
            if (webView != null) {
                webView.removeAllViews();
                this.f9960a.destroy();
                this.f9960a = null;
            }
            super.onDestroy();
            this.f9966g = null;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
