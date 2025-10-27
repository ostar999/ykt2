package com.beizi.fusion.work.nativead;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.k;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ah;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.n;
import com.beizi.fusion.g.u;
import com.beizi.fusion.model.AdSpacesBean;
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

/* loaded from: classes2.dex */
public class e extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5989n;

    /* renamed from: o, reason: collision with root package name */
    private String f5990o;

    /* renamed from: p, reason: collision with root package name */
    private long f5991p;

    /* renamed from: q, reason: collision with root package name */
    private long f5992q;

    /* renamed from: r, reason: collision with root package name */
    private boolean f5993r;

    /* renamed from: s, reason: collision with root package name */
    private NativeUnifiedAD f5994s;

    /* renamed from: t, reason: collision with root package name */
    private NativeUnifiedADData f5995t;

    /* renamed from: u, reason: collision with root package name */
    private float f5996u;

    /* renamed from: v, reason: collision with root package name */
    private float f5997v;

    /* renamed from: w, reason: collision with root package name */
    private ViewGroup f5998w;

    public class a implements NativeADUnifiedListener {
        private a() {
        }

        public void onADLoaded(List<NativeUnifiedADData> list) {
            Log.d("BeiZis", "ShowGdtNativeCustom onADLoaded()");
            ((com.beizi.fusion.work.a) e.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
            e.this.E();
            if (list == null || list.size() == 0) {
                e.this.e(-991);
                return;
            }
            e.this.f5995t = list.get(0);
            if (e.this.f5995t == null) {
                e.this.e(-991);
                return;
            }
            if (e.this.f5995t.getECPM() > 0) {
                e.this.a(r9.f5995t.getECPM());
            }
            if (u.f5253a) {
                e.this.f5995t.setDownloadConfirmListener(u.f5254b);
            }
            NativeADEventListener nativeADEventListener = new NativeADEventListener() { // from class: com.beizi.fusion.work.nativead.e.a.1

                /* renamed from: a, reason: collision with root package name */
                boolean f6001a = false;

                /* renamed from: b, reason: collision with root package name */
                boolean f6002b = false;

                public void onADClicked() {
                    Log.d("BeiZis", "ShowGdtNativeCustom onADClicked()");
                    if (((com.beizi.fusion.work.a) e.this).f5540d != null && ((com.beizi.fusion.work.a) e.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) e.this).f5540d.d(e.this.g());
                    }
                    if (this.f6002b) {
                        return;
                    }
                    this.f6002b = true;
                    e.this.K();
                    e.this.an();
                }

                public void onADError(AdError adError) {
                    Log.d("BeiZis", "ShowGdtNativeCustom onADError: " + adError.getErrorMsg());
                    e.this.b(adError.getErrorMsg(), adError.getErrorCode());
                }

                public void onADExposed() {
                    Log.d("BeiZis", "ShowGdtNativeCustom onExposed()");
                    ((com.beizi.fusion.work.a) e.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                    if (((com.beizi.fusion.work.a) e.this).f5540d != null && ((com.beizi.fusion.work.a) e.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) e.this).f5540d.b(e.this.g());
                    }
                    if (this.f6001a) {
                        return;
                    }
                    this.f6001a = true;
                    e.this.aG();
                    e.this.I();
                    e.this.J();
                    e.this.am();
                }

                public void onADStatusChanged() {
                    Log.d("BeiZis", "ShowGdtNativeCustom onADStatusChanged()");
                }
            };
            NativeADMediaListener nativeADMediaListener = new NativeADMediaListener() { // from class: com.beizi.fusion.work.nativead.e.a.2

                /* renamed from: a, reason: collision with root package name */
                boolean f6004a = false;

                public void onVideoClicked() {
                    Log.d("BeiZis", "ShowGdtNativeCustom MediaView onVideoClicked()");
                    if (((com.beizi.fusion.work.a) e.this).f5540d != null && ((com.beizi.fusion.work.a) e.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) e.this).f5540d.d(e.this.g());
                    }
                    if (this.f6004a) {
                        return;
                    }
                    this.f6004a = true;
                    e.this.K();
                    e.this.an();
                }

                public void onVideoCompleted() {
                    Log.d("BeiZis", "ShowGdtNativeCustom MediaView onVideoCompleted()");
                }

                public void onVideoError(AdError adError) {
                    Log.d("BeiZis", "ShowGdtNativeCustom MediaView onVideoError: " + adError.getErrorMsg());
                    e.this.b(adError.getErrorMsg(), adError.getErrorCode());
                }

                public void onVideoInit() {
                    Log.d("BeiZis", "ShowGdtNativeCustom MediaView onVideoInit()");
                }

                public void onVideoLoaded(int i2) {
                    Log.d("BeiZis", "ShowGdtNativeCustom MediaView onVideoLoaded()");
                }

                public void onVideoLoading() {
                    Log.d("BeiZis", "ShowGdtNativeCustom MediaView onVideoLoading()");
                }

                public void onVideoPause() {
                    Log.d("BeiZis", "ShowGdtNativeCustom MediaView onVideoPause()");
                }

                public void onVideoReady() {
                    Log.d("BeiZis", "ShowGdtNativeCustom MediaView onVideoReady()");
                }

                public void onVideoResume() {
                    Log.d("BeiZis", "ShowGdtNativeCustom MediaView onVideoResume()");
                }

                public void onVideoStart() {
                    Log.d("BeiZis", "ShowGdtNativeCustom MediaView onVideoStart()");
                }

                public void onVideoStop() {
                    Log.d("BeiZis", "ShowGdtNativeCustom MediaView onVideoStop()");
                }
            };
            View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.beizi.fusion.work.nativead.e.a.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Log.d("BeiZis", "ShowGdtNativeCustom onADClosed()");
                    if (((com.beizi.fusion.work.a) e.this).f5540d != null && ((com.beizi.fusion.work.a) e.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) e.this).f5540d.b(e.this.g(), e.this.f5998w);
                    }
                    e.this.M();
                }
            };
            GdtNativeCustomLayout gdtNativeCustomLayout = new GdtNativeCustomLayout(e.this.f5989n);
            gdtNativeCustomLayout.onBindData(e.this.f5995t, e.this.f5996u, e.this.f5997v, nativeADEventListener, nativeADMediaListener, onClickListener);
            e.this.f5998w = gdtNativeCustomLayout;
            e.this.aL();
        }

        public void onNoAD(AdError adError) {
            Log.d("BeiZis", "ShowGdtNativeCustom onNoAD: " + adError.getErrorMsg());
            e.this.b(adError.getErrorMsg(), adError.getErrorCode());
        }
    }

    public e(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, float f2, float f3) {
        this.f5989n = context;
        this.f5990o = str;
        this.f5991p = j2;
        this.f5992q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f5996u = f2;
        this.f5997v = f3;
        x();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aL() {
        if (ac()) {
            b();
        } else {
            S();
        }
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.f5995t == null) {
            return;
        }
        aq();
        int iA = ah.a(this.f5541e.getPriceDict(), this.f5995t.getECPMLevel());
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
        NativeUnifiedADData nativeUnifiedADData = this.f5995t;
        if (nativeUnifiedADData == null || nativeUnifiedADData.getECPM() <= 0 || this.f5993r) {
            return;
        }
        this.f5993r = true;
        ac.a("BeiZis", "channel == GDT竞价成功");
        ac.a("BeiZis", "channel == sendWinNoticeECPM" + this.f5995t.getECPM());
        NativeUnifiedADData nativeUnifiedADData2 = this.f5995t;
        k.a((IBidding) nativeUnifiedADData2, nativeUnifiedADData2.getECPM());
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
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.nativead.e.1
                        @Override // java.lang.Runnable
                        public void run() {
                            e.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "GDT sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    k.a(this.f5989n, this.f5544h);
                    this.f5538b.s(SDKStatus.getIntegrationSDKVersion());
                    aB();
                    B();
                }
            }
        }
        u.f5253a = !n.a(this.f5541e.getDirectDownload());
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5992q);
        long j2 = this.f5992q;
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
        NativeUnifiedADData nativeUnifiedADData = this.f5995t;
        if (nativeUnifiedADData == null || nativeUnifiedADData.getECPM() <= 0 || this.f5993r) {
            return;
        }
        this.f5993r = true;
        ac.a("BeiZis", "channel == GDT竞价失败:" + i2);
        k.b((IBidding) this.f5995t, i2 != 1 ? 10001 : 1);
    }

    @Override // com.beizi.fusion.work.a
    public com.beizi.fusion.f.a k() {
        return this.f5546j;
    }

    @Override // com.beizi.fusion.work.a
    public String l() {
        NativeUnifiedADData nativeUnifiedADData = this.f5995t;
        if (nativeUnifiedADData == null) {
            return null;
        }
        int iA = ah.a(this.f5541e.getPriceDict(), nativeUnifiedADData.getECPMLevel());
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
        if (this.f5996u <= 0.0f) {
            this.f5996u = as.k(this.f5989n);
        }
        if (this.f5997v <= 0.0f) {
            this.f5997v = 0.0f;
        }
        if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
            this.f5994s = new NativeUnifiedAD(this.f5989n, this.f5545i, new a(), aJ());
        } else {
            this.f5994s = new NativeUnifiedAD(this.f5989n, this.f5545i, new a());
        }
        this.f5994s.loadData(1);
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        NativeUnifiedADData nativeUnifiedADData = this.f5995t;
        if (nativeUnifiedADData != null) {
            nativeUnifiedADData.destroy();
        }
    }

    @Override // com.beizi.fusion.work.a
    public void r() {
        NativeUnifiedADData nativeUnifiedADData = this.f5995t;
        if (nativeUnifiedADData != null) {
            nativeUnifiedADData.resume();
        }
    }

    @Override // com.beizi.fusion.work.a
    public View s() {
        return this.f5998w;
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
            if (this.f5998w != null) {
                this.f5540d.a(g(), this.f5998w);
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
