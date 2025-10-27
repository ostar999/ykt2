package com.beizi.fusion.work.interstitial;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import com.beizi.fusion.d.e;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.k;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ah;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.n;
import com.beizi.fusion.g.u;
import com.beizi.fusion.model.AdSpacesBean;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
import com.qq.e.ads.interstitial2.UnifiedInterstitialMediaListener;
import com.qq.e.comm.managers.status.SDKStatus;
import com.qq.e.comm.pi.IBidding;
import com.qq.e.comm.util.AdError;
import com.yikaobang.yixue.R2;
import java.util.Map;

/* loaded from: classes2.dex */
public class b extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5854n;

    /* renamed from: o, reason: collision with root package name */
    private String f5855o;

    /* renamed from: p, reason: collision with root package name */
    private long f5856p;

    /* renamed from: q, reason: collision with root package name */
    private long f5857q;

    /* renamed from: r, reason: collision with root package name */
    private boolean f5858r;

    /* renamed from: s, reason: collision with root package name */
    private UnifiedInterstitialAD f5859s;

    public class a implements UnifiedInterstitialADListener {

        /* renamed from: a, reason: collision with root package name */
        boolean f5861a;

        /* renamed from: b, reason: collision with root package name */
        boolean f5862b;

        private a() {
            this.f5861a = false;
            this.f5862b = false;
        }

        public void onADClicked() {
            Log.d("BeiZis", "showGdtInterstitialAd onADClicked()");
            if (((com.beizi.fusion.work.a) b.this).f5540d != null && ((com.beizi.fusion.work.a) b.this).f5540d.q() != 2) {
                ((com.beizi.fusion.work.a) b.this).f5540d.d(b.this.g());
            }
            if (this.f5862b) {
                return;
            }
            this.f5862b = true;
            b.this.K();
            b.this.an();
        }

        public void onADClosed() {
            Log.d("BeiZis", "showGdtInterstitialAd onADClosed()");
            if (((com.beizi.fusion.work.a) b.this).f5540d != null && ((com.beizi.fusion.work.a) b.this).f5540d.q() != 2) {
                ((com.beizi.fusion.work.a) b.this).f5540d.c(b.this.g());
            }
            b.this.M();
        }

        public void onADExposure() {
            Log.d("BeiZis", "showGdtInterstitialAd onADExposure()");
            ((com.beizi.fusion.work.a) b.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
            if (((com.beizi.fusion.work.a) b.this).f5540d != null && ((com.beizi.fusion.work.a) b.this).f5540d.q() != 2) {
                ((com.beizi.fusion.work.a) b.this).f5540d.b(b.this.g());
            }
            if (this.f5861a) {
                return;
            }
            this.f5861a = true;
            b.this.aG();
            b.this.I();
            b.this.J();
            b.this.am();
        }

        public void onADLeftApplication() {
            Log.d("BeiZis", "showGdtInterstitialAd onADLeftApplication()");
        }

        public void onADOpened() {
            Log.d("BeiZis", "showGdtInterstitialAd onADOpened()");
        }

        public void onADReceive() {
            Log.d("BeiZis", "showGdtInterstitialAd onADReceive()");
            if (b.this.f5859s != null) {
                if (u.f5253a) {
                    b.this.f5859s.setDownloadConfirmListener(u.f5254b);
                }
                if (b.this.f5859s.getAdPatternType() == 2) {
                    b.this.f5859s.setMediaListener(new UnifiedInterstitialMediaListener() { // from class: com.beizi.fusion.work.interstitial.b.a.1
                        public void onVideoComplete() {
                            Log.d("BeiZis", "showGdtInterstitialAd onVideoComplete()");
                        }

                        public void onVideoError(AdError adError) {
                            Log.d("BeiZis", "showGdtInterstitialAd onVideoError()");
                        }

                        public void onVideoInit() {
                            Log.d("BeiZis", "showGdtInterstitialAd onVideoInit()");
                        }

                        public void onVideoLoading() {
                            Log.d("BeiZis", "showGdtInterstitialAd onVideoLoading()");
                        }

                        public void onVideoPageClose() {
                            Log.d("BeiZis", "showGdtInterstitialAd onVideoPageClose()");
                        }

                        public void onVideoPageOpen() {
                            Log.d("BeiZis", "showGdtInterstitialAd onVideoPageOpen()");
                        }

                        public void onVideoPause() {
                            Log.d("BeiZis", "showGdtInterstitialAd onVideoPause()");
                        }

                        public void onVideoReady(long j2) {
                            Log.d("BeiZis", "showGdtInterstitialAd onVideoReady()");
                        }

                        public void onVideoStart() {
                            Log.d("BeiZis", "showGdtInterstitialAd onVideoStart()");
                        }
                    });
                }
            }
        }

        public void onNoAD(AdError adError) {
            Log.d("BeiZis", "showGdtInterstitialAd onNoAD:" + adError.getErrorMsg());
            b.this.b(adError.getErrorMsg(), adError.getErrorCode());
        }

        public void onRenderFail() {
            Log.d("BeiZis", "showGdtInterstitialAd onRenderFail()");
        }

        public void onRenderSuccess() {
            Log.d("BeiZis", "showGdtInterstitialAd onRenderSuccess()");
            if (b.this.f5859s.getECPM() > 0) {
                b.this.a(r0.f5859s.getECPM());
            }
            ((com.beizi.fusion.work.a) b.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
            b.this.E();
            if (b.this.ac()) {
                b.this.b();
            } else {
                b.this.S();
            }
        }

        public void onVideoCached() {
            Log.d("BeiZis", "showGdtInterstitialAd onVideoCached()");
        }
    }

    public b(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, e eVar) {
        this.f5854n = context;
        this.f5855o = str;
        this.f5856p = j2;
        this.f5857q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        x();
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.f5859s == null) {
            return;
        }
        aq();
        int iA = ah.a(this.f5541e.getPriceDict(), this.f5859s.getECPMLevel());
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
        UnifiedInterstitialAD unifiedInterstitialAD = this.f5859s;
        if (unifiedInterstitialAD == null || unifiedInterstitialAD.getECPM() <= 0 || this.f5858r) {
            return;
        }
        this.f5858r = true;
        ac.a("BeiZis", "channel == GDT竞价成功");
        ac.a("BeiZis", "channel == sendWinNoticeECPM" + this.f5859s.getECPM());
        UnifiedInterstitialAD unifiedInterstitialAD2 = this.f5859s;
        k.a((IBidding) unifiedInterstitialAD2, unifiedInterstitialAD2.getECPM());
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "GDT";
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        if (this.f5540d == null) {
            return;
        }
        this.f5544h = this.f5541e.getAppId();
        this.f5545i = this.f5541e.getSpaceId();
        this.f5539c = com.beizi.fusion.f.b.a(this.f5541e.getId());
        com.beizi.fusion.b.d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.qq.e.ads.interstitial2.UnifiedInterstitialAD")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.interstitial.b.1
                        @Override // java.lang.Runnable
                        public void run() {
                            b.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "GDT sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    k.a(this.f5854n, this.f5544h);
                    this.f5538b.s(SDKStatus.getIntegrationSDKVersion());
                    aB();
                    B();
                }
            }
        }
        u.f5253a = !n.a(this.f5541e.getDirectDownload());
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5857q);
        long j2 = this.f5857q;
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
    public void f(int i2) {
        UnifiedInterstitialAD unifiedInterstitialAD = this.f5859s;
        if (unifiedInterstitialAD == null || unifiedInterstitialAD.getECPM() <= 0 || this.f5858r) {
            return;
        }
        this.f5858r = true;
        ac.a("BeiZis", "channel == GDT竞价失败:" + i2);
        k.b((IBidding) this.f5859s, i2 != 1 ? 10001 : 1);
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
        Activity activity = (Activity) this.f5854n;
        if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
            this.f5859s = new UnifiedInterstitialAD(activity, this.f5545i, new a(), (Map) null, aJ());
        } else {
            this.f5859s = new UnifiedInterstitialAD(activity, this.f5545i, new a());
        }
        this.f5859s.setVideoOption(new VideoOption.Builder().setAutoPlayMuted(true).build());
        this.f5859s.loadAD();
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        UnifiedInterstitialAD unifiedInterstitialAD = this.f5859s;
        if (unifiedInterstitialAD != null) {
            unifiedInterstitialAD.destroy();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " InterstitialWorkers:" + eVar.p().toString());
        ad();
        h hVar = this.f5543g;
        if (hVar == h.SUCCESS) {
            e eVar2 = this.f5540d;
            if (eVar2 != null) {
                eVar2.a(g(), (View) null);
                return;
            }
            return;
        }
        if (hVar == h.FAIL) {
            Log.d("BeiZis", "other worker shown," + g() + " remove");
        }
    }

    @Override // com.beizi.fusion.work.a
    public void a(Activity activity) {
        UnifiedInterstitialAD unifiedInterstitialAD = this.f5859s;
        if (unifiedInterstitialAD != null && unifiedInterstitialAD.isValid()) {
            this.f5859s.show();
            return;
        }
        e eVar = this.f5540d;
        if (eVar != null) {
            eVar.b(R2.drawable.ic_coupon_middle);
        }
    }
}
