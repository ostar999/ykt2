package com.beizi.fusion.work.b;

import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import com.beizi.fusion.b.d;
import com.beizi.fusion.d.c;
import com.beizi.fusion.d.e;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.o;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsDrawAd;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsScene;
import com.yikaobang.yixue.R2;
import java.util.List;

/* loaded from: classes2.dex */
public class b extends com.beizi.fusion.work.a implements c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5623n;

    /* renamed from: o, reason: collision with root package name */
    private String f5624o;

    /* renamed from: p, reason: collision with root package name */
    private long f5625p;

    /* renamed from: q, reason: collision with root package name */
    private long f5626q;

    /* renamed from: r, reason: collision with root package name */
    private View f5627r;

    public b(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, e eVar) {
        this.f5623n = context;
        this.f5624o = str;
        this.f5625p = j2;
        this.f5626q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        x();
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "KUAISHOU";
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        KsScene ksSceneBuild = new KsScene.Builder(Long.parseLong(this.f5545i)).adNum(1).build();
        KsLoadManager loadManager = KsAdSDK.getLoadManager();
        if (loadManager == null) {
            Log.d("BeiZis", "showKsDrawAd onError:渠道广告请求对象为空");
            b("渠道广告请求异常", R2.drawable.ic_cut_success_pop_bg_night);
        } else {
            if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
                ksSceneBuild.setBidResponse(aJ());
            }
            loadManager.loadDrawAd(ksSceneBuild, new KsLoadManager.DrawAdListener() { // from class: com.beizi.fusion.work.b.b.2
                public void onDrawAdLoad(@Nullable List<KsDrawAd> list) {
                    Log.d("BeiZis", "showKsDrawAd Callback --> onDrawAdLoad()");
                    ((com.beizi.fusion.work.a) b.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                    b.this.E();
                    if (list == null || list.size() == 0) {
                        b.this.e(-991);
                        return;
                    }
                    b.this.a(list);
                    if (b.this.ac()) {
                        b.this.b();
                    } else {
                        b.this.S();
                    }
                }

                public void onError(int i2, String str) {
                    Log.d("BeiZis", "showKsDrawAd Callback --> onError code=" + i2 + " , message=" + str);
                    b.this.b(str, i2);
                }
            });
        }
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
    }

    @Override // com.beizi.fusion.work.a
    public View s() {
        return this.f5627r;
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
        d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.kwad.sdk.api.KsAdSDK")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.b.b.1
                        @Override // java.lang.Runnable
                        public void run() {
                            b.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "Ks sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    o.a(this.f5623n, this.f5544h);
                    this.f5538b.u(KsAdSDK.getSDKVersion());
                    aB();
                    B();
                }
            }
        }
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5626q);
        long j2 = this.f5626q;
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

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " DrawAdWorkers:" + eVar.p().toString());
        ad();
        h hVar = this.f5543g;
        if (hVar == h.SUCCESS) {
            if (this.f5627r != null) {
                this.f5540d.a(g(), this.f5627r);
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
    public void a(List<KsDrawAd> list) {
        KsDrawAd ksDrawAd = list.get(0);
        if (ksDrawAd == null) {
            return;
        }
        a(ksDrawAd.getECPM());
        ksDrawAd.setAdInteractionListener(new KsDrawAd.AdInteractionListener() { // from class: com.beizi.fusion.work.b.b.3

            /* renamed from: a, reason: collision with root package name */
            boolean f5630a = false;

            /* renamed from: b, reason: collision with root package name */
            boolean f5631b = false;

            public void onAdClicked() {
                Log.d("BeiZis", "showKsDrawAd Callback --> onAdClicked()");
                if (((com.beizi.fusion.work.a) b.this).f5540d != null && ((com.beizi.fusion.work.a) b.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) b.this).f5540d.d(b.this.g());
                }
                if (this.f5631b) {
                    return;
                }
                this.f5631b = true;
                b.this.K();
                b.this.an();
            }

            public void onAdShow() {
                Log.d("BeiZis", "showKsDrawAd Callback --> onAdShow()");
                ((com.beizi.fusion.work.a) b.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                if (((com.beizi.fusion.work.a) b.this).f5540d != null && ((com.beizi.fusion.work.a) b.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) b.this).f5540d.b(b.this.g());
                }
                if (this.f5630a) {
                    return;
                }
                this.f5630a = true;
                b.this.I();
                b.this.J();
                b.this.am();
            }

            public void onVideoPlayEnd() {
                Log.d("BeiZis", "showKsDrawAd Callback --> onVideoPlayEnd()");
            }

            public void onVideoPlayError() {
                Log.d("BeiZis", "showKsDrawAd Callback --> onVideoPlayError()");
                b.this.b("sdk custom error ".concat("onVideoPlayError"), 99991);
            }

            public void onVideoPlayPause() {
                Log.d("BeiZis", "showKsDrawAd Callback --> onVideoPlayPause()");
            }

            public void onVideoPlayResume() {
                Log.d("BeiZis", "showKsDrawAd Callback --> onVideoPlayResume()");
            }

            public void onVideoPlayStart() {
                Log.d("BeiZis", "showKsDrawAd Callback --> onVideoPlayStart()");
            }
        });
        this.f5627r = ksDrawAd.getDrawView(this.f5623n);
    }
}
