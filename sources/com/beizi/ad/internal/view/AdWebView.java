package com.beizi.ad.internal.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.beizi.ad.AdActivity;
import com.beizi.ad.R;
import com.beizi.ad.a.a.i;
import com.beizi.ad.c.e;
import com.beizi.ad.internal.j;
import com.beizi.ad.internal.l;
import com.beizi.ad.internal.network.ServerResponse;
import com.beizi.ad.internal.utilities.HTTPGet;
import com.beizi.ad.internal.utilities.HTTPResponse;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.SPUtils;
import com.beizi.ad.internal.utilities.StringUtil;
import com.beizi.ad.internal.utilities.ViewUtil;
import com.beizi.ad.internal.utilities.WebviewUtil;
import com.beizi.ad.internal.video.AdVideoView;
import com.beizi.ad.internal.view.AdViewImpl;
import com.beizi.ad.internal.view.f;
import com.github.lzyzsd.jsbridge.BridgeUtil;
import com.google.android.material.badge.BadgeDrawable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.regex.Matcher;

@SuppressLint({"ViewConstructor"})
/* loaded from: classes2.dex */
public class AdWebView extends WebView implements com.beizi.ad.internal.view.c {
    private boolean A;
    private boolean B;
    private boolean C;
    private boolean D;
    private int E;
    private long F;
    private float G;
    private float H;
    private boolean I;
    private final Runnable J;

    /* renamed from: a, reason: collision with root package name */
    boolean f4567a;
    public ServerResponse ad;
    public AdViewImpl adViewImpl;

    /* renamed from: b, reason: collision with root package name */
    protected String f4568b;

    /* renamed from: c, reason: collision with root package name */
    boolean f4569c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f4570d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f4571e;

    /* renamed from: f, reason: collision with root package name */
    private f f4572f;

    /* renamed from: g, reason: collision with root package name */
    private int f4573g;
    public GestureDetector gestureDetector;

    /* renamed from: h, reason: collision with root package name */
    private int f4574h;

    /* renamed from: i, reason: collision with root package name */
    private boolean f4575i;

    /* renamed from: j, reason: collision with root package name */
    private int f4576j;

    /* renamed from: k, reason: collision with root package name */
    private int f4577k;

    /* renamed from: l, reason: collision with root package name */
    private int f4578l;

    /* renamed from: m, reason: collision with root package name */
    private int f4579m;
    public AdVideoView mAdVideoView;

    /* renamed from: n, reason: collision with root package name */
    private int f4580n;

    /* renamed from: o, reason: collision with root package name */
    private boolean f4581o;

    /* renamed from: p, reason: collision with root package name */
    private boolean f4582p;

    /* renamed from: q, reason: collision with root package name */
    private Handler f4583q;

    /* renamed from: r, reason: collision with root package name */
    private boolean f4584r;

    /* renamed from: s, reason: collision with root package name */
    private int f4585s;

    /* renamed from: t, reason: collision with root package name */
    private ProgressDialog f4586t;

    /* renamed from: u, reason: collision with root package name */
    private boolean f4587u;

    /* renamed from: v, reason: collision with root package name */
    private boolean f4588v;

    /* renamed from: w, reason: collision with root package name */
    private int f4589w;

    /* renamed from: x, reason: collision with root package name */
    private int f4590x;

    /* renamed from: y, reason: collision with root package name */
    private int f4591y;

    /* renamed from: z, reason: collision with root package name */
    private boolean f4592z;

    public class a extends WebViewClient {
        private a() {
        }

