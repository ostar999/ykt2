package com.beizi.fusion.work.splash;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.huawei.openalliance.ad.beans.parameter.AdSlotParam;
import com.huawei.openalliance.ad.inter.HiAd;
import com.huawei.openalliance.ad.inter.listeners.AdActionListener;
import com.huawei.openalliance.ad.inter.listeners.AdListener;
import com.huawei.openalliance.ad.views.PPSSplashView;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class h extends com.beizi.fusion.work.a {

    /* renamed from: n, reason: collision with root package name */
    long f6190n;

    /* renamed from: o, reason: collision with root package name */
    private Context f6191o;

    /* renamed from: p, reason: collision with root package name */
    private String f6192p;

    /* renamed from: q, reason: collision with root package name */
    private long f6193q;

    /* renamed from: r, reason: collision with root package name */
    private ViewGroup f6194r;

    /* renamed from: s, reason: collision with root package name */
    private PPSSplashView f6195s;

    /* renamed from: t, reason: collision with root package name */
    private ViewGroup f6196t;

    public h(Context context, String str, long j2, ViewGroup viewGroup, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar) {
        this.f6191o = context;
        this.f6192p = str;
        this.f6193q = j2;
        this.f6194r = viewGroup;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f6196t = new SplashContainer(context);
        x();
    }

    private void aL() {
        ViewGroup viewGroup = this.f6194r;
        if (viewGroup == null) {
            aD();
            return;
        }
        viewGroup.removeAllViews();
        ViewGroup viewGroup2 = this.f6196t;
        if (viewGroup2 != null) {
            this.f6194r.addView(viewGroup2);
        } else {
            aD();
        }
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "HUAWEI";
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        if (this.f5540d == null) {
            return;
        }
        this.f6190n = System.currentTimeMillis();
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
                if (!as.a("com.huawei.openalliance.ad.views.PPSSplashView")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.h.1
                        @Override // java.lang.Runnable
                        public void run() {
                            h.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "HUAWEI sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    HiAd.getInstance(this.f6191o).initLog(true, 4);
                    HiAd.getInstance(this.f6191o).enableUserInfo(true);
                    B();
                }
            }
        }
        long sleepTime = this.f5542f.getSleepTime();
        if (this.f5540d.t()) {
            sleepTime = Math.max(sleepTime, this.f5542f.getHotRequestDelay());
        }
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + sleepTime);
        if (sleepTime > 0) {
            this.f5549m.sendEmptyMessageDelayed(1, sleepTime);
            return;
        }
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null || eVar.r() >= 1 || this.f5540d.q() == 2) {
            return;
        }
        p();
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
        Log.d("BeiZis", g() + " out make show ad");
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
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(this.f5545i);
        AdSlotParam.Builder builder = new AdSlotParam.Builder();
        builder.setAdIds(arrayList).setDeviceType(4).setOrientation(1).setTest(false);
        PPSSplashView pPSSplashView = new PPSSplashView(this.f6191o);
        this.f6195s = pPSSplashView;
        pPSSplashView.setAdSlotParam(builder.build());
        this.f6195s.setAdListener(new AdListener() { // from class: com.beizi.fusion.work.splash.h.2
            public void onAdDismissed() {
                Log.d("BeiZis", "showHwSplash onAdDismissed()");
                h.this.ah();
                h.this.M();
            }

            public void onAdFailedToLoad(int i2) {
                Log.d("BeiZis", "showHwSplash onAdFailedToLoad() " + i2);
                h.this.b(String.valueOf(i2), i2);
            }

            public void onAdLoaded() {
                Log.d("BeiZis", "showHwSplash onAdLoaded()");
                h.this.E();
                ((com.beizi.fusion.work.a) h.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                h hVar = h.this;
                hVar.f6196t = hVar.f6195s;
                if (h.this.ac()) {
                    h.this.b();
                } else {
                    h.this.S();
                }
            }
        });
        this.f6195s.setAdActionListener(new AdActionListener() { // from class: com.beizi.fusion.work.splash.h.3
            public void onAdClick() {
                Log.d("BeiZis", "showHwSplash onAdClick()");
                h.this.K();
                if (((com.beizi.fusion.work.a) h.this).f5540d != null) {
                    if (((com.beizi.fusion.work.a) h.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) h.this).f5540d.d(h.this.g());
                    }
                    h.this.an();
                }
            }

            public void onAdShowed() {
                Log.d("BeiZis", "showHwSplash onAdShowed()");
                h.this.I();
                ((com.beizi.fusion.work.a) h.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                h.this.ag();
                h.this.J();
                h.this.am();
            }
        });
        this.f6195s.loadAd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " splashWorkers:" + eVar.p().toString());
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
}
