package com.beizi.fusion.work.nativead;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.v;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.yikaobang.yixue.R2;
import java.util.List;

/* loaded from: classes2.dex */
public class c extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5958n;

    /* renamed from: o, reason: collision with root package name */
    private String f5959o;

    /* renamed from: p, reason: collision with root package name */
    private long f5960p;

    /* renamed from: q, reason: collision with root package name */
    private long f5961q;

    /* renamed from: r, reason: collision with root package name */
    private TTAdNative f5962r;

    /* renamed from: s, reason: collision with root package name */
    private TTNativeExpressAd f5963s;

    /* renamed from: t, reason: collision with root package name */
    private float f5964t;

    /* renamed from: u, reason: collision with root package name */
    private float f5965u;

    /* renamed from: v, reason: collision with root package name */
    private View f5966v;

    public c(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, float f2, float f3) {
        this.f5958n = context;
        this.f5959o = str;
        this.f5960p = j2;
        this.f5961q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f5964t = f2;
        this.f5965u = f3;
        x();
    }

    @Override // com.beizi.fusion.work.a
    public void aF() {
        B();
        e();
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "CSJ";
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        if (this.f5540d == null) {
            return;
        }
        this.f5544h = this.f5541e.getAppId();
        this.f5545i = this.f5541e.getSpaceId();
        this.f5539c = com.beizi.fusion.f.b.a(this.f5541e.getId());
        ac.b("BeiZis", "AdWorker chanel = " + this.f5539c);
        com.beizi.fusion.b.d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.bytedance.sdk.openadsdk.TTAdNative")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.nativead.c.1
                        @Override // java.lang.Runnable
                        public void run() {
                            c.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "CSJ sdk not import , will do nothing");
                } else {
                    A();
                    v.a(this, this.f5958n, this.f5544h, this.f5541e.getDirectDownload());
                    this.f5538b.t(TTAdSdk.getAdManager().getSDKVersion());
                    aB();
                }
            }
        }
    }

    @Override // com.beizi.fusion.work.a
    public void e() {
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5961q);
        long j2 = this.f5961q;
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
        if (aC()) {
            return;
        }
        this.f5962r = v.a().createAdNative(this.f5958n);
        if (this.f5964t <= 0.0f) {
            this.f5964t = as.k(this.f5958n);
        }
        if (this.f5965u <= 0.0f) {
            this.f5965u = 0.0f;
        }
        this.f5962r.loadNativeExpressAd(new AdSlot.Builder().setCodeId(this.f5545i).setSupportDeepLink(true).setAdCount(1).setExpressViewAcceptedSize(this.f5964t, this.f5965u).build(), new TTAdNative.NativeExpressAdListener() { // from class: com.beizi.fusion.work.nativead.c.2
            public void onError(int i2, String str) {
                Log.d("BeiZis", "showCsjNativeAd Callback --> onError:" + str);
                c.this.b(str, i2);
            }

            public void onNativeExpressAdLoad(List<TTNativeExpressAd> list) {
                Log.d("BeiZis", "showCsjNativeAd Callback --> onNativeExpressAdLoad()");
                ((com.beizi.fusion.work.a) c.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                c.this.E();
                if (list == null || list.size() == 0) {
                    c.this.e(-991);
                    return;
                }
                c.this.f5963s = list.get(0);
                c cVar = c.this;
                cVar.a(cVar.f5963s);
                c.this.f5963s.render();
            }
        });
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        TTNativeExpressAd tTNativeExpressAd = this.f5963s;
        if (tTNativeExpressAd != null) {
            tTNativeExpressAd.destroy();
        }
    }

    @Override // com.beizi.fusion.work.a
    public View s() {
        return this.f5966v;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " NativeAdWorkers:" + eVar.p().toString());
        ad();
        h hVar = this.f5543g;
        if (hVar == h.SUCCESS) {
            if (this.f5963s != null && this.f5966v != null) {
                this.f5540d.a(g(), this.f5966v);
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
    public void a(TTNativeExpressAd tTNativeExpressAd) {
        tTNativeExpressAd.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() { // from class: com.beizi.fusion.work.nativead.c.3

            /* renamed from: a, reason: collision with root package name */
            boolean f5969a = false;

            /* renamed from: b, reason: collision with root package name */
            boolean f5970b = false;

            public void onAdClicked(View view, int i2) {
                Log.d("BeiZis", "showCsjNativeAd Callback --> onAdClicked()");
                if (((com.beizi.fusion.work.a) c.this).f5540d != null && ((com.beizi.fusion.work.a) c.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) c.this).f5540d.d(c.this.g());
                }
                if (this.f5970b) {
                    return;
                }
                this.f5970b = true;
                c.this.K();
                c.this.an();
            }

            public void onAdShow(View view, int i2) {
                Log.d("BeiZis", "showCsjNativeAd Callback --> onAdShow()");
                ((com.beizi.fusion.work.a) c.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                if (((com.beizi.fusion.work.a) c.this).f5540d != null && ((com.beizi.fusion.work.a) c.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) c.this).f5540d.b(c.this.g());
                }
                if (this.f5969a) {
                    return;
                }
                this.f5969a = true;
                c.this.I();
                c.this.J();
                c.this.am();
            }

            public void onRenderFail(View view, String str, int i2) {
                Log.d("BeiZis", "showCsjNativeAd Callback --> onRenderFail()");
                c.this.b(str, i2);
            }

            public void onRenderSuccess(View view, float f2, float f3) {
                Log.d("BeiZis", "showCsjNativeAd Callback --> onRenderSuccess()");
                c.this.f5966v = view;
                if (c.this.ac()) {
                    c.this.b();
                } else {
                    c.this.S();
                }
            }
        });
        b(tTNativeExpressAd);
    }

    private void b(TTNativeExpressAd tTNativeExpressAd) {
        try {
            tTNativeExpressAd.setDislikeCallback((Activity) this.f5958n, new TTAdDislike.DislikeInteractionCallback() { // from class: com.beizi.fusion.work.nativead.c.4
                public void onCancel() {
                }

                public void onSelected(int i2, String str, boolean z2) {
                    Log.d("BeiZis", "showCsjNativeAd Callback --> onSelected()");
                    if (((com.beizi.fusion.work.a) c.this).f5540d != null && ((com.beizi.fusion.work.a) c.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) c.this).f5540d.b(c.this.g(), c.this.f5966v);
                    }
                    c.this.M();
                }

                public void onShow() {
                }
            });
        } catch (Exception unused) {
        }
    }
}
