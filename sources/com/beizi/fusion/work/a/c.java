package com.beizi.fusion.work.a;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.beizi.fusion.b.d;
import com.beizi.fusion.d.e;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.k;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ah;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.n;
import com.beizi.fusion.g.u;
import com.beizi.fusion.model.AdSpacesBean;
import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.comm.managers.status.SDKStatus;
import com.qq.e.comm.pi.IBidding;
import com.qq.e.comm.util.AdError;
import com.yikaobang.yixue.R2;
import java.util.Map;

/* loaded from: classes2.dex */
public class c extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5597n;

    /* renamed from: o, reason: collision with root package name */
    private String f5598o;

    /* renamed from: p, reason: collision with root package name */
    private long f5599p;

    /* renamed from: q, reason: collision with root package name */
    private long f5600q;

    /* renamed from: r, reason: collision with root package name */
    private boolean f5601r;

    /* renamed from: s, reason: collision with root package name */
    private UnifiedBannerView f5602s;

    /* renamed from: t, reason: collision with root package name */
    private ViewGroup f5603t;

    /* renamed from: u, reason: collision with root package name */
    private float f5604u;

    /* renamed from: v, reason: collision with root package name */
    private float f5605v;

    /* renamed from: w, reason: collision with root package name */
    private boolean f5606w;

    public class a implements UnifiedBannerADListener {

        /* renamed from: a, reason: collision with root package name */
        boolean f5608a;

        /* renamed from: b, reason: collision with root package name */
        boolean f5609b;

        private a() {
            this.f5608a = false;
            this.f5609b = false;
        }

        public void onADClicked() {
            Log.d("BeiZis", "showGdtBannerAd onADClicked()");
            if (((com.beizi.fusion.work.a) c.this).f5540d != null) {
                ((com.beizi.fusion.work.a) c.this).f5540d.d(c.this.g());
            }
            if (this.f5609b) {
                return;
            }
            this.f5609b = true;
            c.this.K();
            c.this.an();
        }

        public void onADClosed() {
            Log.d("BeiZis", "showGdtBannerAd onADClosed()");
            if (((com.beizi.fusion.work.a) c.this).f5540d != null) {
                ((com.beizi.fusion.work.a) c.this).f5540d.c(c.this.g());
            }
            c.this.M();
        }

        public void onADExposure() {
            Log.d("BeiZis", "showGdtBannerAd onADExposure()");
            ((com.beizi.fusion.work.a) c.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
            if (((com.beizi.fusion.work.a) c.this).f5540d != null) {
                ((com.beizi.fusion.work.a) c.this).f5540d.b(c.this.g());
            }
            if (this.f5608a) {
                return;
            }
            this.f5608a = true;
            c.this.aG();
            c.this.I();
            c.this.J();
            c.this.am();
        }

        public void onADLeftApplication() {
            Log.d("BeiZis", "showGdtBannerAd onADLeftApplication()");
        }

        public void onADReceive() {
            Log.d("BeiZis", "showGdtBannerAd onADReceive()");
            if (c.this.f5602s.getECPM() > 0) {
                c.this.a(r0.f5602s.getECPM());
            }
            if (u.f5253a) {
                c.this.f5602s.setDownloadConfirmListener(u.f5254b);
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
            Log.d("BeiZis", "showGdtBannerAd onError:" + adError.getErrorMsg());
            c.this.b(adError.getErrorMsg(), adError.getErrorCode());
            if (c.this.f5606w) {
                return;
            }
            c.this.q();
        }
    }

    public c(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, e eVar, float f2, float f3, ViewGroup viewGroup) {
        this.f5597n = context;
        this.f5598o = str;
        this.f5599p = j2;
        this.f5600q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f5604u = f2;
        this.f5605v = f3;
        this.f5603t = viewGroup;
        x();
    }

    private ViewGroup.LayoutParams aL() {
        if (this.f5604u <= 0.0f) {
            this.f5604u = as.k(this.f5597n);
        }
        if (this.f5605v <= 0.0f) {
            this.f5605v = Math.round(this.f5604u / 6.4f);
        }
        return new ViewGroup.LayoutParams(as.a(this.f5597n, this.f5604u), as.a(this.f5597n, this.f5605v));
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.f5602s == null) {
            return;
        }
        aq();
        int iA = ah.a(this.f5541e.getPriceDict(), this.f5602s.getECPMLevel());
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
        UnifiedBannerView unifiedBannerView = this.f5602s;
        if (unifiedBannerView == null || unifiedBannerView.getECPM() <= 0 || this.f5601r) {
            return;
        }
        this.f5601r = true;
        ac.a("BeiZis", "channel == GDT竞价成功");
        ac.a("BeiZis", "channel == sendWinNoticeECPM" + this.f5602s.getECPM());
        UnifiedBannerView unifiedBannerView2 = this.f5602s;
        k.a((IBidding) unifiedBannerView2, unifiedBannerView2.getECPM());
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
        d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.qq.e.ads.banner2.UnifiedBannerView")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.a.c.1
                        @Override // java.lang.Runnable
                        public void run() {
                            c.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "GDT sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    k.a(this.f5597n, this.f5544h);
                    this.f5538b.s(SDKStatus.getIntegrationSDKVersion());
                    aB();
                    B();
                }
            }
        }
        u.f5253a = !n.a(this.f5541e.getDirectDownload());
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5600q);
        long j2 = this.f5600q;
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
        UnifiedBannerView unifiedBannerView = this.f5602s;
        if (unifiedBannerView == null || unifiedBannerView.getECPM() <= 0 || this.f5601r) {
            return;
        }
        this.f5601r = true;
        ac.a("BeiZis", "channel == GDT竞价失败:" + i2);
        k.b((IBidding) this.f5602s, i2 != 1 ? 10001 : 1);
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
        this.f5606w = false;
        if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
            this.f5602s = new UnifiedBannerView((Activity) this.f5597n, this.f5545i, new a(), (Map) null, aJ());
        } else {
            this.f5602s = new UnifiedBannerView((Activity) this.f5597n, this.f5545i, new a());
        }
        this.f5602s.loadAD();
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        UnifiedBannerView unifiedBannerView = this.f5602s;
        if (unifiedBannerView != null) {
            unifiedBannerView.destroy();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        ViewGroup viewGroup;
        e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " BannerAdWorker:" + eVar.p().toString());
        ad();
        h hVar = this.f5543g;
        if (hVar == h.SUCCESS) {
            if (this.f5602s != null && (viewGroup = this.f5603t) != null) {
                if (viewGroup.getChildCount() > 0) {
                    this.f5603t.removeAllViews();
                }
                this.f5606w = true;
                this.f5603t.addView((View) this.f5602s, aL());
                this.f5540d.a(g(), (View) null);
                return;
            }
            this.f5540d.b(R2.drawable.ic_coupon_middle);
            return;
        }
        if (hVar == h.FAIL) {
            Log.d("BeiZis", "other worker shown," + g() + " remove");
        }
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
        ViewGroup viewGroup;
        Log.d("BeiZis", "showGdtBannerAd showAd()");
        if (this.f5602s != null && (viewGroup = this.f5603t) != null) {
            if (viewGroup.getChildCount() > 0) {
                this.f5603t.removeAllViews();
            }
            this.f5606w = true;
            this.f5603t.addView((View) this.f5602s, aL());
            return;
        }
        aD();
    }
}
