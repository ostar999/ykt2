package com.tencent.open;

import android.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.open.a;
import com.tencent.open.c.a;
import com.tencent.open.log.SLog;
import com.tencent.open.utils.h;
import com.tencent.open.utils.i;
import com.tencent.open.utils.k;
import com.tencent.tauth.DefaultUiListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class c extends com.tencent.open.b implements a.InterfaceC0349a {

    /* renamed from: c, reason: collision with root package name */
    static Toast f20567c;

    /* renamed from: d, reason: collision with root package name */
    private String f20568d;

    /* renamed from: e, reason: collision with root package name */
    private IUiListener f20569e;

    /* renamed from: f, reason: collision with root package name */
    private C0350c f20570f;

    /* renamed from: g, reason: collision with root package name */
    private Handler f20571g;

    /* renamed from: h, reason: collision with root package name */
    private com.tencent.open.c.a f20572h;

    /* renamed from: i, reason: collision with root package name */
    private com.tencent.open.c.b f20573i;

    /* renamed from: j, reason: collision with root package name */
    private WeakReference<Context> f20574j;

    /* renamed from: k, reason: collision with root package name */
    private int f20575k;

    public class a extends WebViewClient {
        private a() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            c.this.f20573i.setVisibility(0);
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            SLog.v("openSDK_LOG.PKDialog", "Webview loading URL: " + str);
            super.onPageStarted(webView, str, bitmap);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int i2, String str, String str2) {
            super.onReceivedError(webView, i2, str, str2);
            c.this.f20570f.onError(new UiError(i2, str, str2));
            if (c.this.f20574j != null && c.this.f20574j.get() != null) {
                Toast.makeText((Context) c.this.f20574j.get(), "网络连接异常或系统错误", 0).show();
            }
            c.this.dismiss();
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            SLog.v("openSDK_LOG.PKDialog", "Redirect URL: " + str);
            if (str.startsWith(h.a().a((Context) c.this.f20574j.get(), "auth://tauth.qq.com/"))) {
                c.this.f20570f.onComplete(k.c(str));
                c.this.dismiss();
                return true;
            }
            if (str.startsWith(Constants.CANCEL_URI)) {
                c.this.f20570f.onCancel();
                c.this.dismiss();
                return true;
            }
            if (!str.startsWith(Constants.CLOSE_URI)) {
                return false;
            }
            c.this.dismiss();
            return true;
        }
    }

    public class b extends a.b {
        private b() {
        }
    }

    /* renamed from: com.tencent.open.c$c, reason: collision with other inner class name */
    public static class C0350c extends DefaultUiListener {

        /* renamed from: a, reason: collision with root package name */
        String f20585a;

        /* renamed from: b, reason: collision with root package name */
        String f20586b;

        /* renamed from: c, reason: collision with root package name */
        private WeakReference<Context> f20587c;

        /* renamed from: d, reason: collision with root package name */
        private String f20588d;

        /* renamed from: e, reason: collision with root package name */
        private IUiListener f20589e;

        public C0350c(Context context, String str, String str2, String str3, IUiListener iUiListener) {
            this.f20587c = new WeakReference<>(context);
            this.f20588d = str;
            this.f20585a = str2;
            this.f20586b = str3;
            this.f20589e = iUiListener;
        }

        @Override // com.tencent.tauth.DefaultUiListener, com.tencent.tauth.IUiListener
        public void onCancel() {
            IUiListener iUiListener = this.f20589e;
            if (iUiListener != null) {
                iUiListener.onCancel();
                this.f20589e = null;
            }
        }

        @Override // com.tencent.tauth.DefaultUiListener, com.tencent.tauth.IUiListener
        public void onComplete(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            com.tencent.open.b.h.a().a(this.f20588d + "_H5", SystemClock.elapsedRealtime(), 0L, 0L, jSONObject.optInt("ret", -6), this.f20585a, false);
            IUiListener iUiListener = this.f20589e;
            if (iUiListener != null) {
                iUiListener.onComplete(jSONObject);
                this.f20589e = null;
            }
        }

        @Override // com.tencent.tauth.DefaultUiListener, com.tencent.tauth.IUiListener
        public void onError(UiError uiError) {
            String str;
            if (uiError.errorMessage != null) {
                str = uiError.errorMessage + this.f20585a;
            } else {
                str = this.f20585a;
            }
            com.tencent.open.b.h hVarA = com.tencent.open.b.h.a();
            hVarA.a(this.f20588d + "_H5", SystemClock.elapsedRealtime(), 0L, 0L, uiError.errorCode, str, false);
            IUiListener iUiListener = this.f20589e;
            if (iUiListener != null) {
                iUiListener.onError(uiError);
                this.f20589e = null;
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

    public class d extends Handler {

        /* renamed from: b, reason: collision with root package name */
        private C0350c f20591b;

        public d(C0350c c0350c, Looper looper) {
            super(looper);
            this.f20591b = c0350c;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws JSONException {
            SLog.d("openSDK_LOG.PKDialog", "msg = " + message.what);
            int i2 = message.what;
            if (i2 == 1) {
                this.f20591b.a((String) message.obj);
                return;
            }
            if (i2 == 2) {
                this.f20591b.onCancel();
                return;
            }
            if (i2 == 3) {
                if (c.this.f20574j == null || c.this.f20574j.get() == null) {
                    return;
                }
                c.c((Context) c.this.f20574j.get(), (String) message.obj);
                return;
            }
            if (i2 != 5 || c.this.f20574j == null || c.this.f20574j.get() == null) {
                return;
            }
            c.d((Context) c.this.f20574j.get(), (String) message.obj);
        }
    }

    public c(Context context, String str, String str2, IUiListener iUiListener, QQToken qQToken) {
        super(context, R.style.Theme.Translucent.NoTitleBar);
        this.f20574j = new WeakReference<>(context);
        this.f20568d = str2;
        this.f20570f = new C0350c(context, str, str2, qQToken.getAppId(), iUiListener);
        this.f20571g = new d(this.f20570f, context.getMainLooper());
        this.f20569e = iUiListener;
        this.f20575k = Math.round(context.getResources().getDisplayMetrics().density * 185.0f);
        SLog.e("openSDK_LOG.PKDialog", "density=" + context.getResources().getDisplayMetrics().density + "; webviewHeight=" + this.f20575k);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(Context context, String str) throws JSONException {
        if (context == null || str == null) {
            return;
        }
        try {
            JSONObject jSONObjectD = k.d(str);
            jSONObjectD.getInt("action");
            jSONObjectD.getString("msg");
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override // com.tencent.open.b, android.app.Dialog
    public void onCreate(Bundle bundle) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        requestWindowFeature(1);
        super.onCreate(bundle);
        getWindow().setSoftInputMode(16);
        getWindow().setSoftInputMode(1);
        b();
        c();
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void c() {
        this.f20573i.setVerticalScrollBarEnabled(false);
        this.f20573i.setHorizontalScrollBarEnabled(false);
        this.f20573i.setWebViewClient(new a());
        this.f20573i.setWebChromeClient(this.f20519b);
        this.f20573i.clearFormData();
        WebSettings settings = this.f20573i.getSettings();
        if (settings == null) {
            return;
        }
        i.a(settings);
        settings.setSaveFormData(false);
        settings.setCacheMode(-1);
        settings.setNeedInitialFocus(false);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);
        WeakReference<Context> weakReference = this.f20574j;
        if (weakReference != null && weakReference.get() != null) {
            settings.setDatabaseEnabled(true);
            settings.setDatabasePath(this.f20574j.get().getApplicationContext().getDir("databases", 0).getPath());
        }
        settings.setDomStorageEnabled(true);
        this.f20518a.a(new b(), "sdk_js_if");
        this.f20573i.clearView();
        this.f20573i.loadUrl(this.f20568d);
    }

    private void b() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        com.tencent.open.c.a aVar = new com.tencent.open.c.a(this.f20574j.get());
        this.f20572h = aVar;
        aVar.setBackgroundColor(1711276032);
        this.f20572h.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        com.tencent.open.c.b bVar = new com.tencent.open.c.b(this.f20574j.get());
        this.f20573i = bVar;
        bVar.setBackgroundColor(0);
        this.f20573i.setBackgroundDrawable(null);
        try {
            View.class.getMethod("setLayerType", Integer.TYPE, Paint.class).invoke(this.f20573i, 1, new Paint());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, this.f20575k);
        layoutParams.addRule(13, -1);
        this.f20573i.setLayoutParams(layoutParams);
        this.f20572h.addView(this.f20573i);
        this.f20572h.a(this);
        setContentView(this.f20572h);
    }

    @Override // com.tencent.open.c.a.InterfaceC0349a
    public void a(int i2) {
        WeakReference<Context> weakReference = this.f20574j;
        if (weakReference != null && weakReference.get() != null) {
            if (i2 < this.f20575k && 2 == this.f20574j.get().getResources().getConfiguration().orientation) {
                this.f20573i.getLayoutParams().height = i2;
            } else {
                this.f20573i.getLayoutParams().height = this.f20575k;
            }
        }
        SLog.e("openSDK_LOG.PKDialog", "onKeyboardShown keyboard show");
    }

    @Override // com.tencent.open.c.a.InterfaceC0349a
    public void a() {
        this.f20573i.getLayoutParams().height = this.f20575k;
        SLog.e("openSDK_LOG.PKDialog", "onKeyboardHidden keyboard hide");
    }

    @Override // com.tencent.open.b
    public void a(String str) {
        SLog.d("openSDK_LOG.PKDialog", "--onConsoleMessage--");
        try {
            this.f20518a.a(this.f20573i, str);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Context context, String str) throws JSONException {
        try {
            JSONObject jSONObjectD = k.d(str);
            int i2 = jSONObjectD.getInt("type");
            String string = jSONObjectD.getString("msg");
            if (i2 == 0) {
                Toast toast = f20567c;
                if (toast == null) {
                    f20567c = Toast.makeText(context, string, 0);
                } else {
                    toast.setView(toast.getView());
                    f20567c.setText(string);
                    f20567c.setDuration(0);
                }
                f20567c.show();
                return;
            }
            if (i2 == 1) {
                Toast toast2 = f20567c;
                if (toast2 == null) {
                    f20567c = Toast.makeText(context, string, 1);
                } else {
                    toast2.setView(toast2.getView());
                    f20567c.setText(string);
                    f20567c.setDuration(1);
                }
                f20567c.show();
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }
}
