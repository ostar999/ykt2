package com.beizi.fusion.work.nativead;

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
import com.kwad.sdk.api.KsAdVideoPlayConfig;
import com.kwad.sdk.api.KsFeedAd;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsScene;
import com.yikaobang.yixue.R2;
import java.util.List;

/* loaded from: classes2.dex */
public class f extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f6007n;

    /* renamed from: o, reason: collision with root package name */
    private String f6008o;

    /* renamed from: p, reason: collision with root package name */
    private long f6009p;

    /* renamed from: q, reason: collision with root package name */
    private long f6010q;

    /* renamed from: r, reason: collision with root package name */
    private KsFeedAd f6011r;

    /* renamed from: s, reason: collision with root package name */
    private float f6012s;

    /* renamed from: t, reason: collision with root package name */
    private float f6013t;

    /* renamed from: u, reason: collision with root package name */
    private View f6014u;

    public f(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, float f2, float f3) {
        this.f6007n = context;
        this.f6008o = str;
        this.f6009p = j2;
        this.f6010q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f6012s = f2;
        this.f6013t = f3;
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
    public void q() {
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
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.nativead.f.1
                        @Override // java.lang.Runnable
                        public void run() {
                            f.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "ks sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    o.a(this.f6007n, this.f5544h);
                    this.f5538b.u(KsAdSDK.getSDKVersion());
                    aB();
                    B();
                }
            }
        }
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f6010q);
        long j2 = this.f6010q;
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
    public String l() {
        if (this.f6011r == null) {
            return null;
        }
        return this.f6011r.getECPM() + "";
    }

    @Override // com.beizi.fusion.work.a
    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        if (this.f6012s <= 0.0f) {
            this.f6012s = as.k(this.f6007n);
        }
        KsScene ksSceneBuild = new KsScene.Builder(Long.parseLong(this.f5545i)).width(as.a(this.f6007n, this.f6012s)).adNum(1).build();
        KsLoadManager loadManager = KsAdSDK.getLoadManager();
        if (loadManager == null) {
            Log.d("BeiZis", "showKsNativeAd onError:渠道广告请求对象为空");
            b("渠道广告请求异常", R2.drawable.ic_cut_success_pop_bg_night);
        } else {
            if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
                ksSceneBuild.setBidResponse(aJ());
            }
            loadManager.loadConfigFeedAd(ksSceneBuild, new KsLoadManager.FeedAdListener() { // from class: com.beizi.fusion.work.nativead.f.2
                public void onError(int i2, String str) {
                    Log.d("BeiZis", "showKsNativeAd Callback --> onError: code = " + i2 + " ，message= " + str);
                    f.this.b(str, i2);
                }

                public void onFeedAdLoad(@Nullable List<KsFeedAd> list) {
                    Log.d("BeiZis", "showKsNativeAd Callback --> onFeedAdLoad()");
                    ((com.beizi.fusion.work.a) f.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                    f.this.E();
                    if (list == null || list.size() == 0) {
                        f.this.e(-991);
                        return;
                    }
                    f.this.a(list);
                    if (f.this.ac()) {
                        f.this.b();
                    } else {
                        f.this.S();
                    }
                }
            });
        }
    }

    @Override // com.beizi.fusion.work.a
    public View s() {
        return this.f6014u;
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
            if (this.f6011r != null && this.f6014u != null) {
                this.f5540d.a(g(), this.f6014u);
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
    public void a(List<KsFeedAd> list) {
        KsFeedAd ksFeedAd = list.get(0);
        this.f6011r = ksFeedAd;
        if (ksFeedAd == null) {
            return;
        }
        a(ksFeedAd.getECPM());
        this.f6011r.setVideoPlayConfig(new KsAdVideoPlayConfig.Builder().videoSoundEnable(false).dataFlowAutoStart(false).build());
        this.f6011r.setAdInteractionListener(new KsFeedAd.AdInteractionListener() { // from class: com.beizi.fusion.work.nativead.f.3

            /* renamed from: a, reason: collision with root package name */
            boolean f6017a = false;

            /* renamed from: b, reason: collision with root package name */
            boolean f6018b = false;

            public void onAdClicked() {
                Log.d("BeiZis", "showKsNativeAd Callback --> onAdClicked()");
                if (((com.beizi.fusion.work.a) f.this).f5540d != null && ((com.beizi.fusion.work.a) f.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) f.this).f5540d.d(f.this.g());
                }
                if (this.f6018b) {
                    return;
                }
                this.f6018b = true;
                f.this.K();
                f.this.an();
            }

            public void onAdShow() {
                Log.d("BeiZis", "showKsNativeAd Callback --> onAdShow()");
                ((com.beizi.fusion.work.a) f.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                if (((com.beizi.fusion.work.a) f.this).f5540d != null && ((com.beizi.fusion.work.a) f.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) f.this).f5540d.b(f.this.g());
                }
                if (this.f6017a) {
                    return;
                }
                this.f6017a = true;
                f.this.I();
                f.this.J();
                f.this.am();
            }

            public void onDislikeClicked() {
                Log.d("BeiZis", "showKsNativeAd Callback --> onDislikeClicked()");
                if (((com.beizi.fusion.work.a) f.this).f5540d != null) {
                    ((com.beizi.fusion.work.a) f.this).f5540d.b(f.this.g(), f.this.f6014u);
                }
                f.this.M();
            }

            public void onDownloadTipsDialogDismiss() {
                Log.d("BeiZis", "showKsNativeAd Callback --> onDownloadTipsDialogDismiss()");
            }

            public void onDownloadTipsDialogShow() {
                Log.d("BeiZis", "showKsNativeAd Callback --> onDownloadTipsDialogShow()");
            }
        });
        this.f6014u = this.f6011r.getFeedView(this.f6007n);
    }
}