        @Override // android.webkit.WebViewClient
        public void onLoadResource(WebView webView, String str) {
            if (str.startsWith("http")) {
                try {
                    WebView.HitTestResult hitTestResult = AdWebView.this.getHitTestResult();
                    if (hitTestResult == null || hitTestResult.getExtra() == null) {
                        return;
                    }
                    if (hitTestResult.getExtra().equals(str)) {
                        int type = hitTestResult.getType();
                        if (type == 1 || type == 6 || type == 7 || type == 8) {
                            AdWebView.this.b(str);
                            webView.stopLoading();
                            AdWebView.this.c();
                        }
                    }
                } catch (NullPointerException unused) {
                }
            }
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            AdWebView adWebView = AdWebView.this;
            if (adWebView.ad.mMediaType == l.SPLASH) {
                adWebView.visible();
            }
            if (AdWebView.this.f4575i) {
                return;
            }
            webView.evaluateJavascript("javascript:window.mraid.util.pageFinished()", null);
            if (AdWebView.this.f4571e) {
                f fVar = AdWebView.this.f4572f;
                AdWebView adWebView2 = AdWebView.this;
                fVar.a(adWebView2, adWebView2.f4568b);
                AdWebView.this.i();
            }
            AdWebView.this.f4575i = true;
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int i2, String str, String str2) {
            HaoboLog.w(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.webview_received_error, i2, str, str2));
            AdViewImpl adViewImpl = AdWebView.this.adViewImpl;
            if (adViewImpl == null || adViewImpl.getAdDispatcher() == null) {
                return;
            }
            AdWebView.this.adViewImpl.getAdDispatcher().a(2);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            AdWebView.this.h();
            HaoboLog.w(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.webclient_error, sslError.getPrimaryError(), sslError.toString()));
            AdViewImpl adViewImpl = AdWebView.this.adViewImpl;
            if (adViewImpl == null || adViewImpl.getAdDispatcher() == null) {
                return;
            }
            AdWebView.this.adViewImpl.getAdDispatcher().a(2);
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            AdViewImpl adViewImpl;
            HaoboLog.v(HaoboLog.baseLogTag, "Loading URL: " + str);
            i.a("lance", "Loading:::::::::::::::::::::::" + str);
            if (str.startsWith(BridgeUtil.JAVASCRIPT_STR)) {
                return false;
            }
            if (str.startsWith("mraid://")) {
                HaoboLog.v(HaoboLog.mraidLogTag, str);
                if (AdWebView.this.f4571e) {
                    AdWebView.this.f4572f.a(str, AdWebView.this.f4588v);
                } else {
                    String host = Uri.parse(str).getHost();
                    if (host != null && host.equals("enable")) {
                        AdWebView.this.fireMRAIDEnabled();
                    } else if (host != null && host.equals("open")) {
                        AdWebView.this.f4572f.a(str, AdWebView.this.f4588v);
                    }
                }
                return true;
            }
            if (!str.startsWith("BeiZi://")) {
                AdWebView.this.b(str);
                AdWebView.this.c();
                return true;
            }
            try {
                String host2 = Uri.parse(str).getHost();
                if (!TextUtils.isEmpty(host2) && !"ClosePage".equals(host2)) {
                    AdWebView adWebView = AdWebView.this;
                    if (adWebView.ad != null && (adViewImpl = adWebView.adViewImpl) != null && adViewImpl.getAdParameters() != null) {
                        AdWebView adWebView2 = AdWebView.this;
                        adWebView2.ad.handleClick(adWebView2, "", "", "", "", "", "", adWebView2.f4569c, adWebView2.adViewImpl.getAdParameters().a());
                        AdWebView.this.f4569c = true;
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            com.beizi.ad.internal.view.b.a(AdWebView.this, str);
            return true;
        }
    }

    public interface b {
        void a();
    }

    public class c extends WebView {
        @SuppressLint({"SetJavaScriptEnabled"})
        public c(Context context) {
            super(new MutableContextWrapper(context));
            WebviewUtil.setWebViewSettings(this);
            setWebViewClient(new WebViewClient() { // from class: com.beizi.ad.internal.view.AdWebView.c.1

                /* renamed from: c, reason: collision with root package name */
                private boolean f4607c = false;

                @Override // android.webkit.WebViewClient
                public void onPageFinished(WebView webView, String str) {
                    HaoboLog.v(HaoboLog.browserLogTag, "Opening URL: " + str);
                    ViewUtil.removeChildFromParent(c.this);
                    if (AdWebView.this.f4586t != null && AdWebView.this.f4586t.isShowing()) {
                        AdWebView.this.f4586t.dismiss();
                    }
                    if (this.f4607c) {
                        this.f4607c = false;
                        c.this.destroy();
                        AdWebView.this.k();
                    } else {
                        c.this.setVisibility(0);
                        c cVar = c.this;
                        AdWebView.this.a(cVar);
                    }
                }

                @Override // android.webkit.WebViewClient
                public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                    HaoboLog.v(HaoboLog.browserLogTag, "Redirecting to URL: " + str);
                    boolean zH = AdWebView.this.h(str);
                    this.f4607c = zH;
                    if (zH && AdWebView.this.f4586t != null && AdWebView.this.f4586t.isShowing()) {
                        AdWebView.this.f4586t.dismiss();
                    }
                    return this.f4607c;
                }
            });
        }
    }

