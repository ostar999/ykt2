package com.alipay.sdk.auth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.alipay.sdk.authjs.a;
import com.alipay.sdk.util.l;
import com.just.agentweb.DefaultWebClient;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"SetJavaScriptEnabled", "DefaultLocale"})
/* loaded from: classes2.dex */
public class AuthActivity extends Activity {

    /* renamed from: a, reason: collision with root package name */
    static final String f3141a = "params";

    /* renamed from: b, reason: collision with root package name */
    static final String f3142b = "redirectUri";

    /* renamed from: c, reason: collision with root package name */
    private WebView f3143c;

    /* renamed from: d, reason: collision with root package name */
    private String f3144d;

    /* renamed from: e, reason: collision with root package name */
    private com.alipay.sdk.widget.a f3145e;

    /* renamed from: f, reason: collision with root package name */
    private Handler f3146f;

    /* renamed from: g, reason: collision with root package name */
    private boolean f3147g;

    /* renamed from: h, reason: collision with root package name */
    private boolean f3148h;

    /* renamed from: i, reason: collision with root package name */
    private Runnable f3149i = new d(this);

    public class a extends WebChromeClient {
        private a() {
        }

        @Override // android.webkit.WebChromeClient
        public final boolean onConsoleMessage(ConsoleMessage consoleMessage) throws JSONException {
            String strMessage = consoleMessage.message();
            if (TextUtils.isEmpty(strMessage)) {
                return super.onConsoleMessage(consoleMessage);
            }
            String strReplaceFirst = strMessage.startsWith("h5container.message: ") ? strMessage.replaceFirst("h5container.message: ", "") : null;
            if (TextUtils.isEmpty(strReplaceFirst)) {
                return super.onConsoleMessage(consoleMessage);
            }
            AuthActivity.b(AuthActivity.this, strReplaceFirst);
            return super.onConsoleMessage(consoleMessage);
        }

        public /* synthetic */ a(AuthActivity authActivity, byte b3) {
            this();
        }
    }

    public class b extends WebViewClient {
        private b() {
        }

        @Override // android.webkit.WebViewClient
        public final void onPageFinished(WebView webView, String str) {
            AuthActivity.g(AuthActivity.this);
            AuthActivity.this.f3146f.removeCallbacks(AuthActivity.this.f3149i);
        }

        @Override // android.webkit.WebViewClient
        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            AuthActivity.d(AuthActivity.this);
            AuthActivity.this.f3146f.postDelayed(AuthActivity.this.f3149i, 30000L);
            super.onPageStarted(webView, str, bitmap);
        }

        @Override // android.webkit.WebViewClient
        public final void onReceivedError(WebView webView, int i2, String str, String str2) {
            AuthActivity.a(AuthActivity.this);
            super.onReceivedError(webView, i2, str, str2);
        }

