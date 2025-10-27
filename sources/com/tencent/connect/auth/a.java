package com.tencent.connect.auth;

import android.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.azhon.appupdate.config.Constant;
import com.github.lzyzsd.jsbridge.BridgeUtil;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.tencent.connect.auth.b;
import com.tencent.connect.common.Constants;
import com.tencent.open.b.h;
import com.tencent.open.log.SLog;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.i;
import com.tencent.open.utils.k;
import com.tencent.open.web.security.JniInterface;
import com.tencent.open.web.security.SecureJsInterface;
import com.tencent.tauth.DefaultUiListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class a extends Dialog {

    /* renamed from: a, reason: collision with root package name */
    private String f17984a;

    /* renamed from: b, reason: collision with root package name */
    private b f17985b;

    /* renamed from: c, reason: collision with root package name */
    private IUiListener f17986c;

    /* renamed from: d, reason: collision with root package name */
    private Handler f17987d;

    /* renamed from: e, reason: collision with root package name */
    private FrameLayout f17988e;

    /* renamed from: f, reason: collision with root package name */
    private LinearLayout f17989f;

    /* renamed from: g, reason: collision with root package name */
    private FrameLayout f17990g;

    /* renamed from: h, reason: collision with root package name */
    private ProgressBar f17991h;

    /* renamed from: i, reason: collision with root package name */
    private String f17992i;

    /* renamed from: j, reason: collision with root package name */
    private com.tencent.open.c.c f17993j;

    /* renamed from: k, reason: collision with root package name */
    private Context f17994k;

    /* renamed from: l, reason: collision with root package name */
    private com.tencent.open.web.security.b f17995l;

    /* renamed from: m, reason: collision with root package name */
    private boolean f17996m;

    /* renamed from: n, reason: collision with root package name */
    private int f17997n;

    /* renamed from: o, reason: collision with root package name */
    private String f17998o;

    /* renamed from: p, reason: collision with root package name */
    private String f17999p;

    /* renamed from: q, reason: collision with root package name */
    private long f18000q;

    /* renamed from: r, reason: collision with root package name */
    private long f18001r;

    /* renamed from: s, reason: collision with root package name */
    private HashMap<String, Runnable> f18002s;

    /* renamed from: com.tencent.connect.auth.a$a, reason: collision with other inner class name */
    public class C0320a extends WebViewClient {
        private C0320a() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            SLog.v("openSDK_LOG.AuthDialog", "-->onPageFinished, url: " + str);
            a.this.f17990g.setVisibility(8);
            if (a.this.f17993j != null) {
                a.this.f17993j.setVisibility(0);
            }
            if (TextUtils.isEmpty(str)) {
                return;
            }
            a.this.f17987d.removeCallbacks((Runnable) a.this.f18002s.remove(str));
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            SLog.v("openSDK_LOG.AuthDialog", "-->onPageStarted, url: " + str);
            super.onPageStarted(webView, str, bitmap);
            a.this.f17990g.setVisibility(0);
            a.this.f18000q = SystemClock.elapsedRealtime();
            if (!TextUtils.isEmpty(a.this.f17998o)) {
                a.this.f17987d.removeCallbacks((Runnable) a.this.f18002s.remove(a.this.f17998o));
            }
            a.this.f17998o = str;
            a aVar = a.this;
            d dVar = aVar.new d(aVar.f17998o);
            a.this.f18002s.put(str, dVar);
            a.this.f17987d.postDelayed(dVar, 120000L);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int i2, String str, String str2) {
            super.onReceivedError(webView, i2, str, str2);
            SLog.i("openSDK_LOG.AuthDialog", "-->onReceivedError, errorCode: " + i2 + " | description: " + str);
            if (!k.b(a.this.f17994k)) {
                a.this.f17985b.onError(new UiError(9001, "当前网络不可用，请稍后重试！", str2));
                a.this.dismiss();
                return;
            }
            if (a.this.f17998o.startsWith("https://imgcache.qq.com/ptlogin/static/qzsjump.html?")) {
                a.this.f17985b.onError(new UiError(i2, str, str2));
                a.this.dismiss();
                return;
            }
            long jElapsedRealtime = SystemClock.elapsedRealtime() - a.this.f18000q;
            if (a.this.f17997n >= 1 || jElapsedRealtime >= a.this.f18001r) {
                a.this.f17993j.loadUrl(a.this.a());
            } else {
                a.m(a.this);
                a.this.f17987d.postDelayed(new Runnable() { // from class: com.tencent.connect.auth.a.a.1
                    @Override // java.lang.Runnable
                    public void run() {
                        a.this.f17993j.loadUrl(a.this.f17998o);
                    }
                }, 500L);
            }
        }

        @Override // android.webkit.WebViewClient
        @TargetApi(8)
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            SLog.e("openSDK_LOG.AuthDialog", "-->onReceivedSslError " + sslError.getPrimaryError() + "请求不合法，请检查手机安全设置，如系统时间、代理等");
            sslErrorHandler.cancel();
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) throws JSONException {
            List<String> pathSegments;
            SLog.v("openSDK_LOG.AuthDialog", "-->Redirect URL: " + str);
            if (str.startsWith("auth://browser")) {
                JSONObject jSONObjectC = k.c(str);
                a aVar = a.this;
                aVar.f17996m = aVar.e();
                if (!a.this.f17996m) {
                    if (jSONObjectC.optString("fail_cb", null) != null) {
                        a.this.a(jSONObjectC.optString("fail_cb"), "");
                    } else if (jSONObjectC.optInt("fall_to_wv") == 1) {
                        a aVar2 = a.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append(a.this.f17984a);
                        sb.append(a.this.f17984a.indexOf("?") > -1 ? "&" : "?");
                        aVar2.f17984a = sb.toString();
                        a.this.f17984a = a.this.f17984a + "browser_error=1";
                        a.this.f17993j.loadUrl(a.this.f17984a);
                    } else {
                        String strOptString = jSONObjectC.optString("redir", null);
                        if (strOptString != null) {
                            a.this.f17993j.loadUrl(strOptString);
                        }
                    }
                }
                return true;
            }
            if (str.startsWith("auth://tauth.qq.com/")) {
                a.this.f17985b.onComplete(k.c(str));
                a.this.dismiss();
                return true;
            }
            if (str.startsWith(Constants.CANCEL_URI)) {
                a.this.f17985b.onCancel();
                a.this.dismiss();
                return true;
            }
            if (str.startsWith(Constants.CLOSE_URI)) {
                a.this.dismiss();
                return true;
            }
            if (str.startsWith(Constants.DOWNLOAD_URI) || str.endsWith(Constant.APK_SUFFIX)) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW", str.startsWith(Constants.DOWNLOAD_URI) ? Uri.parse(Uri.decode(str.substring(11))) : Uri.parse(Uri.decode(str)));
                    intent.addFlags(268435456);
                    a.this.f17994k.startActivity(intent);
                } catch (Exception e2) {
                    SLog.e("openSDK_LOG.AuthDialog", "-->start download activity exception, e: ", e2);
                }
                return true;
            }
            if (!str.startsWith("auth://progress")) {
                if (str.startsWith("auth://onLoginSubmit")) {
                    try {
                        List<String> pathSegments2 = Uri.parse(str).getPathSegments();
                        if (!pathSegments2.isEmpty()) {
                            a.this.f17999p = pathSegments2.get(0);
                        }
                    } catch (Exception unused) {
                    }
                    return true;
                }
                if (a.this.f17995l.a(a.this.f17993j, str)) {
                    return true;
                }
                SLog.i("openSDK_LOG.AuthDialog", "-->Redirect URL: return false");
                return false;
            }
            try {
                pathSegments = Uri.parse(str).getPathSegments();
            } catch (Exception unused2) {
            }
            if (pathSegments.isEmpty()) {
                return true;
            }
            int iIntValue = Integer.valueOf(pathSegments.get(0)).intValue();
            if (iIntValue == 0) {
                a.this.f17990g.setVisibility(8);
                a.this.f17993j.setVisibility(0);
            } else if (iIntValue == 1) {
                a.this.f17990g.setVisibility(0);
            }
            return true;
        }
    }

    public class b extends DefaultUiListener {

        /* renamed from: a, reason: collision with root package name */
        String f18009a;

        /* renamed from: b, reason: collision with root package name */
        String f18010b;

        /* renamed from: d, reason: collision with root package name */
        private String f18012d;

        /* renamed from: e, reason: collision with root package name */
        private IUiListener f18013e;

        public b(String str, String str2, String str3, IUiListener iUiListener) {
            this.f18012d = str;
            this.f18009a = str2;
            this.f18010b = str3;
            this.f18013e = iUiListener;
        }

        @Override // com.tencent.tauth.DefaultUiListener, com.tencent.tauth.IUiListener
        public void onCancel() {
            IUiListener iUiListener = this.f18013e;
            if (iUiListener != null) {
                iUiListener.onCancel();
                this.f18013e = null;
            }
        }

        @Override // com.tencent.tauth.DefaultUiListener, com.tencent.tauth.IUiListener
        public void onComplete(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            h.a().a(this.f18012d + "_H5", SystemClock.elapsedRealtime(), 0L, 0L, jSONObject.optInt("ret", -6), this.f18009a, false);
            IUiListener iUiListener = this.f18013e;
            if (iUiListener != null) {
                iUiListener.onComplete(jSONObject);
                this.f18013e = null;
            }
        }

        @Override // com.tencent.tauth.DefaultUiListener, com.tencent.tauth.IUiListener
        public void onError(UiError uiError) {
            String str;
            if (uiError.errorMessage != null) {
                str = uiError.errorMessage + this.f18009a;
            } else {
                str = this.f18009a;
            }
            h.a().a(this.f18012d + "_H5", SystemClock.elapsedRealtime(), 0L, 0L, uiError.errorCode, str, false);
            a.this.a(str);
            IUiListener iUiListener = this.f18013e;
            if (iUiListener != null) {
                iUiListener.onError(uiError);
                this.f18013e = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(String str) {
            try {
                onComplete(k.d(str));
            } catch (JSONException e2) {
                e2.printStackTrace();
                onError(new UiError(-4, Constants.MSG_JSON_ERROR, str));
            }
        }
    }

    public class c extends Handler {

        /* renamed from: b, reason: collision with root package name */
        private b f18015b;

        public c(b bVar, Looper looper) {
            super(looper);
            this.f18015b = bVar;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws JSONException {
            int i2 = message.what;
            if (i2 == 1) {
                this.f18015b.a((String) message.obj);
            } else if (i2 == 2) {
                this.f18015b.onCancel();
            } else {
                if (i2 != 3) {
                    return;
                }
                a.b(a.this.f17994k, (String) message.obj);
            }
        }
    }

    public class d implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        String f18016a;

        public d(String str) {
            this.f18016a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            SLog.v("openSDK_LOG.AuthDialog", "-->timeoutUrl: " + this.f18016a + " | mRetryUrl: " + a.this.f17998o);
            if (this.f18016a.equals(a.this.f17998o)) {
                a.this.f17985b.onError(new UiError(9002, "请求页面超时，请稍后重试！", a.this.f17998o));
                a.this.dismiss();
            }
        }
    }

    public a(Context context, String str, String str2, IUiListener iUiListener, QQToken qQToken) {
        super(context, R.style.Theme.Translucent.NoTitleBar);
        this.f17996m = false;
        this.f18000q = 0L;
        this.f18001r = 30000L;
        this.f17994k = context;
        this.f17984a = str2;
        this.f17985b = new b(str, str2, qQToken.getAppId(), iUiListener);
        this.f17987d = new c(this.f17985b, context.getMainLooper());
        this.f17986c = iUiListener;
        this.f17992i = str;
        this.f17995l = new com.tencent.open.web.security.b();
        getWindow().setSoftInputMode(32);
    }

    public static /* synthetic */ int m(a aVar) {
        int i2 = aVar.f17997n;
        aVar.f17997n = i2 + 1;
        return i2;
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        this.f18002s.clear();
        this.f17987d.removeCallbacksAndMessages(null);
        try {
            Context context = this.f17994k;
            if ((context instanceof Activity) && !((Activity) context).isFinishing() && isShowing()) {
                super.dismiss();
                SLog.i("openSDK_LOG.AuthDialog", "-->dismiss dialog");
            }
        } catch (Exception e2) {
            SLog.e("openSDK_LOG.AuthDialog", "-->dismiss dialog exception:", e2);
        }
        com.tencent.open.c.c cVar = this.f17993j;
        if (cVar != null) {
            cVar.destroy();
            this.f17993j = null;
        }
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        if (!this.f17996m) {
            this.f17985b.onCancel();
        }
        super.onBackPressed();
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        Window window = getWindow();
        if (window != null) {
            window.setFlags(1024, 1024);
        }
        super.onCreate(bundle);
        if (window != null) {
            window.getDecorView().setSystemUiVisibility(1280);
        }
        b();
        d();
        this.f18002s = new HashMap<>();
    }

    @Override // android.app.Dialog
    public void onStop() {
        super.onStop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e() {
        com.tencent.connect.auth.b bVarA = com.tencent.connect.auth.b.a();
        String strC = bVarA.c();
        b.a aVar = new b.a();
        aVar.f18023a = this.f17986c;
        aVar.f18024b = this;
        aVar.f18025c = strC;
        String strA = bVarA.a(aVar);
        String str = this.f17984a;
        String strSubstring = str.substring(0, str.indexOf("?"));
        Bundle bundleB = k.b(this.f17984a);
        bundleB.putString("token_key", strC);
        bundleB.putString("serial", strA);
        bundleB.putString("browser", "1");
        String str2 = strSubstring + "?" + HttpUtils.encodeUrl(bundleB);
        this.f17984a = str2;
        return k.a(this.f17994k, str2);
    }

    private void b() {
        c();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        com.tencent.open.c.c cVar = new com.tencent.open.c.c(this.f17994k);
        this.f17993j = cVar;
        cVar.setLayerType(1, null);
        this.f17993j.setLayoutParams(layoutParams);
        FrameLayout frameLayout = new FrameLayout(this.f17994k);
        this.f17988e = frameLayout;
        layoutParams.gravity = 17;
        frameLayout.setLayoutParams(layoutParams);
        this.f17988e.addView(this.f17993j);
        this.f17988e.addView(this.f17990g);
        String string = k.b(this.f17984a).getString(TtmlNode.TAG_STYLE);
        if (string != null && "qr".equals(string)) {
            a(this.f17988e);
        }
        setContentView(this.f17988e);
    }

    private void c() {
        TextView textView;
        this.f17991h = new ProgressBar(this.f17994k);
        this.f17991h.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.f17989f = new LinearLayout(this.f17994k);
        if (this.f17992i.equals("action_login")) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 16;
            layoutParams.leftMargin = 5;
            textView = new TextView(this.f17994k);
            if (Locale.getDefault().getLanguage().equals("zh")) {
                textView.setText("登录中...");
            } else {
                textView.setText("Logging in...");
            }
            textView.setTextColor(Color.rgb(255, 255, 255));
            textView.setTextSize(18.0f);
            textView.setLayoutParams(layoutParams);
        } else {
            textView = null;
        }
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
        layoutParams2.gravity = 17;
        this.f17989f.setLayoutParams(layoutParams2);
        this.f17989f.addView(this.f17991h);
        if (textView != null) {
            this.f17989f.addView(textView);
        }
        this.f17990g = new FrameLayout(this.f17994k);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(-1, -1);
        layoutParams3.gravity = 17;
        this.f17990g.setLayoutParams(layoutParams3);
        this.f17990g.setBackgroundColor(Color.parseColor("#B3000000"));
        this.f17990g.addView(this.f17989f);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void d() {
        this.f17993j.setVerticalScrollBarEnabled(false);
        this.f17993j.setHorizontalScrollBarEnabled(false);
        this.f17993j.setWebViewClient(new C0320a());
        this.f17993j.setWebChromeClient(new WebChromeClient());
        this.f17993j.clearFormData();
        this.f17993j.clearSslPreferences();
        this.f17993j.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.tencent.connect.auth.a.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                return true;
            }
        });
        this.f17993j.setOnTouchListener(new View.OnTouchListener() { // from class: com.tencent.connect.auth.a.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if ((action != 0 && action != 1) || view.hasFocus()) {
                    return false;
                }
                view.requestFocus();
                return false;
            }
        });
        WebSettings settings = this.f17993j.getSettings();
        i.a(settings);
        settings.setSaveFormData(false);
        settings.setCacheMode(-1);
        settings.setNeedInitialFocus(false);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(this.f17994k.getDir("databases", 0).getPath());
        settings.setDomStorageEnabled(true);
        SLog.v("openSDK_LOG.AuthDialog", "-->mUrl : " + this.f17984a);
        String str = this.f17984a;
        this.f17998o = str;
        this.f17993j.loadUrl(str);
        this.f17993j.setVisibility(4);
        this.f17995l.a(new SecureJsInterface(), "SecureJsInterface");
        SecureJsInterface.isPWDEdit = false;
        super.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.tencent.connect.auth.a.4
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                try {
                    if (JniInterface.isJniOk) {
                        JniInterface.clearAllPWD();
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(String str) {
        StringBuilder sb = new StringBuilder(str);
        if (!TextUtils.isEmpty(this.f17999p) && this.f17999p.length() >= 4) {
            String str2 = this.f17999p;
            String strSubstring = str2.substring(str2.length() - 4);
            sb.append("_u_");
            sb.append(strSubstring);
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a() {
        String str = this.f17984a;
        String str2 = "https://imgcache.qq.com/ptlogin/static/qzsjump.html?" + str.substring(str.indexOf("?") + 1);
        SLog.i("openSDK_LOG.AuthDialog", "-->generateDownloadUrl, url: https://imgcache.qq.com/ptlogin/static/qzsjump.html?");
        return str2;
    }

    private void a(ViewGroup viewGroup) {
        ImageView imageView = new ImageView(this.f17994k);
        int iA = com.tencent.connect.avatar.a.a(this.f17994k, 15.6f);
        int iA2 = com.tencent.connect.avatar.a.a(this.f17994k, 25.2f);
        int iA3 = com.tencent.connect.avatar.a.a(this.f17994k, 10.0f);
        int i2 = iA3 * 2;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iA + i2, iA2 + i2);
        layoutParams.leftMargin = iA3;
        imageView.setLayoutParams(layoutParams);
        imageView.setPadding(iA3, iA3, iA3, iA3);
        imageView.setImageDrawable(k.a("h5_qr_back.png", this.f17994k));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.tencent.connect.auth.a.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                a.this.dismiss();
                if (a.this.f17996m || a.this.f17985b == null) {
                    return;
                }
                a.this.f17985b.onCancel();
            }
        });
        viewGroup.addView(imageView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, String str) throws JSONException {
        try {
            JSONObject jSONObjectD = k.d(str);
            int i2 = jSONObjectD.getInt("type");
            Toast.makeText(context.getApplicationContext(), jSONObjectD.getString("msg"), i2).show();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2) {
        this.f17993j.loadUrl(BridgeUtil.JAVASCRIPT_STR + str + "(" + str2 + ");void(" + System.currentTimeMillis() + ");");
    }
}
