package com.beizi.fusion.work.interstitial;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.beizi.fusion.d.e;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.k;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ah;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.n;
import com.beizi.fusion.g.u;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.work.interstitial.GdtNativeInterstitialCustomLayout;
import com.plv.socket.user.PLVAuthorizationBean;
import com.qq.e.ads.nativ.NativeADEventListener;
import com.qq.e.ads.nativ.NativeADMediaListener;
import com.qq.e.ads.nativ.NativeADUnifiedListener;
import com.qq.e.ads.nativ.NativeUnifiedAD;
import com.qq.e.ads.nativ.NativeUnifiedADData;
import com.qq.e.comm.managers.status.SDKStatus;
import com.qq.e.comm.pi.IBidding;
import com.qq.e.comm.util.AdError;
import com.yikaobang.yixue.R2;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class c extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c, GdtNativeInterstitialCustomLayout.a {
    private CountDownTimer A;
    private Activity C;
    private AdSpacesBean.RenderViewBean D;

    /* renamed from: n, reason: collision with root package name */
    View f5865n;

    /* renamed from: o, reason: collision with root package name */
    TextView f5866o;

    /* renamed from: p, reason: collision with root package name */
    RelativeLayout f5867p;

    /* renamed from: q, reason: collision with root package name */
    private Context f5868q;

    /* renamed from: r, reason: collision with root package name */
    private String f5869r;

    /* renamed from: s, reason: collision with root package name */
    private long f5870s;

    /* renamed from: t, reason: collision with root package name */
    private long f5871t;

    /* renamed from: u, reason: collision with root package name */
    private boolean f5872u;

    /* renamed from: v, reason: collision with root package name */
    private NativeUnifiedAD f5873v;

    /* renamed from: w, reason: collision with root package name */
    private NativeUnifiedADData f5874w;

    /* renamed from: x, reason: collision with root package name */
    private float f5875x;

    /* renamed from: y, reason: collision with root package name */
    private float f5876y;

    /* renamed from: z, reason: collision with root package name */
    private FrameLayout f5877z;
    private long B = 5000;
    private boolean E = false;

    public class a implements NativeADUnifiedListener {
        private a() {
        }

        public void onADLoaded(List<NativeUnifiedADData> list) {
            Log.d("BeiZis", "ShowGdtInterstitialCustom onADLoaded()");
            ((com.beizi.fusion.work.a) c.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
            c.this.E();
            if (list == null || list.size() == 0) {
                c.this.e(-991);
                return;
            }
            c.this.f5874w = list.get(0);
            if (c.this.f5874w == null) {
                c.this.e(-991);
                return;
            }
            if (c.this.f5874w.getECPM() > 0) {
                c.this.a(r10.f5874w.getECPM());
            }
            if (u.f5253a) {
                c.this.f5874w.setDownloadConfirmListener(u.f5254b);
            }
            NativeADEventListener nativeADEventListener = new NativeADEventListener() { // from class: com.beizi.fusion.work.interstitial.c.a.1

                /* renamed from: a, reason: collision with root package name */
                boolean f5881a = false;

                /* renamed from: b, reason: collision with root package name */
                boolean f5882b = false;

                public void onADClicked() {
                    Log.d("BeiZis", "ShowGdtInterstitialCustom onADClicked()");
                    if (((com.beizi.fusion.work.a) c.this).f5540d != null && ((com.beizi.fusion.work.a) c.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) c.this).f5540d.d(c.this.g());
                    }
                    if (this.f5882b) {
                        return;
                    }
                    this.f5882b = true;
                    c.this.K();
                    c.this.an();
                }

                public void onADError(AdError adError) {
                    Log.d("BeiZis", "ShowGdtInterstitialCustom onADError: " + adError.getErrorMsg());
                    c.this.b(adError.getErrorMsg(), adError.getErrorCode());
                }

                public void onADExposed() {
                    Log.d("BeiZis", "ShowGdtInterstitialCustom onExposed()");
                    ((com.beizi.fusion.work.a) c.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                    if (((com.beizi.fusion.work.a) c.this).f5540d != null && ((com.beizi.fusion.work.a) c.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) c.this).f5540d.b(c.this.g());
                    }
                    if (this.f5881a) {
                        return;
                    }
                    this.f5881a = true;
                    c.this.aG();
                    c.this.I();
                    c.this.J();
                    c.this.am();
                }

                public void onADStatusChanged() {
                    ac.a("BeiZis", "ShowGdtInterstitialCustom onADStatusChanged()");
                }
            };
            NativeADMediaListener nativeADMediaListener = new NativeADMediaListener() { // from class: com.beizi.fusion.work.interstitial.c.a.2

                /* renamed from: a, reason: collision with root package name */
                boolean f5884a = false;

                public void onVideoClicked() {
                    Log.d("BeiZis", "ShowGdtInterstitialCustom MediaView onVideoClicked()");
                    if (((com.beizi.fusion.work.a) c.this).f5540d != null && ((com.beizi.fusion.work.a) c.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) c.this).f5540d.d(c.this.g());
                    }
                    if (this.f5884a) {
                        return;
                    }
                    this.f5884a = true;
                    c.this.K();
                    c.this.an();
                }

                public void onVideoCompleted() {
                    Log.d("BeiZis", "ShowGdtInterstitialCustom MediaView onVideoCompleted()");
                }

                public void onVideoError(AdError adError) {
                    Log.d("BeiZis", "ShowGdtInterstitialCustom MediaView onVideoError: " + adError.getErrorMsg());
                    c.this.b(adError.getErrorMsg(), adError.getErrorCode());
                }

                public void onVideoInit() {
                    Log.d("BeiZis", "ShowGdtInterstitialCustom MediaView onVideoInit()");
                }

                public void onVideoLoaded(int i2) {
                    Log.d("BeiZis", "ShowGdtInterstitialCustom MediaView onVideoLoaded()");
                }

                public void onVideoLoading() {
                    Log.d("BeiZis", "ShowGdtInterstitialCustom MediaView onVideoLoading()");
                }

                public void onVideoPause() {
                    Log.d("BeiZis", "ShowGdtInterstitialCustom MediaView onVideoPause()");
                }

                public void onVideoReady() {
                    Log.d("BeiZis", "ShowGdtInterstitialCustom MediaView onVideoReady()");
                }

                public void onVideoResume() {
                    Log.d("BeiZis", "ShowGdtInterstitialCustom MediaView onVideoResume()");
                }

                public void onVideoStart() {
                    Log.d("BeiZis", "ShowGdtInterstitialCustom MediaView onVideoStart()");
                }

                public void onVideoStop() {
                    Log.d("BeiZis", "ShowGdtInterstitialCustom MediaView onVideoStop()");
                }
            };
            View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.beizi.fusion.work.interstitial.c.a.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Log.d("BeiZis", "ShowGdtInterstitialCustom onADClosed()");
                    c.this.N();
                    c.this.aQ();
                }
            };
            GdtNativeInterstitialCustomLayout gdtNativeInterstitialCustomLayout = new GdtNativeInterstitialCustomLayout(c.this.f5868q);
            gdtNativeInterstitialCustomLayout.setViewInteractionListener(c.this);
            boolean zOnBindData = gdtNativeInterstitialCustomLayout.onBindData(c.this.f5874w, c.this.f5875x, c.this.f5876y, c.this.D, nativeADEventListener, nativeADMediaListener, onClickListener);
            if (c.this.f5874w.getAdPatternType() == 2 && c.this.D != null && c.this.D.getVideoSkipTime() > 0) {
                c.this.B = r1.D.getVideoSkipTime();
            }
            if (zOnBindData) {
                c.this.f5877z = gdtNativeInterstitialCustomLayout;
                c.this.aO();
            } else {
                c cVar = c.this;
                cVar.b("sdk custom error ".concat(cVar.g()).concat(" ").concat("create view error"), R2.drawable.ic_coupon_middle);
            }
        }

        public void onNoAD(AdError adError) {
            Log.d("BeiZis", "ShowGdtInterstitialCustom onNoAD: " + adError.getErrorMsg());
            c.this.b(adError.getErrorMsg(), adError.getErrorCode());
        }
    }

    public c(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, e eVar) {
        this.f5868q = context;
        this.f5869r = str;
        this.f5870s = j2;
        this.f5871t = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        int sizeRatio = buyerBean.getSizeRatio();
        float fK = as.k(context) * 0.8f;
        this.f5875x = fK;
        this.f5876y = sizeRatio == 1 ? (fK * 16.0f) / 9.0f : (fK * 9.0f) / 16.0f;
        ac.a("BeiZis", "interstitial mAdWidthDp = " + this.f5875x + ",mAdHeightDp = " + this.f5876y);
        x();
    }

    private void aL() {
        TextView textView = new TextView(this.f5868q);
        this.f5866o = textView;
        textView.setTextColor(this.f5868q.getResources().getColor(R.color.white));
        this.f5866o.setTextSize(2, 14.0f);
        g((int) (this.B / 1000));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(2, 107011);
        layoutParams.addRule(7, 107011);
        layoutParams.bottomMargin = as.a(this.f5868q, 3.0f);
        RelativeLayout relativeLayout = this.f5867p;
        if (relativeLayout != null) {
            relativeLayout.addView(this.f5866o, layoutParams);
        }
    }

    private void aM() {
        ((FrameLayout) this.f5865n).removeView(this.f5867p);
    }

    private void aN() {
        e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " NativeAdWorker:" + eVar.p().toString());
        ad();
        h hVar = this.f5543g;
        if (hVar == h.SUCCESS) {
            if (this.f5877z != null) {
                this.f5540d.a(g(), this.f5877z);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void aO() {
        if (ac()) {
            aN();
        } else {
            S();
        }
    }

    private void aP() {
        CountDownTimer countDownTimer = this.A;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        CountDownTimer countDownTimer2 = new CountDownTimer(100 + this.B, 50L) { // from class: com.beizi.fusion.work.interstitial.c.2
            @Override // android.os.CountDownTimer
            public void onFinish() {
                c.this.aQ();
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j2) {
                if (((com.beizi.fusion.work.a) c.this).f5540d != null && ((com.beizi.fusion.work.a) c.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) c.this).f5540d.a(j2);
                }
                c.this.g((int) (j2 / 1000.0f));
            }
        };
        this.A = countDownTimer2;
        countDownTimer2.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aQ() {
        ah();
        M();
        c(this.C);
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.f5874w == null) {
            return;
        }
        aq();
        int iA = ah.a(this.f5541e.getPriceDict(), this.f5874w.getECPMLevel());
        if (iA == -1 || iA == -2) {
            if (iA == -2) {
                Q();
            }
        } else {
            Log.d("BeiZisBid", "gdt realPrice = " + iA);
            a((double) iA);
        }
    }

    @Override // com.beizi.fusion.work.a
    public void aG() {
        NativeUnifiedADData nativeUnifiedADData = this.f5874w;
        if (nativeUnifiedADData == null || nativeUnifiedADData.getECPM() <= 0 || this.f5872u) {
            return;
        }
        this.f5872u = true;
        ac.a("BeiZis", "channel == GDT竞价成功");
        ac.a("BeiZis", "channel == sendWinNoticeECPM" + this.f5874w.getECPM());
        NativeUnifiedADData nativeUnifiedADData2 = this.f5874w;
        k.a((IBidding) nativeUnifiedADData2, nativeUnifiedADData2.getECPM());
    }

    @Override // com.beizi.fusion.work.interstitial.GdtNativeInterstitialCustomLayout.a
    public void a_() {
        r();
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "GDT";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g(int i2) {
        if (this.f5866o == null) {
            return;
        }
        int i3 = 1;
        String str = String.format(Locale.CHINA, "广告%d秒后自动关闭", Integer.valueOf(i2));
        if (i2 >= 10 && i2 <= 99) {
            i3 = 2;
        }
        String strValueOf = String.valueOf(i2);
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT)), 0, str.indexOf(strValueOf), 17);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT)), str.indexOf(strValueOf) + i3, str.length(), 17);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff9933")), str.indexOf(strValueOf), str.indexOf(strValueOf) + i3, 17);
        this.f5866o.setText(spannableString);
    }

    @Override // com.beizi.fusion.work.a
    public void f(int i2) {
        NativeUnifiedADData nativeUnifiedADData = this.f5874w;
        if (nativeUnifiedADData == null || nativeUnifiedADData.getECPM() <= 0 || this.f5872u) {
            return;
        }
        this.f5872u = true;
        ac.a("BeiZis", "channel == GDT竞价失败:" + i2);
        k.b((IBidding) this.f5874w, i2 != 1 ? 10001 : 1);
    }

    @Override // com.beizi.fusion.work.a
    public com.beizi.fusion.f.a k() {
        return this.f5546j;
    }

    @Override // com.beizi.fusion.work.a
    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        if (this.f5875x <= 0.0f) {
            this.f5875x = as.k(this.f5868q);
        }
        if (this.f5876y <= 0.0f) {
            this.f5876y = 0.0f;
        }
        if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
            this.f5873v = new NativeUnifiedAD(this.f5868q, this.f5545i, new a(), aJ());
        } else {
            this.f5873v = new NativeUnifiedAD(this.f5868q, this.f5545i, new a());
        }
        this.f5873v.loadData(1);
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        NativeUnifiedADData nativeUnifiedADData = this.f5874w;
        if (nativeUnifiedADData != null) {
            nativeUnifiedADData.destroy();
        }
    }

    @Override // com.beizi.fusion.work.a
    public void r() {
        NativeUnifiedADData nativeUnifiedADData = this.f5874w;
        if (nativeUnifiedADData != null) {
            nativeUnifiedADData.resume();
        }
    }

    @Override // com.beizi.fusion.work.a
    public View s() {
        return this.f5877z;
    }

    private void c(Activity activity) {
        if (activity != null) {
            if (this.f5865n == null) {
                this.f5865n = activity.getWindow().getDecorView();
            }
            if (this.f5865n instanceof FrameLayout) {
                aM();
            }
        }
        q();
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
        if (renderView != null && renderView.size() > 0) {
            AdSpacesBean.RenderViewBean renderViewBean = renderView.get(0);
            this.D = renderViewBean;
            this.B = renderViewBean.getPicSkipTime() > 0 ? this.D.getPicSkipTime() : this.B;
        }
        com.beizi.fusion.b.d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.qq.e.comm.managers.GDTAdSdk")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.interstitial.c.1
                        @Override // java.lang.Runnable
                        public void run() {
                            c.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "GDT sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    k.a(this.f5868q, this.f5544h);
                    this.f5538b.s(SDKStatus.getIntegrationSDKVersion());
                    aB();
                    B();
                }
            }
        }
        u.f5253a = !n.a(this.f5541e.getDirectDownload());
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5871t);
        long j2 = this.f5871t;
        if (j2 > 0) {
            this.f5549m.sendEmptyMessageDelayed(1, j2);
            return;
        }
        e eVar = this.f5540d;
        if (eVar == null || eVar.r() >= 1 || this.f5540d.q() == 2) {
            return;
        }
        p();
    }

    private void b(Activity activity) {
        if (activity != null) {
            View decorView = activity.getWindow().getDecorView();
            this.f5865n = decorView;
            if (decorView instanceof FrameLayout) {
                if (this.f5867p != null) {
                    aM();
                }
                RelativeLayout relativeLayout = new RelativeLayout(this.f5868q);
                this.f5867p = relativeLayout;
                relativeLayout.setBackgroundColor(Color.parseColor("#B2000000"));
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
                RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
                layoutParams2.addRule(13);
                this.f5877z.setId(107011);
                as.a(this.f5877z);
                this.f5867p.addView(this.f5877z, layoutParams2);
                ((FrameLayout) this.f5865n).addView(this.f5867p, layoutParams);
            }
        }
    }

    @Override // com.beizi.fusion.work.a
    public void a(Activity activity) {
        if (this.E) {
            return;
        }
        this.E = true;
        this.C = activity;
        b(activity);
        aL();
        aP();
    }

    @Override // com.beizi.fusion.work.interstitial.GdtNativeInterstitialCustomLayout.a
    public void b() {
        N();
        aQ();
    }
}
