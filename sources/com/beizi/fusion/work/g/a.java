package com.beizi.fusion.work.g;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import com.baidu.mobads.sdk.api.AdSettings;
import com.baidu.mobads.sdk.api.RewardVideoAd;
import com.beizi.fusion.d.f;
import com.beizi.fusion.d.h;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.yikaobang.yixue.R2;

/* loaded from: classes2.dex */
public class a extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5721n;

    /* renamed from: o, reason: collision with root package name */
    private String f5722o;

    /* renamed from: p, reason: collision with root package name */
    private long f5723p;

    /* renamed from: q, reason: collision with root package name */
    private long f5724q;

    /* renamed from: r, reason: collision with root package name */
    private RewardVideoAd f5725r;

    public a(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar) {
        this.f5721n = context;
        this.f5722o = str;
        this.f5723p = j2;
        this.f5724q = j3;
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
        return "1018";
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "BAIDU";
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
                if (!as.a("com.baidu.mobads.sdk.api.BDAdConfig")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.g.a.1
                        @Override // java.lang.Runnable
                        public void run() {
                            a.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "BAIDU sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    this.f5538b.v(String.valueOf(AdSettings.getSDKVersion()));
                    f.a(this.f5721n, this.f5544h);
                    B();
                }
            }
        }
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5724q);
        long j2 = this.f5724q;
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
        RewardVideoAd rewardVideoAd = new RewardVideoAd(this.f5721n, this.f5545i, new RewardVideoAd.RewardVideoAdListener() { // from class: com.beizi.fusion.work.g.a.2

            /* renamed from: a, reason: collision with root package name */
            boolean f5727a = false;

            /* renamed from: b, reason: collision with root package name */
            boolean f5728b = false;

            public void onAdClick() {
                Log.d("BeiZis", "showBdRewardedVideo Callback --> onAdClick()");
                if (((com.beizi.fusion.work.a) a.this).f5540d != null && ((com.beizi.fusion.work.a) a.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) a.this).f5540d.d(a.this.g());
                }
                if (this.f5728b) {
                    return;
                }
                this.f5728b = true;
                a.this.K();
                a.this.an();
            }

            public void onAdClose(float f2) {
                Log.d("BeiZis", "showBdRewardedVideo Callback --> onAdClose()");
                if (((com.beizi.fusion.work.a) a.this).f5540d != null && ((com.beizi.fusion.work.a) a.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) a.this).f5540d.c(a.this.b());
                }
                a.this.M();
            }

            public void onAdFailed(String str) {
                Log.d("BeiZis", "showBdRewardedVideo Callback --> onAdFailed: " + str);
                a.this.b(str, R2.attr.toolbar_color_new);
            }

            public void onAdLoaded() {
                Log.d("BeiZis", "showBdRewardedVideo Callback --> onAdLoaded()");
            }

            public void onAdShow() {
                Log.d("BeiZis", "showBdRewardedVideo Callback --> onAdShow()");
                ((com.beizi.fusion.work.a) a.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                if (((com.beizi.fusion.work.a) a.this).f5540d != null && ((com.beizi.fusion.work.a) a.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) a.this).f5540d.b(a.this.g());
                }
                if (this.f5727a) {
                    return;
                }
                this.f5727a = true;
                a.this.I();
                a.this.J();
                a.this.am();
            }

            public void onAdSkip(float f2) {
                Log.d("BeiZis", "showBdRewardedVideo Callback --> onAdSkip():" + f2);
            }

            public void onRewardVerify(boolean z2) {
                Log.d("BeiZis", "showBdRewardedVideo Callback --> onRewardVerify():" + z2);
                if (z2) {
                    a.this.O();
                    if (((com.beizi.fusion.work.a) a.this).f5540d != null) {
                        ((com.beizi.fusion.work.a) a.this).f5540d.j();
                    }
                }
            }

            public void onVideoDownloadFailed() {
                Log.d("BeiZis", "showBdRewardedVideo Callback --> onVideoDownloadFailed()");
                a.this.b("sdk custom error ".concat("onVideoDownloadFailed"), 99991);
            }

            public void onVideoDownloadSuccess() {
                Log.d("BeiZis", "showBdRewardedVideo Callback --> onVideoDownloadSuccess()");
                ((com.beizi.fusion.work.a) a.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                a.this.E();
                if (a.this.ac()) {
                    a.this.aL();
                } else {
                    a.this.S();
                }
            }

            public void playCompletion() {
                Log.d("BeiZis", "showBdRewardedVideo Callback --> playCompletion()");
                if (((com.beizi.fusion.work.a) a.this).f5540d != null) {
                    ((com.beizi.fusion.work.a) a.this).f5540d.k();
                }
            }
        }, false);
        this.f5725r = rewardVideoAd;
        rewardVideoAd.setAppSid(this.f5544h);
        this.f5725r.load();
    }

    @Override // com.beizi.fusion.work.a
    public void a(Activity activity) {
        RewardVideoAd rewardVideoAd = this.f5725r;
        if (rewardVideoAd != null && rewardVideoAd.isReady()) {
            this.f5725r.show();
            return;
        }
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar != null) {
            eVar.b(R2.drawable.ic_coupon_middle);
        }
    }
}
