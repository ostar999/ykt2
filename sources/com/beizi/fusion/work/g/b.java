package com.beizi.fusion.work.g;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.v;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.yikaobang.yixue.R2;

/* loaded from: classes2.dex */
public class b extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5730n;

    /* renamed from: o, reason: collision with root package name */
    private String f5731o;

    /* renamed from: p, reason: collision with root package name */
    private long f5732p;

    /* renamed from: q, reason: collision with root package name */
    private long f5733q;

    /* renamed from: r, reason: collision with root package name */
    private TTRewardVideoAd f5734r;

    /* renamed from: s, reason: collision with root package name */
    private TTAdNative f5735s;

    /* renamed from: t, reason: collision with root package name */
    private String f5736t;

    /* renamed from: u, reason: collision with root package name */
    private String f5737u;

    public b(Context context, String str, String str2, String str3, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar) {
        this.f5730n = context;
        this.f5731o = str;
        this.f5732p = j2;
        this.f5733q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f5736t = str2;
        this.f5737u = str3;
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
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.g.b.1
                        @Override // java.lang.Runnable
                        public void run() {
                            b.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "CSJ sdk not import , will do nothing");
                } else {
                    A();
                    v.a(this, this.f5730n, this.f5544h, this.f5541e.getDirectDownload());
                    this.f5538b.t(TTAdSdk.getAdManager().getSDKVersion());
                    aB();
                }
            }
        }
    }

    @Override // com.beizi.fusion.work.a
    public void e() {
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5733q);
        long j2 = this.f5733q;
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
        this.f5735s = v.a().createAdNative(this.f5730n);
        this.f5735s.loadRewardVideoAd(new AdSlot.Builder().setCodeId(this.f5545i).setSupportDeepLink(true).setExpressViewAcceptedSize(500.0f, 500.0f).setUserID(this.f5736t).setMediaExtra(this.f5737u).setOrientation(1).build(), new TTAdNative.RewardVideoAdListener() { // from class: com.beizi.fusion.work.g.b.2
            private void a() {
                b.this.f5734r.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() { // from class: com.beizi.fusion.work.g.b.2.1

                    /* renamed from: a, reason: collision with root package name */
                    boolean f5740a = false;

                    /* renamed from: b, reason: collision with root package name */
                    boolean f5741b = false;

                    public void onAdClose() {
                        Log.d("BeiZis", "showCsjRewardedVideo Callback --> onAdClose()");
                        if (((com.beizi.fusion.work.a) b.this).f5540d != null && ((com.beizi.fusion.work.a) b.this).f5540d.q() != 2) {
                            ((com.beizi.fusion.work.a) b.this).f5540d.c(b.this.b());
                        }
                        b.this.M();
                    }

                    public void onAdShow() {
                        Log.d("BeiZis", "showCsjRewardedVideo Callback --> onAdShow()");
                        ((com.beizi.fusion.work.a) b.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                        if (((com.beizi.fusion.work.a) b.this).f5540d != null && ((com.beizi.fusion.work.a) b.this).f5540d.q() != 2) {
                            ((com.beizi.fusion.work.a) b.this).f5540d.b(b.this.g());
                        }
                        if (this.f5740a) {
                            return;
                        }
                        this.f5740a = true;
                        b.this.I();
                        b.this.J();
                        b.this.am();
                    }

                    public void onAdVideoBarClick() {
                        Log.d("BeiZis", "showCsjRewardedVideo Callback --> onAdVideoBarClick()");
                        if (((com.beizi.fusion.work.a) b.this).f5540d != null && ((com.beizi.fusion.work.a) b.this).f5540d.q() != 2) {
                            ((com.beizi.fusion.work.a) b.this).f5540d.d(b.this.g());
                        }
                        if (this.f5741b) {
                            return;
                        }
                        this.f5741b = true;
                        b.this.K();
                        b.this.an();
                    }

                    public void onRewardArrived(boolean z2, int i2, Bundle bundle) {
                    }

                    public void onRewardVerify(boolean z2, int i2, String str, int i3, String str2) {
                        Log.d("BeiZis", "showCsjRewardedVideo Callback --> onRewardVerify() result== " + ("verify:" + z2));
                        if (z2 && ((com.beizi.fusion.work.a) b.this).f5540d != null) {
                            b.this.O();
                            ((com.beizi.fusion.work.a) b.this).f5540d.j();
                        }
                        if (TextUtils.isEmpty(str2)) {
                            return;
                        }
                        b.this.b(str2, i3);
                    }

                    public void onSkippedVideo() {
                        Log.e("BeiZis", "showCsjRewardedVideo Callback --> onSkippedVideo()");
                    }

                    public void onVideoComplete() {
                        Log.d("BeiZis", "showCsjRewardedVideo Callback --> onVideoComplete()");
                        if (((com.beizi.fusion.work.a) b.this).f5540d != null) {
                            ((com.beizi.fusion.work.a) b.this).f5540d.k();
                        }
                    }

                    public void onVideoError() {
                        Log.d("BeiZis", "showCsjRewardedVideo Callback --> onVideoError()");
                        b.this.b("sdk custom error ".concat("onVideoError"), 99991);
                    }
                });
            }

            public void onError(int i2, String str) {
                Log.d("BeiZis", "showCsjRewardedVideo Callback --> onError:" + str);
                b.this.b(str, i2);
            }

            public void onRewardVideoAdLoad(TTRewardVideoAd tTRewardVideoAd) {
                Log.d("BeiZis", "showCsjRewardedVideo Callback --> onRewardVideoAdLoad()");
                ((com.beizi.fusion.work.a) b.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                b.this.E();
                if (tTRewardVideoAd == null) {
                    b.this.e(-991);
                    return;
                }
                b.this.f5734r = tTRewardVideoAd;
                a();
                if (b.this.ac()) {
                    b.this.aL();
                } else {
                    b.this.S();
                }
            }

            public void onRewardVideoCached() {
                Log.d("BeiZis", "showCsjRewardedVideo Callback --> onRewardVideoCached()");
            }

            public void onRewardVideoCached(TTRewardVideoAd tTRewardVideoAd) {
            }
        });
    }

    @Override // com.beizi.fusion.work.a
    public void a(Activity activity) {
        TTRewardVideoAd tTRewardVideoAd = this.f5734r;
        if (tTRewardVideoAd != null && activity != null) {
            tTRewardVideoAd.showRewardVideoAd(activity);
            return;
        }
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar != null) {
            eVar.b(R2.drawable.ic_coupon_middle);
        }
    }
}
