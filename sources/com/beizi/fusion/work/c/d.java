package com.beizi.fusion.work.c;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import com.beizi.fusion.d.e;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.o;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsFullScreenVideoAd;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsScene;
import com.kwad.sdk.api.KsVideoPlayConfig;
import com.yikaobang.yixue.R2;
import java.util.List;

/* loaded from: classes2.dex */
public class d extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5664n;

    /* renamed from: o, reason: collision with root package name */
    private String f5665o;

    /* renamed from: p, reason: collision with root package name */
    private long f5666p;

    /* renamed from: q, reason: collision with root package name */
    private long f5667q;

    /* renamed from: r, reason: collision with root package name */
    private KsFullScreenVideoAd f5668r;

    public d(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, e eVar) {
        this.f5664n = context;
        this.f5665o = str;
        this.f5666p = j2;
        this.f5667q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        x();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aL() {
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
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.c.d.1
                        @Override // java.lang.Runnable
                        public void run() {
                            d.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "ks sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    o.a(this.f5664n, this.f5544h);
                    this.f5538b.u(KsAdSDK.getSDKVersion());
                    aB();
                    B();
                }
            }
        }
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5667q);
        long j2 = this.f5667q;
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
            Log.d("BeiZis", "showKsFullScreenVideo onError:渠道广告请求对象为空");
            b("渠道广告请求异常", R2.drawable.ic_cut_success_pop_bg_night);
        } else {
            if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
                ksSceneBuild.setBidResponse(aJ());
            }
            loadManager.loadFullScreenVideoAd(ksSceneBuild, new KsLoadManager.FullScreenVideoAdListener() { // from class: com.beizi.fusion.work.c.d.2
                public void onError(int i2, String str) {
                    Log.d("BeiZis", "showKsFullScreenVideo Callback --> onError: code = " + i2 + " ，message= " + str);
                    d.this.b(str, i2);
                }

                public void onFullScreenVideoAdLoad(@Nullable List<KsFullScreenVideoAd> list) {
                    Log.d("BeiZis", "showKsFullScreenVideo Callback --> onFullScreenVideoAdLoad()");
                    ((com.beizi.fusion.work.a) d.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                    d.this.E();
                    if (list == null || list.size() == 0) {
                        d.this.e(-991);
                        return;
                    }
                    d.this.f5668r = list.get(0);
                    if (d.this.f5668r != null) {
                        d.this.a(r3.f5668r.getECPM());
                    }
                    if (d.this.ac()) {
                        d.this.aL();
                    } else {
                        d.this.S();
                    }
                }

                public void onFullScreenVideoResult(@Nullable List<KsFullScreenVideoAd> list) {
                }
            });
        }
    }

    @Override // com.beizi.fusion.work.a
    public void a(Activity activity) {
        KsFullScreenVideoAd ksFullScreenVideoAd = this.f5668r;
        if (ksFullScreenVideoAd != null && ksFullScreenVideoAd.isAdEnable() && activity != null) {
            this.f5668r.setFullScreenVideoAdInteractionListener(new KsFullScreenVideoAd.FullScreenVideoAdInteractionListener() { // from class: com.beizi.fusion.work.c.d.3

                /* renamed from: a, reason: collision with root package name */
                boolean f5671a = false;

                /* renamed from: b, reason: collision with root package name */
                boolean f5672b = false;

                public void onAdClicked() {
                    Log.d("BeiZis", "showKsFullScreenVideo Callback --> onAdClicked()");
                    if (((com.beizi.fusion.work.a) d.this).f5540d != null && ((com.beizi.fusion.work.a) d.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) d.this).f5540d.d(d.this.g());
                    }
                    if (this.f5672b) {
                        return;
                    }
                    this.f5672b = true;
                    d.this.K();
                    d.this.an();
                }

                public void onPageDismiss() {
                    Log.d("BeiZis", "showKsFullScreenVideo Callback --> onPageDismiss()");
                    if (((com.beizi.fusion.work.a) d.this).f5540d != null && ((com.beizi.fusion.work.a) d.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) d.this).f5540d.c(d.this.b());
                    }
                    d.this.M();
                }

                public void onSkippedVideo() {
                    Log.d("BeiZis", "showKsFullScreenVideo Callback --> onSkippedVideo");
                }

                public void onVideoPlayEnd() {
                    Log.d("BeiZis", "showKsFullScreenVideo Callback --> onVideoPlayEnd()");
                }

                public void onVideoPlayError(int i2, int i3) {
                    Log.d("BeiZis", "showKsFullScreenVideo Callback --> onVideoPlayError: code = " + i2 + " ，extra= " + i3);
                    d.this.b(String.valueOf(i3), i2);
                }

                public void onVideoPlayStart() {
                    Log.d("BeiZis", "showKsFullScreenVideo Callback --> onVideoPlayStart()");
                    ((com.beizi.fusion.work.a) d.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                    if (((com.beizi.fusion.work.a) d.this).f5540d != null && ((com.beizi.fusion.work.a) d.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) d.this).f5540d.b(d.this.g());
                    }
                    if (this.f5671a) {
                        return;
                    }
                    this.f5671a = true;
                    d.this.I();
                    d.this.J();
                    d.this.am();
                }
            });
            this.f5668r.showFullScreenVideoAd(activity, (KsVideoPlayConfig) null);
        } else {
            e eVar = this.f5540d;
            if (eVar != null) {
                eVar.b(R2.drawable.ic_coupon_middle);
            }
        }
    }
}
