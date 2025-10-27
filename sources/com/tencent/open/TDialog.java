package com.tencent.open;

import android.R;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.azhon.appupdate.config.Constant;
import com.psychiatrygarden.utils.Constants;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.open.a;
import com.tencent.open.log.SLog;
import com.tencent.open.utils.h;
import com.tencent.open.utils.i;
import com.tencent.open.utils.k;
import com.tencent.tauth.DefaultUiListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class TDialog extends b {

    /* renamed from: c, reason: collision with root package name */
    static final FrameLayout.LayoutParams f20479c = new FrameLayout.LayoutParams(-1, -1);

    /* renamed from: d, reason: collision with root package name */
    static Toast f20480d = null;

    /* renamed from: f, reason: collision with root package name */
    private static WeakReference<ProgressDialog> f20481f;

    /* renamed from: e, reason: collision with root package name */
    private WeakReference<Context> f20482e;

    /* renamed from: g, reason: collision with root package name */
    private String f20483g;

    /* renamed from: h, reason: collision with root package name */
    private OnTimeListener f20484h;

    /* renamed from: i, reason: collision with root package name */
    private IUiListener f20485i;

    /* renamed from: j, reason: collision with root package name */
    private FrameLayout f20486j;

    /* renamed from: k, reason: collision with root package name */
    private com.tencent.open.c.b f20487k;

    /* renamed from: l, reason: collision with root package name */
    private Handler f20488l;

    /* renamed from: m, reason: collision with root package name */
    private boolean f20489m;

    /* renamed from: n, reason: collision with root package name */
    private QQToken f20490n;

    public class FbWebViewClient extends WebViewClient {
        private FbWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            TDialog.this.f20487k.setVisibility(0);
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            SLog.v("openSDK_LOG.TDialog", "Webview loading URL: " + str);
            super.onPageStarted(webView, str, bitmap);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int i2, String str, String str2) {
            super.onReceivedError(webView, i2, str, str2);
            TDialog.this.f20484h.onError(new UiError(i2, str, str2));
            if (TDialog.this.f20482e != null && TDialog.this.f20482e.get() != null) {
                Toast.makeText((Context) TDialog.this.f20482e.get(), "网络连接异常或系统错误", 0).show();
            }
            TDialog.this.dismiss();
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            SLog.v("openSDK_LOG.TDialog", "Redirect URL: " + str);
            if (str.startsWith(h.a().a((Context) TDialog.this.f20482e.get(), "auth://tauth.qq.com/"))) {
                TDialog.this.f20484h.onComplete(k.c(str));
                if (TDialog.this.isShowing()) {
                    TDialog.this.dismiss();
                }
                return true;
            }
            if (str.startsWith(Constants.CANCEL_URI)) {
                TDialog.this.f20484h.onCancel();
                if (TDialog.this.isShowing()) {
                    TDialog.this.dismiss();
                }
                return true;
            }
            if (str.startsWith(Constants.CLOSE_URI)) {
                if (TDialog.this.isShowing()) {
                    TDialog.this.dismiss();
                }
                return true;
            }
            if (!str.startsWith(Constants.DOWNLOAD_URI) && !str.endsWith(Constant.APK_SUFFIX)) {
                return str.startsWith("auth://progress");
            }
            try {
                Intent intent = new Intent("android.intent.action.VIEW", str.startsWith(Constants.DOWNLOAD_URI) ? Uri.parse(Uri.decode(str.substring(11))) : Uri.parse(Uri.decode(str)));
                intent.addFlags(268435456);
                if (TDialog.this.f20482e != null && TDialog.this.f20482e.get() != null) {
                    ((Context) TDialog.this.f20482e.get()).startActivity(intent);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return true;
        }
    }

    public class JsListener extends a.b {
        private JsListener() {
        }

        public void onAddShare(String str) {
            SLog.d("openSDK_LOG.TDialog", "JsListener onAddShare");
            onComplete(str);
        }

        public void onCancel(String str) {
            SLog.e("openSDK_LOG.TDialog", "JsListener onCancel --msg = " + str);
            TDialog.this.f20488l.obtainMessage(2, str).sendToTarget();
            TDialog.this.dismiss();
        }

        public void onCancelAddShare(String str) {
            SLog.e("openSDK_LOG.TDialog", "JsListener onCancelAddShare" + str);
            onCancel("cancel");
        }

        public void onCancelInvite() {
            SLog.e("openSDK_LOG.TDialog", "JsListener onCancelInvite");
            onCancel("");
        }

        public void onCancelLogin() {
            onCancel("");
        }

        public void onComplete(String str) {
            TDialog.this.f20488l.obtainMessage(1, str).sendToTarget();
            SLog.e("openSDK_LOG.TDialog", "JsListener onComplete" + str);
            TDialog.this.dismiss();
        }

        public void onInvite(String str) {
            onComplete(str);
        }

        public void onLoad(String str) {
            TDialog.this.f20488l.obtainMessage(4, str).sendToTarget();
        }

        public void showMsg(String str) {
            TDialog.this.f20488l.obtainMessage(3, str).sendToTarget();
        }
    }

    public static class OnTimeListener extends DefaultUiListener {

        /* renamed from: a, reason: collision with root package name */
        String f20494a;

        /* renamed from: b, reason: collision with root package name */
        String f20495b;

        /* renamed from: c, reason: collision with root package name */
        private WeakReference<Context> f20496c;

        /* renamed from: d, reason: collision with root package name */
        private String f20497d;

        /* renamed from: e, reason: collision with root package name */
        private IUiListener f20498e;

        public OnTimeListener(Context context, String str, String str2, String str3, IUiListener iUiListener) {
            this.f20496c = new WeakReference<>(context);
            this.f20497d = str;
            this.f20494a = str2;
            this.f20495b = str3;
            this.f20498e = iUiListener;
        }

        @Override // com.tencent.tauth.DefaultUiListener, com.tencent.tauth.IUiListener
        public void onCancel() {
            IUiListener iUiListener = this.f20498e;
            if (iUiListener != null) {
                iUiListener.onCancel();
                this.f20498e = null;
            }
        }

        @Override // com.tencent.tauth.DefaultUiListener, com.tencent.tauth.IUiListener
        public void onComplete(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            com.tencent.open.b.h.a().a(this.f20497d + "_H5", SystemClock.elapsedRealtime(), 0L, 0L, jSONObject.optInt("ret", -6), this.f20494a, false);
            IUiListener iUiListener = this.f20498e;
            if (iUiListener != null) {
                iUiListener.onComplete(jSONObject);
                this.f20498e = null;
            }
        }

        @Override // com.tencent.tauth.DefaultUiListener, com.tencent.tauth.IUiListener
        public void onError(UiError uiError) {
            String str;
            if (uiError.errorMessage != null) {
                str = uiError.errorMessage + this.f20494a;
            } else {
                str = this.f20494a;
            }
            com.tencent.open.b.h hVarA = com.tencent.open.b.h.a();
            hVarA.a(this.f20497d + "_H5", SystemClock.elapsedRealtime(), 0L, 0L, uiError.errorCode, str, false);
            IUiListener iUiListener = this.f20498e;
            if (iUiListener != null) {
                iUiListener.onError(uiError);
                this.f20498e = null;
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

    public class THandler extends Handler {

        /* renamed from: b, reason: collision with root package name */
        private OnTimeListener f20500b;

        public THandler(OnTimeListener onTimeListener, Looper looper) {
            super(looper);
            this.f20500b = onTimeListener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws JSONException {
            SLog.d("openSDK_LOG.TDialog", "--handleMessage--msg.WHAT = " + message.what);
            int i2 = message.what;
            if (i2 == 1) {
                this.f20500b.a((String) message.obj);
                return;
            }
            if (i2 == 2) {
                this.f20500b.onCancel();
                return;
            }
            if (i2 == 3) {
                if (TDialog.this.f20482e == null || TDialog.this.f20482e.get() == null) {
                    return;
                }
                TDialog.c((Context) TDialog.this.f20482e.get(), (String) message.obj);
                return;
            }
            if (i2 != 5 || TDialog.this.f20482e == null || TDialog.this.f20482e.get() == null) {
                return;
            }
            TDialog.d((Context) TDialog.this.f20482e.get(), (String) message.obj);
        }
    }

    public TDialog(Context context, String str, String str2, IUiListener iUiListener, QQToken qQToken) {
        super(context, R.style.Theme.Translucent.NoTitleBar);
        this.f20489m = false;
        this.f20490n = null;
        this.f20482e = new WeakReference<>(context);
        this.f20483g = str2;
        this.f20484h = new OnTimeListener(context, str, str2, qQToken.getAppId(), iUiListener);
        this.f20488l = new THandler(this.f20484h, context.getMainLooper());
        this.f20485i = iUiListener;
        this.f20490n = qQToken;
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        OnTimeListener onTimeListener = this.f20484h;
        if (onTimeListener != null) {
            onTimeListener.onCancel();
        }
        super.onBackPressed();
    }

    @Override // com.tencent.open.b, android.app.Dialog
    public void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        a();
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.open.TDialog.1
            @Override // java.lang.Runnable
            public void run() {
                View decorView;
                View childAt;
                Window window = TDialog.this.getWindow();
                if (window == null || (decorView = window.getDecorView()) == null || (childAt = ((ViewGroup) decorView).getChildAt(0)) == null) {
                    return;
                }
                childAt.setPadding(0, 0, 0, 0);
            }
        });
        b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Context context, String str) throws JSONException {
        try {
            JSONObject jSONObjectD = k.d(str);
            int i2 = jSONObjectD.getInt("type");
            String string = jSONObjectD.getString("msg");
            if (i2 == 0) {
                Toast toast = f20480d;
                if (toast == null) {
                    f20480d = Toast.makeText(context, string, 0);
                } else {
                    toast.setView(toast.getView());
                    f20480d.setText(string);
                    f20480d.setDuration(0);
                }
                f20480d.show();
                return;
            }
            if (i2 == 1) {
                Toast toast2 = f20480d;
                if (toast2 == null) {
                    f20480d = Toast.makeText(context, string, 1);
                } else {
                    toast2.setView(toast2.getView());
                    f20480d.setText(string);
                    f20480d.setDuration(1);
                }
                f20480d.show();
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(Context context, String str) throws JSONException {
        if (context == null || str == null) {
            return;
        }
        try {
            JSONObject jSONObjectD = k.d(str);
            int i2 = jSONObjectD.getInt("action");
            String string = jSONObjectD.getString("msg");
            if (i2 == 1) {
                WeakReference<ProgressDialog> weakReference = f20481f;
                if (weakReference == null || weakReference.get() == null) {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage(string);
                    f20481f = new WeakReference<>(progressDialog);
                    progressDialog.show();
                } else {
                    f20481f.get().setMessage(string);
                    if (!f20481f.get().isShowing()) {
                        f20481f.get().show();
                    }
                }
            } else if (i2 == 0) {
                WeakReference<ProgressDialog> weakReference2 = f20481f;
                if (weakReference2 == null) {
                    return;
                }
                if (weakReference2.get() != null && f20481f.get().isShowing()) {
                    f20481f.get().dismiss();
                    f20481f = null;
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    private void a() {
        new TextView(this.f20482e.get()).setText(Constants.ANSWER_MODE.TEST_MODE);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        com.tencent.open.c.b bVar = new com.tencent.open.c.b(this.f20482e.get());
        this.f20487k = bVar;
        bVar.setLayoutParams(layoutParams);
        FrameLayout frameLayout = new FrameLayout(this.f20482e.get());
        this.f20486j = frameLayout;
        layoutParams.gravity = 17;
        frameLayout.setLayoutParams(layoutParams);
        this.f20486j.addView(this.f20487k);
        setContentView(this.f20486j);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void b() {
        this.f20487k.setVerticalScrollBarEnabled(false);
        this.f20487k.setHorizontalScrollBarEnabled(false);
        this.f20487k.setWebViewClient(new FbWebViewClient());
        this.f20487k.setWebChromeClient(this.f20519b);
        this.f20487k.clearFormData();
        WebSettings settings = this.f20487k.getSettings();
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
        WeakReference<Context> weakReference = this.f20482e;
        if (weakReference != null && weakReference.get() != null) {
            settings.setDatabaseEnabled(true);
            settings.setDatabasePath(this.f20482e.get().getApplicationContext().getDir("databases", 0).getPath());
        }
        settings.setDomStorageEnabled(true);
        this.f20518a.a(new JsListener(), "sdk_js_if");
        this.f20487k.loadUrl(this.f20483g);
        this.f20487k.setLayoutParams(f20479c);
        this.f20487k.setVisibility(4);
    }

    @Override // com.tencent.open.b
    public void a(String str) {
        SLog.d("openSDK_LOG.TDialog", "--onConsoleMessage--");
        try {
            this.f20518a.a(this.f20487k, str);
        } catch (Exception unused) {
        }
    }
}
