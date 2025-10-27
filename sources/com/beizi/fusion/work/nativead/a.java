package com.beizi.fusion.work.nativead;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.baidu.mobads.sdk.api.AdSettings;
import com.baidu.mobads.sdk.api.BaiduNativeManager;
import com.baidu.mobads.sdk.api.ExpressResponse;
import com.baidu.mobads.sdk.api.RequestParameters;
import com.beizi.fusion.d.h;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.yikaobang.yixue.R2;
import java.util.List;

/* loaded from: classes2.dex */
public class a extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5909n;

    /* renamed from: o, reason: collision with root package name */
    private String f5910o;

    /* renamed from: p, reason: collision with root package name */
    private long f5911p;

    /* renamed from: q, reason: collision with root package name */
    private long f5912q;

    /* renamed from: r, reason: collision with root package name */
    private boolean f5913r;

    /* renamed from: s, reason: collision with root package name */
    private BaiduNativeManager f5914s;

    /* renamed from: t, reason: collision with root package name */
    private ExpressResponse f5915t;

    /* renamed from: u, reason: collision with root package name */
    private float f5916u;

    /* renamed from: v, reason: collision with root package name */
    private float f5917v;

    /* renamed from: w, reason: collision with root package name */
    private View f5918w;

    public a(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, float f2, float f3) {
        this.f5909n = context;
        this.f5910o = str;
        this.f5911p = j2;
        this.f5912q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f5916u = f2;
        this.f5917v = f3;
        x();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aL() {
        ExpressResponse expressResponse = this.f5915t;
        if (expressResponse != null) {
            expressResponse.setInteractionListener(new ExpressResponse.ExpressInteractionListener() { // from class: com.beizi.fusion.work.nativead.a.3

                /* renamed from: a, reason: collision with root package name */
                boolean f5921a = false;

                /* renamed from: b, reason: collision with root package name */
                boolean f5922b = false;

                public void onAdClick() {
                    Log.d("BeiZis", "showBdNativeAd Callback --> onAdClick()");
                    if (((com.beizi.fusion.work.a) a.this).f5540d != null && ((com.beizi.fusion.work.a) a.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) a.this).f5540d.d(a.this.g());
                    }
                    if (this.f5922b) {
                        return;
                    }
                    this.f5922b = true;
                    a.this.K();
                    a.this.an();
                }

                public void onAdExposed() {
                    Log.d("BeiZis", "showBdNativeAd Callback --> onAdExposed()");
                    ((com.beizi.fusion.work.a) a.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                    if (((com.beizi.fusion.work.a) a.this).f5540d != null && ((com.beizi.fusion.work.a) a.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) a.this).f5540d.b(a.this.g());
                    }
                    if (this.f5921a) {
                        return;
                    }
                    this.f5921a = true;
                    a.this.aG();
                    a.this.I();
                    a.this.J();
                    a.this.am();
                }

                public void onAdRenderFail(View view, String str, int i2) {
                    Log.d("BeiZis", "showBdNativeAd Callback --> onAdRenderFail() error:" + str + ";code:" + i2);
                    a.this.b(str, i2);
                }

                public void onAdRenderSuccess(View view, float f2, float f3) {
                    Log.d("BeiZis", "showBdNativeAd Callback --> onAdRenderSuccess()");
                    a aVar = a.this;
                    aVar.f5918w = aVar.f5915t.getExpressAdView();
                    if (a.this.ac()) {
                        a.this.b();
                    } else {
                        a.this.S();
                    }
                }

                public void onAdUnionClick() {
                    Log.d("BeiZis", "showBdNativeAd Callback --> onAdUnionClick()");
                }
            });
            this.f5915t.setAdPrivacyListener(new ExpressResponse.ExpressAdDownloadWindowListener() { // from class: com.beizi.fusion.work.nativead.a.4
                public void adDownloadWindowClose() {
                    Log.d("BeiZis", "showBdNativeAd Callback --> adDownloadWindowClose()");
                }

                public void adDownloadWindowShow() {
                    Log.d("BeiZis", "showBdNativeAd Callback --> adDownloadWindowShow()");
                }

                public void onADPermissionClose() {
                    Log.d("BeiZis", "showBdNativeAd Callback --> onADPermissionClose()");
                }

                public void onADPermissionShow() {
                    Log.d("BeiZis", "showBdNativeAd Callback --> onADPermissionShow()");
                }

                public void onADPrivacyClick() {
                    Log.d("BeiZis", "showBdNativeAd Callback --> onADPrivacyClick()");
                }
            });
            this.f5915t.setAdDislikeListener(new ExpressResponse.ExpressDislikeListener() { // from class: com.beizi.fusion.work.nativead.a.5
                public void onDislikeItemClick(String str) {
                    Log.d("BeiZis", "showBdNativeAd Callback --> onDislikeItemClick()");
                    Log.d("BeiZis", "showCsjNativeAd Callback --> onSelected()");
                    if (((com.beizi.fusion.work.a) a.this).f5540d != null && ((com.beizi.fusion.work.a) a.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) a.this).f5540d.b(a.this.g(), a.this.f5918w);
                    }
                    a.this.M();
                }

                public void onDislikeWindowClose() {
                    Log.d("BeiZis", "showBdNativeAd Callback --> onDislikeWindowClose()");
                }

                public void onDislikeWindowShow() {
                    Log.d("BeiZis", "showBdNativeAd Callback --> onDislikeWindowShow()");
                }
            });
        }
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.f5915t == null) {
            return;
        }
        aq();
    }

    @Override // com.beizi.fusion.work.a
    public void aG() {
        ExpressResponse expressResponse = this.f5915t;
        if (expressResponse == null || TextUtils.isEmpty(expressResponse.getECPMLevel()) || this.f5913r) {
            return;
        }
        this.f5913r = true;
        ac.a("BeiZis", "showBdNativeAd channel == Baidu竞价成功");
        ac.a("BeiZis", "showBdNativeAd channel == sendWinNoticeECPM:" + this.f5915t.getECPMLevel());
        ExpressResponse expressResponse2 = this.f5915t;
        expressResponse2.biddingSuccess(expressResponse2.getECPMLevel());
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "BAIDU";
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
                if (!as.a("com.baidu.mobads.sdk.api.BDAdConfig")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.nativead.a.1
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
                    aB();
                    com.beizi.fusion.d.f.a(this.f5909n, this.f5544h);
                    B();
                }
            }
        }
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5912q);
        long j2 = this.f5912q;
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
    public void f(int i2) {
        ExpressResponse expressResponse = this.f5915t;
        if (expressResponse == null || TextUtils.isEmpty(expressResponse.getECPMLevel()) || this.f5913r) {
            return;
        }
        this.f5913r = true;
        ac.a("BeiZis", "showBdNativeAd channel == Baidu竞价失败:" + i2);
        this.f5915t.biddingFail(i2 != 1 ? i2 != 2 ? "900" : "100" : "203");
    }

    @Override // com.beizi.fusion.work.a
    public com.beizi.fusion.f.a k() {
        return this.f5546j;
    }

    @Override // com.beizi.fusion.work.a
    public String l() {
        if (this.f5915t == null) {
            return null;
        }
        return this.f5915t.getECPMLevel() + "";
    }

    @Override // com.beizi.fusion.work.a
    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        BaiduNativeManager baiduNativeManager = new BaiduNativeManager(this.f5909n.getApplicationContext(), this.f5545i);
        this.f5914s = baiduNativeManager;
        baiduNativeManager.loadExpressAd((RequestParameters) null, new BaiduNativeManager.ExpressAdListener() { // from class: com.beizi.fusion.work.nativead.a.2
            public void onLpClosed() {
                Log.d("BeiZis", "showBdNativeAd Callback --> onLpClosed()");
            }

            public void onNativeFail(int i2, String str) {
                Log.d("BeiZis", "showBdNativeAd Callback --> onNativeFail() code:" + i2 + " message:" + str);
                a.this.b(str, i2);
            }

            public void onNativeLoad(List<ExpressResponse> list) {
                Log.d("BeiZis", "showBdNativeAd Callback --> onNativeLoad()");
                ((com.beizi.fusion.work.a) a.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                a.this.E();
                if (list == null || list.size() == 0) {
                    a.this.e(-991);
                    return;
                }
                a.this.f5915t = list.get(0);
                try {
                    if (a.this.f5915t != null && !TextUtils.isEmpty(a.this.f5915t.getECPMLevel())) {
                        Log.d("BeiZis", "showBdNativeAd getECPMLevel:" + a.this.f5915t.getECPMLevel());
                        a aVar = a.this;
                        aVar.a(Double.parseDouble(aVar.f5915t.getECPMLevel()));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                a.this.aL();
                a.this.f5915t.render();
            }

            public void onNoAd(int i2, String str) {
                Log.d("BeiZis", "showBdNativeAd  Callback --> onNoAd() code:" + i2 + " message:" + str);
                a.this.b(str, i2);
            }

            public void onVideoDownloadFailed() {
                Log.d("BeiZis", "showBdNativeAd Callback --> onVideoDownloadFailed()");
            }

            public void onVideoDownloadSuccess() {
                Log.d("BeiZis", "showBdNativeAd Callback --> onVideoDownloadSuccess()");
            }
        });
    }

    @Override // com.beizi.fusion.work.a
    public View s() {
        return this.f5918w;
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
            if (this.f5915t != null && this.f5918w != null) {
                this.f5540d.a(g(), this.f5918w);
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
