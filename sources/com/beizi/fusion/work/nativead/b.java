package com.beizi.fusion.work.nativead;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.beizi.ad.NativeAd;
import com.beizi.ad.NativeAdListener;
import com.beizi.ad.NativeAdResponse;
import com.beizi.ad.internal.nativead.NativeAdEventListener;
import com.beizi.ad.internal.nativead.NativeAdShownListener;
import com.beizi.ad.internal.nativead.NativeAdUtil;
import com.beizi.ad.internal.utilities.ImageManager;
import com.beizi.fusion.R;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.x;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ad;
import com.beizi.fusion.g.ai;
import com.beizi.fusion.g.an;
import com.beizi.fusion.g.aq;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.au;
import com.beizi.fusion.g.r;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.widget.dialog.dislike.a;
import com.beizi.fusion.work.splash.SplashContainer;
import com.yikaobang.yixue.R2;
import java.util.List;

/* loaded from: classes2.dex */
public class b extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {
    private AdSpacesBean.BuyerBean.ShakeViewBean A;
    private AdSpacesBean.BuyerBean.CoolShakeViewBean B;
    private ad C;
    private AdSpacesBean.BuyerBean.DislikeConfigBean F;
    private AdSpacesBean.BuyerBean.DislikeConfigBean G;

    /* renamed from: n, reason: collision with root package name */
    private Context f5926n;

    /* renamed from: o, reason: collision with root package name */
    private String f5927o;

    /* renamed from: p, reason: collision with root package name */
    private long f5928p;

    /* renamed from: q, reason: collision with root package name */
    private long f5929q;

    /* renamed from: s, reason: collision with root package name */
    private float f5931s;

    /* renamed from: t, reason: collision with root package name */
    private float f5932t;

    /* renamed from: u, reason: collision with root package name */
    private NativeAd f5933u;

    /* renamed from: v, reason: collision with root package name */
    private ViewGroup f5934v;

    /* renamed from: w, reason: collision with root package name */
    private View f5935w;

    /* renamed from: x, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f5936x;

    /* renamed from: y, reason: collision with root package name */
    private AdSpacesBean.RenderViewBean f5937y;

    /* renamed from: z, reason: collision with root package name */
    private List<Pair<String, Integer>> f5938z;

    /* renamed from: r, reason: collision with root package name */
    private com.beizi.fusion.f.a f5930r = com.beizi.fusion.f.a.ADDEFAULT;
    private String D = null;
    private boolean E = false;
    private float H = 0.0f;
    private float I = 0.0f;
    private float J = 0.0f;
    private float K = 0.0f;
    private String L = null;

