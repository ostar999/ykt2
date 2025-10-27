package com.beizi.fusion.work.g;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import com.beizi.fusion.d.h;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.huawei.openalliance.ad.inter.HiAd;
import com.huawei.openalliance.ad.inter.RewardAdLoader;
import com.huawei.openalliance.ad.inter.data.IRewardAd;
import com.huawei.openalliance.ad.inter.listeners.IRewardAdStatusListener;
import com.huawei.openalliance.ad.inter.listeners.RewardAdListener;
import com.yikaobang.yixue.R2;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class d extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5755n;

    /* renamed from: o, reason: collision with root package name */
    private String f5756o;

    /* renamed from: p, reason: collision with root package name */
    private long f5757p;

    /* renamed from: q, reason: collision with root package name */
    private long f5758q;

    /* renamed from: r, reason: collision with root package name */
    private RewardAdLoader f5759r;

    /* renamed from: s, reason: collision with root package name */
    private List<IRewardAd> f5760s;

    public d(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar) {
        this.f5755n = context;
        this.f5756o = str;
        this.f5757p = j2;
        this.f5758q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        x();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aL() {
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " RewardVideoWorkers:" + eVar.p().toString());
        ad();
        h hVar = this.f5543g;
        if (hVar == h.SUCCESS) {
            com.beizi.fusion.d.e eVar2 = this.f5540d;
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

    public String b() {
        return "1020";
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "HUAWEI";
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
                if (!as.a("com.huawei.openalliance.ad.inter.RewardAdLoader")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.g.d.1
                        @Override // java.lang.Runnable
                        public void run() {
                            d.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "HUAWEI sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    HiAd.getInstance(this.f5755n).initLog(true, 4);
                    HiAd.getInstance(this.f5755n).enableUserInfo(true);
                    B();
                }
            }
        }
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5758q);
        long j2 = this.f5758q;
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
        RewardAdLoader rewardAdLoader = new RewardAdLoader(this.f5755n, new String[]{this.f5545i});
        this.f5759r = rewardAdLoader;
        rewardAdLoader.setListener(new RewardAdListener() { // from class: com.beizi.fusion.work.g.d.2
            public void onAdFailed(int i2) {
                Log.d("BeiZis", "showHwRewardedVideo Callback --> onAdFailed: errorCode = " + i2);
                d.this.b(String.valueOf(i2), i2);
            }

            public void onAdsLoaded(Map<String, List<IRewardAd>> map) {
                Log.d("BeiZis", "showHwRewardedVideo Callback --> onAdsLoaded()");
                ((com.beizi.fusion.work.a) d.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                d.this.E();
                if (map == null && map.isEmpty()) {
                    d.this.e(-991);
                    return;
                }
                d dVar = d.this;
                dVar.f5760s = map.get(((com.beizi.fusion.work.a) dVar).f5545i);
                if (d.this.ac()) {
                    d.this.aL();
                } else {
                    d.this.S();
                }
            }
        });
        this.f5759r.loadAds(4, false);
    }

    @Override // com.beizi.fusion.work.a
    public void a(Activity activity) {
        List<IRewardAd> list = this.f5760s;
        if (list != null && !list.isEmpty()) {
            IRewardAd iRewardAd = this.f5760s.get(0);
            if (iRewardAd == null) {
                com.beizi.fusion.d.e eVar = this.f5540d;
                if (eVar != null) {
                    eVar.b(R2.drawable.ic_coupon_middle);
                    return;
                }
                return;
            }
            if (!iRewardAd.isExpired() && iRewardAd.isValid()) {
                iRewardAd.setMute(true);
                iRewardAd.show(this.f5755n, new IRewardAdStatusListener() { // from class: com.beizi.fusion.work.g.d.3

                    /* renamed from: a, reason: collision with root package name */
                    boolean f5763a = false;

                    /* renamed from: b, reason: collision with root package name */
                    boolean f5764b = false;

                    public void onAdClicked() {
                        Log.d("BeiZis", "showHwRewardedVideo Callback --> onAdClicked()");
                        if (((com.beizi.fusion.work.a) d.this).f5540d != null && ((com.beizi.fusion.work.a) d.this).f5540d.q() != 2) {
                            ((com.beizi.fusion.work.a) d.this).f5540d.d(d.this.g());
                        }
                        if (this.f5764b) {
                            return;
                        }
                        this.f5764b = true;
                        d.this.K();
                        d.this.an();
                    }

                    public void onAdClosed() {
                        Log.d("BeiZis", "showHwRewardedVideo Callback --> onAdClosed()");
                        if (((com.beizi.fusion.work.a) d.this).f5540d != null && ((com.beizi.fusion.work.a) d.this).f5540d.q() != 2) {
                            ((com.beizi.fusion.work.a) d.this).f5540d.c(d.this.b());
                        }
                        d.this.M();
                    }

                    public void onAdCompleted() {
                        Log.d("BeiZis", "showHwRewardedVideo Callback --> onAdCompleted()");
                        if (((com.beizi.fusion.work.a) d.this).f5540d != null) {
                            ((com.beizi.fusion.work.a) d.this).f5540d.k();
                        }
                    }

                    public void onAdError(int i2, int i3) {
                        Log.d("BeiZis", "showHwRewardedVideo Callback --> onAdError: code = " + i2 + " ，extra= " + i3);
                        d.this.b(String.valueOf(i2), i3);
                    }

                    public void onAdShown() {
                        Log.d("BeiZis", "showHwRewardedVideo Callback --> onAdShown()");
                        ((com.beizi.fusion.work.a) d.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                        if (((com.beizi.fusion.work.a) d.this).f5540d != null && ((com.beizi.fusion.work.a) d.this).f5540d.q() != 2) {
                            ((com.beizi.fusion.work.a) d.this).f5540d.b(d.this.g());
                        }
                        if (this.f5763a) {
                            return;
                        }
                        this.f5763a = true;
                        d.this.I();
                        d.this.J();
                        d.this.am();
                    }

                    public void onRewarded() {
                        Log.d("BeiZis", "showHwRewardedVideo Callback --> onRewarded()");
                        if (((com.beizi.fusion.work.a) d.this).f5540d != null) {
                            d.this.O();
                            ((com.beizi.fusion.work.a) d.this).f5540d.j();
                        }
                    }
                });
                return;
            } else {
                com.beizi.fusion.d.e eVar2 = this.f5540d;
                if (eVar2 != null) {
                    eVar2.b(R2.drawable.ic_coupon_middle);
                    return;
                }
                return;
            }
        }
        com.beizi.fusion.d.e eVar3 = this.f5540d;
        if (eVar3 != null) {
            eVar3.b(R2.drawable.ic_coupon_middle);
        }
    }
}