    public AdWebView(AdViewImpl adViewImpl) {
        super(new MutableContextWrapper(adViewImpl.getContext()));
        this.f4570d = false;
        this.ad = null;
        this.mAdVideoView = null;
        this.f4567a = false;
        this.f4581o = false;
        this.f4582p = false;
        this.f4583q = new Handler();
        this.f4584r = false;
        this.f4587u = false;
        this.f4588v = false;
        this.f4591y = -1;
        this.f4592z = false;
        this.A = false;
        this.B = false;
        this.C = false;
        this.D = false;
        this.E = 0;
        this.f4569c = false;
        this.J = new Runnable() { // from class: com.beizi.ad.internal.view.AdWebView.5
            @Override // java.lang.Runnable
            public void run() {
                if (AdWebView.this.f4584r) {
                    return;
                }
                AdWebView.this.f();
                AdWebView.this.f4583q.postDelayed(this, 1000L);
            }
        };
        setBackgroundColor(0);
        this.adViewImpl = adViewImpl;
        this.f4568b = f.f4658a[f.b.STARTING_DEFAULT.ordinal()];
        a();
        b();
        setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        AdViewImpl adViewImpl = this.adViewImpl;
        if (adViewImpl == null || !(adViewImpl instanceof InterstitialAdViewImpl)) {
            return;
        }
        ((InterstitialAdViewImpl) adViewImpl).g();
    }

    private void setCreativeHeight(int i2) {
        this.f4579m = i2;
    }

    private void setCreativeWidth(int i2) {
        this.f4578l = i2;
    }

    public boolean IsVideoWifiOnly() {
        return this.D;
    }

    @Override // android.webkit.WebView, com.beizi.ad.internal.view.c
    public void destroy() {
        setVisibility(4);
        ViewUtil.removeChildFromParent(this);
        super.destroy();
        removeAllViews();
        j();
    }

    @Override // com.beizi.ad.internal.view.c
    public boolean failed() {
        return this.f4570d;
    }

    public void fireMRAIDEnabled() {
        if (this.f4571e) {
            return;
        }
        this.f4571e = true;
        if (this.f4575i) {
            this.f4572f.a(this, this.f4568b);
            i();
        }
    }

    public HashMap<String, Object> getAdExtras() {
        ServerResponse serverResponse = this.ad;
        if (serverResponse == null) {
            return null;
        }
        return serverResponse.getExtras();
    }

    public int getAutoCloseTime() {
        return this.f4590x;
    }

    public Context getContextFromMutableContext() {
        return getContext() instanceof MutableContextWrapper ? ((MutableContextWrapper) getContext()).getBaseContext() : getContext();
    }

    @Override // com.beizi.ad.internal.view.c
    public int getCreativeHeight() {
        return this.f4579m;
    }

    public int getCreativeLeft() {
        return this.f4576j;
    }

    public int getCreativeTop() {
        return this.f4577k;
    }

    @Override // com.beizi.ad.internal.view.c
    public int getCreativeWidth() {
        return this.f4578l;
    }

    public f getMRAIDImplementation() {
        return this.f4572f;
    }

    public int getOrientation() {
        return this.f4585s;
    }

    public com.beizi.ad.internal.view.c getRealDisplayable() {
        AdVideoView adVideoView;
        return (!this.C || (adVideoView = this.mAdVideoView) == null) ? this : adVideoView;
    }

    @Override // com.beizi.ad.internal.view.c
    public int getRefreshInterval() {
        return this.f4580n;
    }

    public int getShowCloseBtnTime() {
        return this.f4589w;
    }

    public boolean getUserInteraction() {
        return this.f4588v;
    }

    @Override // com.beizi.ad.internal.view.c
    public View getView() {
        return this;
    }

    public void handleClickView(MotionEvent motionEvent, long j2, long j3) {
        AdViewImpl adViewImpl = this.adViewImpl;
        if (adViewImpl == null || adViewImpl.getAdDispatcher() == null || this.adViewImpl.isRewardedVideo()) {
            return;
        }
        this.E++;
        this.adViewImpl.getAdDispatcher().d();
        this.ad.setOpenInNativeBrowser(this.adViewImpl.getOpensNativeBrowser());
        if (motionEvent == null) {
            this.ad.handleClick(this, "100", "200", "105", "206", String.valueOf(j2), String.valueOf(j3), this.f4569c, this.adViewImpl.getAdParameters().a());
        } else {
            this.ad.handleClick(this, motionEvent.getX() + "", motionEvent.getY() + "", motionEvent.getRawX() + "", motionEvent.getRawY() + "", String.valueOf(j2), String.valueOf(j3), this.f4569c, this.adViewImpl.getAdParameters().a());
        }
        this.f4569c = true;
    }

    public boolean isAutoPlay() {
        return this.f4592z;
    }