    public b(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, float f2, float f3) {
        this.f5926n = context;
        this.f5927o = str;
        this.f5928p = j2;
        this.f5929q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f5931s = f2;
        this.f5932t = f3;
        this.f5934v = new SplashContainer(context);
        x();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aL() {
        AdSpacesBean.BuyerBean.DislikeConfigBean dislikeConfigBeanB;
        AdSpacesBean.BuyerBean.DislikeConfigBean dislikeConfig = this.f5541e.getDislikeConfig();
        this.F = dislikeConfig;
        if (dislikeConfig == null || (dislikeConfigBeanB = b(dislikeConfig.getOrderData(), this.f5933u.getAdId())) == null) {
            return;
        }
        this.G = dislikeConfigBeanB;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean aM() {
        AdSpacesBean.BuyerBean.DislikeConfigBean dislikeConfigBean = this.G;
        if (dislikeConfigBean != null) {
            return dislikeConfigBean.getIsHide() == 0;
        }
        AdSpacesBean.BuyerBean.DislikeConfigBean dislikeConfigBean2 = this.F;
        return dislikeConfigBean2 != null && dislikeConfigBean2.getIsHide() == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean aN() {
        AdSpacesBean.BuyerBean.DislikeConfigBean dislikeConfigBean = this.G;
        if (dislikeConfigBean != null) {
            return ai.a(dislikeConfigBean.getRandomNum());
        }
        AdSpacesBean.BuyerBean.DislikeConfigBean dislikeConfigBean2 = this.F;
        if (dislikeConfigBean2 != null) {
            return ai.a(dislikeConfigBean2.getRandomNum());
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean aO() {
        long jLongValue = ((Long) aq.b(this.f5926n, this.L, 0L)).longValue();
        return this.F == null || jLongValue == 0 || System.currentTimeMillis() - jLongValue >= this.F.getCoolTime();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aP() {
        a.C0074a c0074a = new a.C0074a(this.f5926n);
        c0074a.a(new a.c() { // from class: com.beizi.fusion.work.nativead.b.2
            @Override // com.beizi.fusion.widget.dialog.dislike.a.c
            public void a() {
                if (((com.beizi.fusion.work.a) b.this).f5540d != null && ((com.beizi.fusion.work.a) b.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) b.this).f5540d.b(b.this.g(), b.this.f5935w);
                }
                b.this.M();
            }
        });
        c0074a.a().show();
    }

    private boolean aQ() {
        return ai.a(this.A.getRenderRate());
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "BEIZI";
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.f5933u == null) {
            return;
        }
        aq();
    }

    @Override // com.beizi.fusion.work.a
    public com.beizi.fusion.f.a k() {
        return this.f5930r;
    }

    @Override // com.beizi.fusion.work.a
    public String l() {
        NativeAd nativeAd = this.f5933u;
        if (nativeAd == null) {
            return null;
        }
        return nativeAd.getPrice();
    }

    @Override // com.beizi.fusion.work.a
    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        AdSpacesBean.BuyerBean.ShakeViewBean shakeView = this.f5541e.getShakeView();
        this.A = shakeView;
        if (shakeView != null) {
            this.B = shakeView.getCoolShakeView();
        }
        this.C = new ad(this.f5926n);
        this.D = "cool_" + this.f5545i;
        this.L = "dl_cool_" + this.f5545i;
        if (this.f5931s <= 0.0f) {
            this.f5931s = as.k(this.f5926n);
        }
        if (this.f5932t <= 0.0f) {
            this.f5932t = 0.0f;
        }
        NativeAd nativeAd = new NativeAd(this.f5926n, this.f5545i, 3, new NativeAdListener() { // from class: com.beizi.fusion.work.nativead.b.3
            @Override // com.beizi.ad.NativeAdListener
            public void onAdClick() {
                Log.d("BeiZis", "showBeiZiNativeAd onAdClick()");
            }

            @Override // com.beizi.ad.NativeAdListener
            public void onAdFailed(int i2) {
                Log.d("BeiZis", "showBeiZiNativeAd onAdFailed: " + i2);
                b.this.b(String.valueOf(i2), i2);
            }

            @Override // com.beizi.ad.NativeAdListener
            public void onAdLoaded(NativeAdResponse nativeAdResponse) {
                Log.d("BeiZis", "showBeiZiNativeAd onAdLoaded()");
                b.this.f5930r = com.beizi.fusion.f.a.ADLOAD;
                if (b.this.f5933u.getPrice() != null) {
                    try {
                        b bVar = b.this;
                        bVar.a(Double.parseDouble(bVar.f5933u.getPrice()));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                b.this.E();
                if (nativeAdResponse == null) {
                    b.this.e(-991);
                } else {
                    b.this.c(nativeAdResponse);
                }
            }
        });
        this.f5933u = nativeAd;
        nativeAd.openAdInNativeBrowser(true);
        this.f5933u.loadAd();
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        NativeAd nativeAd = this.f5933u;
        if (nativeAd != null) {
            nativeAd.cancel();
        }
        ad adVar = this.C;
        if (adVar != null) {
            adVar.c();
        }
    }

    @Override // com.beizi.fusion.work.a
    public View s() {
        return this.f5935w;
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        if (this.f5540d == null) {
            return;
        }
        this.f5544h = this.f5541e.getAppId();
        this.f5545i = this.f5541e.getSpaceId();
        this.f5539c = com.beizi.fusion.f.b.a(this.f5541e.getId());
        List<AdSpacesBean.RenderViewBean> renderView = this.f5541e.getRenderView();
        this.f5936x = renderView;
        if (renderView != null && renderView.size() > 0) {
            AdSpacesBean.RenderViewBean renderViewBean = this.f5936x.get(0);
            this.f5937y = renderViewBean;
            this.f5938z = r.a(renderViewBean.getDpLinkUrlList());
        }
        com.beizi.fusion.b.d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.beizi.ad.BeiZi")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.nativead.b.1
                        @Override // java.lang.Runnable
                        public void run() {
                            b.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "BeiZi sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    x.a(this.f5926n, this.f5544h);
                    B();
                }
            }
        }
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5929q);
        long j2 = this.f5929q;
        if (j2 > 0) {
            this.f5549m.sendEmptyMessageDelayed(1, j2);
            return;
        }
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null || eVar.r() >= 1 || this.f5540d.q() == 2) {
            return;
        }
        p();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(final NativeAdResponse nativeAdResponse) {
        if (nativeAdResponse == null) {
            e(-991);
            return;
        }
        final ImageView imageView = new ImageView(this.f5926n);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setVisibility(0);
        NativeAdUtil.getOneAdBitmap(nativeAdResponse, new ImageManager.BitmapLoadedListener() { // from class: com.beizi.fusion.work.nativead.b.4
            @Override // com.beizi.ad.internal.utilities.ImageManager.BitmapLoadedListener
            public void onBitmapLoadFailed() {
                Log.d("BeiZis", "showBeiZiNativeAd onBitmapLoadFailed");
                b.this.b("sdk custom error ".concat("onBitmapLoadFailed"), 99991);
            }

            @Override // com.beizi.ad.internal.utilities.ImageManager.BitmapLoadedListener
            public void onBitmapLoaded(Bitmap bitmap) {
                Log.d("BeiZis", "showBeiZiNativeAd onBitmapLoaded");
                imageView.setImageBitmap(bitmap);
                int iA = as.a(b.this.f5926n, b.this.f5931s);
                int iA2 = b.this.f5932t > 0.0f ? as.a(b.this.f5926n, b.this.f5932t) : -2;
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iA, iA2);
                if (b.this.f5934v != null) {
                    b.this.f5934v.removeAllViews();
                    StringBuilder sb = new StringBuilder();
                    sb.append("mNativeAd != null ? ");
                    sb.append(b.this.f5933u != null);
                    sb.append(",renderViewBean != null ? ");
                    sb.append(b.this.f5937y != null);
                    ac.a("BeiZis", sb.toString());
                    b.this.f5934v.addView(imageView, layoutParams);
                    b.this.a(nativeAdResponse, iA, iA2, bitmap.getHeight());
                    b.this.aL();
                    if (((com.beizi.fusion.work.a) b.this).f5538b != null && b.this.F != null) {
                        ((com.beizi.fusion.work.a) b.this).f5538b.Q(b.this.F.getDislikeUuid());
                        b.this.aB();
                    }
                    if (b.this.aM()) {
                        b.this.d(nativeAdResponse);
                    }
                    b bVar = b.this;
                    bVar.f5935w = bVar.f5934v;
                    b.this.C.a(b.this.f5935w);
                }
                if (b.this.f5933u != null && b.this.f5937y != null) {
                    b.this.f5933u.setOrderOptimizeList(b.this.f5938z);
                    b.this.f5933u.setAdOptimizePercent(b.this.f5937y.getOptimizePercent());
                    ac.a("BeiZis", "percent = " + b.this.f5937y.getOptimizePercent());
                    b.this.f5934v.post(new Runnable() { // from class: com.beizi.fusion.work.nativead.b.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            NativeAd nativeAd = b.this.f5933u;
                            int optimizeSize = b.this.f5937y.getOptimizeSize();
                            AnonymousClass4 anonymousClass4 = AnonymousClass4.this;
                            nativeAd.optimizeClickArea(optimizeSize, imageView, b.this.f5934v, b.this.f5937y.getDirection());
                        }
                    });
                }
                b.this.b(nativeAdResponse);
            }
        });
        NativeAdUtil.registerTracking(nativeAdResponse, imageView, new NativeAdEventListener() { // from class: com.beizi.fusion.work.nativead.b.5

            /* renamed from: a, reason: collision with root package name */
            boolean f5948a = false;

            /* JADX WARN: Code restructure failed: missing block: B:13:0x0034, code lost:
            
                r0 = com.beizi.fusion.g.ai.a(java.lang.Integer.parseInt(r5.getRate()));
             */
            /* JADX WARN: Removed duplicated region for block: B:24:0x0051  */
            /* JADX WARN: Removed duplicated region for block: B:29:0x0078 A[ADDED_TO_REGION] */
            /* JADX WARN: Removed duplicated region for block: B:34:0x0088  */
            /* JADX WARN: Removed duplicated region for block: B:39:0x00b6  */
            /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
            @Override // com.beizi.ad.internal.nativead.NativeAdEventListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onAdWasClicked() {
                /*
                    r8 = this;
                    java.lang.String r0 = "showBeiZiNativeAd onAdWasClicked"
                    java.lang.String r1 = "BeiZis"
                    android.util.Log.d(r1, r0)
                    r0 = 0
                    com.beizi.fusion.work.nativead.b r2 = com.beizi.fusion.work.nativead.b.this     // Catch: java.lang.Exception -> L48
                    com.beizi.fusion.model.AdSpacesBean$BuyerBean r2 = com.beizi.fusion.work.nativead.b.q(r2)     // Catch: java.lang.Exception -> L48
                    java.util.List r2 = r2.getCallBackStrategy()     // Catch: java.lang.Exception -> L48
                    if (r2 == 0) goto L46
                    int r3 = r2.size()     // Catch: java.lang.Exception -> L48
                    if (r3 <= 0) goto L46
                    r3 = r0
                L1b:
                    r4 = 1
                    int r5 = r2.size()     // Catch: java.lang.Exception -> L44
                    if (r3 >= r5) goto L4d
                    java.lang.Object r5 = r2.get(r3)     // Catch: java.lang.Exception -> L44
                    com.beizi.fusion.model.AdSpacesBean$CallBackStrategyBean r5 = (com.beizi.fusion.model.AdSpacesBean.CallBackStrategyBean) r5     // Catch: java.lang.Exception -> L44
                    java.lang.String r6 = "290.300"
                    java.lang.String r7 = r5.getEventCode()     // Catch: java.lang.Exception -> L44
                    boolean r6 = r6.equalsIgnoreCase(r7)     // Catch: java.lang.Exception -> L44
                    if (r6 == 0) goto L41
                    java.lang.String r2 = r5.getRate()     // Catch: java.lang.Exception -> L44
                    int r2 = java.lang.Integer.parseInt(r2)     // Catch: java.lang.Exception -> L44
                    boolean r0 = com.beizi.fusion.g.ai.a(r2)     // Catch: java.lang.Exception -> L44
                    goto L4d
                L41:
                    int r3 = r3 + 1
                    goto L1b
                L44:
                    r2 = move-exception
                    goto L4a
                L46:
                    r2 = r0
                    goto L4f
                L48:
                    r2 = move-exception
                    r4 = r0
                L4a:
                    r2.printStackTrace()
                L4d:
                    r2 = r0
                    r0 = r4
                L4f:
                    if (r0 == 0) goto L71
                    com.beizi.fusion.work.nativead.b r3 = com.beizi.fusion.work.nativead.b.this
                    com.beizi.fusion.model.AdSpacesBean$BuyerBean r3 = com.beizi.fusion.work.nativead.b.r(r3)
                    if (r3 == 0) goto L71
                    com.beizi.fusion.work.nativead.b r3 = com.beizi.fusion.work.nativead.b.this
                    com.beizi.fusion.b.b r3 = com.beizi.fusion.work.nativead.b.t(r3)
                    com.beizi.fusion.work.nativead.b r4 = com.beizi.fusion.work.nativead.b.this
                    com.beizi.fusion.model.AdSpacesBean$BuyerBean r4 = com.beizi.fusion.work.nativead.b.s(r4)
                    java.lang.String r4 = r4.getCallBackStrategyUuid()
                    r3.P(r4)
                    com.beizi.fusion.work.nativead.b r3 = com.beizi.fusion.work.nativead.b.this
                    com.beizi.fusion.work.nativead.b.u(r3)
                L71:
                    com.beizi.fusion.work.nativead.b r3 = com.beizi.fusion.work.nativead.b.this
                    com.beizi.fusion.work.nativead.b.v(r3)
                    if (r0 == 0) goto L80
                    if (r2 != 0) goto L80
                    java.lang.String r0 = "strategy not pass"
                    android.util.Log.e(r1, r0)
                    return
                L80:
                    com.beizi.fusion.work.nativead.b r0 = com.beizi.fusion.work.nativead.b.this
                    com.beizi.fusion.d.e r0 = com.beizi.fusion.work.nativead.b.w(r0)
                    if (r0 == 0) goto La4
                    com.beizi.fusion.work.nativead.b r0 = com.beizi.fusion.work.nativead.b.this
                    com.beizi.fusion.d.e r0 = com.beizi.fusion.work.nativead.b.x(r0)
                    int r0 = r0.q()
                    r1 = 2
                    if (r0 == r1) goto La4
                    com.beizi.fusion.work.nativead.b r0 = com.beizi.fusion.work.nativead.b.this
                    com.beizi.fusion.d.e r0 = com.beizi.fusion.work.nativead.b.y(r0)
                    com.beizi.fusion.work.nativead.b r1 = com.beizi.fusion.work.nativead.b.this
                    java.lang.String r1 = r1.g()
                    r0.d(r1)
                La4:
                    com.beizi.fusion.work.nativead.b r0 = com.beizi.fusion.work.nativead.b.this
                    com.beizi.fusion.work.nativead.b.z(r0)
                    com.beizi.fusion.work.nativead.b r0 = com.beizi.fusion.work.nativead.b.this
                    com.beizi.fusion.work.nativead.b.A(r0)
                    com.beizi.fusion.work.nativead.b r0 = com.beizi.fusion.work.nativead.b.this
                    com.beizi.ad.NativeAd r0 = com.beizi.fusion.work.nativead.b.a(r0)
                    if (r0 == 0) goto Lbf
                    com.beizi.fusion.work.nativead.b r0 = com.beizi.fusion.work.nativead.b.this
                    com.beizi.ad.NativeAd r0 = com.beizi.fusion.work.nativead.b.a(r0)
                    r0.setTouchAreaNormal()
                Lbf:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.beizi.fusion.work.nativead.b.AnonymousClass5.onAdWasClicked():void");
            }

            @Override // com.beizi.ad.internal.nativead.NativeAdEventListener
            public void onAdWillLeaveApplication() {
                Log.d("BeiZis", "showBeiZiNativeAd onAdWillLeaveApplication");
            }
        });
        NativeAdUtil.registerShow(nativeAdResponse, imageView, new NativeAdShownListener() { // from class: com.beizi.fusion.work.nativead.b.6
            @Override // com.beizi.ad.internal.nativead.NativeAdShownListener
            public void onAdShown() {
                Log.d("BeiZis", "showBeiZiNativeAd onAdShown()");
                b.this.f5930r = com.beizi.fusion.f.a.ADSHOW;
                if (((com.beizi.fusion.work.a) b.this).f5540d != null && ((com.beizi.fusion.work.a) b.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) b.this).f5540d.b(b.this.g());
                }
                b.this.I();
                b.this.J();
                b.this.am();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(NativeAdResponse nativeAdResponse) {
        if (ac()) {
            a(nativeAdResponse);
        } else {
            S();
        }
    }

    private boolean b(final AdSpacesBean.BuyerBean.ShakeViewBean shakeViewBean) {
        long jLongValue = ((Long) aq.b(this.f5926n, this.D, 0L)).longValue();
        if (jLongValue != 0) {
            long jCurrentTimeMillis = System.currentTimeMillis() - jLongValue;
            if (jCurrentTimeMillis < this.B.getCoolTime()) {
                new Handler().postDelayed(new Runnable() { // from class: com.beizi.fusion.work.nativead.b.8
                    @Override // java.lang.Runnable
                    public void run() {
                        b.this.C.a(shakeViewBean);
                    }
                }, this.B.getCoolTime() - jCurrentTimeMillis);
                return true;
            }
            an.a().a(this.D);
        }
        return false;
    }

    private void a(NativeAdResponse nativeAdResponse) {
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " NativeAdWorker:" + eVar.p().toString());
        ad();
        h hVar = this.f5543g;
        if (hVar == h.SUCCESS) {
            if (this.f5935w != null) {
                this.f5540d.a(g(), this.f5935w);
                return;
            } else {
                this.f5540d.b(R2.drawable.ic_coupon_middle);
                return;
            }
        }
        if (hVar == h.FAIL) {
            Log.d("BeiZis", "other worker shown," + g() + " remove");
        }
    }

    private boolean b() {
        try {
            return System.currentTimeMillis() - this.f5926n.getPackageManager().getPackageInfo(this.f5926n.getPackageName(), 0).firstInstallTime < this.B.getUserProtectTime();
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private AdSpacesBean.BuyerBean.DislikeConfigBean b(List<AdSpacesBean.BuyerBean.OrderDataDislikeConfigBean> list, String str) {
        AdSpacesBean.BuyerBean.DislikeConfigBean dislike;
        if (list != null && str != null) {
            for (AdSpacesBean.BuyerBean.OrderDataDislikeConfigBean orderDataDislikeConfigBean : list) {
                List<String> orderList = orderDataDislikeConfigBean.getOrderList();
                if (orderList != null && orderList.contains(str) && (dislike = orderDataDislikeConfigBean.getDislike()) != null) {
                    return dislike;
                }
            }
        }
        return null;
    }

    private AdSpacesBean.BuyerBean.OrderDataShakeViewBean a(List<AdSpacesBean.BuyerBean.OrderDataShakeViewBean> list, String str) {
        if (list != null && str != null) {
            for (AdSpacesBean.BuyerBean.OrderDataShakeViewBean orderDataShakeViewBean : list) {
                List<String> orderList = orderDataShakeViewBean.getOrderList();
                if (orderList != null && orderList.contains(str)) {
                    return orderDataShakeViewBean;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final NativeAdResponse nativeAdResponse, final int i2, final int i3, int i4) {
        AdSpacesBean.BuyerBean.ShakeViewBean shakeViewBean;
        try {
            if (!aQ() || (shakeViewBean = this.A) == null || this.C == null || shakeViewBean.getPosition() == null) {
                return;
            }
            com.beizi.fusion.b.b bVar = this.f5538b;
            if (bVar != null) {
                bVar.G(this.A.getShakeViewUuid());
                aB();
            }
            AdSpacesBean.BuyerBean.OrderDataShakeViewBean orderDataShakeViewBeanA = a(this.A.getOrderData(), this.f5933u.getAdId());
            AdSpacesBean.BuyerBean.ShakeViewBean shakeView = (orderDataShakeViewBeanA == null || orderDataShakeViewBeanA.getShakeView() == null) ? null : orderDataShakeViewBeanA.getShakeView();
            if (i3 <= 0) {
                i3 = i4;
            }
            View viewA = this.C.a(as.b(this.f5926n, i2), as.b(this.f5926n, i3), this.A.getPosition());
            if (viewA != null) {
                ViewGroup.LayoutParams layoutParams = viewA.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(marginLayoutParams.width, marginLayoutParams.height);
                    layoutParams2.leftMargin = marginLayoutParams.leftMargin;
                    layoutParams2.topMargin = marginLayoutParams.topMargin;
                    AdSpacesBean.BuyerBean.ShakeViewBean shakeViewBean2 = this.A;
                    if (shakeViewBean2 != null && shakeViewBean2.getIsHideAnim() == 0) {
                        this.f5934v.addView(viewA, layoutParams2);
                    }
                }
            }
            a(shakeView);
            this.C.a(new ad.a() { // from class: com.beizi.fusion.work.nativead.b.7
                @Override // com.beizi.fusion.g.ad.a
                public void a() {
                    if (au.a(b.this.f5935w)) {
                        int[] iArr = new int[2];
                        b.this.f5935w.getLocationOnScreen(iArr);
                        int[] iArrA = ai.a(i2 / 2, i3 / 2);
                        NativeAdUtil.handleClick(nativeAdResponse, b.this.f5935w, String.valueOf(iArrA[0]), String.valueOf(iArrA[1]), String.valueOf(iArrA[0] + iArr[0]), String.valueOf(iArrA[1] + iArr[1]), 2);
                        if (!b.this.E || b.this.B == null) {
                            return;
                        }
                        b.this.E = false;
                        b.this.C.a(b.this.B);
                        aq.a(b.this.f5926n, b.this.D, Long.valueOf(System.currentTimeMillis()));
                        an.a().a(b.this.D, System.currentTimeMillis());
                    }
                }
            });
            AdSpacesBean.BuyerBean.CoolShakeViewBean coolShakeViewBean = this.B;
            if (coolShakeViewBean != null) {
                this.C.a(coolShakeViewBean, this.D);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(final NativeAdResponse nativeAdResponse) {
        ImageView imageView = new ImageView(this.f5926n);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setVisibility(0);
        imageView.setImageResource(R.drawable.ic_white_close);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(35, 35);
        layoutParams.rightMargin = 20;
        layoutParams.topMargin = 20;
        layoutParams.gravity = 5;
        this.f5934v.addView(imageView, layoutParams);
        imageView.setOnTouchListener(new View.OnTouchListener() { // from class: com.beizi.fusion.work.nativead.b.9
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 1) {
                    return false;
                }
                b.this.H = motionEvent.getX();
                b.this.I = motionEvent.getY();
                b.this.J = motionEvent.getRawX();
                b.this.K = motionEvent.getRawY();
                return false;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.work.nativead.b.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!b.this.aN()) {
                    b.this.aP();
                } else if (!b.this.aO()) {
                    b.this.aP();
                } else {
                    aq.a(b.this.f5926n, b.this.L, Long.valueOf(System.currentTimeMillis()));
                    NativeAdUtil.handleClick(nativeAdResponse, b.this.f5935w, String.valueOf(b.this.H), String.valueOf(b.this.I), String.valueOf(b.this.J), String.valueOf(b.this.K), 0);
                }
            }
        });
    }

    private void a(AdSpacesBean.BuyerBean.ShakeViewBean shakeViewBean) {
        if (shakeViewBean == null) {
            shakeViewBean = this.A;
        }
        if (this.B == null) {
            this.E = true;
            this.C.a(shakeViewBean);
        } else if (b(shakeViewBean)) {
            this.C.a(this.B);
        } else if (b()) {
            this.C.a(this.B);
        } else {
            this.E = true;
            this.C.a(shakeViewBean);
        }
    }
}
