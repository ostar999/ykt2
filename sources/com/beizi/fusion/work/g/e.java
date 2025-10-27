package com.beizi.fusion.work.g;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.o;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsRewardVideoAd;
import com.kwad.sdk.api.KsScene;
import com.kwad.sdk.api.KsVideoPlayConfig;
import com.yikaobang.yixue.R2;
import java.util.List;

/* loaded from: classes2.dex */
public class e extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5766n;

    /* renamed from: o, reason: collision with root package name */
    private String f5767o;

    /* renamed from: p, reason: collision with root package name */
    private long f5768p;

    /* renamed from: q, reason: collision with root package name */
    private long f5769q;

    /* renamed from: r, reason: collision with root package name */
    private KsRewardVideoAd f5770r;

    public e(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar) {
        this.f5766n = context;
        this.f5767o = str;
        this.f5768p = j2;
        this.f5769q = j3;
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
        return "1019";
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "KUAISHOU";
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
                if (!as.a("com.kwad.sdk.api.KsAdSDK")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.g.e.1
                        @Override // java.lang.Runnable
                        public void run() {
                            e.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "ks sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    o.a(this.f5766n, this.f5544h);
                    this.f5538b.u(KsAdSDK.getSDKVersion());
                    aB();
                    B();
                }
            }
        }
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5769q);
        long j2 = this.f5769q;
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
        KsScene ksSceneBuild = new KsScene.Builder(Long.parseLong(this.f5545i)).build();
        KsLoadManager loadManager = KsAdSDK.getLoadManager();
        if (loadManager == null) {
            Log.d("BeiZis", "showKsRewardedVideo onError:渠道广告请求对象为空");
            b("渠道广告请求异常", R2.drawable.ic_cut_success_pop_bg_night);
        } else {
            if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
                ksSceneBuild.setBidResponse(aJ());
            }
            loadManager.loadRewardVideoAd(ksSceneBuild, new KsLoadManager.RewardVideoAdListener() { // from class: com.beizi.fusion.work.g.e.2
                public void onError(int i2, String str) {
                    Log.d("BeiZis", "showKsRewardedVideo Callback --> onError: code = " + i2 + " ，message= " + str);
                    e.this.b(str, i2);
                }

                public void onRewardVideoAdLoad(@Nullable List<KsRewardVideoAd> list) {
                    Log.d("BeiZis", "showKsRewardedVideo Callback --> onRewardVideoAdLoad()");
                    ((com.beizi.fusion.work.a) e.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                    e.this.E();
                    if (list == null || list.size() == 0) {
                        e.this.e(-991);
                        return;
                    }
                    e.this.f5770r = list.get(0);
                    if (e.this.f5770r != null) {
                        e.this.a(r3.f5770r.getECPM());
                    }
                    if (e.this.ac()) {
                        e.this.aL();
                    } else {
                        e.this.S();
                    }
                }

                public void onRewardVideoResult(@Nullable List<KsRewardVideoAd> list) {
                }
            });
        }
    }

    @Override // com.beizi.fusion.work.a
    public void a(Activity activity) {
        KsRewardVideoAd ksRewardVideoAd = this.f5770r;
        if (ksRewardVideoAd != null && ksRewardVideoAd.isAdEnable() && activity != null) {
            this.f5770r.setRewardAdInteractionListener(new KsRewardVideoAd.RewardAdInteractionListener() { // from class: com.beizi.fusion.work.g.e.3

                /* renamed from: a, reason: collision with root package name */
                boolean f5773a = false;

                /* renamed from: b, reason: collision with root package name */
                boolean f5774b = false;

                public void onAdClicked() {
                    Log.d("BeiZis", "showKsRewardedVideo Callback --> onAdClicked()");
                    if (((com.beizi.fusion.work.a) e.this).f5540d != null && ((com.beizi.fusion.work.a) e.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) e.this).f5540d.d(e.this.g());
                    }
                    if (this.f5774b) {
                        return;
                    }
                    this.f5774b = true;
                    e.this.K();
                    e.this.an();
                }

                public void onExtraRewardVerify(int i2) {
                    Log.d("BeiZis", "showKsRewardedVideo Callback --> onExtraRewardVerify()i:" + i2);
                }

                public void onPageDismiss() {
                    Log.d("BeiZis", "showKsRewardedVideo Callback --> onPageDismiss()");
                    if (((com.beizi.fusion.work.a) e.this).f5540d != null && ((com.beizi.fusion.work.a) e.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) e.this).f5540d.c(e.this.b());
                    }
                    e.this.M();
                }

                public void onRewardStepVerify(int i2, int i3) {
                    Log.d("BeiZis", "showKsRewardedVideo Callback --> onRewardStepVerify()i:" + i2 + " i1:" + i3);
                }

                public void onRewardVerify() {
                    Log.d("BeiZis", "showKsRewardedVideo Callback --> onRewardVerify()");
                    if (((com.beizi.fusion.work.a) e.this).f5540d != null) {
                        e.this.O();
                        ((com.beizi.fusion.work.a) e.this).f5540d.j();
                    }
                }

                public void onVideoPlayEnd() {
                    Log.d("BeiZis", "showKsRewardedVideo Callback --> onVideoPlayEnd()");
                    if (((com.beizi.fusion.work.a) e.this).f5540d != null) {
                        ((com.beizi.fusion.work.a) e.this).f5540d.k();
                    }
                }

                public void onVideoPlayError(int i2, int i3) {
                    Log.d("BeiZis", "showKsRewardedVideo Callback --> onVideoPlayError: code = " + i2 + " ，extra= " + i3);
                    e.this.b(String.valueOf(i3), i2);
                }

                public void onVideoPlayStart() {
                    Log.d("BeiZis", "showKsRewardedVideo Callback --> onVideoPlayStart()");
                    ((com.beizi.fusion.work.a) e.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                    if (((com.beizi.fusion.work.a) e.this).f5540d != null && ((com.beizi.fusion.work.a) e.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) e.this).f5540d.b(e.this.g());
                    }
                    if (this.f5773a) {
                        return;
                    }
                    this.f5773a = true;
                    e.this.I();
                    e.this.J();
                    e.this.am();
                }

                public void onVideoSkipToEnd(long j2) {
                    Log.d("BeiZis", "showKsRewardedVideo Callback --> onVideoSkipToEnd()");
                }
            });
            this.f5770r.showRewardVideoAd(activity, (KsVideoPlayConfig) null);
        } else {
            com.beizi.fusion.d.e eVar = this.f5540d;
            if (eVar != null) {
                eVar.b(R2.drawable.ic_coupon_middle);
            }
        }
    }
}
