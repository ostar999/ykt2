package com.beizi.fusion.work.splash;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.bytedance.msdk.adapter.pangle.PangleNetworkRequestInfo;
import com.bytedance.msdk.api.AdError;
import com.bytedance.msdk.api.AdSlot;
import com.bytedance.msdk.api.TTMediationAdSdk;
import com.bytedance.msdk.api.splash.TTSplashAd;
import com.bytedance.msdk.api.splash.TTSplashAdListener;
import com.bytedance.msdk.api.splash.TTSplashAdLoadCallback;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class g extends com.beizi.fusion.work.a {

    /* renamed from: n, reason: collision with root package name */
    private Context f6174n;

    /* renamed from: o, reason: collision with root package name */
    private String f6175o;

    /* renamed from: p, reason: collision with root package name */
    private long f6176p;

    /* renamed from: q, reason: collision with root package name */
    private View f6177q;

    /* renamed from: r, reason: collision with root package name */
    private ViewGroup f6178r;

    /* renamed from: s, reason: collision with root package name */
    private ViewGroup f6179s;

    /* renamed from: t, reason: collision with root package name */
    private TTSplashAd f6180t;

    /* renamed from: u, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6181u;

    /* renamed from: v, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6182v = new ArrayList();

    /* renamed from: w, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6183w = new ArrayList();

    /* renamed from: x, reason: collision with root package name */
    private float f6184x;

    /* renamed from: y, reason: collision with root package name */
    private float f6185y;

    /* renamed from: z, reason: collision with root package name */
    private boolean f6186z;

    public g(Context context, String str, long j2, View view, ViewGroup viewGroup, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.d.e eVar) {
        this.f6174n = context;
        this.f6175o = str;
        this.f6176p = j2;
        this.f6177q = view;
        this.f6178r = viewGroup;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f6179s = new SplashContainer(context);
        this.f6181u = list;
        x();
    }

    private void aL() {
        Log.d("BeiZis", "GmSplashWorker.finalShowAd()");
        ViewGroup viewGroup = this.f6178r;
        if (viewGroup != null) {
            this.f6180t.showAd(viewGroup);
        } else {
            aD();
        }
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "GM";
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
                if (!as.a("com.bytedance.msdk.api.splash.TTSplashAd")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.g.1
                        @Override // java.lang.Runnable
                        public void run() {
                            g.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    ac.a("BeiZis", "groMore sdk not import , will do nothing");
                    return;
                }
                A();
                ac.a("BeiZis", "requestAd() appId：" + this.f5544h + "  spaceId：" + this.f5545i);
                com.beizi.fusion.d.l.a(this.f6174n, this.f5544h, this.f5541e.getDirectDownload());
                this.f5538b.x(TTMediationAdSdk.getSdkVersion());
                aB();
                B();
            }
        }
        long sleepTime = this.f5542f.getSleepTime();
        if (this.f5540d.t()) {
            sleepTime = Math.max(sleepTime, this.f5542f.getHotRequestDelay());
        }
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + sleepTime);
        if (sleepTime > 0) {
            this.f5549m.sendEmptyMessageDelayed(1, sleepTime);
        } else {
            com.beizi.fusion.d.e eVar = this.f5540d;
            if (eVar != null && eVar.r() < 1 && this.f5540d.q() != 2) {
                p();
            }
        }
        this.f6184x = as.m(this.f6174n);
        this.f6185y = as.n(this.f6174n);
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
        Log.d("BeiZis", "GmSplashWorker.showAd()");
        aL();
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
        Activity activity = (Activity) this.f6174n;
        TTSplashAdListener tTSplashAdListener = new TTSplashAdListener() { // from class: com.beizi.fusion.work.splash.g.2
            public void onAdClicked() {
                Log.d("BeiZis", "onAdClicked");
                if (((com.beizi.fusion.work.a) g.this).f5540d != null) {
                    if (((com.beizi.fusion.work.a) g.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) g.this).f5540d.d(g.this.g());
                        ((com.beizi.fusion.work.a) g.this).f5549m.sendEmptyMessageDelayed(2, (((com.beizi.fusion.work.a) g.this).f5548l + 5000) - System.currentTimeMillis());
                    }
                    g.this.an();
                }
                g.this.K();
            }

            public void onAdDismiss() {
                Log.d("BeiZis", "onAdDismiss");
                if (((com.beizi.fusion.work.a) g.this).f5540d != null && ((com.beizi.fusion.work.a) g.this).f5540d.q() != 2) {
                    g.this.ah();
                }
                g.this.M();
            }

            public void onAdShow() {
                Log.d("BeiZis", "GmSplashWorker.onAdShow");
                g.this.ag();
                g.this.I();
                g.this.J();
                g.this.am();
            }

            public void onAdShowFail(AdError adError) {
                Log.d("BeiZis", "onAdShowFail");
                g.this.b(adError.message, adError.code);
            }

            public void onAdSkip() {
                Log.d("BeiZis", "onAdSkip");
                if (((com.beizi.fusion.work.a) g.this).f5540d != null && ((com.beizi.fusion.work.a) g.this).f5540d.q() != 2) {
                    g.this.ah();
                }
                g.this.N();
            }
        };
        if (this.f5545i == null) {
            return;
        }
        TTSplashAd tTSplashAd = new TTSplashAd(activity, this.f5545i);
        this.f6180t = tTSplashAd;
        tTSplashAd.setTTAdSplashListener(tTSplashAdListener);
        AdSlot adSlotBuild = new AdSlot.Builder().setImageAdSize(R2.attr.color_hot_circle_one_end, R2.attr.iconTint).setSplashButtonType(1).setDownloadType(1).build();
        ac.a("BeiZis", "request() appId：" + this.f5544h + "spaceId：" + this.f5545i);
        this.f6180t.loadAd(adSlotBuild, new PangleNetworkRequestInfo(this.f5544h, this.f5545i), new TTSplashAdLoadCallback() { // from class: com.beizi.fusion.work.splash.g.3
            public void onAdLoadTimeout() {
                g.this.f6186z = true;
                Log.i("BeiZis", "onAdLoadTimeout.......");
                if (g.this.f6180t != null) {
                    Log.d("BeiZis", "ad load infos: " + g.this.f6180t.getAdLoadInfoList());
                }
            }

            public void onSplashAdLoadFail(AdError adError) {
                if (adError == null) {
                    return;
                }
                Log.d("BeiZis", adError.message);
                g.this.f6186z = true;
                Log.e("BeiZis", "GmSplashWorker.onSplashAdLoadFail : " + adError.code + ", " + adError.message);
                g.this.b(adError.message, adError.code);
            }

            public void onSplashAdLoadSuccess() {
                Log.d("BeiZis", "GmSplashWorker.onSplashAdLoadSuccess ");
                ((com.beizi.fusion.work.a) g.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                g.this.E();
                if (g.this.ac()) {
                    g.this.b();
                } else {
                    g.this.S();
                }
            }
        }, (int) this.f6176p);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", " GmSplashWorker.load():" + eVar.p().toString());
        ad();
        com.beizi.fusion.d.h hVar = this.f5543g;
        if (hVar == com.beizi.fusion.d.h.SUCCESS) {
            ai();
            return;
        }
        if (hVar == com.beizi.fusion.d.h.FAIL) {
            Log.d("BeiZis", "other worker shown," + g() + " remove");
        }
    }

    @Override // com.beizi.fusion.work.a
    public void a(Message message) {
        com.beizi.fusion.b.b bVar = this.f5538b;
        if (bVar != null) {
            bVar.i(String.valueOf(message.obj));
            aB();
            H();
        }
    }
}
