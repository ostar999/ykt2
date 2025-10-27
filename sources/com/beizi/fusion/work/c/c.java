package com.beizi.fusion.work.c;

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
import com.qq.e.comm.managers.status.SDKStatus;
import com.qq.e.comm.pi.IBidding;
import com.qq.e.comm.util.AdError;
import com.yikaobang.yixue.R2;
import java.util.Map;

/* loaded from: classes2.dex */
public class c extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5654n;

    /* renamed from: o, reason: collision with root package name */
    private String f5655o;

    /* renamed from: p, reason: collision with root package name */
    private long f5656p;

    /* renamed from: q, reason: collision with root package name */
    private long f5657q;

    /* renamed from: r, reason: collision with root package name */
    private boolean f5658r;

    /* renamed from: s, reason: collision with root package name */
    private UnifiedInterstitialAD f5659s;

    public class a implements UnifiedInterstitialADListener {

        /* renamed from: a, reason: collision with root package name */
        boolean f5661a;

        /* renamed from: b, reason: collision with root package name */
        boolean f5662b;

        private a() {
            this.f5661a = false;
            this.f5662b = false;
        }

        public void onADClicked() {
            Log.d("BeiZis", "showGdtFullScreenVideo onADClicked()");
            if (((com.beizi.fusion.work.a) c.this).f5540d != null && ((com.beizi.fusion.work.a) c.this).f5540d.q() != 2) {
                ((com.beizi.fusion.work.a) c.this).f5540d.d(c.this.g());
            }
            if (this.f5662b) {
                return;
            }
            this.f5662b = true;
            c.this.K();
            c.this.an();
        }

        public void onADClosed() {
            Log.d("BeiZis", "showGdtFullScreenVideo onADClosed()");
            if (((com.beizi.fusion.work.a) c.this).f5540d != null && ((com.beizi.fusion.work.a) c.this).f5540d.q() != 2) {
                ((com.beizi.fusion.work.a) c.this).f5540d.c(c.this.g());
            }
            c.this.M();
        }

        public void onADExposure() {
            Log.d("BeiZis", "showGdtFullScreenVideo onADExposure()");
            ((com.beizi.fusion.work.a) c.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
            if (((com.beizi.fusion.work.a) c.this).f5540d != null && ((com.beizi.fusion.work.a) c.this).f5540d.q() != 2) {
                ((com.beizi.fusion.work.a) c.this).f5540d.b(c.this.g());
            }
            if (this.f5661a) {
                return;
            }
            this.f5661a = true;
            c.this.aG();
            c.this.I();
            c.this.J();
            c.this.am();
        }

        public void onADLeftApplication() {
            Log.d("BeiZis", "showGdtFullScreenVideo onADLeftApplication()");
        }

        public void onADOpened() {
            Log.d("BeiZis", "showGdtFullScreenVideo onADOpened()");
        }

        public void onADReceive() {
            Log.d("BeiZis", "showGdtFullScreenVideo onADReceive()");
            if (c.this.f5659s.getECPM() > 0) {
                c.this.a(r0.f5659s.getECPM());
            }
            if (u.f5253a) {
                c.this.f5659s.setDownloadConfirmListener(u.f5254b);
            }
            ((com.beizi.fusion.work.a) c.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
            c.this.E();
            if (c.this.ac()) {
                c.this.b();
            } else {
                c.this.S();
            }
        }

        public void onNoAD(AdError adError) {
            Log.d("BeiZis", "showGdtFullScreenVideo onError:" + adError.getErrorMsg());
            c.this.b(adError.getErrorMsg(), adError.getErrorCode());
        }

        public void onRenderFail() {
            Log.d("BeiZis", "showGdtFullScreenVideo onRenderFail()");
        }

        public void onRenderSuccess() {
            Log.d("BeiZis", "showGdtFullScreenVideo onRenderSuccess()");
        }

        public void onVideoCached() {
            Log.d("BeiZis", "showGdtFullScreenVideo onVideoCached()");
        }
    }

    public c(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, e eVar) {
        this.f5654n = context;
        this.f5655o = str;
        this.f5656p = j2;
        this.f5657q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        x();
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.f5659s == null) {
            return;
        }
        aq();
        int iA = ah.a(this.f5541e.getPriceDict(), this.f5659s.getECPMLevel());
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
        UnifiedInterstitialAD unifiedInterstitialAD = this.f5659s;
        if (unifiedInterstitialAD == null || unifiedInterstitialAD.getECPM() <= 0 || this.f5658r) {
            return;
        }
        this.f5658r = true;
        ac.a("BeiZis", "channel == GDT竞价成功");
        ac.a("BeiZis", "channel == sendWinNoticeECPM" + this.f5659s.getECPM());
        UnifiedInterstitialAD unifiedInterstitialAD2 = this.f5659s;
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
                if (!as.a("com.qq.e.comm.managers.GDTAdSdk")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.c.c.1
                        @Override // java.lang.Runnable
                        public void run() {
                            c.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "GDT sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    k.a(this.f5654n, this.f5544h);
                    this.f5538b.s(SDKStatus.getIntegrationSDKVersion());
                    aB();
                    B();
                }
            }
        }
        u.f5253a = !n.a(this.f5541e.getDirectDownload());
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5657q);
        long j2 = this.f5657q;
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
        UnifiedInterstitialAD unifiedInterstitialAD = this.f5659s;
        if (unifiedInterstitialAD == null || unifiedInterstitialAD.getECPM() <= 0 || this.f5658r) {
            return;
        }
        this.f5658r = true;
        ac.a("BeiZis", "channel == GDT竞价失败:" + i2);
        k.b((IBidding) this.f5659s, i2 != 1 ? 10001 : 1);
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
        Activity activity = (Activity) this.f5654n;
        if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
            this.f5659s = new UnifiedInterstitialAD(activity, this.f5545i, new a(), (Map) null, aJ());
        } else {
            this.f5659s = new UnifiedInterstitialAD(activity, this.f5545i, new a());
        }
        this.f5659s.setVideoOption(new VideoOption.Builder().setAutoPlayMuted(true).build());
        this.f5659s.loadFullScreenAD();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " FullScreenVideoWorkers:" + eVar.p().toString());
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
        UnifiedInterstitialAD unifiedInterstitialAD = this.f5659s;
        if (unifiedInterstitialAD != null && unifiedInterstitialAD.isValid() && activity != null) {
            this.f5659s.showFullScreenAD(activity);
            return;
        }
        e eVar = this.f5540d;
        if (eVar != null) {
            eVar.b(R2.drawable.ic_coupon_middle);
        }
    }
}
