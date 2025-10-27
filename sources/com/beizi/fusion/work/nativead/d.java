package com.beizi.fusion.work.nativead;

import android.content.Context;
import android.util.Log;
import android.view.View;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.k;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ah;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.n;
import com.beizi.fusion.g.u;
import com.beizi.fusion.model.AdSpacesBean;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.comm.managers.status.SDKStatus;
import com.qq.e.comm.pi.IBidding;
import com.qq.e.comm.util.AdError;
import com.yikaobang.yixue.R2;
import java.util.List;

/* loaded from: classes2.dex */
public class d extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5973n;

    /* renamed from: o, reason: collision with root package name */
    private String f5974o;

    /* renamed from: p, reason: collision with root package name */
    private long f5975p;

    /* renamed from: q, reason: collision with root package name */
    private long f5976q;

    /* renamed from: r, reason: collision with root package name */
    private boolean f5977r;

    /* renamed from: s, reason: collision with root package name */
    private NativeExpressAD f5978s;

    /* renamed from: t, reason: collision with root package name */
    private NativeExpressADView f5979t;

    /* renamed from: u, reason: collision with root package name */
    private float f5980u;

    /* renamed from: v, reason: collision with root package name */
    private float f5981v;

    /* renamed from: w, reason: collision with root package name */
    private View f5982w;

    /* renamed from: x, reason: collision with root package name */
    private boolean f5983x;

    public class a implements NativeExpressAD.NativeExpressADListener {

        /* renamed from: a, reason: collision with root package name */
        boolean f5985a;

        /* renamed from: b, reason: collision with root package name */
        boolean f5986b;

        private a() {
            this.f5985a = false;
            this.f5986b = false;
        }

        public void onADClicked(NativeExpressADView nativeExpressADView) {
            Log.d("BeiZis", "showGdtNativeAd onADClicked()");
            if (((com.beizi.fusion.work.a) d.this).f5540d != null && ((com.beizi.fusion.work.a) d.this).f5540d.q() != 2) {
                ((com.beizi.fusion.work.a) d.this).f5540d.d(d.this.g());
            }
            if (this.f5986b) {
                return;
            }
            this.f5986b = true;
            d.this.K();
            d.this.an();
        }

        public void onADClosed(NativeExpressADView nativeExpressADView) {
            Log.d("BeiZis", "showGdtNativeAd onADClosed()");
            if (((com.beizi.fusion.work.a) d.this).f5540d != null && ((com.beizi.fusion.work.a) d.this).f5540d.q() != 2) {
                ((com.beizi.fusion.work.a) d.this).f5540d.b(d.this.g(), d.this.f5982w);
            }
            d.this.M();
        }

        public void onADExposure(NativeExpressADView nativeExpressADView) {
            Log.d("BeiZis", "showGdtNativeAd onADExposure()");
            ((com.beizi.fusion.work.a) d.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
            if (((com.beizi.fusion.work.a) d.this).f5540d != null && ((com.beizi.fusion.work.a) d.this).f5540d.q() != 2) {
                ((com.beizi.fusion.work.a) d.this).f5540d.b(d.this.g());
            }
            if (this.f5985a) {
                return;
            }
            this.f5985a = true;
            d.this.aG();
            d.this.I();
            d.this.J();
            d.this.am();
        }

        public void onADLeftApplication(NativeExpressADView nativeExpressADView) {
            Log.d("BeiZis", "showGdtNativeAd onADLeftApplication()");
        }

        public void onADLoaded(List<NativeExpressADView> list) {
            Log.d("BeiZis", "showGdtNativeAd onADLoad()");
            ((com.beizi.fusion.work.a) d.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
            d.this.E();
            if (list == null || list.size() == 0) {
                d.this.e(-991);
                return;
            }
            if (d.this.f5979t != null) {
                d.this.f5979t.destroy();
            }
            d.this.f5979t = list.get(0);
            if (d.this.f5979t.getECPM() > 0) {
                d.this.a(r3.f5979t.getECPM());
            }
            if (u.f5253a) {
                d.this.f5979t.setDownloadConfirmListener(u.f5254b);
            }
            if (d.this.f5979t.getBoundData().getAdPatternType() == 2) {
                d.this.f5983x = true;
                d.this.f5979t.setMediaListener(new NativeExpressMediaListener() { // from class: com.beizi.fusion.work.nativead.d.a.1
                    public void onVideoCached(NativeExpressADView nativeExpressADView) {
                        Log.d("BeiZis", "showGdtNativeAd onVideoCached()");
                        d.this.aL();
                    }

                    public void onVideoComplete(NativeExpressADView nativeExpressADView) {
                        Log.d("BeiZis", "showGdtNativeAd onVideoComplete()");
                    }

                    public void onVideoError(NativeExpressADView nativeExpressADView, AdError adError) {
                        Log.d("BeiZis", "showGdtNativeAd onVideoError()");
                    }

                    public void onVideoInit(NativeExpressADView nativeExpressADView) {
                        Log.d("BeiZis", "showGdtNativeAd onVideoInit()");
                    }

                    public void onVideoLoading(NativeExpressADView nativeExpressADView) {
                        Log.d("BeiZis", "showGdtNativeAd onVideoLoading()");
                    }

                    public void onVideoPageClose(NativeExpressADView nativeExpressADView) {
                        Log.d("BeiZis", "showGdtNativeAd onVideoPageClose()");
                    }

                    public void onVideoPageOpen(NativeExpressADView nativeExpressADView) {
                        Log.d("BeiZis", "showGdtNativeAd onVideoPageOpen()");
                    }

                    public void onVideoPause(NativeExpressADView nativeExpressADView) {
                        Log.d("BeiZis", "showGdtNativeAd onVideoPause()");
                    }

                    public void onVideoReady(NativeExpressADView nativeExpressADView, long j2) {
                        Log.d("BeiZis", "showGdtNativeAd onVideoReady()");
                    }

                    public void onVideoStart(NativeExpressADView nativeExpressADView) {
                        Log.d("BeiZis", "showGdtNativeAd onVideoStart()");
                    }
                });
                d.this.f5979t.preloadVideo();
            }
            d dVar = d.this;
            dVar.f5982w = dVar.f5979t;
            if (d.this.f5983x) {
                return;
            }
            d.this.aL();
        }

        public void onNoAD(AdError adError) {
            Log.d("BeiZis", "showGdtNativeAd onError:" + adError.getErrorMsg());
            d.this.b(adError.getErrorMsg(), adError.getErrorCode());
        }

        public void onRenderFail(NativeExpressADView nativeExpressADView) {
            Log.d("BeiZis", "showGdtNativeAd onRenderFail()");
            d.this.b("sdk custom error ".concat("Render Fail"), 99991);
        }

        public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
            Log.d("BeiZis", "showGdtNativeAd onRenderSuccess()");
        }
    }

    public d(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, float f2, float f3) {
        this.f5973n = context;
        this.f5974o = str;
        this.f5975p = j2;
        this.f5976q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f5980u = f2;
        this.f5981v = f3;
        x();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aL() {
        NativeExpressADView nativeExpressADView = this.f5979t;
        if (nativeExpressADView != null) {
            nativeExpressADView.render();
        }
        if (ac()) {
            b();
        } else {
            S();
        }
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.f5979t == null) {
            return;
        }
        aq();
        int iA = ah.a(this.f5541e.getPriceDict(), this.f5979t.getECPMLevel());
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
        NativeExpressADView nativeExpressADView = this.f5979t;
        if (nativeExpressADView == null || nativeExpressADView.getECPM() <= 0 || this.f5977r) {
            return;
        }
        this.f5977r = true;
        ac.a("BeiZis", "channel == GDT竞价成功");
        ac.a("BeiZis", "channel == sendWinNoticeECPM" + this.f5979t.getECPM());
        NativeExpressADView nativeExpressADView2 = this.f5979t;
        k.a((IBidding) nativeExpressADView2, nativeExpressADView2.getECPM());
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
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.nativead.d.1
                        @Override // java.lang.Runnable
                        public void run() {
                            d.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "GDT sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    k.a(this.f5973n, this.f5544h);
                    this.f5538b.s(SDKStatus.getIntegrationSDKVersion());
                    aB();
                    B();
                }
            }
        }
        u.f5253a = !n.a(this.f5541e.getDirectDownload());
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5976q);
        long j2 = this.f5976q;
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

    @Override // com.beizi.fusion.work.a
    public void f(int i2) {
        NativeExpressADView nativeExpressADView = this.f5979t;
        if (nativeExpressADView == null || nativeExpressADView.getECPM() <= 0 || this.f5977r) {
            return;
        }
        this.f5977r = true;
        ac.a("BeiZis", "channel == GDT竞价失败:" + i2);
        k.b((IBidding) this.f5979t, i2 != 1 ? 10001 : 1);
    }

    @Override // com.beizi.fusion.work.a
    public com.beizi.fusion.f.a k() {
        return this.f5546j;
    }

    @Override // com.beizi.fusion.work.a
    public String l() {
        NativeExpressADView nativeExpressADView = this.f5979t;
        if (nativeExpressADView == null) {
            return null;
        }
        int iA = ah.a(this.f5541e.getPriceDict(), nativeExpressADView.getECPMLevel());
        if (iA == -1 || iA == -2) {
            return null;
        }
        return iA + "";
    }

    @Override // com.beizi.fusion.work.a
    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        if (this.f5980u <= 0.0f) {
            this.f5980u = -1.0f;
        }
        if (this.f5981v <= 0.0f) {
            this.f5981v = -2.0f;
        }
        this.f5983x = false;
        if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
            this.f5978s = new NativeExpressAD(this.f5973n, new ADSize((int) this.f5980u, (int) this.f5981v), this.f5545i, new a(), aJ());
        } else {
            this.f5978s = new NativeExpressAD(this.f5973n, new ADSize((int) this.f5980u, (int) this.f5981v), this.f5545i, new a());
        }
        this.f5978s.setVideoOption(new VideoOption.Builder().setAutoPlayPolicy(0).setAutoPlayMuted(true).build());
        this.f5978s.loadAD(1);
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        NativeExpressADView nativeExpressADView = this.f5979t;
        if (nativeExpressADView != null) {
            nativeExpressADView.destroy();
        }
    }

    @Override // com.beizi.fusion.work.a
    public View s() {
        return this.f5982w;
    }

    private void b() {
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " NativeAdWorker:" + eVar.p().toString());
        ad();
        h hVar = this.f5543g;
        if (hVar == h.SUCCESS) {
            if (this.f5979t != null) {
                this.f5540d.a(g(), (View) this.f5979t);
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
}
