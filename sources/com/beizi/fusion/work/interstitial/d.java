package com.beizi.fusion.work.interstitial;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.beizi.fusion.d.e;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.o;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.work.interstitial.KsNativeInterstitialCustomLayout;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsNativeAd;
import com.kwad.sdk.api.KsScene;
import com.plv.socket.user.PLVAuthorizationBean;
import com.yikaobang.yixue.R2;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class d extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c, KsNativeInterstitialCustomLayout.a {
    private Activity B;
    private AdSpacesBean.RenderViewBean C;

    /* renamed from: n, reason: collision with root package name */
    RelativeLayout f5887n;

    /* renamed from: o, reason: collision with root package name */
    View f5888o;

    /* renamed from: p, reason: collision with root package name */
    TextView f5889p;

    /* renamed from: q, reason: collision with root package name */
    KsNativeAd.AdInteractionListener f5890q;

    /* renamed from: r, reason: collision with root package name */
    KsNativeAd.VideoPlayListener f5891r;

    /* renamed from: s, reason: collision with root package name */
    private Context f5892s;

    /* renamed from: t, reason: collision with root package name */
    private String f5893t;

    /* renamed from: u, reason: collision with root package name */
    private long f5894u;

    /* renamed from: v, reason: collision with root package name */
    private long f5895v;

    /* renamed from: w, reason: collision with root package name */
    private float f5896w;

    /* renamed from: x, reason: collision with root package name */
    private float f5897x;

    /* renamed from: y, reason: collision with root package name */
    private FrameLayout f5898y;

    /* renamed from: z, reason: collision with root package name */
    private CountDownTimer f5899z;
    private long A = 5000;
    private boolean D = false;

    public d(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, e eVar) {
        this.f5892s = context;
        this.f5893t = str;
        this.f5894u = j2;
        this.f5895v = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        int sizeRatio = buyerBean.getSizeRatio();
        float fK = as.k(context) * 0.8f;
        this.f5896w = fK;
        this.f5897x = sizeRatio == 1 ? (fK * 16.0f) / 9.0f : (fK * 9.0f) / 16.0f;
        ac.a("BeiZis", "interstitial mAdWidthDp = " + this.f5896w + ",mAdHeightDp = " + this.f5897x);
        x();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aL() {
        Log.d("BeiZis", "ShowKsInterstitialCustom onADClosed()");
        ah();
        M();
        c(this.B);
    }

    private void aM() {
        TextView textView = new TextView(this.f5892s);
        this.f5889p = textView;
        textView.setTextColor(this.f5892s.getResources().getColor(R.color.white));
        this.f5889p.setTextSize(2, 14.0f);
        g((int) (this.A / 1000));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(2, 107017);
        layoutParams.addRule(7, 107017);
        layoutParams.bottomMargin = as.a(this.f5892s, 3.0f);
        RelativeLayout relativeLayout = this.f5887n;
        if (relativeLayout != null) {
            relativeLayout.addView(this.f5889p, layoutParams);
        }
    }

    private void aN() {
        ((FrameLayout) this.f5888o).removeView(this.f5887n);
    }

    private void aO() {
        e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " NativeAdWorker:" + eVar.p().toString());
        ad();
        h hVar = this.f5543g;
        if (hVar == h.SUCCESS) {
            if (this.f5898y != null) {
                this.f5540d.a(g(), this.f5898y);
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
    public void aP() {
        if (ac()) {
            aO();
        } else {
            S();
        }
    }

    private void aQ() {
        CountDownTimer countDownTimer = this.f5899z;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        CountDownTimer countDownTimer2 = new CountDownTimer(100 + this.A, 50L) { // from class: com.beizi.fusion.work.interstitial.d.3
            @Override // android.os.CountDownTimer
            public void onFinish() {
                d.this.aL();
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j2) {
                if (((com.beizi.fusion.work.a) d.this).f5540d != null && ((com.beizi.fusion.work.a) d.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) d.this).f5540d.a(j2);
                }
                d.this.g((int) (j2 / 1000.0f));
            }
        };
        this.f5899z = countDownTimer2;
        countDownTimer2.start();
    }

    @Override // com.beizi.fusion.work.interstitial.KsNativeInterstitialCustomLayout.a
    public void b_() {
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "KUAISHOU";
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
    }

    @Override // com.beizi.fusion.work.a
    public void r() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g(int i2) {
        if (this.f5889p == null) {
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
        this.f5889p.setText(spannableString);
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
            this.C = renderViewBean;
            this.A = renderViewBean.getPicSkipTime() > 0 ? this.C.getPicSkipTime() : this.A;
        }
        com.beizi.fusion.b.d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.kwad.sdk.api.KsAdSDK")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.interstitial.d.1
                        @Override // java.lang.Runnable
                        public void run() {
                            d.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "Ks sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    o.a(this.f5892s, this.f5544h);
                    this.f5538b.u(KsAdSDK.getSDKVersion());
                    aB();
                    B();
                }
            }
        }
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5895v);
        long j2 = this.f5895v;
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
        if (this.f5896w <= 0.0f) {
            this.f5896w = as.k(this.f5892s);
        }
        if (this.f5897x <= 0.0f) {
            this.f5897x = 0.0f;
        }
        KsScene ksSceneBuild = new KsScene.Builder(Long.parseLong(this.f5545i)).adNum(1).build();
        ksSceneBuild.setAdNum(1);
        KsLoadManager loadManager = KsAdSDK.getLoadManager();
        if (loadManager == null) {
            Log.d("BeiZis", "ShowKsInterstitialCustom onError:渠道广告请求对象为空");
            b("渠道广告请求异常", R2.drawable.ic_cut_success_pop_bg_night);
        } else {
            if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
                ksSceneBuild.setBidResponse(aJ());
            }
            loadManager.loadNativeAd(ksSceneBuild, new KsLoadManager.NativeAdListener() { // from class: com.beizi.fusion.work.interstitial.d.2
                public void onError(int i2, String str) {
                    Log.d("BeiZis", "ShowKsInterstitialCustom onNoAD: " + str);
                    d.this.b(str, i2);
                }

                public void onNativeAdLoad(@Nullable List<KsNativeAd> list) {
                    Log.d("BeiZis", "ShowKsInterstitialCustom onADLoaded()");
                    ((com.beizi.fusion.work.a) d.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                    d.this.E();
                    if (list == null || list.isEmpty()) {
                        d.this.e(-991);
                        return;
                    }
                    if (list.get(0) != null) {
                        d.this.a(list.get(0).getECPM());
                    }
                    d.this.f5890q = new KsNativeAd.AdInteractionListener() { // from class: com.beizi.fusion.work.interstitial.d.2.1

                        /* renamed from: a, reason: collision with root package name */
                        boolean f5902a = false;

                        /* renamed from: b, reason: collision with root package name */
                        boolean f5903b = false;

                        public boolean handleDownloadDialog(DialogInterface.OnClickListener onClickListener) {
                            return false;
                        }

                        public void onAdClicked(View view, KsNativeAd ksNativeAd) {
                            Log.d("BeiZis", "ShowKsInterstitialCustom MediaView onVideoClicked()");
                            if (((com.beizi.fusion.work.a) d.this).f5540d != null && ((com.beizi.fusion.work.a) d.this).f5540d.q() != 2) {
                                ((com.beizi.fusion.work.a) d.this).f5540d.d(d.this.g());
                            }
                            if (this.f5903b) {
                                return;
                            }
                            this.f5903b = true;
                            d.this.K();
                            d.this.an();
                        }

                        public void onAdShow(KsNativeAd ksNativeAd) {
                            Log.d("BeiZis", "ShowKsInterstitialCustom onExposed()");
                            ((com.beizi.fusion.work.a) d.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                            if (((com.beizi.fusion.work.a) d.this).f5540d != null && ((com.beizi.fusion.work.a) d.this).f5540d.q() != 2) {
                                ((com.beizi.fusion.work.a) d.this).f5540d.b(d.this.g());
                            }
                            if (this.f5902a) {
                                return;
                            }
                            this.f5902a = true;
                            d.this.I();
                            d.this.J();
                            d.this.am();
                        }

                        public void onDownloadTipsDialogDismiss() {
                        }

                        public void onDownloadTipsDialogShow() {
                        }
                    };
                    d.this.f5891r = new KsNativeAd.VideoPlayListener() { // from class: com.beizi.fusion.work.interstitial.d.2.2
                        public void onVideoPlayComplete() {
                        }

                        public void onVideoPlayError(int i2, int i3) {
                        }

                        public void onVideoPlayPause() {
                        }

                        public void onVideoPlayReady() {
                        }

                        public void onVideoPlayResume() {
                        }

                        public void onVideoPlayStart() {
                        }
                    };
                    View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.beizi.fusion.work.interstitial.d.2.3
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            d.this.N();
                            d.this.aL();
                        }
                    };
                    KsNativeInterstitialCustomLayout ksNativeInterstitialCustomLayout = new KsNativeInterstitialCustomLayout(d.this.f5892s);
                    ksNativeInterstitialCustomLayout.setViewInteractionListener(d.this);
                    KsNativeAd ksNativeAd = list.get(0);
                    float f2 = d.this.f5896w;
                    float f3 = d.this.f5897x;
                    AdSpacesBean.RenderViewBean renderViewBean = d.this.C;
                    d dVar = d.this;
                    boolean zOnBindData = ksNativeInterstitialCustomLayout.onBindData(ksNativeAd, f2, f3, renderViewBean, dVar.f5890q, dVar.f5891r, onClickListener);
                    if (list.get(0).getMaterialType() == 1 && d.this.C != null && d.this.C.getVideoSkipTime() > 0) {
                        d.this.A = r12.C.getVideoSkipTime();
                    }
                    if (zOnBindData) {
                        d.this.f5898y = ksNativeInterstitialCustomLayout;
                        d.this.aP();
                    } else {
                        d dVar2 = d.this;
                        dVar2.b("sdk custom error ".concat(dVar2.g()).concat(" ").concat("create view error"), R2.drawable.ic_coupon_middle);
                    }
                }
            });
        }
    }

    @Override // com.beizi.fusion.work.a
    public View s() {
        return this.f5898y;
    }

    private void c(Activity activity) {
        if (activity != null) {
            if (this.f5888o == null) {
                this.f5888o = activity.getWindow().getDecorView();
            }
            if (this.f5888o instanceof FrameLayout) {
                aN();
            }
        }
        q();
    }

    private void b(Activity activity) {
        if (activity == null || this.f5898y == null) {
            return;
        }
        View decorView = activity.getWindow().getDecorView();
        this.f5888o = decorView;
        if (decorView instanceof FrameLayout) {
            if (this.f5887n != null) {
                aN();
            }
            RelativeLayout relativeLayout = new RelativeLayout(this.f5892s);
            this.f5887n = relativeLayout;
            relativeLayout.setBackgroundColor(Color.parseColor("#B2000000"));
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams2.addRule(13);
            this.f5898y.setId(107017);
            as.a(this.f5898y);
            this.f5887n.addView(this.f5898y, layoutParams2);
            ((FrameLayout) this.f5888o).addView(this.f5887n, layoutParams);
        }
    }

    @Override // com.beizi.fusion.work.a
    public void a(Activity activity) {
        if (this.D) {
            return;
        }
        this.D = true;
        this.B = activity;
        b(activity);
        aM();
        aQ();
    }

    @Override // com.beizi.fusion.work.interstitial.KsNativeInterstitialCustomLayout.a
    public void b() {
        N();
        aL();
    }
}