    public boolean isMRAIDUseCustomClose() {
        return this.f4587u;
    }

    public boolean isMuted() {
        return this.A;
    }

    public boolean isVideoFullScreen() {
        return this.B;
    }

    public void loadAd(ServerResponse serverResponse) {
        int i2;
        if (serverResponse == null) {
            return;
        }
        this.ad = serverResponse;
        setCreativeHeight(serverResponse.getHeight());
        setCreativeWidth(serverResponse.getWidth());
        setCreativeLeft(serverResponse.getLeft());
        setCreativeTop(serverResponse.getTop());
        setRefreshInterval(serverResponse.getRefreshInterval());
        if (serverResponse.isManualClose()) {
            this.f4589w = serverResponse.getMinTimer();
        } else {
            this.f4589w = -1;
        }
        if (!serverResponse.isAutoClose() || serverResponse.getMaxTimer() == 0) {
            this.f4590x = -1;
        } else {
            this.f4590x = serverResponse.getMaxTimer();
        }
        if (this.f4589w == -1 && this.f4590x == -1 && serverResponse.getAdType() != e.a.ADP_IVIDEO) {
            this.f4589w = 0;
        } else {
            int i3 = this.f4589w;
            if (i3 != -1 && (i2 = this.f4590x) != -1 && i3 > i2) {
                this.f4589w = i2;
            }
        }
        this.f4592z = serverResponse.isAutoPlay();
        this.A = serverResponse.isMuted();
        this.B = serverResponse.isFullScreen();
        this.f4585s = serverResponse.getAdOrientation();
        this.D = serverResponse.isWifiOnly();
        this.f4591y = -1;
        setInitialScale((int) ((com.beizi.ad.internal.g.a().j() * 100.0f) + 0.5f));
        loadAdAt(0);
    }

    public boolean loadAdAt(int i2) {
        int creativeHeight;
        int creativeWidth;
        ServerResponse serverResponse = this.ad;
        if (serverResponse != null && this.f4591y != i2) {
            if (!serverResponse.getCreatives().isEmpty() && this.ad.getCreatives().size() > i2) {
                Pair<j, String> pair = this.ad.getCreatives().get(i2);
                if (StringUtil.isEmpty((String) pair.second)) {
                    h();
                    return false;
                }
                if (pair.first == j.VIDEO) {
                    if (this.mAdVideoView == null) {
                        this.mAdVideoView = new AdVideoView(this);
                    }
                    this.mAdVideoView.transferAd(this, (String) pair.second);
                    this.C = true;
                    String strA = com.beizi.ad.a.a.b.a("aHR0cDovL2Fib3V0OmJsYW5r");
                    if (!TextUtils.isEmpty(strA)) {
                        loadUrl(strA);
                    }
                } else {
                    HaoboLog.v(HaoboLog.baseLogTag, HaoboLog.getString(R.string.webview_loading, (String) pair.second));
                    a(this.ad.getExtras());
                    String strF = f(e(d((String) pair.second)));
                    float fH = com.beizi.ad.internal.g.a().h();
                    float fI = com.beizi.ad.internal.g.a().i();
                    float fJ = com.beizi.ad.internal.g.a().j();
                    if (getCreativeWidth() == -1 && getCreativeHeight() == -1) {
                        creativeWidth = -1;
                        creativeHeight = -1;
                    } else {
                        creativeHeight = (int) ((getCreativeHeight() * fJ) + 0.5f);
                        creativeWidth = (int) ((getCreativeWidth() * fJ) + 0.5f);
                    }
                    if (getCreativeLeft() == 0 && getCreativeTop() == 0) {
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(creativeWidth, creativeHeight, 17);
                        l lVar = this.ad.mMediaType;
                        if (lVar == l.SPLASH) {
                            setLayoutParams(new FrameLayout.LayoutParams(-1, -1, 17));
                        } else if (lVar == l.BANNER) {
                            a(new FrameLayout.LayoutParams(-2, -1, 17));
                        } else {
                            a(layoutParams);
                        }
                    } else {
                        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(creativeWidth, creativeHeight, BadgeDrawable.TOP_START);
                        layoutParams2.setMargins((int) ((getCreativeLeft() * fH) + 0.5f), (int) ((getCreativeTop() * fI) + 0.5f), 0, 0);
                        if (this.ad.mMediaType == l.SPLASH) {
                            setLayoutParams(new FrameLayout.LayoutParams(-1, -1, 17));
                        } else {
                            a(layoutParams2);
                        }
                    }
                    loadDataWithBaseURL(null, strF, "text/html", "UTF-8", null);
                    this.C = false;
                }
                this.f4591y = i2;
                return true;
            }
            h();
        }
        return false;
    }