        @Override // android.webkit.WebViewClient
        public final void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            if (!AuthActivity.this.f3147g) {
                AuthActivity.this.runOnUiThread(new e(this, sslErrorHandler));
            } else {
                sslErrorHandler.proceed();
                AuthActivity.this.f3147g = false;
            }
        }

        @Override // android.webkit.WebViewClient
        public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (!str.toLowerCase().startsWith(com.alipay.sdk.cons.a.f3203i.toLowerCase()) && !str.toLowerCase().startsWith(com.alipay.sdk.cons.a.f3204j.toLowerCase())) {
                if (!AuthActivity.a(AuthActivity.this, str)) {
                    return super.shouldOverrideUrlLoading(webView, str);
                }
                webView.stopLoading();
                return true;
            }
            try {
                l.a aVarA = l.a(AuthActivity.this);
                if (aVarA != null && !aVarA.a()) {
                    if (str.startsWith("intent://platformapi/startapp")) {
                        str = str.replaceFirst(com.alipay.sdk.cons.a.f3204j, com.alipay.sdk.cons.a.f3203i);
                    }
                    AuthActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                }
            } catch (Throwable unused) {
            }
            return true;
        }

        public /* synthetic */ b(AuthActivity authActivity, byte b3) {
            this();
        }
    }

    public static /* synthetic */ boolean a(AuthActivity authActivity) {
        authActivity.f3148h = true;
        return true;
    }

    public static /* synthetic */ void d(AuthActivity authActivity) {
        try {
            if (authActivity.f3145e == null) {
                authActivity.f3145e = new com.alipay.sdk.widget.a(authActivity, com.alipay.sdk.widget.a.f3397a);
            }
            authActivity.f3145e.a();
        } catch (Exception unused) {
            authActivity.f3145e = null;
        }
    }

    public static /* synthetic */ void g(AuthActivity authActivity) {
        com.alipay.sdk.widget.a aVar = authActivity.f3145e;
        if (aVar != null) {
            aVar.b();
        }
        authActivity.f3145e = null;
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (!this.f3143c.canGoBack()) {
            h.a(this, this.f3144d + "?resultCode=150");
            finish();
            return;
        }
        if (this.f3148h) {
            h.a(this, this.f3144d + "?resultCode=150");
            finish();
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(bundle);
        try {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                finish();
                return;
            }
            try {
                this.f3144d = extras.getString(f3142b);
                String string = extras.getString("params");
                if (!l.b(string)) {
                    finish();
                    return;
                }
                super.requestWindowFeature(1);
                this.f3146f = new Handler(getMainLooper());
                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
                linearLayout.setOrientation(1);
                setContentView(linearLayout, layoutParams);
                WebView webView = new WebView(this);
                this.f3143c = webView;
                layoutParams.weight = 1.0f;
                byte b3 = 0;
                webView.setVisibility(0);
                linearLayout.addView(this.f3143c, layoutParams);
                WebSettings settings = this.f3143c.getSettings();
                settings.setUserAgentString(settings.getUserAgentString() + l.f(getApplicationContext()));
                settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
                settings.setSupportMultipleWindows(true);
                settings.setJavaScriptEnabled(true);
                settings.setSavePassword(false);
                settings.setJavaScriptCanOpenWindowsAutomatically(true);
                settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
                settings.setAllowFileAccess(false);
                settings.setTextSize(WebSettings.TextSize.NORMAL);
                this.f3143c.setVerticalScrollbarOverlay(true);
                this.f3143c.setWebViewClient(new b(this, b3));
                this.f3143c.setWebChromeClient(new a(this, b3));
                this.f3143c.setDownloadListener(new com.alipay.sdk.auth.a(this));
                this.f3143c.loadUrl(string);
                try {
                    Method method = this.f3143c.getSettings().getClass().getMethod("setDomStorageEnabled", Boolean.TYPE);
                    if (method != null) {
                        method.invoke(this.f3143c.getSettings(), Boolean.TRUE);
                    }
                } catch (Exception unused) {
                }
                try {
                    try {
                        this.f3143c.removeJavascriptInterface("searchBoxJavaBridge_");
                        this.f3143c.removeJavascriptInterface("accessibility");
                        this.f3143c.removeJavascriptInterface("accessibilityTraversal");
                    } catch (Throwable unused2) {
                        Method method2 = this.f3143c.getClass().getMethod("removeJavascriptInterface", new Class[0]);
                        if (method2 != null) {
                            method2.invoke(this.f3143c, "searchBoxJavaBridge_");
                            method2.invoke(this.f3143c, "accessibility");
                            method2.invoke(this.f3143c, "accessibilityTraversal");
                        }
                    }
                } catch (Throwable unused3) {
                }
                this.f3143c.getSettings().setCacheMode(1);
            } catch (Exception unused4) {
                finish();
            }
        } catch (Exception unused5) {
            finish();
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        WebView webView = this.f3143c;
        if (webView != null) {
            webView.removeAllViews();
            try {
                this.f3143c.destroy();
            } catch (Throwable unused) {
            }
            this.f3143c = null;
        }
    }

    private void b(String str) throws JSONException {
        com.alipay.sdk.authjs.d dVar = new com.alipay.sdk.authjs.d(getApplicationContext(), new com.alipay.sdk.auth.b(this));
        String str2 = null;
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(com.alipay.sdk.authjs.a.f3172e);
            try {
                if (TextUtils.isEmpty(string)) {
                    return;
                }
                JSONObject jSONObject2 = jSONObject.getJSONObject("param");
                JSONObject jSONObject3 = jSONObject2 instanceof JSONObject ? jSONObject2 : null;
                String string2 = jSONObject.getString(com.alipay.sdk.authjs.a.f3174g);
                String string3 = jSONObject.getString(com.alipay.sdk.authjs.a.f3171d);
                com.alipay.sdk.authjs.a aVar = new com.alipay.sdk.authjs.a("call");
                aVar.f3177j = string3;
                aVar.f3178k = string2;
                aVar.f3180m = jSONObject3;
                aVar.f3176i = string;
                dVar.a(aVar);
            } catch (Exception unused) {
                str2 = string;
                if (TextUtils.isEmpty(str2)) {
                    return;
                }
                try {
                    dVar.a(str2, a.EnumC0026a.f3185d);
                } catch (JSONException unused2) {
                }
            }
        } catch (Exception unused3) {
        }
    }

    private boolean a(String str) {
        if (TextUtils.isEmpty(str) || str.startsWith(DefaultWebClient.HTTP_SCHEME) || str.startsWith(DefaultWebClient.HTTPS_SCHEME)) {
            return false;
        }
        if (!"SDKLite://h5quit".equalsIgnoreCase(str)) {
            if (TextUtils.equals(str, this.f3144d)) {
                str = str + "?resultCode=150";
            }
            h.a(this, str);
        }
        finish();
        return true;
    }

    private void a(com.alipay.sdk.authjs.a aVar) throws JSONException {
        if (this.f3143c == null || aVar == null) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(com.alipay.sdk.authjs.a.f3172e, aVar.f3176i);
            jSONObject.put(com.alipay.sdk.authjs.a.f3174g, aVar.f3178k);
            jSONObject.put("param", aVar.f3180m);
            jSONObject.put(com.alipay.sdk.authjs.a.f3175h, aVar.f3179l);
            runOnUiThread(new c(this, String.format("AlipayJSBridge._invokeJS(%s)", jSONObject.toString())));
        } catch (JSONException unused) {
        }
    }

    private void b() {
        com.alipay.sdk.widget.a aVar = this.f3145e;
        if (aVar != null) {
            aVar.b();
        }
        this.f3145e = null;
    }

    private void a() {
        try {
            if (this.f3145e == null) {
                this.f3145e = new com.alipay.sdk.widget.a(this, com.alipay.sdk.widget.a.f3397a);
            }
            this.f3145e.a();
        } catch (Exception unused) {
            this.f3145e = null;
        }
    }

    public static /* synthetic */ void b(AuthActivity authActivity, String str) throws JSONException {
        com.alipay.sdk.authjs.d dVar = new com.alipay.sdk.authjs.d(authActivity.getApplicationContext(), new com.alipay.sdk.auth.b(authActivity));
        String str2 = null;
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(com.alipay.sdk.authjs.a.f3172e);
            try {
                if (TextUtils.isEmpty(string)) {
                    return;
                }
                JSONObject jSONObject2 = jSONObject.getJSONObject("param");
                JSONObject jSONObject3 = jSONObject2 instanceof JSONObject ? jSONObject2 : null;
                String string2 = jSONObject.getString(com.alipay.sdk.authjs.a.f3174g);
                String string3 = jSONObject.getString(com.alipay.sdk.authjs.a.f3171d);
                com.alipay.sdk.authjs.a aVar = new com.alipay.sdk.authjs.a("call");
                aVar.f3177j = string3;
                aVar.f3178k = string2;
                aVar.f3180m = jSONObject3;
                aVar.f3176i = string;
                dVar.a(aVar);
            } catch (Exception unused) {
                str2 = string;
                if (TextUtils.isEmpty(str2)) {
                    return;
                }
                try {
                    dVar.a(str2, a.EnumC0026a.f3185d);
                } catch (JSONException unused2) {
                }
            }
        } catch (Exception unused3) {
        }
    }

    public static /* synthetic */ boolean a(AuthActivity authActivity, String str) {
        if (TextUtils.isEmpty(str) || str.startsWith(DefaultWebClient.HTTP_SCHEME) || str.startsWith(DefaultWebClient.HTTPS_SCHEME)) {
            return false;
        }
        if (!"SDKLite://h5quit".equalsIgnoreCase(str)) {
            if (TextUtils.equals(str, authActivity.f3144d)) {
                str = str + "?resultCode=150";
            }
            h.a(authActivity, str);
        }
        authActivity.finish();
        return true;
    }

    public static /* synthetic */ void a(AuthActivity authActivity, com.alipay.sdk.authjs.a aVar) throws JSONException {
        if (authActivity.f3143c == null || aVar == null) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(com.alipay.sdk.authjs.a.f3172e, aVar.f3176i);
            jSONObject.put(com.alipay.sdk.authjs.a.f3174g, aVar.f3178k);
            jSONObject.put("param", aVar.f3180m);
            jSONObject.put(com.alipay.sdk.authjs.a.f3175h, aVar.f3179l);
            authActivity.runOnUiThread(new c(authActivity, String.format("AlipayJSBridge._invokeJS(%s)", jSONObject.toString())));
        } catch (JSONException unused) {
        }
    }
}
