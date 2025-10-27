package com.beizi.fusion.work.b;

import android.content.Context;
import android.util.Log;
import android.view.View;
import com.beizi.fusion.b.d;
import com.beizi.fusion.d.c;
import com.beizi.fusion.d.e;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.v;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.yikaobang.yixue.R2;
import java.util.List;

/* loaded from: classes2.dex */
public class a extends com.beizi.fusion.work.a implements c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5611n;

    /* renamed from: o, reason: collision with root package name */
    private String f5612o;

    /* renamed from: p, reason: collision with root package name */
    private long f5613p;

    /* renamed from: q, reason: collision with root package name */
    private long f5614q;

    /* renamed from: r, reason: collision with root package name */
    private TTAdNative f5615r;

    /* renamed from: s, reason: collision with root package name */
    private View f5616s;

    public a(Context context, String str, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, e eVar) {
        this.f5611n = context;
        this.f5612o = str;
        this.f5613p = j2;
        this.f5614q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        x();
    }

    @Override // com.beizi.fusion.work.a
    public void aF() {
        B();
        e();
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "CSJ";
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        if (aC()) {
            return;
        }
        this.f5615r = v.a().createAdNative(this.f5611n);
        this.f5615r.loadExpressDrawFeedAd(new AdSlot.Builder().setCodeId(this.f5545i).setSupportDeepLink(true).setAdCount(1).setExpressViewAcceptedSize(com.beizi.fusion.g.a.a(this.f5611n), 0.0f).build(), new TTAdNative.NativeExpressAdListener() { // from class: com.beizi.fusion.work.b.a.2
            public void onError(int i2, String str) {
                Log.d("BeiZis", "showCsjDrawAd Callback --> onError code=" + i2 + " , message=" + str);
                a.this.b(str, i2);
            }

            public void onNativeExpressAdLoad(List<TTNativeExpressAd> list) {
                Log.d("BeiZis", "showCsjDrawAd Callback --> onNativeExpressAdLoad()");
                ((com.beizi.fusion.work.a) a.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                a.this.E();
                if (list == null || list.size() == 0) {
                    a.this.e(-991);
                } else {
                    a.this.a(list);
                }
            }
        });
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
    }

    @Override // com.beizi.fusion.work.a
    public View s() {
        return this.f5616s;
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
                if (!as.a("com.bytedance.sdk.openadsdk.TTAdNative")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.b.a.1
                        @Override // java.lang.Runnable
                        public void run() {
                            a.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "CSJ sdk not import , will do nothing");
                } else {
                    A();
                    v.a(this, this.f5611n, this.f5544h, this.f5541e.getDirectDownload());
                    this.f5538b.t(TTAdSdk.getAdManager().getSDKVersion());
                    aB();
                }
            }
        }
    }

    @Override // com.beizi.fusion.work.a
    public void e() {
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5614q);
        long j2 = this.f5614q;
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
            if (this.f5616s != null) {
                this.f5540d.a(g(), this.f5616s);
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
    public void a(List<TTNativeExpressAd> list) {
        TTNativeExpressAd tTNativeExpressAd = list.get(0);
        tTNativeExpressAd.setVideoAdListener(new TTNativeExpressAd.ExpressVideoAdListener() { // from class: com.beizi.fusion.work.b.a.3
            public void onClickRetry() {
                Log.d("BeiZis", "showCsjDrawAd Callback --> onClickRetry()");
            }

            public void onProgressUpdate(long j2, long j3) {
            }

            public void onVideoAdComplete() {
                Log.d("BeiZis", "showCsjDrawAd Callback --> onVideoAdComplete()");
            }

            public void onVideoAdContinuePlay() {
                Log.d("BeiZis", "showCsjDrawAd Callback --> onVideoAdContinuePlay()");
            }

            public void onVideoAdPaused() {
                Log.d("BeiZis", "showCsjDrawAd Callback --> onVideoAdPaused()");
            }

            public void onVideoAdStartPlay() {
                Log.d("BeiZis", "showCsjDrawAd Callback --> onVideoAdStartPlay()");
            }

            public void onVideoError(int i2, int i3) {
                Log.d("BeiZis", "showCsjDrawAd Callback --> onVideoError() errorCode=" + i2 + ", extraCode=" + i3);
                a.this.b(String.valueOf(i3), i2);
            }

            public void onVideoLoad() {
                Log.d("BeiZis", "showCsjDrawAd Callback --> onVideoLoad()");
            }
        });
        tTNativeExpressAd.setCanInterruptVideoPlay(true);
        tTNativeExpressAd.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() { // from class: com.beizi.fusion.work.b.a.4

            /* renamed from: a, reason: collision with root package name */
            boolean f5620a = false;

            /* renamed from: b, reason: collision with root package name */
            boolean f5621b = false;

            public void onAdClicked(View view, int i2) {
                Log.d("BeiZis", "showCsjDrawAd Callback --> onAdClicked()");
                if (((com.beizi.fusion.work.a) a.this).f5540d != null && ((com.beizi.fusion.work.a) a.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) a.this).f5540d.d(a.this.g());
                }
                if (this.f5621b) {
                    return;
                }
                this.f5621b = true;
                a.this.K();
                a.this.an();
            }

            public void onAdShow(View view, int i2) {
                Log.d("BeiZis", "showCsjDrawAd Callback --> onAdShow()");
                ((com.beizi.fusion.work.a) a.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                if (((com.beizi.fusion.work.a) a.this).f5540d != null && ((com.beizi.fusion.work.a) a.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) a.this).f5540d.b(a.this.g());
                }
                if (this.f5620a) {
                    return;
                }
                this.f5620a = true;
                a.this.I();
                a.this.J();
                a.this.am();
            }

            public void onRenderFail(View view, String str, int i2) {
                Log.d("BeiZis", "showCsjDrawAd Callback --> onRenderFail() code=" + i2 + ", error=" + str);
                a.this.b(str, i2);
            }

            public void onRenderSuccess(View view, float f2, float f3) {
                Log.d("BeiZis", "showCsjDrawAd Callback --> onRenderSuccess()");
                a.this.f5616s = view;
                if (a.this.ac()) {
                    a.this.b();
                } else {
                    a.this.S();
                }
            }
        });
        tTNativeExpressAd.render();
    }
}