    public boolean loadAdBy(int i2) {
        return loadAdAt(this.f4591y + i2);
    }

    @Override // android.webkit.WebView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        f();
    }

    @Override // com.beizi.ad.internal.view.c
    public void onDestroy() {
        destroy();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ProgressDialog progressDialog = this.f4586t;
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        this.f4586t.dismiss();
    }

    @Override // android.webkit.WebView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override // android.webkit.WebView, android.widget.AbsoluteLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.f4588v = true;
        int action = motionEvent.getAction();
        if (action == 0) {
            this.F = System.currentTimeMillis();
            this.G = motionEvent.getX();
            this.H = motionEvent.getY();
            this.I = true;
        } else if (action == 1) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j2 = this.F;
            if (jCurrentTimeMillis - j2 < 1000 && this.I) {
                handleClickView(motionEvent, j2, jCurrentTimeMillis);
            }
        } else if (action == 2 && this.I && a(this.G, this.H, motionEvent.getX(), motionEvent.getY()) > 15.0f) {
            this.I = false;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.webkit.WebView, android.view.View
    public void onVisibilityChanged(View view, int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.onVisibilityChanged(view, i2);
        a(getWindowVisibility(), i2);
    }

    @Override // android.webkit.WebView, android.view.View
    public void onWindowVisibilityChanged(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.onWindowVisibilityChanged(i2);
        a(i2, getVisibility());
    }

    public void resize(int i2, int i3, int i4, int i5, f.a aVar, boolean z2) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getLayoutParams());
        f fVar = this.f4572f;
        if (!fVar.f4661d) {
            this.f4573g = layoutParams.width;
            this.f4574h = layoutParams.height;
        }
        float f2 = displayMetrics.density;
        int i6 = (int) ((i3 * f2) + 0.5d);
        int i7 = (int) ((i2 * f2) + 0.5d);
        layoutParams.height = i6;
        layoutParams.width = i7;
        layoutParams.gravity = 17;
        AdViewImpl adViewImpl = this.adViewImpl;
        if (adViewImpl != null) {
            adViewImpl.a(i7, i6, i4, i5, aVar, z2, fVar);
        }
        AdViewImpl adViewImpl2 = this.adViewImpl;
        if (adViewImpl2 != null) {
            adViewImpl2.f();
        }
        setLayoutParams(layoutParams);
    }

    @Override // android.view.View
    public void scrollTo(int i2, int i3) {
        super.scrollTo(0, 0);
    }

    public void setCreativeLeft(int i2) {
        this.f4576j = i2;
    }

    public void setCreativeTop(int i2) {
        this.f4577k = i2;
    }

    public void setMRAIDUseCustomClose(boolean z2) {
        this.f4587u = z2;
    }

    public void setRefreshInterval(int i2) {
        this.f4580n = i2;
    }

    public boolean shouldDisplayButton() {
        if (this.ad.getAdType() == e.a.ADP_BANNER) {
            return false;
        }
        if (this.ad.getAdType() == e.a.ADP_IVIDEO) {
            if (this.ad.getCreatives().get(this.f4591y).first != j.VIDEO) {
                return false;
            }
        } else if (this.f4591y != 0) {
            return false;
        }
        return true;
    }

    @Override // com.beizi.ad.internal.view.c
    public void visible() {
        Handler handler;
        AdViewImpl adViewImpl;
        setVisibility(0);
        this.adViewImpl.showAdLogo(this);
        l lVar = l.BANNER;
        if (lVar.equals(this.adViewImpl.getMediaType())) {
            AdViewImpl adViewImpl2 = this.adViewImpl;
            adViewImpl2.showBannerCloseBtn(adViewImpl2);
        }
        if (shouldDisplayButton() && (adViewImpl = this.adViewImpl) != null) {
            if (adViewImpl.getMediaType() == l.INTERSTITIAL) {
                this.adViewImpl.addInterstitialCloseButton(getShowCloseBtnTime(), getAutoCloseTime(), this, this.ad.getAdType() == e.a.ADP_IVIDEO);
            } else {
                this.adViewImpl.isLoadToShow(this);
            }
        }
        AdViewImpl adViewImpl3 = this.adViewImpl;
        if (adViewImpl3 == null || adViewImpl3.getAdDispatcher() == null || this.adViewImpl.getMediaType() != lVar || (handler = this.f4583q) == null) {
            return;
        }
        handler.postDelayed(new Runnable() { // from class: com.beizi.ad.internal.view.AdWebView.3
            @Override // java.lang.Runnable
            public void run() {
                AdWebView.this.adViewImpl.getAdDispatcher().a();
                AdWebView adWebView = AdWebView.this;
                ServerResponse serverResponse = adWebView.ad;
                if (serverResponse != null) {
                    serverResponse.handleView(adWebView, adWebView.adViewImpl.getAdParameters().a());
                }
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String e(String str) {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        Resources resources = getResources();
        StringBuilder sb = new StringBuilder("<html><head><script>");
        if (resources != null && StringUtil.appendLocalStr(sb, StringUtil.adscopeJSStr) && StringUtil.appendLocalStr(sb, StringUtil.adscopeStr) && StringUtil.appendRes(sb)) {
            sb.append("</script></head>");
            return str.replaceFirst("<html>", Matcher.quoteReplacement(sb.toString()));
        }
        HaoboLog.e(HaoboLog.baseLogTag, "Error reading SDK's raw resources.");
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String f(String str) {
        return !StringUtil.isEmpty(str) ? str.replaceFirst("<head>", Matcher.quoteReplacement(new StringBuilder("<head><meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,user-scalable=no\"/>").toString())) : str;
    }

    private boolean g(String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.setFlags(268435456);
        try {
            this.adViewImpl.getContext().startActivity(intent);
            return true;
        } catch (ActivityNotFoundException unused) {
            HaoboLog.w(HaoboLog.baseLogTag, HaoboLog.getString(R.string.opening_url_failed, str));
            if (this.f4571e) {
                Toast.makeText(this.adViewImpl.getContext(), R.string.action_cant_be_completed, 0).show();
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean h(String str) {
        String strA = com.beizi.ad.a.a.b.a("aHR0cDovL2Fib3V0OmJsYW5r");
        if (!str.contains("://play.google.com") && (str.startsWith("http") || str.startsWith(strA))) {
            return false;
        }
        HaoboLog.i(HaoboLog.baseLogTag, HaoboLog.getString(R.string.opening_app_store));
        return g(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        if (this.f4582p) {
            this.f4584r = false;
            this.f4583q.removeCallbacks(this.J);
            this.f4583q.post(this.J);
        }
    }

    private void j() {
        this.f4584r = true;
        this.f4583q.removeCallbacks(this.J);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String d(String str) {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        str.trim();
        if (str.startsWith("<html>")) {
            return str;
        }
        return "<html><body style='padding:0;margin:0;'>" + str + "</body></html>";
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public void b() {
        this.f4572f = new f(this);
        setWebChromeClient(new h(this));
        setWebViewClient(new a());
    }

    public void c() {
        AdViewImpl adViewImpl = this.adViewImpl;
        if (adViewImpl != null) {
            adViewImpl.f();
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public void a() {
        try {
            String userAgentString = getSettings().getUserAgentString();
            if (!TextUtils.isEmpty(userAgentString) && !userAgentString.equals(com.beizi.ad.internal.g.a().f4182d)) {
                SPUtils.put(getContext(), "userAgent", userAgentString);
            }
            com.beizi.ad.internal.g.a().f4182d = userAgentString;
            getSettings().setJavaScriptEnabled(true);
            getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            getSettings().setBuiltInZoomControls(false);
            getSettings().setEnableSmoothTransition(true);
            getSettings().setLightTouchEnabled(false);
            getSettings().setPluginState(WebSettings.PluginState.ON);
            getSettings().setLoadsImagesAutomatically(true);
            getSettings().setSavePassword(false);
            getSettings().setSupportZoom(false);
            getSettings().setUseWideViewPort(false);
            getSettings().setMediaPlaybackRequiresUserGesture(false);
            getSettings().setMixedContentMode(0);
            WebView.setWebContentsDebuggingEnabled(false);
            getSettings().setAllowFileAccess(false);
            getSettings().setAllowContentAccess(false);
            getSettings().setAllowFileAccessFromFileURLs(false);
            getSettings().setAllowUniversalAccessFromFileURLs(false);
            CookieManager cookieManager = CookieManager.getInstance();
            if (cookieManager != null) {
                cookieManager.setAcceptThirdPartyCookies(this, true);
            } else {
                HaoboLog.d(HaoboLog.baseLogTag, "Failed to set Webview to accept 3rd party cookie");
            }
            setHorizontalScrollbarOverlay(false);
            setHorizontalScrollBarEnabled(false);
            setVerticalScrollbarOverlay(false);
            setVerticalScrollBarEnabled(false);
            if (this.B) {
                setBackgroundColor(0);
            }
            setScrollBarStyle(0);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void c(String str) {
        evaluateJavascript(str, null);
    }

    public void f() {
        if (getContextFromMutableContext() instanceof Activity) {
            int[] iArr = new int[2];
            getLocationOnScreen(iArr);
            boolean z2 = false;
            int i2 = iArr[0];
            int width = getWidth() + i2;
            int i3 = iArr[1];
            int height = getHeight() + i3;
            int[] screenSizeAsPixels = ViewUtil.getScreenSizeAsPixels((Activity) getContextFromMutableContext());
            if (width > 0 && i2 < screenSizeAsPixels[0] && height > 0 && i3 < screenSizeAsPixels[1]) {
                z2 = true;
            }
            this.f4581o = z2;
            f fVar = this.f4572f;
            if (fVar != null) {
                fVar.b();
                this.f4572f.a(i2, i3, getWidth(), getHeight());
                this.f4572f.a(getContext().getResources().getConfiguration().orientation);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        this.f4570d = true;
    }

    public void b(String str) {
        if (!this.adViewImpl.getOpensNativeBrowser()) {
            HaoboLog.d(HaoboLog.baseLogTag, HaoboLog.getString(R.string.opening_inapp));
            if (h(str)) {
                return;
            }
            try {
                if (this.adViewImpl.getLoadsInBackground()) {
                    final c cVar = new c(getContext());
                    cVar.loadUrl(str);
                    cVar.setVisibility(8);
                    this.adViewImpl.addView(cVar);
                    if (this.adViewImpl.getShowLoadingIndicator()) {
                        ProgressDialog progressDialog = new ProgressDialog(getContextFromMutableContext());
                        this.f4586t = progressDialog;
                        progressDialog.setCancelable(true);
                        this.f4586t.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.beizi.ad.internal.view.AdWebView.2
                            @Override // android.content.DialogInterface.OnCancelListener
                            public void onCancel(DialogInterface dialogInterface) {
                                cVar.stopLoading();
                            }
                        });
                        this.f4586t.setMessage(getContext().getResources().getString(R.string.loading));
                        this.f4586t.setProgressStyle(0);
                        this.f4586t.show();
                    }
                } else {
                    WebView webView = new WebView(new MutableContextWrapper(getContext()));
                    WebviewUtil.setWebViewSettings(webView);
                    webView.loadUrl(str);
                    a(webView);
                }
                return;
            } catch (Exception e2) {
                HaoboLog.e(HaoboLog.baseLogTag, "Exception initializing the redirect webview: " + e2.getMessage());
                return;
            }
        }
        HaoboLog.d(HaoboLog.baseLogTag, HaoboLog.getString(R.string.opening_native));
        g(str);
        k();
    }

    public void d() {
        AdViewImpl adViewImpl = this.adViewImpl;
        if (adViewImpl != null) {
            adViewImpl.c();
        }
    }

    public void handleClickView(com.beizi.ad.c.c cVar, long j2, long j3, int i2) {
        AdViewImpl adViewImpl = this.adViewImpl;
        if (adViewImpl == null || adViewImpl.getAdDispatcher() == null || this.adViewImpl.isRewardedVideo()) {
            return;
        }
        this.E++;
        this.adViewImpl.getAdDispatcher().d();
        this.ad.setOpenInNativeBrowser(this.adViewImpl.getOpensNativeBrowser());
        this.ad.handleClick(this, cVar, String.valueOf(j2), String.valueOf(j3), this.f4569c, this.adViewImpl.getAdParameters().a(), i2);
        this.f4569c = true;
    }

    public boolean g() {
        return this.f4581o && this.f4582p;
    }

    public void e() {
        AdViewImpl adViewImpl = this.adViewImpl;
        if (adViewImpl != null) {
            adViewImpl.a(this.f4573g, this.f4574h, this.f4572f);
        }
    }

    private void a(FrameLayout.LayoutParams layoutParams) {
        AdViewImpl adViewImpl = this.adViewImpl;
        if (adViewImpl instanceof BannerAdViewImpl) {
            if (((BannerAdViewImpl) adViewImpl).getResizeAdToFitContainer()) {
                setLayoutParams(new FrameLayout.LayoutParams(-1, -1, 17));
                return;
            } else {
                setLayoutParams(layoutParams);
                return;
            }
        }
        setLayoutParams(layoutParams);
    }

    private void a(HashMap map) {
        if (!map.isEmpty() && map.containsKey(ServerResponse.EXTRAS_KEY_MRAID)) {
            this.f4571e = ((Boolean) map.get(ServerResponse.EXTRAS_KEY_MRAID)).booleanValue();
        }
    }

    public void a(final String str) {
        new HTTPGet(false) { // from class: com.beizi.ad.internal.view.AdWebView.1
            @Override // com.beizi.ad.internal.utilities.HTTPGet
            public String getUrl() {
                return str;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.beizi.ad.internal.utilities.HTTPGet, android.os.AsyncTask
            public void onPostExecute(HTTPResponse hTTPResponse) {
                if (hTTPResponse.getSucceeded()) {
                    AdWebView.this.loadDataWithBaseURL(null, AdWebView.this.f(AdWebView.this.e(AdWebView.this.d(hTTPResponse.getResponseBody()))), "text/html", "UTF-8", null);
                    AdWebView.this.fireMRAIDEnabled();
                }
            }
        }.execute(new Void[0]);
    }

    private static float a(float f2, float f3, float f4, float f5) {
        float f6 = f2 - f4;
        float f7 = f3 - f5;
        return a((float) Math.sqrt((f6 * f6) + (f7 * f7)));
    }

    private static float a(float f2) {
        return f2 / com.beizi.ad.internal.g.a().k().density;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(WebView webView) {
        Class clsA = AdActivity.a();
        Intent intent = new Intent(this.adViewImpl.getContext(), (Class<?>) clsA);
        intent.setFlags(268435456);
        intent.putExtra("ACTIVITY_TYPE", "BROWSER");
        com.beizi.ad.internal.a.a.f3988a.add(webView);
        if (this.adViewImpl.getBrowserStyle() != null) {
            String str = "" + super.hashCode();
            intent.putExtra("bridgeid", str);
            AdViewImpl.c.f4566a.add(new Pair<>(str, this.adViewImpl.getBrowserStyle()));
        }
        try {
            this.adViewImpl.getContext().startActivity(intent);
            k();
        } catch (ActivityNotFoundException unused) {
            HaoboLog.w(HaoboLog.baseLogTag, HaoboLog.getString(R.string.adactivity_missing, clsA.getName()));
            com.beizi.ad.internal.a.a.f3988a.remove();
        }
    }

    private void a(int i2, int i3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (i2 == 0 && i3 == 0) {
            WebviewUtil.onResume(this);
            this.f4582p = true;
            if (this.f4571e && this.f4575i) {
                i();
            }
        } else {
            WebviewUtil.onPause(this);
            this.f4582p = false;
            j();
        }
        f fVar = this.f4572f;
        if (fVar != null) {
            fVar.b();
        }
    }

    public void a(int i2, int i3, boolean z2, final f fVar, final boolean z3, final AdActivity.b bVar) {
        int i4 = i2;
        int i5 = i3;
        ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getMetrics(new DisplayMetrics());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getLayoutParams());
        if (!this.f4572f.f4661d) {
            this.f4573g = layoutParams.width;
            this.f4574h = layoutParams.height;
        }
        if (i5 == -1 && i4 == -1 && this.adViewImpl != null) {
            this.f4567a = true;
        }
        if (i5 != -1) {
            i5 = (int) ((i5 * r3.density) + 0.5d);
        }
        int i6 = i5;
        if (i4 != -1) {
            i4 = (int) ((i4 * r3.density) + 0.5d);
        }
        int i7 = i4;
        layoutParams.height = i6;
        layoutParams.width = i7;
        layoutParams.gravity = 17;
        b bVar2 = this.f4567a ? new b() { // from class: com.beizi.ad.internal.view.AdWebView.4
            @Override // com.beizi.ad.internal.view.AdWebView.b
            public void a() {
                f fVar2 = fVar;
                if (fVar2 == null || fVar2.c() == null) {
                    return;
                }
                AdWebView.this.a(fVar.c(), z3, bVar);
                AdViewImpl.setMRAIDFullscreenListener(null);
            }
        } : null;
        AdViewImpl adViewImpl = this.adViewImpl;
        if (adViewImpl != null) {
            adViewImpl.a(i7, i6, z2, fVar, bVar2);
            this.adViewImpl.f();
        }
        setLayoutParams(layoutParams);
    }

    public void a(Activity activity, boolean z2, AdActivity.b bVar) {
        AdActivity.b bVar2 = AdActivity.b.none;
        if (bVar != bVar2) {
            AdActivity.a(activity, bVar);
        }
        if (z2) {
            AdActivity.b(activity);
        } else if (bVar == bVar2) {
            AdActivity.a(activity);
        }
    }
}
