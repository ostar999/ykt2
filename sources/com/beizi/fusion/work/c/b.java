package com.beizi.fusion.work.c;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import com.beizi.fusion.d.e;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.v;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdLoadType;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.yikaobang.yixue.R2;

/* loaded from: classes2.dex */
public class b extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5642n;

    /* renamed from: o, reason: collision with root package name */
    private String f5643o;

    /* renamed from: p, reason: collision with root package name */
    private long f5644p;

    /* renamed from: q, reason: collision with root package name */
    private long f5645q;

    /* renamed from: r, reason: collision with root package name */
    private TTFullScreenVideoAd f5646r;

    /* renamed from: s, reason: collision with root package name */
    private TTAdNative f5647s;

    public b(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, e eVar) {
        this.f5642n = context;
        this.f5643o = str;
        this.f5644p = j2;
        this.f5645q = j3;
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

    @Override // com.beizi.fusion.work.a
    public void aF() {
        B();
        e();
    }

    public String b() {
        return "1013";
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
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.c.b.1
                        @Override // java.lang.Runnable
                        public void run() {
                            b.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "CSJ sdk not import , will do nothing");
                } else {
                    A();
                    v.a(this, this.f5642n, this.f5544h, this.f5541e.getDirectDownload());
                    this.f5538b.t(TTAdSdk.getAdManager().getSDKVersion());
                    aB();
                }
            }
        }
    }

    @Override // com.beizi.fusion.work.a
    public void e() {
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5645q);
        long j2 = this.f5645q;
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
        if (aC()) {
            return;
        }
        this.f5647s = v.a().createAdNative(this.f5642n);
        this.f5647s.loadFullScreenVideoAd(new AdSlot.Builder().setCodeId(this.f5545i).setExpressViewAcceptedSize(500.0f, 500.0f).setSupportDeepLink(true).setOrientation(1).setAdLoadType(TTAdLoadType.PRELOAD).build(), new TTAdNative.FullScreenVideoAdListener() { // from class: com.beizi.fusion.work.c.b.2
            private void a() {
                b.this.f5646r.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() { // from class: com.beizi.fusion.work.c.b.2.1

                    /* renamed from: a, reason: collision with root package name */
                    boolean f5650a = false;

                    /* renamed from: b, reason: collision with root package name */
                    boolean f5651b = false;

                    public void onAdClose() {
                        Log.d("BeiZis", "showCsjFullScreenVideo Callback --> onAdClose");
                        if (((com.beizi.fusion.work.a) b.this).f5540d != null && ((com.beizi.fusion.work.a) b.this).f5540d.q() != 2) {
                            ((com.beizi.fusion.work.a) b.this).f5540d.c(b.this.b());
                        }
                        b.this.M();
                    }

                    public void onAdShow() {
                        Log.d("BeiZis", "showCsjFullScreenVideo Callback --> onAdShow");
                        ((com.beizi.fusion.work.a) b.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                        if (((com.beizi.fusion.work.a) b.this).f5540d != null && ((com.beizi.fusion.work.a) b.this).f5540d.q() != 2) {
                            ((com.beizi.fusion.work.a) b.this).f5540d.b(b.this.g());
                        }
                        if (this.f5650a) {
                            return;
                        }
                        this.f5650a = true;
                        b.this.I();
                        b.this.J();
                        b.this.am();
                    }

                    public void onAdVideoBarClick() {
                        Log.d("BeiZis", "showCsjFullScreenVideo Callback --> onAdVideoBarClick");
                        if (((com.beizi.fusion.work.a) b.this).f5540d != null && ((com.beizi.fusion.work.a) b.this).f5540d.q() != 2) {
                            ((com.beizi.fusion.work.a) b.this).f5540d.d(b.this.g());
                        }
                        if (this.f5651b) {
                            return;
                        }
                        this.f5651b = true;
                        b.this.K();
                        b.this.an();
                    }

                    public void onSkippedVideo() {
                        Log.d("BeiZis", "showCsjFullScreenVideo Callback --> onSkippedVideo");
                    }

                    public void onVideoComplete() {
                        Log.d("BeiZis", "showCsjFullScreenVideo Callback --> onVideoComplete");
                    }
                });
                b.this.f5646r.setDownloadListener(new TTAppDownloadListener() { // from class: com.beizi.fusion.work.c.b.2.2
                    public void onDownloadActive(long j2, long j3, String str, String str2) {
                    }

                    public void onDownloadFailed(long j2, long j3, String str, String str2) {
                    }

                    public void onDownloadFinished(long j2, String str, String str2) {
                    }

                    public void onDownloadPaused(long j2, long j3, String str, String str2) {
                    }

                    public void onIdle() {
                    }

                    public void onInstalled(String str, String str2) {
                    }
                });
            }

            public void onError(int i2, String str) {
                Log.d("BeiZis", "showCsjFullScreenVideo Callback --> onError:" + str);
                b.this.b(str, i2);
            }

            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd tTFullScreenVideoAd) {
                Log.d("BeiZis", "showCsjFullScreenVideo Callback --> onFullScreenVideoAdLoad");
                ((com.beizi.fusion.work.a) b.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                b.this.E();
                if (tTFullScreenVideoAd == null) {
                    b.this.e(-991);
                    return;
                }
                b.this.f5646r = tTFullScreenVideoAd;
                a();
                if (b.this.ac()) {
                    b.this.aL();
                } else {
                    b.this.S();
                }
            }

            public void onFullScreenVideoCached() {
                Log.d("BeiZis", "showCsjFullScreenVideo Callback --> onFullScreenVideoCached");
            }

            public void onFullScreenVideoCached(TTFullScreenVideoAd tTFullScreenVideoAd) {
            }
        });
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        if (this.f5646r != null) {
            this.f5646r = null;
        }
    }

    @Override // com.beizi.fusion.work.a
    public void a(Activity activity) {
        TTFullScreenVideoAd tTFullScreenVideoAd = this.f5646r;
        if (tTFullScreenVideoAd != null && activity != null) {
            tTFullScreenVideoAd.showFullScreenVideoAd(activity, TTAdConstant.RitScenes.GAME_GIFT_BONUS, (String) null);
            return;
        }
        e eVar = this.f5540d;
        if (eVar != null) {
            eVar.b(R2.drawable.ic_coupon_middle);
        }
    }
}
