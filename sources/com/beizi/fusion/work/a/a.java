package com.beizi.fusion.work.a;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import com.beizi.ad.AdListener;
import com.beizi.ad.AdRequest;
import com.beizi.ad.BannerAdView;
import com.beizi.ad.internal.animation.TransitionDirection;
import com.beizi.ad.internal.animation.TransitionType;
import com.beizi.fusion.b.d;
import com.beizi.fusion.d.e;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.x;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.r;
import com.beizi.fusion.model.AdSpacesBean;
import com.yikaobang.yixue.R2;
import java.util.List;

/* loaded from: classes2.dex */
public class a extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5566n;

    /* renamed from: o, reason: collision with root package name */
    private String f5567o;

    /* renamed from: p, reason: collision with root package name */
    private long f5568p;

    /* renamed from: q, reason: collision with root package name */
    private long f5569q;

    /* renamed from: r, reason: collision with root package name */
    private BannerAdView f5570r;

    /* renamed from: s, reason: collision with root package name */
    private ViewGroup f5571s;

    /* renamed from: t, reason: collision with root package name */
    private float f5572t;

    /* renamed from: u, reason: collision with root package name */
    private float f5573u;

    /* renamed from: v, reason: collision with root package name */
    private boolean f5574v;

    /* renamed from: w, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f5575w;

    /* renamed from: x, reason: collision with root package name */
    private AdSpacesBean.RenderViewBean f5576x;

    /* renamed from: y, reason: collision with root package name */
    private List<Pair<String, Integer>> f5577y;

    public a(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, e eVar, float f2, float f3, ViewGroup viewGroup) {
        this.f5566n = context;
        this.f5567o = str;
        this.f5568p = j2;
        this.f5569q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f5572t = f2;
        this.f5573u = f3;
        this.f5571s = viewGroup;
        x();
    }

    private ViewGroup.LayoutParams aL() {
        if (this.f5572t <= 0.0f) {
            this.f5572t = as.k(this.f5566n);
        }
        if (this.f5573u <= 0.0f) {
            this.f5573u = Math.round(this.f5572t / 6.4f);
        }
        return new ViewGroup.LayoutParams(as.a(this.f5566n, this.f5572t), as.a(this.f5566n, this.f5573u));
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.f5570r == null) {
            return;
        }
        aq();
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "BEIZI";
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
        this.f5575w = renderView;
        if (renderView != null && renderView.size() > 0) {
            AdSpacesBean.RenderViewBean renderViewBean = this.f5575w.get(0);
            this.f5576x = renderViewBean;
            this.f5577y = r.a(renderViewBean.getDpLinkUrlList());
        }
        d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.beizi.ad.BeiZi")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.a.a.1
                        @Override // java.lang.Runnable
                        public void run() {
                            a.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "BeiZi sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    x.a(this.f5566n, this.f5544h);
                    B();
                }
            }
        }
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5569q);
        long j2 = this.f5569q;
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
        ViewGroup viewGroup;
        Log.d("BeiZis", "showBeiZiBannerAd showAd()");
        if (this.f5570r == null || (viewGroup = this.f5571s) == null) {
            aD();
            return;
        }
        if (viewGroup.getChildCount() > 0) {
            this.f5571s.removeAllViews();
        }
        this.f5574v = true;
        this.f5571s.addView(this.f5570r, aL());
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
        this.f5574v = false;
        this.f5570r = new BannerAdView(this.f5566n);
        final AdRequest adRequestBuild = new AdRequest.Builder().build();
        this.f5570r.setAdUnitId(this.f5545i);
        this.f5570r.setTransitionType(TransitionType.MOVEIN);
        this.f5570r.setTransitionDerection(TransitionDirection.LEFT);
        this.f5570r.setTransitionDuration(600);
        this.f5570r.setAdListener(new AdListener() { // from class: com.beizi.fusion.work.a.a.2
            @Override // com.beizi.ad.AdListener
            public void onAdClicked() {
                if (a.this.f5570r != null) {
                    a.this.f5570r.setTouchAreaNormal();
                }
                Log.d("BeiZis", "showBeiZiBannerAd onADClicked()");
                if (((com.beizi.fusion.work.a) a.this).f5540d != null) {
                    ((com.beizi.fusion.work.a) a.this).f5540d.d(a.this.g());
                }
                a.this.K();
                a.this.an();
            }

            @Override // com.beizi.ad.AdListener
            public void onAdClosed() {
                Log.d("BeiZis", "showBeiZiBannerAd onADClosed()");
                if (((com.beizi.fusion.work.a) a.this).f5540d != null) {
                    ((com.beizi.fusion.work.a) a.this).f5540d.c(a.this.g());
                }
                a.this.M();
            }

            @Override // com.beizi.ad.AdListener
            public void onAdFailedToLoad(int i2) {
                Log.d("BeiZis", "showBeiZiBannerAd onError:" + i2);
                a.this.b(String.valueOf(i2), i2);
                if (a.this.f5574v) {
                    return;
                }
                a.this.q();
            }

            @Override // com.beizi.ad.AdListener
            public void onAdLoaded() {
                Log.d("BeiZis", "showBeiZiBannerAd onADReceive()");
                ((com.beizi.fusion.work.a) a.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                if (a.this.f5570r.getPrice() != null) {
                    try {
                        a aVar = a.this;
                        aVar.a(Double.parseDouble(aVar.f5570r.getPrice()));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                a.this.E();
                if (a.this.ac()) {
                    a.this.b();
                } else {
                    a.this.S();
                }
                if (a.this.f5570r == null || a.this.f5576x == null) {
                    return;
                }
                a.this.f5570r.setOrderOptimizeList(a.this.f5577y);
                a.this.f5570r.setAdOptimizePercent(a.this.f5576x.getOptimizePercent());
                a.this.f5570r.post(new Runnable() { // from class: com.beizi.fusion.work.a.a.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        a.this.f5570r.optimizeClickArea(a.this.f5576x.getOptimizeSize(), a.this.f5570r, a.this.f5571s, a.this.f5576x.getDirection());
                    }
                });
            }

            @Override // com.beizi.ad.AdListener
            public void onAdRequest() {
                Log.d("BeiZis", "showBeiZiBannerAd onAdRequest()");
                a.this.C();
            }

            @Override // com.beizi.ad.AdListener
            public void onAdShown() {
                Log.d("BeiZis", "showBeiZiBannerAd onADExposure()");
                ((com.beizi.fusion.work.a) a.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                if (((com.beizi.fusion.work.a) a.this).f5540d != null) {
                    ((com.beizi.fusion.work.a) a.this).f5540d.b(a.this.g());
                }
                a.this.I();
                a.this.J();
                a.this.am();
            }
        });
        this.f5570r.post(new Runnable() { // from class: com.beizi.fusion.work.a.a.3
            @Override // java.lang.Runnable
            public void run() {
                a.this.f5570r.loadAd(adRequestBuild);
            }
        });
        ViewGroup viewGroup = this.f5571s;
        if (viewGroup != null) {
            if (viewGroup.getChildCount() > 0) {
                this.f5571s.removeAllViews();
            }
            this.f5571s.addView(this.f5570r, aL());
        }
        this.f5570r.openAdInNativeBrowser(true);
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        BannerAdView bannerAdView = this.f5570r;
        if (bannerAdView != null) {
            bannerAdView.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " BannerAdWorker:" + eVar.p().toString());
        ad();
        h hVar = this.f5543g;
        if (hVar == h.SUCCESS) {
            if (this.f5570r != null && this.f5571s != null) {
                this.f5574v = true;
                this.f5540d.a(g(), (View) null);
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
