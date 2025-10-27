package com.beizi.fusion.work.a;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.beizi.fusion.b.d;
import com.beizi.fusion.d.e;
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
public class b extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5583n;

    /* renamed from: o, reason: collision with root package name */
    private String f5584o;

    /* renamed from: p, reason: collision with root package name */
    private long f5585p;

    /* renamed from: q, reason: collision with root package name */
    private long f5586q;

    /* renamed from: r, reason: collision with root package name */
    private TTAdNative f5587r;

    /* renamed from: s, reason: collision with root package name */
    private TTNativeExpressAd f5588s;

    /* renamed from: t, reason: collision with root package name */
    private View f5589t;

    /* renamed from: u, reason: collision with root package name */
    private float f5590u;

    /* renamed from: v, reason: collision with root package name */
    private float f5591v;

    /* renamed from: w, reason: collision with root package name */
    private ViewGroup f5592w;

    public b(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, e eVar, float f2, float f3, ViewGroup viewGroup) {
        this.f5583n = context;
        this.f5584o = str;
        this.f5585p = j2;
        this.f5586q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f5590u = f2;
        this.f5591v = f3;
        this.f5592w = viewGroup;
        x();
    }

    @Override // com.beizi.fusion.work.a
    public void aF() {
        B();
        e();
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
        ac.b("BeiZis", "AdWorker chanel = " + this.f5539c);
        d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.bytedance.sdk.openadsdk.TTAdNative")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.a.b.1
                        @Override // java.lang.Runnable
                        public void run() {
                            b.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "CSJ sdk not import , will do nothing");
                } else {
                    A();
                    v.a(this, this.f5583n, this.f5544h, this.f5541e.getDirectDownload());
                    this.f5538b.t(TTAdSdk.getAdManager().getSDKVersion());
                    aB();
                }
            }
        }
    }

    @Override // com.beizi.fusion.work.a
    public void e() {
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5586q);
        long j2 = this.f5586q;
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
    public void f() {
        Log.d("BeiZis", "showCsjBannerAd showAd()");
        ViewGroup viewGroup = this.f5592w;
        if (viewGroup == null || this.f5589t == null) {
            aD();
            return;
        }
        if (viewGroup.getChildCount() > 0) {
            this.f5592w.removeAllViews();
        }
        this.f5592w.addView(this.f5589t);
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
        if (this.f5590u <= 0.0f) {
            this.f5590u = as.k(this.f5583n);
        }
        if (this.f5591v <= 0.0f) {
            this.f5591v = Math.round(this.f5590u / 6.4f);
        }
        if (aC()) {
            return;
        }
        this.f5587r = v.a().createAdNative((Activity) this.f5583n);
        this.f5587r.loadBannerExpressAd(new AdSlot.Builder().setCodeId(this.f5545i).setSupportDeepLink(true).setAdCount(1).setExpressViewAcceptedSize(this.f5590u, this.f5591v).build(), new TTAdNative.NativeExpressAdListener() { // from class: com.beizi.fusion.work.a.b.2
            public void onError(int i2, String str) {
                Log.d("BeiZis", "showCsjBannerAd Callback --> onError:" + str);
                b.this.b(str, i2);
            }

            public void onNativeExpressAdLoad(List<TTNativeExpressAd> list) {
                Log.d("BeiZis", "showCsjBannerAd Callback --> onNativeExpressAdLoad()");
                ((com.beizi.fusion.work.a) b.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                b.this.E();
                if (list == null || list.size() == 0) {
                    b.this.e(-991);
                    return;
                }
                b.this.f5588s = list.get(0);
                b.this.f5588s.setSlideIntervalTime(30000);
                b bVar = b.this;
                bVar.a(bVar.f5588s);
                b.this.f5588s.render();
            }
        });
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        TTNativeExpressAd tTNativeExpressAd = this.f5588s;
        if (tTNativeExpressAd != null) {
            tTNativeExpressAd.destroy();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " BannerAdWorkers:" + eVar.p().toString());
        ad();
        h hVar = this.f5543g;
        if (hVar == h.SUCCESS) {
            ViewGroup viewGroup = this.f5592w;
            if (viewGroup != null && this.f5589t != null) {
                if (viewGroup.getChildCount() > 0) {
                    this.f5592w.removeAllViews();
                }
                this.f5592w.addView(this.f5589t);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void a(TTNativeExpressAd tTNativeExpressAd) {
        tTNativeExpressAd.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() { // from class: com.beizi.fusion.work.a.b.3
            public void onAdClicked(View view, int i2) {
                Log.d("BeiZis", "showCsjBannerAd Callback --> onAdClicked()");
                if (((com.beizi.fusion.work.a) b.this).f5540d != null) {
                    ((com.beizi.fusion.work.a) b.this).f5540d.d(b.this.g());
                }
                b.this.K();
                b.this.an();
            }

            public void onAdShow(View view, int i2) {
                Log.d("BeiZis", "showCsjBannerAd Callback --> onAdShow()");
                ((com.beizi.fusion.work.a) b.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                if (((com.beizi.fusion.work.a) b.this).f5540d != null) {
                    ((com.beizi.fusion.work.a) b.this).f5540d.b(b.this.g());
                }
                b.this.I();
                b.this.J();
                b.this.am();
            }

            public void onRenderFail(View view, String str, int i2) {
                Log.d("BeiZis", "showCsjBannerAd Callback --> onRenderFail()");
                b.this.b(str, i2);
            }

            public void onRenderSuccess(View view, float f2, float f3) {
                Log.d("BeiZis", "showCsjBannerAd Callback --> onRenderSuccess() width== " + f2 + ", height== " + f3);
                b.this.f5589t = view;
                if (b.this.ac()) {
                    b.this.b();
                } else {
                    b.this.S();
                }
            }
        });
        b(tTNativeExpressAd);
    }

    private void b(TTNativeExpressAd tTNativeExpressAd) {
        try {
            tTNativeExpressAd.setDislikeCallback((Activity) this.f5583n, new TTAdDislike.DislikeInteractionCallback() { // from class: com.beizi.fusion.work.a.b.4
                public void onCancel() {
                }

                public void onSelected(int i2, String str, boolean z2) {
                    Log.d("BeiZis", "showCsjBannerAd Callback --> onSelected()");
                    if (((com.beizi.fusion.work.a) b.this).f5540d != null) {
                        ((com.beizi.fusion.work.a) b.this).f5540d.c(b.this.g());
                    }
                    b.this.M();
                }

                public void onShow() {
                }
            });
        } catch (Exception unused) {
        }
    }
}
