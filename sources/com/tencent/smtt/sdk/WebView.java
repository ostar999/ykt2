package com.tencent.smtt.sdk;

import android.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.net.http.SslCertificate;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebChromeClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebSettingsExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.extension.proxy.X5ProxyWebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.sdk.stat.MttLoader;
import com.tencent.smtt.utils.FileUtil;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.tbs.one.TBSOneConfigurationKeys;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class WebView extends FrameLayout implements View.OnLongClickListener {
    public static final int GETPVERROR = -1;
    public static final int NIGHT_MODE_COLOR = -16777216;
    public static final int NORMAL_MODE_ALPHA = 255;
    public static final String SCHEME_GEO = "geo:0,0?q=";
    public static final String SCHEME_MAILTO = "mailto:";
    public static final String SCHEME_TEL = "tel:";

    /* renamed from: a, reason: collision with root package name */
    volatile int f21067a;

    /* renamed from: b, reason: collision with root package name */
    private final String f21068b;

    /* renamed from: e, reason: collision with root package name */
    private boolean f21069e;

    /* renamed from: f, reason: collision with root package name */
    private IX5WebViewBase f21070f;

    /* renamed from: g, reason: collision with root package name */
    private a f21071g;

    /* renamed from: h, reason: collision with root package name */
    private WebSettings f21072h;

    /* renamed from: i, reason: collision with root package name */
    private Context f21073i;

    /* renamed from: k, reason: collision with root package name */
    private volatile boolean f21074k;

    /* renamed from: l, reason: collision with root package name */
    private boolean f21075l;
    public WebViewCallbackClient mWebViewCallbackClient;

    /* renamed from: n, reason: collision with root package name */
    private WebViewClient f21076n;

    /* renamed from: o, reason: collision with root package name */
    private WebChromeClient f21077o;

    /* renamed from: q, reason: collision with root package name */
    private final int f21078q;

    /* renamed from: r, reason: collision with root package name */
    private final int f21079r;

    /* renamed from: s, reason: collision with root package name */
    private final int f21080s;

    /* renamed from: t, reason: collision with root package name */
    private final String f21081t;

    /* renamed from: u, reason: collision with root package name */
    private final String f21082u;

    /* renamed from: x, reason: collision with root package name */
    private Object f21083x;

    /* renamed from: y, reason: collision with root package name */
    private View.OnLongClickListener f21084y;

    /* renamed from: c, reason: collision with root package name */
    private static final Lock f21060c = new ReentrantLock();

    /* renamed from: d, reason: collision with root package name */
    private static OutputStream f21061d = null;

    /* renamed from: j, reason: collision with root package name */
    private static Context f21062j = null;
    public static boolean mWebViewCreated = false;

    /* renamed from: m, reason: collision with root package name */
    private static Method f21063m = null;

    /* renamed from: p, reason: collision with root package name */
    private static String f21064p = null;
    public static boolean mSysWebviewCreated = false;

    /* renamed from: v, reason: collision with root package name */
    private static Paint f21065v = null;

    /* renamed from: w, reason: collision with root package name */
    private static boolean f21066w = true;
    public static int NIGHT_MODE_ALPHA = 153;

    public static class HitTestResult {

        @Deprecated
        public static final int ANCHOR_TYPE = 1;
        public static final int EDIT_TEXT_TYPE = 9;
        public static final int EMAIL_TYPE = 4;
        public static final int GEO_TYPE = 3;

        @Deprecated
        public static final int IMAGE_ANCHOR_TYPE = 6;
        public static final int IMAGE_TYPE = 5;
        public static final int PHONE_TYPE = 2;
        public static final int SRC_ANCHOR_TYPE = 7;
        public static final int SRC_IMAGE_ANCHOR_TYPE = 8;
        public static final int UNKNOWN_TYPE = 0;

        /* renamed from: a, reason: collision with root package name */
        private IX5WebViewBase.HitTestResult f21096a;

        /* renamed from: b, reason: collision with root package name */
        private WebView.HitTestResult f21097b;

        public HitTestResult() {
            this.f21096a = null;
            this.f21097b = null;
        }

        public HitTestResult(WebView.HitTestResult hitTestResult) {
            this.f21096a = null;
            this.f21097b = hitTestResult;
        }

        public HitTestResult(IX5WebViewBase.HitTestResult hitTestResult) {
            this.f21096a = hitTestResult;
            this.f21097b = null;
        }

        public String getExtra() {
            IX5WebViewBase.HitTestResult hitTestResult = this.f21096a;
            if (hitTestResult != null) {
                return hitTestResult.getExtra();
            }
            WebView.HitTestResult hitTestResult2 = this.f21097b;
            return hitTestResult2 != null ? hitTestResult2.getExtra() : "";
        }

        public int getType() {
            IX5WebViewBase.HitTestResult hitTestResult = this.f21096a;
            if (hitTestResult != null) {
                return hitTestResult.getType();
            }
            WebView.HitTestResult hitTestResult2 = this.f21097b;
            if (hitTestResult2 != null) {
                return hitTestResult2.getType();
            }
            return 0;
        }
    }

    @Deprecated
    public interface PictureListener {
        @Deprecated
        void onNewPicture(WebView webView, Picture picture);
    }

    public class WebViewTransport {

        /* renamed from: b, reason: collision with root package name */
        private WebView f21099b;

        public WebViewTransport() {
        }

        public synchronized WebView getWebView() {
            return this.f21099b;
        }

        public synchronized void setWebView(WebView webView) {
            this.f21099b = webView;
        }
    }

    public class a extends android.webkit.WebView {
        public a(WebView webView, Context context) {
            this(context, null);
        }

        public a(Context context, AttributeSet attributeSet) throws NoSuchMethodException, SecurityException {
            super(WebView.this.d(context), attributeSet);
            if (QbSdk.getIsSysWebViewForcedByOuter() && TbsShareManager.isThirdPartyApp(context)) {
                return;
            }
            CookieSyncManager.createInstance(WebView.this.f21073i).startSync();
            try {
                Method declaredMethod = Class.forName("android.webkit.WebViewWorker").getDeclaredMethod("getHandler", new Class[0]);
                declaredMethod.setAccessible(true);
                ((Handler) declaredMethod.invoke(null, new Object[0])).getLooper().getThread().setUncaughtExceptionHandler(new h());
                WebView.mSysWebviewCreated = true;
            } catch (Exception unused) {
            }
        }

        public void a() {
            super.computeScroll();
        }

        public void a(int i2, int i3, int i4, int i5) {
            super.onScrollChanged(i2, i3, i4, i5);
        }

        @TargetApi(9)
        public void a(int i2, int i3, boolean z2, boolean z3) {
            super.onOverScrolled(i2, i3, z2, z3);
        }

        @TargetApi(9)
        public boolean a(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z2) {
            return super.overScrollBy(i2, i3, i4, i5, i6, i7, i8, i9, z2);
        }

        public boolean a(MotionEvent motionEvent) {
            return super.onTouchEvent(motionEvent);
        }

        public boolean b(MotionEvent motionEvent) {
            return super.dispatchTouchEvent(motionEvent);
        }

        public boolean c(MotionEvent motionEvent) {
            return super.onInterceptTouchEvent(motionEvent);
        }

        @Override // android.webkit.WebView, android.view.View
        public void computeScroll() {
            WebViewCallbackClient webViewCallbackClient = WebView.this.mWebViewCallbackClient;
            if (webViewCallbackClient != null) {
                webViewCallbackClient.computeScroll(this);
            } else {
                super.computeScroll();
            }
        }

        @Override // android.webkit.WebView, android.view.ViewGroup, android.view.View
        public void dispatchDraw(Canvas canvas) {
            try {
                super.dispatchDraw(canvas);
                if (WebView.f21066w || WebView.f21065v == null) {
                    return;
                }
                canvas.save();
                canvas.drawPaint(WebView.f21065v);
                canvas.restore();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            WebViewCallbackClient webViewCallbackClient = WebView.this.mWebViewCallbackClient;
            return webViewCallbackClient != null ? webViewCallbackClient.dispatchTouchEvent(motionEvent, this) : super.dispatchTouchEvent(motionEvent);
        }

        @Override // android.webkit.WebView
        public android.webkit.WebSettings getSettings() {
            try {
                return super.getSettings();
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }

        @Override // android.view.View
        public void invalidate() {
            super.invalidate();
            WebViewCallbackClient webViewCallbackClient = WebView.this.mWebViewCallbackClient;
            if (webViewCallbackClient != null) {
                webViewCallbackClient.invalidate();
            }
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            WebViewCallbackClient webViewCallbackClient = WebView.this.mWebViewCallbackClient;
            return webViewCallbackClient != null ? webViewCallbackClient.onInterceptTouchEvent(motionEvent, this) : super.onInterceptTouchEvent(motionEvent);
        }

        @Override // android.webkit.WebView, android.view.View
        @TargetApi(9)
        public void onOverScrolled(int i2, int i3, boolean z2, boolean z3) {
            WebViewCallbackClient webViewCallbackClient = WebView.this.mWebViewCallbackClient;
            if (webViewCallbackClient != null) {
                webViewCallbackClient.onOverScrolled(i2, i3, z2, z3, this);
            } else {
                super.onOverScrolled(i2, i3, z2, z3);
            }
        }

        @Override // android.webkit.WebView, android.view.View
        public void onScrollChanged(int i2, int i3, int i4, int i5) {
            WebViewCallbackClient webViewCallbackClient = WebView.this.mWebViewCallbackClient;
            if (webViewCallbackClient != null) {
                webViewCallbackClient.onScrollChanged(i2, i3, i4, i5, this);
            } else {
                super.onScrollChanged(i2, i3, i4, i5);
                WebView.this.onScrollChanged(i2, i3, i4, i5);
            }
        }

        @Override // android.webkit.WebView, android.view.View
        @SuppressLint({"ClickableViewAccessibility"})
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (!hasFocus()) {
                requestFocus();
            }
            WebViewCallbackClient webViewCallbackClient = WebView.this.mWebViewCallbackClient;
            if (webViewCallbackClient != null) {
                return webViewCallbackClient.onTouchEvent(motionEvent, this);
            }
            try {
                return super.onTouchEvent(motionEvent);
            } catch (Throwable th) {
                th.printStackTrace();
                return false;
            }
        }

        @Override // android.view.View
        @TargetApi(9)
        public boolean overScrollBy(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z2) {
            WebViewCallbackClient webViewCallbackClient = WebView.this.mWebViewCallbackClient;
            return webViewCallbackClient != null ? webViewCallbackClient.overScrollBy(i2, i3, i4, i5, i6, i7, i8, i9, z2, this) : super.overScrollBy(i2, i3, i4, i5, i6, i7, i8, i9, z2);
        }

        @Override // android.webkit.WebView, android.view.View
        public void setOverScrollMode(int i2) {
            try {
                super.setOverScrollMode(i2);
            } catch (Exception unused) {
            }
        }
    }

    public WebView(Context context) {
        this(context, (AttributeSet) null);
    }

    public WebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WebView(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, false);
    }

    @TargetApi(11)
    public WebView(Context context, AttributeSet attributeSet, int i2, Map<String, Object> map, boolean z2) throws NoSuchMethodException, Resources.NotFoundException, SecurityException {
        super(context, attributeSet, i2);
        this.f21068b = "WebView";
        this.f21069e = false;
        this.f21072h = null;
        this.f21073i = null;
        this.f21067a = 0;
        this.f21074k = false;
        this.f21075l = false;
        this.f21076n = null;
        this.f21077o = null;
        this.f21078q = 1;
        this.f21079r = 2;
        this.f21080s = 3;
        this.f21081t = "javascript:document.getElementsByTagName('HEAD').item(0).removeChild(document.getElementById('QQBrowserSDKNightMode'));";
        this.f21082u = "javascript:var style = document.createElement('style');style.type='text/css';style.id='QQBrowserSDKNightMode';style.innerHTML='html,body{background:none !important;background-color: #1d1e2a !important;}html *{background-color: #1d1e2a !important; color:#888888 !important;border-color:#3e4f61 !important;text-shadow:none !important;box-shadow:none !important;}a,a *{border-color:#4c5b99 !important; color:#2d69b3 !important;text-decoration:none !important;}a:visited,a:visited *{color:#a600a6 !important;}a:active,a:active *{color:#5588AA !important;}input,select,textarea,option,button{background-image:none !important;color:#AAAAAA !important;border-color:#4c5b99 !important;}form,div,button,span{background-color:#1d1e2a !important; border-color:#4c5b99 !important;}img{opacity:0.5}';document.getElementsByTagName('HEAD').item(0).appendChild(style);";
        this.f21083x = null;
        this.f21084y = null;
        mWebViewCreated = true;
        com.tencent.smtt.utils.s.a("0");
        if (QbSdk.getIsSysWebViewForcedByOuter() && TbsShareManager.isThirdPartyApp(context)) {
            this.f21073i = context;
            this.f21070f = null;
            this.f21069e = false;
            QbSdk.a(context, "failed to createTBSWebview!");
            this.f21071g = new a(context, attributeSet);
            CookieSyncManager.createInstance(this.f21073i).startSync();
            try {
                Method declaredMethod = Class.forName("android.webkit.WebViewWorker").getDeclaredMethod("getHandler", new Class[0]);
                declaredMethod.setAccessible(true);
                ((Handler) declaredMethod.invoke(null, new Object[0])).getLooper().getThread().setUncaughtExceptionHandler(new h());
                mSysWebviewCreated = true;
            } catch (Exception unused) {
            }
            CookieManager.getInstance().a();
            this.f21071g.setFocusableInTouchMode(true);
            addView(this.f21071g, new FrameLayout.LayoutParams(-1, -1));
            TbsLog.i("WebView", "SystemWebView Created Success! #3, SysWebViewForcedByOuter.");
            TbsLog.e("WebView", "sys WebView: IsSysWebViewForcedByOuter = true", true);
            return;
        }
        if (TbsShareManager.isThirdPartyApp(context)) {
            TbsLog.setWriteLogJIT(true);
            TbsLog.i("webview", "new WebView, thread is " + Thread.currentThread() + "stack: " + Log.getStackTraceString(new Throwable("new WebView Detect")));
        } else {
            TbsLog.setWriteLogJIT(false);
        }
        TbsLog.initIfNeed(context);
        if (context == null) {
            throw new IllegalArgumentException("Invalid context argument");
        }
        c(context);
        this.f21073i = context;
        f21062j = context.getApplicationContext();
        if (!this.f21069e || QbSdk.f20817a) {
            this.f21070f = null;
            if (TbsShareManager.isThirdPartyApp(this.f21073i)) {
                this.f21071g = new a(context, attributeSet);
            } else {
                this.f21071g = new a(this, context);
            }
            TbsLog.i("WebView", "SystemWebView Created Success! #2");
            CookieManager.getInstance().a();
            this.f21071g.setFocusableInTouchMode(true);
            addView(this.f21071g, new FrameLayout.LayoutParams(-1, -1));
            setDownloadListener(null);
            TbsLog.writeLogToDisk();
            o.a(context);
        } else {
            IX5WebViewBase iX5WebViewBaseA = w.a().a(true).a(context);
            this.f21070f = iX5WebViewBaseA;
            if (iX5WebViewBaseA == null || iX5WebViewBaseA.getView() == null) {
                TbsLog.e("WebView", "sys WebView: failed to createTBSWebview", true);
                this.f21070f = null;
                this.f21069e = false;
                QbSdk.a(context, "failed to createTBSWebview!");
                c(context);
                if (TbsShareManager.isThirdPartyApp(this.f21073i)) {
                    this.f21071g = new a(context, attributeSet);
                } else {
                    this.f21071g = new a(this, context);
                }
                TbsLog.i("WebView", "SystemWebView Created Success! #1");
                CookieManager.getInstance().a();
                this.f21071g.setFocusableInTouchMode(true);
                addView(this.f21071g, new FrameLayout.LayoutParams(-1, -1));
                try {
                    removeJavascriptInterface("searchBoxJavaBridge_");
                    removeJavascriptInterface("accessibility");
                    removeJavascriptInterface("accessibilityTraversal");
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                TbsLog.writeLogToDisk();
                o.a(context);
                return;
            }
            TbsLog.i("WebView", "X5 WebView Created Success!!");
            this.f21070f.getView().setFocusableInTouchMode(true);
            a(attributeSet);
            addView(this.f21070f.getView(), new FrameLayout.LayoutParams(-1, -1));
            this.f21070f.setDownloadListener(new b(this, null, this.f21069e));
            this.f21070f.getX5WebViewExtension().setWebViewClientExtension(new X5ProxyWebViewClientExtension(w.a().a(true).k()) { // from class: com.tencent.smtt.sdk.WebView.1
                @Override // com.tencent.smtt.export.external.extension.proxy.ProxyWebViewClientExtension, com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
                public void invalidate() {
                }

                @Override // com.tencent.smtt.export.external.extension.proxy.ProxyWebViewClientExtension, com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
                public void onScrollChanged(int i3, int i4, int i5, int i6) {
                    super.onScrollChanged(i3, i4, i5, i6);
                    WebView.this.onScrollChanged(i5, i6, i3, i4);
                }
            });
        }
        try {
            removeJavascriptInterface("searchBoxJavaBridge_");
            removeJavascriptInterface("accessibility");
            removeJavascriptInterface("accessibilityTraversal");
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
        if (("com.tencent.mobileqq".equals(this.f21073i.getApplicationInfo().packageName) || "com.tencent.mm".equals(this.f21073i.getApplicationInfo().packageName)) && g.a(true).i()) {
            setLayerType(1, null);
        }
        if (this.f21070f != null) {
            TbsLog.writeLogToDisk();
        }
    }

    @Deprecated
    public WebView(Context context, AttributeSet attributeSet, int i2, boolean z2) {
        this(context, attributeSet, i2, null, z2);
    }

    public WebView(Context context, boolean z2) {
        super(context);
        this.f21068b = "WebView";
        this.f21069e = false;
        this.f21072h = null;
        this.f21073i = null;
        this.f21067a = 0;
        this.f21074k = false;
        this.f21075l = false;
        this.f21076n = null;
        this.f21077o = null;
        this.f21078q = 1;
        this.f21079r = 2;
        this.f21080s = 3;
        this.f21081t = "javascript:document.getElementsByTagName('HEAD').item(0).removeChild(document.getElementById('QQBrowserSDKNightMode'));";
        this.f21082u = "javascript:var style = document.createElement('style');style.type='text/css';style.id='QQBrowserSDKNightMode';style.innerHTML='html,body{background:none !important;background-color: #1d1e2a !important;}html *{background-color: #1d1e2a !important; color:#888888 !important;border-color:#3e4f61 !important;text-shadow:none !important;box-shadow:none !important;}a,a *{border-color:#4c5b99 !important; color:#2d69b3 !important;text-decoration:none !important;}a:visited,a:visited *{color:#a600a6 !important;}a:active,a:active *{color:#5588AA !important;}input,select,textarea,option,button{background-image:none !important;color:#AAAAAA !important;border-color:#4c5b99 !important;}form,div,button,span{background-color:#1d1e2a !important; border-color:#4c5b99 !important;}img{opacity:0.5}';document.getElementsByTagName('HEAD').item(0).appendChild(style);";
        this.f21083x = null;
        this.f21084y = null;
    }

    private void a(AttributeSet attributeSet) throws Resources.NotFoundException {
        View view;
        if (attributeSet != null) {
            try {
                int attributeCount = attributeSet.getAttributeCount();
                for (int i2 = 0; i2 < attributeCount; i2++) {
                    if (attributeSet.getAttributeName(i2).equalsIgnoreCase("scrollbars")) {
                        int[] intArray = getResources().getIntArray(R.attr.scrollbars);
                        int attributeIntValue = attributeSet.getAttributeIntValue(i2, -1);
                        if (attributeIntValue == intArray[1]) {
                            this.f21070f.getView().setVerticalScrollBarEnabled(false);
                            view = this.f21070f.getView();
                        } else if (attributeIntValue == intArray[2]) {
                            this.f21070f.getView().setVerticalScrollBarEnabled(false);
                        } else if (attributeIntValue == intArray[3]) {
                            view = this.f21070f.getView();
                        }
                        view.setHorizontalScrollBarEnabled(false);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z2) throws Throwable {
        boolean zIsX5CoreSandboxMode;
        com.tencent.smtt.utils.s.b("old03");
        if (!this.f21074k && this.f21067a != 0) {
            com.tencent.smtt.utils.s.b("old04");
            this.f21074k = true;
            String string = "";
            String string2 = "";
            String string3 = "";
            if (this.f21069e) {
                com.tencent.smtt.utils.s.b("old05");
                Bundle sdkQBStatisticsInfo = this.f21070f.getX5WebViewExtension().getSdkQBStatisticsInfo();
                if (sdkQBStatisticsInfo != null) {
                    string = sdkQBStatisticsInfo.getString(TBSOneConfigurationKeys.GUID);
                    string2 = sdkQBStatisticsInfo.getString("qua2");
                    string3 = sdkQBStatisticsInfo.getString("lc");
                }
                com.tencent.smtt.utils.s.b("old06");
            }
            String str = string;
            String str2 = string2;
            String str3 = string3;
            if ("com.qzone".equals(this.f21073i.getApplicationInfo().packageName)) {
                int iE = e(this.f21073i);
                if (iE == -1) {
                    iE = this.f21067a;
                }
                this.f21067a = iE;
                f(this.f21073i);
            }
            try {
                com.tencent.smtt.utils.s.b("old07");
                zIsX5CoreSandboxMode = this.f21070f.getX5WebViewExtension().isX5CoreSandboxMode();
            } catch (Throwable th) {
                TbsLog.w("tbsWebviewDestroy", "exception: " + th);
                zIsX5CoreSandboxMode = false;
            }
            com.tencent.smtt.utils.s.b("old08");
            com.tencent.smtt.sdk.stat.b.a(this.f21073i, str, str2, str3, this.f21067a, this.f21069e, i(), zIsX5CoreSandboxMode);
            com.tencent.smtt.utils.s.b("old09");
            this.f21067a = 0;
            this.f21074k = false;
        }
        com.tencent.smtt.utils.s.b("old10");
        if (this.f21069e) {
            com.tencent.smtt.utils.s.b("old18");
            if (z2) {
                this.f21070f.destroy();
            }
            com.tencent.smtt.utils.s.b("old19");
        } else {
            try {
                com.tencent.smtt.utils.s.b("old11");
                Class<?> cls = Class.forName("android.webkit.WebViewClassic");
                Method method = cls.getMethod("fromWebView", android.webkit.WebView.class);
                method.setAccessible(true);
                Object objInvoke = method.invoke(null, this.f21071g);
                if (objInvoke != null) {
                    com.tencent.smtt.utils.s.b("old12");
                    Field declaredField = cls.getDeclaredField("mListBoxDialog");
                    declaredField.setAccessible(true);
                    Object obj = declaredField.get(objInvoke);
                    if (obj != null) {
                        com.tencent.smtt.utils.s.b("old13");
                        Dialog dialog = (Dialog) obj;
                        dialog.setOnCancelListener(null);
                        Class<?> cls2 = Class.forName("android.app.Dialog");
                        Field declaredField2 = cls2.getDeclaredField("CANCEL");
                        declaredField2.setAccessible(true);
                        int iIntValue = ((Integer) declaredField2.get(dialog)).intValue();
                        Field declaredField3 = cls2.getDeclaredField("mListenersHandler");
                        declaredField3.setAccessible(true);
                        ((Handler) declaredField3.get(dialog)).removeMessages(iIntValue);
                    }
                }
            } catch (Exception unused) {
            }
            com.tencent.smtt.utils.s.b("old14");
            if (z2) {
                this.f21071g.destroy();
            }
            try {
                com.tencent.smtt.utils.s.b("old15");
                TbsLog.i("sdkreport", "webview.tbsWebviewDestroy mQQMusicCrashFix is " + this.f21075l);
                if (this.f21075l) {
                    return;
                }
                Field declaredField4 = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                declaredField4.setAccessible(true);
                ComponentCallbacks componentCallbacks = (ComponentCallbacks) declaredField4.get(null);
                if (componentCallbacks != null) {
                    com.tencent.smtt.utils.s.b("old16");
                    declaredField4.set(null, null);
                    Field declaredField5 = Class.forName("android.view.ViewRoot").getDeclaredField("sConfigCallbacks");
                    declaredField5.setAccessible(true);
                    Object obj2 = declaredField5.get(null);
                    if (obj2 != null) {
                        List list = (List) obj2;
                        synchronized (list) {
                            list.remove(componentCallbacks);
                        }
                    }
                }
                com.tencent.smtt.utils.s.b("old17");
            } catch (Exception unused2) {
            }
        }
        TbsLog.i("WebView", "X5 GUID = " + QbSdk.b());
    }

    private boolean a(View view) {
        Object objA;
        Context context = this.f21073i;
        if ((context == null || getTbsCoreVersion(context) <= 36200) && (objA = com.tencent.smtt.utils.j.a(this.f21083x, "onLongClick", (Class<?>[]) new Class[]{View.class}, view)) != null) {
            return ((Boolean) objA).booleanValue();
        }
        return false;
    }

    private boolean a(WebChromeClient webChromeClient) throws NoSuchMethodException, SecurityException {
        if (webChromeClient == null) {
            return false;
        }
        boolean z2 = false;
        boolean z3 = false;
        for (Class<?> superclass = webChromeClient.getClass(); superclass != WebChromeClient.class && (!z2 || !z3); superclass = superclass.getSuperclass()) {
            if (!z2) {
                try {
                    superclass.getDeclaredMethod("onShowCustomView", View.class, IX5WebChromeClient.CustomViewCallback.class);
                    z2 = true;
                } catch (NoSuchMethodException unused) {
                }
            }
            if (!z3) {
                try {
                    superclass.getDeclaredMethod("onHideCustomView", new Class[0]);
                    z3 = true;
                } catch (NoSuchMethodException unused2) {
                }
            }
        }
        return z2 && z3;
    }

    private boolean b(Context context) {
        try {
            return context.getPackageName().indexOf("com.tencent.mobileqq") >= 0;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static void c() {
        try {
            new Thread(new Runnable() { // from class: com.tencent.smtt.sdk.WebView.8
                @Override // java.lang.Runnable
                public void run() throws Throwable {
                    if (WebView.f21062j == null) {
                        TbsLog.d("TbsNeedReboot", "WebView.updateNeeeRebootStatus--mAppContext == null");
                        return;
                    }
                    g gVarA = g.a(true);
                    if (g.f21180b) {
                        TbsLog.d("TbsNeedReboot", "WebView.updateNeeeRebootStatus--needReboot = true");
                        return;
                    }
                    m mVarA = m.a(WebView.f21062j);
                    int iC = mVarA.c();
                    TbsLog.d("TbsNeedReboot", "WebView.updateNeeeRebootStatus--installStatus = " + iC);
                    if (iC == 2) {
                        TbsLog.d("TbsNeedReboot", "WebView.updateNeeeRebootStatus--install setTbsNeedReboot true");
                        gVarA.a(String.valueOf(mVarA.b()));
                        gVarA.b(true);
                        return;
                    }
                    int iB = mVarA.b("copy_status");
                    TbsLog.d("TbsNeedReboot", "WebView.updateNeeeRebootStatus--copyStatus = " + iB);
                    if (iB == 1) {
                        TbsLog.d("TbsNeedReboot", "WebView.updateNeeeRebootStatus--copy setTbsNeedReboot true");
                        gVarA.a(String.valueOf(mVarA.c("copy_core_ver")));
                        gVarA.b(true);
                    } else {
                        if (w.a().b()) {
                            return;
                        }
                        if (iC == 3 || iB == 3) {
                            TbsLog.d("TbsNeedReboot", "WebView.updateNeeeRebootStatus--setTbsNeedReboot true");
                            gVarA.a(String.valueOf(g.d()));
                            gVarA.b(true);
                        }
                    }
                }
            }).start();
        } catch (Throwable th) {
            TbsLog.e("webview", "updateRebootStatus excpetion: " + th);
        }
    }

    private void c(Context context) {
        if (QbSdk.f20825i && TbsShareManager.isThirdPartyApp(context)) {
            TbsExtensionFunctionManager.getInstance().initTbsBuglyIfNeed(context);
        }
        w wVarA = w.a();
        wVarA.a(context);
        this.f21069e = wVarA.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Context d(Context context) {
        return context;
    }

    @Deprecated
    public static void disablePlatformNotifications() {
        if (w.a().b()) {
            return;
        }
        com.tencent.smtt.utils.j.a("android.webkit.WebView", "disablePlatformNotifications");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v10, types: [java.lang.String] */
    public int e(Context context) throws Throwable {
        FileLock fileLockA;
        StringBuilder sb;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        File file;
        ?? r6;
        String property;
        FileOutputStream fileOutputStreamB = FileUtil.b(context, true, "tbslock.txt");
        if (fileOutputStreamB == null || (fileLockA = FileUtil.a(context, fileOutputStreamB)) == null) {
            return -1;
        }
        Lock lock = f21060c;
        if (!lock.tryLock()) {
            FileUtil.a(fileLockA, fileOutputStreamB);
            return -1;
        }
        FileInputStream fileInputStream3 = null;
        fileInputStream3 = null;
        FileInputStream fileInputStream4 = null;
        try {
            try {
                file = new File(QbSdk.getTbsFolderDir(context) + File.separator + "core_private", "pv.db");
            } catch (Exception e2) {
                e = e2;
            }
            if (!file.exists()) {
                lock.unlock();
                FileUtil.a(fileLockA, fileOutputStreamB);
                return -1;
            }
            Properties properties = new Properties();
            FileInputStream fileInputStream5 = new FileInputStream(file);
            try {
                properties.load(fileInputStream5);
                fileInputStream5.close();
                r6 = "PV";
                property = properties.getProperty("PV");
            } catch (Exception e3) {
                e = e3;
                fileInputStream4 = fileInputStream5;
                TbsLog.e("getTbsCorePV", "TbsInstaller--getTbsCorePV Exception=" + e.toString());
                fileInputStream2 = fileInputStream4;
                if (fileInputStream4 != null) {
                    try {
                        fileInputStream4.close();
                        fileInputStream2 = fileInputStream4;
                    } catch (IOException e4) {
                        e = e4;
                        sb = new StringBuilder();
                        fileInputStream = fileInputStream4;
                        sb.append("TbsInstaller--getTbsCorePV IOException=");
                        sb.append(e.toString());
                        TbsLog.e("getTbsCorePV", sb.toString());
                        fileInputStream2 = fileInputStream;
                        f21060c.unlock();
                        fileInputStream3 = fileInputStream2;
                        FileUtil.a(fileLockA, fileOutputStreamB);
                        return -1;
                    }
                }
                f21060c.unlock();
                fileInputStream3 = fileInputStream2;
                FileUtil.a(fileLockA, fileOutputStreamB);
                return -1;
            } catch (Throwable th) {
                th = th;
                fileInputStream3 = fileInputStream5;
                if (fileInputStream3 != null) {
                    try {
                        fileInputStream3.close();
                    } catch (IOException e5) {
                        TbsLog.e("getTbsCorePV", "TbsInstaller--getTbsCorePV IOException=" + e5.toString());
                    }
                }
                f21060c.unlock();
                FileUtil.a(fileLockA, fileOutputStreamB);
                throw th;
            }
            if (property != null) {
                int i2 = Integer.parseInt(property);
                try {
                    fileInputStream5.close();
                } catch (IOException e6) {
                    TbsLog.e("getTbsCorePV", "TbsInstaller--getTbsCorePV IOException=" + e6.toString());
                }
                f21060c.unlock();
                FileUtil.a(fileLockA, fileOutputStreamB);
                return i2;
            }
            try {
                fileInputStream5.close();
                fileInputStream2 = r6;
            } catch (IOException e7) {
                e = e7;
                sb = new StringBuilder();
                fileInputStream = r6;
                sb.append("TbsInstaller--getTbsCorePV IOException=");
                sb.append(e.toString());
                TbsLog.e("getTbsCorePV", sb.toString());
                fileInputStream2 = fileInputStream;
                f21060c.unlock();
                fileInputStream3 = fileInputStream2;
                FileUtil.a(fileLockA, fileOutputStreamB);
                return -1;
            }
            f21060c.unlock();
            fileInputStream3 = fileInputStream2;
            FileUtil.a(fileLockA, fileOutputStreamB);
            return -1;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @Deprecated
    public static void enablePlatformNotifications() {
        if (w.a().b()) {
            return;
        }
        com.tencent.smtt.utils.j.a("android.webkit.WebView", "enablePlatformNotifications");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(Context context) {
        try {
            File file = new File(QbSdk.getTbsFolderDir(context) + File.separator + "core_private", "pv.db");
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e2) {
            TbsLog.i("getTbsCorePV", "TbsInstaller--getTbsCorePV Exception=" + e2.toString());
        }
    }

    @Deprecated
    public static String findAddress(String str) {
        if (w.a().b()) {
            return null;
        }
        return android.webkit.WebView.findAddress(str);
    }

    private void g() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        String str;
        synchronized (WebView.class) {
            com.tencent.smtt.utils.s.b("new01");
            if (!this.f21074k && this.f21067a != 0) {
                j();
            }
            com.tencent.smtt.utils.s.b("new02");
        }
        if (this.f21069e) {
            com.tencent.smtt.utils.s.b("new08");
            this.f21070f.destroy();
            str = "new09";
        } else {
            com.tencent.smtt.utils.s.b("new03");
            this.f21071g.destroy();
            com.tencent.smtt.utils.s.b("new04");
            try {
                TbsLog.i("sdkreport", "webview.destroyImplNow mQQMusicCrashFix is " + this.f21075l);
                if (this.f21075l) {
                    return;
                }
                Field declaredField = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                declaredField.setAccessible(true);
                ComponentCallbacks componentCallbacks = (ComponentCallbacks) declaredField.get(null);
                com.tencent.smtt.utils.s.b("new05");
                if (componentCallbacks != null) {
                    declaredField.set(null, null);
                    Field declaredField2 = Class.forName("android.view.ViewRoot").getDeclaredField("sConfigCallbacks");
                    declaredField2.setAccessible(true);
                    Object obj = declaredField2.get(null);
                    if (obj != null) {
                        List list = (List) obj;
                        synchronized (list) {
                            list.remove(componentCallbacks);
                        }
                    }
                }
                com.tencent.smtt.utils.s.b("new06");
                return;
            } catch (Exception unused) {
                str = "new07";
            }
        }
        com.tencent.smtt.utils.s.b(str);
    }

    public static String getCrashExtraCacheInfo(Context context) {
        Map<String, Object> map;
        if (context == null) {
            return "";
        }
        String str = "tbs_core_version:" + QbSdk.getTbsVersionForCrash(context) + com.alipay.sdk.util.h.f3376b + "tbs_sdk_version:44226" + com.alipay.sdk.util.h.f3376b;
        StringBuilder sb = new StringBuilder();
        sb.append(g.a(true).f());
        sb.append("\n");
        sb.append(str);
        if (!TbsShareManager.isThirdPartyApp(context) && (map = QbSdk.f20831o) != null && map.containsKey(TbsCoreSettings.TBS_SETTINGS_WEAPP_ID_KEY) && QbSdk.f20831o.containsKey(TbsCoreSettings.TBS_SETTINGS_WEAPP_NAME_KEY)) {
            String str2 = "weapp_id:" + QbSdk.f20831o.get(TbsCoreSettings.TBS_SETTINGS_WEAPP_ID_KEY) + com.alipay.sdk.util.h.f3376b + TbsCoreSettings.TBS_SETTINGS_WEAPP_NAME_KEY + ":" + QbSdk.f20831o.get(TbsCoreSettings.TBS_SETTINGS_WEAPP_NAME_KEY) + com.alipay.sdk.util.h.f3376b;
            sb.append("\n");
            sb.append(str2);
        }
        return sb.length() > 8192 ? sb.substring(sb.length() - 8192) : sb.toString();
    }

    public static String getCrashExtraMessage(Context context) {
        Map<String, Object> map;
        if (context == null) {
            return "";
        }
        String str = "tbs_core_version:" + QbSdk.getTbsVersionForCrash(context) + com.alipay.sdk.util.h.f3376b + "tbs_sdk_version:44226" + com.alipay.sdk.util.h.f3376b;
        StringBuilder sb = new StringBuilder();
        sb.append(g.a(true).e());
        sb.append("\n");
        sb.append(str);
        if (!TbsShareManager.isThirdPartyApp(context) && (map = QbSdk.f20831o) != null && map.containsKey(TbsCoreSettings.TBS_SETTINGS_WEAPP_ID_KEY) && QbSdk.f20831o.containsKey(TbsCoreSettings.TBS_SETTINGS_WEAPP_NAME_KEY)) {
            String str2 = "weapp_id:" + QbSdk.f20831o.get(TbsCoreSettings.TBS_SETTINGS_WEAPP_ID_KEY) + com.alipay.sdk.util.h.f3376b + TbsCoreSettings.TBS_SETTINGS_WEAPP_NAME_KEY + ":" + QbSdk.f20831o.get(TbsCoreSettings.TBS_SETTINGS_WEAPP_NAME_KEY) + com.alipay.sdk.util.h.f3376b;
            sb.append("\n");
            sb.append(str2);
        }
        return sb.length() > 8192 ? sb.substring(sb.length() - 8192) : sb.toString();
    }

    public static PackageInfo getCurrentWebViewPackage() {
        if (w.a().b() || Build.VERSION.SDK_INT < 26) {
            return null;
        }
        try {
            return (PackageInfo) com.tencent.smtt.utils.j.a("android.webkit.WebView", "getCurrentWebViewPackage");
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public static synchronized Object getPluginList() {
        if (w.a().b()) {
            return null;
        }
        return com.tencent.smtt.utils.j.a("android.webkit.WebView", "getPluginList");
    }

    public static int getTbsCoreVersion(Context context) {
        return QbSdk.getTbsVersion(context);
    }

    public static boolean getTbsNeedReboot() {
        c();
        return g.a(true).g();
    }

    public static int getTbsSDKVersion(Context context) {
        return 44226;
    }

    private void h() throws Throwable {
        try {
            com.tencent.smtt.utils.s.b("old01");
            if ("com.xunmeng.pinduoduo".equals(this.f21073i.getApplicationInfo().packageName)) {
                new Thread("WebviewDestroy") { // from class: com.tencent.smtt.sdk.WebView.2
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() throws Throwable {
                        WebView.this.a(false);
                    }
                }.start();
                if (this.f21069e) {
                    this.f21070f.destroy();
                } else {
                    this.f21071g.destroy();
                }
            } else {
                com.tencent.smtt.utils.s.b("old02");
                a(true);
            }
        } catch (Throwable unused) {
            com.tencent.smtt.utils.s.b("old30");
            a(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long i() {
        long j2;
        synchronized (QbSdk.f20824h) {
            if (QbSdk.f20821e) {
                QbSdk.f20823g += System.currentTimeMillis() - QbSdk.f20822f;
                TbsLog.d("sdkreport", "pv report, WebView.getWifiConnectedTime QbSdk.sWifiConnectedTime=" + QbSdk.f20823g);
            }
            j2 = QbSdk.f20823g / 1000;
            QbSdk.f20823g = 0L;
            QbSdk.f20822f = System.currentTimeMillis();
        }
        return j2;
    }

    private void j() {
        new Thread(new Runnable() { // from class: com.tencent.smtt.sdk.WebView.7
            @Override // java.lang.Runnable
            public void run() {
                boolean zIsX5CoreSandboxMode;
                Bundle sdkQBStatisticsInfo;
                if (WebView.this.f21074k || WebView.this.f21067a == 0) {
                    return;
                }
                synchronized (WebView.class) {
                    if (!WebView.this.f21074k && WebView.this.f21067a != 0) {
                        WebView.this.f21074k = true;
                        String string = "";
                        String string2 = "";
                        String string3 = "";
                        if (WebView.this.f21069e && (sdkQBStatisticsInfo = WebView.this.f21070f.getX5WebViewExtension().getSdkQBStatisticsInfo()) != null) {
                            string = sdkQBStatisticsInfo.getString(TBSOneConfigurationKeys.GUID);
                            string2 = sdkQBStatisticsInfo.getString("qua2");
                            string3 = sdkQBStatisticsInfo.getString("lc");
                        }
                        String str = string2;
                        String str2 = string3;
                        String str3 = string;
                        if ("com.qzone".equals(WebView.this.f21073i.getApplicationInfo().packageName)) {
                            WebView webView = WebView.this;
                            int iE = webView.e(webView.f21073i);
                            WebView webView2 = WebView.this;
                            if (iE == -1) {
                                iE = webView2.f21067a;
                            }
                            webView2.f21067a = iE;
                            WebView webView3 = WebView.this;
                            webView3.f(webView3.f21073i);
                        }
                        try {
                            zIsX5CoreSandboxMode = WebView.this.f21070f.getX5WebViewExtension().isX5CoreSandboxMode();
                        } catch (Throwable th) {
                            TbsLog.w("onVisibilityChanged", "exception: " + th);
                            zIsX5CoreSandboxMode = false;
                        }
                        com.tencent.smtt.sdk.stat.b.a(WebView.this.f21073i, str3, str, str2, WebView.this.f21067a, WebView.this.f21069e, WebView.this.i(), zIsX5CoreSandboxMode);
                        WebView.this.f21067a = 0;
                        WebView.this.f21074k = false;
                    }
                }
            }
        }).start();
    }

    public static void setDataDirectorySuffix(String str) {
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                com.tencent.smtt.utils.j.a(Class.forName("android.webkit.WebView"), "setDataDirectorySuffix", (Class<?>[]) new Class[]{String.class}, str);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        HashMap map = new HashMap();
        map.put("data_directory_suffix", str);
        QbSdk.initTbsSettings(map);
    }

    public static synchronized void setSysDayOrNight(boolean z2) {
        int i2;
        Paint paint;
        if (z2 == f21066w) {
            return;
        }
        f21066w = z2;
        if (f21065v == null) {
            Paint paint2 = new Paint();
            f21065v = paint2;
            paint2.setColor(-16777216);
        }
        if (z2) {
            i2 = 255;
            if (f21065v.getAlpha() != 255) {
                paint = f21065v;
                paint.setAlpha(i2);
            }
        }
        int alpha = f21065v.getAlpha();
        i2 = NIGHT_MODE_ALPHA;
        if (alpha != i2) {
            paint = f21065v;
            paint.setAlpha(i2);
        }
    }

    public static void setWebContentsDebuggingEnabled(boolean z2) {
        w wVarA = w.a();
        if (wVarA != null && wVarA.b()) {
            wVarA.c().a(z2);
            return;
        }
        try {
            Method declaredMethod = Class.forName("android.webkit.WebView").getDeclaredMethod("setWebContentsDebuggingEnabled", Boolean.TYPE);
            f21063m = declaredMethod;
            if (declaredMethod != null) {
                declaredMethod.setAccessible(true);
                f21063m.invoke(null, Boolean.valueOf(z2));
            }
        } catch (Exception e2) {
            TbsLog.e("QbSdk", "Exception:" + e2.getStackTrace());
            e2.printStackTrace();
        }
    }

    public android.webkit.WebView a() {
        if (this.f21069e) {
            return null;
        }
        return this.f21071g;
    }

    /* JADX WARN: Finally extract failed */
    public void a(Context context) throws Throwable {
        String str;
        int iE = e(context);
        if (iE != -1) {
            str = "PV=" + String.valueOf(iE + 1);
        } else {
            str = "PV=1";
        }
        File file = new File(QbSdk.getTbsFolderDir(context) + File.separator + "core_private", "pv.db");
        try {
            try {
                file.getParentFile().mkdirs();
                if (!file.isFile() || !file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                f21061d = fileOutputStream;
                fileOutputStream.write(str.getBytes());
                OutputStream outputStream = f21061d;
                if (outputStream != null) {
                    outputStream.flush();
                }
            } catch (Throwable th) {
                OutputStream outputStream2 = f21061d;
                if (outputStream2 != null) {
                    outputStream2.flush();
                }
                throw th;
            }
        } catch (Throwable unused) {
        }
    }

    public void a(android.webkit.WebView webView) {
    }

    public void a(IX5WebViewBase iX5WebViewBase) {
        this.f21070f = iX5WebViewBase;
    }

    public void addJavascriptInterface(Object obj, String str) {
        if (this.f21069e) {
            this.f21070f.addJavascriptInterface(obj, str);
        } else {
            this.f21071g.addJavascriptInterface(obj, str);
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View view) {
        if (!this.f21069e) {
            this.f21071g.addView(view);
            return;
        }
        View view2 = this.f21070f.getView();
        try {
            Method methodA = com.tencent.smtt.utils.j.a(view2, "addView", View.class);
            methodA.setAccessible(true);
            methodA.invoke(view2, view);
        } catch (Throwable unused) {
        }
    }

    public IX5WebViewBase b() {
        return this.f21070f;
    }

    public boolean canGoBack() {
        return !this.f21069e ? this.f21071g.canGoBack() : this.f21070f.canGoBack();
    }

    public boolean canGoBackOrForward(int i2) {
        return !this.f21069e ? this.f21071g.canGoBackOrForward(i2) : this.f21070f.canGoBackOrForward(i2);
    }

    public boolean canGoForward() {
        return !this.f21069e ? this.f21071g.canGoForward() : this.f21070f.canGoForward();
    }

    @Deprecated
    public boolean canZoomIn() {
        if (this.f21069e) {
            return this.f21070f.canZoomIn();
        }
        Object objA = com.tencent.smtt.utils.j.a(this.f21071g, "canZoomIn");
        if (objA == null) {
            return false;
        }
        return ((Boolean) objA).booleanValue();
    }

    @Deprecated
    public boolean canZoomOut() {
        if (this.f21069e) {
            return this.f21070f.canZoomOut();
        }
        Object objA = com.tencent.smtt.utils.j.a(this.f21071g, "canZoomOut");
        if (objA == null) {
            return false;
        }
        return ((Boolean) objA).booleanValue();
    }

    @Deprecated
    public Picture capturePicture() {
        if (this.f21069e) {
            return this.f21070f.capturePicture();
        }
        Object objA = com.tencent.smtt.utils.j.a(this.f21071g, "capturePicture");
        if (objA == null) {
            return null;
        }
        return (Picture) objA;
    }

    public void clearCache(boolean z2) {
        if (this.f21069e) {
            this.f21070f.clearCache(z2);
        } else {
            this.f21071g.clearCache(z2);
        }
    }

    public void clearFormData() {
        if (this.f21069e) {
            this.f21070f.clearFormData();
        } else {
            this.f21071g.clearFormData();
        }
    }

    public void clearHistory() {
        if (this.f21069e) {
            this.f21070f.clearHistory();
        } else {
            this.f21071g.clearHistory();
        }
    }

    @TargetApi(3)
    public void clearMatches() {
        if (this.f21069e) {
            this.f21070f.clearMatches();
        } else {
            this.f21071g.clearMatches();
        }
    }

    public void clearSslPreferences() {
        if (this.f21069e) {
            this.f21070f.clearSslPreferences();
        } else {
            this.f21071g.clearSslPreferences();
        }
    }

    @Deprecated
    public void clearView() {
        if (this.f21069e) {
            this.f21070f.clearView();
        } else {
            com.tencent.smtt.utils.j.a(this.f21071g, "clearView");
        }
    }

    @Override // android.view.View
    public int computeHorizontalScrollExtent() throws SecurityException {
        try {
            if (this.f21069e) {
                Method methodA = com.tencent.smtt.utils.j.a(this.f21070f.getView(), "computeHorizontalScrollExtent", new Class[0]);
                methodA.setAccessible(true);
                return ((Integer) methodA.invoke(this.f21070f.getView(), new Object[0])).intValue();
            }
            Method methodA2 = com.tencent.smtt.utils.j.a(this.f21071g, "computeHorizontalScrollExtent", new Class[0]);
            methodA2.setAccessible(true);
            return ((Integer) methodA2.invoke(this.f21071g, new Object[0])).intValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    @Override // android.view.View
    public int computeHorizontalScrollOffset() throws SecurityException {
        try {
            if (this.f21069e) {
                Method methodA = com.tencent.smtt.utils.j.a(this.f21070f.getView(), "computeHorizontalScrollOffset", new Class[0]);
                methodA.setAccessible(true);
                return ((Integer) methodA.invoke(this.f21070f.getView(), new Object[0])).intValue();
            }
            Method methodA2 = com.tencent.smtt.utils.j.a(this.f21071g, "computeHorizontalScrollOffset", new Class[0]);
            methodA2.setAccessible(true);
            return ((Integer) methodA2.invoke(this.f21071g, new Object[0])).intValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    @Override // android.view.View
    public int computeHorizontalScrollRange() throws SecurityException {
        try {
            if (this.f21069e) {
                return ((Integer) com.tencent.smtt.utils.j.a(this.f21070f.getView(), "computeHorizontalScrollRange", (Class<?>[]) new Class[0], new Object[0])).intValue();
            }
            Method methodA = com.tencent.smtt.utils.j.a(this.f21071g, "computeHorizontalScrollRange", new Class[0]);
            methodA.setAccessible(true);
            return ((Integer) methodA.invoke(this.f21071g, new Object[0])).intValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.f21069e) {
            this.f21070f.computeScroll();
        } else {
            this.f21071g.computeScroll();
        }
    }

    @Override // android.view.View
    public int computeVerticalScrollExtent() throws SecurityException {
        try {
            if (this.f21069e) {
                Method methodA = com.tencent.smtt.utils.j.a(this.f21070f.getView(), "computeVerticalScrollExtent", new Class[0]);
                methodA.setAccessible(true);
                return ((Integer) methodA.invoke(this.f21070f.getView(), new Object[0])).intValue();
            }
            Method methodA2 = com.tencent.smtt.utils.j.a(this.f21071g, "computeVerticalScrollExtent", new Class[0]);
            methodA2.setAccessible(true);
            return ((Integer) methodA2.invoke(this.f21071g, new Object[0])).intValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    @Override // android.view.View
    public int computeVerticalScrollOffset() throws SecurityException {
        try {
            if (this.f21069e) {
                Method methodA = com.tencent.smtt.utils.j.a(this.f21070f.getView(), "computeVerticalScrollOffset", new Class[0]);
                methodA.setAccessible(true);
                return ((Integer) methodA.invoke(this.f21070f.getView(), new Object[0])).intValue();
            }
            Method methodA2 = com.tencent.smtt.utils.j.a(this.f21071g, "computeVerticalScrollOffset", new Class[0]);
            methodA2.setAccessible(true);
            return ((Integer) methodA2.invoke(this.f21071g, new Object[0])).intValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    @Override // android.view.View
    public int computeVerticalScrollRange() throws SecurityException {
        try {
            if (this.f21069e) {
                return ((Integer) com.tencent.smtt.utils.j.a(this.f21070f.getView(), "computeVerticalScrollRange", (Class<?>[]) new Class[0], new Object[0])).intValue();
            }
            Method methodA = com.tencent.smtt.utils.j.a(this.f21071g, "computeVerticalScrollRange", new Class[0]);
            methodA.setAccessible(true);
            return ((Integer) methodA.invoke(this.f21071g, new Object[0])).intValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public WebBackForwardList copyBackForwardList() {
        return this.f21069e ? WebBackForwardList.a(this.f21070f.copyBackForwardList()) : WebBackForwardList.a(this.f21071g.copyBackForwardList());
    }

    public Object createPrintDocumentAdapter(String str) {
        if (!this.f21069e) {
            return com.tencent.smtt.utils.j.a(this.f21071g, "createPrintDocumentAdapter", (Class<?>[]) new Class[]{String.class}, str);
        }
        try {
            return this.f21070f.createPrintDocumentAdapter(str);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public void customDiskCachePathEnabled(boolean z2, String str) {
        if (!this.f21069e || getX5WebViewExtension() == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("enabled", z2);
        bundle.putString("path", str);
        getX5WebViewExtension().invokeMiscMethod("customDiskCachePathEnabled", bundle);
    }

    public void destroy() {
        this.f21075l = false;
        try {
            this.f21073i.getApplicationInfo().packageName.contains("com.tencent.qqmusic");
        } catch (Throwable th) {
            TbsLog.i("webview", "stack is " + Log.getStackTraceString(th));
        }
        TbsLog.i("webview", "destroy forceDestoyOld is false");
        g();
    }

    public void documentHasImages(Message message) {
        if (this.f21069e) {
            this.f21070f.documentHasImages(message);
        } else {
            this.f21071g.documentHasImages(message);
        }
    }

    public void dumpViewHierarchyWithProperties(BufferedWriter bufferedWriter, int i2) {
        if (this.f21069e) {
            this.f21070f.dumpViewHierarchyWithProperties(bufferedWriter, i2);
        } else {
            com.tencent.smtt.utils.j.a(this.f21071g, "dumpViewHierarchyWithProperties", (Class<?>[]) new Class[]{BufferedWriter.class, Integer.TYPE}, bufferedWriter, Integer.valueOf(i2));
        }
    }

    public void evaluateJavascript(String str, ValueCallback<String> valueCallback) {
        if (!this.f21069e) {
            try {
                Method declaredMethod = Class.forName("android.webkit.WebView").getDeclaredMethod("evaluateJavascript", String.class, android.webkit.ValueCallback.class);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(this.f21071g, str, valueCallback);
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        try {
            Method methodA = com.tencent.smtt.utils.j.a(this.f21070f.getView(), "evaluateJavascript", String.class, android.webkit.ValueCallback.class);
            methodA.setAccessible(true);
            methodA.invoke(this.f21070f.getView(), str, valueCallback);
        } catch (Exception e3) {
            e3.printStackTrace();
            loadUrl(str);
        }
    }

    @Deprecated
    public int findAll(String str) {
        if (this.f21069e) {
            return this.f21070f.findAll(str);
        }
        Object objA = com.tencent.smtt.utils.j.a(this.f21071g, "findAll", (Class<?>[]) new Class[]{String.class}, str);
        if (objA == null) {
            return 0;
        }
        return ((Integer) objA).intValue();
    }

    @TargetApi(16)
    public void findAllAsync(String str) {
        if (this.f21069e) {
            this.f21070f.findAllAsync(str);
        } else {
            com.tencent.smtt.utils.j.a(this.f21071g, "findAllAsync", (Class<?>[]) new Class[]{String.class}, str);
        }
    }

    public View findHierarchyView(String str, int i2) {
        return !this.f21069e ? (View) com.tencent.smtt.utils.j.a(this.f21071g, "findHierarchyView", (Class<?>[]) new Class[]{String.class, Integer.TYPE}, str, Integer.valueOf(i2)) : this.f21070f.findHierarchyView(str, i2);
    }

    @TargetApi(3)
    public void findNext(boolean z2) {
        if (this.f21069e) {
            this.f21070f.findNext(z2);
        } else {
            this.f21071g.findNext(z2);
        }
    }

    public void flingScroll(int i2, int i3) {
        if (this.f21069e) {
            this.f21070f.flingScroll(i2, i3);
        } else {
            this.f21071g.flingScroll(i2, i3);
        }
    }

    @Deprecated
    public void freeMemory() {
        if (this.f21069e) {
            this.f21070f.freeMemory();
        } else {
            com.tencent.smtt.utils.j.a(this.f21071g, "freeMemory");
        }
    }

    public SslCertificate getCertificate() {
        return !this.f21069e ? this.f21071g.getCertificate() : this.f21070f.getCertificate();
    }

    public int getContentHeight() {
        return !this.f21069e ? this.f21071g.getContentHeight() : this.f21070f.getContentHeight();
    }

    public int getContentWidth() {
        if (this.f21069e) {
            return this.f21070f.getContentWidth();
        }
        Object objA = com.tencent.smtt.utils.j.a(this.f21071g, "getContentWidth");
        if (objA == null) {
            return 0;
        }
        return ((Integer) objA).intValue();
    }

    public Bitmap getFavicon() {
        return !this.f21069e ? this.f21071g.getFavicon() : this.f21070f.getFavicon();
    }

    public HitTestResult getHitTestResult() {
        return !this.f21069e ? new HitTestResult(this.f21071g.getHitTestResult()) : new HitTestResult(this.f21070f.getHitTestResult());
    }

    public String[] getHttpAuthUsernamePassword(String str, String str2) {
        return !this.f21069e ? this.f21071g.getHttpAuthUsernamePassword(str, str2) : this.f21070f.getHttpAuthUsernamePassword(str, str2);
    }

    public boolean getIsX5Core() {
        return this.f21069e;
    }

    @TargetApi(3)
    public String getOriginalUrl() {
        return !this.f21069e ? this.f21071g.getOriginalUrl() : this.f21070f.getOriginalUrl();
    }

    public int getProgress() {
        return !this.f21069e ? this.f21071g.getProgress() : this.f21070f.getProgress();
    }

    public boolean getRendererPriorityWaivedWhenNotVisible() {
        Object objA;
        try {
            if (!this.f21069e && Build.VERSION.SDK_INT >= 26 && (objA = com.tencent.smtt.utils.j.a(this.f21071g, "getRendererPriorityWaivedWhenNotVisible")) != null) {
                return ((Boolean) objA).booleanValue();
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int getRendererRequestedPriority() {
        Object objA;
        try {
            if (!this.f21069e && Build.VERSION.SDK_INT >= 26 && (objA = com.tencent.smtt.utils.j.a(this.f21071g, "getRendererRequestedPriority")) != null) {
                return ((Integer) objA).intValue();
            }
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    @Deprecated
    public float getScale() {
        if (this.f21069e) {
            return this.f21070f.getScale();
        }
        Object objA = com.tencent.smtt.utils.j.a(this.f21071g, "getScale");
        if (objA == null) {
            return 0.0f;
        }
        return ((Float) objA).floatValue();
    }

    @Override // android.view.View
    public int getScrollBarDefaultDelayBeforeFade() {
        if (getView() == null) {
            return 0;
        }
        return getView().getScrollBarDefaultDelayBeforeFade();
    }

    @Override // android.view.View
    public int getScrollBarFadeDuration() {
        if (getView() == null) {
            return 0;
        }
        return getView().getScrollBarFadeDuration();
    }

    @Override // android.view.View
    public int getScrollBarSize() {
        if (getView() == null) {
            return 0;
        }
        return getView().getScrollBarSize();
    }

    @Override // android.view.View
    public int getScrollBarStyle() {
        if (getView() == null) {
            return 0;
        }
        return getView().getScrollBarStyle();
    }

    public WebSettings getSettings() {
        WebSettings webSettings = this.f21072h;
        if (webSettings != null) {
            return webSettings;
        }
        WebSettings webSettings2 = this.f21069e ? new WebSettings(this.f21070f.getSettings()) : new WebSettings(this.f21071g.getSettings());
        this.f21072h = webSettings2;
        return webSettings2;
    }

    public IX5WebSettingsExtension getSettingsExtension() {
        if (this.f21069e) {
            return this.f21070f.getX5WebViewExtension().getSettingsExtension();
        }
        return null;
    }

    public int getSysNightModeAlpha() {
        return NIGHT_MODE_ALPHA;
    }

    public String getTitle() {
        return !this.f21069e ? this.f21071g.getTitle() : this.f21070f.getTitle();
    }

    public String getUrl() {
        return !this.f21069e ? this.f21071g.getUrl() : this.f21070f.getUrl();
    }

    public View getView() {
        return !this.f21069e ? this.f21071g : this.f21070f.getView();
    }

    public int getVisibleTitleHeight() {
        if (this.f21069e) {
            return this.f21070f.getVisibleTitleHeight();
        }
        Object objA = com.tencent.smtt.utils.j.a(this.f21071g, "getVisibleTitleHeight");
        if (objA == null) {
            return 0;
        }
        return ((Integer) objA).intValue();
    }

    public WebChromeClient getWebChromeClient() {
        return this.f21077o;
    }

    public IX5WebChromeClientExtension getWebChromeClientExtension() {
        if (this.f21069e) {
            return this.f21070f.getX5WebViewExtension().getWebChromeClientExtension();
        }
        return null;
    }

    public int getWebScrollX() {
        return (this.f21069e ? this.f21070f.getView() : this.f21071g).getScrollX();
    }

    public int getWebScrollY() {
        return (this.f21069e ? this.f21070f.getView() : this.f21071g).getScrollY();
    }

    public WebViewClient getWebViewClient() {
        return this.f21076n;
    }

    public IX5WebViewClientExtension getWebViewClientExtension() {
        if (this.f21069e) {
            return this.f21070f.getX5WebViewExtension().getWebViewClientExtension();
        }
        return null;
    }

    public IX5WebViewBase.HitTestResult getX5HitTestResult() {
        if (this.f21069e) {
            return this.f21070f.getHitTestResult();
        }
        return null;
    }

    public IX5WebViewExtension getX5WebViewExtension() {
        if (this.f21069e) {
            return this.f21070f.getX5WebViewExtension();
        }
        return null;
    }

    @Deprecated
    public View getZoomControls() {
        return !this.f21069e ? (View) com.tencent.smtt.utils.j.a(this.f21071g, "getZoomControls") : this.f21070f.getZoomControls();
    }

    public void goBack() {
        if (this.f21069e) {
            this.f21070f.goBack();
        } else {
            this.f21071g.goBack();
        }
    }

    public void goBackOrForward(int i2) {
        if (this.f21069e) {
            this.f21070f.goBackOrForward(i2);
        } else {
            this.f21071g.goBackOrForward(i2);
        }
    }

    public void goForward() {
        if (this.f21069e) {
            this.f21070f.goForward();
        } else {
            this.f21071g.goForward();
        }
    }

    public void invokeZoomPicker() {
        if (this.f21069e) {
            this.f21070f.invokeZoomPicker();
        } else {
            this.f21071g.invokeZoomPicker();
        }
    }

    public boolean isDayMode() {
        return f21066w;
    }

    public boolean isPrivateBrowsingEnabled() {
        if (this.f21069e) {
            return this.f21070f.isPrivateBrowsingEnable();
        }
        Object objA = com.tencent.smtt.utils.j.a(this.f21071g, "isPrivateBrowsingEnabled");
        if (objA == null) {
            return false;
        }
        return ((Boolean) objA).booleanValue();
    }

    public void loadData(String str, String str2, String str3) {
        if (this.f21069e) {
            this.f21070f.loadData(str, str2, str3);
        } else {
            this.f21071g.loadData(str, str2, str3);
        }
    }

    public void loadDataWithBaseURL(String str, String str2, String str3, String str4, String str5) {
        if (this.f21069e) {
            this.f21070f.loadDataWithBaseURL(str, str2, str3, str4, str5);
        } else {
            this.f21071g.loadDataWithBaseURL(str, str2, str3, str4, str5);
        }
    }

    public void loadUrl(String str) {
        if (str == null || showDebugView(str)) {
            return;
        }
        if (this.f21069e) {
            this.f21070f.loadUrl(str);
        } else {
            this.f21071g.loadUrl(str);
        }
    }

    @TargetApi(8)
    public void loadUrl(String str, Map<String, String> map) {
        if (str == null || showDebugView(str)) {
            return;
        }
        if (this.f21069e) {
            this.f21070f.loadUrl(str, map);
        } else {
            this.f21071g.loadUrl(str, map);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.f21074k || this.f21067a == 0) {
            return;
        }
        j();
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        View.OnLongClickListener onLongClickListener = this.f21084y;
        if (onLongClickListener == null || !onLongClickListener.onLongClick(view)) {
            return a(view);
        }
        return true;
    }

    public void onPause() {
        if (this.f21069e) {
            this.f21070f.onPause();
        } else {
            com.tencent.smtt.utils.j.a(this.f21071g, "onPause");
        }
    }

    public void onResume() {
        if (this.f21069e) {
            this.f21070f.onResume();
        } else {
            com.tencent.smtt.utils.j.a(this.f21071g, "onResume");
        }
    }

    @Override // android.view.View
    @TargetApi(11)
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (!b(this.f21073i) || !isHardwareAccelerated() || i2 <= 0 || i3 <= 0) {
            return;
        }
        getLayerType();
    }

    @Override // android.view.View
    public void onVisibilityChanged(View view, int i2) {
        Context context = this.f21073i;
        if (context == null) {
            super.onVisibilityChanged(view, i2);
            return;
        }
        if (f21064p == null) {
            f21064p = context.getApplicationInfo().packageName;
        }
        String str = f21064p;
        if (str != null && (str.equals("com.tencent.mm") || f21064p.equals("com.tencent.mobileqq"))) {
            super.onVisibilityChanged(view, i2);
            return;
        }
        if (i2 != 0 && !this.f21074k && this.f21067a != 0) {
            j();
        }
        super.onVisibilityChanged(view, i2);
    }

    public boolean overlayHorizontalScrollbar() {
        return !this.f21069e ? this.f21071g.overlayHorizontalScrollbar() : this.f21070f.overlayHorizontalScrollbar();
    }

    public boolean overlayVerticalScrollbar() {
        return this.f21069e ? this.f21070f.overlayVerticalScrollbar() : this.f21071g.overlayVerticalScrollbar();
    }

    public boolean pageDown(boolean z2) {
        return !this.f21069e ? this.f21071g.pageDown(z2) : this.f21070f.pageDown(z2, -1);
    }

    public boolean pageUp(boolean z2) {
        return !this.f21069e ? this.f21071g.pageUp(z2) : this.f21070f.pageUp(z2, -1);
    }

    public void pauseTimers() {
        if (this.f21069e) {
            this.f21070f.pauseTimers();
        } else {
            this.f21071g.pauseTimers();
        }
    }

    @TargetApi(5)
    public void postUrl(String str, byte[] bArr) {
        if (this.f21069e) {
            this.f21070f.postUrl(str, bArr);
        } else {
            this.f21071g.postUrl(str, bArr);
        }
    }

    @Deprecated
    public void refreshPlugins(boolean z2) {
        if (this.f21069e) {
            this.f21070f.refreshPlugins(z2);
        } else {
            com.tencent.smtt.utils.j.a(this.f21071g, "refreshPlugins", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z2));
        }
    }

    public void reload() {
        if (this.f21069e) {
            this.f21070f.reload();
        } else {
            this.f21071g.reload();
        }
    }

    @TargetApi(11)
    public void removeJavascriptInterface(String str) {
        if (this.f21069e) {
            this.f21070f.removeJavascriptInterface(str);
        } else {
            com.tencent.smtt.utils.j.a(this.f21071g, "removeJavascriptInterface", (Class<?>[]) new Class[]{String.class}, str);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(View view) {
        if (!this.f21069e) {
            this.f21071g.removeView(view);
            return;
        }
        View view2 = this.f21070f.getView();
        try {
            Method methodA = com.tencent.smtt.utils.j.a(view2, "removeView", View.class);
            methodA.setAccessible(true);
            methodA.invoke(view2, view);
        } catch (Throwable unused) {
        }
    }

    public JSONObject reportInitPerformance(long j2, int i2, long j3, long j4) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("IS_X5", this.f21069e);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z2) {
        if (!this.f21069e) {
            a aVar = this.f21071g;
            if (view == this) {
                view = aVar;
            }
            return aVar.requestChildRectangleOnScreen(view, rect, z2);
        }
        View view2 = this.f21070f.getView();
        if (!(view2 instanceof ViewGroup)) {
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) view2;
        if (view == this) {
            view = view2;
        }
        return viewGroup.requestChildRectangleOnScreen(view, rect, z2);
    }

    public void requestFocusNodeHref(Message message) {
        if (this.f21069e) {
            this.f21070f.requestFocusNodeHref(message);
        } else {
            this.f21071g.requestFocusNodeHref(message);
        }
    }

    public void requestImageRef(Message message) {
        if (this.f21069e) {
            this.f21070f.requestImageRef(message);
        } else {
            this.f21071g.requestImageRef(message);
        }
    }

    @Deprecated
    public boolean restorePicture(Bundle bundle, File file) {
        if (this.f21069e) {
            return this.f21070f.restorePicture(bundle, file);
        }
        Object objA = com.tencent.smtt.utils.j.a(this.f21071g, "restorePicture", (Class<?>[]) new Class[]{Bundle.class, File.class}, bundle, file);
        if (objA == null) {
            return false;
        }
        return ((Boolean) objA).booleanValue();
    }

    public WebBackForwardList restoreState(Bundle bundle) {
        return !this.f21069e ? WebBackForwardList.a(this.f21071g.restoreState(bundle)) : WebBackForwardList.a(this.f21070f.restoreState(bundle));
    }

    public void resumeTimers() {
        if (this.f21069e) {
            this.f21070f.resumeTimers();
        } else {
            this.f21071g.resumeTimers();
        }
    }

    @Deprecated
    public void savePassword(String str, String str2, String str3) {
        if (this.f21069e) {
            this.f21070f.savePassword(str, str2, str3);
        } else {
            com.tencent.smtt.utils.j.a(this.f21071g, "savePassword", (Class<?>[]) new Class[]{String.class, String.class, String.class}, str, str2, str3);
        }
    }

    @Deprecated
    public boolean savePicture(Bundle bundle, File file) {
        if (this.f21069e) {
            return this.f21070f.savePicture(bundle, file);
        }
        Object objA = com.tencent.smtt.utils.j.a(this.f21071g, "savePicture", (Class<?>[]) new Class[]{Bundle.class, File.class}, bundle, file);
        if (objA == null) {
            return false;
        }
        return ((Boolean) objA).booleanValue();
    }

    public WebBackForwardList saveState(Bundle bundle) {
        return !this.f21069e ? WebBackForwardList.a(this.f21071g.saveState(bundle)) : WebBackForwardList.a(this.f21070f.saveState(bundle));
    }

    @TargetApi(11)
    public void saveWebArchive(String str) {
        if (this.f21069e) {
            this.f21070f.saveWebArchive(str);
        } else {
            com.tencent.smtt.utils.j.a(this.f21071g, "saveWebArchive", (Class<?>[]) new Class[]{String.class}, str);
        }
    }

    @TargetApi(11)
    public void saveWebArchive(String str, boolean z2, ValueCallback<String> valueCallback) {
        if (this.f21069e) {
            this.f21070f.saveWebArchive(str, z2, valueCallback);
        } else {
            com.tencent.smtt.utils.j.a(this.f21071g, "saveWebArchive", (Class<?>[]) new Class[]{String.class, Boolean.TYPE, android.webkit.ValueCallback.class}, str, Boolean.valueOf(z2), valueCallback);
        }
    }

    public void setARModeEnable(boolean z2) {
        try {
            if (this.f21069e) {
                getSettingsExtension().setARModeEnable(z2);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override // android.view.View
    public void setBackgroundColor(int i2) {
        if (this.f21069e) {
            this.f21070f.setBackgroundColor(i2);
        } else {
            this.f21071g.setBackgroundColor(i2);
        }
        super.setBackgroundColor(i2);
    }

    @Deprecated
    public void setCertificate(SslCertificate sslCertificate) {
        if (this.f21069e) {
            this.f21070f.setCertificate(sslCertificate);
        } else {
            this.f21071g.setCertificate(sslCertificate);
        }
    }

    public void setDayOrNight(boolean z2) {
        try {
            if (this.f21069e) {
                getSettingsExtension().setDayOrNight(z2);
            }
            setSysDayOrNight(z2);
            getView().postInvalidate();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void setDownloadListener(final DownloadListener downloadListener) {
        boolean z2 = this.f21069e;
        if (z2) {
            this.f21070f.setDownloadListener(new b(this, downloadListener, z2));
        } else {
            this.f21071g.setDownloadListener(new android.webkit.DownloadListener() { // from class: com.tencent.smtt.sdk.WebView.4
                @Override // android.webkit.DownloadListener
                public void onDownloadStart(String str, String str2, String str3, String str4, long j2) throws PackageManager.NameNotFoundException {
                    DownloadListener downloadListener2 = downloadListener;
                    if (downloadListener2 != null) {
                        downloadListener2.onDownloadStart(str, str2, str3, str4, j2);
                        return;
                    }
                    ApplicationInfo applicationInfo = WebView.this.f21073i == null ? null : WebView.this.f21073i.getApplicationInfo();
                    if (applicationInfo == null || !"com.tencent.mm".equals(applicationInfo.packageName)) {
                        MttLoader.loadUrl(WebView.this.f21073i, str, null, null);
                    }
                }
            });
        }
    }

    @TargetApi(16)
    public void setFindListener(final IX5WebViewBase.FindListener findListener) {
        if (this.f21069e) {
            this.f21070f.setFindListener(findListener);
        } else {
            this.f21071g.setFindListener(new WebView.FindListener() { // from class: com.tencent.smtt.sdk.WebView.3
                @Override // android.webkit.WebView.FindListener
                public void onFindResultReceived(int i2, int i3, boolean z2) {
                    findListener.onFindResultReceived(i2, i3, z2);
                }
            });
        }
    }

    public void setHorizontalScrollbarOverlay(boolean z2) {
        if (this.f21069e) {
            this.f21070f.setHorizontalScrollbarOverlay(z2);
        } else {
            this.f21071g.setHorizontalScrollbarOverlay(z2);
        }
    }

    public void setHttpAuthUsernamePassword(String str, String str2, String str3, String str4) {
        if (this.f21069e) {
            this.f21070f.setHttpAuthUsernamePassword(str, str2, str3, str4);
        } else {
            this.f21071g.setHttpAuthUsernamePassword(str, str2, str3, str4);
        }
    }

    public void setInitialScale(int i2) {
        if (this.f21069e) {
            this.f21070f.setInitialScale(i2);
        } else {
            this.f21071g.setInitialScale(i2);
        }
    }

    @Deprecated
    public void setMapTrackballToArrowKeys(boolean z2) {
        if (this.f21069e) {
            this.f21070f.setMapTrackballToArrowKeys(z2);
        } else {
            com.tencent.smtt.utils.j.a(this.f21071g, "setMapTrackballToArrowKeys", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z2));
        }
    }

    public void setNetworkAvailable(boolean z2) {
        if (this.f21069e) {
            this.f21070f.setNetworkAvailable(z2);
        } else {
            this.f21071g.setNetworkAvailable(z2);
        }
    }

    @Override // android.view.View
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        if (!this.f21069e) {
            this.f21071g.setOnLongClickListener(onLongClickListener);
            return;
        }
        View view = this.f21070f.getView();
        try {
            if (this.f21083x == null) {
                Method methodA = com.tencent.smtt.utils.j.a(view, "getListenerInfo", new Class[0]);
                methodA.setAccessible(true);
                Object objInvoke = methodA.invoke(view, null);
                Field declaredField = objInvoke.getClass().getDeclaredField("mOnLongClickListener");
                declaredField.setAccessible(true);
                this.f21083x = declaredField.get(objInvoke);
            }
            this.f21084y = onLongClickListener;
            getView().setOnLongClickListener(this);
        } catch (Throwable unused) {
        }
    }

    @Override // android.view.View
    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        getView().setOnTouchListener(onTouchListener);
    }

    @Deprecated
    public void setPictureListener(final PictureListener pictureListener) {
        if (this.f21069e) {
            if (pictureListener == null) {
                this.f21070f.setPictureListener(null);
                return;
            } else {
                this.f21070f.setPictureListener(new IX5WebViewBase.PictureListener() { // from class: com.tencent.smtt.sdk.WebView.6
                    @Override // com.tencent.smtt.export.external.interfaces.IX5WebViewBase.PictureListener
                    public void onNewPicture(IX5WebViewBase iX5WebViewBase, Picture picture, boolean z2) {
                        WebView.this.a(iX5WebViewBase);
                        pictureListener.onNewPicture(WebView.this, picture);
                    }

                    @Override // com.tencent.smtt.export.external.interfaces.IX5WebViewBase.PictureListener
                    public void onNewPictureIfHaveContent(IX5WebViewBase iX5WebViewBase, Picture picture) {
                    }
                });
                return;
            }
        }
        if (pictureListener == null) {
            this.f21071g.setPictureListener(null);
        } else {
            this.f21071g.setPictureListener(new WebView.PictureListener() { // from class: com.tencent.smtt.sdk.WebView.5
                @Override // android.webkit.WebView.PictureListener
                public void onNewPicture(android.webkit.WebView webView, Picture picture) {
                    WebView.this.a(webView);
                    pictureListener.onNewPicture(WebView.this, picture);
                }
            });
        }
    }

    public void setRendererPriorityPolicy(int i2, boolean z2) {
        try {
            if (this.f21069e || Build.VERSION.SDK_INT < 26) {
                return;
            }
            com.tencent.smtt.utils.j.a(this.f21071g, "setRendererPriorityPolicy", (Class<?>[]) new Class[]{Integer.TYPE, Boolean.TYPE}, Integer.valueOf(i2), Boolean.valueOf(z2));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.view.View
    public void setScrollBarStyle(int i2) {
        if (this.f21069e) {
            this.f21070f.getView().setScrollBarStyle(i2);
        } else {
            this.f21071g.setScrollBarStyle(i2);
        }
    }

    public void setSysNightModeAlpha(int i2) {
        NIGHT_MODE_ALPHA = i2;
    }

    public void setVerticalScrollbarOverlay(boolean z2) {
        if (this.f21069e) {
            this.f21070f.setVerticalScrollbarOverlay(z2);
        } else {
            this.f21071g.setVerticalScrollbarOverlay(z2);
        }
    }

    public boolean setVideoFullScreen(Context context, boolean z2) {
        if (!context.getApplicationInfo().processName.contains("com.tencent.android.qqdownloader") || this.f21070f == null) {
            return false;
        }
        Bundle bundle = new Bundle();
        if (z2) {
            bundle.putInt("DefaultVideoScreen", 2);
        } else {
            bundle.putInt("DefaultVideoScreen", 1);
        }
        this.f21070f.getX5WebViewExtension().invokeMiscMethod("setVideoParams", bundle);
        return true;
    }

    @Override // android.view.View
    public void setVisibility(int i2) {
        super.setVisibility(i2);
        if (getView() == null) {
            return;
        }
        getView().setVisibility(i2);
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        a aVar;
        android.webkit.WebChromeClient systemWebChromeClient = null;
        if (this.f21069e) {
            this.f21070f.setWebChromeClient(webChromeClient != null ? new i(w.a().a(true).i(), this, webChromeClient) : null);
        } else {
            if (webChromeClient == null) {
                aVar = this.f21071g;
            } else if (a(webChromeClient)) {
                aVar = this.f21071g;
                systemWebChromeClient = new e(this, webChromeClient);
            } else {
                aVar = this.f21071g;
                systemWebChromeClient = new SystemWebChromeClient(this, webChromeClient);
            }
            aVar.setWebChromeClient(systemWebChromeClient);
        }
        this.f21077o = webChromeClient;
    }

    public void setWebChromeClientExtension(IX5WebChromeClientExtension iX5WebChromeClientExtension) {
        if (this.f21069e) {
            this.f21070f.getX5WebViewExtension().setWebChromeClientExtension(iX5WebChromeClientExtension);
        }
    }

    public void setWebViewCallbackClient(WebViewCallbackClient webViewCallbackClient) {
        this.mWebViewCallbackClient = webViewCallbackClient;
        if (!this.f21069e || getX5WebViewExtension() == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", true);
        getX5WebViewExtension().invokeMiscMethod("setWebViewCallbackClientFlag", bundle);
    }

    public void setWebViewClient(WebViewClient webViewClient) {
        if (this.f21069e) {
            this.f21070f.setWebViewClient(webViewClient != null ? new j(w.a().a(true).j(), this, webViewClient) : null);
        } else {
            this.f21071g.setWebViewClient(webViewClient != null ? new SystemWebViewClient(this, webViewClient) : null);
        }
        this.f21076n = webViewClient;
    }

    public void setWebViewClientExtension(IX5WebViewClientExtension iX5WebViewClientExtension) {
        if (this.f21069e) {
            this.f21070f.getX5WebViewExtension().setWebViewClientExtension(iX5WebViewClientExtension);
        }
    }

    @SuppressLint({"NewApi"})
    public boolean showDebugView(String str) {
        String lowerCase = str.toLowerCase();
        if (lowerCase.startsWith("https://debugtbs.qq.com")) {
            getView().setVisibility(4);
            com.tencent.smtt.utils.d.a(this.f21073i).a(lowerCase, this, this.f21073i, n.a().getLooper());
            return true;
        }
        if (!lowerCase.startsWith("https://debugx5.qq.com") || this.f21069e) {
            return false;
        }
        loadDataWithBaseURL(null, "<!DOCTYPE html><html><body><head><title>无法打开debugx5</title><meta name=\"viewport\" content=\"width=device-width, user-scalable=no\" /></head><br/><br /><h2>debugx5页面仅在使用了X5内核时有效，由于当前没有使用X5内核，无法打开debugx5！</h2><br />尝试<a href=\"https://debugtbs.qq.com?10000\">进入DebugTbs安装或打开X5内核</a></body></html>", "text/html", "utf-8", null);
        return true;
    }

    public boolean showFindDialog(String str, boolean z2) {
        return false;
    }

    public void stopLoading() {
        if (this.f21069e) {
            this.f21070f.stopLoading();
        } else {
            this.f21071g.stopLoading();
        }
    }

    public void super_computeScroll() {
        if (!this.f21069e) {
            this.f21071g.a();
            return;
        }
        try {
            com.tencent.smtt.utils.j.a(this.f21070f.getView(), "super_computeScroll");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public boolean super_dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.f21069e) {
            return this.f21071g.b(motionEvent);
        }
        try {
            Object objA = com.tencent.smtt.utils.j.a(this.f21070f.getView(), "super_dispatchTouchEvent", (Class<?>[]) new Class[]{MotionEvent.class}, motionEvent);
            if (objA == null) {
                return false;
            }
            return ((Boolean) objA).booleanValue();
        } catch (Throwable unused) {
            return false;
        }
    }

    public boolean super_onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.f21069e) {
            return this.f21071g.c(motionEvent);
        }
        try {
            Object objA = com.tencent.smtt.utils.j.a(this.f21070f.getView(), "super_onInterceptTouchEvent", (Class<?>[]) new Class[]{MotionEvent.class}, motionEvent);
            if (objA == null) {
                return false;
            }
            return ((Boolean) objA).booleanValue();
        } catch (Throwable unused) {
            return false;
        }
    }

    public void super_onOverScrolled(int i2, int i3, boolean z2, boolean z3) {
        if (!this.f21069e) {
            this.f21071g.a(i2, i3, z2, z3);
            return;
        }
        View view = this.f21070f.getView();
        try {
            Class cls = Integer.TYPE;
            Class cls2 = Boolean.TYPE;
            com.tencent.smtt.utils.j.a(view, "super_onOverScrolled", (Class<?>[]) new Class[]{cls, cls, cls2, cls2}, Integer.valueOf(i2), Integer.valueOf(i3), Boolean.valueOf(z2), Boolean.valueOf(z3));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void super_onScrollChanged(int i2, int i3, int i4, int i5) {
        if (!this.f21069e) {
            this.f21071g.a(i2, i3, i4, i5);
            return;
        }
        View view = this.f21070f.getView();
        try {
            Class cls = Integer.TYPE;
            com.tencent.smtt.utils.j.a(view, "super_onScrollChanged", (Class<?>[]) new Class[]{cls, cls, cls, cls}, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public boolean super_onTouchEvent(MotionEvent motionEvent) {
        if (!this.f21069e) {
            return this.f21071g.a(motionEvent);
        }
        try {
            Object objA = com.tencent.smtt.utils.j.a(this.f21070f.getView(), "super_onTouchEvent", (Class<?>[]) new Class[]{MotionEvent.class}, motionEvent);
            if (objA == null) {
                return false;
            }
            return ((Boolean) objA).booleanValue();
        } catch (Throwable unused) {
            return false;
        }
    }

    public boolean super_overScrollBy(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z2) {
        if (!this.f21069e) {
            return this.f21071g.a(i2, i3, i4, i5, i6, i7, i8, i9, z2);
        }
        View view = this.f21070f.getView();
        try {
            Class cls = Integer.TYPE;
            Object objA = com.tencent.smtt.utils.j.a(view, "super_overScrollBy", (Class<?>[]) new Class[]{cls, cls, cls, cls, cls, cls, cls, cls, Boolean.TYPE}, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i8), Integer.valueOf(i9), Boolean.valueOf(z2));
            if (objA == null) {
                return false;
            }
            return ((Boolean) objA).booleanValue();
        } catch (Throwable unused) {
            return false;
        }
    }

    public void switchNightMode(boolean z2) {
        String str;
        if (z2 == f21066w) {
            return;
        }
        f21066w = z2;
        if (z2) {
            TbsLog.e("QB_SDK", "deleteNightMode");
            str = "javascript:document.getElementsByTagName('HEAD').item(0).removeChild(document.getElementById('QQBrowserSDKNightMode'));";
        } else {
            TbsLog.e("QB_SDK", "nightMode");
            str = "javascript:var style = document.createElement('style');style.type='text/css';style.id='QQBrowserSDKNightMode';style.innerHTML='html,body{background:none !important;background-color: #1d1e2a !important;}html *{background-color: #1d1e2a !important; color:#888888 !important;border-color:#3e4f61 !important;text-shadow:none !important;box-shadow:none !important;}a,a *{border-color:#4c5b99 !important; color:#2d69b3 !important;text-decoration:none !important;}a:visited,a:visited *{color:#a600a6 !important;}a:active,a:active *{color:#5588AA !important;}input,select,textarea,option,button{background-image:none !important;color:#AAAAAA !important;border-color:#4c5b99 !important;}form,div,button,span{background-color:#1d1e2a !important; border-color:#4c5b99 !important;}img{opacity:0.5}';document.getElementsByTagName('HEAD').item(0).appendChild(style);";
        }
        loadUrl(str);
    }

    public void switchToNightMode() {
        TbsLog.e("QB_SDK", "switchToNightMode 01");
        if (f21066w) {
            return;
        }
        TbsLog.e("QB_SDK", "switchToNightMode");
        loadUrl("javascript:var style = document.createElement('style');style.type='text/css';style.id='QQBrowserSDKNightMode';style.innerHTML='html,body{background:none !important;background-color: #1d1e2a !important;}html *{background-color: #1d1e2a !important; color:#888888 !important;border-color:#3e4f61 !important;text-shadow:none !important;box-shadow:none !important;}a,a *{border-color:#4c5b99 !important; color:#2d69b3 !important;text-decoration:none !important;}a:visited,a:visited *{color:#a600a6 !important;}a:active,a:active *{color:#5588AA !important;}input,select,textarea,option,button{background-image:none !important;color:#AAAAAA !important;border-color:#4c5b99 !important;}form,div,button,span{background-color:#1d1e2a !important; border-color:#4c5b99 !important;}img{opacity:0.5}';document.getElementsByTagName('HEAD').item(0).appendChild(style);");
    }

    public boolean zoomIn() {
        return !this.f21069e ? this.f21071g.zoomIn() : this.f21070f.zoomIn();
    }

    public boolean zoomOut() {
        return !this.f21069e ? this.f21071g.zoomOut() : this.f21070f.zoomOut();
    }
}
